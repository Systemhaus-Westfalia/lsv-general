/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.                                     *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net                                                  *
 * or https://github.com/adempiere/adempiere/blob/develop/license.html        *
 *****************************************************************************/
package org.shw.bankstatement;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.adempiere.core.domains.models.X_I_BankStatement;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.compiere.impexp.BankStatementMatchInfo;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * Loads a Banco Agrícola XLS bank statement into the I_BankStatement staging
 * table, creating one statement record per calendar month.
 *
 * Expected XLS layout (Banco Agrícola El Salvador export format):
 *   Row 1: "Numero Cuenta:XXXXXXXXX"
 *   Row 2: "Saldo Anterior:$N,NNN.NN"
 *   Row 3: "Fecha Desde:d/m/yyyy"
 *   Row 4: "Fecha Hasta:d/m/yyyy"
 *   Row 5: Column headers
 *   Row 6+: Transactions
 *     Col 0: Fecha Transaccion  (dd/MM/yyyy)
 *     Col 1: Fecha Aplicada     (dd/MM/yyyy)
 *     Col 2: Hora               (HH:mm:ss AM/PM)
 *     Col 3: Transaccion        (description)
 *     Col 4: Canal              (channel)
 *     Col 5: Referencia         (bank reference, used as EftTrxID)
 *     Col 6: Cargo              (debit amount, empty if none)
 *     Col 7: Abono              (credit amount, empty if none)
 *     Col 8: Saldo              (running balance)
 *
 * After loading, run the standard "Import Bank Statement" process to move
 * rows from I_BankStatement into C_BankStatement / C_BankStatementLine.
 *
 * @author Systemhaus Westfalia
 */
public class SHW_LoadBankStatementAgricolaXLS extends SHW_LoadBankStatementAgricolaXLSAbstract {

	/** Full path to the .xls file on the server */
	//private String  p_FilePath;
	/** Bank account (C_BankAccount_ID) to assign to each imported row */
	//private int     p_C_BankAccount_ID;
	/** ISO currency code — defaults to USD */
	//private String  p_ISO_Code = "USD";
	/** When true, existing unimported rows for this account are deleted first */
	//private boolean p_DeleteOldUnimported = false;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	protected String doIt() throws Exception {
		if (isDeleteOldImported()) {
			int deleted = DB.executeUpdateEx(
				"DELETE FROM I_BankStatement WHERE I_IsImported='N' AND C_BankAccount_ID=? AND AD_Client_ID=?",
				new Object[]{getBankAccountId(), getAD_Client_ID()}, get_TrxName());
			if (deleted > 0)
				addLog("Deleted " + deleted + " previous unimported row(s)");
		}

		int inserted = 0;
		try (FileInputStream fis = new FileInputStream(getFilePathOrName().trim());
		     Workbook wb = new HSSFWorkbook(fis)) {

			Sheet sheet = wb.getSheetAt(0);

			// --- Header rows (0-indexed) ---
			// Row 1 (index 1): "Numero Cuenta:005558600623"
			// Row 2 (index 2): "Saldo Anterior:$3,098.13"
			BigDecimal openingBalance = parseOpeningBalance(sheet.getRow(2));

			// Rows 6+ (index 6+) are transactions; index 5 is the column header
			String lastMonth     = null;
			BigDecimal lastSaldo = openingBalance;

			int lineNo = 10;
			for (int r = 6; r <= sheet.getLastRowNum(); r++) {
				Row row = sheet.getRow(r);
				if (row == null) continue;

				String dateStr = getCellString(row.getCell(0));
				if (dateStr == null || dateStr.trim().isEmpty()) continue;

				Date txDate;
				try {
					txDate = DATE_FORMAT.parse(dateStr.trim());
				} catch (Exception ex) {
					log.warning("Row " + r + ": unparseable date '" + dateStr + "' — skipped");
					continue;
				}

				BigDecimal cargo  = getCellBigDecimal(row.getCell(6));
				BigDecimal abono  = getCellBigDecimal(row.getCell(7));
				BigDecimal saldo  = getCellBigDecimal(row.getCell(8));

				// Skip rows with neither a debit nor a credit
				if (cargo == null && abono == null) continue;

				// TrxAmt: positive for credit (abono), negative for debit (cargo)
				BigDecimal trxAmt;
				if (abono != null && abono.compareTo(Env.ZERO) != 0)
					trxAmt = abono;
				else
					trxAmt = cargo.negate();

				String month = getYearMonth(txDate);
				if (!month.equals(lastMonth)) {
					lastMonth = month;
					lineNo = 10;
				}

				String dateApplied  = getCellString(row.getCell(1));
				String hora         = getCellString(row.getCell(2));
				String descripcion  = getCellString(row.getCell(3));
				String canal        = getCellString(row.getCell(4));
				String referencia   = getCellString(row.getCell(5));

				// Unique transaction ID: reference + date + time
				String eftTrxID = buildEftTrxID(referencia, dateStr, hora);

				Timestamp txTimestamp    = new Timestamp(txDate.getTime());
				Timestamp valutaTimestamp = parseDateTimestamp(dateApplied, txTimestamp);

				X_I_BankStatement staging = new X_I_BankStatement(getCtx(), 0, get_TrxName());
				//staging.setAD_Org_ID(getAD_Org_ID());
				staging.setIsActive(true);
				staging.setI_IsImported(false);

				// Statement grouping: one statement per month
				staging.setName(month);
				staging.setStatementDate(lastDayOfMonth(txDate));

				// Bank account
				staging.setC_BankAccount_ID(getBankAccountId());
				//staging.setISO_Code(p_ISO_Code);

				// Line data
				staging.setStatementLineDate(txTimestamp);
				staging.setValutaDate(valutaTimestamp);
				staging.setTrxAmt(trxAmt);
				staging.setStmtAmt(trxAmt);   // no separate charge/interest in this format
				staging.setChargeAmt(Env.ZERO);
				staging.setInterestAmt(Env.ZERO);

				// EFT fields
				staging.setEftTrxID(eftTrxID);
				staging.setEftReference(referencia != null ? referencia : "");
				staging.setEftMemo(descripcion != null ? descripcion : "");
				staging.setEftPayee(canal != null ? truncate(canal, 60) : "");
				staging.setEftStatementLineDate(txTimestamp);
				staging.setEftValutaDate(valutaTimestamp);
				staging.setEftAmt(trxAmt);

				staging.setLine(lineNo);
				staging.saveEx();
				inserted++;
				lineNo += 10;

				if (saldo != null) lastSaldo = saldo;
			}
		}

		addLog("Rows inserted into I_BankStatement: " + inserted);

		// Phase 2: import staging rows → C_BankStatement / C_BankStatementLine + auto-match
		int[] counts = importAndMatch();
		addLog("Bank statements created: " + counts[0]);
		addLog("Lines created: " + counts[1]);
		addLog("Lines auto-matched: " + counts[2]);
		return "@Success@";
	}

	// -----------------------------------------------------------------------

	/**
	 * Moves I_BankStatement staging rows (I_IsImported='N') for this bank account
	 * into C_BankStatement / C_BankStatementLine, then runs SHW_AmountBankMatcher
	 * on each line. Returns [statementsCreated, linesCreated, linesMatched].
	 */
	private int[] importAndMatch() {
		List<X_I_BankStatement> stagingRows = new Query(getCtx(), X_I_BankStatement.Table_Name,
				"I_IsImported='N' AND C_BankAccount_ID=? AND AD_Client_ID=?", get_TrxName())
				.setParameters(getBankAccountId(), getAD_Client_ID())
				.setOrderBy("Name, StatementDate, Line")
				.list();

		SHW_AmountBankMatcher matcher = new SHW_AmountBankMatcher();
		MBankStatement currentStatement = null;
		String currentName = null;
		int stmtLineNo = 10;
		int statementsCreated = 0, linesCreated = 0, linesMatched = 0;

		for (X_I_BankStatement staging : stagingRows) {
			String name = staging.getName();
			if (!name.equals(currentName)) {
				currentName = name;
				stmtLineNo = 10;
				currentStatement = new MBankStatement(getCtx(), 0, get_TrxName());
				currentStatement.setC_BankAccount_ID(getBankAccountId());
				currentStatement.setName(name);
				currentStatement.setStatementDate(staging.getStatementDate());
				currentStatement.setEndingBalance(Env.ZERO);
				currentStatement.saveEx(get_TrxName());
				statementsCreated++;
			}

			MBankStatementLine line = new MBankStatementLine(currentStatement, staging, stmtLineNo);
			line.saveEx(get_TrxName());
			linesCreated++;
			stmtLineNo += 10;

			// auto-match
			BankStatementMatchInfo info = matcher.findMatch(line, null, null);
			if (info != null && info.getC_Payment_ID() > 0) {
				line.setC_Payment_ID(info.getC_Payment_ID());
				line.saveEx(get_TrxName());
				linesMatched++;
			}

			// mark staging row as imported
			staging.setC_BankStatement_ID(currentStatement.getC_BankStatement_ID());
			staging.setC_BankStatementLine_ID(line.getC_BankStatementLine_ID());
			staging.setI_IsImported(true);
			staging.setProcessed(true);
			staging.saveEx(get_TrxName());
		}

		return new int[]{statementsCreated, linesCreated, linesMatched};
	}

	// -----------------------------------------------------------------------

	private BigDecimal parseOpeningBalance(Row row) {
		if (row == null) return Env.ZERO;
		String cell = getCellString(row.getCell(0));
		if (cell == null) return Env.ZERO;
		// "Saldo Anterior:$3,098.13"
		int colon = cell.indexOf(':');
		if (colon < 0) return Env.ZERO;
		String raw = cell.substring(colon + 1).replaceAll("[$,]", "").trim();
		try { return new BigDecimal(raw); } catch (Exception e) { return Env.ZERO; }
	}

	private String getCellString(Cell cell) {
		if (cell == null) return null;
		CellType type = cell.getCellTypeEnum();
		if (type == CellType.BLANK) return null;
		if (type == CellType.NUMERIC) {
			// References stored as numbers — return without decimal point
			double v = cell.getNumericCellValue();
			if (v == Math.floor(v) && !Double.isInfinite(v))
				return String.valueOf((long) v);
			return String.valueOf(v);
		}
		String s = cell.toString().trim();
		return s.isEmpty() ? null : s;
	}

	private BigDecimal getCellBigDecimal(Cell cell) {
		if (cell == null) return null;
		CellType type = cell.getCellTypeEnum();
		if (type == CellType.BLANK) return null;
		if (type == CellType.NUMERIC) {
			double v = cell.getNumericCellValue();
			if (v == 0.0) return null;
			return BigDecimal.valueOf(v);
		}
		String s = cell.toString().trim().replaceAll(",", "");
		if (s.isEmpty()) return null;
		try { return new BigDecimal(s); } catch (Exception e) { return null; }
	}

	private String buildEftTrxID(String ref, String date, String time) {
		StringBuilder sb = new StringBuilder();
		if (ref  != null && !ref.isEmpty())  sb.append(ref);
		if (date != null && !date.isEmpty()) { if (sb.length() > 0) sb.append('_'); sb.append(date.replaceAll("/", "")); }
		if (time != null && !time.isEmpty()) { if (sb.length() > 0) sb.append('_'); sb.append(time.replaceAll(":", "").replaceAll(" ", "")); }
		return truncate(sb.toString(), 40);
	}

	private Timestamp parseDateTimestamp(String dateStr, Timestamp fallback) {
		if (dateStr == null || dateStr.trim().isEmpty()) return fallback;
		try { return new Timestamp(DATE_FORMAT.parse(dateStr.trim()).getTime()); }
		catch (Exception e) { return fallback; }
	}

	/** Returns the last day of the month of the given date as a Timestamp. */
	private Timestamp lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}

	/** Returns "yyyy-MM" for use as the statement Name (one statement per month). */
	private String getYearMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return String.format("%04d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
	}

	private String truncate(String s, int maxLen) {
		if (s == null) return null;
		return s.length() > maxLen ? s.substring(0, maxLen) : s;
	}
}

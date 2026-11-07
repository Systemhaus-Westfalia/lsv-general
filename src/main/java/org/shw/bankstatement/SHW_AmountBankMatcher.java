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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.core.domains.models.X_I_BankStatement;
import org.compiere.impexp.BankStatementMatchInfo;
import org.compiere.impexp.BankStatementMatcherInterface;
import org.compiere.model.MBankStatementLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 * Bank statement matcher for use in VBankStatementMatch (post-import auto-match).
 *
 * Matching strategy for C_BankStatementLine → C_Payment:
 *   1. Amount must match exactly (TrxAmt = PayAmt, sign determines IsReceipt)
 *   2. Bank account must match
 *   3. Payment must be completed (DocStatus IN 'CO','CL') and not yet reconciled
 *   4. If EftReference is set, it is compared against CheckNo and DocumentNo
 *      as a tiebreaker (preferred match), but amount alone is sufficient
 *
 * Also implements the X_I_BankStatement variant (used during import) with the
 * same logic, so this single class can be used in both scenarios.
 *
 * Configure in ADempiere: Finance → Setup → Bank Statement Matcher
 *   Classname: org.shw.bankstatement.SHW_AmountBankMatcher
 *
 * @author Systemhaus Westfalia
 */
public class SHW_AmountBankMatcher implements BankStatementMatcherInterface {

	@Override
	public BankStatementMatchInfo findMatch(MBankStatementLine bsl,
			List<Integer> includedPayments, List<Integer> excludedPayments) {

		if (bsl == null) return new BankStatementMatchInfo();
		if (bsl.getC_Payment_ID() != 0) return new BankStatementMatchInfo(); // already matched

		BigDecimal trxAmt = bsl.getTrxAmt();
		if (trxAmt == null || trxAmt.compareTo(Env.ZERO) == 0)
			return new BankStatementMatchInfo();

		boolean isReceipt = trxAmt.compareTo(Env.ZERO) > 0;
		BigDecimal payAmt = isReceipt ? trxAmt : trxAmt.negate();
		int bankAccountId = bsl.getParent().getC_BankAccount_ID();

		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
			"SELECT p.C_Payment_ID FROM C_Payment p "
			+ "WHERE p.AD_Client_ID = ? "
			+ "AND p.PayAmt = ? "
			+ "AND p.IsReceipt = ? "
			+ "AND p.C_BankAccount_ID = ? "
			+ "AND p.DocStatus IN ('CO','CL') "
			+ "AND p.IsReconciled = 'N' "
			+ "AND NOT EXISTS (SELECT 1 FROM C_BankStatementLine l "
			+     "WHERE l.C_Payment_ID = p.C_Payment_ID "
			+     "AND l.C_BankStatementLine_ID <> ?) ");
		params.add(bsl.getAD_Client_ID());
		params.add(payAmt);
		params.add(isReceipt);
		params.add(bankAccountId);
		params.add(bsl.getC_BankStatementLine_ID());

		appendPaymentFilter(sql, params, includedPayments, excludedPayments);

		// Prefer payment whose DocumentNo or CheckNo matches the EFT reference
		String ref = bsl.getEftReference();
		if (!Util.isEmpty(ref)) {
			sql.append("ORDER BY CASE WHEN TRIM(p.DocumentNo) = ? OR TRIM(p.CheckNo) = ? "
				+ "THEN 0 ELSE 1 END, p.DateTrx ASC ");
			params.add(ref.trim());
			params.add(ref.trim());
		} else {
			sql.append("ORDER BY p.DateTrx ASC ");
		}

		int paymentId = DB.getSQLValue(bsl.get_TrxName(), sql.toString(), params);

		BankStatementMatchInfo info = new BankStatementMatchInfo();
		if (paymentId > 0)
			info.setC_Payment_ID(paymentId);
		return info;
	}

	@Override
	public BankStatementMatchInfo findMatch(X_I_BankStatement ibs,
			List<Integer> includedPayments, List<Integer> excludedPayments) {

		BankStatementMatchInfo info = new BankStatementMatchInfo();
		if (ibs == null || ibs.getC_Payment_ID() != 0) return info;

		BigDecimal trxAmt = ibs.getTrxAmt();
		if (trxAmt == null || trxAmt.compareTo(Env.ZERO) == 0) return info;

		boolean isReceipt = trxAmt.compareTo(Env.ZERO) > 0;
		BigDecimal payAmt = isReceipt ? trxAmt : trxAmt.negate();

		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
			"SELECT p.C_Payment_ID FROM C_Payment p "
			+ "WHERE p.AD_Client_ID = ? "
			+ "AND p.PayAmt = ? "
			+ "AND p.IsReceipt = ? "
			+ "AND p.C_BankAccount_ID = ? "
			+ "AND p.DocStatus IN ('CO','CL') "
			+ "AND p.IsReconciled = 'N' "
			+ "AND NOT EXISTS (SELECT 1 FROM I_BankStatement i "
			+     "WHERE i.C_Payment_ID = p.C_Payment_ID) ");
		params.add(ibs.getAD_Client_ID());
		params.add(payAmt);
		params.add(isReceipt);
		params.add(ibs.getC_BankAccount_ID());

		appendPaymentFilter(sql, params, includedPayments, excludedPayments);

		String ref = ibs.getEftReference();
		if (!Util.isEmpty(ref)) {
			sql.append("ORDER BY CASE WHEN TRIM(p.DocumentNo) = ? OR TRIM(p.CheckNo) = ? "
				+ "THEN 0 ELSE 1 END, p.DateTrx ASC ");
			params.add(ref.trim());
			params.add(ref.trim());
		} else {
			sql.append("ORDER BY p.DateTrx ASC ");
		}

		int paymentId = DB.getSQLValue(ibs.get_TrxName(), sql.toString(), params);
		if (paymentId > 0)
			info.setC_Payment_ID(paymentId);
		return info;
	}

	private void appendPaymentFilter(StringBuilder sql, List<Object> params,
			List<Integer> included, List<Integer> excluded) {
		if (included != null && !included.isEmpty())
			sql.append("AND p.C_Payment_ID IN ")
			   .append(included.toString().replace('[', '(').replace(']', ')'))
			   .append(" ");
		if (excluded != null && !excluded.isEmpty())
			sql.append("AND p.C_Payment_ID NOT IN ")
			   .append(excluded.toString().replace('[', '(').replace(']', ')'))
			   .append(" ");
	}
}

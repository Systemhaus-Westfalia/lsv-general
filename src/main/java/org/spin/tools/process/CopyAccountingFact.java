/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.										*
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net or http://www.adempiere.net/license.html         *
 *****************************************************************************/

package org.spin.tools.process;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MConversionRate;
import org.compiere.model.MConversionType;
import org.compiere.model.MCurrency;
import org.compiere.model.MFactAcct;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTable;
import org.compiere.util.DB;

/** Generated Process for (Copy Accounting Fact From Another Accounting Schema)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopyAccountingFact extends CopyAccountingFactAbstract
{
	
	boolean converBasedOnDocumentDateAcct = false;
	
	@Override
	protected void prepare()
	{
		super.prepare();
		converBasedOnDocumentDateAcct = getConversionRateId()==0;
	}

	@Override
	protected String doIt() throws Exception
	{
		if (getAcctSchemaId()==0)
			throw new AdempiereException("@Invalid@ @C_AcctSchema_ID@ @From@");
		
		if (getAcctSchemaIdTo()==0)
			throw new AdempiereException("@Invalid@ @C_AcctSchema_ID@ @To@");
		
		if (getConvertAcct()==0)
			throw new AdempiereException("@Invalid@ @Currency_Convert_Acct@");
		
		copyFactAccounting();
		
		return "@OK@";
	}
	/**
	 * Copy Accounting Fact
	 */
	@SuppressWarnings("deprecation")
	private void copyFactAccounting() {
		ArrayList<Object> paramsDelete = new ArrayList<Object>();
		ArrayList<Object> paramsInsert = new ArrayList<Object>();
		LinkedHashMap<String, String> columnsSQL = new LinkedHashMap<>(); 
		AtomicReference<String> sqlInsert = new AtomicReference<>("");
		AtomicReference<String> sqlSelect = new AtomicReference<>("");
		boolean nativeSequence = MSysConfig.getBooleanValue("SYSTEM_NATIVE_SEQUENCE", false);
		Optional<MSequence> maybeSequence = Optional.ofNullable(MSequence.get(getCtx(), MFactAcct.Table_Name, get_TrxName()));
		AtomicReference<String> sequenceId = new AtomicReference<>("0");
		
		maybeSequence.ifPresent(sequence -> sequenceId.set(((Integer)sequence.get_ID()).toString()));
		String whereClause = "C_AcctSchema_ID = ?";
		paramsInsert.add(getAcctSchemaId());
		paramsDelete.add(getAcctSchemaIdTo());
		
		if (getDateAcct()!=null) {
			whereClause+= " AND DateAcct >= ?";
			paramsInsert.add(getDateAcct());
			paramsDelete.add(getDateAcct());
		}
		
		if (getDateAcctTo()!=null) {
			whereClause+= " AND DateAcct <= ?";
			paramsInsert.add(getDateAcctTo());
			paramsDelete.add(getDateAcctTo());
		}
		
		//Create Fact Accounting Copy from Accounting Schema 
		Optional<MTable> maybeFactAcct = Optional.ofNullable(MTable.get(getCtx(), MFactAcct.Table_Name));
		maybeFactAcct.ifPresent(factAcct->{
			factAcct.getColumnsAsList()
					.forEach(column ->{
						String key = column.getColumnName();
						String value = key;
						//Change Accounting Schema by Target Accounting Schema Identifier
						if (column.getColumnName().equals(MFactAcct.COLUMNNAME_C_AcctSchema_ID))
							value = ((Integer)getAcctSchemaIdTo()).toString();
						//Change Fact Accounting Identifier by Next Sequence
						if (column.getColumnName().equals(MFactAcct.COLUMNNAME_Fact_Acct_ID)) {
							
							if (nativeSequence)
								value = "NEXTVAL('fact_acct_seq')";
							else
								value = "NEXTID(".concat(sequenceId.get()).concat(", 'N')");
						}
						//Change Credit Accounting
						if (column.getColumnName().equals(MFactAcct.COLUMNNAME_AmtAcctCr))
							value = convertedAmount(MFactAcct.COLUMNNAME_AmtAcctCr, MFactAcct.COLUMNNAME_DateAcct);
						//Change Debit Accounting
						if (column.getColumnName().equals(MFactAcct.COLUMNNAME_AmtAcctDr))
							value = convertedAmount(MFactAcct.COLUMNNAME_AmtAcctDr, MFactAcct.COLUMNNAME_DateAcct);
						
						columnsSQL.put(key, value);
					});
		});
		
		if (!columnsSQL.isEmpty()) {
			columnsSQL.entrySet()
					  .forEach(item ->{
						  if (sqlInsert.get().isEmpty()) {
							  sqlInsert.set(item.getKey());
							  sqlSelect.set(item.getValue());
						  }
						  else {
							  sqlInsert.set(sqlInsert.get().concat(", ").concat(item.getKey()));
							  sqlSelect.set(sqlSelect.get().concat(", ").concat(item.getValue()));
						  }
					  });
			
			String deleteFact = "DELETE FROM ".concat(MFactAcct.Table_Name).concat(" WHERE ").concat(whereClause);
			String insertFact = "INSERT INTO ".concat(MFactAcct.Table_Name).concat("(").concat(sqlInsert.get()).concat(")")
						.concat("SELECT ").concat(sqlSelect.get()).concat(" ")
						.concat("FROM ").concat(MFactAcct.Table_Name).concat(" ")
						.concat("WHERE ")
						.concat(whereClause);
			
			//Delete Matches Result
			int deleteRecords = DB.executeUpdate(deleteFact, paramsDelete.toArray(), false, get_TrxName());
			addLog("@Records@ @Deleted@ " + deleteRecords);
			//Insert New Values for Accounting Schema
			int insertedRecords = DB.executeUpdate(insertFact, paramsInsert.toArray(), false, get_TrxName());
			addLog("@Records@ @Inserted@ " + insertedRecords);
			
			//Create Table for Fact Accounting Difference
			columnsSQL.clear();
			sqlInsert.set("");
			sqlSelect.set("");
			paramsInsert.clear();
			
			whereClause = "fa.C_AcctSchema_ID = ?";
			paramsInsert.add(getAcctSchemaIdTo());
			
			insertFact = "CREATE TABLE Fact_Acct_Diff AS " + 
						 "SELECT AD_Table_ID,Record_ID,MIN(Fact_Acct_ID) Fact_Acct_ID,sum(AmtAcctDR- AmtAcctCr) AmtDiff " + 
						 "FROM Fact_Acct " + 
						 "WHERE C_AcctSchema_ID= ? " + 
						 "GROUP BY AD_Table_ID,Record_ID " + 
						 "HAVING SUM(AmtAcctDR- AmtAcctCr) !=0" ;
			//Delete Matches Result
			insertedRecords = DB.executeUpdate(insertFact, getAcctSchemaIdTo(), false, get_TrxName());
			addLog("@Records@ @Inserted@ @Adjustment@ " + insertedRecords);
			
			
			maybeFactAcct.ifPresent(factAcct->{
				factAcct.getColumnsAsList()
						.forEach(column ->{
							String key = column.getColumnName();
							String value = "fa.".concat(key);
							//Change Accounting Schema by Target Accounting Schema Identifier
							if (column.getColumnName().equals(MFactAcct.COLUMNNAME_C_AcctSchema_ID))
								value = ((Integer)getAcctSchemaIdTo()).toString();
							//Change Fact Accounting Identifier by Next Sequence
							if (column.getColumnName().equals(MFactAcct.COLUMNNAME_Fact_Acct_ID)) {
								
								if (nativeSequence)
									value = "NEXTVAL('fact_acct_seq')";
								else
									value = "NEXTID(".concat(sequenceId.get()).concat(", 'N')");
							}
							//Change Credit Accounting
							if (column.getColumnName().equals(MFactAcct.COLUMNNAME_AmtAcctCr))
								value = "CASE WHEN fad.AmtDiff > 0 THEN fad.AmtDiff ELSE 0 END AmtAcctCr";
							//Change Debit Accounting
							if (column.getColumnName().equals(MFactAcct.COLUMNNAME_AmtAcctDr))
								value = "CASE WHEN fad.AmtDiff < 0 THEN ABS(fad.AmtDiff) ELSE 0 END AmtAcctDr";
							//Change Credit Source
							if (column.getColumnName().equals(MFactAcct.COLUMNNAME_AmtSourceCr))
								value = "CASE WHEN fad.AmtDiff > 0 THEN fad.AmtDiff ELSE 0 END AmtSourceCr";
							//Change Debit Accounting
							if (column.getColumnName().equals(MFactAcct.COLUMNNAME_AmtSourceDr))
								value = "CASE WHEN fad.AmtDiff < 0 THEN ABS(fad.AmtDiff) ELSE 0 END AmtSourceDr";
							//Change Currency
							if (column.getColumnName().equals(MFactAcct.COLUMNNAME_C_Currency_ID)) {
								MAcctSchema targetSchema = MAcctSchema.get(getCtx(), getAcctSchemaIdTo());
								value = ((Integer)targetSchema.getC_Currency_ID()).toString();
							}
							//Change Credit Accounting
							if (column.getColumnName().equals(MFactAcct.COLUMNNAME_Account_ID))
								value = ((Integer)MAccount.get(getCtx(), getConvertAcct()).getAccount_ID()).toString();
							columnsSQL.put(key, value);
						});
			});
			
			columnsSQL.entrySet()
			  .forEach(item ->{
				  if (sqlInsert.get().isEmpty()) {
					  sqlInsert.set(item.getKey());
					  sqlSelect.set(item.getValue());
				  }
				  else {
					  sqlInsert.set(sqlInsert.get().concat(", ").concat(item.getKey()));
					  sqlSelect.set(sqlSelect.get().concat(", ").concat(item.getValue()));
				  }
			  });
			
			//Insert Difference Values for Accounting Schema
			insertFact = "INSERT INTO ".concat(MFactAcct.Table_Name).concat("(").concat(sqlInsert.get()).concat(")")
					.concat("SELECT ").concat(sqlSelect.get()).concat(" ")
					.concat("FROM ").concat(MFactAcct.Table_Name).concat(" fa ")
					.concat("INNER JOIN Fact_Acct_Diff fad ON (fa.Fact_Acct_ID = fad.Fact_Acct_ID) ")
					.concat("WHERE ")
					.concat(whereClause);
			
			insertedRecords = DB.executeUpdate(insertFact, paramsInsert.toArray(), false, get_TrxName());
			addLog("@Records@ @Inserted@ " + insertedRecords);
			
			DB.executeUpdate("DROP TABLE Fact_Acct_Diff", false, get_TrxName());
		}
	}

	/**
	 * Get Converted Amount for SQL Insert
	 * @param columnName
	 * @param columnDate
	 * @return
	 */
	private String convertedAmount(String columnName, String columnDate) {
		String columnResult = columnName;
		MAcctSchema sourceSchema = MAcctSchema.get(getCtx(), getAcctSchemaId());
		MAcctSchema targetSchema = MAcctSchema.get(getCtx(), getAcctSchemaIdTo());
		Integer conversionTypeID  = getConversionTypeId();
		Integer targetPrecision = MCurrency.getStdPrecision(getCtx(), targetSchema.getC_Currency_ID());
		if (conversionTypeID == 0)
			conversionTypeID = MConversionType.getDefault(getAD_Client_ID());
		
		if (converBasedOnDocumentDateAcct) 
			columnResult = "COALESCE(CurrencyConvert(".concat(columnName).concat(", ")
											 .concat(((Integer)sourceSchema.getC_Currency_ID()).toString()).concat(", ")
											 .concat(((Integer)targetSchema.getC_Currency_ID()).toString()).concat(", ")
											 .concat(columnDate).concat(", ")
											 .concat(conversionTypeID.toString()).concat(", ")
											 .concat(MFactAcct.COLUMNNAME_AD_Client_ID).concat(", ")
											 .concat(MFactAcct.COLUMNNAME_AD_Org_ID).concat("),0)");
		else {
			MConversionRate conversionRate = MConversionRate.get(getCtx(), getConversionRateId());
			columnResult = "ROUND(COALESCE(".concat(columnName).concat(" * ").concat(conversionRate.getMultiplyRate().toString()).concat(",0) , ")
										   .concat(targetPrecision.toString()).concat(")");
		}
		return columnResult;
	}
}
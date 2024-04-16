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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.adempiere.core.domains.models.I_AD_PrintFormat;
import org.adempiere.core.domains.models.I_AD_PrintFormatItem;
import org.adempiere.core.domains.models.I_C_Invoice;
import org.adempiere.core.domains.models.I_C_InvoiceLine;
import org.adempiere.core.domains.models.I_C_Order;
import org.adempiere.core.domains.models.I_C_PaySelectionLine;
import org.adempiere.core.domains.models.I_C_Payment;
import org.compiere.model.MColumn;
import org.compiere.model.MCurrency;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.print.MPrintFormat;
import org.compiere.print.MPrintFormatItem;
import org.compiere.util.DisplayType;

/** Generated Process for (Copy Print Format With Conversion)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class CopyPrintFormatWithConversion extends CopyPrintFormatWithConversionAbstract {
	
	/**	Counter for created	*/
	private AtomicInteger created = new AtomicInteger();
	/**	Just make a where clause for assurance that is correct entity	*/
	private final String DEFAULT_WHERE_CLAUSE = "UUID IS NOT NULL AND LENGTH(UUID) > 7";
	
	@Override
	protected String doIt() throws Exception {
		Query query = null;
		if(getRecord_ID() > 0) {
			query = new Query(getCtx(), MPrintFormat.Table_Name, I_AD_PrintFormat.COLUMNNAME_AD_PrintFormat_ID + " = ?", get_TrxName()).setParameters(getRecord_ID());
		} else {
			query = new Query(getCtx(), MPrintFormat.Table_Name, DEFAULT_WHERE_CLAUSE + " " + 
					" AND EXISTS(SELECT 1 " + 
					"    FROM AD_PrintFormatItem pfi" + 
					"    INNER JOIN AD_Column c ON(pfi.AD_Column_ID = c.AD_Column_ID) " + 
					"    WHERE EXISTS(SELECT 1 FROM AD_Table t " + 
					"    INNER JOIN AD_Column rc ON(rc.AD_Table_ID = t.AD_Table_ID) " + 
					"    WHERE length(c.ColumnName) > 3 AND substr(c.ColumnName, 1, length(c.ColumnName) - 3) = t.TableName " + 
					"    AND rc.ColumnName IN('C_Currency_ID', 'DateAcct', 'C_ConversionType_ID') " + 
					"    AND COALESCE(rc.ColumnSQL,'')='') " + 
					"    AND pfi.AD_PrintFormat_ID = AD_PrintFormat.AD_PrintFormat_ID) " + 
					"AND IsForm = 'N' ", get_TrxName());
		}
		query
			.setOnlyActiveRecords(true)
			.setClient_ID()
			.<MPrintFormat>list()
			.forEach(sourcePrintFormat -> {
				copyPrintFormat(sourcePrintFormat);
			});
		return "@Created@ " + created;
	}
	
	
	/**
	 * Copy Prints Formats of views
	 * @param sourceTable
	 * @param targetTable
	 */
	private void copyPrintFormat(MPrintFormat sourcePrintFormat) {
		List<MPrintFormatItem> sourceReferencedItems = Arrays.asList(sourcePrintFormat.getItems()).stream().filter(printFormatItem -> {
			if(printFormatItem.getAD_Column_ID() <= 0) {
				return false;
			}
			//	Validate Column
			MColumn column = MColumn.get(getCtx(), printFormatItem.getAD_Column_ID());
			MTable referencedTable = MTable.get(getCtx(), column.getColumnName().replaceAll("_ID", ""));
			if(referencedTable == null
					|| referencedTable.getAD_Table_ID() <= 0) {
				return false;
			}
			if(!referencedTable.isDocument()) {
				return false;
			}
			return referencedTable.getColumnsAsList().stream()
				.filter(columnToFind -> columnToFind.getColumnName().equals(I_C_Invoice.COLUMNNAME_C_Currency_ID) || columnToFind.getColumnName().equals(I_C_Invoice.COLUMNNAME_C_ConversionType_ID))
				.findFirst().isPresent();
		}).collect(Collectors.toList());
		//	For Payment
		Optional<MPrintFormatItem> maybeReferencedItem = sourceReferencedItems.stream().filter(printFormatItem -> MColumn.get(getCtx(), printFormatItem.getAD_Column_ID()).getColumnName().equals(I_C_Payment.COLUMNNAME_C_Payment_ID)).findFirst();
		//	For Invoice
		if(!maybeReferencedItem.isPresent()) {
			maybeReferencedItem = sourceReferencedItems.stream().filter(printFormatItem -> MColumn.get(getCtx(), printFormatItem.getAD_Column_ID()).getColumnName().equals(I_C_Invoice.COLUMNNAME_C_Invoice_ID)).findFirst();
		}
		//	For Order
		if(!maybeReferencedItem.isPresent()) {
			maybeReferencedItem = sourceReferencedItems.stream().filter(printFormatItem -> MColumn.get(getCtx(), printFormatItem.getAD_Column_ID()).getColumnName().equals(I_C_Order.COLUMNNAME_C_Order_ID)).findFirst();
		}
		//	For Any
		if(!maybeReferencedItem.isPresent()) {
			maybeReferencedItem = sourceReferencedItems.stream().findFirst();
		}
		//	Get Column Date
		if(maybeReferencedItem.isPresent()) {
			MColumn sourceReferencedColumn = MColumn.get(getCtx(), maybeReferencedItem.get().getAD_Column_ID());
			MTable referencedTable = MTable.get(getCtx(), sourceReferencedColumn.getAD_Table_ID());
			AtomicReference<MColumn> sourceDateColumn = new AtomicReference<MColumn>();
			Optional<MColumn> maybeDateColumn = referencedTable.getColumnsAsList().stream().filter(column -> column.getColumnName().equals(I_C_Invoice.COLUMNNAME_DateAcct)).findFirst();
			if(!maybeDateColumn.isPresent()) {
				maybeDateColumn = referencedTable.getColumnsAsList().stream().filter(column -> column.getAD_Reference_ID() == DisplayType.Date
						&& !column.getColumnName().equals(I_C_Invoice.COLUMNNAME_Created) 
						&& !column.getColumnName().equals(I_C_Invoice.COLUMNNAME_Updated)).findFirst();
				maybeDateColumn.ifPresent(date -> sourceDateColumn.set(date));
			} else {
				sourceDateColumn.set(maybeDateColumn.get());
			}
			//	Copy it
			if(maybeDateColumn.isPresent()) {
				MPrintFormat targetPrintFormat = new MPrintFormat(getCtx(), 0, get_TrxName());
				PO.copyValues(sourcePrintFormat, targetPrintFormat);
				targetPrintFormat.setName(sourcePrintFormat.getName() + getSuffix());
				targetPrintFormat.saveEx(get_TrxName());
				targetPrintFormat.setUUID(String.valueOf(sourcePrintFormat.getAD_PrintFormat_ID()));
				targetPrintFormat.setIsDirectLoad(true);
				targetPrintFormat.saveEx();
				Arrays.asList(sourcePrintFormat.getItems()).forEach(sourcePrintFormatItem -> {
					MPrintFormatItem targetPrintFormatItem = new MPrintFormatItem(getCtx(), 0, get_TrxName());
					PO.copyValues(sourcePrintFormatItem, targetPrintFormatItem);
					targetPrintFormatItem.setAD_PrintFormat_ID(targetPrintFormat.getAD_PrintFormat_ID());
					targetPrintFormatItem.saveEx(get_TrxName());
					targetPrintFormatItem.setUUID(String.valueOf(sourcePrintFormatItem.getAD_PrintFormatItem_ID()));
					targetPrintFormatItem.setIsDirectLoad(true);
					targetPrintFormatItem.saveEx();
					//	Copy Translation
					copyTranslation(sourcePrintFormatItem, targetPrintFormatItem);
					//	Add converted Amount
					if(sourcePrintFormatItem.getAD_Column_ID() > 0) {
						MColumn column = MColumn.get(getCtx(), sourcePrintFormatItem.getAD_Column_ID());
						if(column.getColumnName().equals(I_C_Invoice.COLUMNNAME_TotalLines)
								|| column.getColumnName().equals(I_C_Invoice.COLUMNNAME_GrandTotal)
								|| column.getColumnName().equals(I_C_InvoiceLine.COLUMNNAME_LineNetAmt)
								|| column.getColumnName().equals(I_C_Payment.COLUMNNAME_PayAmt)
								|| column.getColumnName().equals(I_C_PaySelectionLine.COLUMNNAME_OpenAmt)
								|| column.getColumnName().equals("PaidAmt")
								) {
							targetPrintFormatItem.set_ValueOfColumn("IsConvertedColumn", true);
							targetPrintFormatItem.set_ValueOfColumn(I_C_Invoice.COLUMNNAME_C_Currency_ID, getCurrencyId());
							if (getConversionTypeId() > 0)
								targetPrintFormatItem.set_ValueOfColumn(I_C_Invoice.COLUMNNAME_C_ConversionType_ID, getConversionTypeId());
							targetPrintFormatItem.set_ValueOfColumn("SourceColumnDocument_ID", sourceReferencedColumn.getAD_Column_ID());
							targetPrintFormatItem.set_ValueOfColumn("SourceColumnDate_ID", sourceDateColumn.get().getAD_Column_ID());
							MCurrency currency = MCurrency.get(getCtx(), getCurrencyId());
							targetPrintFormatItem.setPrintName(targetPrintFormatItem.getPrintName() + " (" + currency.getISO_Code() + ")");
							targetPrintFormatItem.setIsDirectLoad(true);
							targetPrintFormatItem.saveEx();
							if (targetPrintFormatItem.isCentrallyMaintained()) {
								copyTranslation(sourcePrintFormatItem, targetPrintFormatItem);
								new Query(getCtx(), I_AD_PrintFormatItem.Table_Name + "_Trl",
										I_AD_PrintFormatItem.COLUMNNAME_AD_PrintFormatItem_ID + " = ?", get_TrxName())
									.setParameters(targetPrintFormatItem.getAD_PrintFormatItem_ID())
									.<PO>list()
									.stream()
									.forEach(translation -> {
										translation.set_ValueOfColumn(I_AD_PrintFormatItem.COLUMNNAME_PrintName, translation.get_ValueAsString(I_AD_PrintFormatItem.COLUMNNAME_PrintName) + " (" + currency.getISO_Code() + ")");
										translation.saveEx();
									});
							}
						}
					}
				});
				created.incrementAndGet();
				//	Add to log
				addLog("@AD_PrintFormat_ID@ " + targetPrintFormat.getName() + " @Added@");
			}
		}
	}
	
	/**
	 * Copy translation from PO
	 * @param source
	 * @param target
	 */
	private void copyTranslation(PO source, PO target) {
		String tableName = source.get_TableName() + "_Trl";
		MTable.get(getCtx(), source.get_Table_ID()).getColumnsAsList().stream().filter(column -> column.isTranslated()).findAny().ifPresent(column -> {
			new Query(getCtx(), tableName, source.get_KeyColumns()[0] + " = ?", get_TrxName())
				.setParameters(source.get_ID())
				.<PO>list()
				.forEach(sourceTranslation -> {
					new Query(getCtx(), tableName, target.get_KeyColumns()[0] + " = ? AND AD_Language = ?", get_TrxName())
					.setParameters(target.get_ID(), sourceTranslation.get_ValueAsString("AD_Language"))
					.<PO>list()
					.forEach(targetTranslation -> {
						MTable.get(getCtx(), source.get_Table_ID()).getColumnsAsList().stream().filter(translatedColumn -> translatedColumn.isTranslated()).forEach(translatedColumn -> {
							targetTranslation.set_ValueOfColumn(translatedColumn.getColumnName(), sourceTranslation.get_Value(translatedColumn.getColumnName()));
						});
						targetTranslation.saveEx();
					});
				});
		});
	}
}
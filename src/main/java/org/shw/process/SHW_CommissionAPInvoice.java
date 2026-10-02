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

package org.shw.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.core.domains.models.I_C_BPartner;
import org.adempiere.core.domains.models.I_C_BPartner_Location;
import org.adempiere.core.domains.models.I_C_CommissionAmt;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCommission;
import org.compiere.model.MCommissionAmt;
import org.compiere.model.MCommissionRun;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MPriceList;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

/** Generated Process for (SHW_CommissionAPInvoice)
 *  @author ADempiere (generated)
 *  @version Release 3.9.4
 */
public class SHW_CommissionAPInvoice extends SHW_CommissionAPInvoiceAbstract
{
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		MCommissionRun run = new MCommissionRun(getCtx(), getRecord_ID(), get_TrxName());
		int docTypeId = getDocTypeId();

		Timestamp dateInvoice = getDateInvoiced();
		if (dateInvoice == null)
			dateInvoice = run.getDateDoc();
		final Timestamp invoiceDate = dateInvoice;

		for (int partnerId : getBPartnerIDs(run)) {
			if (partnerId <= 0)
				continue;
			Trx.run(trxName -> {
				MBPartner partner = new MBPartner(getCtx(), partnerId, trxName);
				MInvoice invoice = createInvoice(run, partner, docTypeId, invoiceDate, trxName);
				if (invoice == null)
					return;
				for (MCommissionAmt amt : getCommissionAmts(run, partnerId, trxName))
					createInvoiceLine(invoice, amt);
			});
		}
		return "@OK@";
	}

	private int[] getBPartnerIDs(MCommissionRun run) {
		String whereClause = "EXISTS (SELECT 1 FROM " + I_C_CommissionAmt.Table_Name + " a "
				+ "WHERE a." + I_C_CommissionAmt.COLUMNNAME_C_CommissionRun_ID + " = ? "
				+ "AND a." + I_C_CommissionAmt.COLUMNNAME_C_BPartner_ID + " = "
				+ I_C_BPartner.Table_Name + "." + I_C_BPartner.COLUMNNAME_C_BPartner_ID + ")";
		return new Query(getCtx(), MBPartner.Table_Name, whereClause, get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setParameters(run.getC_CommissionRun_ID())
				.setOrderBy(I_C_BPartner.COLUMNNAME_Value)
				.getIDs();
	}

	private List<MCommissionAmt> getCommissionAmts(MCommissionRun run, int partnerId, String trxName) {
		String whereClause = I_C_CommissionAmt.COLUMNNAME_C_CommissionRun_ID + " = ? AND "
				+ I_C_CommissionAmt.COLUMNNAME_C_BPartner_ID + " = ?";
		return new Query(getCtx(), I_C_CommissionAmt.Table_Name, whereClause, trxName)
				.setClient_ID()
				.setParameters(run.getC_CommissionRun_ID(), partnerId)
				.list();
	}

	private MInvoice createInvoice(MCommissionRun run, MBPartner partner, int docTypeId, Timestamp dateInvoice, String trxName) {
		MBPartnerLocation partnerLocation = getLocationBill(partner, trxName);
		if (partnerLocation == null) {
			log.log(Level.SEVERE, "@C_BPartner_Location_ID@ @NotFound@: " + partner.getName());
			addLog(0, dateInvoice, null, "@Bill_Location_ID@ @NotFound@ "
					+ run.getDocumentNo() + " " + partner.getName());
			return null;
		}

		String paymentRule = partner.getPaymentRule();
		if (paymentRule == null || paymentRule.isEmpty()) {
			log.log(Level.SEVERE, "@PaymentRule@ @NotFound@: " + partner.getName());
			paymentRule = "P";
		}

		MInvoice invoice = new MInvoice(getCtx(), 0, trxName);
		invoice.setBPartner(partner);
		invoice.setAD_Org_ID(run.getAD_Org_ID());
		invoice.setIsSOTrx(false);
		//invoice.setPaymentRule(paymentRule);
		invoice.setC_DocTypeTarget_ID(docTypeId);
		invoice.setDescription(Msg.parseTranslation(getCtx(),
				"@C_CommissionRun_ID@ " + run.getDocumentNo()));
		invoice.setDateInvoiced(dateInvoice);
		invoice.setDateAcct(dateInvoice);
		

		MPaymentTerm paymentTerm = MPaymentTerm.getPaymentTermByDefault(getCtx(), trxName);
		if (paymentTerm != null)
			invoice.setC_PaymentTerm_ID(paymentTerm.getC_PaymentTerm_ID());
		if (invoice.getM_PriceList_ID() == 0) {
			MPriceList priceList = MPriceList.getDefault(getCtx(), false);
			if (priceList != null)
				invoice.setM_PriceList_ID(priceList.getM_PriceList_ID());
		}
		invoice.setSalesRep_ID(Env.getAD_User_ID(getCtx()));
		invoice.saveEx();
		addLog(0, invoice.getDateInvoiced(), invoice.getGrandTotal(),
				"@C_Invoice_ID@ " + invoice.getDocumentNo()
				+ " @C_BPartner_ID@ @TaxId@ " + partner.getValue()
				+ " @Name@ " + partner.getName());
		return invoice;
	}

	private MInvoiceLine createInvoiceLine(MInvoice invoice, MCommissionAmt commissionAmt) {
		MInvoiceLine line = new MInvoiceLine(invoice);
		line.setC_Charge_ID(commissionAmt.getC_CommissionLine().getC_Commission().getC_Charge_ID());
		line.setQty(BigDecimal.ONE);
		line.setPrice(commissionAmt.getCommissionAmt());
		line.setTax();
		line.saveEx();
		return line;
	}

	private MBPartnerLocation getLocationBill(MBPartner partner, String trxName) {
		String whereClause = I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID + "=? AND "
				+ I_C_BPartner_Location.COLUMNNAME_IsBillTo + "=? AND "
				+ I_C_BPartner_Location.COLUMNNAME_IsActive + "=?";
		return new Query(partner.getCtx(), I_C_BPartner_Location.Table_Name, whereClause, trxName)
				.setClient_ID()
				.setParameters(partner.getC_BPartner_ID(), true, true)
				.first();
	}
}

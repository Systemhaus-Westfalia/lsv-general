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

package org.shw.lsv.einvoice.process;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.core.domains.models.I_C_BPartner;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCharge;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocation;
import org.compiere.model.MPriceList;
import org.compiere.model.MRequest;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.shw.lsv.einvoice.utils.CuerpoDocumentoItem;
import org.shw.lsv.einvoice.utils.DteRoot;
import org.shw.lsv.einvoice.utils.TributosItem;

import com.fasterxml.jackson.databind.ObjectMapper;

/** Generated Process for (ei_ReadJson)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class ei_readJson extends ei_readJsonAbstract
{
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		try {
			ObjectMapper mapper = new ObjectMapper();
			File jsonfile = new File(getFilePathOrName());
			// This maps the whole JSON to your Java objects
			DteRoot dte = mapper.readValue(jsonfile, DteRoot.class);
			MBPartner partner = getbyNIT(dte.getEmisor().getNit());
			if (partner==null) {
				partner = getbyNRC(dte.getEmisor().getNrc());
			}
			if (partner == null)
				partner = 	createBpartner(dte);
			MInvoice invoice=    createInvoice(dte, partner);
			createInvoiceLines(invoice, dte);
			// Access your data easily:
			System.out.println("Generation Code: " + dte.getIdentificacion().getCodigoGeneracion());
			System.out.println("First Item: " + dte.getCuerpoDocumento().get(0).getDescripcion());
			System.out.println("Total Gravada: " + dte.getResumen().getTotalGravada());

		} catch (Exception e) {
			e.printStackTrace();
		}
		openResult(MInvoice.Table_Name);
		return "";

	}
	
	private MInvoice createInvoice(DteRoot dteRoot, MBPartner partner)  throws ParseException {

		log.info("New Invoice for ");
		MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
		Optional<MDocType> doctypeOptional = Arrays.stream(MDocType.getOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_APInvoice))
				.sorted((docType1, docType2) -> Boolean.compare(docType2.isDefault(), docType1.isDefault()))
				.findFirst();
		doctypeOptional.ifPresent(docType -> invoice.setC_DocTypeTarget_ID(docType.getC_DocType_ID()));
		if (invoice.getC_DocTypeTarget_ID() <= 0)
			throw new AdempiereException("@C_DocType_ID@ @FillMandatory@");
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());

		MBPartnerLocation bpl = null;

		bpl = Arrays.stream(MBPartnerLocation.getForBPartner(getCtx(), partner.getC_BPartner_ID(), null, get_TrxName()))
				.findFirst()
				.orElse(null);
		if (bpl != null)
			invoice.setC_BPartner_Location_ID(bpl.getC_BPartner_Location_ID());
		String dateinvoiced  = dteRoot.getIdentificacion().getFecEmi();
		String pattern = "yyyy-MM-dd";
		Timestamp dateacct = getDate(pattern, dateinvoiced);
		invoice.setDateAcct(dateacct);
		invoice.setDateInvoiced(dateacct);
		invoice.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		invoice.setDocStatus(MInvoice.STATUS_Drafted);
		invoice.setDocAction(MInvoice.DOCACTION_Complete);
		Optional<MPriceList> maybePriceList = Optional.ofNullable(MPriceList.getDefault(getCtx(), false));
		maybePriceList.ifPresent(priceList -> {
			invoice.setM_PriceList_ID(priceList.get_ID());
			invoice.setC_Currency_ID(priceList.getC_Currency_ID());
		});
		invoice.saveEx();

		return invoice;
	}
	
	
	private MBPartner getbyNIT (String nit)
	{
		if (nit == null || nit.length() == 0)
			return null;
		final String whereClause = "TRIM(REPLACE(taxID, '-', ''))=? AND AD_Client_ID=?";
		MBPartner retValue = new Query(getCtx(), I_C_BPartner.Table_Name, whereClause, get_TrxName())
		.setParameters(nit,Env.getAD_Client_ID(getCtx()))
		.setOnlyActiveRecords(true)
		.firstOnly();
		return retValue;
	}	//	get
	

	private MBPartner getbyNRC(String nrc)
	{
		if (nrc == null || nrc.length() == 0)
			return null;
		final String whereClause = " TRIM(REPLACE(duns, '-', ''))=? AND AD_Client_ID=?";
		MBPartner retValue = new Query(getCtx(), I_C_BPartner.Table_Name, whereClause, get_TrxName())
		.setParameters(nrc,Env.getAD_Client_ID(getCtx()))
		.setOnlyActiveRecords(true)
		.firstOnly();
		return retValue;
	}	//	get
	


	private MBPartner createBpartner(DteRoot dteRoot)
	{
		MBPartner partner = new MBPartner(getCtx(), 0 , get_TrxName());
		partner.setIsVendor(true);
		partner.setTaxID(dteRoot.getEmisor().getNit());
		partner.setDUNS(dteRoot.getEmisor().getNrc());
		partner.setName(dteRoot.getEmisor().getNombre());
        partner.setC_BP_Group_ID(MBPGroup.getDefault(getCtx()).get_ID());
		partner.saveEx();
		MBPartnerLocation partnerLocation = new MBPartnerLocation(partner);
		MLocation location = new MLocation(getCtx(), 0, get_TrxName());
		location.setC_City_ID(getCityID(dteRoot));
		location.setC_Region_ID(getRegionID(dteRoot));
		location.setAddress1(dteRoot.getEmisor().getDireccion().getComplemento());
		location.saveEx();
		partnerLocation.setC_Location_ID(location.getC_Location_ID());
		partnerLocation.saveEx();
		
		return partner;
	}	//	get
	
	private int getRegionID(DteRoot root) {
		String sql = "SELECT c_Region_ID FROM c_Region WHERE value = ?";
		int regionID = DB.getSQLValueEx(get_TrxName(), sql, root.getEmisor().getDireccion().getDepartamento());
		
		return regionID;
	}
	

	
	private int getCityID(DteRoot root) {
		String sql = "SELECT c_city_ID FROM c_city WHERE value = ?";
		int cityID = DB.getSQLValueEx(get_TrxName(), sql, root.getEmisor().getDireccion().getMunicipio());
		
		return cityID;
	}

	
	private int getTaxID(String taxValue) {
		String sql = "select c_tax_ID from c_tax where e_Duties_ID in (select e_Duties_ID from e_Duties where value = ?)"
				+ "AND ad_Client_ID=? "
				+ "ORDER BY isdefault desc";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(taxValue);
		params.add(Env.getAD_Client_ID(getCtx()));
		int taxID = DB.getSQLValueEx(get_TrxName(), sql, params);		
		return taxID;
	}
	

	private Timestamp getDate(String pattern, String value) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		//	Parse
		Date date = dateFormat.parse(value);
		return new Timestamp(date.getTime());
	}
	
	private void createInvoiceLines(MInvoice invoice, DteRoot dteRoot) {
		List<CuerpoDocumentoItem> cuerDocumentoItems = dteRoot.getCuerpoDocumento();
		cuerDocumentoItems.stream().forEach(cuerpoDocumentoItem -> {
			createInvoiceLine(invoice, cuerpoDocumentoItem, dteRoot);
		});
		createINvoiceLineFovialCotrans(invoice, dteRoot );
		
	}
	private void createInvoiceLine(MInvoice invoice, CuerpoDocumentoItem cuerpoDocumentoItem,DteRoot dteRoot) {
		MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
		invoiceLine.setQty(new BigDecimal(cuerpoDocumentoItem.getCantidad()));
		invoiceLine.setDescription(cuerpoDocumentoItem.getDescripcion());
		invoiceLine.setPrice(new BigDecimal(cuerpoDocumentoItem.getPrecioUni()));
		List<String> tributos = cuerpoDocumentoItem.getTributos();
		tributos.stream().forEach(tributo ->{
			if (!tributo.equals("D1") && !tributo.equals("C8")) {
				invoiceLine.setC_Tax_ID(getTaxID(tributo));				
			}
		});
	}
	
	private void createINvoiceLineFovialCotrans(MInvoice invoice, DteRoot dteRoot ) {
		
		List<TributosItem> tributosItems =  dteRoot.getResumen().getTributos();
		String sql = "SELECT C_Charge_ID from C_Charge where ad_Client_ID=? AND description = ?";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(invoice.getAD_Client_ID());
		tributosItems.stream().forEach(tributoItem ->{
			if (tributoItem.getCodigo().equals("C8")){
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				invoiceLine.setQty(Env.ONE);
				invoiceLine.setPrice(tributoItem.getValor());
				invoiceLine.setDescription(tributoItem.getDescripcion());
				params.add(tributoItem.getCodigo());
				int chargeID = DB.getSQLValueEx(get_TrxName(), sql , params);
				invoiceLine.setC_Charge_ID(chargeID);
				invoiceLine.saveEx();				
			}
			if (tributoItem.getCodigo().equals("D1")){
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				invoiceLine.setQty(Env.ONE);
				invoiceLine.setPrice(tributoItem.getValor());
				invoiceLine.setDescription(tributoItem.getDescripcion());
				params.add(tributoItem.getCodigo());
				int chargeID = DB.getSQLValueEx(get_TrxName(), sql , params);
				invoiceLine.setC_Charge_ID(chargeID);
				invoiceLine.saveEx();				
			}
		});

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
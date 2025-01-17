package org.shw.lsv.einvoice.utils;
/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/


import org.adempiere.core.domains.models.X_E_DocType;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;
import org.compiere.util.CLogger;
import org.eevolution.services.dsl.ProcessBuilder;
import org.shw.lsv.einvoice.process.EI_C_Invoice_Print;
import org.shw.lsv.einvoice.process.EInvoiceGenerateAndPost;

/**
 *	Validator for Customization Central America
 *	
 *  @author Susanne Calderon Systemhaus Westfalia
 */
public class SV_EI_Validator implements ModelValidator

{
	public SV_EI_Validator ()
	{
		super ();
	}
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(SV_EI_Validator.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	
	/**
	 *	Initialize Validation
	 *	@param engine validation engine 
	 *	@param client client
	 */
	public void initialize (ModelValidationEngine engine, MClient client)
	{
		//client = null for global validator
		if (client != null) {	
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  {
			log.info("Initializing global validator: "+this.toString());
		}


		engine.addDocValidate(MInvoice.Table_Name, this);
		
		
		//	Documents to be monitored
	//	engine.addDocValidate(MInvoice.Table_Name, this);

	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	Called after PO.beforeSave/PO.beforeDelete
     *	when you called addModelChange for the table
     *	@param po persistent object
     *	@param type TYPE_
     *	@return error message or null
     *	@exception Exception if the recipient wishes the change to be not accept.
     */
	public String modelChange (PO po, int type) throws Exception
	{
		String error = "";
		if (type == ModelValidator.TYPE_BEFORE_NEW  ){
			if (po.get_TableName().equals(MInvoice.Table_Name)) {
				po.set_ValueOfColumn("ei_codigoGeneracion", null);
				po.set_ValueOfColumn("ei_dateReceived", null);
				po.set_ValueOfColumn("ei_Error_Extern", null);
				po.set_ValueOfColumn("ei_numeroControl", null);
				po.set_ValueOfColumn("ei_output", null);
				po.set_ValueOfColumn("ei_selloRecibido", null);
				po.set_ValueOfColumn("ei_Status_Extern", null);

			}			
		}
		return error;}

	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("CAValidator");
		return sb.toString ();
	}	//	toString
	

/**
 *	User Login.
	 *	Called when preferences are set
	 *	@param AD_Org_ID org
	 *	@param AD_Role_ID role
	 *	@param AD_User_ID user
	 *	@return error message or null
	 */
	public String login (int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		log.info("AD_User_ID=" + AD_User_ID);
		 
		
		return null;
	}	//	login

	
	/**
	 *	Get Client to be monitored
	 *	@return AD_Client_ID client
	 */
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}	//	getAD_Client_ID
	

	public String docValidate (PO po, int timing)
	{
		if (po instanceof MInvoice && timing ==TIMING_AFTER_POST) {

			MInvoice invoice = (MInvoice)po;
            if (invoice.get_ValueAsString("ei_selloRecibido") != null && invoice.get_ValueAsString("ei_selloRecibido").length() >0 && invoice.isPrinted())
            	return "";
            if (invoice.get_ValueAsString("ei_selloRecibido") == null || invoice.get_ValueAsString("ei_selloRecibido").length() ==0) {
            	
            
			MDocType docType = (MDocType)invoice.getC_DocType();
			Boolean isreversal = invoice.getReversal_ID() > 0 && invoice.getReversal_ID()<invoice.getC_DocType_ID();
			int eDocTypeId = docType.get_ValueAsInt(X_E_DocType.COLUMNNAME_E_DocType_ID);
			if (eDocTypeId<=0 || isreversal)
				return "";
			
			//	Execute ProcessProcessInfo processInfo = ProcessBuilder.create(getCtx())
			ProcessInfo processInfo = ProcessBuilder.create(invoice.getCtx())
			.process(EInvoiceGenerateAndPost.getProcessId())
			.withParameter(MInvoice.COLUMNNAME_AD_Client_ID, invoice.getAD_Client_ID())
			.withParameter(MInvoice.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID())
			.execute(invoice.get_TrxName());
	if (processInfo.isError())
		throw new AdempiereException(processInfo.getSummary());	
            }
	if (invoice.isPrinted())
		return "";
	ProcessInfo processInfo = ProcessBuilder.create(invoice.getCtx())
			.process(EI_C_Invoice_Print.getProcessId())
			.withParameter("R_MAILTEXT_ID", 1000107)
			.withRecordId(MInvoice.Table_ID, invoice.getC_Invoice_ID())
			.execute(invoice.get_TrxName());
	if (processInfo.isError())
		throw new AdempiereException(processInfo.getSummary());	
    
		return "";			
		}
		
		return "";
	}
		

	
}	//	Validator
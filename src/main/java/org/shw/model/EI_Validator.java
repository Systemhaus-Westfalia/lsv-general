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
package org.shw.model;


import java.sql.Timestamp;

import org.adempiere.core.domains.models.X_AD_Client;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MProcess;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.eevolution.services.dsl.ProcessBuilder;
import org.shw.lsv.einvoice.process.EInvoiceGenerateAndPost;

/**
 *	Validator Example Implementation
 *	
 *	@author Jorg Janke
 *	@version $Id: MyValidator.java,v 1.2 2006/07/30 00:51:57 jjanke Exp $
 */
public class EI_Validator implements ModelValidator
{
	/**
	 *	Constructor.
	 */
	public EI_Validator ()
	{
		super ();
	}	//	MyValidator

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(SHWValidator_BASICSNEW.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	/** User	*/
	private int		m_AD_User_ID = -1;
	/** Role	*/
	private int		m_AD_Role_ID = -1;
	

	/**
	 *	Initialize Validation
	 *	@param engine validation engine 
	 *	@param client client
	 */
	public void initialize (ModelValidationEngine engine, MClient client)
	{
		//client = null for global validatorALG
		if (client != null) {	
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  {
			log.info("Initializing global validator: "+this.toString());
		}

		//	We want to be informed when C_Order is created/changed
		engine.addDocValidate(MInvoice.Table_Name, this);
		engine.addDocValidate(MOrder.Table_Name, this);
		engine.addModelChange(MInvoice.Table_Name, this);
		engine.addModelChange(MBPartner.Table_Name, this);

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
		String error = null;		

		if (po.get_TableName().equals(MInvoice.Table_Name))
		{
			if (type == ModelValidator.TYPE_BEFORE_NEW) {
				MInvoice invoice = (MInvoice)po;
				if (invoice.getReversal_ID()>0) {
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
			}
		}
		if (po instanceof MBPartner) {
			
		}
		



		return error;
	}	//	modelChange

	/**
	 *	Validate Document.
	 *	Called as first step of DocAction.prepareIt 
	 *	when you called addDocValidate for the table.
	 *	Note that totals, etc. may not be correct.
	 *	@param po persistent object
	 *	@param timing see TIMING_ constants
	 *	@return error message or null
	 */
	public String docValidate (PO po, int timing)
	{
		String error = null;		
		System.out.println(" ei_validator docvalidate" );
		if (po instanceof MInvoice)
		{
			if (timing == TIMING_BEFORE_POST)
			{		
				MInvoice invoice = (MInvoice)po; 				
				System.out.println(" ei_validator docvalidate_beforePost evaluate evaluate for "  + invoice.getDocumentNo());	
				Timestamp startdate = (Timestamp)MClient.get(po.getCtx()).get_Value("ei_startdate");
				if (startdate == null || startdate.after(invoice.getDateAcct()))
					return "";
				// if (invoice.get_ValueAsString("ei_Status_Extern").equals("Firmado"))
				//	  return "";
				// Trx transaction = Trx.get(invoice.get_TrxName(), false);
				// transaction.commit();
				MDocType docType = (MDocType)invoice.getC_DocType();				  

				if (docType.get_ValueAsInt("E_DocType_ID") <= 0) return ""; 

				System.out.println(" ei_validator docvalidate_beforePost start publish for "  + invoice.getDocumentNo());					 
				ProcessInfo  processInfo =
						ProcessBuilder.create(invoice.getCtx())
						.process(EInvoiceGenerateAndPost.getProcessId())
						.withTitle(EInvoiceGenerateAndPost.getProcessName())
						.withRecordId(MInvoice.Table_ID , invoice.getC_Invoice_ID())
						.withParameter(MProcess.COLUMNNAME_IsDirectPrint, true)
						.withParameter(MInvoice.COLUMNNAME_AD_Client_ID, invoice.getAD_Client_ID())
						.withParameter(MInvoice.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID())
						.withoutTransactionClose()
						.execute(invoice.get_TrxName()); 
				if (processInfo.isError())
					throw new AdempiereException(processInfo.getSummary());

				return "";
			}			
		}
		
		if (po instanceof MOrder) {
			error = "";
			MOrder order = (MOrder)po;
			if (!order.isSOTrx())
				return "";             
			MDocType invoicedocType = (MDocType)order.getC_DocTypeTarget().getC_DocTypeInvoice();
			if (invoicedocType.get_ValueAsInt("E_DocType_ID") <=0)
				return "";
			MClient client = new MClient(order.getCtx(), order.get_TrxName());
			Timestamp startdate = (Timestamp)client.get_Value("ei_Startdate");
			if (startdate == null)
				return "";
			if (order.getDateAcct().before(startdate))
				return "";
			MBPartner partner = (MBPartner)order.getC_BPartner();
			Boolean eiCorrect =  (partner.get_ValueAsInt( "E_Activity_ID") >0 &&
					partner.get_ValueAsInt( "E_BPType_ID") > 0 &&
					partner.get_ValueAsInt( "E_Recipient_Identification_ID") > 0);
			if (!eiCorrect)
				return "Socio de Negocio: Configuracion factura electronica ";
			Boolean addressCorrect = (order.getBill_Location().getC_Location().getC_City_ID()>0
					&& order.getBill_Location().getC_Location().getC_Region_ID()>0
					&& order.getBill_Location().getC_Location().getAddress1()!="NN");
			if (!addressCorrect)
				return "Socio de Negocio: Dirección";
			return error;
		}





		return error;
	}	//	docValidate

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
		m_AD_User_ID = AD_User_ID;
		m_AD_Role_ID = AD_Role_ID;
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


	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("SHWValidator");
		sb.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * Sample Validator Before Save Properties - to set mandatory properties on users
	 * avoid users changing properties
	 */
	public void beforeSaveProperties() {
		// not for SuperUser or role SysAdmin
		if (   m_AD_User_ID == 0  // System
				|| m_AD_User_ID == 100   // SuperUser
				|| m_AD_Role_ID == 0  // System Administrator
				|| m_AD_Role_ID == 1000000)  // ECO Admin
			return;

		log.info("Setting default Properties");


		// Example - if you don't want user to select auto commit property
		// Ini.setProperty(Ini.P_A_COMMIT, false);

		// Example - if you don't want user to select auto login
		// Ini.setProperty(Ini.P_A_LOGIN, false);

		// Example - if you don't want user to select store password
		// Ini.setProperty(Ini.P_STORE_PWD, false);

		// Example - if you want your user inherit ALWAYS the show accounting from role
		// Ini.setProperty(Ini.P_SHOW_ACCT, role.isShowAcct());

		// Example - if you want to avoid your user from changing the working date
		/*
		Timestamp DEFAULT_TODAY =	new Timestamp(System.currentTimeMillis());
		//  Date (remove seconds)
		DEFAULT_TODAY.setHours(0);
		DEFAULT_TODAY.setMinutes(0);
		DEFAULT_TODAY.setSeconds(0);
		DEFAULT_TODAY.setNanos(0);
		Ini.setProperty(Ini.P_TODAY, DEFAULT_TODAY.toString());
		Env.setContext(Env.getCtx(), "#Date", DEFAULT_TODAY);
		 */

	}	// beforeSaveProperties




	//-----------------------------------------------------------------------------------	

	




	








}	//	MyValidator

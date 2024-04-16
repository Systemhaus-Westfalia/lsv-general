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

import java.util.ArrayList;
import java.util.List;

import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Validator Example Implementation
 *	
 *	@author Jorg Janke
 *	@version $Id: MyValidator.java,v 1.2 2006/07/30 00:51:57 jjanke Exp $
 */
public class SHWValidator_BASICSNEW implements ModelValidator
{
	/**
	 *	Constructor.
	 */
	public SHWValidator_BASICSNEW ()
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
		engine.addDocValidate(MPayment.Table_Name, this);
		engine.addDocValidate(MAllocationHdr.Table_Name, this);


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

		if (po.get_TableName().equals(MAllocationHdr.Table_Name))
		{
			if (timing == TIMING_BEFORE_POST)
			{
				//error = updatePostingAllocation(po);
				error = AfterPost_CorrectGL_Category(po);
			}
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

	




	







	public String AfterPost_CorrectGL_Category(PO po)	
	{
		MAllocationHdr ah = (MAllocationHdr)po;

		Doc doc = ah.getDoc();

		ArrayList<Fact> facts = doc.getFacts();
		// one fact per acctschema
		String description = "";
		for (Fact fact:facts)
		{
			for (FactLine fLine:fact.getLines())
			{
				description = "";
				Boolean isPayment = false;
				MAllocationLine alo = new MAllocationLine(po.getCtx(), fLine.getLine_ID(), po.get_TrxName());
				if (alo.getC_Payment_ID() !=0)
				{
					fLine.setGL_Category_ID(alo.getC_Payment().getC_DocType().getGL_Category_ID());

					description = "Pago: " + alo.getC_Payment().getDocumentNo();
					continue;
				}
				else if (alo.getC_CashLine_ID()!=0)
				{
					if (alo.getC_CashLine().getC_Invoice_ID() != 0)
						isPayment = alo.getC_Invoice().getC_DocType().getDocBaseType().equals(MDocType.DOCBASETYPE_APPayment)?
								true:false;
					else 
					{
						isPayment = alo.getAmount().compareTo(Env.ZERO)>=0?true:false;
					}

				}
				MDocType dt = null;
				if (isPayment)
					dt = new Query(po.getCtx(), MDocType.Table_Name, "Docbasetype = 'APP'", null)
					.first();
				else
					dt = new Query(po.getCtx(), MDocType.Table_Name, "Docbasetype = 'ARR'", null)
					.first();
				fLine.setGL_Category_ID(dt.getGL_Category_ID());
				if (alo.getC_Invoice_ID() != 0)
					description = description + " Factura: " + alo.getC_Invoice().getDocumentNo();
				else if (alo.getC_Charge_ID() != 0)
					description = description + " Cargo: " + alo.getC_Charge().getName();
			}
		}	
		return "";
	}

	public String PaymentCompleteNote(PO A_PO, int A_Type)
	{
		MPayment pay = (MPayment)A_PO;
		if (pay.getC_Charge_ID() != 0)
			return "";
		String note = "";
		if (pay.getC_Invoice_ID() !=0)
		{
			note = "Factura # " + pay.getC_Invoice().getDocumentNo() + " de " + pay.getC_BPartner().getName();
			pay.set_ValueOfColumn("Note", note);
		}
		String whereClause = "C_Payment_ID=?";
		List< MAllocationLine> alos = new Query(A_PO.getCtx(), MAllocationLine.Table_Name, whereClause, A_PO.get_TrxName())
				.list();
		for (MAllocationLine alo:alos)
		{
			note = "Facturas # ";
			if (alo.getC_Invoice_ID() !=0)
				note = note + ", "  + alo.getC_Invoice().getDocumentNo() + " de " + alo.getC_BPartner().getName();
		}
		pay.set_ValueOfColumn("Note", note);
		return "";
	
	}
	
	public String InvoiceUpdateActivity(PO A_PO)
	{
		MAllocationHdr ah = (MAllocationHdr)A_PO;
		for (MAllocationLine alo:ah.getLines(true))
		{
			if (alo.getC_Invoice_ID() ==0 || alo.getC_Invoice()== null)
				return "";
			if (alo.getC_Invoice().getDateAcct() == ah.getDateAcct())
			{
				MInvoice invoice = (MInvoice)alo.getC_Invoice();
				int no = DB.executeUpdateEx("Delete from fact_Acct where ad_table_ID = 318 and record_ID = " + invoice.getC_Invoice_ID(), alo.get_TrxName());
				invoice.setPosted(false);
				invoice.setC_Activity_ID(1000145);
				invoice.saveEx();
			}
		}
		return "";
	}
	

}	//	MyValidator

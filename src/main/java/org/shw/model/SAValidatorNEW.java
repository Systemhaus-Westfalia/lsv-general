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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Level;

import org.adempiere.core.domains.models.I_C_BPartner;
import org.adempiere.core.domains.models.X_C_Order;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBPartner;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MCharge;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPricing;
import org.compiere.model.MProject;
import org.compiere.model.MProjectType;
import org.compiere.model.MRequest;
import org.compiere.model.MRole;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.spin.queue.model.MADQueue;
import org.spin.queue.notification.model.MADNotificationQueue;

/**
 * Validator Example Implementation
 * 
 * @author Susanne Calderon aktualisiert für 3.9.4
 */
public class SAValidatorNEW implements ModelValidator {
	/**
	 * Constructor.
	 */
	public SAValidatorNEW() {
		super();
	} // MyValidator

	/** Logger */
	private CLogger log = CLogger.getCLogger(getClass());
	/** Client */
	private int m_AD_Client_ID = -1;
	/** User */
	private int m_AD_User_ID = -1;
	/** Role */
	private int m_AD_Role_ID = -1;
	Function<Integer, String> converter = (num) -> Integer.toString(num);
	Runnable r1 = () -> System.out.println("VVV");

	/**
	 * Initialize Validation
	 * 
	 * @param engine validation engine
	 * @param client client
	 */
	public void initialize(ModelValidationEngine engine, MClient client) {
		// client = null for global validator
		if (client != null) {
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		} else {
			log.info("Initializing global validator: " + this.toString());
		}

		// We want to be informed when C_Order is created/changed
		engine.addModelChange(MProject.Table_Name, this);
		engine.addModelChange(MAllocationHdr.Table_Name, this);
		engine.addModelChange(MOrder.Table_Name, this);
		engine.addModelChange(MOrderLine.Table_Name, this);
		engine.addModelChange(MPaymentAllocate.Table_Name, this);
		engine.addModelChange(MRequest.Table_Name, this);
		engine.addModelChange(MProject.Table_Name, this);

		// We want to validate Order before preparing
		engine.addDocValidate(MOrder.Table_Name, this);
		engine.addDocValidate(MPayment.Table_Name, this);
		engine.addDocValidate(MAllocationHdr.Table_Name, this);
		engine.addDocValidate(MInvoice.Table_Name, this);
		engine.addDocValidate(MBankStatement.Table_Name, this);

	} // initialize

	/**
	 * Model Change of a monitored Table. Called after PO.beforeSave/PO.beforeDelete
	 * when you called addModelChange for the table
	 * 
	 * @param po   persistent object
	 * @param type TYPE_
	 * @return error message or null * @exception Exception if the recipient wishes
	 *         the change to be not accept.
	 */
	public String modelChange(PO po, int type) throws Exception {
		String error = null;
		
		if (po.get_TableName().equals(MProject.Table_Name)) {
			Boolean changed = po.is_ValueChanged("UpdateQtyCount");
			if (changed && type == TYPE_BEFORE_CHANGE) {
				MProject project = (MProject)po;
				updateProject(project);
			}
		}
		if (po.get_TableName().equals(MPayment.Table_Name)) {

			
		}
		
		if (po.get_TableName().equals(MOrderLine.Table_Name)) {
			if (type == TYPE_BEFORE_NEW || type==TYPE_BEFORE_CHANGE)
				error = orderLine_setLineNetAmt(po);
			if (type == TYPE_BEFORE_CHANGE)
				;
			if (type == TYPE_AFTER_NEW)
				error = OrderLineUpdateControlAmt(po);
			if (type == TYPE_AFTER_CHANGE) {
				error = OrderLineUpdateControlAmt(po);
				error = updateIsInvoiced(po);
			}
			if (type == TYPE_AFTER_DELETE)
				;
			if (type == TYPE_BEFORE_DELETE) {
				error = updateRequest(po);
			}
				
				;
			if (type == TYPE_DELETE)
				error = OLineBeforeDelete(po);
		}

		if (po.get_TableName().equals(MPaymentAllocate.Table_Name)) {
			
		}

		if (po.get_TableName().equals(MAllocationHdr.Table_Name)) {
			
		}

		
		if (po.get_TableName().equals(MProject.Table_Name)) {

			if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE) {
				projectBeforeNew(po);
			}
			;
			if (type == TYPE_AFTER_NEW) {
				error = UpdateDocumentation(po);
				}
			if (type == TYPE_AFTER_CHANGE) {
				error = UpdateDocumentation(po);
			}
			if (type == TYPE_AFTER_DELETE)
				;
			if (type == TYPE_AFTER_DELETE)
				;
			if (type == TYPE_DELETE)
				;
		}

		if (po.get_TableName().equals(MOrder.Table_Name)) {
		}

		if (po.get_TableName().equals(MRequest.Table_Name)) {
			
			if (type == TYPE_BEFORE_NEW)
				//error = requestUpdateBpartner(po)
				;
			
		}
		if (po.get_TableName().equals(MADNotificationQueue.Table_Name)) {
			if (type == TYPE_BEFORE_NEW) {
				MADNotificationQueue notificationQueue = (MADNotificationQueue)po;
				
				MADQueue queue = (MADQueue)notificationQueue.getAD_Queue();
				if (queue.getAD_Table_ID()==318) {
					MInvoice invoice = new MInvoice(notificationQueue.getCtx(), queue.getRecord_ID(), notificationQueue.get_TrxName());
					notificationQueue.setDescription(notificationQueue.getDescription() + " No de Documento " + invoice.get_ValueAsString("ei_numeroControl"));
					
				}
			}
			
		}

		return error;

	} // modelChange

	/**
	 * Validate Document. Called as first step of DocAction.prepareIt when you
	 * called addDocValidate for the table. Note that totals, etc. may not be
	 * correct.
	 * 
	 * @param po     persistent object
	 * @param timing see TIMING_ constants
	 * @return error message or null
	 */
	public String docValidate(PO po, int timing) {
		String error = null;

		if (po.get_TableName().equals(MInvoice.Table_Name)) {
			if (timing == TIMING_BEFORE_PREPARE) {
				// Pricing Modul
				error = InvoiceCalculateContract(po);
			}
			if (timing == TIMING_AFTER_PREPARE)
				;
			if (timing == TIMING_BEFORE_COMPLETE)
				;
			if (timing == TIMING_AFTER_COMPLETE) {
			    error = ProjectInvoiceComplete(po);
				error = OrderLineControlInvoiced(po);
				error = InvoiceLineCreateOdV(po);
				error = NdCUpdateControlAmt(po);
				error = assignPrepayment(po);
			}
			if (timing == TIMING_BEFORE_VOID)
				;
			if (timing == TIMING_AFTER_VOID)
				// Script DocNoInvoiceAfterVoid DocNo fuer annulierte Rechnungen
				;
			if (timing == TIMING_BEFORE_POST)
				;
			if (timing == TIMING_AFTER_POST)
				;
		}

		if (po.get_TableName().equals(MPayment.Table_Name)) {
			if (timing == TIMING_BEFORE_PREPARE)
				;
			if (timing == TIMING_AFTER_PREPARE) {
				error = PaymentCreateOrderLine(po);
			}
			if (timing == TIMING_BEFORE_COMPLETE)
				;
			if (timing == TIMING_AFTER_COMPLETE) {
				error = PaymentCreateBankstatementLine(po);
				// error = ProjectPagoCuentaAjena(po);
				// error = ProjectPaymentAllocationComplete(po);
				// Script DocNoPaymentAfterComplete Anulation
			}
			if (timing == TIMING_BEFORE_VOID)
				;
			if (timing == TIMING_AFTER_VOID || timing == TIMING_AFTER_REVERSECORRECT
					|| timing == TIMING_AFTER_REACTIVATE) {
				error = DeletePayment(po);
			}
			if (timing == TIMING_BEFORE_POST)
				;
			if (timing == TIMING_AFTER_POST)
				;
		}
		if (po.get_TableName().equals(MOrder.Table_Name)) {
			if (timing == TIMING_BEFORE_PREPARE) {
			}
			if (timing == TIMING_AFTER_PREPARE) {
				;
			}
			if (timing == TIMING_BEFORE_COMPLETE)
				;
			if (timing == TIMING_AFTER_COMPLETE) {
				error = ProjectOrderComplete(po);
				error = purchaseInvoiceCreateOrderLine(po);
			}
			if (timing == TIMING_AFTER_VOID || timing == TIMING_AFTER_REVERSECORRECT
					|| timing == TIMING_AFTER_REACTIVATE) {
				error = ProjectOrderVoid(po);
				// 
			}
			if (timing == TIMING_BEFORE_POST)
				;
			if (timing == TIMING_AFTER_POST)
				;
		}

		if (po.get_TableName().equals(MAllocationHdr.Table_Name)) {
			if (timing == TIMING_BEFORE_PREPARE) {
			}
			if (timing == TIMING_AFTER_PREPARE)
				;
			if (timing == TIMING_BEFORE_COMPLETE)
				;
			if (timing == TIMING_AFTER_COMPLETE) {
				CreatePaymentFromAllocationReembolso(po);
			}
			if (timing == TIMING_BEFORE_VOID)
				;
			if (timing == TIMING_AFTER_VOID || timing == TIMING_AFTER_REVERSECORRECT
					|| timing == TIMING_AFTER_REACTIVATE)
				if (timing == TIMING_BEFORE_POST)
					AfterPost_CorrectGL_Category(po);
			if (timing == TIMING_AFTER_POST)
				;
		}

		if (po.get_TableName().equals(MBankStatement.Table_Name)) {
		}


		return error;
	} // docValidate

	/**
	 * User Login. Called when preferences are set
	 * 
	 * @param AD_Org_ID  org
	 * @param AD_Role_ID role
	 * @param AD_User_ID user
	 * @return error message or null
	 */
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {

		log.info("AD_User_ID=" + AD_User_ID);
		m_AD_User_ID = AD_User_ID;
		m_AD_Role_ID = AD_Role_ID;
		MUser user = new MUser(Env.getCtx(), AD_User_ID, null);
		if (user.getC_BPartner_ID() != 0) {
			MBPartner bpartner = (MBPartner) user.getC_BPartner();
			Env.setContext(Env.getCtx(), "#User1_ID", bpartner.get_ValueAsInt("User1_ID"));
		}

		return null;
	} // login

	/**
	 * Get Client to be monitored
	 * 
	 * @return AD_Client_ID client
	 */
	public int getAD_Client_ID() {
		return m_AD_Client_ID;
	} // getAD_Client_ID

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer("SAValidatorNew");
		sb.append("]");
		return sb.toString();
	} // toString

	/**
	 * Sample Validator Before Save Properties - to set mandatory properties on
	 * users avoid users changing properties
	 */
	public void beforeSaveProperties() {
		// not for SuperUser or role SysAdmin
		if (m_AD_User_ID == 0 // System
				|| m_AD_User_ID == 100 // SuperUser
				|| m_AD_Role_ID == 0 // System Administrator
				|| m_AD_Role_ID == 1000000) // ECO Admin
			return;

		log.info("Setting default Properties");

		MRole role = MRole.get(Env.getCtx(), m_AD_Role_ID);
		if (!role.get_ValueAsBoolean("isshowAdvancedTab"))
			Ini.setProperty(Ini.P_SHOW_ADVANCED, false);
		// Example - if you don't want user to select auto commit property
		// Ini.setProperty(Ini.P_A_COMMIT, false);

		// Example - if you don't want user to select auto login
		// Ini.setProperty(Ini.P_A_LOGIN, false);

		// Example - if you don't want user to select store password
		// Ini.setProperty(Ini.P_STORE_PWD, false);

		// Example - if you want your user inherit ALWAYS the show accounting from role
		Ini.setProperty(Ini.P_SHOW_ACCT, role.isShowAcct());
		Ini.setProperty(Ini.P_TRACELEVEL, true);

		// Example - if you want to avoid your user from changing the working date
		/*
		 * Timestamp DEFAULT_TODAY = new Timestamp(System.currentTimeMillis()); // Date
		 * (remove seconds) DEFAULT_TODAY.setHours(0); DEFAULT_TODAY.setMinutes(0);
		 * DEFAULT_TODAY.setSeconds(0); DEFAULT_TODAY.setNanos(0);
		 * Ini.setProperty(Ini.P_TODAY, DEFAULT_TODAY.toString());
		 * Env.setContext(Env.getCtx(), "#Date", DEFAULT_TODAY);
		 */
	} // beforeSaveProperties

	


	String projectBeforeNew(PO A_PO) {
		MProject project = (MProject) A_PO;
		Boolean dateChanged = A_PO.is_ValueChanged("xav_DateLoadingAtOrigin")
				|| A_PO.is_ValueChanged("xav_DateShipAtOrigin") || A_PO.is_ValueChanged("xav_DestPuertoLlegadaFecha")
				|| A_PO.is_ValueChanged("xav_DestBodegaLlegadaFecha");
		if (dateChanged && project.isSummary()) {
			String whereClause = "C_Project_Parent_ID=?";
			List<MProject> projectList = new Query(project.getCtx(), MProject.Table_Name, whereClause,
					A_PO.get_TrxName()).setParameters(project.getC_Project_ID()).list();
			for (MProject projectSon : projectList) {
				projectSon.set_ValueOfColumn("xav_DateLoadingAtOrigin", project.get_Value("xav_DateLoadingAtOrigin"));
				projectSon.set_ValueOfColumn("xav_DateShipAtOrigin", project.get_Value("xav_DateShipAtOrigin"));
				projectSon.set_ValueOfColumn("xav_DestPuertoLlegadaFecha",
						project.get_Value("xav_DestPuertoLlegadaFecha"));
				projectSon.set_ValueOfColumn("xav_DestBodegaLlegadaFecha",
						project.get_Value("xav_DestBodegaLlegadaFecha"));
				projectSon.saveEx();
			}
		}

		if (A_PO.is_ValueChanged("xav_aduanaregistrofecha") && A_PO.get_Value("xav_aduanaregistrofecha") != null) {
			A_PO.set_ValueOfColumn("SHW_ProjectStatus", "20");
		}

		if (A_PO.is_ValueChanged("datepagodm") && A_PO.get_Value("datepagodm") != null) {
			A_PO.set_ValueOfColumn("SHW_ProjectStatus", "30");
		}

		if (A_PO.is_ValueChanged("shw_dateselectividad") && A_PO.get_Value("shw_dateselectividad") != null) {
			A_PO.set_ValueOfColumn("SHW_ProjectStatus", "40");
		}

		if (A_PO.is_ValueChanged("datedelivered") && A_PO.get_Value("datedelivered") != null) {
			A_PO.set_ValueOfColumn("SHW_ProjectStatus", "50");
		}

		if (A_PO.is_ValueChanged(MProject.COLUMNNAME_C_ProjectType_ID)) {
			int projecttype_ID = A_PO.get_ValueAsInt(MProject.COLUMNNAME_C_ProjectType_ID);
			if (projecttype_ID == 0)
				return "";

			String neueDocNo = "";
			String neuerName = "";
			String prefix = "";
			String suffix = "";
			String zahelerString = "";

			MProjectType projecttype = new MProjectType(A_PO.getCtx(), projecttype_ID, A_PO.get_TrxName());
			if (projecttype.getProjectCategory().equals("M") || projecttype.getProjectCategory().equals("T"))
				A_PO.set_ValueOfColumn("isshipped", false);
			prefix = projecttype.get_ValueAsString("Prefix");
			if (prefix == null)
				prefix = "";

			int zaehlerInt = projecttype.get_ValueAsInt("CurrentNext");
			zahelerString = String.format("%07d", zaehlerInt);

			suffix = projecttype.get_ValueAsString("Suffix");
			if (suffix == null)
				suffix = "";

			neueDocNo = prefix + zahelerString + suffix;
			if (A_PO.get_ValueAsBoolean("isSummary")) {
				A_PO.set_ValueOfColumn(MProject.COLUMNNAME_Value, neueDocNo);
				int lg_route_ID = A_PO.get_ValueAsInt("LG_Route_ID");
				if (lg_route_ID != 0) {
					neueDocNo = DB.getSQLValueStringEx(null, "select name from lg_Route where LG_Route_ID =?",
							lg_route_ID) + "-" + neueDocNo;
				}
				A_PO.set_ValueOfColumn(MProject.COLUMNNAME_Name, neueDocNo);
				zaehlerInt = zaehlerInt + 1;
				projecttype.set_ValueOfColumn("CurrentNext", zaehlerInt);
				projecttype.saveEx();
				return "";
			}

			I_C_BPartner bpartner = project.getC_BPartner();
			String suffix2 = bpartner.getName2() == null ? bpartner.getName() : bpartner.getName2();

			// if(suffix2!=null)
			neuerName = suffix2 + "-" + neueDocNo;

			A_PO.set_ValueOfColumn(MProject.COLUMNNAME_Value, neueDocNo);
			A_PO.set_ValueOfColumn(MProject.COLUMNNAME_Name, neuerName);

			zaehlerInt = zaehlerInt + 1;
			projecttype.set_ValueOfColumn("CurrentNext", zaehlerInt);
			projecttype.saveEx();
		}
		return "";
	}

	
	

	Boolean OrdersetBPartner(MOrder order, int C_BPartner_ID, int C_PaymentTerm_ID) {
		if (C_BPartner_ID == 0)
			return false;
		String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
				+ " COALESCE(p.M_PriceList_ID,g.M_PriceList_ID) AS M_PriceList_ID, p.PaymentRule,p.POReference,"
				+ " p.SO_Description,p.IsDiscountPrinted,"
				+ " p.InvoiceRule,p.DeliveryRule,p.FreightCostRule,DeliveryViaRule,"
				+ " p.SO_CreditLimit, p.SO_CreditLimit-p.SO_CreditUsed AS CreditAvailable,"
				+ " lship.C_BPartner_Location_ID,c.AD_User_ID,"
				+ " COALESCE(p.PO_PriceList_ID,g.PO_PriceList_ID) AS PO_PriceList_ID, p.PaymentRulePO,p.PO_PaymentTerm_ID,"
				+ " lbill.C_BPartner_Location_ID AS Bill_Location_ID, p.SOCreditStatus, " + " p.SalesRep_ID "
				+ "FROM C_BPartner p" + " INNER JOIN C_BP_Group g ON (p.C_BP_Group_ID=g.C_BP_Group_ID)"
				+ " LEFT OUTER JOIN C_BPartner_Location lbill ON (p.C_BPartner_ID=lbill.C_BPartner_ID AND lbill.IsBillTo='Y' AND lbill.IsActive='Y')"
				+ " LEFT OUTER JOIN C_BPartner_Location lship ON (p.C_BPartner_ID=lship.C_BPartner_ID AND lship.IsShipTo='Y' AND lship.IsActive='Y')"
				+ " LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) "
				+ "WHERE p.C_BPartner_ID=? AND p.IsActive='Y'"; // #1

		boolean IsSOTrx = true;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_BPartner_ID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// Sales Rep - If BP has a default SalesRep then default it
				Integer salesRep = rs.getInt("SalesRep_ID");
				if (IsSOTrx && salesRep != 0) {
					order.setSalesRep_ID(salesRep);
				}

				// PriceList (indirect: IsTaxIncluded & Currency)
				Integer ii = new Integer(rs.getInt(IsSOTrx ? "M_PriceList_ID" : "PO_PriceList_ID"));
				if (!rs.wasNull())
					order.setM_PriceList_ID(ii);
				else { // get default PriceList
					int i = Env.getContextAsInt(order.getCtx(), "#M_PriceList_ID");
					if (i != 0)
						order.setM_PriceList_ID(i);
				}

				// Bill-To
				order.setBill_BPartner_ID(C_BPartner_ID);
				int bill_Location_ID = rs.getInt("Bill_Location_ID");
				order.setBill_Location_ID(bill_Location_ID);
				// Ship-To Location
				int shipTo_ID = rs.getInt("C_BPartner_Location_ID");
				order.setC_BPartner_Location_ID(shipTo_ID);

				// Contact - overwritten by InfoBP selection
				int contID = rs.getInt("AD_User_ID");
				order.setAD_User_ID(contID);
				order.setBill_User_ID(contID);

				order.setInvoiceRule(X_C_Order.INVOICERULE_AfterDelivery);
				order.setDeliveryRule(X_C_Order.DELIVERYRULE_Availability);
				order.setPaymentRule(X_C_Order.PAYMENTRULE_OnCredit);
				if (C_PaymentTerm_ID != 0)
					order.setC_PaymentTerm_ID(C_PaymentTerm_ID);
				else {
					C_PaymentTerm_ID = new Query(order.getCtx(), MPaymentTerm.Table_Name, "isdefault =?",
							order.get_TrxName()).setClient_ID().setOnlyActiveRecords(true).setParameters(true)
									.firstId();
					order.setC_PaymentTerm_ID(C_PaymentTerm_ID);
				}
				order.setInvoiceRule(MOrder.INVOICERULE_Immediate);
				order.setDeliveryRule(MOrder.DELIVERYRULE_Availability);
				order.setFreightCostRule(MOrder.FREIGHTCOSTRULE_FreightIncluded);
				order.setDeliveryViaRule(MOrder.DELIVERYVIARULE_Pickup);

			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, sql, e);
			return false;
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return true;
	}

	private String DeletePayment(PO A_PO) {

		int c_payment_ID = A_PO.get_ID();

		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(c_payment_ID);
		List<MOrderLine> oLines = new Query(A_PO.getCtx(), MOrderLine.Table_Name, "c_payment_ID=?", A_PO.get_TrxName())
				.setParameters(parameters).list();
		for (MOrderLine oLine : oLines) {
			if (oLine.getParent().getDocStatus().equals(MPayment.DOCSTATUS_Completed)
					|| oLine.getParent().getDocStatus().equals(MOrder.DOCSTATUS_Closed))
				return "No es posible anular o borrar un pago a cuenta ajena que se encuentra en una orden de venta completada";
			if (oLine.getParent().getDocStatus().equals(MPayment.DOCSTATUS_Reversed)
					|| oLine.getParent().getDocStatus().equals(MPayment.DOCSTATUS_Voided)
			// || oLine.getParent().getDocStatus().equals(MOrder.DOCSTATUS_InProgress)
			)
				return "";
			if (oLine.get_ValueAsInt(MPayment.COLUMNNAME_C_Payment_ID) > 0) {
				ArrayList<Object> deleteParameters = new ArrayList<Object>();
				deleteParameters.add(oLine.getC_OrderLine_ID());
				String sqlDelete = "Update r_request set c_orderline_ID = null where c_Orderline_ID=?";
				DB.executeUpdateEx(sqlDelete, deleteParameters.toArray(), A_PO.get_TrxName());
				oLine.deleteEx(true);
				A_PO.set_ValueOfColumn("isInvoiced", false);
				A_PO.saveEx();
			}
		}

		return "";
	}

	
	private String UpdateDocumentation(PO A_PO) {
		if (A_PO.is_ValueChanged("DocumentoDeTransporte") || A_PO.is_ValueChanged("CodigoDeDeclaracion")
				|| A_PO.is_ValueChanged("ReferenciaDeDeclaracion") || A_PO.is_ValueChanged("Provider")
				|| A_PO.is_ValueChanged("ProviderPO")) {
			String whereClause = "c_Project_ID=" + A_PO.get_ID();

			List<MOrder> orders = new Query(A_PO.getCtx(), MOrder.Table_Name, whereClause, A_PO.get_TrxName()).list();
			for (MOrder order : orders) {
				order.set_ValueOfColumn("DocumentoDeTransporte", A_PO.get_Value("DocumentoDeTransporte"));
				order.set_ValueOfColumn("CodigoDeDeclaracion", A_PO.get_Value("CodigoDeDeclaracion"));
				order.set_ValueOfColumn("ReferenciaDeDeclaracion", A_PO.get_Value("ReferenciaDeDeclaracion"));
				order.set_ValueOfColumn("Provider", A_PO.get_Value("Provider"));
				order.set_ValueOfColumn("ProviderPO", A_PO.get_Value("ProviderPO"));
				order.saveEx();
			}

			List<MInvoice> invoices = new Query(A_PO.getCtx(), MInvoice.Table_Name, whereClause, A_PO.get_TrxName())
					.list();
			for (MInvoice invoice : invoices) {
				invoice.set_ValueOfColumn("DocumentoDeTransporte", A_PO.get_Value("DocumentoDeTransporte"));
				invoice.set_ValueOfColumn("CodigoDeDeclaracion", A_PO.get_Value("CodigoDeDeclaracion"));
				invoice.set_ValueOfColumn("ReferenciaDeDeclaracion", A_PO.get_Value("ReferenciaDeDeclaracion"));
				invoice.set_ValueOfColumn("Provider", A_PO.get_Value("Provider"));
				invoice.set_ValueOfColumn("ProviderPO", A_PO.get_Value("ProviderPO"));
				invoice.saveEx();
			}
		}
		return "";
	}

	private String ProjectInvoiceComplete(PO A_PO) {
		List<Integer> ProjectIds = new ArrayList<Integer>();
		MInvoice invoice = (MInvoice)A_PO;
		if (!invoice.isSOTrx()  || invoice.getC_Project_ID() == 0)
			return"";
		Boolean oneProjectInvoice = true;
		int C_Project_ID = invoice.getC_Project_ID();
		for(MInvoiceLine ivl: invoice.getLines()){
			if(C_Project_ID!=ivl.getC_Project_ID()){
				invoice.set_ValueOfColumn("oneProjectInvoice", false);
				oneProjectInvoice=false;
				break;
			}
		}
		if(oneProjectInvoice)
			invoice.setDescription("");
		else{
			invoice.setC_Order_ID(0);
			invoice.setC_Project_ID(0);
		}
		invoice.saveEx();
		int LGRouteID = 0;
		if (invoice.getC_Order_ID()>0) {
			MOrder order = (MOrder)invoice.getC_Order();
			LGRouteID = order.get_ValueAsInt("LG_Route_ID");
	        if (LGRouteID != 0)     
	           invoice.set_ValueOfColumn("LG_Route_ID", LGRouteID);      

	        invoice.set_ValueOfColumn("Provider", order.get_Value("Provider"));
	        invoice.set_ValueOfColumn("ProviderPO", order.get_Value("ProviderPO"));
	        invoice.set_ValueOfColumn("DocumentoDeTransporte", order.get_Value("DocumentoDeTransporte"));
	        invoice.set_ValueOfColumn("CodigoDeDeclaracion", order.get_Value("CodigoDeDeclaracion"));
	        invoice.set_ValueOfColumn("ReferenciaDeDeclaracion", order.get_Value("ReferenciaDeDeclaracion"));
		}
		invoice.saveEx();
		for (MInvoiceLine invoiceLine : invoice.getLines()) {
			if (LGRouteID>0)
			invoiceLine.set_ValueOfColumn("LG_Route_ID", LGRouteID);
			invoiceLine.saveEx();
			int c_project_ID = invoiceLine.getC_Project_ID();
			if (c_project_ID == 0)
				c_project_ID = invoiceLine.getC_Invoice().getC_Project_ID();
			if (c_project_ID != 0 && !ProjectIds.contains(invoiceLine.getC_Project_ID()))
				ProjectIds.add(c_project_ID);
		}
		for (int c_project_ID : ProjectIds) {
			MProject project = new MProject(A_PO.getCtx(), c_project_ID, A_PO.get_TrxName());
			//updateProject(project);
			int UpdateQtyCount = project.get_ValueAsInt("UpdateQtyCount") + 1;
			project.set_ValueOfColumn("UpdateQtyCount", UpdateQtyCount);
			project.saveEx();
		}
		return "";
	}
	
	private String PaymentCreateBankstatementLine (PO A_PO) {
		MPayment payment = (MPayment)A_PO;
		String error = "";
		if (payment.getC_POS_ID() <= 0 && payment.getC_BankAccount().getC_Bank().getBankType().equals(  MBank.BANKTYPE_CashJournal)){
			addPayment(payment);
		}
		return error;
	}

	private String ProjectOrderComplete(PO A_PO) {
		List<Integer> ProjectIds = new ArrayList<Integer>();
		MOrder order = (MOrder) A_PO;
       
		
		if (!order.getC_DocType().getDocSubTypeSO().equals("SO"))
			return "";
		for (MOrderLine ol : order.getLines()) {
			int c_project_ID = ol.getC_Project_ID();
			if (c_project_ID == 0)
				c_project_ID = ol.getC_Order().getC_Project_ID();
			if (c_project_ID != 0 && !ProjectIds.contains(ol.getC_Project_ID()))
				ProjectIds.add(c_project_ID);
		}

		for (int c_project_ID : ProjectIds) {
			MProject project = new MProject(A_PO.getCtx(), c_project_ID, A_PO.get_TrxName());
			updateProject(project);
			//int UpdateQtyCount = project.get_ValueAsInt("UpdateQtyCount") + 1;
			//project.set_ValueOfColumn("UpdateQtyCount", UpdateQtyCount);
			//project.saveEx();
		}
		 for (MOrderLine oLine: order.getLines())
	        {
	            if (oLine.get_ValueAsInt("C_Payment_ID") == 0)
	                return "";
	            if (oLine.getC_Charge_ID() == 0)
	                return "";
	            if (!(oLine.getC_Charge().getC_ChargeType().getName().contains("CTAJ")))
	                return "";
	            MPayment pay = new MPayment(A_PO.getCtx(), oLine.get_ValueAsInt("C_Payment_ID"), A_PO.get_TrxName());
	            pay.set_ValueOfColumn("ControlAmt", oLine.getLineNetAmt());
	            pay.saveEx();                    
	        }
		return "";
	}

	private String ProjectOrderVoid(PO A_PO) {
		int C_Project_ID = A_PO.get_ValueAsInt(MProject.COLUMNNAME_C_Project_ID);
		if (C_Project_ID <= 0)
			return "";
		MProject project = new MProject(A_PO.getCtx(), C_Project_ID, A_PO.get_TrxName());
		MOrder order = (MOrder) A_PO;
		if (order.isSOTrx())
			project.setPlannedAmt(project.getPlannedAmt().subtract(order.getGrandTotal()));
		else
			project.setCommittedAmt(project.getCommittedAmt().subtract(order.getGrandTotal()));
		project.saveEx();
		return "";
	}



	public String AfterPost_CorrectGL_Category(PO po) {
		MAllocationHdr ah = (MAllocationHdr) po;

		Doc doc = ah.getDoc();

		ArrayList<Fact> facts = doc.getFacts();
		// one fact per acctschema
		String description = "";
		for (Fact fact : facts) {
			for (FactLine fLine : fact.getLines()) {
				description = "";
				Boolean isPayment = false;
				MAllocationLine alo = new MAllocationLine(po.getCtx(), fLine.getLine_ID(), po.get_TrxName());
				if (alo.getC_Payment_ID() != 0) {
					fLine.setGL_Category_ID(alo.getC_Payment().getC_DocType().getGL_Category_ID());

					description = "Pago: " + alo.getC_Payment().getDocumentNo();
					continue;
				} else if (alo.getC_CashLine_ID() != 0) {
					if (alo.getC_CashLine().getC_Invoice_ID() != 0)
						isPayment = alo.getC_Invoice().getC_DocType().getDocBaseType()
								.equals(MDocType.DOCBASETYPE_APPayment) ? true : false;
					else {
						isPayment = alo.getAmount().compareTo(Env.ZERO) >= 0 ? true : false;
					}

				}
				MDocType dt = null;
				if (isPayment)
					dt = new Query(po.getCtx(), MDocType.Table_Name, "Docbasetype = 'APP'", null).first();
				else
					dt = new Query(po.getCtx(), MDocType.Table_Name, "Docbasetype = 'ARR'", null).first();
				fLine.setGL_Category_ID(dt.getGL_Category_ID());
				if (alo.getC_Invoice_ID() != 0)
					description = description + " Factura: " + alo.getC_Invoice().getDocumentNo();
				else if (alo.getC_Charge_ID() != 0)
					description = description + " Cargo: " + alo.getC_Charge().getName();
			}
		}
		return "";

	}

	private String OrderLineControlInvoiced(PO A_PO) {
		MInvoice inv = (MInvoice) A_PO;
		if (!inv.isSOTrx())
			return "";
		if (inv.getC_Order_ID() != 0
				&& inv.getC_Order().getC_DocType().getDocSubTypeSO().equals(MDocType.DOCSUBTYPESO_OnCreditOrder))
			return "";
		String whereClause = "c_order_ID in (select c_order_ID from c_orderline where "
				+ " c_orderline_ID in (select c_orderline_ID from c_invoiceline where c_invoice_ID =?))";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(A_PO.get_ID());
		List<MOrder> orders = new Query(A_PO.getCtx(), MOrder.Table_Name, whereClause, A_PO.get_TrxName())
				.setParameters(params.toArray()).list();
		Boolean isinvoiced = true;
		for (MOrder order : orders) {
			for (MOrderLine oLine : order.getLines()) {
				if (oLine.getQtyInvoiced().compareTo(oLine.getQtyOrdered()) < 0) {
					isinvoiced = false;
					break;
				}
			}
			order.setIsInvoiced(isinvoiced);
			order.saveEx();
			int r_status = 0;
			Boolean processed = false;

			
			whereClause = "c_order_ID=? and r_requesttype_ID in (1000002,1000004)";
			MRequest req = new Query(inv.getCtx(), MRequest.Table_Name, whereClause, inv.get_TrxName())
					.setParameters(order.getC_Order_ID()).first();
			if (req == null)
				return "";
			if (r_status == 0)
				return "";
			// for (MRequest req:reqs)
			// {
			String sqlupdate = "update r_request set r_status_ID=?, processed =? where r_request_ID=?";
			params.clear();
			params.add(r_status);
			params.add(processed);
			params.add(req.getR_Request_ID());
			int no = DB.executeUpdateEx(sqlupdate, params.toArray(), inv.get_TrxName());
			return "";
		}

		return "";
	}

	private String InvoiceCalculateContract(PO A_PO) {
		MInvoice inv = (MInvoice) A_PO;
		if (inv.getReversal_ID() > 0)
			return "";
		Calendar invoice1 = TimeUtil.getToday();
		Calendar invoice2 = TimeUtil.getToday();
		if (inv.getReversal_ID() != 0 || !inv.isSOTrx())
			return "";
		for (MInvoiceLine ivlLine : inv.getLines()) {
			if (ivlLine.getC_Charge_ID() > 0)
				continue;
			String whereClause = "isvalid = 'Y' and (c_bpartner_ID = ? or c_bpartner_ID is null) and LG_RateType = 'G' and m_pricelist_ID = ? "
					+ " and  exists (select 1 from lg_productpricerateline where m_product_id = ? and isactive = 'Y') ";
			ArrayList<Object> param1 = new ArrayList<Object>();
			param1.add(inv.getC_BPartner_ID());
			param1.add(inv.getM_PriceList_ID());
			param1.add(ivlLine.getM_Product_ID());
			MLGProductPriceRate pprl = new Query(A_PO.getCtx(), X_LG_ProductPriceRate.Table_Name, whereClause,
					A_PO.get_TrxName()).setOnlyActiveRecords(true).setParameters(param1).first();
			if (pprl == null)
				continue;
			String whereClauseProducts = "(";
			for (MLGProductPriceRateLine ppl : pprl.getLines()) {
				whereClauseProducts = whereClauseProducts + ppl.getM_Product_ID() + ",";
			}
			whereClauseProducts = whereClauseProducts.substring(0, whereClauseProducts.length() - 1) + ")";
			String sqlSales = "select sum(qtyinvoiced) from c_invoiceline ivl "
					+ " inner join c_invoice i on ivl.c_invoice_ID = i.c_invoice_ID" + " where m_product_ID in "
					+ whereClauseProducts + " and c_bpartner_ID =?" + " and (i.docstatus in ('CO','CL')"
					+ " and i.dateinvoiced between ? and ?)"
					+ " or (ivl.c_invoice_ID=? and ivl.line < ? and m_product_ID in " + whereClauseProducts + ")";
			;
			if (pprl.get_ValueAsString("InvoiceFrequency") != null) {
				ArrayList<Object> params = new ArrayList<Object>();
				String frequencytype = pprl.get_ValueAsString("InvoiceFrequency");
				if (frequencytype.equals("D")) {
					params.add(ivlLine.getC_Invoice().getC_BPartner_ID());
					params.add(ivlLine.getC_Invoice().getDateInvoiced());
					params.add(ivlLine.getC_Invoice().getDateInvoiced());
					params.add(inv.getC_Invoice_ID());
					params.add(ivlLine.getLine());
					invoice1.setTime(ivlLine.getC_Invoice().getDateInvoiced());
					invoice2.setTime(ivlLine.getC_Invoice().getDateInvoiced());
				} else if (frequencytype.equals("M")) {
					Timestamp orderDate = ivlLine.getC_Invoice().getDateInvoiced();
					invoice1 = TimeUtil.getToday();
					invoice1.setTime(orderDate);
					invoice1.set(Calendar.DAY_OF_MONTH, 1);
					invoice2 = TimeUtil.getToday();
					invoice2.setTime(orderDate);
					invoice2.set(Calendar.DAY_OF_MONTH, invoice1.getActualMaximum(Calendar.DAY_OF_MONTH));
					params.add(ivlLine.getC_Invoice().getC_BPartner_ID());
					params.add(new Timestamp(invoice1.getTimeInMillis()));
					params.add(new Timestamp(invoice2.getTimeInMillis()));
					params.add(inv.getC_Invoice_ID());
					params.add(ivlLine.getLine());
				}
				BigDecimal qtyinvoiced = DB.getSQLValueBD(A_PO.get_TrxName(), sqlSales, params);
				if (qtyinvoiced == null)
					qtyinvoiced = Env.ZERO;
				qtyinvoiced = qtyinvoiced.add(ivlLine.getQtyInvoiced());
				String whereClause_LPR = " m_product_ID=? and lg_productpricerate_ID=? and ?> breakvalue";
				ArrayList<Object> param2 = new ArrayList<Object>();
				param2.add(ivlLine.getM_Product_ID());
				param2.add(pprl.getLG_ProductPriceRate_ID());
				param2.add(qtyinvoiced);
				MLGProductPriceRateLine plr = new Query(A_PO.getCtx(), MLGProductPriceRateLine.Table_Name,
						whereClause_LPR, A_PO.get_TrxName()).setParameters(param2)
								.setOrderBy("ORDER BY BreakValue DESC").first();
				/*
				 * BigDecimal qtyToInvoice = Env.ZERO; if
				 * (plr.getBreakValue().compareTo(Env.ZERO) == 0) qtyToInvoice =
				 * ivlLine.getQtyEntered(); BigDecimal qtyleft =
				 * (plr.getBreakValue().subtract(qtyinvoiced)); if
				 * (qtyleft.compareTo(Env.ZERO)<=0) qtyleft= Env.ZERO; qtyToInvoice =
				 * ivlLine.getQtyInvoiced().subtract(qtyleft); if
				 * (qtyToInvoice.compareTo(Env.ZERO) == 1)
				 */
				{
					// ivlLine.setPriceActual(plr.getPriceStd());
					ivlLine.setPriceActual(plr.getPriceStd());
					ivlLine.setPriceEntered(plr.getPriceStd());
				} /*
					 * else ivlLine.setPriceActual(Env.ZERO);
					 */
				ivlLine.saveEx();
			}
		}
		return "";
	}
	
	private String InvoiceSplit(PO po) {
		return "";
	}

	private String PaymentCreateOrderLine(PO A_PO) {
		MPayment pay = (MPayment) A_PO;
		MDocType docType = (MDocType)pay.getC_DocType();
		Boolean iscuentaajena = docType.get_ValueAsBoolean("iscuentaajena");
		if (!iscuentaajena)
			return "";

		if (pay.getC_Charge_ID() == 0) {
			CreateCTAJPaymentAllocs(pay);
			return "";
		}
		if (pay.get_ValueAsInt("R_Request_ID") == 0)
			return "";
		// List<MRequest> reqs = new Query(A_PO.getCtx(), MRequest.Table_Name,
		// "c_payment_ID=?", A_PO.get_TrxName())
		// .setParameters(pay.getC_Payment_ID())
		// .list();
		// if (reqs == null || reqs.isEmpty())
		// return "";

		// for (MRequest req:reqs)
		// {
		MRequest req = new MRequest(pay.getCtx(), pay.get_ValueAsInt("R_Request_ID"), pay.get_TrxName());
		if (req.getC_Order_ID() <= 0)
			return "";
		if (req.getR_Status().isClosed())
			return "";
		// if (pay.getC_Charge_ID()!= 0)
		String result = CreateCTAJPayment(req, pay);
		if (result != "")
			return result;
		
		return "";
	}

	private String OrderLineUpdateControlAmt(PO A_PO) {
		MOrderLine oLine = (MOrderLine) A_PO;
		// for (MOrderLine oLine: order.getLines())
		{
			if (A_PO.is_ValueChanged("LineNetAmt"))
				return "";
			if (oLine.get_ValueAsInt("C_Payment_ID") == 0 && oLine.get_ValueAsInt("C_PaymentAllocate_ID") == 0)
				return "";
			if (oLine.getC_Charge_ID() == 0)
				return "";
			if (!(oLine.getC_Charge().getC_ChargeType_ID() == 1000002
					|| oLine.getC_Charge().getC_ChargeType_ID() == 1000003))
				return "";
			if (oLine.get_ValueAsInt("C_Payment_ID") != 0 && oLine.get_ValueAsInt("C_PaymentAllocate_ID") == 0) {
				MPayment pay = new MPayment(A_PO.getCtx(), oLine.get_ValueAsInt("C_Payment_ID"), A_PO.get_TrxName());
				pay.set_ValueOfColumn("ControlAmt", oLine.getLineNetAmt());
				pay.saveEx();
			}
			if (oLine.get_ValueAsInt("C_PaymentAllocate_ID") != 0) {
				MPaymentAllocate alloc = new MPaymentAllocate(A_PO.getCtx(),
						oLine.get_ValueAsInt("C_PaymentAllocate_ID"), A_PO.get_TrxName());
				alloc.set_ValueOfColumn("ControlAmt", oLine.getLineNetAmt());
				alloc.saveEx();
			}
		}
		return "";
	}

	private String NdCUpdateControlAmt(PO A_PO) {
		MInvoice ndN = (MInvoice) A_PO;
		for (MInvoiceLine iLine : ndN.getLines()) {
			if (iLine.get_ValueAsInt("C_Payment_ID") == 0 && iLine.get_ValueAsInt("C_PaymentAllocate_ID") == 0)
				return "";
			if (iLine.get_ValueAsInt("C_Payment_ID") != 0 && iLine.get_ValueAsInt("C_PaymentAllocate_ID") == 0) {
				MPayment pay = new MPayment(A_PO.getCtx(), iLine.get_ValueAsInt("C_Payment_ID"), A_PO.get_TrxName());
				BigDecimal ctajAmt = (BigDecimal) pay.get_Value("ControlAmt");
				ctajAmt = ctajAmt.add(iLine.getLineNetAmt());
				pay.set_ValueOfColumn("ControlAmt", ctajAmt);
				pay.saveEx();
			}
			if (iLine.get_ValueAsInt("C_PaymentAllocate_ID") != 0) {
				MPaymentAllocate alloc = new MPaymentAllocate(A_PO.getCtx(),
						iLine.get_ValueAsInt("C_PaymentAllocate_ID"), A_PO.get_TrxName());
				BigDecimal ctajAmt = (BigDecimal) alloc.get_Value("ControlAmt");
				ctajAmt = ctajAmt.add(iLine.getLineNetAmt());
				alloc.set_ValueOfColumn("ControlAmt", ctajAmt);
				alloc.saveEx();
			}
		}
		return "";
	}

	//private String distributeGenerarInvoice(MLGProductPriceRateLine pprl, MInvoiceLine ivl, Timestamp date1,
	//		Timestamp date2) {
		/*
		 * String whereClause =
		 * "exists (select 1 from c_invoiceline where M_product_ID =?) and dateinvoiced between ? and ? "
		 * +
		 * " and c_bpartner_ID =? and issotrx = 'Y' and docstatus in ('DR','IP','CO', 'CL')"
		 * ; ArrayList<Object> params = new ArrayList<Object>();
		 * params.add(pprl.getLG_ProductPriceRate().getM_Product_ID());
		 * params.add(date1); params.add(date2);
		 * params.add(ivl.getC_Invoice().getC_BPartner_ID()); MInvoice invoice = new
		 * Query(ivl.getCtx(), MInvoice.Table_Name, whereClause, ivl.get_TrxName())
		 * .setParameters(params) .first(); if (invoice == null) return""; BigDecimal
		 * calcIncome = (BigDecimal)pprl.get_Value("calculatedIncome"); whereClause =
		 * "c_invoice_ID=?"; MJournal journal = new Query(ivl.getCtx(),
		 * MJournal.Table_Name, whereClause, ivl.get_TrxName())
		 * .setParameters(invoice.getC_Invoice_ID()) .first(); if (journal == null)
		 * MJournal journal = new MJournal(ivl.getCtx(), 0, ivl.get_TrxName()); for
		 * (MJournalLine jl:journal.getLines(true)) { if
		 * (jl.get_ValueAsInt("C_InvoiceLine_ID") == ivl.getC_InvoiceLine_ID()) return
		 * ""; } MAcctSchema[] accts = MAcctSchema.getClientAcctSchema(ivl.getCtx(),
		 * ivl.getAD_Client_ID()); for (MAcctSchema as:accts){ int glcat_ID =
		 * MGLCategory.getDefault(ivl.getCtx(),
		 * MGLCategory.CATEGORYTYPE_Manual).getGL_Category_ID(); int c_doctype_ID = new
		 * Query(ivl.getCtx(), MDocType.Table_Name , "gl_category_ID=?",
		 * ivl.get_TrxName()) .setParameters(glcat_ID) .setOnlyActiveRecords(true)
		 * .firstId(); journal.setC_Currency_ID(100);
		 * journal.setClientOrg(invoice.getAD_Client_ID(), invoice.getAD_Org_ID());
		 * journal.setC_AcctSchema_ID(as.get_ID()); journal.setC_ConversionType_ID(114);
		 * journal.setDescription(""); journal.setGL_Category_ID(glcat_ID);
		 * journal.setC_DocType_ID(c_doctype_ID); journal.setPostingType("A");
		 * journal.setDocumentNo(DB.getDocumentNo(ivl.getAD_Client_ID(),
		 * MJournal.Table_Name, ivl.get_TrxName()));
		 * journal.setDescription(invoice.getDocumentInfo()); journal.saveEx();
		 * //NeuBuchung mit Projekt MJournalLine jLine = new MJournalLine(journal);
		 * jLine.setM_Product_ID(ivl.getM_Product_ID());
		 * jLine.setC_Project_ID(ivl.getC_Project_ID());
		 * jLine.setC_BPartner_ID(ivl.getC_Invoice().getC_BPartner_ID());
		 * jLine.setC_Activity_ID(ivl.getC_Invoice().getC_Activity_ID());
		 * jLine.setC_Campaign_ID(ivl.getC_Invoice().getC_Campaign_ID()); ProductCost pc
		 * = new ProductCost (ivl.getCtx(),ivl.getM_Product_ID() , 0,ivl.get_TrxName());
		 * MAccount revenue = pc.getAccount(ProductCost.ACCTTYPE_P_Revenue, as);
		 * jLine.setAccount_ID(revenue.getAccount_ID());
		 * jLine.setAmtSourceCr(calcIncome); jLine.set_ValueOfColumn("C_InvoiceLine_ID",
		 * ivl.getC_InvoiceLine_ID()); jLine.saveEx(); //Rueckbuchung
		 * 
		 * jLine = new MJournalLine(journal);
		 * jLine.setM_Product_ID(pprl.getLG_ProductPriceRate().getM_Product_ID());
		 * jLine.setC_BPartner_ID(ivl.getC_Invoice().getC_BPartner_ID());
		 * jLine.setC_Activity_ID(ivl.getC_Invoice().getC_Activity_ID());
		 * jLine.setC_Campaign_ID(ivl.getC_Invoice().getC_Campaign_ID()); pc = new
		 * ProductCost (ivl.getCtx(),pprl.getM_Product_ID() , 0,ivl.get_TrxName());
		 * jLine.setAccount_ID(pc.getAccount(ProductCost.ACCTTYPE_P_Revenue,
		 * as).getAccount_ID()); jLine.setAmtSourceDr(calcIncome);
		 * jLine.set_ValueOfColumn("C_InvoiceLine_ID", ivl.getC_InvoiceLine_ID());
		 * jLine.saveEx(); } journal.processIt(MJournal.DOCACTION_Complete);
		 * journal.setDocAction(MJournal.DOCACTION_Close); journal.saveEx();
		 * 
		 * 
		 */
		//return "";
		//}

	private String OLineBeforeDelete(PO A_PO) {
		MOrderLine oLine = (MOrderLine) A_PO;
		if (oLine.getC_Charge_ID() == 0)
			return "";
		if (!(oLine.getC_Charge().getC_ChargeType_ID() == 1000002
				|| oLine.getC_Charge().getC_ChargeType_ID() == 1000003))
			return "";
		if (oLine.get_ValueAsInt("C_Payment_ID") == 0 && oLine.get_ValueAsInt("C_PaymentAllocate_ID") == 0)
			return "";

		if (oLine.get_ValueAsInt("C_Payment_ID") != 0 && oLine.get_ValueAsInt("C_PaymentAllocate_ID") == 0) {
			MPayment pay = new MPayment(A_PO.getCtx(), oLine.get_ValueAsInt("C_Payment_ID"), A_PO.get_TrxName());
			pay.set_ValueOfColumn("ControlAmt", Env.ZERO);
			pay.saveEx();
		}

		if (oLine.get_ValueAsInt("C_PaymentAllocate_ID") != 0) {
			MPaymentAllocate alloc = new MPaymentAllocate(A_PO.getCtx(), oLine.get_ValueAsInt("C_PaymentAllocate_ID"),
					A_PO.get_TrxName());
			alloc.set_ValueOfColumn("ControlAmt", Env.ZERO);
			alloc.saveEx();
		}
		return "";

	}

	private String CreateCTAJPayment(MRequest req, MPayment pay) {
		MOrder order = (MOrder) req.getC_Order();
		if (req.getC_Order_ID() <= 0)
			return "la solicitud " + req.getDocumentNo() + "no tiene referencia a niguna orden";
		if (req.get_ValueAsInt("C_OrderLine_ID") != 0) {
			MOrderLine oLine = new MOrderLine(pay.getCtx(), req.get_ValueAsInt("C_OrderLine_ID"), pay.get_TrxName());
			oLine.set_ValueOfColumn(MPayment.COLUMNNAME_C_Payment_ID, pay.getC_Payment_ID());
			oLine.saveEx();
			return "";
		}
		BigDecimal p_DistributionAmt = pay.getPayAmt();
		BigDecimal distributionAmtConverted = MConversionRate.convert (order.getCtx(),
				p_DistributionAmt, pay.getC_Currency_ID(), order.getC_Currency_ID(),
				order.getDateAcct(), order.getC_ConversionType_ID(), order.getAD_Client_ID(), order.getAD_Org_ID());
		MOrderLine oLine = new Query(pay.getCtx(), MOrderLine.Table_Name, "c_order_ID=? and c_payment_ID=?",
				pay.get_TrxName()).setParameters(order.getC_Order_ID(), pay.getC_Payment_ID()).first();
		if (oLine != null)
			return "";
		oLine = new MOrderLine(order);
		int chargeID;
		chargeID = pay.getC_Charge_ID();
		oLine.setC_Charge_ID(chargeID);
		MCharge charge = new MCharge(pay.getCtx(), chargeID, pay.get_TrxName());
		MTaxCategory tc = (MTaxCategory) charge.getC_TaxCategory();
		oLine.setC_Tax_ID(tc.getDefaultTax().getC_Tax_ID());

		oLine.setQty(Env.ONE);
		oLine.setPrice(distributionAmtConverted);
		oLine.set_ValueOfColumn(MPayment.COLUMNNAME_C_Payment_ID, pay.getC_Payment_ID());
		oLine.setC_Project_ID(pay.getC_Project_ID());
		MBPartner bpartner = (MBPartner) oLine.getC_Order().getC_BPartner();
		oLine.set_ValueOfColumn("isSplitInvoice", bpartner.get_ValueAsBoolean("isSplitInvoice"));
		oLine.set_ValueOfColumn("R_Request_ID", req.getR_Request_ID());
		oLine.saveEx();
		pay.set_ValueOfColumn("isInvoiced", true);
		pay.setDescription(pay.getDescription() == null ? ""
				: pay.getDescription() + " Asignado en orden: " + order.getDocumentNo());
		pay.set_ValueOfColumn("ControlAmt", p_DistributionAmt);
		pay.saveEx();
		return "";
	}

	private void CreateCTAJPaymentAllocs(MPayment pay) {
		String description = "";
		int chargeID;
		MPaymentAllocate[] pAllocs = MPaymentAllocate.get(pay);
		for (MPaymentAllocate alloc : pAllocs) {
			if (alloc.getC_Invoice_ID() > 0)
				continue;
			int r_request_ID = alloc.get_ValueAsInt("R_Request_ID");
			if (r_request_ID <= 0)
				continue;
			MRequest req = new MRequest(pay.getCtx(), r_request_ID, pay.get_TrxName());
			if (req.getC_Order_ID() <= 0)
				continue;
			if (req.get_ValueAsInt("C_OrderLine_ID") != 0) {
				MOrderLine oLine = new MOrderLine(pay.getCtx(), req.get_ValueAsInt("C_OrderLine_ID"),
						pay.get_TrxName());
				oLine.set_ValueOfColumn(MPayment.COLUMNNAME_C_Payment_ID, pay.getC_Payment_ID());
				oLine.saveEx();
				continue;
			}
			MOrder order = (MOrder) req.getC_Order();
			MOrderLine oLine = new MOrderLine(order);
			chargeID = alloc.get_ValueAsInt("C_Charge_ID");
			if (chargeID <= 0)
				continue;
			oLine.setC_Charge_ID(chargeID);
			MCharge charge = new MCharge(pay.getCtx(), chargeID, pay.get_TrxName());
			MTaxCategory tc = (MTaxCategory) charge.getC_TaxCategory();
			oLine.setC_Tax_ID(tc.getDefaultTax().getC_Tax_ID());
			oLine.setQty(Env.ONE);
			BigDecimal distributionAmtConverted = MConversionRate.convert (order.getCtx(),
					alloc.getAmount(), pay.getC_Currency_ID(), order.getC_Currency_ID(),
					order.getDateAcct(), order.getC_ConversionType_ID(), order.getAD_Client_ID(), order.getAD_Org_ID());
			
			oLine.setPrice(distributionAmtConverted);
			oLine.set_ValueOfColumn(MPaymentAllocate.COLUMNNAME_C_PaymentAllocate_ID, alloc.getC_PaymentAllocate_ID());
			oLine.set_ValueOfColumn(MPaymentAllocate.COLUMNNAME_C_Payment_ID, pay.getC_Payment_ID());
			oLine.setC_Project_ID(alloc.get_ValueAsInt("C_Project_ID"));
			MBPartner bpartner = (MBPartner) oLine.getC_Order().getC_BPartner();
			oLine.set_ValueOfColumn("isSplitInvoice", bpartner.get_ValueAsBoolean("isSplitInvoice"));
			oLine.saveEx();
			req.setC_Payment_ID(pay.getC_Payment_ID());
			req.setSalesRep_ID(req.getCreatedBy());
			req.setC_OrderLine_ID(oLine.getC_OrderLine_ID());
			req.saveEx();
			description = description + " " + order.getDocumentNo();
			alloc.set_ValueOfColumn("ControlAmt", oLine.getLineNetAmt());
		}
		pay.set_ValueOfColumn("isInvoiced", true);
		pay.setDescription(
				pay.getDescription() == null ? "" : pay.getDescription() + " Asignado en orden: " + description);
		pay.saveEx();

	}

	private String InvoiceLineCreateOdV(PO A_PO) {
		MInvoice invoice = (MInvoice) A_PO;
		if (!invoice.getC_DocType().getDocBaseType().equals("API"))
			return "";


		String whereClause = "c_project_ID=? and issotrx = 'Y' and docstatus in ('DR','IP') and c_doctypetarget_ID not in (1000424, 1000375)";
		for (MInvoiceLine iLine : invoice.getLines()) {
			if (iLine.getC_OrderLine_ID() > 0)
				return "";
			if (!(iLine.get_ValueAsBoolean("isSalesOrderImmediate") && iLine.getC_Project_ID() >= 0))
				return "";
			MOrder salesOrder = new Query(A_PO.getCtx(), MOrder.Table_Name, whereClause, A_PO.get_TrxName())
					.setParameters(iLine.getC_Project_ID()).setOnlyActiveRecords(true).first();
			if (salesOrder != null && salesOrder.getC_Order_ID() != 0) {
				MOrderLine oSalesLine = new MOrderLine(salesOrder);
				oSalesLine.setC_Charge_ID(iLine.getC_Charge_ID());
				oSalesLine.setQty(iLine.getQtyEntered());
				oSalesLine.setPrice(iLine.getPriceEntered());
				oSalesLine.set_ValueOfColumn("c_invoiceline_PO_ID", iLine.getC_OrderLine_ID());
				MBPartner bpartner = (MBPartner) salesOrder.getC_BPartner();
				oSalesLine.set_ValueOfColumn("isSplitInvoice", bpartner.get_ValueAsBoolean("isSplitInvoice"));
				oSalesLine.setPriceCost(iLine.getPriceEntered());
				oSalesLine.setC_Project_ID(iLine.getC_Project_ID());
				oSalesLine.setC_Activity_ID(iLine.getC_Activity_ID());
				oSalesLine.setUser1_ID(iLine.getUser1_ID());
				oSalesLine.saveEx();

				iLine.set_ValueOfColumn("C_OrderLine_SO_ID", oSalesLine.getC_OrderLine_ID());
				iLine.saveEx();
			} else
				return "No existe una orden de venta";
		}
		whereClause = "c_invoice_ID=?";
		MRequest req = new Query(invoice.getCtx(), MRequest.Table_Name, whereClause, invoice.get_TrxName())
				.setParameters(invoice.getC_Invoice_ID()).first();
		if (req == null)
			return "";
		if (req.getR_Status().isClosed())
			return "";
		int r_status = 0;
		if (r_status == 0)
			return "";
		// for (MRequest req:reqs)
		// {
		req.setR_Status_ID(r_status);
		req.setSalesRep_ID(req.getCreatedBy());
		req.saveEx();
		return "";
	}

	private String CreatePaymentFromAllocationReembolso(PO A_PO) {
		MAllocationHdr alloc = (MAllocationHdr) A_PO;
		MPayment payOrg = null;
		MPayment pay = null;
		for (MAllocationLine alo : alloc.getLines(true)) {
			if (alo.getC_Payment_ID() != 0)
				payOrg = (MPayment) alo.getC_Payment();
		}
		for (MAllocationLine alo : alloc.getLines(true)) {
			if (!(alo.getC_Charge_ID() == 1000280 || alo.getC_Charge_ID() == 1000279))
				continue;

			int defaultaccount = new Query(A_PO.getCtx(), MBankAccount.Table_Name, "", A_PO.get_TrxName())
					.setClient_ID().setOnlyActiveRecords(true).setOrderBy("C_Bankaccount_ID ").firstId();
			BigDecimal multiplicator = payOrg.getC_DocType().getDocBaseType().equals(MDocType.DOCBASETYPE_ARReceipt)
					? Env.ONE.negate()
					: Env.ONE;
			pay = new MPayment(A_PO.getCtx(), 0, A_PO.get_TrxName());
			pay.setC_BankAccount_ID(defaultaccount);
			pay.setTenderType(MPayment.TENDERTYPE_Account);
			pay.setDateTrx(new Timestamp(System.currentTimeMillis()));
			pay.setIsOverUnderPayment(true);
			pay.setAD_Org_ID(alo.getAD_Org_ID());
			pay.setC_Project_ID(payOrg.getC_Project_ID());
			int c_charge_ID = alo.getC_Charge_ID();
			if (c_charge_ID == 0)
				return "";
			int c_doctype_ID = 0;
			
			{
				String docbasetype = payOrg.getC_DocType().getDocBaseType().equals(MDocType.DOCBASETYPE_ARReceipt)
						? MDocType.DOCBASETYPE_APPayment
						: MDocType.DOCBASETYPE_ARReceipt;
				c_doctype_ID = MDocType.getDocType(docbasetype);
				// c_doctype_ID =
				// payOrg.getC_DocType().getDocBaseType().equals(MDocType.DOCBASETYPE_ARReceipt)?
				// 1000479:1000465;
			}
			// else if (alo.getAD_Client_ID() == 1000012)
			// {
			// c_doctype_ID = alo.getC_Invoice().isSOTrx()?1000481:1000464;
			// }
			if (c_doctype_ID == 0)
				return "";

			pay.setC_DocType_ID(c_doctype_ID);

			pay.setIsReceipt(pay.getC_DocType().isSOTrx());
			pay.setC_Charge_ID(c_charge_ID);
			pay.setC_Currency_ID(100);
			pay.setC_BPartner_ID(payOrg.getC_BPartner_ID());
			pay.setPayAmt(alo.getAmount().multiply(multiplicator));
			pay.setDescription("Aviso de Pago por reembolso de anticipo");
			pay.set_CustomColumn("C_AllocationLine_ID", alo.getC_AllocationLine_ID());
			pay.saveEx();
			Env.setContext(Env.getCtx(), alloc.getDocumentNo(), pay.getDocumentNo());
		}
		return "";
	}

	private String updateIsInvoiced(PO A_PO)

	{
		if (!A_PO.is_ValueChanged("QtyInvoiced"))
			return "";

		ArrayList<Object> params = new ArrayList<Object>();
		params.add(A_PO.get_ValueAsInt("C_Order_ID"));

		String sql = "select sum(qtyordered-qtyinvoiced) from c_orderline where c_order_ID=?";
		BigDecimal i = DB.getSQLValueBDEx(A_PO.get_TrxName(), sql, params.toArray());
		if (i.signum() == 0) {
			sql = "Update c_order set isinvoiced = 'Y' where c_order_ID=?";
			DB.executeUpdateEx(sql, params.toArray(), A_PO.get_TrxName());
		}
		return "";
	}

	private String assignPrepayment(PO A_PO) {
		BiFunction<BigDecimal, BigDecimal, BigDecimal> getMaxPayAmt = (t1, t2) -> {
			return t1.abs().compareTo(t2.abs()) <= 0 ? t1 : t2;
		};
		BiFunction<BigDecimal, BigDecimal, BigDecimal> getOverUnderAmt = (payamt, openamt) -> {
			return payamt.abs().compareTo(openamt.abs()) >= 0 ? Env.ZERO : openamt.subtract(payamt);
		};

		BiFunction<BigDecimal, Boolean, BigDecimal> getAmtIsreceipt = (amt, isReceipt) -> {
			return isReceipt ? amt : amt.negate();
		};

		MInvoice invoice = (MInvoice) A_PO;
		if (invoice.getC_Order_ID() == 0)
			return "";
		if (invoice.getC_Order_ID() != 0 && invoice.getC_Order().getC_POS_ID() != 0)
			return "";
		String whereClause = "C_ORDER_ID=? and docstatus in ('CO','CL') and isallocated = 'N'";
		List<MPayment> prepayments = new Query(A_PO.getCtx(), MPayment.Table_Name, whereClause, A_PO.get_TrxName())
				.setParameters(invoice.getC_Order_ID()).list();
		BigDecimal openAmt = invoice.getOpenAmt();
		for (MPayment pay : prepayments) {
			MAllocationHdr alloc = new MAllocationHdr(Env.getCtx(), true, // manual
					invoice.getDateInvoiced(), invoice.getC_Currency_ID(),
					Env.getContext(Env.getCtx(), "#AD_User_Name"), A_PO.get_TrxName());
			alloc.setAD_Org_ID(invoice.getAD_Org_ID());
			alloc.saveEx();
			BigDecimal aLineAmount = getMaxPayAmt.apply(openAmt, pay.getPayAmt());
			BigDecimal overUnderAmt = getOverUnderAmt.apply(aLineAmount, openAmt);
			// Allocation Line
			MAllocationLine aLine = new MAllocationLine(alloc, getAmtIsreceipt.apply(aLineAmount, invoice.isSOTrx()),
					Env.ZERO, Env.ZERO, getAmtIsreceipt.apply(overUnderAmt, invoice.isSOTrx()));
			aLine.setDocInfo(invoice.getC_BPartner_ID(), invoice.getC_Order_ID(), invoice.getC_Invoice_ID());
			aLine.setPaymentInfo(pay.getC_Payment_ID(), 0);
			aLine.saveEx();
			if (!alloc.processIt(DocAction.ACTION_Complete)) // @Trifon
				throw new AdempiereException("Cannot complete allocation: " + alloc.getProcessMsg()); // @Trifon
			alloc.saveEx();
			openAmt = openAmt.subtract(aLineAmount);
		}
		return "";
	}

	public String updateProject(MProject project) {
		BigDecimal result = SHW_CostActual(project); // Aktualisiert die tatsaechlichen Kosten, aus Einkaufsrechnungen
		project.set_ValueOfColumn("SHW_CostActual", result);
		result = SHW_CostPlanned(project); // Aktualisiert die geplanten Kosten, aus Einkaufsorders
		project.set_ValueOfColumn("SHW_CostPlanned", result);
		result = SHW_CostExtrapolated(project); // Summe aus tatsaechlichen und geplanten Kosten(noch nicht fakturiert)
		project.set_ValueOfColumn("SHW_CostExtrapolated", result);

		result = SHW_RevenueActual(project); // Einkommen aus Rechnungen
		project.set_ValueOfColumn("SHW_RevenueActual", result);
		result = SHW_RevenuePlanned(project);
		project.set_ValueOfColumn("SHW_RevenuePlanned", result); // geplantes Einkommen aus verkaufsorder
		result = SHW_RevenueExtrapolated(project); // Summe aus fakturiertem und geplanten nicht fakturiertem Einkommen
		project.set_ValueOfColumn("SHW_RevenueExtrapolated", result);

		if (project.isSummary()) {
			result = SHW_RevenuePlanned_Sons(project, project.getC_Project_ID());
			project.set_ValueOfColumn("SHW_RevenuePlanned_Sons", result);
			result = SHW_RevenueActual_Sons(project, project.getC_Project_ID());
			project.set_ValueOfColumn("SHW_RevenueActual_Sons", result);
			result = SHW_RevenueExtrapolated_Sons(project, project.getC_Project_ID());
			project.set_ValueOfColumn("SHW_RevenueExtrapolated_Sons", result);

			result = SHW_CostPlannedHeritage_Parent(project, project.getC_Project_ID());
			project.set_ValueOfColumn("SHW_CostPlanned_Heritage", result);
			result = SHW_CostActualHeritage_Parent(project, project.getC_Project_ID());
			project.set_ValueOfColumn("SHW_CostActual_Heritage", result);
			result = SHW_CostExtrapolatedHeritage_Parent(project, project.getC_Project_ID());
			project.set_ValueOfColumn("SHW_CostExtrapolated_Heritage", result);

			project.saveEx();

			BigDecimal costPlannedFather = (BigDecimal) project.get_Value("SHW_CostPlanned");
			BigDecimal costActualFather = (BigDecimal) project.get_Value("SHW_CostActual");
			BigDecimal costExtrapolatedFather = (BigDecimal) project.get_Value("SHW_CostExtrapolated");

			BigDecimal revenuePlannedAll = SHW_RevenuePlanned_Sons(project, project.get_ValueAsInt("C_Project_ID"));
			BigDecimal revenueExtrapolatedAll = SHW_RevenueExtrapolated_Sons(project,
					project.get_ValueAsInt("C_Project_ID"));

			BigDecimal weight_father = (BigDecimal) project.get_Value("Weight");
			BigDecimal Volume_father = (BigDecimal) project.get_Value("Volume");

			List<MProject> projectsOfFather = new Query(project.getCtx(), MProject.Table_Name, "C_Project_Parent_ID=?",
					project.get_TrxName()).setParameters(project.getC_Project_ID()).list();
			for (MProject projectson : projectsOfFather) {
				BigDecimal revenuePlanned = (BigDecimal) projectson.get_Value("SHW_RevenuePlanned");
				BigDecimal revenueExtrapolated = (BigDecimal) projectson.get_Value("SHW_RevenueExtrapolated");
				BigDecimal weight = (BigDecimal) projectson.get_Value("Weight");
				BigDecimal volume = (BigDecimal) projectson.get_Value("volume");
				BigDecimal shareRevenue = Env.ZERO;
				BigDecimal shareWeight = Env.ZERO;
				BigDecimal shareVolume = Env.ZERO;
				if (revenuePlannedAll.longValue() != 0)
					shareRevenue = revenuePlanned.divide(revenuePlannedAll, 5, BigDecimal.ROUND_HALF_DOWN);
				if (weight_father != null && weight_father.longValue() != 0)
					shareWeight = weight.divide(weight_father, 5, BigDecimal.ROUND_HALF_DOWN);
				if (Volume_father != null && Volume_father.longValue() != 0)
					shareVolume = volume.divide(Volume_father, 5, BigDecimal.ROUND_HALF_DOWN);
				SHW_CostPlanned_Heritage(project, projectson, costPlannedFather, costActualFather,
						costExtrapolatedFather, shareVolume, shareWeight, shareRevenue);
			}
		}

		if (project.get_ValueAsInt("C_Project_Parent_ID") != 0) {
			MProject father = new MProject(project.getCtx(), project.get_ValueAsInt("C_Project_Parent_ID"),
					project.get_TrxName());
			result = SHW_CostPlannedHeritage_Parent(project, project.get_ValueAsInt("C_Project_Parent_ID"));
			father.set_ValueOfColumn("SHW_CostPlanned_Heritage", result);
			result = SHW_CostActualHeritage_Parent(project, project.getC_Project_ID());
			father.set_ValueOfColumn("SHW_CostActual_Heritage", result);
			result = SHW_CostExtrapolatedHeritage_Parent(project, project.getC_Project_ID());
			father.set_ValueOfColumn("SHW_CostExtrapolated_Heritage", result);

			father.saveEx();// Bis hier

			BigDecimal costPlannedFather = (BigDecimal) father.get_Value("SHW_CostPlanned");
			BigDecimal costActualFather = (BigDecimal) father.get_Value("SHW_CostActual");
			BigDecimal costExtrapolatedFather = (BigDecimal) father.get_Value("SHW_CostExtrapolated");

			BigDecimal revenuePlanned = SHW_RevenuePlanned_Sons(project, project.get_ValueAsInt("C_Project_Parent_ID"));
			BigDecimal revenueAllAct = SHW_RevenueActual_Sons(project, project.get_ValueAsInt("C_Project_Parent_ID"));
			BigDecimal revenueAllExtrapolated = SHW_RevenueExtrapolated_Sons(project,
					project.get_ValueAsInt("C_Project_Parent_ID"));

			BigDecimal weight_father = (BigDecimal) father.get_Value("Weight");
			BigDecimal Volume_father = (BigDecimal) father.get_Value("Volume");

			List<MProject> projectsOfFather = new Query(project.getCtx(), MProject.Table_Name, "C_Project_Parent_ID=?",
					project.get_TrxName()).setParameters(project.get_ValueAsInt("C_Project_Parent_ID")).list();
			for (MProject projectson : projectsOfFather) {
				BigDecimal revenueExtrapolated = (BigDecimal) projectson.get_Value("SHW_RevenuePlanned");
				BigDecimal weight = (BigDecimal) projectson.get_Value("Weight");
				BigDecimal volume = (BigDecimal) projectson.get_Value("volume");
				if (volume == null)
					volume = Env.ZERO;
				BigDecimal shareRevenue = Env.ZERO;
				BigDecimal shareWeight = Env.ZERO;
				BigDecimal shareVolume = Env.ZERO;
				if (revenueAllExtrapolated.longValue() != 0)
					shareRevenue = revenueExtrapolated.divide(revenueAllExtrapolated, 5, BigDecimal.ROUND_HALF_DOWN);
				if (weight_father != null && weight_father.longValue() != 0)
					shareWeight = weight.divide(weight_father, 5, BigDecimal.ROUND_HALF_DOWN);
				if (Volume_father != null && Volume_father.longValue() != 0)
					shareVolume = volume.divide(Volume_father, 5, BigDecimal.ROUND_HALF_DOWN);
				SHW_CostPlanned_Heritage(project, projectson, costPlannedFather, costActualFather,
						costExtrapolatedFather, shareVolume, shareWeight, shareRevenue);

			}
			father.set_ValueOfColumn("SHW_RevenuePlanned_Sons", revenuePlanned);
			father.set_ValueOfColumn("SHW_RevenueActual_Sons", revenueAllAct);
			father.set_ValueOfColumn("SHW_RevenueExtrapolated_Sons", revenueAllExtrapolated);
			father.saveEx();
			project.saveEx();
			// father.updateProject();
		}
		project.saveEx();

		return "";
	}

	private BigDecimal SHW_CostActual(MProject project)// Summiert d
	{
		String expresion = "LineNetAmtRealInvoiceLine(c_invoiceline_ID)";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_invoice_ID in (select c_invoice_ID from c_invoice where docstatus in ('CO','CL') ");
		whereClause.append(" AND issotrx = 'N')");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append(" and c_project_ID in (?)");
		BigDecimal result = Env.ZERO;
		result = new Query(project.getCtx(), MInvoiceLine.Table_Name, whereClause.toString(), project.get_TrxName())
				.setParameters(project.getC_Project_ID()).aggregate(expresion, Query.AGGREGATE_SUM);
		return result;
	}

	private BigDecimal SHW_CostPlanned(MProject project) {
		String expresion = "linenetamt - taxAmtReal(c_Orderline_ID)";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_order_ID in (select c_order_ID from c_order where docstatus in ('CO','CL','IP') ");
		whereClause.append(" AND issotrx = 'N')");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append(" and c_project_ID in (?)");
		BigDecimal result = Env.ZERO;
		result = new Query(project.getCtx(), MOrderLine.Table_Name, whereClause.toString(), project.get_TrxName())
				.setParameters(project.getC_Project_ID()).aggregate(expresion, Query.AGGREGATE_SUM);
		return result;
	}

	private BigDecimal SHW_NotInvoicedCost(MProject project) {
		String expresion = "((qtyordered-qtyinvoiced)*Priceactual) - (taxamt_Notinvoiced(c_Orderline_ID))";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_order_ID in (select c_order_ID from c_order where docstatus in ('CO','CL') ");
		whereClause.append(" AND issotrx = 'N')");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append(" and c_project_ID in (?)");
		BigDecimal amtNotInvoiced = Env.ZERO;
		amtNotInvoiced = new Query(project.getCtx(), MOrderLine.Table_Name, whereClause.toString(),
				project.get_TrxName()).setParameters(project.getC_Project_ID()).aggregate(expresion,
						Query.AGGREGATE_SUM);
		return amtNotInvoiced;
	}

	private BigDecimal SHW_CostExtrapolated(MProject project) {
		BigDecimal result = SHW_NotInvoicedCost(project).add(SHW_CostActual(project));
		return result;
	}

	private BigDecimal SHW_RevenueActual(MProject project) {
		String expresion = "LineNetAmtRealInvoiceLine(c_invoiceline_ID)";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_invoice_ID in (select c_invoice_ID from c_invoice where docstatus in ('CO','CL') ");
		whereClause.append(" AND issotrx = 'Y')");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append(" and c_project_ID in (?)");
		BigDecimal result = Env.ZERO;
		List<MInvoiceLine> iLine = new Query(project.getCtx(), MInvoiceLine.Table_Name, whereClause.toString(), null)
				.setParameters(project.getC_Project_ID())
						.list();
		return result;
	}

	private BigDecimal SHW_RevenuePlanned(MProject project) {
		String expresion = "linenetamt - taxAmtReal(c_Orderline_ID)";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_order_ID in (select c_order_ID from c_order where docstatus in ('CO','CL','IP') ");
		whereClause.append(" AND issotrx = 'Y' )");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append(" and c_project_ID in (?)");
		BigDecimal result = Env.ZERO;
		result = new Query(project.getCtx(), MOrderLine.Table_Name, whereClause.toString(), project.get_TrxName())
				.setParameters(project.getC_Project_ID()).aggregate(expresion, Query.AGGREGATE_SUM);
		return result;
	}

	private BigDecimal SHW_NotInvoicedRevenue(MProject project) {
		String expresion = "((qtyordered-qtyinvoiced)*Priceactual) - (taxamt_Notinvoiced(c_Orderline_ID))";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_order_ID in (select c_order_ID from c_order where docstatus in ('CO','CL') ");
		whereClause.append(" AND issotrx = 'Y')");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append(" and c_project_ID in (?)");
		BigDecimal amtNotInvoiced = Env.ZERO;
		amtNotInvoiced = new Query(project.getCtx(), MOrderLine.Table_Name, whereClause.toString(),
				project.get_TrxName()).setParameters(project.getC_Project_ID()).aggregate(expresion,
						Query.AGGREGATE_SUM);
		return amtNotInvoiced;
	}

	private BigDecimal SHW_RevenueExtrapolated(MProject project) {
		BigDecimal result = SHW_NotInvoicedRevenue(project).add(SHW_RevenueActual(project));
		return result;
	}

	private BigDecimal SHW_RevenueActual_Sons(MProject project, int c_project_parent_ID) {
		String expresion = "LineNetAmtRealInvoiceLine(c_invoiceline_ID)";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_invoice_ID in (select c_invoice_ID from c_invoice where docstatus in ('CO','CL') ");
		whereClause.append(" AND issotrx = 'Y')");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append(" and c_project_ID in (select c_project_ID from c_project where c_project_parent_ID =?)");
		BigDecimal result = Env.ZERO;
		result = new Query(project.getCtx(), MInvoiceLine.Table_Name, whereClause.toString(), project.get_TrxName())
				.setParameters(c_project_parent_ID).aggregate(expresion, Query.AGGREGATE_SUM);
		return result;
	}

	private BigDecimal SHW_RevenuePlanned_Sons(MProject project, int c_Project_Parent_ID) {
		String expresion = "linenetamt - taxAmtReal(c_Orderline_ID)";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_order_ID in (select c_order_ID from c_order where docstatus in ('CO','CL','IP') ");
		whereClause.append(" AND issotrx = 'Y')");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append(" and c_project_ID in (select c_project_ID from c_project where c_project_parent_ID =?)");
		BigDecimal result = Env.ZERO;
		result = new Query(project.getCtx(), MOrderLine.Table_Name, whereClause.toString(), project.get_TrxName())
				.setParameters(c_Project_Parent_ID).aggregate(expresion, Query.AGGREGATE_SUM);
		return result;
	}

	private BigDecimal SHW_NotInvoicedRevenue_Sons(MProject project, int c_Project_Parent_ID) {
		String expresion = "((qtyordered-qtyinvoiced)*Priceactual) - (taxamt_Notinvoiced(c_Orderline_ID))";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_order_ID in (select c_order_ID from c_order where docstatus in ('CO','CL') ");
		whereClause.append(" AND issotrx = 'Y')");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append("  and c_project_ID in (select c_project_ID from c_project where c_project_parent_ID =?)");
		BigDecimal amtNotInvoiced = Env.ZERO;
		amtNotInvoiced = new Query(project.getCtx(), MOrderLine.Table_Name, whereClause.toString(),
				project.get_TrxName()).setParameters(c_Project_Parent_ID).aggregate(expresion, Query.AGGREGATE_SUM);
		return amtNotInvoiced;
	}

	private BigDecimal SHW_RevenueExtrapolated_Sons(MProject project, int c_project_Parent_ID) {
		BigDecimal result = SHW_NotInvoicedRevenue_Sons(project, c_project_Parent_ID)
				.add(SHW_RevenueActual_Sons(project, c_project_Parent_ID));
		return result;
	}

	private BigDecimal SHW_CostPlannedHeritage_Parent(MProject project, int c_Project_Parent_ID) {
		String expresion = "linenetamt - taxAmtReal(c_Orderline_ID)";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_order_ID in (select c_order_ID from c_order where docstatus in ('CO','CL','IP') ");
		whereClause.append(" AND issotrx = 'N')");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append(" and c_project_ID in (select c_project_ID from c_project where c_project_parent_ID =?)");
		BigDecimal result = Env.ZERO;
		result = new Query(project.getCtx(), MOrderLine.Table_Name, whereClause.toString(), project.get_TrxName())
				.setParameters(c_Project_Parent_ID).aggregate(expresion, Query.AGGREGATE_SUM);
		return result;
	}

	private BigDecimal SHW_CostActualHeritage_Parent(MProject project, int c_Project_Parent_ID) {
		String expresion = "LineNetAmtRealInvoiceLine(c_invoiceline_ID)";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_invoice_ID in (select c_invoice_ID from c_invoice where docstatus in ('CO','CL') ");
		whereClause.append(" AND issotrx = 'N')");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append(" and c_project_ID in (select c_project_ID from c_project where c_project_parent_ID =?)");
		BigDecimal result = Env.ZERO;
		result = new Query(project.getCtx(), MInvoiceLine.Table_Name, whereClause.toString(), project.get_TrxName())
				.setParameters(project.getC_Project_ID()).aggregate(expresion, Query.AGGREGATE_SUM);
		return result;
	}

	private BigDecimal SHW_NotInvoiceCostHeritage_Parent(MProject project, int c_Project_Parent_ID) {
		String expresion = "((qtyordered-qtyinvoiced)*Priceactual) - (taxamt_Notinvoiced(c_Orderline_ID))";
		StringBuffer whereClause = new StringBuffer();
		whereClause.append("c_order_ID in (select c_order_ID from c_order where docstatus in ('CO','CL') ");
		whereClause.append(" AND issotrx = 'N')");
		whereClause.append(
				" and (c_charge_ID is null or c_charge_ID not in (select c_charge_ID from c_charge where c_chargetype_ID in (1000003,1000002)))");
		whereClause.append(" and c_project_ID in (select c_project_ID from c_project where c_project_parent_ID =?)");
		BigDecimal result = Env.ZERO;
		result = new Query(project.getCtx(), MOrderLine.Table_Name, whereClause.toString(), project.get_TrxName())
				.setParameters(c_Project_Parent_ID).aggregate(expresion, Query.AGGREGATE_SUM);
		return result;
	}

	private BigDecimal SHW_CostExtrapolatedHeritage_Parent(MProject project, int c_Project_Parent_ID) {
		return SHW_NotInvoiceCostHeritage_Parent(project, c_Project_Parent_ID)
				.add(SHW_CostActualHeritage_Parent(project, c_Project_Parent_ID));
	}

	private Boolean SHW_CostPlanned_Heritage(MProject project, MProject son, BigDecimal costPlannedFather,
			BigDecimal costActualFather, BigDecimal costExtrapolatedFather, BigDecimal shareVolume,
			BigDecimal shareWeight, BigDecimal shareRevenue) {
		BigDecimal result = Env.ZERO;
		result = costPlannedFather.multiply(shareRevenue);
		son.set_ValueOfColumn("SHW_CostPlanned_Heritage", result);
		result = costPlannedFather.multiply(shareVolume);
		son.set_ValueOfColumn("SHW_CostPlanned_Heritage_V", result);
		result = costPlannedFather.multiply(shareWeight);
		son.set_ValueOfColumn("SHW_CostPlanned_Heritage_W", result);

		result = costActualFather.multiply(shareRevenue);
		son.set_ValueOfColumn("SHW_CostActual_Heritage", result);
		result = costActualFather.multiply(shareVolume);
		son.set_ValueOfColumn("SHW_CostActual_Heritage_V", result);
		result = costActualFather.multiply(shareWeight);
		son.set_ValueOfColumn("SHW_CostActual_Heritage_W", result);

		result = costExtrapolatedFather.multiply(shareRevenue);
		son.set_ValueOfColumn("SHW_CostExtrapolated_Heritage", result);
		result = costExtrapolatedFather.multiply(shareVolume);
		son.set_ValueOfColumn("SHW_CostExtrapol_Heritage_V", result);
		result = costExtrapolatedFather.multiply(shareWeight);
		son.set_ValueOfColumn("SHW_CostExtrapol_Heritage_W", result);

		if (son.getC_Project_ID() != project.getC_Project_ID())
			son.saveEx();
		return true;
	}
	

	String purchaseInvoiceCreateOrderLine(PO A_PO) {
		MOrder order = (MOrder)A_PO;
		MDocType doctype = (MDocType)order.getC_DocTypeTarget();
		Boolean isSalesOrderImmediate = doctype.get_ValueAsBoolean("isSalesOrderImmediate");
		if (!isSalesOrderImmediate)
			return "";
		
		MProject project = (MProject)order.getC_Project();
		String whereClause = "issotrx = 'Y' and docstatus in ('DR','IP') and c_doctypetarget_ID not in (1000424, 1000375) and c_project_ID =?";
		String projectclause = "";
		if (!project.isSummary())
			projectclause = "C_Project_ID =?";
		else
		{
			projectclause = "C_Project_Parent_ID =?";

		}
		List<MProject> projects = new Query(A_PO.getCtx(), MProject.Table_Name, projectclause, A_PO.get_TrxName())
				.setParameters(project.getC_Project_ID())
				.setOrderBy("C_Project_ID")
				.list();
		for (MOrderLine oLine:order.getLines())            
		{
			if (!oLine.get_ValueAsBoolean("isSalesOrderImmediate"))
				continue;
			List<MOrderLine> iLineHistory = new Query(A_PO.getCtx(), MOrderLine.Table_Name, "c_orderline_PO_ID =?", A_PO.get_TrxName())
					.setParameters(oLine.getC_OrderLine_ID())
					.list();
			if (!iLineHistory.isEmpty())
				continue;
			MOrder salesOrder = null;
			for (MProject projectHijo : projects)
			{
				if (oLine.get_ValueAsInt("c_OrderSO_ID") >0)
					salesOrder = new MOrder(A_PO.getCtx(), oLine.get_ValueAsInt("c_OrderSO_ID"), A_PO.get_TrxName());
				else
				{ 
					salesOrder = new Query(A_PO.getCtx(), MOrder.Table_Name, whereClause     , A_PO.get_TrxName())
							.setParameters(projectHijo.getC_Project_ID())
							.setOnlyActiveRecords(true)
							.first();                
				}
				if (salesOrder != null && salesOrder.getC_Order_ID() != 0)
				{
					MOrderLine oSalesLine = new MOrderLine(salesOrder);
					if (oLine.getM_Product_ID()>0)
					{

						StringBuffer sql = new StringBuffer( "select coalesce(bpp.substitute_ID, p.substitute_ID, p.m_product_ID) ");
						sql.append(" from m_product p  ");
						sql.append(" left join c_bpartner_product  bpp on p.m_product_ID = bpp.m_product_ID and bpp.c_bpartner_ID =?");
						sql.append("  where p.m_product_ID =?");
						ArrayList<Object> params = new ArrayList();
						params.add(salesOrder.getC_BPartner_ID());
						params.add(oLine.getM_Product_ID());
						int M_Product_ID = DB.getSQLValueEx(salesOrder.get_TrxName(), sql.toString(), params);

						MProductPricing pp = new MProductPricing (M_Product_ID, salesOrder.getC_BPartner_ID(),
								oLine.getQtyOrdered(), salesOrder.isSOTrx(), null);
						//
						MPriceList pl = (MPriceList)salesOrder.getM_PriceList();
						pp.setM_PriceList_ID(pl.getM_PriceList_ID());
						MPriceListVersion plv = pl.getPriceListVersion(salesOrder.getDateOrdered());
						if (plv == null)
							return "No existe una versión válida de la lista de precios";
						pp.setM_PriceList_Version_ID(plv.getM_PriceList_Version_ID()); 
						pp.setPriceDate(salesOrder.getDateOrdered());           
						oSalesLine.setM_Product_ID(M_Product_ID);
						oSalesLine.setPrice(pp.getPriceStd());
					}
					if (oLine.getC_Charge_ID()>0)
					{                    
						oSalesLine.setC_Charge_ID(oLine.getC_Charge_ID());
						oSalesLine.setPrice(oLine.getPriceActual());
					}

					oSalesLine.setC_Project_ID(projectHijo.getC_Project_ID());
					oSalesLine.setQty(oLine.getQtyEntered());
					oSalesLine.setC_Tax_ID(oLine.getC_Tax_ID());
					oSalesLine.set_ValueOfColumn("c_orderline_PO_ID", oLine.getC_OrderLine_ID());
					oSalesLine.saveEx();
				}
				else
					return "No existe una orden de venta para el projecto " + projectHijo.getValue();
			}
		}
		return  "";

	}
	
	 private Boolean addPayment(MPayment payment)
	{
		//	Validate if exist on a bank statement
		MBankStatementLine bankStatementLine = payment.getBankStatementLine();
		if(bankStatementLine != null
				&& bankStatementLine.getC_BankStatement_ID() > 0) {
			return true;
		}
		//	Add
		StringBuilder whereClause = new StringBuilder();
		whereClause.append(MBankStatement.COLUMNNAME_C_BankAccount_ID).append("=? AND ")
				.append("TRUNC(").append(MBankStatement.COLUMNNAME_StatementDate).append(",'DD')<=? AND ")
				.append(MBankStatement.COLUMNNAME_Processed).append("=?");
		MBankStatement bankStatement = new Query(payment.getCtx() , MBankStatement.Table_Name , whereClause.toString(), payment.get_TrxName())
				.setClient_ID()
				.setParameters(payment.getC_BankAccount_ID(), TimeUtil.getDay(payment.getDateTrx()) , false)
				.first();
		if (bankStatement == null || bankStatement.get_ID() <= 0)
		{
			bankStatement = new MBankStatement(payment.getCtx() , 0 , payment.get_TrxName());
			bankStatement.setC_BankAccount_ID(payment.getC_BankAccount_ID());
			bankStatement.setStatementDate(payment.getDateAcct());
			if(payment.getDescription() != null) {
				bankStatement.setName(payment.getDescription());
			} else {
				SimpleDateFormat format = DisplayType.getDateFormat(DisplayType.Date);
				bankStatement.setName(Msg.parseTranslation(payment.getCtx(), "@Generate@: ") + format.format(payment.getDateAcct()));
			}
			bankStatement.saveEx();
		}

		bankStatementLine = new MBankStatementLine(bankStatement);
		bankStatementLine.setPayment(payment);
		bankStatementLine.setStatementLineDate(payment.getDateAcct());
		bankStatementLine.setDateAcct(payment.getDateAcct());
		bankStatementLine.saveEx();
		return true;
	}
	 String orderLine_setLineNetAmt(PO A_PO) {

		 //	Line Net Amt
		 MOrderLine orderLine = (MOrderLine)A_PO;
		 boolean documentLevel = orderLine.getC_Tax().isDocumentLevel();
		 MTax orderTax = (MTax)orderLine.getC_Tax();
		 if (!orderLine.getC_Order().getM_PriceList().isTaxIncluded() || documentLevel)
			 return "";
		 BigDecimal lineNetAmount = null;

		 int stdTax_ID = 0;
		 if (orderLine.getProduct() == null)
		 {
			 if (orderLine.getCharge() != null)	 {
				 stdTax_ID = ((MTaxCategory)orderLine.getCharge().getC_TaxCategory()).getDefaultTax().getC_Tax_ID();
			 }

		 }
		 else	// Product
			 stdTax_ID = ((MTaxCategory) orderLine.getProduct().getC_TaxCategory()).getDefaultTax().getC_Tax_ID();
		 if (orderLine.getC_Tax_ID() == stdTax_ID)
			 return "";

		 if(orderLine.getM_Product_ID() != 0) {
			 MProduct product = MProduct.get(orderLine.getCtx(), orderLine.getM_Product_ID(), orderLine.get_TrxName());
				 if(product.getC_UOM_ID() != orderLine.getC_UOM_ID()
				 && orderLine.getPriceEntered() != null && !orderLine.getPriceEntered().equals(Env.ZERO)
				 && orderLine.getQtyEntered() != null && !orderLine.getQtyEntered().equals(Env.ZERO)) {
					 lineNetAmount = orderLine.getQtyEntered().multiply(orderLine.getPriceEntered());
				 }
		 }
		 //	Set default
		 if (lineNetAmount == null)
			 lineNetAmount = orderLine.getPriceActual().multiply(orderLine.getQtyOrdered());


		 if (lineNetAmount.scale() > orderLine.getPrecision())
			 lineNetAmount = lineNetAmount.setScale(orderLine.getPrecision(), BigDecimal.ROUND_HALF_UP);
		 orderLine.setLineNetAmt (lineNetAmount);
		 return "";
	 }
	 
	 String invoiceLine_setLineNetAmt(PO A_PO) {

		 //	Line Net Amt
		 MInvoiceLine invoiceLine = (MInvoiceLine)A_PO;
		 boolean documentLevel = invoiceLine.getC_Tax().isDocumentLevel();
		 MTax orderTax = (MTax)invoiceLine.getC_Tax();
		 if (!invoiceLine.getC_Invoice().getM_PriceList().isTaxIncluded() || documentLevel)
			 return "";
		 BigDecimal lineNetAmount = null;

		 int stdTax_ID = 0;
		 if (invoiceLine.getProduct() == null)
		 {
			 if (invoiceLine.getCharge() != null)	 {
				 stdTax_ID = ((MTaxCategory)invoiceLine.getCharge().getC_TaxCategory()).getDefaultTax().getC_Tax_ID();
			 }

		 }
		 else	// Product
			 stdTax_ID = ((MTaxCategory) invoiceLine.getProduct().getC_TaxCategory()).getDefaultTax().getC_Tax_ID();
		 if (invoiceLine.getC_Tax_ID() == stdTax_ID)
			 return "";

		 if(invoiceLine.getM_Product_ID() != 0) {
			 MProduct product = MProduct.get(invoiceLine.getCtx(), invoiceLine.getM_Product_ID(), invoiceLine.get_TrxName());
				 if(product.getC_UOM_ID() != invoiceLine.getC_UOM_ID()
				 && invoiceLine.getPriceEntered() != null && !invoiceLine.getPriceEntered().equals(Env.ZERO)
				 && invoiceLine.getQtyEntered() != null && !invoiceLine.getQtyEntered().equals(Env.ZERO)) {
					 lineNetAmount = invoiceLine.getQtyEntered().multiply(invoiceLine.getPriceEntered());
				 }
		 }
		 //	Set default
		 if (lineNetAmount == null)
			 lineNetAmount = invoiceLine.getPriceActual().multiply(invoiceLine.getQtyInvoiced());


		 if (lineNetAmount.scale() > invoiceLine.getPrecision())
			 lineNetAmount = lineNetAmount.setScale(invoiceLine.getPrecision(), BigDecimal.ROUND_HALF_UP);
		 invoiceLine.setLineNetAmt (lineNetAmount);
		 return "";
	 }
	 
	 private String requestUpdateBpartner(PO A_PO) {
		 MRequest request = (MRequest)A_PO;
		 if (request.getC_Order_ID() == 0)
			 return "";
		 if (!request.getC_Order().isSOTrx()) {
			 request.set_ValueOfColumn("C_BPartnerVendor_ID", request.getC_Order().getC_BPartner_ID());
		 }
		 return "";
	 }
	 

	 
	 private String updateRequest(PO A_PO) {
		 String error = "";
		 MOrderLine orderLine = (MOrderLine)A_PO;
		 if (orderLine.getC_Order().isSOTrx()) {

				int paymentID= orderLine.get_ValueAsInt("C_Payment_ID");
				int no = DB.getSQLValueEx(orderLine.get_TrxName(), 
						"Select count(*) from c_Payment where docstatus in ('CO','CL') AND C_Payment_ID=?", paymentID);
				if (no > 0)
					return "Esta línea está asignada a un pago completado";
				
		 }
		 return error;
	 }
	 
	 
	 
	 
	 

} // MyValidator

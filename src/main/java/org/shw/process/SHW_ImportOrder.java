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
package org.shw.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import org.adempiere.core.domains.models.X_I_Order;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProductPrice;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Import Order from I_Order
 *  @author Oscar Gomez
 * 			<li>BF [ 2936629 ] Error when creating bpartner in the importation order
 * 			<li>https://sourceforge.net/tracker/?func=detail&aid=2936629&group_id=176962&atid=879332
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ImportOrder.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 * 	@author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *		<li> FR [ 1436 ] Add User Parameter for import orders
 *		@see https://github.com/adempiere/adempiere/issues/1436
 *  @author Victor Pérez, victor.perez@e-evolution.com , e-Evolution http://www.e-evolution.com
 * 		<li>#3712 [Bug Report] Incorrect handling of DB transactions in the order import process</>
 * 		<li>https://github.com/adempiere/adempiere/issues/3712</li>
 * 		<li> Add support the Import Order in Parallel and Change the approach to import
 * 	    <li> https://github.com/adempiere/adempiere/issues/3742
 */
public class SHW_ImportOrder extends SHW_ImportOrderAbstract {
    /**
     * Effective
     */
    private Timestamp dateValue = null;
    private MOrder order = null;
    private ArrayList<MOrder> newOrders = new ArrayList();

    /**
     * Prepare - e.g., get Parameters.
     */
    protected void prepare() {
        super.prepare();
        if (dateValue == null) {
            dateValue = new Timestamp(System.currentTimeMillis());
        }
        //	Validate Document Action
        if (getDocAction() == null) {
            setDocAction(MOrder.DOCACTION_Prepare);
        }
    }    //	prepare

    


    /**
     * Perform process.
     *
     * @return Message
     * @throws Exception
     */
    protected String doIt() throws java.lang.Exception {
    	StringBuffer sql = null;
    	int no = 0;
    	String clientCheck = " AND AD_Client_ID=" + getClientId();
    	//	for user
    	if(getUserId() !=0) {
    		clientCheck += " AND CreatedBy = " + getUserId();
    	}

    	//	-- New Orders -----------------------------------------------------

    	getSelectionKeys().stream().forEach( key -> {
    		X_I_Order iOrder = new X_I_Order (getCtx (), key, get_TrxName());
    		
    		if (order == null || 
    				(iOrder.getC_DocType_ID() != order.getC_DocTypeTarget_ID()
    				|| iOrder.getC_BPartner_ID() != order.getC_BPartner_ID())) {
    			createOrder(iOrder);
    		}
    		createOrderLine(iOrder);
    	});
    	newOrders.stream().forEach(order ->{
    		order.processIt(getDocAction());
    		order.saveEx();
    	});
    	return "";


    }    //	doIt
    
		private void createOrder(X_I_Order i_order) {
			order = new MOrder (getCtx(), 0, get_TrxName());
			order.setClientOrg (i_order.getAD_Client_ID(), i_order.getAD_Org_ID());
			order.setC_DocTypeTarget_ID(i_order.getC_DocType_ID());
			order.setIsSOTrx(i_order.isSOTrx());
			if (i_order.getDeliveryRule() != null ) {
				order.setDeliveryRule(i_order.getDeliveryRule());
			}
			order.setC_BPartner_ID(i_order.getC_BPartner_ID());
			order.setC_BPartner_Location_ID(i_order.getC_BPartner_Location_ID());
			if (i_order.getAD_User_ID() != 0)
				order.setAD_User_ID(i_order.getAD_User_ID());
			//	Bill Partner
			order.setBill_BPartner_ID(i_order.getC_BPartner_ID());
			order.setBill_Location_ID(i_order.getBillTo_ID());
			//
			if (i_order.getDescription() != null)
				order.setDescription(i_order.getDescription());
			order.setC_PaymentTerm_ID(i_order.getC_PaymentTerm_ID());
			order.setM_PriceList_ID(i_order.getM_PriceList_ID());
			order.setM_Warehouse_ID(i_order.getM_Warehouse_ID());
			if (i_order.getM_Shipper_ID() != 0)
				order.setM_Shipper_ID(i_order.getM_Shipper_ID());
			//	SalesRep from Import or the person running the import
			if (i_order.getSalesRep_ID() != 0)
				order.setSalesRep_ID(i_order.getSalesRep_ID());
			if (order.getSalesRep_ID() == 0)
				order.setSalesRep_ID(getAD_User_ID());
			//
			if (i_order.getAD_OrgTrx_ID() != 0)
				order.setAD_OrgTrx_ID(i_order.getAD_OrgTrx_ID());
			if (i_order.getC_Activity_ID() != 0)
				order.setC_Activity_ID(i_order.getC_Activity_ID());
			if (i_order.getC_Campaign_ID() != 0)
				order.setC_Campaign_ID(i_order.getC_Campaign_ID());
			if (i_order.getC_Project_ID() != 0)
				order.setC_Project_ID(i_order.getC_Project_ID());
			//
			if (i_order.getDateOrdered() != null)
				order.setDateOrdered(i_order.getDateOrdered());
			if (i_order.getDateAcct() != null)
				order.setDateAcct(i_order.getDateAcct());

			order.saveEx();
			newOrders.add(order);
			i_order.setC_Order_ID(order.getC_Order_ID());    	
		}
		private void createOrderLine(X_I_Order i_Order) {
			//			New OrderLine
			MOrderLine line = new MOrderLine (order);
			if (i_Order.getM_Product_ID() != 0)
				line.setM_Product_ID(i_Order.getM_Product_ID(), true);
			if (i_Order.getC_Charge_ID() != 0)
				line.setC_Charge_ID(i_Order.getC_Charge_ID());
			line.setQty(i_Order.getQtyOrdered());
			line.setPrice();
			if (i_Order.getPriceActual().compareTo(Env.ZERO) != 0)
				line.setPrice(i_Order.getPriceActual());
			if (i_Order.getC_Tax_ID() != 0)
				line.setC_Tax_ID(i_Order.getC_Tax_ID());
			else
			{
				line.setTax();
				i_Order.setC_Tax_ID(line.getC_Tax_ID());
			}
			if (i_Order.getFreightAmt() != null)
				line.setFreightAmt(i_Order.getFreightAmt());
			if (i_Order.getLineDescription() != null)
				line.setDescription(i_Order.getLineDescription());
			line.saveEx();
			i_Order.setC_OrderLine_ID(line.getC_OrderLine_ID());
			i_Order.setI_IsImported(true);
			i_Order.setProcessed(true);
			i_Order.saveEx();
		}

  
}    //	ImportOrder

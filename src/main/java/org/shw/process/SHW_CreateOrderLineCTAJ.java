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

import org.compiere.model.MBPartner;
import org.compiere.model.MCharge;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MRequest;
import org.compiere.model.MTaxCategory;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

/**
 *  Creates Payment from c_invoice, including Aging
 *
 *  @author Susanne Calderon
 */

public class SHW_CreateOrderLineCTAJ  extends SvrProcess
{	
    
    @Override    
    protected void prepare()
    {    	

        ProcessInfoParameter[] parameters = getParameter();
        for (ProcessInfoParameter parameter : parameters) {

            String name = parameter.getParameterName();
            if (parameter.getParameter() == null)
                ;
        }
    }
    
      
    @Override
    protected String doIt() throws Exception
    {
    	MRequest req = new MRequest(getCtx(), getRecord_ID(), get_TrxName());
		MOrder order = (MOrder)req.getC_Order();
		if (req.getC_Order_ID()<= 0)
			return "la solicitud " + req.getDocumentNo() + "no tiene referencia a niguna orden";
		BigDecimal p_DistributionAmt = req.getRequestAmt();
		MOrderLine oLine = new Query(getCtx(), MOrderLine.Table_Name, "c_order_ID=? and r_request_ID=?", get_TrxName())
			.setParameters(order.getC_Order_ID(), getRecord_ID())
			.first();
		if (oLine != null)
			return "";
		oLine = new MOrderLine(order);
		int chargeID;
		chargeID = req.get_ValueAsInt("C_Charge_ID");
		oLine.setC_Charge_ID(chargeID);
		MCharge charge = new MCharge(getCtx(), chargeID, get_TrxName());
		MTaxCategory tc = (MTaxCategory)charge.getC_TaxCategory();
		oLine.setC_Tax_ID(tc.getDefaultTax().getC_Tax_ID());

		oLine.setQty(Env.ONE);
		oLine.setPrice(p_DistributionAmt);
		oLine.setC_Project_ID(req.getC_Project_ID());
		MBPartner bpartner = (MBPartner)oLine.getC_Order().getC_BPartner();
		oLine.set_ValueOfColumn("isSplitInvoice", bpartner.get_ValueAsBoolean("isSplitInvoice"));
		oLine.set_ValueOfColumn("R_Request_ID", getRecord_ID());
		oLine.saveEx();
		req.set_ValueOfColumn("C_OrderLine_ID", oLine.getC_OrderLine_ID());
		req.saveEx();
		return "";
	}
    //MSession session = new MSession(getCtx(), Env.getContext(getCtx(), "), trxName)
    

}

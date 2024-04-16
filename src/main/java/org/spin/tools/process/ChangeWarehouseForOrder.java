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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.adempiere.core.domains.models.X_C_Order;
import org.adempiere.core.domains.models.X_C_OrderLine;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.compiere.util.Util;

/**
 * @author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class ChangeWarehouseForOrder extends ChangeWarehouseForOrderAbstract {
	/**	Counter for created	*/
	private AtomicInteger updated = new AtomicInteger();
	/**	Counter for created	*/
	private AtomicInteger errors = new AtomicInteger();
	/**	Log	*/
	private StringBuffer errorsMessage = new StringBuffer();
	
	@Override
	protected String doIt() throws Exception {
		getSelectionKeys().forEach(orderId -> {
			try {
				Trx.run(transactionName -> {
					MOrder order = new MOrder(getCtx(), orderId, transactionName);
					//	Get Current Document Status
					String previousDocumentStatus = order.getDocStatus();
					//	Validate status of order
					if(order.getDocStatus().equals(X_C_Order.DOCSTATUS_InProgress)){
						changeWarehouse(order);
					} else if(order.getDocStatus().equals(X_C_Order.DOCSTATUS_Completed)) {
						if(!order.processIt(X_C_Order.DOCACTION_Re_Activate)) {
							addLog(order.getC_Order_ID(), order.getDateOrdered(), null, order.getProcessMsg());
							throw new AdempiereException(order.getProcessMsg());
						} else {
							order.saveEx();
							String errorMessage = changeWarehouse(order);
							if(!Util.isEmpty(errorMessage)) {
								addLog(order.getC_Order_ID(), order.getDateOrdered(), null, errorMessage);
								throw new AdempiereException(order.getProcessMsg());
							}
						}
					}
					//	For invalid
					if(order.getDocStatus().equals(X_C_Order.DOCSTATUS_Invalid)) {
						addLog(order.getC_Order_ID(), order.getDateOrdered(), null, order.getProcessMsg());
						throw new AdempiereException(order.getProcessMsg());
					} else {
						if(!order.processIt(previousDocumentStatus)) {
							addLog(order.getC_Order_ID(), order.getDateOrdered(), null, order.getProcessMsg());
							throw new AdempiereException(order.getProcessMsg());
						} else {
							order.saveEx();
							updated.getAndIncrement();
						}
					}
				});
			} catch (Exception e) {
				errors.getAndIncrement();
				addMessage(e.getLocalizedMessage());
			}
		});
		return "@Updated@ " + updated + (errors.get() > 0? " @Error@" + errors + " - " + errorsMessage.toString(): "");
	}
	
	/**
	 * add global message
	 * @param message
	 */
	private void addMessage(String message) {
		if(Util.isEmpty(message)) {
			return;
		}
		if(errorsMessage.length() > 0) {
			errorsMessage.append(Env.NL);
		}
		//	
		errorsMessage.append(message);
	}
	
	/**
	 * Change Warehouse 
	 * @param order
	 * @return error if exists
	 */
	private String changeWarehouse(MOrder order) {
		Map<Integer, BigDecimal> quantities = new HashMap<Integer, BigDecimal>();
		Arrays.asList(order.getLines(true, X_C_OrderLine.COLUMNNAME_Line)).forEach(orderLine -> {
			quantities.put(orderLine.getC_OrderLine_ID(), orderLine.getQtyEntered());
			orderLine.setQty(Env.ZERO);
			orderLine.saveEx();
		});
		//	
		if(!order.processIt(X_C_Order.DOCACTION_Prepare)) {
			return order.getProcessMsg();
		} else {
			order.setM_Warehouse_ID(getWarehouseId());
			order.saveEx();
		}
		//	Process all lines
		Arrays.asList(order.getLines(true, X_C_OrderLine.COLUMNNAME_Line)).forEach(orderLine -> {
			orderLine.setM_Warehouse_ID(getWarehouseId());
			orderLine.setQty(quantities.get(orderLine.getC_OrderLine_ID()));
			orderLine.saveEx();
		});
		//	
		if(!order.processIt(X_C_Order.DOCACTION_Prepare)) {
			return order.getProcessMsg();
		} else {
			order.saveEx();
		}
		//	Default nothing
		return null;
	}
}
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

import java.sql.Timestamp;
import java.util.List;

import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MProject;
import org.compiere.model.MUser;

/** Generated Process for (C_Project_GenerateOrder_POSO)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class Project_CreateOrder extends Project_CreateOrderAbstract
{
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		String Docbasetype = isSOTrx()?"SOO":"POO";
		MProject project = new MProject(getCtx(), getRecord_ID(), get_TrxName());
		{


			MOrder order = new MOrder(getCtx(), 0, get_TrxName());
			order.setAD_Org_ID(project.getAD_Org_ID());
			order.setC_Campaign_ID(project.getC_Campaign_ID());
			//order.
			order.setC_Project_ID(project.getC_Project_ID());
			order.setDescription(project.getName());
			Timestamp ts = project.getDateStart();
			if (ts != null)
				order.setDateOrdered (ts);
			ts = project.getDateFinish();
			if (ts != null)
				order.setDatePromised (ts);
			//
			MBPartner bpartner = null;
			if (isSOTrx())
			{
				order.setC_BPartner_ID(project.getC_BPartner_ID());
				bpartner = (MBPartner)project.getC_BPartner();
				order.setAD_User_ID(project.getAD_User_ID());
				order.setSalesRep_ID(project.getSalesRep_ID());
				int c_DocType_ID = bpartner.get_ValueAsInt("C_DocType_ID")<=0?  MDocType.getDocType(Docbasetype):
					bpartner.get_ValueAsInt("C_DocType_ID");
				order.setC_DocTypeTarget_ID(c_DocType_ID);
				order.setM_PriceList_ID(bpartner.getM_PriceList_ID());
				order.setC_PaymentTerm_ID(bpartner.getC_PaymentTerm_ID());
				order.setIsSOTrx(true);
			}
			else
			{
				order.setC_BPartner_ID(getBPartnerId());
				bpartner = new MBPartner(getCtx(), order.getC_BPartner_ID(), get_TrxName());
				MUser[] contacts = bpartner.getContacts(true);
				if (contacts.length>0)
					order.setAD_User_ID(contacts[0].getAD_User_ID());
				order.setSalesRep_ID(project.getSalesRep_ID());
				int c_DocType_ID = 0;
				if (getDocTypeId() ==0)
					c_DocType_ID = bpartner.get_ValueAsInt("C_DoctypePO_ID")<=0?  MDocType.getDocType(Docbasetype):
						bpartner.get_ValueAsInt("C_DoctypePO_ID");
				else c_DocType_ID = getDocTypeId();
				order.setC_DocTypeTarget_ID(c_DocType_ID);
				order.setM_PriceList_ID(bpartner.getPO_PriceList_ID());
				order.setC_PaymentTerm_ID(bpartner.getPO_PaymentTerm_ID());
				order.setIsSOTrx(false);
			}
			order.setC_BPartner_Location_ID(bpartner.getPrimaryC_BPartner_Location_ID());    		//
			order.setM_Warehouse_ID(project.getM_Warehouse_ID());
			order.saveEx();
		}

		return  "";
	}

}
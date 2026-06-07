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

import java.util.ArrayList;
import java.util.List;

import org.compiere.util.DB;

/** Generated Process for (SHW_ValuationEffectiveDate_Komp)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class SHW_ValuationEffectiveDate_Komp extends SHW_ValuationEffectiveDate_KompAbstract
{
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		List<Object> params = new ArrayList<Object>();
		params.add(getAD_PInstance_ID());
		params.add(getDateValue());
		params.add(getAD_Client_ID());
		params.add(getDateValue());
		StringBuffer whereClause =  new StringBuffer(" WHERE p.AD_Client_ID=? AND t.movementdate <=? ");
		
		if (getProductCategoryId()>0) {
			whereClause.append(" AND M_Product_Category_ID=?");
			params.add(getProductCategoryId());			
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO T_InventoryValue");
		sql.append("(AD_PInstance_ID,DateValue,AD_Client_ID,AD_Org_ID,m_Warehouse_ID,m_Attributesetinstance_ID,"+
		" M_Product_ID,M_Product_Category_ID,QtyOnHand,costamt, cumulatedamt)");
		sql.append("SELECT ? ,? ,p.AD_Client_ID, p.AD_Org_ID,0,0, p.M_Product_ID,M_Product_Category_ID,");
		sql.append("sum(t.movementqty) as QtyOnHand,");
		sql.append("shw_searchProductCost(p.m_Product_ID, to_date('01/04/2026','dd/mm/yyyy')) ascostamt, ");
		sql.append("shw_searchProductCost(p.m_Product_ID, to_date('01/04/2026','dd/mm/yyyy')) * sum(t.movementqty) as cumulatedamt ");
		sql.append("FROM m_Transaction t ");
		sql.append("INNER JOIN m_Product p ON t.m_Product_ID=p.m_Product_ID ");
		sql.append(whereClause.toString());
		sql.append("GROUP BY p.m_Product_ID, p.m_Product_Category_ID ");
		int no = DB.executeUpdateEx(sql.toString(), params.toArray(),  get_TrxName());
		return "ok";
	}
}
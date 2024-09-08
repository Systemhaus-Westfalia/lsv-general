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

import org.adempiere.core.domains.models.I_AD_User;
import org.adempiere.core.domains.models.I_C_Recurring;
import org.compiere.model.MRecurring;
import org.compiere.model.Query;
import org.compiere.process.Recurring;
import org.eevolution.services.dsl.ProcessBuilder;


/** Generated Process for (C_Recurring_All)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class C_Recurring_All extends C_Recurring_AllAbstract
{
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{		
		new Query(getCtx(), I_C_Recurring.Table_Name, " runsMax - (SELECT COUNT(*) FROM C_Recurring_Run "
				+ " WHERE C_Recurring_Run.c_Recurring_ID=c_Recurring.c_Recurring_ID) >0 ", get_TrxName())	
		.setOnlyActiveRecords(true)
		.setClient_ID()
		.getIDsAsList()
		.forEach(recurringID-> {
			MRecurring rec = new MRecurring (getCtx(), recurringID, get_TrxName());
			rec.executeRun();
		});
		return "";
	}
}
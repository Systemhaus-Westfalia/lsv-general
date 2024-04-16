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

package org.shw.hr.engine;
import java.util.Map;

import org.eevolution.hr.model.MHRContract;
import org.eevolution.hr.model.MHRPayroll;
import org.eevolution.hr.model.MHRProcess;
import org.spin.hr.util.RuleInterface;



/** Generated Process for (groovy:R_Factor R_Factor)
 *  Description: Factor de liquidacion de sueldo
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class groovy_R_Factor implements RuleInterface {

	String description = null;

	@Override
	public Object run(MHRProcess process, Map<String, Object> engineContext) {
		
		description = null;
		MHRPayroll payroll = new MHRPayroll(process.getCtx(), process.getHR_Payroll_ID(), process.get_TrxName());
		MHRContract
		contract = new MHRContract(process.getCtx(), payroll.getHR_Contract_ID(), null);
		int factor = contract.getNetDays() == 15? 2:1;
		return factor;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
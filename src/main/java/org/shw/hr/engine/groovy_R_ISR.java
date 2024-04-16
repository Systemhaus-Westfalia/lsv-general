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

import org.compiere.util.Env;
import org.eevolution.hr.model.MHRProcess;
import org.spin.hr.util.RuleInterface;



/** Generated Process for (groovy:R_ISR Deduciones ISR)
 *  Description: Usado en R_ISR
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class groovy_R_ISR implements RuleInterface {

	String description = null;

	@Override
	public Object run(MHRProcess process, Map<String, Object> engineContext) {

		double result = 0;
		description = null;
		description = null;
		int countryID = Env.getContextAsInt(process.getCtx(), "#C_Country_ID");
		Double salarioExtra1 = process.getConceptAvg( "P_BAS", "1000001", 4, 26);
		String listName = "ISR";
		Double ingresosGravados =  process.getConcept("R_SBAS_NOMINA_MENOSAFPISS");
		if (ingresosGravados <=0)
			ingresosGravados = 0.01;
		Double valorLimite = process.getList(listName, ingresosGravados, "2");
		Double valorfijo = process.getList(listName, ingresosGravados, "3");
		Double porcentaje = process.getList(listName, ingresosGravados, "1")/100;
		Double valoraplicable = ingresosGravados - valorLimite;
		Double isr = valoraplicable*(porcentaje);
		isr  = isr + valorfijo;
		result = isr;
		return result;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
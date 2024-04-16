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

import org.eevolution.hr.model.MHRProcess;
import org.spin.hr.util.RuleInterface;



/** Generated Process for (groovy:R_HEXTR_NN Horas Extras Nocturnas)
 *  Description: Horas Extras Nocturnas
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class groovy_R_HEXTR_NN implements RuleInterface {

	String description = null;

	@Override
	public Object run(MHRProcess process, Map<String, Object> engineContext) {
		
		double result = 0;
		description = null;
		
		
		
		
		//Menge der geleisteten Ueberstunden
		double hours = process.getConcept("O_HorasExtras_NN");
		
		//Brutolohn Stunde
		double brutolohn = process.getConcept("P_BAS");
		double bl_stunde = brutolohn/240;
		
		double faktor = process.getConcept("P_HEXT");
		// Valor Horas Extras
		double amountExtra = hours*faktor*bl_stunde;
		
		//FaktorP_HEXT_N
		double faktor_add = process.getConcept("P_HEXT_N");
		// Valor adicional por nocturnas
		double amount_Add =amountExtra * faktor_add ;
		
		result = amountExtra + amount_Add;
		return result;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
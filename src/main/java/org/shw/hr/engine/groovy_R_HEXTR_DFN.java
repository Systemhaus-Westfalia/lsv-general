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



/** Generated Process for (groovy:R_HEXTR_DFN Horas Extras Domingo, nocturnas)
 *  Description: Uebersunden, Sonn-Feiertag, nachts
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class groovy_R_HEXTR_DFN implements RuleInterface {

	String description = null;

	@Override
	public Object run(MHRProcess process, Map<String, Object> engineContext) {
		
		double result = 0;
		description = null;
		
		
		
		
		//Menge der geleisteten Ueberstunden
		double c_he_o = process.getConcept("O_HorasExtras_NDF");
		
		//Brutolohn Stunde
		double brutolohn = process.getConcept("P_BAS");
		double bl_stunde = brutolohn/240;
		
		//Faktor
		double faktor = process.getConcept("P_HEXT_DF_N");
		
		//Wert der verschiedenen geleisteten Uebersundenarten
		double s_he_o = c_he_o*faktor*bl_stunde;
		
		
		result = s_he_o;
		return result;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
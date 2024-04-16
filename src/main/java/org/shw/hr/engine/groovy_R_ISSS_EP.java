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



/** Generated Process for (groovy:R_ISSS_EP Deduciones ISSS)
 *  Description: Usado en R_ISSS_EP
 *  Help: 
        
        Double salarioRealizado = getHistoryConceptPeriod("R_SBAS_NOMINA");
        Double salarioActual = getConcept("R_SBAS_NOMINA");
        Double salarioCalculado = (salarioRealizado + salarioActual) * getFactorPeriod();
        Double ISSSMax = getConcept("P_ISSS_MAX");
        salarioCalculado =   salarioCalculado>ISSSMax?ISSSMax:salarioCalculado;
        Double ISSSTotal = salarioCalculado * getConcept("P_ISSS_EP");
        
        Double ISSSRealizado = getHistoryConceptPeriod("R_ISSS_EP");
        Double ISSSPendiente = ISSSTotal - ISSSRealizado;
        Double ISSSPagable = ISSSPendiente / getCountMissingPeriods();
        result =  ISSSPagable;
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class groovy_R_ISSS_EP implements RuleInterface {

	String description = null;

	@Override
	public Object run(MHRProcess process, Map<String, Object> engineContext) {
		//double result = 0;
		description = null;
		//MBPartner bPartner = (MBPartner)engineContext.get("_C_BPartner");
		Double salarioCalculado =  process.getConcept("R_Salario_AFPISSS");
		Double factor = process.getConcept("R_Factor");
		/*
		 * Double salarioquincena = 0.0; Double factor = process.getConcept("R_Factor");
		 * Double salarioTotal = 0.0; GregorianCalendar cal = new GregorianCalendar();
		 * cal.setTime(process.getHR_Period().getStartDate()); if
		 * (cal.get(Calendar.DAY_OF_MONTH) == 1) { salarioquincena =
		 * process.getConcept("P_BAS")/factor; } else { salarioquincena =
		 * MHRMovement.getConceptSum(process.getCtx(), "R_Salario_AFPISSS",
		 * process.getHR_Payroll_ID(), bPartner.getC_BPartner_ID(),
		 * process.getHR_Period().getC_Period().getStartDate(),
		 * process.getHR_Period().getEndDate(), false,process.get_TrxName()); }
		 * 
		 * salarioTotal = salarioCalculado + salarioquincena;
		 */
		Double ISSSMax = process.getConcept("P_ISSS_MAX")/factor;

		salarioCalculado =   salarioCalculado>ISSSMax?ISSSMax:salarioCalculado;
		Double ISSSRate = process.getConcept("P_ISSS_EP");
		Double ISSSTotal = salarioCalculado *ISSSRate;

		Double salarioExtra1 = process.getConceptAvg( "P_BAS", "1000001", 4, 26);
		/*
		 * if (cal.get(Calendar.DAY_OF_MONTH) == 1) { result = ISSSTotal/factor; } else
		 * { Double isss_01 = MHRMovement.getConceptSum(process.getCtx(),
		 * "R_ISSS_PATRON", process.getHR_Payroll_ID(), bPartner.getC_BPartner_ID(),
		 * process.getHR_Period().getC_Period().getStartDate(),
		 * process.getHR_Period().getEndDate(), false,process.get_TrxName()); result =
		 * ISSSTotal - isss_01; }
		 */

		return ISSSTotal;
	}
	@Override
	public String getDescription() {
		return description;
	}
}
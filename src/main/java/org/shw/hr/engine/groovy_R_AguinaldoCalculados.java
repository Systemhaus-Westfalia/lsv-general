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

import org.eevolution.hr.model.MHREmployee;
import org.eevolution.hr.model.MHRMovement;
import org.eevolution.hr.model.MHRProcess;
import org.spin.hr.util.RuleInterface;



/** Generated Process for (groovy:R_AguinaldoCalculados Aguinaldos Calculados Año)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class groovy_R_AguinaldoCalculados implements RuleInterface {

	String description = null;

	@Override
	public Object run(MHRProcess process, Map<String, Object> engineContext) {

		MHREmployee employee = (MHREmployee)engineContext.get("_HR_Employee");
		Double salarioExtra1 = process.getConceptAvg( "P_BAS", "1000001", 4, 26);
		Double salarioExtra = MHRMovement.getConceptAvg(process.getCtx(), "O_SalarioEvtl", process.getHR_Payroll_ID(), 
				employee.getC_BPartner_ID(), 
				process.getHR_Period_ID(), 0,0, false, process.get_TrxName());	
		Double salarioActual = process.getConcept("P_BAS") + process.getConcept("P_BonifiacionFija") + salarioExtra;		
		Double diascalculatorios = process.getConcept("R_AguinaldoDias");	
		Double salariodiario = salarioActual/30;
		Double aguinaldo  = diascalculatorios * salariodiario;		
		return aguinaldo;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
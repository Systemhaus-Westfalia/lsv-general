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




/** Generated Process for (groovy:R_SNET Sueldo Neto)
 *  Description: Usado en concepto R_SNET
 *  Help: result =  salarioBasico - AFP - ISR - ISSS - OpenItems - prestamos + celular + viaticos +prefinc + comisiones + comisionvar + ded2 + ded3;
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class groovy_R_SNET implements RuleInterface {

	String description = null;

	@Override
	public Object run(MHRProcess process, Map<String, Object> engineContext) {

		double result = 0;
		description = null;	

		double salarioTotal = process.getConceptCategory("IngresosGravados") + process.getConceptCategory("IngresosNoGravados");
		double deducciones = process.getConceptCategory("DeduccionesLegales");

		double celular =  process.getConcept("O_Celular");
        double reposicion = process.getConcept("O_Reposicion"); 
        double isr = process.getConcept("O_CorrecionISR"); 

		double prestamos = process.getConcept("R_PrestamosTotal");
		double presperso = process.getConcept("O_PrestamoPersonal");

		result =  salarioTotal  - celular - prestamos + reposicion  - deducciones + presperso;
		return result;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
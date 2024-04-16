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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.compiere.model.Query;
import org.eevolution.hr.model.MHRMovement;
import org.eevolution.hr.model.MHRPeriod;
import org.eevolution.hr.model.MHRProcess;
import org.spin.hr.util.RuleInterface;


/** Generated Process for (groovy:R_ISR_Paid groovy:R_ISR_Paid)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public class groovy_R_ISR_Paid implements RuleInterface {

	String description = null;

	@Override
	public Object run(MHRProcess process, Map<String, Object> engineContext) {
		
		double result = 0;
		description = null;
		
		        GregorianCalendar calStart = new GregorianCalendar();
		        MHRPeriod period = new MHRPeriod(process.getCtx(), process.getHR_Period_ID(), process.get_TrxName());
		        calStart.setTime(period.getStartDate());
		        calStart.set(Calendar.HOUR_OF_DAY, 0);
		        calStart.set(Calendar.MINUTE, 0);
		        calStart.set(Calendar.SECOND, 0);
		        calStart.set(Calendar.MILLISECOND, 0);
		        calStart.set(calStart.get(Calendar.YEAR), 0, 1, 00, 00);
		        Timestamp yearStart = new Timestamp(calStart.getTimeInMillis());
		        calStart.set(calStart.get(Calendar.YEAR), 11, 31, 00, 00);
		        Timestamp yearEnd = new Timestamp(calStart.getTimeInMillis());
		        List<Object> params = new ArrayList<>();
		        params.add(((Integer) engineContext.get("_C_BPartner_ID")));
		        params.add(1000209);
		        params.add(yearStart);
		        params.add(yearEnd);
		        String whereClause = "C_BPartner_ID =? and HR_Concept_ID=? and HR_Process_ID in " +
		                 "(select HR_Process_ID from hr_Process where  dateacct between ? and ?)";
		
		        Double isrPaid = new Query(process.getCtx(), MHRMovement.Table_Name, whereClause, process.get_TrxName())
		                .setOnlyActiveRecords(true)
		                .setClient_ID()
		                .setParameters(params).aggregate(MHRMovement.COLUMNNAME_Amount, Query.AGGREGATE_SUM).doubleValue();
		        Double isrCalculado = process.getConcept("R_ISR_Calculado");        
		        result = isrPaid + isrCalculado ;
		return result;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
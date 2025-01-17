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

import java.util.List;

import org.adempiere.core.domains.models.I_C_InvoiceLine;
import org.adempiere.core.domains.models.X_HR_ListLine;
import org.adempiere.core.domains.models.X_HR_ListVersion;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.Query;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.util.Env;

/** Generated Process for (HR_ListLines_Copy)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class HR_ListLines_Copy extends HR_ListLines_CopyAbstract
{
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		X_HR_ListVersion hr_ListVersion = new X_HR_ListVersion(getCtx(), getRecord_ID(), get_TrxName());
		String whereClause = X_HR_ListVersion.COLUMNNAME_HR_ListVersion_ID + " = ?";
		
		List<X_HR_ListLine> list = new Query(getCtx(), X_HR_ListLine.Table_Name, whereClause, get_TrxName())
										.setParameters(getListVersionId())
										.setOrderBy(X_HR_ListLine.COLUMNNAME_MinValue)
										.list();
		list.stream()
		.forEach(hrListLine -> {
			X_HR_ListLine hr_ListLineNew = new X_HR_ListLine(getCtx(), 0, get_TrxName());
			X_HR_ListLine.copyValues(hrListLine, hr_ListLineNew);
			hr_ListLineNew.setHR_ListVersion_ID(getRecord_ID());
			hr_ListLineNew.setAD_Org_ID(0);
			hr_ListLineNew.saveEx();
});

		
		return "";
	}
}
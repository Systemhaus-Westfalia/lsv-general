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

import java.sql.Timestamp;
import org.compiere.process.SvrProcess;

/** Generated Process for (SHW_ValuationEffectiveDate_Komp)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public abstract class SHW_ValuationEffectiveDate_KompAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "SHW_ValuationEffectiveDate_Komp";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "SHW_ValuationEffectiveDate_Komp";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 1000097;
	/**	Parameter Name for Valuation Date	*/
	public static final String DATEVALUE = "DateValue";
	/**	Parameter Name for Product	*/
	public static final String M_PRODUCT_ID = "M_Product_ID";
	/**	Parameter Name for Product Category	*/
	public static final String M_PRODUCT_CATEGORY_ID = "M_Product_Category_ID";
	/**	Parameter Value for Valuation Date	*/
	private Timestamp dateValue;
	/**	Parameter Value for Product	*/
	private int productId;
	/**	Parameter Value for Product Category	*/
	private int productCategoryId;

	@Override
	protected void prepare() {
		dateValue = getParameterAsTimestamp(DATEVALUE);
		productId = getParameterAsInt(M_PRODUCT_ID);
		productCategoryId = getParameterAsInt(M_PRODUCT_CATEGORY_ID);
	}

	/**	 Getter Parameter Value for Valuation Date	*/
	protected Timestamp getDateValue() {
		return dateValue;
	}

	/**	 Setter Parameter Value for Valuation Date	*/
	protected void setDateValue(Timestamp dateValue) {
		this.dateValue = dateValue;
	}

	/**	 Getter Parameter Value for Product	*/
	protected int getProductId() {
		return productId;
	}

	/**	 Setter Parameter Value for Product	*/
	protected void setProductId(int productId) {
		this.productId = productId;
	}

	/**	 Getter Parameter Value for Product Category	*/
	protected int getProductCategoryId() {
		return productCategoryId;
	}

	/**	 Setter Parameter Value for Product Category	*/
	protected void setProductCategoryId(int productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	/**	 Getter Parameter Value for Process ID	*/
	public static final int getProcessId() {
		return ID_FOR_PROCESS;
	}

	/**	 Getter Parameter Value for Process Value	*/
	public static final String getProcessValue() {
		return VALUE_FOR_PROCESS;
	}

	/**	 Getter Parameter Value for Process Name	*/
	public static final String getProcessName() {
		return NAME_FOR_PROCESS;
	}
}
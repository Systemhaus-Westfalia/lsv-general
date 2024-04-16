/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.										*
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net or http://www.adempiere.net/license.html         *
 *****************************************************************************/

package org.spin.tools.process;

import org.compiere.process.SvrProcess;

/** Generated Process for (Copy Client Setup Process)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public abstract class CopyClientSetupAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "ECA25_CopyClient";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "Copy Client Setup Process";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 54625;
	/**	Parameter Name for Template Client	*/
	public static final String ECA25_TEMPLATECLIENT_ID = "ECA25_TemplateClient_ID";
	/**	Parameter Name for Search Key	*/
	public static final String VALUE = "Value";
	/**	Parameter Name for Name	*/
	public static final String NAME = "Name";
	/**	Parameter Name for Tax ID	*/
	public static final String TAXID = "TaxID";
	/**	Parameter Name for Region	*/
	public static final String C_REGION_ID = "C_Region_ID";
	/**	Parameter Name for City	*/
	public static final String C_CITY_ID = "C_City_ID";
	/**	Parameter Name for Address 1	*/
	public static final String ADDRESS1 = "Address1";
	/**	Parameter Name for Address 2	*/
	public static final String ADDRESS2 = "Address2";
	/**	Parameter Name for Address 3	*/
	public static final String ADDRESS3 = "Address3";
	/**	Parameter Name for Address 4	*/
	public static final String ADDRESS4 = "Address4";
	/**	Parameter Value for Template Client	*/
	private int templateClientId;
	/**	Parameter Value for Search Key	*/
	private String value;
	/**	Parameter Value for Name	*/
	private String name;
	/**	Parameter Value for Tax ID	*/
	private String taxID;
	/**	Parameter Value for Region	*/
	private int regionId;
	/**	Parameter Value for City	*/
	private int cityId;
	/**	Parameter Value for Address 1	*/
	private String address1;
	/**	Parameter Value for Address 2	*/
	private String address2;
	/**	Parameter Value for Address 3	*/
	private String address3;
	/**	Parameter Value for Address 4	*/
	private String address4;

	@Override
	protected void prepare() {
		templateClientId = getParameterAsInt(ECA25_TEMPLATECLIENT_ID);
		value = getParameterAsString(VALUE);
		name = getParameterAsString(NAME);
		taxID = getParameterAsString(TAXID);
		regionId = getParameterAsInt(C_REGION_ID);
		cityId = getParameterAsInt(C_CITY_ID);
		address1 = getParameterAsString(ADDRESS1);
		address2 = getParameterAsString(ADDRESS2);
		address3 = getParameterAsString(ADDRESS3);
		address4 = getParameterAsString(ADDRESS4);
	}

	/**	 Getter Parameter Value for Template Client	*/
	protected int getTemplateClientId() {
		return templateClientId;
	}

	/**	 Setter Parameter Value for Template Client	*/
	protected void setTemplateClientId(int templateClientId) {
		this.templateClientId = templateClientId;
	}

	/**	 Getter Parameter Value for Search Key	*/
	protected String getValue() {
		return value;
	}

	/**	 Setter Parameter Value for Search Key	*/
	protected void setValue(String value) {
		this.value = value;
	}

	/**	 Getter Parameter Value for Name	*/
	protected String getName() {
		return name;
	}

	/**	 Setter Parameter Value for Name	*/
	protected void setName(String name) {
		this.name = name;
	}

	/**	 Getter Parameter Value for Tax ID	*/
	protected String getTaxID() {
		return taxID;
	}

	/**	 Setter Parameter Value for Tax ID	*/
	protected void setTaxID(String taxID) {
		this.taxID = taxID;
	}

	/**	 Getter Parameter Value for Region	*/
	protected int getRegionId() {
		return regionId;
	}

	/**	 Setter Parameter Value for Region	*/
	protected void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	/**	 Getter Parameter Value for City	*/
	protected int getCityId() {
		return cityId;
	}

	/**	 Setter Parameter Value for City	*/
	protected void setCityId(int cityId) {
		this.cityId = cityId;
	}

	/**	 Getter Parameter Value for Address 1	*/
	protected String getAddress1() {
		return address1;
	}

	/**	 Setter Parameter Value for Address 1	*/
	protected void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**	 Getter Parameter Value for Address 2	*/
	protected String getAddress2() {
		return address2;
	}

	/**	 Setter Parameter Value for Address 2	*/
	protected void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**	 Getter Parameter Value for Address 3	*/
	protected String getAddress3() {
		return address3;
	}

	/**	 Setter Parameter Value for Address 3	*/
	protected void setAddress3(String address3) {
		this.address3 = address3;
	}

	/**	 Getter Parameter Value for Address 4	*/
	protected String getAddress4() {
		return address4;
	}

	/**	 Setter Parameter Value for Address 4	*/
	protected void setAddress4(String address4) {
		this.address4 = address4;
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
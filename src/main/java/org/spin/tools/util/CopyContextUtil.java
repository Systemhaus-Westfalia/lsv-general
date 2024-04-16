/*************************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                              *
 * This program is free software; you can redistribute it and/or modify it    		 *
 * under the terms version 2 or later of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope   		 *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 		 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           		 *
 * See the GNU General Public License for more details.                       		 *
 * You should have received a copy of the GNU General Public License along    		 *
 * with this program; if not, write to the Free Software Foundation, Inc.,    		 *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     		 *
 * For the text or an alternative of this public license, you may reach us    		 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpya.com				  		                 *
 *************************************************************************************/
package org.spin.tools.util;

import java.util.Properties;

import org.compiere.util.Env;
/**
 * Util class for context management
 * @author yamel
 *
 */
public class CopyContextUtil {
	/** WindowNo for this process */
	public static final int     WINDOW_THIS_PROCESS = 9999;
	/**	Current Client	*/
	private int currentClientId = 0;
	/**	Context instance	*/
	private static CopyContextUtil instance;
	
	/**
	 * Single instance
	 * @return
	 */
	public static CopyContextUtil newInstance() {
		if(instance == null) {
			instance = new CopyContextUtil();
		}
		return instance;
	}
	
	/**
	 * Set context to new Client
	 */
	public void updateContextToNewClient(Properties context, int targetClientId, boolean isBatch) {
		Env.setContext(context, WINDOW_THIS_PROCESS, "AD_Client_ID", targetClientId);
		Env.setContext(context, "#AD_Client_ID", targetClientId);
	}
	
	/**
	 * Revert context
	 */
	public void revertContextToCurrentClient(Properties context, boolean isBatch) {
		if(isBatch) {
			return;
		}
		Env.setContext(context, WINDOW_THIS_PROCESS, "AD_Client_ID", currentClientId);
		Env.setContext(context, "#AD_Client_ID", currentClientId);
	}
}

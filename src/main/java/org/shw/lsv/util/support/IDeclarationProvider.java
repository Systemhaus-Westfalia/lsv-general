/*************************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                              *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com                                      *
 * This program is free software: you can redistribute it and/or modify              *
 * it under the terms of the GNU General Public License as published by              *
 * the Free Software Foundation, either version 3 of the License, or                 *
 * (at your option) any later version.                                               *
 * This program is distributed in the hope that it will be useful,                   *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                     *
 * GNU General Public License for more details.                                      *
 * You should have received a copy of the GNU General Public License                 *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.            *
 ************************************************************************************/
package org.shw.lsv.util.support;
import org.compiere.model.PO;
import org.spin.util.support.IAppSupport;

/**
 * 	Wrapper for Declaration Provider
 * 	@author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public interface IDeclarationProvider extends IAppSupport {
	
	/**
	 * Publish a document for this provider
	 * @param document
	 * @throws Exception 
	 */
	public String publishDocument(PO document) throws Exception;
	
	
	
	/**
	 * Get declaration document based on entity
	 * @param entity
	 * @return
	 */
	public IDeclarationDocument getDeclarationDocument(PO entity);
}

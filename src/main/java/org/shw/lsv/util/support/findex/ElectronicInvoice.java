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
package org.shw.lsv.util.support.findex;

import org.adempiere.core.domains.models.X_E_InvoiceElectronic;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrgInfo;
import org.shw.lsv.einvoice.factory.AnulacionFactory;
import org.shw.lsv.einvoice.factory.CreditoFiscalFactory;
import org.shw.lsv.einvoice.factory.FacturaExportacionFactory;
import org.shw.lsv.einvoice.factory.FacturaFactory;
import org.shw.lsv.einvoice.factory.FacturaSujetoExcluidoFactory;
import org.shw.lsv.einvoice.factory.NotaDeCreditoFactory;
import org.shw.lsv.einvoice.factory.RetencionFactory;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.util.support.IDeclarationDocument;

/**
 * A implementation for Invoice using Electronic Invoice LSV
 * @author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class ElectronicInvoice implements IDeclarationDocument {

	private MClient	client = null;
	private MOrgInfo orgInfo = null;
	private MInvoice invoice;
	private EDocumentFactory documentFactory = null;
	private X_E_InvoiceElectronic electronicInvoiceModel = null;
	private String errorMsg = null;
	
	public ElectronicInvoice(MInvoice document) {
		this.invoice = document;
	}
	
	@Override
	public X_E_InvoiceElectronic processElectronicInvoice() throws Exception {
		System.out.println("ElectronicInvoice.processElectronicInvoice(): Started with Invoice " + invoice.getDocumentNo());
		Boolean iscorrectDocType = 
				invoice.getC_DocType().getE_DocType_ID()>0 ;
		if (!iscorrectDocType) {
			errorMsg = "El documento " + invoice.getDocumentNo() + " no es Factura, Credito Fiscal, Nota de Credito u otro documento permitido. Aquí se interrumpe el proceso";
			System.out.println(errorMsg);
			System.out.println("****************** ElectronicInvoice.processElectronicInvoice(): finished with errors");
			return null;
		}
		boolean isreversal = ((invoice.getDocStatus().equals("VO")) || invoice.getDocStatus().equals("RE"))
				&& invoice.getReversal_ID() > 0
				&& invoice.getReversal_ID() < invoice.getC_Invoice_ID();
				
		boolean existsWithholding = false;	
		client = new MClient(invoice.getCtx(), invoice.getAD_Client_ID(), invoice.get_TrxName());
		int orgID = invoice.getAD_Org_ID();		
		orgInfo= MOrgInfo.get(invoice.getCtx(), orgID, invoice.get_TrxName());
		documentFactory = getDocumentFactory(invoice, isreversal, existsWithholding);
		if (documentFactory == null) {
			errorMsg = "El documento " + invoice.getDocumentNo() + " no pertenece a un tipo de documento valido: " + invoice.getC_DocType().getE_DocType().getValue() ;
			System.out.println("****************** Error producido en ElectronicInvoice.processElectronicInvoice(): " + errorMsg);
			return null;
		}
		System.out.println("Start documentFactory.generateJSONInputData() for invoice " + invoice.getDocumentNo() );
		documentFactory.generateJSONInputData();
		System.out.println("End documentFactory.generateJSONInputData() for invoice " + invoice.getDocumentNo() );
		System.out.println("Start documentFactory.generateEDocument() for invoice " + invoice.getDocumentNo() );
		documentFactory.generateEDocument();
		System.out.println("End documentFactory.generateEDocument() for invoice " + invoice.getDocumentNo() );	
		
    	electronicInvoiceModel = new X_E_InvoiceElectronic(invoice.getCtx(), 0, invoice.get_TrxName());
    	electronicInvoiceModel.setC_Invoice_ID(invoice.getC_Invoice_ID());
    	electronicInvoiceModel.setei_ValidationStatus("01");
    	if (documentFactory.getEDocumentErrorMessages().length() > 0) {
			errorMsg = documentFactory.getEDocumentErrorMessages().toString();
    		electronicInvoiceModel.seterrMsgIntern(errorMsg);
    		electronicInvoiceModel.setei_ValidationStatus("02");
    		invoice.setei_ValidationStatus("02");
        	electronicInvoiceModel.saveEx();
        	invoice.saveEx();
			System.out.println("****************** ElectronicInvoice.processElectronicInvoice(): produced the following errors:");
			System.out.println(errorMsg);
			System.out.println("ElectronicInvoice.processElectronicInvoice(): finished");
    		return null;
    	}	

		System.out.println("Start documentFactory.createJsonString() for invoice " + invoice.getDocumentNo() );
    	String creditoFiscalAsJsonString = documentFactory.createJsonString();
		System.out.println("End documentFactory.createJsonString() for invoice " + invoice.getDocumentNo() );
    	String ei_codigoGeneracion = documentFactory.getCodigoGeneracion(creditoFiscalAsJsonString);
    	String ei_numeroControl = "";

		System.out.println("Start " + invoice.getDocumentNo() + " Update ei values" );
    	if (!isreversal) {
    		ei_numeroControl = documentFactory.getNumeroControl(creditoFiscalAsJsonString);
        	invoice.setei_numeroControl(ei_numeroControl);
    	}
    	
    	invoice.setei_codigoGeneracion(ei_codigoGeneracion);
    	invoice.setei_ValidationStatus("01");
    	invoice.saveEx();
		System.out.println("End " + invoice.getDocumentNo() + " Update ei values" );
       	electronicInvoiceModel.setjson(creditoFiscalAsJsonString);
		System.out.println("Start electronicInvoiceModel " + invoice.getDocumentNo() + " Update ei values" );
    	electronicInvoiceModel.saveEx();
    	System.out.println("Documento electrónico generado para: " + invoice.getDocumentNo() + ". Estado: " + electronicInvoiceModel.getei_ValidationStatus());
		System.out.println("ElectronicInvoice.processElectronicInvoice(): finished");
		
		return electronicInvoiceModel;
	}

	private EDocumentFactory getDocumentFactory(MInvoice invoice, boolean isreversal, boolean existsWithholding) {
		EDocumentFactory documentFactory = null;
		if (isreversal) {
			documentFactory = new AnulacionFactory(invoice.get_TrxName(), invoice.getCtx(), client, orgInfo, invoice);
			System.out.println("Se procesa el tipo de documento 'Anulacion'");
		} else if (existsWithholding) {
			documentFactory = new RetencionFactory(invoice.get_TrxName(), invoice.getCtx(), client, orgInfo, invoice);
			System.out.println("Se procesa el tipo de documento 'Retencion'");
		} else if (invoice.getC_DocType().getE_DocType().getValue().equals("03")) {		//Credito Fiscal
			documentFactory = new CreditoFiscalFactory(invoice.get_TrxName(), invoice.getCtx(), client, orgInfo, invoice);
			System.out.println("Se procesa el tipo de documento 'Credito Fiscal'");
		} else if (invoice.getC_DocType().getE_DocType().getValue().equals("05")) {		//Nota de Credito
			documentFactory = new NotaDeCreditoFactory(invoice.get_TrxName(), invoice.getCtx(), client, orgInfo, invoice);
			System.out.println("Se procesa el tipo de documento 'Nota de Credito'");
		} else if (invoice.getC_DocType().getE_DocType().getValue().equals("01")) {		//Factura Comsumidor Final
			documentFactory = new FacturaFactory(invoice.get_TrxName(), invoice.getCtx(), client, orgInfo, invoice);
			System.out.println("Se procesa el tipo de documento 'Factura'");
		} else if (invoice.getC_DocType().getE_DocType().getValue().equals("11")) {		//Factura Exportacion
			documentFactory = new FacturaExportacionFactory(invoice.get_TrxName(), invoice.getCtx(), client, orgInfo, invoice);
			System.out.println("Se procesa el tipo de documento 'Factura de Exportacion'");
		} else if (invoice.getC_DocType().getE_DocType().getValue().equals("14")) {		// Factura Sujeto Excluido
			documentFactory = new FacturaSujetoExcluidoFactory(invoice.get_TrxName(), invoice.getCtx(), client, orgInfo, invoice);
			System.out.println("Se procesa el tipo de documento 'Sujeto Excluido'");
		}
		return documentFactory;
	}

	public EDocumentFactory getDocumentFactory() {
		return documentFactory;
	}

	public X_E_InvoiceElectronic getElectronicInvoiceModel() {
		return electronicInvoiceModel;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
	
}

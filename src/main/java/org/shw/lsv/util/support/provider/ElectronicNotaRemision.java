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
package org.shw.lsv.util.support.provider;

import org.adempiere.core.domains.models.X_E_DocType;
import org.adempiere.core.domains.models.X_E_InOutElectronic;
import org.adempiere.core.domains.models.X_E_InvoiceElectronic;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MOrgInfo;
import org.compiere.util.Env;
import org.shw.lsv.einvoice.factory.NotaRemisionFactory;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.util.support.IDeclarationDocument;

/**
 * A implementation for Invoice using Electronic Invoice LSV
 * @author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class ElectronicNotaRemision implements IDeclarationDocument {

	private MClient	client = null;
	private MOrgInfo orgInfo = null;
	private MInOut inOut = null;
	private EDocumentFactory documentFactory = null;
	private X_E_InOutElectronic electronicInOutModel = null;
	private String errorMsg = null;
	private X_E_DocType e_DocType = null;
	


	@Override
	public X_E_InvoiceElectronic processElectronicInvoice() throws Exception {
		return null;
	}
	
	public ElectronicNotaRemision(MInOut document) {
		this.inOut= document;
		MDocType docType = (MDocType)inOut.getC_DocType();
		e_DocType = new X_E_DocType(Env.getCtx()	, docType.get_ValueAsInt(X_E_DocType.COLUMNNAME_E_DocType_ID), null);
	}
	
	public X_E_InOutElectronic processElectronicInOut() throws Exception {
		System.out.println("ElectronicInvoice.processElectronicInvoice(): Started with Invoice " + inOut.getDocumentNo());
		Boolean iscorrectDocType = 
				e_DocType.getE_DocType_ID()>0 ;
		if (!iscorrectDocType) {
			errorMsg = "El documento " + inOut.getDocumentNo() + " no es Factura, Credito Fiscal, Nota de Credito u otro documento permitido. Aquí se interrumpe el proceso";
			System.out.println(errorMsg);
			System.out.println("****************** ElectronicInvoice.processElectronicInvoice(): finished with errors");
			return null;
		}
		boolean isreversal = ((inOut.getDocStatus().equals("VO")) || inOut.getDocStatus().equals("RE") || inOut.getDocStatus().equals("CO"))
				&& inOut.getReversal_ID() > 0
				&& inOut.getReversal_ID() < inOut.getC_Invoice_ID();
				

		boolean isContingencia = false;
		isContingencia = inOut.get_ValueAsBoolean("isContingencia");
		if (isContingencia)
			isreversal = false;
		client = new MClient(inOut.getCtx(), inOut.getAD_Client_ID(), inOut.get_TrxName());
		int orgID = inOut.getAD_Org_ID();		
		orgInfo= MOrgInfo.get(inOut.getCtx(), orgID, inOut.get_TrxName());
		documentFactory = getDocumentFactoryRemision(inOut, isreversal, isContingencia);
		if (documentFactory == null) {
			errorMsg = "El documento " + inOut.getDocumentNo() + " no pertenece a un tipo de documento valido: " + e_DocType.getValue() ;
			System.out.println("****************** Error producido en ElectronicInvoice.processElectronicInvoice(): " + errorMsg);
			return null;
		}
		System.out.println("Start documentFactory.generateJSONInputData() for invoice " + inOut.getDocumentNo() );
		documentFactory.generateJSONInputData();
		System.out.println("End documentFactory.generateJSONInputData() for invoice " + inOut.getDocumentNo() );
		System.out.println("Start documentFactory.generateEDocument() for invoice " + inOut.getDocumentNo() );
		documentFactory.generateEDocument();
		System.out.println("End documentFactory.generateEDocument() for invoice " + inOut.getDocumentNo() );	
		
    	electronicInOutModel = new X_E_InOutElectronic(inOut.getCtx(), 0, inOut.get_TrxName());
    	electronicInOutModel.setM_InOut_ID(inOut.getM_InOut_ID());
    	electronicInOutModel.setei_ValidationStatus("01");
    	if (documentFactory.getEDocumentErrorMessages().length() > 0) {
			errorMsg = documentFactory.getEDocumentErrorMessages().toString();
			electronicInOutModel.seterrMsgIntern(errorMsg);
			electronicInOutModel.setei_ValidationStatus("02");
    		inOut.set_ValueOfColumn("ei_ValidationStatus",  "02"); 
    		electronicInOutModel.saveEx();
        	inOut.saveEx();
			System.out.println("****************** ElectronicInvoice.processElectronicInvoice(): produced the following errors:");
			System.out.println(errorMsg);
			System.out.println("ElectronicInvoice.processElectronicInvoice(): finished");
    		return null;
    	}	

		System.out.println("Start documentFactory.createJsonString() for invoice " + inOut.getDocumentNo() );
    	String eInvoiceAsJsonString = documentFactory.createJsonString();
		System.out.println("End documentFactory.createJsonString() for invoice " + inOut.getDocumentNo() );
		
		System.out.println("Start documentFactory.generateSignature() for invoice " + inOut.getDocumentNo() );
		//SignatureGenerationAPI signatureAPI = new SignatureGenerationAPI(client, invoice.getDocumentNo(), eInvoiceAsJsonString);
    	//String result = documentFactory.generateSignature(signatureAPI);

		//if (result!="") {  // ProcessBuilder muss Status==leere Zeichenkette zurück liefern
		//	System.out.println("documentFactory.generateSignature() for invoice " + invoice.getDocumentNo() + "EXITED WITH ERROR");
		//	System.out.println(result);
		//}
		//System.out.println("End documentFactory.generateSignature() for invoice " + invoice.getDocumentNo() );
		
    	String ei_codigoGeneracion = documentFactory.getCodigoGeneracion(eInvoiceAsJsonString);
    	String ei_numeroControl = "";

		System.out.println("Start " + inOut.getDocumentNo() + " Update ei values" );
    	if (!isreversal) {
    		ei_numeroControl = documentFactory.getNumeroControl(eInvoiceAsJsonString);
    		inOut.set_ValueOfColumn("ei_numeroControl", ei_numeroControl); 
    	}
    	
    	inOut.set_ValueOfColumn("ei_numeroControl", ei_numeroControl);
    	inOut.set_ValueOfColumn("ei_codigoGeneracion", ei_codigoGeneracion);
    	
    	inOut.set_ValueOfColumn("ei_ValidationStatus",  "01");
    	inOut.saveEx();
		System.out.println("End of " + inOut.getDocumentNo() + " Update ei values" );
       	electronicInOutModel.setjson(eInvoiceAsJsonString);
       	electronicInOutModel.set_ValueOfColumn("ei_Signature", documentFactory.getSignature());
		System.out.println("Start electronicInvoiceModel " + inOut.getDocumentNo() + " Update ei values" );
		electronicInOutModel.saveEx();
    	System.out.println("Documento electrónico generado para: " + inOut.getDocumentNo() + ". Estado: " + electronicInOutModel.getei_ValidationStatus());
		System.out.println("ElectronicInvoice.processElectronicInvoice(): finished");
		
		return electronicInOutModel;
	}

	
	
	private EDocumentFactory getDocumentFactoryRemision(MInOut inOut, boolean isreversal, boolean isContingencia) {
		EDocumentFactory documentFactory = null;
		//if (isreversal) {
		//	documentFactory = new AnulacionFactory(invoice.get_TrxName(), invoice.getCtx(), client, orgInfo, invoice);
			System.out.println("Se procesa el tipo de documento 'Anulacion'");
		//} else if (existsWithholding) {
		//}
	if (e_DocType.getValue().equals("04")) {		//Credito Fiscal
			documentFactory = new NotaRemisionFactory(inOut.get_TrxName(), inOut.getCtx(), client, orgInfo, inOut);
			System.out.println("Se procesa el tipo de documento 'Credito Fiscal'");
		} 
		return documentFactory;
	}

	public EDocumentFactory getDocumentFactory() {
		return documentFactory;
	}

	public X_E_InOutElectronic getElectronicInvoiceModel() {
		return electronicInOutModel;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
	
	
}

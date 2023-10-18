package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.json.JSONObject;
import org.shw.lsv.einvoice.anulacionv2.Anulacion;
import org.shw.lsv.einvoice.anulacionv2.DocumentoAnulacion;
import org.shw.lsv.einvoice.anulacionv2.EmisorAnulacion;
import org.shw.lsv.einvoice.anulacionv2.IdentificacionAnulacion;
import org.shw.lsv.einvoice.anulacionv2.MotivoAnulacion;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AnulacionFactory extends EDocumentFactory {
	Anulacion anulacion;
	MInvoice invoice;
	String codigoGeneracion = "";
	
	public AnulacionFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo, MInvoice invoice) {
		super(trxName, contextProperties, client, orgInfo);
		this.invoice = invoice;
	}

	@Override
	public Anulacion generateEDocument() {
		System.out.println("Anulacion: start generating and filling the Document");
		String result="";
		anulacion = new Anulacion();

		System.out.println("Instatiate, fill and verify Identificacion");
		IdentificacionAnulacion identification = anulacion.getIdentificacion();
		if(identification!=null) {
			anulacion.errorMessages.append(anulacion.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				anulacion.errorMessages.append(result);
			}
		}

		System.out.println("Instatiate, fill and verify Emisor");
		EmisorAnulacion emisor = anulacion.getEmisor();
		if(emisor!=null) {
			anulacion.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				anulacion.errorMessages.append(result);
			}
		}

		System.out.println("Instatiate, fill and verify Documento");
		DocumentoAnulacion documento = anulacion.getDocumento();
		if(documento!=null) {
			anulacion.fillDocumento(jsonInputToFactory);
			result = documento.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				anulacion.errorMessages.append(result);
			}
		}
//		
		System.out.println("Instatiate, fill and verify Motivo");
		MotivoAnulacion motivo = anulacion.getMotivo();
		if(documento!=null) {
			anulacion.fillMotivo(jsonInputToFactory);
			result = motivo.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				anulacion.errorMessages.append(result);
			}
		}

		anulacion.validateValues();
		if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
			anulacion.errorMessages.append(result);
		}

		System.out.println("Anulacion: end generating and filling the Document");
		return anulacion;
	}

	@Override
	public void generateJSONInputData() {
		System.out.println("Anulacion: start collecting JSON data for all components");
		jsonInputToFactory = new JSONObject();

		jsonInputToFactory.put(Anulacion.IDENTIFICACION, generateIdentificationInputData());
		jsonInputToFactory.put(Anulacion.EMISOR, generateEmisorInputData());
		jsonInputToFactory.put(Anulacion.DOCUMENTO, generateDocumentoInputData());
		jsonInputToFactory.put(Anulacion.MOTIVO, generateMotivoInputData());
		
		System.out.println("Generated JSON object from Invoice:");
		System.out.println(jsonInputToFactory.toString());
		System.out.println("Anulacion: end collecting JSON data for all components");
	}
	
	private JSONObject generateIdentificationInputData() {
		System.out.println("Factura: start collecting JSON data for Identificacion");

		String prefix = invoice.getC_DocType().getDefiniteSequence().getPrefix();
		String documentno = invoice.getDocumentNo().replace(prefix,"");
		documentno = documentno.replace("^","");
		int position = documentno.indexOf("_");
		if (position >= 0)
		documentno = documentno.substring(0,position);
		
		Integer invoiceID = invoice.get_ID();
		Integer clientID = (Integer)client.getAD_Client_ID();
		codigoGeneracion = StringUtils.leftPad(clientID.toString(), 8, "0") + "-0000-0000-0000-" + StringUtils.leftPad(invoiceID.toString(), 12,"0");
		
		JSONObject jsonObjectIdentificacion = new JSONObject();

		jsonObjectIdentificacion.put(Anulacion.AMBIENTE, client.getE_Enviroment().getValue());
		jsonObjectIdentificacion.put(Anulacion.CODIGOGENERACION, codigoGeneracion);
		jsonObjectIdentificacion.put(Anulacion.FECANULA, invoice.getDateAcct().toString().substring(0, 10));
		jsonObjectIdentificacion.put(Anulacion.HORANULA, "00:00:00");
		
		System.out.println("Factura: end collecting JSON data for Identificacion");
		return jsonObjectIdentificacion;	
	}
	
	private JSONObject generateEmisorInputData() {
		System.out.println("Factura: start collecting JSON data for Emisor");
		
		JSONObject jsonObjectEmisor = new JSONObject();
		jsonObjectEmisor.put(Anulacion.NIT, orgInfo.getTaxID().replace("-", ""));
		jsonObjectEmisor.put(Anulacion.NOMBRE, client.getName());
		jsonObjectEmisor.put(Anulacion.TIPOESTABLECIMIENTO, client.getE_PlantType().getValue());
		jsonObjectEmisor.put(Anulacion.NOMESTABLECIMIENTO, client.getE_PlantType().getName());						// TODO: korrekte Daten einsetzen
		//jsonObjectEmisor.put(Anulacion.CODESTABLEMH, "");								// TODO: korrekte Daten einsetzen
		//jsonObjectEmisor.put(Anulacion.CODESTABLE, client.getE_PlantType().getValue());								// TODO: korrekte Daten einsetzen
		//jsonObjectEmisor.put(Anulacion.CODPUNTOVENTAMH, "");							// TODO: korrekte Daten einsetzen
		//jsonObjectEmisor.put(Anulacion.CODPUNTOVENTA, "");							// TODO: korrekte Daten einsetzen
		jsonObjectEmisor.put(Anulacion.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectEmisor.put(Anulacion.CORREO, client.getEMail());

		System.out.println("Factura: end collecting JSON data for Emisor");
		return jsonObjectEmisor;	
	}
	

	private JSONObject generateDocumentoInputData() {
		System.out.println("Start collecting JSON data for Documento");
		
		JSONObject jsonObjectDocumento = new JSONObject();
		jsonObjectDocumento.put(Anulacion.TIPODTE, invoice.getReversal().getC_DocType().getE_DocType().getValue());			
		jsonObjectDocumento.put(Anulacion.CODIGOGENERACION, invoice.getReversal().getei_codigoGeneracion());		
		jsonObjectDocumento.put(Anulacion.SELLORECIBIDO, invoice.getReversal().getei_selloRecibido());			// TODO: korrekte Daten einsetzen
		jsonObjectDocumento.put(Anulacion.NUMEROCONTROL, invoice.getReversal().getei_numeroControl());			
		jsonObjectDocumento.put(Anulacion.FECEMI, invoice.getReversal().getDateAcct().toString().substring(0, 10));
		jsonObjectDocumento.put(Anulacion.CODIGOGENERACIONR, codigoGeneracion);		
		jsonObjectDocumento.put(Anulacion.TIPODOCUMENTO, invoice.getReversal().getC_BPartner().getE_Recipient_Identification().getValue());
		String numDocumento = invoice.getC_BPartner().getTaxID();
		if (!invoice.getC_BPartner().getE_Recipient_Identification().getValue().equals("36"))
			numDocumento = invoice.getC_BPartner().getDUNS();
		jsonObjectDocumento.put(Anulacion.NUMDOCUMENTO, numDocumento);			
		jsonObjectDocumento.put(Anulacion.NOMBRE, invoice.getReversal().getC_BPartner().getName());
		String phone = invoice.getReversal().getC_BPartner().getPhone().replace("-", "").trim();
		phone = phone.length()==8?phone:"";
		jsonObjectDocumento.put(Anulacion.TELEFONO, phone);
		jsonObjectDocumento.put(Anulacion.CORREO, invoice.getReversal().getC_BPartner().getEMail());
		
		BigDecimal montoIVA = Env.ZERO;
		List<MInvoiceTax> invoiceTaxes = new Query(contextProperties , MInvoiceTax.Table_Name , "C_Invoice_ID=?" , trxName)
				.setParameters(invoice.getC_Invoice_ID())
				.list();
		for (MInvoiceTax invoiceTax:invoiceTaxes) {
			if (invoiceTax.getC_Tax().getTaxIndicator().equals("IVA"))
				montoIVA = montoIVA.add(invoiceTax.getTaxAmt().multiply(new BigDecimal(-1)));
		}
		jsonObjectDocumento.put(Anulacion.MONTOIVA, montoIVA);				

		System.out.println("Finish collecting JSON data for Documento");
		return jsonObjectDocumento;		
	}
	
	private JSONObject generateMotivoInputData() {
		System.out.println("Start collecting JSON data for Motivo");
		MUser user = new MUser(contextProperties, invoice.getCreatedBy(), trxName);
		
		JSONObject jsonObjectMotivo = new JSONObject();
		jsonObjectMotivo.put(Anulacion.TIPOANULACION, 3);		
		jsonObjectMotivo.put(Anulacion.MOTIVOANULACION, "Error en generacion");		
		jsonObjectMotivo.put(Anulacion.NOMBRERESPONSABLE, user.getC_BPartner().getName());	
		jsonObjectMotivo.put(Anulacion.TIPDOCRESPONSABLE, user.getC_BPartner().getE_Recipient_Identification().getValue());	
		jsonObjectMotivo.put(Anulacion.NUMDOCRESPONSABLE, user.getC_BPartner().getTaxID().replace("-", ""));	
		jsonObjectMotivo.put(Anulacion.NOMBRESOLICITA, user.getC_BPartner().getName());		// TODO: korrekte Daten einsetzen
		jsonObjectMotivo.put(Anulacion.TIPDOCSOLICITA, user.getC_BPartner().getE_Recipient_Identification().getValue());		// TODO: korrekte Daten einsetzen
		jsonObjectMotivo.put(Anulacion.NUMDOCSOLICITA, user.getC_BPartner().getTaxID().replace("-", ""));		// TODO: korrekte Daten einsetzen

		System.out.println("Finish collecting JSON data for Maotivo");
		return jsonObjectMotivo;
		
	}
	
	
	public String createJsonString() throws Exception {
		System.out.println("Anulacion: start generating JSON object from Document");
		ObjectMapper objectMapper  = new ObjectMapper();
		String anulacionAsString   = objectMapper.writeValueAsString(anulacion);
		JSONObject anulacionAsJson = new JSONObject(anulacionAsString);

		anulacionAsJson.remove(Anulacion.ERRORMESSAGES);

		// Manipulate generated JSON string
		String anulacionAsStringFinal = anulacionAsJson.toString().
				replace(":[],", ":null,").
				replace("\"documentoRelacionado\":[]", "\"documentoRelacionado\":null").
				replace("\"ventaTercero\":{\"nit\":null,\"nombre\":null},", "\"ventaTercero\":null,").
				replace("\"tributos\":[{\"descripcion\":null,\"codigo\":null,\"valor\":null}]", "\"tributos\":null").
				replace("\"extension\":{\"docuEntrega\":null,\"placaVehiculo\":null,\"observaciones\":null,\"nombRecibe\":null,\"nombEntrega\":null,\"docuRecibe\":null},", 
						"\"extension\":null,");

		System.out.println("Anulacion: generated JSON object from Document:");
		System.out.println(anulacionAsStringFinal);
		System.out.println("Anulacion: end generating JSON object from Document");
		return anulacionAsStringFinal;	
	}

	public String getNumeroControl(Integer id, MOrgInfo orgInfo, String prefix) {
		String idIdentification  = StringUtils.leftPad(id.toString(), 15,"0");
		String duns = orgInfo.getDUNS().replace("-", "");
		String numeroControl = prefix + StringUtils.leftPad(duns.trim(), 8,"0") + "-"+ idIdentification;
		return numeroControl;
	}
	

	@Override
	public StringBuffer getEDocumentErrorMessages() {
		 return anulacion.errorMessages;
	 }
}

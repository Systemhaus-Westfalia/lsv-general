package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.core.domains.models.X_E_Recipient_Identification;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
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

		System.out.println("Instantiate, fill and verify Identificacion");
		IdentificacionAnulacion identification = anulacion.getIdentificacion();
		if(identification!=null) {
			anulacion.errorMessages.append(anulacion.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				anulacion.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Emisor");
		EmisorAnulacion emisor = anulacion.getEmisor();
		if(emisor!=null) {
			anulacion.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				anulacion.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Documento");
		DocumentoAnulacion documento = anulacion.getDocumento();
		if(documento!=null) {
			anulacion.fillDocumento(jsonInputToFactory);
			result = documento.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				anulacion.errorMessages.append(result);
			}
		}
//		
		System.out.println("Instantiate, fill and verify Motivo");
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
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String horEmi = timeFormat.format(cal.getTime());
		String dateEmi = dateFormat.format(cal);
		jsonObjectIdentificacion.put(Anulacion.AMBIENTE, client_getE_Enviroment(client).getValue());
		jsonObjectIdentificacion.put(Anulacion.CODIGOGENERACION, codigoGeneracion);
		jsonObjectIdentificacion.put(Anulacion.FECANULA, dateEmi);
		jsonObjectIdentificacion.put(Anulacion.HORANULA, horEmi);
		
		System.out.println("Factura: end collecting JSON data for Identificacion");
		return jsonObjectIdentificacion;	
	}
	
	private JSONObject generateEmisorInputData() {
		System.out.println("Factura: start collecting JSON data for Emisor");
		
		JSONObject jsonObjectEmisor = new JSONObject();
		jsonObjectEmisor.put(Anulacion.NIT, orgInfo.getTaxID().replace("-", ""));
		jsonObjectEmisor.put(Anulacion.NOMBRE, client.getDescription());
		jsonObjectEmisor.put(Anulacion.TIPOESTABLECIMIENTO, client_getE_PlantType(client).getValue());
		jsonObjectEmisor.put(Anulacion.NOMESTABLECIMIENTO, client.getName());						// TODO: korrekte Daten einsetzen
		//jsonObjectEmisor.put(Anulacion.CODESTABLEMH, "");								// TODO: korrekte Daten einsetzen
		//jsonObjectEmisor.put(Anulacion.CODESTABLE, client.getE_PlantType().getValue());								// TODO: korrekte Daten einsetzen
		//jsonObjectEmisor.put(Anulacion.CODPUNTOVENTAMH, "");							// TODO: korrekte Daten einsetzen
		//jsonObjectEmisor.put(Anulacion.CODPUNTOVENTA, "");							// TODO: korrekte Daten einsetzen
		jsonObjectEmisor.put(Anulacion.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectEmisor.put(Anulacion.CORREO, client_getEmail(client));

		System.out.println("Factura: end collecting JSON data for Emisor");
		return jsonObjectEmisor;	
	}
	

	private JSONObject generateDocumentoInputData() {
		System.out.println("Start collecting JSON data for Documento");
		
		JSONObject jsonObjectDocumento = new JSONObject();
		jsonObjectDocumento.put(Anulacion.TIPODTE, docType_getE_DocType((MDocType)invoice.getReversal().getC_DocType()).getValue());					
		jsonObjectDocumento.put(Anulacion.CODIGOGENERACION, invoice_ei_codigoGeneracion((MInvoice)invoice.getReversal()));		
		jsonObjectDocumento.put(Anulacion.SELLORECIBIDO, invoice_ei_selloRecibido((MInvoice)invoice.getReversal()));			
		jsonObjectDocumento.put(Anulacion.NUMEROCONTROL, invoice_ei_numeroControl((MInvoice)invoice.getReversal()));
			
		jsonObjectDocumento.put(Anulacion.FECEMI, invoice.getReversal().getDateAcct().toString().substring(0, 10));
		X_E_Recipient_Identification recipient_Identification =  bPartner_getE_Recipient_Identification((MBPartner)invoice.getReversal().getC_BPartner());
		//jsonObjectDocumento.put(Anulacion.CODIGOGENERACIONR, codigoGeneracion);		
		jsonObjectDocumento.put(Anulacion.TIPODOCUMENTO, recipient_Identification.getValue());
		String numDocumento = invoice.getC_BPartner().getTaxID().replace("-", "");
		if (!recipient_Identification.getValue().equals("36"))
			numDocumento = invoice.getC_BPartner().getDUNS();
		jsonObjectDocumento.put(Anulacion.NUMDOCUMENTO, numDocumento);			
		jsonObjectDocumento.put(Anulacion.NOMBRE, invoice.getReversal().getC_BPartner().getName());
		String phone = bPartner_getPhone((MBPartner)invoice.getReversal().getC_BPartner()).replace("-", "").trim();
		phone = phone.length()==8?phone:"";
		jsonObjectDocumento.put(Anulacion.TELEFONO, phone);
		jsonObjectDocumento.put(Anulacion.CORREO, bPartner_getEmail((MBPartner)invoice.getReversal().getC_BPartner()));
		
		BigDecimal montoIVA = Env.ZERO;
		List<MInvoiceTax> invoiceTaxes = new Query(contextProperties , MInvoiceTax.Table_Name , "C_Invoice_ID=?" , trxName)
				.setParameters(invoice.getC_Invoice_ID())
				.list();
		for (MInvoiceTax invoiceTax:invoiceTaxes) {
			if (invoiceTax.getC_Tax().getTaxIndicator().equals("IVA"))
				montoIVA = montoIVA.add(invoiceTax.getTaxAmt().negate());
		}
		jsonObjectDocumento.put(Anulacion.MONTOIVA, montoIVA);				

		System.out.println("Finish collecting JSON data for Documento");
		return jsonObjectDocumento;		
	}
	
	private JSONObject generateMotivoInputData() {
		System.out.println("Start collecting JSON data for Motivo");
		MUser user = new MUser(contextProperties, invoice.getCreatedBy(), trxName);
		
		JSONObject jsonObjectMotivo = new JSONObject();
		jsonObjectMotivo.put(Anulacion.TIPOANULACION, 2);		
		jsonObjectMotivo.put(Anulacion.MOTIVOANULACION, "Rescindir de la operacion realizada.");		
		jsonObjectMotivo.put(Anulacion.NOMBRERESPONSABLE, client.getDescription());	
		jsonObjectMotivo.put(Anulacion.TIPDOCRESPONSABLE, "36");	
		jsonObjectMotivo.put(Anulacion.NUMDOCRESPONSABLE, orgInfo.getTaxID().replace("-", ""));	
		jsonObjectMotivo.put(Anulacion.NOMBRESOLICITA, invoice.getC_BPartner().getName());		
		jsonObjectMotivo.put(Anulacion.TIPDOCSOLICITA, bPartner_getE_Recipient_Identification((MBPartner)invoice.getC_BPartner()).getValue());		// TODO: korrekte Daten einsetzen
		jsonObjectMotivo.put(Anulacion.NUMDOCSOLICITA, invoice.getC_BPartner().getTaxID().replace("-", ""));		// TODO: korrekte Daten einsetzen

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

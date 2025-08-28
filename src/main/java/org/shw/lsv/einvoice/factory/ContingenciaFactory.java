package org.shw.lsv.einvoice.factory;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.core.domains.models.X_E_Contingency;
import org.adempiere.core.domains.models.X_E_DocType;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MUser;
import org.compiere.util.Env;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.contingencia.Contingencia;
import org.shw.lsv.einvoice.contingencia.DetalleDTEItemContingencia;
import org.shw.lsv.einvoice.contingencia.EmisorContingencia;
import org.shw.lsv.einvoice.contingencia.IdentificacionContingencia;
import org.shw.lsv.einvoice.contingencia.MotivoContingencia;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ContingenciaFactory extends EDocumentFactory {
	Contingencia contingencia;
	MInvoice invoice;
	String codigoGeneracion = "";
	
	public ContingenciaFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo, MInvoice invoice) {
		super(trxName, contextProperties, client, orgInfo);
		this.invoice = invoice;
	}

	@Override
	public Contingencia generateEDocument() {
		System.out.println("Anulacion: start generating and filling the Document");
		String result="";
		contingencia = new Contingencia();

		System.out.println("Instantiate, fill and verify Identificacion");
		IdentificacionContingencia identification = contingencia.getIdentificacion();
		if(identification!=null) {
			contingencia.errorMessages.append(contingencia.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				contingencia.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Emisor");
		EmisorContingencia emisor = contingencia.getEmisor();
		if(emisor!=null) {
			contingencia.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				contingencia.errorMessages.append(result);
			}
		}
		List<DetalleDTEItemContingencia> detalleDTEs = contingencia.getDetalleDTE();
		if(detalleDTEs!=null) {
			contingencia.fillDetalleDTE(jsonInputToFactory);			
		}
		
//		
		System.out.println("Instantiate, fill and verify Motivo");
		MotivoContingencia motivo = contingencia.getMotivo();
		if(invoice!=null) {
			contingencia.fillMotivo(jsonInputToFactory);
			result = motivo.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				contingencia.errorMessages.append(result);
			}
		}

		contingencia.validateValues();
		if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
			contingencia.errorMessages.append(result);
		}

		System.out.println("Anulacion: end generating and filling the Document");
		return contingencia;
	}

	@Override
	public void generateJSONInputData() {
		System.out.println("Contingencia: start collecting JSON data for all components");
		jsonInputToFactory = new JSONObject();

		jsonInputToFactory.put(Contingencia.IDENTIFICACION, generateIdentificationInputData());
		jsonInputToFactory.put(Contingencia.EMISOR, generateEmisorInputData());
		jsonInputToFactory.put(Contingencia.DETALLEDTE, generateDetalleDTE());
		jsonInputToFactory.put(Contingencia.MOTIVO, generateMotivoInputData());
		
		System.out.println("Generated JSON object from Invoice:");
		System.out.println(jsonInputToFactory.toString());
		System.out.println("Anulacion: end collecting JSON data for all components");
	}
	
	private JSONObject generateIdentificationInputData() {
		System.out.println("Factura: start collecting JSON data for Identificacion");
		codigoGeneracion = createCodigoGeneracion(invoice);
		
		JSONObject jsonObjectIdentificacion = new JSONObject();
		String hTransmision = gethorEmi();
		String fTransmision = getfecEmi();
		jsonObjectIdentificacion.put(Contingencia.AMBIENTE, client_getE_Enviroment(client).getValue());
		jsonObjectIdentificacion.put(Contingencia.CODIGOGENERACION, codigoGeneracion);
		jsonObjectIdentificacion.put(Contingencia.FTRANSMISION, fTransmision);
		jsonObjectIdentificacion.put(Contingencia.HTRANSMISION, hTransmision);
		jsonObjectIdentificacion.put(Contingencia.VERSION, 3);
		
		System.out.println("Factura: end collecting JSON data for Identificacion");
		return jsonObjectIdentificacion;	
	}
	
	private JSONObject generateEmisorInputData() {
		System.out.println("Factura: start collecting JSON data for Emisor");
		
		JSONObject jsonObjectEmisor = new JSONObject();
		jsonObjectEmisor.put(Contingencia.NIT, orgInfo.getTaxID().replace("-", ""));
		jsonObjectEmisor.put(Contingencia.NOMBRE, client.getDescription());
		jsonObjectEmisor.put(Contingencia.TIPOESTABLECIMIENTO, client_getE_PlantType(client).getValue());
		//jsonObjectEmisor.put(Anulacion.CODESTABLEMH, "");								// TODO: korrekte Daten einsetzen
		//jsonObjectEmisor.put(Anulacion.CODESTABLE, client.getE_PlantType().getValue());								// TODO: korrekte Daten einsetzen
		//jsonObjectEmisor.put(Anulacion.CODPUNTOVENTAMH, "");							// TODO: korrekte Daten einsetzen
		//jsonObjectEmisor.put(Anulacion.CODPUNTOVENTA, "");							// TODO: korrekte Daten einsetzen
		jsonObjectEmisor.put(Contingencia.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectEmisor.put(Contingencia.CORREO, client_getEmail(client));

		jsonObjectEmisor.put(Contingencia.NUMDOCRESPONSABLE, orgInfo.getTaxID().replace("-", ""));	
		jsonObjectEmisor.put(Contingencia.NOMBRERESPONSABLE, "SCalderon");		
		jsonObjectEmisor.put(Contingencia.TIPDOCRESPONSABLE, "36");		// TODO: korrekte Daten einsetzen

		System.out.println("Factura: end collecting JSON data for Emisor");
		return jsonObjectEmisor;	
	}
	

	private JSONObject generateDetalleDTE() {
		System.out.println("Start collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		JSONObject jsonDocumentoRelacionado = new JSONObject();
		JSONArray jsonDocumentoRelacionadoArray = new JSONArray();

		HashMap<Integer,MInvoice> invoiceIds = new HashMap<Integer, MInvoice>();
		invoiceIds.put(invoice.getC_Invoice_ID(), invoice);
		for (MInvoice invoiceOrginal : invoiceIds.values()) {
			JSONObject jsonDocumentoRelacionadoItem = new JSONObject();
			String codidoGeneracion = createCodigoGeneracion(invoiceOrginal);	
			X_E_DocType doctype = docType_getE_DocType((MDocType)invoiceOrginal.getC_DocType());
			String doctypevalue = doctype.getValue();
			jsonDocumentoRelacionadoItem.put("noItem", 1);
			jsonDocumentoRelacionadoItem.put("tipoDoc", doctypevalue);
			jsonDocumentoRelacionadoItem.put("codigoGeneracion", codidoGeneracion);
			jsonDocumentoRelacionadoArray.put(jsonDocumentoRelacionadoItem);
		}
		
		jsonDocumentoRelacionado.put(Contingencia.DETALLEDTE, jsonDocumentoRelacionadoArray);
		System.out.println("Finish collecting JSON data for Documento Relacionado. Document: " + invoice.getDocumentNo());
		
		return jsonDocumentoRelacionado;
	}
	
	private JSONObject generateMotivoInputData() {
		System.out.println("Start collecting JSON data for Motivo");
		MUser user = new MUser(contextProperties, invoice.getCreatedBy(), trxName);
		
		JSONObject jsonObjectMotivo = new JSONObject();
		int contingenciatypeID = Env.getContextAsInt(Env.getCtx(), "@E_Contingency_ID@");
		X_E_Contingency contingency = new X_E_Contingency(contextProperties, contingenciatypeID, null);
		jsonObjectMotivo.put(Contingencia.TIPOCONTINGENCIA, contingency.getValue());		

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String fInicio = dateFormat.format(invoice.getCreatedBy());
		String hInicio = "00:00:00";
		String fFin = dateFormat.format(invoice.getCreatedBy());
		String hFin = timeFormat.format(invoice.getUpdated());
		System.out.println("Finish collecting JSON data for Maotivo");
		jsonObjectMotivo.put(Contingencia.FINICIO, fInicio);
		jsonObjectMotivo.put(Contingencia.HINICIO, hInicio);
		jsonObjectMotivo.put(Contingencia.FFIN, fFin);
		jsonObjectMotivo.put(Contingencia.HFIN, hFin);
		return jsonObjectMotivo;
		
	}
	
	
	public String createJsonString() throws Exception {
		System.out.println("Anulacion: start generating JSON object from Document");
		ObjectMapper objectMapper  = new ObjectMapper();
		String anulacionAsString   = objectMapper.writeValueAsString(contingencia);
		JSONObject anulacionAsJson = new JSONObject(anulacionAsString);

		anulacionAsJson.remove(Contingencia.ERRORMESSAGES);

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
		 return contingencia.errorMessages;
	 }
}

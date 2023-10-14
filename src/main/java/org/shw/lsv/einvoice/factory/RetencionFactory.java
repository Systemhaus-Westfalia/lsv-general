package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MTax;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.fecrretencionv1.ApendiceItemRetencion;
import org.shw.lsv.einvoice.fecrretencionv1.CuerpoDocumentoItemRetencion;
import org.shw.lsv.einvoice.fecrretencionv1.EmisorRetencion;
import org.shw.lsv.einvoice.fecrretencionv1.ExtensionRetencion;
import org.shw.lsv.einvoice.fecrretencionv1.IdentificacionRetencion;
import org.shw.lsv.einvoice.fecrretencionv1.ResumenRetencion;
import org.shw.lsv.einvoice.fecrretencionv1.Retencion;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RetencionFactory extends EDocumentFactory {
	Retencion retencion;
	MInvoice invoice;
	
	public RetencionFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo, MInvoice invoice) {
		super(trxName, contextProperties, client, orgInfo);
		this.invoice = invoice;
	}

	public Retencion generateEDocument() {
		System.out.println("Retencion: start generating and filling the Document");
		String result="";
		retencion = new Retencion();

		System.out.println("Instantiate, fill and verify Identificacion");
		IdentificacionRetencion identification = retencion.getIdentificacion();
		if(identification!=null) {
			retencion.errorMessages.append(retencion.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				retencion.errorMessages.append(result);
			}
		}
		
		System.out.println("Instantiate, fill and verify Emisor");
		EmisorRetencion emisor = retencion.getEmisor();
		if(emisor!=null) {
			retencion.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				retencion.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Cuerpo Documento");
		List<CuerpoDocumentoItemRetencion> cuerpoDocumento = retencion.getCuerpoDocumento();
		if(cuerpoDocumento!=null) {
			retencion.fillCuerpoDocumento(jsonInputToFactory);
			
			cuerpoDocumento.stream().forEach( cuerpoDocumentoItem -> { 
				String resultLambda = cuerpoDocumentoItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						retencion.errorMessages.append(resultLambda);
					}
				} 
			);
		}

		System.out.println("Instantiate, fill and verify Resumen");
		ResumenRetencion resumen = retencion.getResumen();
		if(resumen!=null) {
			retencion.fillResumen(jsonInputToFactory);
			result = resumen.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				retencion.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Extension");
		ExtensionRetencion extension = retencion.getExtension();
		if(extension!=null) {
			retencion.fillExtension(jsonInputToFactory);
			result = extension.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				retencion.errorMessages.append(result);
			}
		}
//		
		List<ApendiceItemRetencion> apendice = retencion.getApendice();
		if(apendice!=null) {
			retencion.fillApendice(jsonInputToFactory);
			
			apendice.stream().forEach( apendiceItem -> { 
				String resultLambda = apendiceItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						retencion.errorMessages.append(resultLambda);
					}
				} 
			);
		}

		retencion.validateValues();
		if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
			retencion.errorMessages.append(result);
		}

		System.out.println("Retencion: end generating and filling the Document");	
		return retencion;
	}

	@Override
	public void generateJSONInputData() {
		System.out.println("Retencion: start collecting JSON data for all components");
		jsonInputToFactory = new JSONObject();

		jsonInputToFactory.put(Retencion.IDENTIFICACION, generateIdentificationInputData());
		jsonInputToFactory.put(Retencion.RECEPTOR, generateReceptorInputData());
		jsonInputToFactory.put(Retencion.EMISOR, generateEmisorInputData());
		jsonInputToFactory.put(Retencion.RESUMEN, generateResumenInputData());
		jsonInputToFactory.put(Retencion.CUERPODOCUMENTO, generateCuerpoDocumentoInputData());
		
		System.out.println("Generated JSON object from Invoice:");
		System.out.println(jsonInputToFactory.toString());
		System.out.println("Retencion: end collecting JSON data for all components");
	}
	
	private JSONObject generateIdentificationInputData() {
		String prefix = invoice.getC_DocType().getDefiniteSequence().getPrefix();
		String documentno = invoice.getDocumentNo().replace(prefix,"");
		int position = documentno.indexOf("_");
		documentno = documentno.substring(0,position);
		String idIdentification  = StringUtils.leftPad(documentno, 15,"0");
		String duns = orgInfo.getDUNS().replace("-", "");
		
		String numeroControl = "DTE-" + invoice.getC_DocType().getE_DocType().getValue()
				+ "-"+ StringUtils.leftPad(duns.trim(), 8,"0") + "-"+ idIdentification;
		Integer invoiceID = invoice.get_ID();
		//String numeroControl = getNumeroControl(invoiceID, orgInfo, "DTE-01-");
		Integer clientID = (Integer)client.getAD_Client_ID();
		String codigoGeneracion = StringUtils.leftPad(clientID.toString(), 8, "0") + "-0000-0000-0000-" + StringUtils.leftPad(invoiceID.toString(), 12,"0");
		
		Boolean isContigencia = false;
		if (TimeUtil.getDaysBetween(invoice.getDateAcct(), TimeUtil.getDay(0))>=3) {
			isContigencia = true;
		}

		int tipoModelo = isContigencia?Retencion.TIPOMODELO_CONTIGENCIA:Retencion.TIPOMODELO_NOCONTIGENCIA;
		int tipoOperacion = isContigencia?Retencion.TIPOOPERACION_CONTIGENCIA:Retencion.TIPOOPERACION_NOCONTIGENCIA;
		JSONObject jsonObjectIdentificacion = new JSONObject();
		jsonObjectIdentificacion.put(Retencion.AMBIENTE,client.getE_Enviroment().getValue());									// TODO: korrekte Daten einsetzen
		jsonObjectIdentificacion.put(Retencion.TIPODTE, invoice.getC_DocType().getE_DocType().getValue());				// TODO: korrekte Daten einsetzen
		jsonObjectIdentificacion.put(Retencion.NUMEROCONTROL, numeroControl);
		jsonObjectIdentificacion.put(Retencion.CODIGOGENERACION, codigoGeneracion);
		jsonObjectIdentificacion.put(Retencion.TIPOMODELO, tipoModelo);									// TODO: korrekte Daten einsetzen
		jsonObjectIdentificacion.put(Retencion.TIPOOPERACION, tipoOperacion);								// TODO: korrekte Daten einsetzen
		if (isContigencia) {
			jsonObjectIdentificacion.put(Retencion.MOTIVOCONTIN, "Contigencia por fecha de factura");
			jsonObjectIdentificacion.put(Retencion.TIPOCONTINGENCIA, 5);
		}
		else {
			jsonObjectIdentificacion.put(Retencion.MOTIVOCONTIN, "");
			jsonObjectIdentificacion.put(Retencion.TIPOCONTINGENCIA, "");
		}		jsonObjectIdentificacion.put(Retencion.FECEMI, invoice.getDateAcct().toString().substring(0, 10));
		jsonObjectIdentificacion.put(Retencion.HOREMI, "00:00:00");
		jsonObjectIdentificacion.put(Retencion.TIPOMONEDA, "USD");

		System.out.println("Finish collecting JSON data for Identificacion");
		return jsonObjectIdentificacion;
		
	}
	
	private JSONObject generateEmisorInputData() {
		System.out.println("Start collecting JSON data for Emisor");
		
		JSONObject jsonObjectEmisor = new JSONObject();
		jsonObjectEmisor.put(Retencion.NIT, orgInfo.getTaxID().replace("-", ""));
		jsonObjectEmisor.put(Retencion.NRC, StringUtils.leftPad(orgInfo.getDUNS().trim().replace("-", ""), 7));
		jsonObjectEmisor.put(Retencion.NOMBRE, client.getName());
		jsonObjectEmisor.put(Retencion.CODACTIVIDAD, client.getE_Activity().getValue());
		jsonObjectEmisor.put(Retencion.DESCACTIVIDAD, client.getE_Activity().getName());
		jsonObjectEmisor.put(Retencion.NOMBRECOMERCIAL, client.getDescription());
		jsonObjectEmisor.put(Retencion.TIPOESTABLECIMIENTO, client.getE_PlantType().getValue());

		JSONObject jsonDireccion = new JSONObject();
		jsonDireccion.put(Retencion.DEPARTAMENTO, orgInfo.getC_Location().getC_City().getC_Region().getValue());
		jsonDireccion.put(Retencion.MUNICIPIO, orgInfo.getC_Location().getC_City().getValue());
		jsonDireccion.put(Retencion.COMPLEMENTO, orgInfo.getC_Location().getAddress1());
		jsonObjectEmisor.put(Retencion.DIRECCION, jsonDireccion);
		
		jsonObjectEmisor.put(Retencion.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectEmisor.put(Retencion.CODIGOMH, "");				// TODO: korrekte Daten einsetzen
		jsonObjectEmisor.put(Retencion.CODIGO, "");				// TODO: korrekte Daten einsetzen
		jsonObjectEmisor.put(Retencion.PUNTOVENTAMH, "");			// TODO: korrekte Daten einsetzen
		jsonObjectEmisor.put(Retencion.PUNTOVENTA, "");			// TODO: korrekte Daten einsetzen
		jsonObjectEmisor.put(Retencion.CORREO, client.getEMail());

		System.out.println("Finish collecting JSON data for Emisor");
		return jsonObjectEmisor;
		
	}
	
	private JSONObject generateReceptorInputData() {
		System.out.println("Retencion: start collecting JSON data for Receptor");

		MBPartner partner = (MBPartner)invoice.getC_BPartner();
		if (partner.getE_Activity_ID()<=0 || partner.getE_Recipient_Identification_ID() <= 0) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Retencion Electronica"; 
			retencion.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		
		JSONObject jsonObjectReceptor = new JSONObject();
		
		jsonObjectReceptor.put(Retencion.TIPODOCUMENTO, partner.getE_Recipient_Identification().getValue());
		if (partner.getTaxID() != null) {
			jsonObjectReceptor.put(Retencion.NUMDOCUMENTO, partner.getTaxID().replace("-", ""));
			jsonObjectReceptor.put(Retencion.NRC, partner.getDUNS().trim().replace("-", ""));
			jsonObjectReceptor.put(Retencion.NOMBRE, partner.getName());	
			jsonObjectReceptor.put(Retencion.NOMBRECOMERCIAL, client.getDescription());		
		}
		else {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta NIT"; 
			retencion.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		
		if (partner.getE_Activity_ID()>0) {
			jsonObjectReceptor.put(Retencion.CODACTIVIDAD, partner.getE_Activity().getValue());
			jsonObjectReceptor.put(Retencion.DESCACTIVIDAD, partner.getE_Activity().getName());
		} else  {
			jsonObjectReceptor.put(Retencion.CODACTIVIDAD, "");
			jsonObjectReceptor.put(Retencion.DESCACTIVIDAD, "");
		}

		JSONObject jsonDireccion = new JSONObject();
		String departamento = "";
		String municipio = "";
		String complemento = "";
		for (MBPartnerLocation partnerLocation : MBPartnerLocation.getForBPartner(contextProperties, partner.getC_BPartner_ID(), trxName)){
			if (partnerLocation.isBillTo()) {
				departamento = partnerLocation.getC_Location().getC_City().getC_Region().getValue();
				municipio =  partnerLocation.getC_Location().getC_City().getValue();
				complemento = (partnerLocation.getC_Location().getAddress1() + " " + partnerLocation.getC_Location().getAddress2());
				jsonDireccion.put(Retencion.DEPARTAMENTO, departamento);
				jsonDireccion.put(Retencion.MUNICIPIO, municipio);
				jsonDireccion.put(Retencion.COMPLEMENTO, complemento);
				break;
			}
		}		
		
		// In case there is no address
		if (departamento == null) {
			jsonDireccion.put(Retencion.DEPARTAMENTO, departamento);
			jsonDireccion.put(Retencion.MUNICIPIO, municipio);
			jsonDireccion.put(Retencion.COMPLEMENTO, complemento);
		}		
		jsonObjectReceptor.put(Retencion.DIRECCION, jsonDireccion);
		
		jsonObjectReceptor.put(Retencion.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectReceptor.put(Retencion.CORREO, partner.get_ValueAsString("EMail"));		

		System.out.println("Retencion: end collecting JSON data for Receptor");
		return jsonObjectReceptor;
		
	}
	
	private JSONObject generateResumenInputData() {						// TODO: korrekte Daten einsetzen
		System.out.println("Start collecting JSON data for Resumen");
		BigDecimal totalNoSuj 	= Env.ZERO;
		BigDecimal totalExenta 	= Env.ZERO;
		String totalLetras=Msg.getAmtInWords(Env.getLanguage(contextProperties), invoice.getGrandTotal().setScale(2).toString());

		List<MInvoiceTax> invoiceTaxes = new Query(contextProperties , MInvoiceTax.Table_Name , "C_Invoice_ID=?" , trxName)
				.setParameters(invoice.getC_Invoice_ID())
				.list();
		
		for (MInvoiceTax invoiceTax:invoiceTaxes) {
			if (invoiceTax.getC_Tax().getTaxIndicator().equals("NSUJ")) {
				totalNoSuj = invoiceTax.getTaxBaseAmt();
			}
			if (!invoiceTax.getC_Tax().getTaxIndicator().equals("NSUJ") && invoiceTax.getC_Tax().getRate().doubleValue()==0.00) {
				totalExenta = invoiceTax.getTaxBaseAmt();
			}
		}
				
		JSONObject jsonObjectResumen = new JSONObject();
		jsonObjectResumen.put(Retencion.TOTALSUJETORETENCION, totalNoSuj);		// TODO: korrekte Daten einsetzen
		jsonObjectResumen.put(Retencion.TOTALIVARETENIDO, totalExenta);			// TODO: korrekte Daten einsetzen
		jsonObjectResumen.put(Retencion.TOTALIVARETENIDOLETRAS, totalLetras);	// TODO: korrekte Daten einsetzen
		
		System.out.println("Finish collecting JSON data for Resumen");
		return jsonObjectResumen;
		
	}
	
	private JSONObject generateCuerpoDocumentoInputData() {
		System.out.println("Retencion: start collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		JSONObject jsonCuerpoDocumento = new JSONObject();
		JSONArray jsonCuerpoDocumentoArray = new JSONArray();
		
		for (MInvoiceLine invoiceLine:invoice.getLines()) { 
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() );
			
			BigDecimal ventaNoSuj 	= Env.ZERO;
			BigDecimal ventaExenta 	= Env.ZERO;
			BigDecimal ventaGravada = Env.ONEHUNDRED;
			BigDecimal ivaItem 		= Env.ZERO;
			MTax tax = (MTax)invoiceLine.getC_Tax();
			
			
			if (invoiceLine.getC_Tax().getTaxIndicator().equals("NSUJ")) {
				ventaNoSuj = invoiceLine.getLineNetAmt();
			}
			if (invoiceLine.getC_Tax().getTaxIndicator().equals("EXT"))
				ventaExenta = invoiceLine.getLineNetAmt();
			if (invoiceLine.getC_Tax().getTaxIndicator().equals("IVA") ) {
				ventaGravada = invoiceLine.getLineNetAmt(); 
				if (invoiceLine.getTaxAmt().compareTo(Env.ZERO) == 0)
					ivaItem = tax.calculateTax(invoiceLine.getLineNetAmt(), invoice.getM_PriceList().isTaxIncluded(), 2);
			}
			
			JSONObject jsonCuerpoDocumentoItem = new JSONObject();
                
			jsonCuerpoDocumentoItem.put(Retencion.NUMITEM, invoiceLine.getLine()/10);
			jsonCuerpoDocumentoItem.put(Retencion.TIPODTE, "BBBBBBBBBBBBBBBBBBBBBB");				// TODO: korrekte Daten einsetzen
			jsonCuerpoDocumentoItem.put(Retencion.TIPODOC, "BBBBBBBBBBBBBBBBBBBBBB");				// TODO: korrekte Daten einsetzen
			jsonCuerpoDocumentoItem.put(Retencion.NUMDOCUMENTO, "BBBBBBBBBBBBBBBBBBBBBB");			// TODO: korrekte Daten einsetzen
			jsonCuerpoDocumentoItem.put(Retencion.FECHAEMISION, "BBBBBBBBBBBBBBBBBBBBBB");			// TODO: korrekte Daten einsetzen
			jsonCuerpoDocumentoItem.put(Retencion.MONTOSUJETOGRAV, "BBBBBBBBBBBBBBBBBBBBBB");		// TODO: korrekte Daten einsetzen
			jsonCuerpoDocumentoItem.put(Retencion.CODIGORETENCIONMH, "BBBBBBBBBBBBBBBBBBBBBB");		// TODO: korrekte Daten einsetzen
			jsonCuerpoDocumentoItem.put(Retencion.IVARETENIDO, "BBBBBBBBBBBBBBBBBBBBBB");			// TODO: korrekte Daten einsetzen			
			jsonCuerpoDocumentoItem.put(Retencion.DESCRIPCION, invoiceLine.getM_Product_ID()>0?invoiceLine.getM_Product().getName():invoiceLine.getC_Charge().getName());

			jsonCuerpoDocumentoArray.put(jsonCuerpoDocumentoItem);

		
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() + " Finished");

		}  
		jsonCuerpoDocumento.put(Retencion.CUERPODOCUMENTO, jsonCuerpoDocumentoArray);
		System.out.println("Retencion: end collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		
		return jsonCuerpoDocumento;
	}

	public String createJsonString() throws Exception {
		System.out.println("Retencion: start generating JSON object from Document");
		ObjectMapper objectMapper  = new ObjectMapper();
		String retencionAsString   = objectMapper.writeValueAsString(retencion);
		JSONObject retencionAsJson = new JSONObject(retencionAsString);

		retencionAsJson.remove(Retencion.ERRORMESSAGES);

		// Manipulate generated JSON string
		String retencionAsStringFinal = retencionAsJson.toString().
				replace(":[],", ":null,").
				replace("\"documentoRelacionado\":[]", "\"documentoRelacionado\":null").
				replace("\"ventaTercero\":{\"nit\":null,\"nombre\":null},", "\"ventaTercero\":null,").
				replace("\"tributos\":[{\"descripcion\":null,\"codigo\":null,\"valor\":null}]", "\"tributos\":null").
				replace("\"extension\":{\"docuEntrega\":null,\"placaVehiculo\":null,\"observaciones\":null,\"nombRecibe\":null,\"nombEntrega\":null,\"docuRecibe\":null},", 
						"\"extension\":null,");

		System.out.println("Retencion: generated JSON object from Document:");
		System.out.println(retencionAsStringFinal);
		System.out.println("Retencion: end generating JSON object from Document");
		return retencionAsStringFinal;	
	}

	public String getNumeroControl(Integer id, MOrgInfo orgInfo, String prefix) {
		String idIdentification  = StringUtils.leftPad(id.toString(), 15,"0");
		String duns = orgInfo.getDUNS().replace("-", "");
		String numeroControl = prefix + StringUtils.leftPad(duns.trim(), 8,"0") + "-"+ idIdentification;
		return numeroControl;
	}
	

	@Override
	public StringBuffer getEDocumentErrorMessages() {
		 return retencion.errorMessages;
	 }
}

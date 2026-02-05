package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.core.domains.models.X_C_UOM;
import org.adempiere.core.domains.models.X_E_Activity;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCity;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MPackageExp;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MTax;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.feccfcreditofiscalv3.ApendiceItemCreditoFiscal;
import org.shw.lsv.einvoice.feccfcreditofiscalv3.CreditoFiscal;
import org.shw.lsv.einvoice.feccfcreditofiscalv3.CuerpoDocumentoItemCreditoFiscal;
import org.shw.lsv.einvoice.feccfcreditofiscalv3.EmisorCreditoFiscal;
import org.shw.lsv.einvoice.feccfcreditofiscalv3.ExtensionCreditoFiscal;
import org.shw.lsv.einvoice.feccfcreditofiscalv3.IdentificacionCreditoFiscal;
import org.shw.lsv.einvoice.feccfcreditofiscalv3.ReceptorCreditoFiscal;
import org.shw.lsv.einvoice.feccfcreditofiscalv3.ResumenCreditoFiscal;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CreditoFiscalFactory extends EDocumentFactory {
	CreditoFiscal creditoFiscal;
	MInvoice invoice;
	
	public CreditoFiscalFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo, MInvoice invoice) {
		super(trxName, contextProperties, client, orgInfo);
		this.invoice = invoice;
		creditoFiscal = new CreditoFiscal();
	}

	public CreditoFiscal generateEDocument() {
		System.out.println("CreditoFiscal: start generating and filling the Document");
		String result="";

		System.out.println("Instantiate, fill and verify Identificacion");
		IdentificacionCreditoFiscal identification = creditoFiscal.getIdentificacion();
		if(identification!=null) {
			creditoFiscal.errorMessages.append(creditoFiscal.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				creditoFiscal.errorMessages.append(result);
			}
		}
		
//		List<DocumentoRelacionadoItem> documentoRelacionado = factura.getDocumentoRelacionado();
//		if(documentoRelacionado!=null) {
//			errorMessages.append(factura.fillDocumentoRelacionado(jsonInputToFactory));
//			
//			documentoRelacionado.stream().forEach( documentoRelacionadoItem -> { 
//					String resultLambda = documentoRelacionadoItem.validateValues();
//					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
//						errorMessages.append(resultLambda);
//					}
//				} 
//			);
//		}

		System.out.println("Instantiate, fill and verify Emisor");
		EmisorCreditoFiscal emisor = creditoFiscal.getEmisor();
		if(emisor!=null) {
			creditoFiscal.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				creditoFiscal.errorMessages.append(result);
			}
		}
		
//		List<OtrosDocumentosItem> otrosDocumentos = factura.getOtrosDocumentos();
//		if(otrosDocumentos!=null) {
//			factura.fillOtrosDocumentos(jsonInputToFactory);
//			
//			otrosDocumentos.stream().forEach( otrosDocumentosItem -> { 
//				String resultLambda = otrosDocumentosItem.validateValues();
//					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
//						errorMessages.append(resultLambda);
//					}
//				} 
//			);
//		}
		
//		VentaTercero ventaTercero = factura.getVentaTercero();
//		if(ventaTercero!=null) {
//			factura.fillVentaTercero(jsonInputToFactory);
//			result = ventaTercero.validateValues();
//			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
//				errorMessages.append(result);
//			}
//		}

		System.out.println("Instantiate, fill and verify Cuerpo Documento");
		List<CuerpoDocumentoItemCreditoFiscal> cuerpoDocumento = creditoFiscal.getCuerpoDocumento();
		if(cuerpoDocumento!=null) {
			creditoFiscal.fillCuerpoDocumento(jsonInputToFactory);
			
			cuerpoDocumento.stream().forEach( cuerpoDocumentoItem -> { 
				String resultLambda = cuerpoDocumentoItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						creditoFiscal.errorMessages.append(resultLambda);
					}
				} 
			);
		}

		System.out.println("Instantiate, fill and verify Resumen");
		ResumenCreditoFiscal resumen = creditoFiscal.getResumen();
		if(resumen!=null) {
			creditoFiscal.fillResumen(jsonInputToFactory);
			result = resumen.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				creditoFiscal.errorMessages.append(result);
			}
		}
		

		System.out.println("Instantiate, fill and verify ReceptorCreditoFiscal");
		ReceptorCreditoFiscal receptor = creditoFiscal.getReceptor();
		if(receptor!=null) {
			creditoFiscal.fillReceptor(jsonInputToFactory);
			result = receptor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				creditoFiscal.errorMessages.append(result);
			}
		}
		
		System.out.println("Instantiate, fill and verify ExtensionCreditoFiscal");
		ExtensionCreditoFiscal extension = creditoFiscal.getExtension();
		if(extension!=null) {
			creditoFiscal.fillExtension(jsonInputToFactory);
			result = extension.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				creditoFiscal.errorMessages.append(result);
			}
		}
		
		List<ApendiceItemCreditoFiscal> apendice = creditoFiscal.getApendice();
		if(apendice!=null) {
			creditoFiscal.fillApendice(jsonInputToFactory);
			
			apendice.stream().forEach( apendiceItem -> { 
				String resultLambda = apendiceItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						creditoFiscal.errorMessages.append(resultLambda);
					}
				} 
			);
		}
		
//		Documento documento = eDocument.getDocumento();
//		if(documento!=null) {
//			eDocument.fillDocumento(jsonInputToFactory);
//			result = documento.validateValues();
//			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
//				errorMessages.append(result);
//			}
//		}
		
//		Motivo motivo = eDocument.getMotivo();
//		if(documento!=null) {
//			eDocument.fillMotivo(jsonInputToFactory);
//			result = motivo.validateValues();
//			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
//				errorMessages.append(result);
//			}
//		}

		creditoFiscal.validateValues();
		if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
			creditoFiscal.errorMessages.append(result);
		}

		System.out.println("CreditoFiscal: end generating and filling the Document");
		return creditoFiscal;
	}

	@Override
	public void generateJSONInputData() {
		System.out.println("Credito Fiscal: start collecting JSON data for all components");
		jsonInputToFactory = new JSONObject();

		jsonInputToFactory.put(CreditoFiscal.IDENTIFICACION, generateIdentificationInputData());
		jsonInputToFactory.put(CreditoFiscal.RECEPTOR, generateReceptorInputData());
		jsonInputToFactory.put(CreditoFiscal.EMISOR, generateEmisorInputData());
		jsonInputToFactory.put(CreditoFiscal.RESUMEN, generateResumenInputData());
		jsonInputToFactory.put(CreditoFiscal.CUERPODOCUMENTO, generateCuerpoDocumentoInputData());
		jsonInputToFactory.put(CreditoFiscal.APENDICE, generateApendiceInputData(invoice.getC_Invoice_ID()));
		jsonInputToFactory.put(CreditoFiscal.EXTENSION, generateExtensionInputData());
		
		System.out.println("Generated JSON object from Invoice:");
		System.out.println(jsonInputToFactory.toString());
		System.out.println("Credito Fiscal: end collecting JSON data for all components");
	}
	
	private JSONObject generateIdentificationInputData() {
		String numeroControl = createNumeroControl(invoice, client);
		String codigoGeneracion =  createCodigoGeneracion(invoice);
		JSONObject jsonObjectIdentificacion = new JSONObject();
		Boolean isContigencia = false;
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String horEmi = timeFormat.format(cal.getTime());
		if (TimeUtil.getDaysBetween(invoice.getDateAcct(), TimeUtil.getDay(0))>3) {
			isContigencia = true;
		}
		int tipoModelo = isContigencia?CreditoFiscal.TIPOMODELO_CONTIGENCIA:CreditoFiscal.TIPOMODELO_NOCONTIGENCIA;
		int tipoOperacion = isContigencia?CreditoFiscal.TIPOOPERACION_CONTIGENCIA:CreditoFiscal.TIPOOPERACION_NOCONTIGENCIA;
		jsonObjectIdentificacion.put(CreditoFiscal.NUMEROCONTROL, numeroControl);
		jsonObjectIdentificacion.put(CreditoFiscal.CODIGOGENERACION, codigoGeneracion);
		jsonObjectIdentificacion.put(CreditoFiscal.TIPOMODELO, tipoModelo);
		jsonObjectIdentificacion.put(CreditoFiscal.TIPOOPERACION, tipoOperacion);
		jsonObjectIdentificacion.put(CreditoFiscal.FECEMI,  invoice.getDateAcct().toString().substring(0, 10));
		jsonObjectIdentificacion.put(CreditoFiscal.HOREMI, horEmi);
		jsonObjectIdentificacion.put(CreditoFiscal.TIPOMONEDA, "USD");
		jsonObjectIdentificacion.put(CreditoFiscal.AMBIENTE, client_getE_Enviroment(client).getValue());
		
		/*if (isContigencia) {
			jsonObjectIdentificacion.put(CreditoFiscal.MOTIVOCONTIN, "Contigencia por fecha de factura");
			jsonObjectIdentificacion.put(CreditoFiscal.TIPOCONTINGENCIA, 5);
		}*/
		{
			jsonObjectIdentificacion.put(CreditoFiscal.MOTIVOCONTIN, "");
			jsonObjectIdentificacion.put(CreditoFiscal.TIPOCONTINGENCIA, "");
		}
		
		System.out.println("CreditoFiscal: end collecting JSON data for Identificacion");
		return jsonObjectIdentificacion;
		
	}
	
	private JSONObject generateEmisorInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Emisor");
		
		int activityID = client.get_ValueAsInt(Columnname_E_Activity_ID);
		X_E_Activity e_Activity = new X_E_Activity(Env.getCtx(), activityID, trxName);
		JSONObject jsonObjectEmisor = new JSONObject();
		jsonObjectEmisor.put(CreditoFiscal.NIT, client.get_ValueAsString("ei_nit"));
		jsonObjectEmisor.put(CreditoFiscal.NRC, StringUtils.leftPad(orgInfo.getDUNS().trim().replace("-", ""), 7));
		jsonObjectEmisor.put(CreditoFiscal.NOMBRE, client.getDescription());
		jsonObjectEmisor.put(CreditoFiscal.CODACTIVIDAD, e_Activity.getValue());
		jsonObjectEmisor.put(CreditoFiscal.DESCACTIVIDAD, e_Activity.getName());
		jsonObjectEmisor.put(CreditoFiscal.NOMBRECOMERCIAL, client.getName());
		jsonObjectEmisor.put(CreditoFiscal.TIPOESTABLECIMIENTO, client_getE_PlantType(client).getValue());

		JSONObject jsonDireccion = new JSONObject();
		jsonDireccion.put(CreditoFiscal.DEPARTAMENTO, city_getRegionValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(CreditoFiscal.MUNICIPIO, city_getValue((MCity)orgInfo.getC_Location().getC_City()) );
		jsonDireccion.put(CreditoFiscal.COMPLEMENTO, orgInfo.getC_Location().getAddress1());
		jsonObjectEmisor.put(CreditoFiscal.DIRECCION, jsonDireccion);
		
		jsonObjectEmisor.put(CreditoFiscal.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectEmisor.put(CreditoFiscal.CORREO, client_getEmail(client));

		System.out.println("CreditoFiscal: end collecting JSON data for Emisor");
		return jsonObjectEmisor;
		
	}
	
	private JSONObject generateReceptorInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Receptor");

		MBPartner partner = (MBPartner)invoice.getC_BPartner();
		if (bPartner_getE_Activity(partner).getE_Activity_ID()    <=0 || bPartner_getE_Recipient_Identification(partner).getE_Recipient_Identification_ID() <= 0) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica"; 
			creditoFiscal.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		
		JSONObject jsonObjectReceptor = new JSONObject();
		if (partner.getTaxID() == null && partner.getDUNS() == null) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica"; 
			creditoFiscal.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		jsonObjectReceptor.put(CreditoFiscal.NIT, partner.getTaxID().replace("-", ""));
		String duns = Optional.ofNullable(partner.getDUNS()).orElse("");
		jsonObjectReceptor.put(CreditoFiscal.NRC, duns.trim().replace("-", ""));
		jsonObjectReceptor.put(CreditoFiscal.NOMBRE, partner.getName());
		jsonObjectReceptor.put(CreditoFiscal.CORREO, partner.get_ValueAsString("EMail"));
		
		if (bPartner_getE_Activity(partner).getE_Activity_ID()>0) {
			jsonObjectReceptor.put(CreditoFiscal.CODACTIVIDAD, bPartner_getE_Activity(partner).getValue());
			jsonObjectReceptor.put(CreditoFiscal.DESCACTIVIDAD, bPartner_getE_Activity(partner).getName());
		}

		JSONObject jsonDireccion = new JSONObject();
		String departamento = "";
		String municipio = "";
		String complemento = "";
		for (MBPartnerLocation partnerLocation : MBPartnerLocation.getForBPartner(contextProperties, partner.getC_BPartner_ID(), trxName)){
			if (partnerLocation.isBillTo()) {
				departamento = city_getRegionValue((MCity)partnerLocation.getC_Location().getC_City());
				municipio =  city_getValue((MCity)partnerLocation.getC_Location().getC_City());
				String address = partnerLocation.getC_Location().getAddress2() == null?partnerLocation.getC_Location().getAddress1():
					partnerLocation.getC_Location().getAddress1() + " " + partnerLocation.getC_Location().getAddress2();
				complemento = (address);
				jsonDireccion.put(CreditoFiscal.DEPARTAMENTO, departamento);
				jsonDireccion.put(CreditoFiscal.MUNICIPIO, municipio);
				jsonDireccion.put(CreditoFiscal.COMPLEMENTO, complemento);
				break;
			}
		}		
		
		// In case there is no address
		if (departamento == null) {
			jsonDireccion.put(CreditoFiscal.DEPARTAMENTO, departamento);
			jsonDireccion.put(CreditoFiscal.MUNICIPIO, municipio);
			jsonDireccion.put(CreditoFiscal.COMPLEMENTO, complemento);
		}		
		jsonObjectReceptor.put(CreditoFiscal.DIRECCION, jsonDireccion);
		jsonObjectReceptor.put(CreditoFiscal.TELEFONO, partner.get_ValueAsString("phone"));
		jsonObjectReceptor.put(CreditoFiscal.CORREO, partner.get_ValueAsString("EMail"));		

		System.out.println("CreditoFiscal: end collecting JSON data for Receptor");
		return jsonObjectReceptor;
		
	}
	
	private JSONObject generateResumenInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Resumen");
		BigDecimal totalNoSuj 		= Env.ZERO;
		BigDecimal totalExenta 		= Env.ZERO;
		BigDecimal totalGravada 	= Env.ZERO;	
		BigDecimal totalNoGravada 	= Env.ZERO;		
		BigDecimal ivaRete1 		= Env.ZERO;
		BigDecimal montotributos	= Env.ZERO;

		String totalLetras=Msg.getAmtInWords(client.getLanguage(), invoice.getGrandTotal().setScale(2).toString());

		List<MInvoiceTax> invoiceTaxes = new Query(contextProperties , MInvoiceTax.Table_Name , "C_Invoice_ID=?" , trxName)
				.setParameters(invoice.getC_Invoice_ID())
				.list();
		JSONObject jsonObjectResumen = new JSONObject();

		
		JSONArray jsonTributosArray = new JSONArray();
		for (MInvoiceTax invoiceTax:invoiceTaxes) {
			if (invoiceTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_RET)) {
				ivaRete1 = ivaRete1.add(invoiceTax.getTaxAmt().multiply(new BigDecimal(-1)));
				continue;
			}
			JSONObject jsonTributoItem = new JSONObject();		
			if (invoiceTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_NSUJ)) {
				if (invoiceTax.getC_Tax().getC_TaxCategory().getCommodityCode() != null &&
						invoiceTax.getC_Tax().getC_TaxCategory().getCommodityCode().equals(CHARGETYPE_CTAJ))
					totalNoGravada = invoiceTax.getTaxBaseAmt();	
				else {					
					totalNoSuj = invoiceTax.getTaxBaseAmt();	
				}
			}
			else if (invoiceTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_EXT)) {
				totalExenta = invoiceTax.getTaxBaseAmt();
			}
			else if (invoiceTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_IVA)) {
				totalGravada = invoiceTax.getTaxBaseAmt();
				montotributos = montotributos.add(invoiceTax.getTaxAmt());
				jsonTributoItem.put(CreditoFiscal.CODIGO, tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getValue());
				jsonTributoItem.put(CreditoFiscal.DESCRIPCION, tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getName());
				jsonTributoItem.put(CreditoFiscal.VALOR, invoiceTax.getTaxAmt());
				jsonTributosArray.put(jsonTributoItem); 
			}
		}
		jsonObjectResumen.put(CreditoFiscal.TRIBUTOS, jsonTributosArray);


		jsonObjectResumen.put(CreditoFiscal.TOTALNOSUJ, totalNoSuj);
		jsonObjectResumen.put(CreditoFiscal.TOTALEXENTA, totalExenta);
		jsonObjectResumen.put(CreditoFiscal.TOTALGRAVADA, totalGravada);
		jsonObjectResumen.put(CreditoFiscal.SUBTOTALVENTAS, totalGravada.add(totalNoSuj).add(totalExenta));
		jsonObjectResumen.put(CreditoFiscal.DESCUNOSUJ, Env.ZERO);
		jsonObjectResumen.put(CreditoFiscal.DESCUEXENTA, Env.ZERO);
		jsonObjectResumen.put(CreditoFiscal.DESCUGRAVADA, Env.ZERO);
		jsonObjectResumen.put(CreditoFiscal.PORCENTAJEDESCUENTO, Env.ZERO);
		jsonObjectResumen.put(CreditoFiscal.SUBTOTAL, totalGravada.add(totalNoSuj).add(totalExenta));
		jsonObjectResumen.put(CreditoFiscal.IVARETE1, ivaRete1);
		jsonObjectResumen.put(CreditoFiscal.MONTOTOTALOPERACION, totalGravada.add(totalNoSuj).add(totalExenta).add(montotributos));
		jsonObjectResumen.put(CreditoFiscal.TOTALNOGRAVADO, totalNoGravada);
		jsonObjectResumen.put(CreditoFiscal.TOTALPAGAR, invoice.getGrandTotal());
		jsonObjectResumen.put(CreditoFiscal.TOTALLETRAS, totalLetras);
		jsonObjectResumen.put(CreditoFiscal.SALDOFAVOR, Env.ZERO);
		int condicionOperacion = 
		invoice.getC_PaymentTerm().getNetDays() == 0? CreditoFiscal.CONDICIONOPERACION_AL_CONTADO:
			CreditoFiscal.CONDICIONOPERACION_A_CREDITO;
		jsonObjectResumen.put(CreditoFiscal.CONDICIONOPERACION, condicionOperacion);
		jsonObjectResumen.put(CreditoFiscal.TOTALDESCU, Env.ZERO);
		jsonObjectResumen.put(CreditoFiscal.RETERENTA, Env.ZERO);
		jsonObjectResumen.put(CreditoFiscal.IVAPERCI1, Env.ZERO);

		JSONArray jsonArrayPagos = new JSONArray();
		JSONObject jsonPago = new JSONObject();
		jsonPago.put(CreditoFiscal.CODIGO, "05");
		jsonPago.put(CreditoFiscal.MONTOPAGO, invoice.getGrandTotal());
		jsonPago.put(CreditoFiscal.REFERENCIA, "Transferencia_ Deposito Bancario");
		jsonPago.put(CreditoFiscal.PLAZO, paymentterm_getE_TimeSpan((MPaymentTerm)invoice.getC_PaymentTerm()).getValue());
		jsonPago.put(CreditoFiscal.PERIODO, invoice.getC_PaymentTerm().getNetDays());
		jsonArrayPagos.put(jsonPago);

		jsonObjectResumen.put(CreditoFiscal.PAGOS, jsonArrayPagos);


		System.out.println("CreditoFiscal: end collecting JSON data for Resumen");
		return jsonObjectResumen;

	}
	
	private JSONObject generateCuerpoDocumentoInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		JSONObject jsonCuerpoDocumento = new JSONObject();
		JSONArray jsonCuerpoDocumentoArray = new JSONArray();
		if (invoice.getLines().length>lineNo)
			createLinesAcumulated(jsonCuerpoDocumentoArray);
		else
			createLines(jsonCuerpoDocumentoArray);
//		int i = 0;
//		for (MInvoiceLine invoiceLine:invoice.getLines()) {
//		}  
		jsonCuerpoDocumento.put(CreditoFiscal.CUERPODOCUMENTO, jsonCuerpoDocumentoArray);
		System.out.println("CreditoFiscal: end collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		
		return jsonCuerpoDocumento;
	}

	private JSONObject generateExtensionInputData() {
		System.out.println("Credito Fiscal: start collecting JSON data for Extension. Document: " + invoice.getDocumentNo());
		JSONObject jsonExtension = new JSONObject();
		String observaciones = invoice.get_ValueAsString(MPackageExp.COLUMNNAME_Instructions);
		
		jsonExtension.put(CreditoFiscal.NOMBENTREGA, "");
		jsonExtension.put(CreditoFiscal.DOCUENTREGA, "");
		jsonExtension.put(CreditoFiscal.NOMBRECIBE, "");
		jsonExtension.put(CreditoFiscal.DOCURECIBE, "");
		jsonExtension.put(CreditoFiscal.OBSERVACIONES, observaciones);
		jsonExtension.put(CreditoFiscal.PLACAVEHICULO, "");
		System.out.println("Credito Fiscal: end collecting JSON data for Extension. Document: " + invoice.getDocumentNo());
		return jsonExtension;
	}
	
	public String createJsonString() throws Exception {
		System.out.println("CreditoFiscal: start generating JSON object from Document");
    	ObjectMapper objectMapper       = new ObjectMapper();
    	String creditoFiscalAsStringTmp = objectMapper.writeValueAsString(creditoFiscal);
        JSONObject creditoFiscalAsJson  = new JSONObject(creditoFiscalAsStringTmp);
        
        creditoFiscalAsJson.remove(CreditoFiscal.ERRORMESSAGES);

     // Manipulate generated JSON string
        String creditoFiscalAsStringFinal = creditoFiscalAsJson.toString().
        		replace(":[],", ":null,").
        		replace("\"telefono\":\"\"", "\"telefono\":null").
        		replace("\"periodo\":0,\"plazo\":\"01\"", "\"periodo\":null,\"plazo\":null").
        		replace("\"documentoRelacionado\":[]", "\"documentoRelacionado\":null").
        		replace("\"ventaTercero\":{\"nit\":null,\"nombre\":null},", "\"ventaTercero\":null,").
        		replace("\"tributos\":[{\"descripcion\":null,\"codigo\":null,\"valor\":null}]", "\"tributos\":null")
				;
				//.
				//ToDo: Element "extension" ersetzen nur, wenn alle Felder null sind, oder auch dann veroeffentlichen?
        		// replace("\"extension\":{\"docuEntrega\":null,\"placaVehiculo\":null,\"observaciones\":null,\"nombRecibe\":null,\"nombEntrega\":null,\"docuRecibe\":null},",
        		// 		"\"extension\":null,");

		System.out.println("Credito Fiscal: generated JSON object from Document:");
		//System.out.println(creditoFiscalAsStringFinal);
		System.out.println("Credito Fiscal: end generating JSON object from Document");
		return creditoFiscalAsStringFinal;
	}

	public String getNumeroControl(Integer id, MOrgInfo orgInfo, String prefix) {
		String idIdentification  = StringUtils.leftPad(id.toString(), 15,"0");
		String duns = orgInfo.getDUNS().replace("-", "");
		String numeroControl = prefix + StringUtils.leftPad(duns.trim(), 8,"0") + "-"+ idIdentification;
		return numeroControl;
	}
	

	@Override
	public StringBuffer getEDocumentErrorMessages() {
		 return creditoFiscal.errorMessages;
	 }
	
	private void createLines(JSONArray jsonCuerpoDocumentoArray ) {
		int i = 0;
		for (MInvoiceLine invoiceLine:invoice.getLines()) {
			i++;
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() );

			BigDecimal ventaNoSuj 				= Env.ZERO;
			BigDecimal ventaExenta 				= Env.ZERO;
			BigDecimal ventaGravada 			= Env.ZERO;
			BigDecimal ventaNoGravada 			= Env.ZERO;
			BigDecimal ivaItem 					= Env.ZERO;
			BigDecimal precioUnitario 			= invoiceLine.getPriceEntered();
			String codTributo 					= "";
			MTax tax 							= (MTax)invoiceLine.getC_Tax();

			boolean isVentanoGravada = (invoiceLine.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_NSUJ) && 
					invoiceLine.getC_Charge_ID() > 0 
					&& invoiceLine.getC_Charge().getC_ChargeType().getValue().equals(CHARGETYPE_CTAJ))?true:false;
			if (invoiceLine.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_NSUJ)) {
				if (isVentanoGravada) {		
					ventaNoGravada = invoiceLine.getLineNetAmt();
					precioUnitario = Env.ZERO;
				}
				else {
					ventaNoSuj = invoiceLine.getLineNetAmt();					
				}
			}
			else if (invoiceLine.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_EXT))
				ventaExenta = invoiceLine.getLineNetAmt();
			else if (invoiceLine.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_IVA) ) {
				ventaGravada = invoiceLine.getLineNetAmt(); 
				if (invoiceLine.getTaxAmt().compareTo(Env.ZERO) == 0)
					ivaItem = tax.calculateTax(invoiceLine.getLineNetAmt(), invoice.getM_PriceList().isTaxIncluded(), 2);
			}

			JSONObject jsonCuerpoDocumentoItem = new JSONObject();
			String name = "";
			String description = "";
			if (invoiceLine.getC_Charge_ID() > 0)
				name = invoiceLine.getC_Charge().getName();
			else if (invoiceLine.getM_Product_ID()>0)
				name = invoiceLine.getM_Product().getName();
			if (invoiceLine.getDescription() != null && invoiceLine.getDescription().length()>0)
				description = name + " " + invoiceLine.getDescription();
			else 
				description = name;
			if (description.length()>999)
				description = description.substring(0, 998);
			jsonCuerpoDocumentoItem.put(CreditoFiscal.NUMITEM, i);
			jsonCuerpoDocumentoItem.put(CreditoFiscal.TIPOITEM, invoiceLineProductType(invoiceLine.getM_Product_ID()));
			jsonCuerpoDocumentoItem.put(CreditoFiscal.CANTIDAD, invoiceLine.getQtyEntered());
			jsonCuerpoDocumentoItem.put(CreditoFiscal.CODIGO, invoiceLine.getM_Product_ID()>0? 
					invoiceLine.getProduct().getValue(): invoiceLine.getC_Charge().getC_ChargeType().getValue());
			//jsonCuerpoDocumentoItem.put(CreditoFiscal.UNIMEDIDA, 59);
			X_C_UOM uom = (X_C_UOM)invoiceLine.getC_UOM();
			jsonCuerpoDocumentoItem.put(CreditoFiscal.UNIMEDIDA, uom_getValue(uom));
			jsonCuerpoDocumentoItem.put(CreditoFiscal.DESCRIPCION, description);
			jsonCuerpoDocumentoItem.put(CreditoFiscal.PRECIOUNI, precioUnitario);
			jsonCuerpoDocumentoItem.put(CreditoFiscal.MONTODESCU, Env.ZERO);
			jsonCuerpoDocumentoItem.put(CreditoFiscal.VENTANOSUJ, ventaNoSuj);
			jsonCuerpoDocumentoItem.put(CreditoFiscal.VENTAEXENTA, ventaExenta);
			jsonCuerpoDocumentoItem.put(CreditoFiscal.VENTAGRAVADA, ventaGravada);
			jsonCuerpoDocumentoItem.put(CreditoFiscal.PSV, Env.ZERO);
			jsonCuerpoDocumentoItem.put(CreditoFiscal.NOGRAVADO, ventaNoGravada);
			jsonCuerpoDocumentoItem.put(CreditoFiscal.CODTRIBUTO, codTributo);

			JSONArray jsonTributosArray = new JSONArray();
			if (ventaGravada.compareTo(Env.ZERO) != 0) {
				jsonTributosArray.put(tax_getE_Duties(tax).getValue());
			}
			jsonCuerpoDocumentoItem.put( CreditoFiscal.TRIBUTOS, jsonTributosArray);

			jsonCuerpoDocumentoArray.put(jsonCuerpoDocumentoItem);


			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() + " Finished");
		}		
	}
	
	private void createLinesAcumulated(JSONArray jsonCuerpoDocumentoArray ) {
		int i = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlCuerpoDocumento, null);
			pstmt.setInt(1, invoice.getC_Invoice_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				i++;
				JSONObject jsonCuerpoDocumentoItem = new JSONObject();
				System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + i );

				BigDecimal ventaNoSuj 				= rs.getBigDecimal(CUERPODOCUMENTO_VENTANOSUJETO)==null?Env.ZERO:rs.getBigDecimal(CUERPODOCUMENTO_VENTANOSUJETO);
				BigDecimal ventaExenta 				= rs.getBigDecimal(CUERPODOCUMENTO_VENTAEXENTA)==null?Env.ZERO:rs.getBigDecimal(CUERPODOCUMENTO_VENTAEXENTA);
				BigDecimal ventaGravada 			= rs.getBigDecimal(CUERPODOCUMENTO_VENTAGRAVADA)==null?Env.ZERO:rs.getBigDecimal(CUERPODOCUMENTO_VENTAGRAVADA);
				BigDecimal ventaNoGravada 			= rs.getBigDecimal(CUERPODOCUMENTO_VENTANOGRAVADA)==null?Env.ZERO:rs.getBigDecimal(CUERPODOCUMENTO_VENTANOGRAVADA);
				String productvalue					= rs.getString(CUERPODOCUMENTO_PRODUCTVALUE);
				String name							= rs.getString(CUERPODOCUMENTO_PRODUCTNAME);
				int productID   					= rs.getInt(CUERPODOCUMENTO_PRODUCTID);
				BigDecimal precioUnitario 			= rs.getBigDecimal(CUERPODOCUMENTO_PRICEACTUAL)==null?Env.ZERO:rs.getBigDecimal(CUERPODOCUMENTO_PRICEACTUAL);
				precioUnitario = ventaNoGravada.compareTo(Env.ZERO)!=0? Env.ZERO: precioUnitario;
				BigDecimal qtyInvoiced 				= rs.getBigDecimal(CUERPODOCUMENTO_QTYINVOICED);
				String codTributo 					= "";

				jsonCuerpoDocumentoItem.put(CreditoFiscal.NUMITEM, i);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.TIPOITEM, invoiceLineProductType(productID));
				jsonCuerpoDocumentoItem.put(CreditoFiscal.CANTIDAD, qtyInvoiced);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.CODIGO, productvalue);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.UNIMEDIDA, 59);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.DESCRIPCION, name);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.PRECIOUNI, precioUnitario);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.MONTODESCU, Env.ZERO);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.VENTANOSUJ, ventaNoSuj);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.VENTAEXENTA, ventaExenta);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.VENTAGRAVADA, ventaGravada);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.PSV, Env.ZERO);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.NOGRAVADO, ventaNoGravada);
				jsonCuerpoDocumentoItem.put(CreditoFiscal.CODTRIBUTO, codTributo);

				JSONArray jsonTributosArray = new JSONArray();

				if (ventaGravada.compareTo(Env.ZERO) != 0) {
					jsonTributosArray.put("20");
				}
				jsonCuerpoDocumentoItem.put( CreditoFiscal.TRIBUTOS, jsonTributosArray);

				jsonCuerpoDocumentoArray.put(jsonCuerpoDocumentoItem);			

			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{				
			System.out.println("SQLException for documento " + invoice.getDocumentNo() + " " + e );			
		}
	}
	
}

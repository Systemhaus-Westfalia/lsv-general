package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
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
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrderTax;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MPOS;
import org.compiere.model.MPackageExp;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MTax;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.fefcfacturaelectronicav1.Factura;
import org.shw.lsv.einvoice.fenotaremisionv4.ApendiceItemNotaRemision;
import org.shw.lsv.einvoice.fenotaremisionv4.CuerpoDocumentoItemNotaRemision;
import org.shw.lsv.einvoice.fenotaremisionv4.EmisorNotaRemision;
import org.shw.lsv.einvoice.fenotaremisionv4.IdentificacionNotaRemision;
import org.shw.lsv.einvoice.fenotaremisionv4.NotaRemision;
import org.shw.lsv.einvoice.fenotaremisionv4.ReceptorNotaRemision;
import org.shw.lsv.einvoice.fenotaremisionv4.ResumenNotaRemision;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NotaRemisionFactory extends EDocumentFactory {
	NotaRemision notaRemision;
	MInOut inOut;
	MOrder order; 
	
	public NotaRemisionFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo, MInOut inOut) {
		super(trxName, contextProperties, client, orgInfo);
		this.inOut = inOut;
		notaRemision = new NotaRemision();
		order = (MOrder)inOut.getC_Order();
	}

	public NotaRemision generateEDocument() {
		System.out.println("CreditoFiscal: start generating and filling the Document");
		String result="";

		System.out.println("Instantiate, fill and verify Identificacion");
		IdentificacionNotaRemision identification = notaRemision.getIdentificacion();
		if(identification!=null) {
			notaRemision.errorMessages.append(notaRemision.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaRemision.errorMessages.append(result);
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
		EmisorNotaRemision emisor = notaRemision.getEmisor();
		if(emisor!=null) {
			notaRemision.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaRemision.errorMessages.append(result);
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
		List<CuerpoDocumentoItemNotaRemision> cuerpoDocumento = notaRemision.getCuerpoDocumento();
		if(cuerpoDocumento!=null) {
			notaRemision.fillCuerpoDocumento(jsonInputToFactory);
			
			cuerpoDocumento.stream().forEach( cuerpoDocumentoItem -> { 
				String resultLambda = cuerpoDocumentoItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						notaRemision.errorMessages.append(resultLambda);
					}
				} 
			);
		}

		System.out.println("Instantiate, fill and verify Resumen");
		ResumenNotaRemision resumen = notaRemision.getResumen();
		if(resumen!=null) {
			notaRemision.fillResumen(jsonInputToFactory);
			result = resumen.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaRemision.errorMessages.append(result);
			}
		}
		

		System.out.println("Instantiate, fill and verify ReceptorCreditoFiscal");
		ReceptorNotaRemision receptor = notaRemision.getReceptor();
		if(receptor!=null) {
			notaRemision.fillReceptor(jsonInputToFactory);
			result = receptor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaRemision.errorMessages.append(result);
			}
		}
		
		
		//List<ApendiceItemNotaRemision> apendice = notaRemision.getApendice();
		//if(apendice!=null) {
		//	notaRemision.fillApendice(jsonInputToFactory);
			
		//	apendice.stream().forEach( apendiceItem -> { 
		//		String resultLambda = apendiceItem.validateValues();
		//			if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
		//				notaRemision.errorMessages.append(resultLambda);
		//			}
		//		} 
		//	);
		//}
		
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

		notaRemision.validateValues();
		if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
			notaRemision.errorMessages.append(result);
		}

		System.out.println("CreditoFiscal: end generating and filling the Document");
		return notaRemision;
	}

	@Override
	public void generateJSONInputData() {
		System.out.println("Credito Fiscal: start collecting JSON data for all components");
		jsonInputToFactory = new JSONObject();

		jsonInputToFactory.put(NotaRemision.IDENTIFICACION, generateIdentificationInputData());
		jsonInputToFactory.put(NotaRemision.RECEPTOR, generateReceptorInputData());
		jsonInputToFactory.put(NotaRemision.EMISOR, generateEmisorInputData());
		jsonInputToFactory.put(NotaRemision.RESUMEN, generateResumenInputData());
		jsonInputToFactory.put(NotaRemision.CUERPODOCUMENTO, generateCuerpoDocumentoInputData());
		//jsonInputToFactory.put(NotaRemision.APENDICE, generateApendiceInputData(inOut.getDateAcct()));
		jsonInputToFactory.put(NotaRemision.EXTENSION, generateExtensionInputData());
		
		System.out.println("Generated JSON object from Invoice:");
		System.out.println(jsonInputToFactory.toString());
		System.out.println("Credito Fiscal: end collecting JSON data for all components");
	}
	
	private JSONObject generateIdentificationInputData() {
		String numeroControl = createNumeroControl(inOut, client);
		String codigoGeneracion =  createCodigoGeneracion(inOut);
		JSONObject jsonObjectIdentificacion = new JSONObject();
		Boolean isContigencia = false;
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String horEmi = timeFormat.format(cal.getTime());
		int daysContingencia = getContingenciaDays(inOut.getAD_Client_ID());
		if (TimeUtil.getDaysBetween(inOut.getDateAcct(), TimeUtil.getDay(0))>daysContingencia) {
			isContigencia = true;
		}
		int tipoModelo = isContigencia?NotaRemision.TIPOMODELO_CONTIGENCIA:NotaRemision.TIPOMODELO_NOCONTIGENCIA;
		int tipoOperacion = isContigencia?NotaRemision.TIPOOPERACION_CONTIGENCIA:NotaRemision.TIPOOPERACION_NOCONTIGENCIA;
		jsonObjectIdentificacion.put(NotaRemision.NUMEROCONTROL, numeroControl);
		jsonObjectIdentificacion.put(NotaRemision.CODIGOGENERACION, codigoGeneracion);
		jsonObjectIdentificacion.put(NotaRemision.TIPOMODELO, tipoModelo);
		jsonObjectIdentificacion.put(NotaRemision.TIPOOPERACION, tipoOperacion);
		jsonObjectIdentificacion.put(NotaRemision.FECEMI,  inOut.getDateAcct().toString().substring(0, 10));
		jsonObjectIdentificacion.put(NotaRemision.HOREMI, horEmi);
		jsonObjectIdentificacion.put(NotaRemision.TIPOMONEDA, "USD");
		jsonObjectIdentificacion.put(NotaRemision.AMBIENTE, client_getE_Enviroment(client).getValue());
		
		if (isContigencia) {
			jsonObjectIdentificacion.put(NotaRemision.MOTIVOCONTIN, "Contigencia por fecha de factura");
			jsonObjectIdentificacion.put(NotaRemision.TIPOCONTINGENCIA, 5);
		}
		else
		{
			
			jsonObjectIdentificacion.put(NotaRemision.MOTIVOCONTIN, "");
			jsonObjectIdentificacion.put(NotaRemision.TIPOCONTINGENCIA, "");
		}
		
		System.out.println("CreditoFiscal: end collecting JSON data for Identificacion");
		return jsonObjectIdentificacion;
		
	}
	
	private JSONObject generateEmisorInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Emisor");
		
		int activityID = client.get_ValueAsInt(Columnname_E_Activity_ID);
		X_E_Activity e_Activity = new X_E_Activity(Env.getCtx(), activityID, trxName);
		JSONObject jsonObjectEmisor = new JSONObject();
		jsonObjectEmisor.put(NotaRemision.NIT, client.get_ValueAsString("ei_nit"));
		jsonObjectEmisor.put(NotaRemision.NRC, StringUtils.leftPad(orgInfo.getDUNS().trim().replace("-", ""), 7));
		jsonObjectEmisor.put(NotaRemision.NOMBRE, client.getDescription());
		jsonObjectEmisor.put(NotaRemision.CODACTIVIDAD, e_Activity.getValue());
		jsonObjectEmisor.put(NotaRemision.DESCACTIVIDAD, e_Activity.getName());
		jsonObjectEmisor.put(NotaRemision.NOMBRECOMERCIAL, client.getName());
		jsonObjectEmisor.put(NotaRemision.CODESTABLE, getCodEstable(inOut));
		jsonObjectEmisor.put(NotaRemision.CODPUNTOVENTA, getCodPuntoVenta(inOut));
		
		//jsonObjectEmisor.put(CreditoFiscal.TIPOESTABLECIMIENTO, client_getE_PlantType(client).getValue());

		JSONObject jsonDireccion = new JSONObject();
		jsonDireccion.put(NotaRemision.DEPARTAMENTO,  city_getRegionValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(NotaRemision.DISTRITO,     city_getValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(NotaRemision.MUNICIPIO,     city_getMunicipioValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(NotaRemision.COMPLEMENTO, orgInfo.getC_Location().getAddress1());
		jsonObjectEmisor.put(NotaRemision.DIRECCION, jsonDireccion);
		
		jsonObjectEmisor.put(NotaRemision.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectEmisor.put(NotaRemision.CORREO, client_getEmail(client));

		System.out.println("CreditoFiscal: end collecting JSON data for Emisor");
		return jsonObjectEmisor;
		
	}
	
	private JSONObject generateReceptorInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Receptor");

		MBPartner partner = (MBPartner)inOut.getC_BPartner();
		if (bPartner_getE_Activity(partner).getE_Activity_ID()    <=0 || bPartner_getE_Recipient_Identification(partner).getE_Recipient_Identification_ID() <= 0) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica"; 
			notaRemision.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		
		JSONObject jsonObjectReceptor = new JSONObject();
		if (partner.getTaxID() == null && partner.getDUNS() == null) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica"; 
			notaRemision.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		jsonObjectReceptor.put(NotaRemision.TIPODOCUMENTO,  bPartner_getE_Recipient_Identification(partner).getValue());
		if (partner.getTaxID() != null) {
			jsonObjectReceptor.put(NotaRemision.NUMDOCUMENTO, partner.getTaxID().replace("-", ""));
			jsonObjectReceptor.put(NotaRemision.NOMBRE, partner.getName());			
		}
		//jsonObjectReceptor.put(NotaRemision.NRC, duns.trim().replace("-", ""));
		jsonObjectReceptor.put(NotaRemision.BIENTITULO, order_getTitle(order).getValue() );
		jsonObjectReceptor.put(NotaRemision.NOMBRE, partner.getName());
		jsonObjectReceptor.put(NotaRemision.NOMBRECOMERCIAL, partner.getName());
		jsonObjectReceptor.put(NotaRemision.CORREO, partner.get_ValueAsString("EMail"));
		
		if (bPartner_getE_Activity(partner).getE_Activity_ID()>0) {
			jsonObjectReceptor.put(NotaRemision.CODACTIVIDAD, bPartner_getE_Activity(partner).getValue());
			jsonObjectReceptor.put(NotaRemision.DESCACTIVIDAD, bPartner_getE_Activity(partner).getName());
		}
		JSONObject jsonDireccion = new JSONObject();
		String departamento = "";
		String municipio = "";
		String distrito = "";
		String complemento = "";
		for (MBPartnerLocation partnerLocation : MBPartnerLocation.getForBPartner(contextProperties, partner.getC_BPartner_ID(), trxName)){
			if (partnerLocation.isBillTo() && partnerLocation.getC_Location().getC_Country_ID() == 173) {
				departamento =  city_getRegionValue((MCity)partnerLocation.getC_Location().getC_City());
				distrito =  city_getValue((MCity)partnerLocation.getC_Location().getC_City());
				municipio = city_getMunicipioValue((MCity)partnerLocation.getC_Location().getC_City());
				complemento = (partnerLocation.getC_Location().getAddress1() + " " 
				+ partnerLocation.getC_Location().getAddress2());
				jsonDireccion.put(NotaRemision.DEPARTAMENTO, departamento);
				jsonDireccion.put(NotaRemision.MUNICIPIO, municipio);
				jsonDireccion.put(NotaRemision.DISTRITO, distrito);
				jsonDireccion.put(NotaRemision.COMPLEMENTO, complemento.replace("null", ""));
				jsonObjectReceptor.put(NotaRemision.DIRECCION, jsonDireccion);
				break;
			}
		}		
		
		// In case there is no address
		if (departamento == null) {
			jsonDireccion.put(NotaRemision.DEPARTAMENTO, departamento);
			jsonDireccion.put(NotaRemision.MUNICIPIO, municipio);
			jsonDireccion.put(NotaRemision.COMPLEMENTO, complemento);
		}		
		jsonObjectReceptor.put(NotaRemision.DIRECCION, jsonDireccion);
		jsonObjectReceptor.put(NotaRemision.TELEFONO, partner.get_ValueAsString("phone"));
		jsonObjectReceptor.put(NotaRemision.CORREO, partner.get_ValueAsString("EMail"));		

		System.out.println("CreditoFiscal: end collecting JSON data for Receptor");
		return jsonObjectReceptor;
		
	}
	
	private JSONObject generateResumenInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Resumen");
		BigDecimal totalNoSuj 		= Env.ZERO;
		BigDecimal totalExenta 		= Env.ZERO;
		BigDecimal totalGravada 	= Env.ZERO;	
		BigDecimal totalNoGravada 	= Env.ZERO;		
		BigDecimal ivaRete			= Env.ZERO;
		BigDecimal montotributos	= Env.ZERO;


		String totalLetras=Msg.getAmtInWords(client.getLanguage(), order.getGrandTotal().setScale(2).toString());
		List<MOrderTax> orderTaxes = new Query(contextProperties , MOrderTax.Table_Name , "C_Order_ID=?" , trxName)
				.setParameters(order.getC_Order_ID())
				.list();
		JSONObject jsonObjectResumen = new JSONObject();


		JSONArray jsonTributosArray = new JSONArray();
		for (MOrderTax orderTax:orderTaxes) {
			if (orderTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_RET)) {
				ivaRete = ivaRete.add(orderTax.getTaxAmt().multiply(new BigDecimal(-1)));
				continue;
			}
			JSONObject jsonTributoItem = new JSONObject();		
			if (orderTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_NSUJ)) {
				if (orderTax.getC_Tax().getC_TaxCategory().getCommodityCode() != null &&
						orderTax.getC_Tax().getC_TaxCategory().getCommodityCode().equals(CHARGETYPE_CTAJ))
					totalNoGravada = orderTax.getTaxBaseAmt();	
				else {					
					totalNoSuj = orderTax.getTaxBaseAmt();	
				}
			}
			else if (orderTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_EXT)) {
				totalExenta = orderTax.getTaxBaseAmt();
			}
			else if (orderTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_IVA)) {
				totalGravada = orderTax.getTaxBaseAmt();
				montotributos = montotributos.add(orderTax.getTaxAmt());
				jsonTributoItem.put(NotaRemision.CODIGO, tax_getE_Duties((MTax)orderTax.getC_Tax()).getValue());
				jsonTributoItem.put(NotaRemision.DESCRIPCION, tax_getE_Duties((MTax)orderTax.getC_Tax()).getName());
				jsonTributoItem.put(NotaRemision.VALOR, orderTax.getTaxAmt());
				jsonTributosArray.put(jsonTributoItem); 
			}
		}

		jsonObjectResumen.put(NotaRemision.TRIBUTOS, jsonTributosArray);

		jsonObjectResumen.put(NotaRemision.TOTALNOSUJ, totalNoSuj);
		jsonObjectResumen.put(NotaRemision.TOTALEXENTA, totalExenta);
		jsonObjectResumen.put(NotaRemision.TOTALGRAVADA, totalGravada);
		jsonObjectResumen.put(NotaRemision.SUBTOTALVENTAS, totalGravada.add(totalNoSuj).add(totalExenta));
		jsonObjectResumen.put(NotaRemision.DESCUNOSUJ, Env.ZERO);
		jsonObjectResumen.put(NotaRemision.DESCUEXENTA, Env.ZERO);
		jsonObjectResumen.put(NotaRemision.DESCUGRAVADA, Env.ZERO);
		jsonObjectResumen.put(NotaRemision.PORCENTAJEDESCUENTO, Env.ZERO);
		jsonObjectResumen.put(NotaRemision.SUBTOTAL, totalGravada.add(totalNoSuj).add(totalExenta));
		jsonObjectResumen.put(NotaRemision.IVARETE, ivaRete);
		jsonObjectResumen.put(NotaRemision.MONTOTOTALOPERACION, totalGravada.add(totalNoSuj).add(totalExenta).add(montotributos));
		jsonObjectResumen.put(NotaRemision.TOTALLETRAS, totalLetras);
		String observacionesRaw = order.get_ValueAsString("instructions");
		jsonObjectResumen.put(NotaRemision.OBSERVACIONES, observacionesRaw.length()>0? observacionesRaw : JSONObject.NULL);

		
		jsonObjectResumen.put(NotaRemision.TOTALDESCU, Env.ZERO);
		jsonObjectResumen.put(NotaRemision.IVAPERCI, Env.ZERO);

		


		System.out.println("CreditoFiscal: end collecting JSON data for Resumen");
		return jsonObjectResumen;

	}
	
	private JSONObject generateCuerpoDocumentoInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Cuerpo Documento. Document: " + order.getDocumentNo());
		JSONObject jsonCuerpoDocumento = new JSONObject();
		JSONArray jsonCuerpoDocumentoArray = new JSONArray();
		createLines(jsonCuerpoDocumentoArray);
//		int i = 0;
//		for (MInvoiceLine invoiceLine:invoice.getLines()) {
//		}  
		jsonCuerpoDocumento.put(NotaRemision.CUERPODOCUMENTO, jsonCuerpoDocumentoArray);
		System.out.println("CreditoFiscal: end collecting JSON data for Cuerpo Documento. Document: " + order.getDocumentNo());
		
		return jsonCuerpoDocumento;
	}

	private JSONObject generateExtensionInputData() {
		System.out.println("Credito Fiscal: start collecting JSON data for Extension. Document: " + order.getDocumentNo());
		JSONObject jsonExtension = new JSONObject();
		String observaciones = order.get_ValueAsString(MPackageExp.COLUMNNAME_Instructions);
		
		jsonExtension.put(NotaRemision.NOMBENTREGA, "");
		jsonExtension.put(NotaRemision.DOCUENTREGA, "");
		jsonExtension.put(NotaRemision.NOMBRECIBE, "");
		jsonExtension.put(NotaRemision.DOCURECIBE, "");
		jsonExtension.put(NotaRemision.OBSERVACIONES, observaciones);
		jsonExtension.put(NotaRemision.PLACAVEHICULO, "");
		System.out.println("Credito Fiscal: end collecting JSON data for Extension. Document: " + order.getDocumentNo());
		return jsonExtension;
	}
	
	public String createJsonString() throws Exception {
		System.out.println("CreditoFiscal: start generating JSON object from Document");
    	ObjectMapper objectMapper       = new ObjectMapper();
    	String notaremisionAsStringTmp = objectMapper.writeValueAsString(notaRemision);
        JSONObject notaremisionAsJson  = new JSONObject(notaremisionAsStringTmp);
        
        notaremisionAsJson.remove(NotaRemision.ERRORMESSAGES);

     // Manipulate generated JSON string
        String creditoFiscalAsStringFinal = notaremisionAsJson.toString().
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
		 return notaRemision.errorMessages;
	 }
	
	private void createLines(JSONArray jsonCuerpoDocumentoArray ) {
		int i = 0;
		for (MOrderLine orderLine:order.getLines()) {
			i++;
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + order.getDocumentNo() + ", Line: " + orderLine.getLine() );

			BigDecimal ventaNoSuj 				= Env.ZERO;
			BigDecimal ventaExenta 				= Env.ZERO;
			BigDecimal ventaGravada 			= Env.ZERO;
			BigDecimal ventaNoGravada 			= Env.ZERO;
			BigDecimal ivaItem 					= Env.ZERO;
			BigDecimal precioUnitario 			= orderLine.getPriceEntered();
			String codTributo 					= "";
			MTax tax 							= (MTax)orderLine.getC_Tax();

			boolean isVentanoGravada = (orderLine.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_NSUJ) && 
					orderLine.getC_Charge_ID() > 0 
					&& orderLine.getC_Charge().getC_ChargeType().getValue().equals(CHARGETYPE_CTAJ))?true:false;
			if (orderLine.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_NSUJ)) {
				if (isVentanoGravada) {		
					ventaNoGravada = orderLine.getLineNetAmt();
					precioUnitario = Env.ZERO;
				}
				else {
					ventaNoSuj = orderLine.getLineNetAmt();					
				}
			}
			else if (orderLine.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_EXT))
				ventaExenta = orderLine.getLineNetAmt();
			else if (orderLine.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_IVA) ) {
				ventaGravada = orderLine.getLineNetAmt(); 				
				ivaItem = tax.calculateTax(orderLine.getLineNetAmt(), order.getM_PriceList().isTaxIncluded(), 2);
			}

			JSONObject jsonCuerpoDocumentoItem = new JSONObject();
			String name = "";
			String description = "";
			if (orderLine.getC_Charge_ID() > 0)
				name = orderLine.getC_Charge().getName();
			else if (orderLine.getM_Product_ID()>0)
				name = orderLine.getM_Product().getName();
			if (orderLine.getDescription() != null && orderLine.getDescription().length()>0)
				description = name + " " + orderLine.getDescription();
			else 
				description = name;
			if (description.length()>1499)
				description = description.substring(0, 998);
			jsonCuerpoDocumentoItem.put(NotaRemision.NUMDOCUMENTO, JSONObject.NULL);
			jsonCuerpoDocumentoItem.put(NotaRemision.NUMITEM, i);
			jsonCuerpoDocumentoItem.put(NotaRemision.TIPOITEM, invoiceLineProductType(orderLine.getM_Product_ID()));
			jsonCuerpoDocumentoItem.put(NotaRemision.CANTIDAD, orderLine.getQtyEntered());
			jsonCuerpoDocumentoItem.put(NotaRemision.CODIGO, orderLine.getM_Product_ID()>0? 
					orderLine.getProduct().getValue(): orderLine.getC_Charge().getC_ChargeType().getValue());
			//jsonCuerpoDocumentoItem.put(CreditoFiscal.UNIMEDIDA, 59);
			X_C_UOM uom = (X_C_UOM)orderLine.getC_UOM();
			jsonCuerpoDocumentoItem.put(NotaRemision.UNIMEDIDA, uom_getValue(uom));
			jsonCuerpoDocumentoItem.put(NotaRemision.DESCRIPCION, description);
			jsonCuerpoDocumentoItem.put(NotaRemision.PRECIOUNI, precioUnitario);
			jsonCuerpoDocumentoItem.put(NotaRemision.MONTODESCU, Env.ZERO);
			jsonCuerpoDocumentoItem.put(NotaRemision.VENTANOSUJ, ventaNoSuj);
			jsonCuerpoDocumentoItem.put(NotaRemision.VENTAEXENTA, ventaExenta);
			jsonCuerpoDocumentoItem.put(NotaRemision.VENTAGRAVADA, ventaGravada);
			jsonCuerpoDocumentoItem.put(NotaRemision.PSV, Env.ZERO);
			jsonCuerpoDocumentoItem.put(NotaRemision.NOGRAVADO, ventaNoGravada);
			jsonCuerpoDocumentoItem.put(NotaRemision.CODTRIBUTO, codTributo);

			JSONArray jsonTributosArray = new JSONArray();
			if (ventaGravada.compareTo(Env.ZERO) != 0) {
				jsonTributosArray.put(tax_getE_Duties(tax).getValue());
			}
			jsonCuerpoDocumentoItem.put( NotaRemision.TRIBUTOS, jsonTributosArray);

			jsonCuerpoDocumentoArray.put(jsonCuerpoDocumentoItem);


			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + order.getDocumentNo() + ", Line: " + orderLine.getLine() + " Finished");
		}		
	}
	
	
	
	public String createNumeroControl(MInOut inOut, MClient client) {
		String prefix = Optional.ofNullable(inOut.getC_DocType().getDefiniteSequence().getPrefix()).orElse("");
		String documentno = inOut.getDocumentNo().replace(prefix,"");
		String suffix = Optional.ofNullable(inOut.getC_DocType().getDefiniteSequence().getSuffix()).orElse("");	
		documentno = documentno.replace(suffix,"");
		String idIdentification  = StringUtils.leftPad(documentno, 15,"0");
		String pos = "";
			MPOS mpos = new Query(inOut.getCtx(), MPOS.Table_Name, "AD_Org_ID=? ", trxName)
					.setParameters(inOut.getAD_Org_ID())
					.setOnlyActiveRecords(true)
					.setOrderBy("C_POS_ID")
					.first();
			pos = mpos.get_ValueAsString("ei_POS");
		MOrgInfo orgInfo = MOrgInfo.get(inOut.getCtx(), inOut.getAD_Org_ID(), inOut.get_TrxName());
		String idPosCompany = getCodEstable(inOut) + mpos.get_ValueAsString("ei_POS");
		String numeroControl = "DTE-" + docType_getE_DocType((MDocType)inOut.getC_DocType()).getValue()
				+ "-"+ StringUtils.leftPad(idPosCompany, 8,"0") + "-"+ idIdentification;
		return numeroControl;
	}
	
	

	
}

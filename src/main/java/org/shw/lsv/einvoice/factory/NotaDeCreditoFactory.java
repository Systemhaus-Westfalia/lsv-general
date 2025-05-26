package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCity;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MPaymentTerm;
import org.compiere.model.MTax;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.fencnotadecreditov1.CuerpoDocumentoItemNotaDeCredito;
import org.shw.lsv.einvoice.fencnotadecreditov1.DocumentoRelacionadoItemNotaDeCredito;
import org.shw.lsv.einvoice.fencnotadecreditov1.EmisorNotaDeCredito;
import org.shw.lsv.einvoice.fencnotadecreditov1.IdentificacionNotaDeCredito;
import org.shw.lsv.einvoice.fencnotadecreditov1.NotaDeCredito;
import org.shw.lsv.einvoice.fencnotadecreditov1.ReceptorNotaDeCredito;
import org.shw.lsv.einvoice.fencnotadecreditov1.ResumenNotaDeCredito;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NotaDeCreditoFactory extends EDocumentFactory {
	NotaDeCredito notaDeCredito;
	MInvoice invoice;
	
	public NotaDeCreditoFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo, MInvoice invoice) {
		super(trxName, contextProperties, client, orgInfo);
		this.invoice = invoice;
		notaDeCredito = new NotaDeCredito();
	}

	public NotaDeCredito generateEDocument() {
		System.out.println("Nota de Credito: start generating and filling the Document");
		String result="";

		System.out.println("Instantiate, fill and verify Identificacion");		
		IdentificacionNotaDeCredito identification = notaDeCredito.getIdentificacion();
		if(identification!=null) {
			notaDeCredito.errorMessages.append(notaDeCredito.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeCredito.errorMessages.append(result);
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
		EmisorNotaDeCredito emisor = notaDeCredito.getEmisor();
		if(emisor!=null) {
			notaDeCredito.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeCredito.errorMessages.append(result);
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
		List<DocumentoRelacionadoItemNotaDeCredito> documentosRelacionados = notaDeCredito.getDocumentoRelacionado();
		if(documentosRelacionados!=null) {
			notaDeCredito.fillDocumentosRelacionados(jsonInputToFactory);
			
			documentosRelacionados.stream().forEach( documentoRelacionadoItem -> { 
				String resultLambda = documentoRelacionadoItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						notaDeCredito.errorMessages.append(resultLambda);
					}
				} 
			);
		}
		
		System.out.println("Instantiate, fill and verify Documento Relacionados");
		List<CuerpoDocumentoItemNotaDeCredito> cuerpoDocumento = notaDeCredito.getCuerpoDocumento();
		if(cuerpoDocumento!=null) {
			notaDeCredito.fillCuerpoDocumento(jsonInputToFactory);
			
			cuerpoDocumento.stream().forEach( cuerpoDocumentoItem -> { 
				String resultLambda = cuerpoDocumentoItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						notaDeCredito.errorMessages.append(resultLambda);
					}
				} 
			);
		}

		System.out.println("Instantiate, fill and verify Resumen");
		ResumenNotaDeCredito resumen = notaDeCredito.getResumen();
		if(resumen!=null) {
			notaDeCredito.fillResumen(jsonInputToFactory);
			result = resumen.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeCredito.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Receptor");
		ReceptorNotaDeCredito receptor = notaDeCredito.getReceptor();
		if(receptor!=null) {
			notaDeCredito.fillReceptor(jsonInputToFactory);
			result = receptor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeCredito.errorMessages.append(result);
			}
		}
		
		
//		Extension extension = factura.getExtension();
//		if(extension!=null) {
//			factura.fillExtension(jsonInputToFactory);
//			result = extension.validateValues();
//			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
//				errorMessages.append(result);
//			}
//		}
		
//		List<ApendiceItem> apendice = factura.getApendice();
//		if(apendice!=null) {
//			factura.fillApendice(jsonInputToFactory);
//			
//			apendice.stream().forEach( apendiceItem -> { 
//				String resultLambda = apendiceItem.validateValues();
//					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
//						errorMessages.append(resultLambda);
//					}
//				} 
//			);
//		}
		
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

		notaDeCredito.validateValues();
		if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
			notaDeCredito.errorMessages.append(result);
		}

		System.out.println("Nota de Credito: end generating and filling the Document");	
		return notaDeCredito;
	}

	@Override
	public void generateJSONInputData() {
		System.out.println("Nota  de Credito: start collecting JSON data for all components");
		jsonInputToFactory = new JSONObject();

		jsonInputToFactory.put(NotaDeCredito.IDENTIFICACION, generateIdentificationInputData());
		jsonInputToFactory.put(NotaDeCredito.RECEPTOR, generateReceptorInputData());
		jsonInputToFactory.put(NotaDeCredito.EMISOR, generateEmisorInputData());
		jsonInputToFactory.put(NotaDeCredito.RESUMEN, generateResumenInputData());
		jsonInputToFactory.put(NotaDeCredito.CUERPODOCUMENTO, generateCuerpoDocumentoInputData());
		jsonInputToFactory.put(NotaDeCredito.DOCUMENTORELACIONADO, generateDocumentoRelacionadoInputData());
		
		System.out.println("Generated JSON object from Invoice:");
		System.out.println(jsonInputToFactory.toString());
		System.out.println("Nota  de Credito: end collecting JSON data for all components");
	}
	
	private JSONObject generateIdentificationInputData() {
		System.out.println("Start collecting JSON data for Identificacion");
		
		String motivoContin      = "";
		Integer tipoContingencia = 0;
		int tipoModelo           = 1;
		int tipoOperacion        = 1;
		//if (TimeUtil.getDaysBetween(invoice.getDateAcct(), TimeUtil.getDay(0))>=3) {
		//	tipoModelo       = 2;
		//	tipoOperacion    = 2;	
		//	motivoContin     = "Contigencia por fecha de factura";	
		//	tipoContingencia = 5;
		//}

		
		String numeroControl = createNumeroControl(invoice, client);
		
		String codigoGeneracion = createCodigoGeneracion(invoice);
		String horEmi = gethorEmi();
		String fecEmi = getfecEmi();
		JSONObject jsonObjectIdentificacion = new JSONObject();
		jsonObjectIdentificacion.put(NotaDeCredito.MOTIVOCONTIN, motivoContin);
		jsonObjectIdentificacion.put(NotaDeCredito.TIPOCONTINGENCIA, tipoContingencia);
		jsonObjectIdentificacion.put(NotaDeCredito.NUMEROCONTROL, numeroControl);
		jsonObjectIdentificacion.put(NotaDeCredito.CODIGOGENERACION, codigoGeneracion);
		jsonObjectIdentificacion.put(NotaDeCredito.TIPOMODELO, tipoModelo);
		jsonObjectIdentificacion.put(NotaDeCredito.TIPOOPERACION, tipoOperacion);
		jsonObjectIdentificacion.put(NotaDeCredito.FECEMI, fecEmi);
		jsonObjectIdentificacion.put(NotaDeCredito.HOREMI, horEmi);
		jsonObjectIdentificacion.put(NotaDeCredito.TIPOMONEDA, "USD");
		jsonObjectIdentificacion.put(NotaDeCredito.AMBIENTE, client_getE_Enviroment(client).getValue());

		System.out.println("Finish collecting JSON data for Identificacion");
		return jsonObjectIdentificacion;
		
	}
	
	private JSONObject generateEmisorInputData() {
		System.out.println("Start collecting JSON data for Emisor");
		
		JSONObject jsonObjectEmisor = new JSONObject();
		jsonObjectEmisor.put(NotaDeCredito.NIT, orgInfo.getTaxID().replace("-", ""));
		jsonObjectEmisor.put(NotaDeCredito.NRC, StringUtils.leftPad(orgInfo.getDUNS().trim().replace("-", ""), 7));
		jsonObjectEmisor.put(NotaDeCredito.NOMBRE, client.getDescription());
		jsonObjectEmisor.put(NotaDeCredito.CODACTIVIDAD, client_getE_Activity(client).getValue());
		jsonObjectEmisor.put(NotaDeCredito.DESCACTIVIDAD, client_getE_Activity(client).getName());
		jsonObjectEmisor.put(NotaDeCredito.NOMBRECOMERCIAL, client.getName());
		jsonObjectEmisor.put(NotaDeCredito.TIPOESTABLECIMIENTO,  client_getE_PlantType(client).getValue());

		JSONObject jsonDireccion = new JSONObject();
		jsonDireccion.put(NotaDeCredito.DEPARTAMENTO, city_getRegionValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(NotaDeCredito.MUNICIPIO, city_getValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(NotaDeCredito.COMPLEMENTO, orgInfo.getC_Location().getAddress1());
		jsonObjectEmisor.put(NotaDeCredito.DIRECCION, jsonDireccion);
		
		jsonObjectEmisor.put(NotaDeCredito.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectEmisor.put(NotaDeCredito.CORREO, client_getEmail(client));

		System.out.println("Finish collecting JSON data for Emisor");
		return jsonObjectEmisor;
		
		
	}
	
	private JSONObject generateReceptorInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Receptor");

		MBPartner partner = (MBPartner)invoice.getC_BPartner();
		if (bPartner_getE_Activity(partner).getE_Activity_ID()<=0 ||  bPartner_getE_Recipient_Identification(partner).getE_Recipient_Identification_ID() <= 0) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica"; 
			notaDeCredito.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		
		JSONObject jsonObjectReceptor = new JSONObject();
		if (partner.getTaxID() == null && partner.getDUNS() == null) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica"; 
			notaDeCredito.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		jsonObjectReceptor.put(NotaDeCredito.NIT, partner.getTaxID().replace("-", ""));
		jsonObjectReceptor.put(NotaDeCredito.NRC, partner.getDUNS().trim().replace("-", ""));
		jsonObjectReceptor.put(NotaDeCredito.NOMBRE, partner.getName());
		
		if (bPartner_getE_Activity(partner).getE_Activity_ID()>0) {
			jsonObjectReceptor.put(NotaDeCredito.CODACTIVIDAD, bPartner_getE_Activity(partner).getValue());
			jsonObjectReceptor.put(NotaDeCredito.DESCACTIVIDAD, bPartner_getE_Activity(partner).getName());
		}

		JSONObject jsonDireccion = new JSONObject();
		String departamento = "";
		String municipio = "";
		String complemento = "";
		for (MBPartnerLocation partnerLocation : MBPartnerLocation.getForBPartner(contextProperties, partner.getC_BPartner_ID(), trxName)){
			if (partnerLocation.isBillTo()) {
				departamento =  city_getRegionValue((MCity)partnerLocation.getC_Location().getC_City());
				municipio =  city_getValue((MCity)partnerLocation.getC_Location().getC_City());
				complemento = (partnerLocation.getC_Location().getAddress1() + " " + partnerLocation.getC_Location().getAddress2());
				jsonDireccion.put(NotaDeCredito.DEPARTAMENTO, departamento);
				jsonDireccion.put(NotaDeCredito.MUNICIPIO, municipio);
				jsonDireccion.put(NotaDeCredito.COMPLEMENTO, complemento);
				break;
			}
		}		
		
		// In case there is no address
		if (departamento == null) {
			jsonDireccion.put(NotaDeCredito.DEPARTAMENTO, departamento);
			jsonDireccion.put(NotaDeCredito.MUNICIPIO, municipio);
			jsonDireccion.put(NotaDeCredito.COMPLEMENTO, complemento);
		}		
		jsonObjectReceptor.put(NotaDeCredito.DIRECCION, jsonDireccion);
		
		jsonObjectReceptor.put(NotaDeCredito.TELEFONO, partner.get_ValueAsString("phone"));
		jsonObjectReceptor.put(NotaDeCredito.CORREO, partner.get_ValueAsString("EMail"));		

		System.out.println("NotaDeCredito: end collecting JSON data for Receptor");
		return jsonObjectReceptor;
		
	}
	
	private JSONObject generateResumenInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Resumen");
		BigDecimal totalNoSuj 	= Env.ZERO;
		BigDecimal totalExenta 	= Env.ZERO;
		BigDecimal totalGravada = Env.ZERO;	
		BigDecimal totalNoGravada = Env.ZERO;	
		BigDecimal ivaRete1 	= Env.ZERO;

		String totalLetras=Msg.getAmtInWords(client.getLanguage(), invoice.getGrandTotal().setScale(2).toString());

		List<MInvoiceTax> invoiceTaxes = new Query(contextProperties , MInvoiceTax.Table_Name , "C_Invoice_ID=?" , trxName)
				.setParameters(invoice.getC_Invoice_ID())
				.list();
		JSONObject jsonObjectResumen = new JSONObject();


		JSONArray jsonTributosArray = new JSONArray();
		for (MInvoiceTax invoiceTax:invoiceTaxes) {
			if (invoiceTax.getC_Tax().getTaxIndicator().equals("RET")) {
				ivaRete1 = ivaRete1.add(invoiceTax.getTaxAmt().multiply(new BigDecimal(-1)));
				continue;
			}
			JSONObject jsonTributoItem = new JSONObject();		
			if (invoiceTax.getC_Tax().getTaxIndicator().equals("NSUJ")) 
			{
				if (invoiceTax.getC_Tax().getC_TaxCategory().getCommodityCode() != null &&
					invoiceTax.getC_Tax().getC_TaxCategory().getCommodityCode().equals(CHARGETYPE_CTAJ))
				totalNoGravada = invoiceTax.getTaxBaseAmt();	
			else
				totalNoSuj = invoiceTax.getTaxBaseAmt();	
			}
			else if (invoiceTax.getC_Tax().getTaxIndicator().equals("EXT")) {
				totalExenta = invoiceTax.getTaxBaseAmt();
				jsonTributoItem.put(NotaDeCredito.CODIGO,   tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getValue());
				jsonTributoItem.put(NotaDeCredito.DESCRIPCION, tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getName());
				jsonTributoItem.put(NotaDeCredito.VALOR, invoiceTax.getTaxAmt());
			}
			else if (invoiceTax.getC_Tax().getTaxIndicator().equals("IVA")) {
				totalGravada = invoiceTax.getTaxBaseAmt();
				jsonTributoItem.put(NotaDeCredito.CODIGO, tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getValue());
				jsonTributoItem.put(NotaDeCredito.DESCRIPCION, tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getName());
				jsonTributoItem.put(NotaDeCredito.VALOR, invoiceTax.getTaxAmt());
				jsonTributosArray.put(jsonTributoItem); 
			}
		}
		jsonObjectResumen.put(NotaDeCredito.TRIBUTOS, jsonTributosArray);


		jsonObjectResumen.put(NotaDeCredito.TOTALNOSUJ, totalNoSuj);
		jsonObjectResumen.put(NotaDeCredito.TOTALEXENTA, totalExenta);
		jsonObjectResumen.put(NotaDeCredito.TOTALGRAVADA, totalGravada);
		jsonObjectResumen.put(NotaDeCredito.SUBTOTALVENTAS, totalGravada.add(totalNoSuj).add(totalExenta).add(totalNoGravada));
		jsonObjectResumen.put(NotaDeCredito.DESCUNOSUJ, Env.ZERO);
		jsonObjectResumen.put(NotaDeCredito.DESCUEXENTA, Env.ZERO);
		jsonObjectResumen.put(NotaDeCredito.DESCUGRAVADA, Env.ZERO);
		jsonObjectResumen.put(NotaDeCredito.PORCENTAJEDESCUENTO, Env.ZERO);
		jsonObjectResumen.put(NotaDeCredito.SUBTOTAL, totalGravada.add(totalNoSuj).add(totalExenta).add(totalNoGravada));
		jsonObjectResumen.put(NotaDeCredito.IVARETE1, ivaRete1);
		jsonObjectResumen.put(NotaDeCredito.MONTOTOTALOPERACION, invoice.getGrandTotal());
		jsonObjectResumen.put(NotaDeCredito.TOTALNOGRAVADO, totalNoGravada);
		jsonObjectResumen.put(NotaDeCredito.TOTALPAGAR, invoice.getGrandTotal());
		jsonObjectResumen.put(NotaDeCredito.TOTALLETRAS, totalLetras);
		jsonObjectResumen.put(NotaDeCredito.SALDOFAVOR, Env.ZERO);
		jsonObjectResumen.put(NotaDeCredito.CONDICIONOPERACION, NotaDeCredito.CONDICIONOPERACION_A_CREDITO);
		jsonObjectResumen.put(NotaDeCredito.TOTALDESCU, Env.ZERO);
		jsonObjectResumen.put(NotaDeCredito.RETERENTA, Env.ZERO);
		jsonObjectResumen.put(NotaDeCredito.IVAPERCI1, Env.ZERO);

		JSONArray jsonArrayPagos = new JSONArray();
		JSONObject jsonPago = new JSONObject();
		jsonPago.put(NotaDeCredito.CODIGO, "05");
		jsonPago.put(NotaDeCredito.MONTOPAGO, invoice.getGrandTotal());
		jsonPago.put(NotaDeCredito.REFERENCIA, "Transferencia_ Dep??sito Bancario");
		jsonPago.put(NotaDeCredito.PLAZO, paymentterm_getE_TimeSpan((MPaymentTerm)invoice.getC_PaymentTerm()).getValue());
		jsonPago.put(NotaDeCredito.PERIODO, invoice.getC_PaymentTerm().getNetDays());
		jsonArrayPagos.put(jsonPago);

		jsonObjectResumen.put(NotaDeCredito.PAGOS, jsonArrayPagos);


		System.out.println("NotaDeCredito: end collecting JSON data for Resumen");
		return jsonObjectResumen;

	}
	
	private JSONObject generateCuerpoDocumentoInputData() {
		System.out.println("Start collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		JSONObject jsonCuerpoDocumento = new JSONObject();
		JSONArray jsonCuerpoDocumentoArray = new JSONArray();
		int i=0;
		
		for (MInvoiceLine invoiceLine:invoice.getLines()) { 
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() );
			i++;
			BigDecimal ventaNoSuj 	= Env.ZERO;
			BigDecimal ventaExenta 	= Env.ZERO;
			BigDecimal ventaGravada = Env.ZERO;
			BigDecimal ventaNoGravada = Env.ZERO;
			BigDecimal ivaItem 		= Env.ZERO;
			MTax tax = (MTax)invoiceLine.getC_Tax();
			boolean ventanoGravada = (invoiceLine.getC_Tax().getTaxIndicator().equals("NSUJ") && 
					invoiceLine.getC_Charge_ID() > 0 
					&& invoiceLine.getC_Charge().getC_ChargeType().getValue().equals("CTAJ"))?true:false;
			
			if (invoiceLine.getC_Tax().getTaxIndicator().equals("NSUJ")) {
				if (ventanoGravada)
					ventaNoGravada = invoiceLine.getLineNetAmt();
				else
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
			MInvoice refInvoice = invoiceLine_getRef_InvoiceLine_getC_Invoice(invoiceLine);
            String numerodocumentno = invoice_ei_codigoGeneracion(refInvoice)!=null?
            		invoice_ei_codigoGeneracion(refInvoice):
            			refInvoice.getDocumentNo();  
			jsonCuerpoDocumentoItem.put(NotaDeCredito.NUMITEM, i);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.TIPOITEM, 2);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.NUMERODOCUMENTO, numerodocumentno);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.CANTIDAD, invoiceLine.getQtyInvoiced());
			jsonCuerpoDocumentoItem.put(NotaDeCredito.CODIGO, invoiceLine.getM_Product_ID()>0? invoiceLine.getProduct().getValue(): invoiceLine.getC_Charge().getName());
			jsonCuerpoDocumentoItem.put(NotaDeCredito.CODTRIBUTO, "");  // String codTributo = "20";
			jsonCuerpoDocumentoItem.put(NotaDeCredito.UNIMEDIDA, 59);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.DESCRIPCION, invoiceLine.getM_Product_ID()>0?invoiceLine.getM_Product().getName():invoiceLine.getC_Charge().getName());
			jsonCuerpoDocumentoItem.put(NotaDeCredito.PRECIOUNI, invoiceLine.getPriceActual());
			jsonCuerpoDocumentoItem.put(NotaDeCredito.MONTODESCU, Env.ZERO);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.VENTANOSUJ, ventaNoSuj);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.VENTAEXENTA, ventaExenta);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.NOGRAVADO, ventaNoGravada);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.VENTAGRAVADA, ventaGravada);	

			JSONArray jsonTributosArray = new JSONArray();
			if (ventaGravada.compareTo(Env.ZERO) != 0) {
				jsonTributosArray.put(tax_getE_Duties(tax).getValue());
			}
			jsonCuerpoDocumentoItem.put( NotaDeCredito.TRIBUTOS, jsonTributosArray);

			jsonCuerpoDocumentoArray.put(jsonCuerpoDocumentoItem);
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() + " Finished");

		}  
		
		jsonCuerpoDocumento.put(NotaDeCredito.CUERPODOCUMENTO, jsonCuerpoDocumentoArray);
		System.out.println("Finish collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		
		return jsonCuerpoDocumento;
	}
	
	private JSONObject generateDocumentoRelacionadoInputData() {
		System.out.println("Start collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		JSONObject jsonDocumentoRelacionado = new JSONObject();
		JSONArray jsonDocumentoRelacionadoArray = new JSONArray();

		HashMap<Integer,MInvoice> invoiceIds = new HashMap<Integer, MInvoice>();
		for (MInvoiceLine invoiceLine:invoice.getLines()) {
			MInvoiceLine invoiceLineOrg = new MInvoiceLine(contextProperties, invoiceLine.getRef_InvoiceLine_ID(), null);
			invoiceIds.put(invoiceLineOrg.getC_Invoice_ID(), (MInvoice)invoiceLineOrg.getC_Invoice());
		}  
		for (MInvoice invoiceOrginal : invoiceIds.values()) {
			JSONObject jsonDocumentoRelacionadoItem = new JSONObject();
			jsonDocumentoRelacionadoItem.put(NotaDeCredito.TIPODOCUMENTO, docType_getE_DocType((MDocType)invoiceOrginal.getC_DocType()).getValue());
			int tipoGeneracion = invoice_ei_codigoGeneracion(invoiceOrginal) == ""? 1:2;
			jsonDocumentoRelacionadoItem.put(NotaDeCredito.TIPOGENERACION, tipoGeneracion);
			String documentno = tipoGeneracion==2?   invoice_ei_codigoGeneracion(invoiceOrginal)   :invoiceOrginal.getDocumentNo();
			jsonDocumentoRelacionadoItem.put(NotaDeCredito.NUMERODOCUMENTO, documentno);
			jsonDocumentoRelacionadoItem.put(NotaDeCredito.FECEMI, invoiceOrginal.getDateAcct().toString().substring(0, 10));
			jsonDocumentoRelacionadoArray.put(jsonDocumentoRelacionadoItem);
		}
		
		jsonDocumentoRelacionado.put(NotaDeCredito.DOCUMENTORELACIONADO, jsonDocumentoRelacionadoArray);
		System.out.println("Finish collecting JSON data for Documento Relacionado. Document: " + invoice.getDocumentNo());
		
		return jsonDocumentoRelacionado;
	}

	public String createJsonString() throws Exception {
		System.out.println("Nota de Credito: start generating JSON object from Document");
		ObjectMapper objectMapper      = new ObjectMapper();
		String notaDeCreditoAsString   = objectMapper.writeValueAsString(notaDeCredito);
		JSONObject notaDeCreditoAsJson = new JSONObject(notaDeCreditoAsString);

		notaDeCreditoAsJson.remove(NotaDeCredito.ERRORMESSAGES);

		// Manipulate generated JSON string
		String notaDeCreditoAsStringFinal = notaDeCreditoAsJson.toString().
				replace(":[],", ":null,").
        		replace("\"telefono\":\"\"", "\"telefono\":null").
        		replace("\"motivoContin\":\"\"", "\"motivoContin\":null").
        		replace("\"tipoContingencia\":0", "\"tipoContingencia\":null").
        		replace("\"periodo\":0,\"plazo\":\"01\"", "\"periodo\":null,\"plazo\":null").
				replace("\"documentoRelacionado\":[]", "\"documentoRelacionado\":null").
				replace("\"ventaTercero\":{\"nit\":null,\"nombre\":null},", "\"ventaTercero\":null,").
				replace("\"tributos\":[{\"descripcion\":null,\"codigo\":null,\"valor\":null}]", "\"tributos\":null").
				replace("\"extension\":{\"docuEntrega\":null,\"placaVehiculo\":null,\"observaciones\":null,\"nombRecibe\":null,\"nombEntrega\":null,\"docuRecibe\":null},", 
						"\"extension\":null,");

		System.out.println("Nota de Credito: generated JSON object from Document:");
		System.out.println(notaDeCreditoAsStringFinal);
		System.out.println("Nota de Credito: end generating JSON object from Document");
		return notaDeCreditoAsStringFinal;	
	}

	public String getNumeroControl(Integer id, MOrgInfo orgInfo, String prefix) {
		String idIdentification  = StringUtils.leftPad(id.toString(), 15,"0");
		String duns = orgInfo.getDUNS().replace("-", "");
		String numeroControl = prefix + StringUtils.leftPad(duns.trim(), 8,"0") + "-"+ idIdentification;
		return numeroControl;
	}
	

	@Override
	public StringBuffer getEDocumentErrorMessages() {
		 return notaDeCredito.errorMessages;
	 }
}

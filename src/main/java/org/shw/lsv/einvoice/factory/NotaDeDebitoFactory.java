package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
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
import org.shw.lsv.einvoice.fendnotadedebitov3.CuerpoDocumentoItemNotaDeDebito;
import org.shw.lsv.einvoice.fendnotadedebitov3.DocumentoRelacionadoItemNotaDeDebito;
import org.shw.lsv.einvoice.fendnotadedebitov3.EmisorNotaDeDebito;
import org.shw.lsv.einvoice.fendnotadedebitov3.IdentificacionNotaDeDebito;
import org.shw.lsv.einvoice.fendnotadedebitov3.NotaDeDebito;
import org.shw.lsv.einvoice.fendnotadedebitov3.ReceptorNotaDeDebito;
import org.shw.lsv.einvoice.fendnotadedebitov3.ResumenNotaDeDebito;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NotaDeDebitoFactory extends EDocumentFactory {
	NotaDeDebito notaDeDebito;
	MInvoice invoice;
	
	public NotaDeDebitoFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo, MInvoice invoice) {
		super(trxName, contextProperties, client, orgInfo);
		this.invoice = invoice;
		notaDeDebito = new NotaDeDebito();
	}

	public NotaDeDebito generateEDocument() {
		System.out.println("Nota de Debito: start generating and filling the Document");
		String result="";

		System.out.println("Instantiate, fill and verify Identificacion");		
		IdentificacionNotaDeDebito identification = notaDeDebito.getIdentificacion();
		if(identification!=null) {
			notaDeDebito.errorMessages.append(notaDeDebito.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeDebito.errorMessages.append(result);
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
		EmisorNotaDeDebito emisor = notaDeDebito.getEmisor();
		if(emisor!=null) {
			notaDeDebito.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeDebito.errorMessages.append(result);
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
		List<DocumentoRelacionadoItemNotaDeDebito> documentosRelacionados = notaDeDebito.getDocumentoRelacionado();
		if(documentosRelacionados!=null) {
			notaDeDebito.fillDocumentosRelacionados(jsonInputToFactory);
			
			documentosRelacionados.stream().forEach( documentoRelacionadoItem -> { 
				String resultLambda = documentoRelacionadoItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						notaDeDebito.errorMessages.append(resultLambda);
					}
				} 
			);
		}
		
		System.out.println("Instantiate, fill and verify Documento Relacionados");
		List<CuerpoDocumentoItemNotaDeDebito> cuerpoDocumento = notaDeDebito.getCuerpoDocumento();
		if(cuerpoDocumento!=null) {
			notaDeDebito.fillCuerpoDocumento(jsonInputToFactory);
			
			cuerpoDocumento.stream().forEach( cuerpoDocumentoItem -> { 
				String resultLambda = cuerpoDocumentoItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						notaDeDebito.errorMessages.append(resultLambda);
					}
				} 
			);
		}

		System.out.println("Instantiate, fill and verify Resumen");
		ResumenNotaDeDebito resumen = notaDeDebito.getResumen();
		if(resumen!=null) {
			notaDeDebito.fillResumen(jsonInputToFactory);
			result = resumen.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeDebito.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Receptor");
		ReceptorNotaDeDebito receptor = notaDeDebito.getReceptor();
		if(receptor!=null) {
			notaDeDebito.fillReceptor(jsonInputToFactory);
			result = receptor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeDebito.errorMessages.append(result);
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

		notaDeDebito.validateValues();
		if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
			notaDeDebito.errorMessages.append(result);
		}

		System.out.println("Nota de Debito: end generating and filling the Document");	
		return notaDeDebito;
	}

	@Override
	public void generateJSONInputData() {
		System.out.println("Nota  de Credito: start collecting JSON data for all components");
		jsonInputToFactory = new JSONObject();

		jsonInputToFactory.put(NotaDeDebito.IDENTIFICACION, generateIdentificationInputData());
		jsonInputToFactory.put(NotaDeDebito.RECEPTOR, generateReceptorInputData());
		jsonInputToFactory.put(NotaDeDebito.EMISOR, generateEmisorInputData());
		jsonInputToFactory.put(NotaDeDebito.RESUMEN, generateResumenInputData());
		jsonInputToFactory.put(NotaDeDebito.DOCUMENTORELACIONADO, generateDocumentoRelacionadoInputData());
		jsonInputToFactory.put(NotaDeDebito.CUERPODOCUMENTO, generateCuerpoDocumentoInputData());
		
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
		if (TimeUtil.getDaysBetween(invoice.getDateAcct(), TimeUtil.getDay(0))>=3) {
			tipoModelo       = 2;
			tipoOperacion    = 2;	
			motivoContin     = "Contigencia por fecha de factura";	
			tipoContingencia = 5;
		}

		String prefix = invoice.getC_DocType().getDefiniteSequence().getPrefix();
		String documentno = invoice.getDocumentNo().replace(prefix,"");
		String suffix = Optional.ofNullable(invoice.getC_DocType().getDefiniteSequence().getSuffix()).orElse("");	
		documentno = documentno.replace(suffix,"");
		String idIdentification  = StringUtils.leftPad(documentno, 15,"0");
		String duns = orgInfo.getDUNS().replace("-", "");
		
		String numeroControl = "DTE-" + docType_getE_DocType((MDocType)invoice.getC_DocType()).getValue()
				+ "-"+ StringUtils.leftPad(duns.trim(), 8,"0") + "-"+ idIdentification;
		
		Integer invoiceID = invoice.get_ID();
		Integer clientID = (Integer)client.getAD_Client_ID();
		String codigoGeneracion = StringUtils.leftPad(clientID.toString(), 8, "0") + "-0000-0000-0000-" + StringUtils.leftPad(invoiceID.toString(), 12,"0");
		
		JSONObject jsonObjectIdentificacion = new JSONObject();
		jsonObjectIdentificacion.put(NotaDeDebito.MOTIVOCONTIN, motivoContin);
		jsonObjectIdentificacion.put(NotaDeDebito.TIPOCONTINGENCIA, tipoContingencia);
		jsonObjectIdentificacion.put(NotaDeDebito.NUMEROCONTROL, numeroControl);
		jsonObjectIdentificacion.put(NotaDeDebito.CODIGOGENERACION, codigoGeneracion);
		jsonObjectIdentificacion.put(NotaDeDebito.TIPOMODELO, tipoModelo);
		jsonObjectIdentificacion.put(NotaDeDebito.TIPOOPERACION, tipoOperacion);
		jsonObjectIdentificacion.put(NotaDeDebito.FECEMI, invoice.getDateAcct().toString().substring(0, 10));
		jsonObjectIdentificacion.put(NotaDeDebito.HOREMI, "00:00:00");
		jsonObjectIdentificacion.put(NotaDeDebito.TIPOMONEDA, "USD");
		jsonObjectIdentificacion.put(NotaDeDebito.AMBIENTE, client_getE_Enviroment(client).getValue());

		System.out.println("Finish collecting JSON data for Identificacion");
		return jsonObjectIdentificacion;
		
	}
	
	private JSONObject generateEmisorInputData() {
		System.out.println("Start collecting JSON data for Emisor");
		
		JSONObject jsonObjectEmisor = new JSONObject();
		jsonObjectEmisor.put(NotaDeDebito.NIT, orgInfo.getTaxID().replace("-", ""));
		jsonObjectEmisor.put(NotaDeDebito.NRC, StringUtils.leftPad(orgInfo.getDUNS().trim().replace("-", ""), 7));
		jsonObjectEmisor.put(NotaDeDebito.NOMBRE, client.getName());
		jsonObjectEmisor.put(NotaDeDebito.CODACTIVIDAD, client_getE_Activity(client).getValue());
		jsonObjectEmisor.put(NotaDeDebito.DESCACTIVIDAD, client_getE_Activity(client).getName());
		jsonObjectEmisor.put(NotaDeDebito.NOMBRECOMERCIAL, client.getDescription());
		jsonObjectEmisor.put(NotaDeDebito.TIPOESTABLECIMIENTO, client_getE_PlantType(client).getValue());

		JSONObject jsonDireccion = new JSONObject();
		jsonDireccion.put(NotaDeDebito.DEPARTAMENTO, city_getRegionValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(NotaDeDebito.MUNICIPIO, city_getValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(NotaDeDebito.COMPLEMENTO, orgInfo.getC_Location().getAddress1());
		jsonObjectEmisor.put(NotaDeDebito.DIRECCION, jsonDireccion);
		
		jsonObjectEmisor.put(NotaDeDebito.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectEmisor.put(NotaDeDebito.CORREO, client_getEmail(client));

		System.out.println("Finish collecting JSON data for Emisor");
		return jsonObjectEmisor;
		
		
	}
	
	private JSONObject generateReceptorInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Receptor");

		MBPartner partner = (MBPartner)invoice.getC_BPartner();
		if (bPartner_getE_Activity(partner).getE_Activity_ID()<=0 || bPartner_getE_Recipient_Identification(partner).getE_Recipient_Identification_ID() <= 0) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica"; 
			notaDeDebito.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		
		JSONObject jsonObjectReceptor = new JSONObject();
		if (partner.getTaxID() == null && partner.getDUNS() == null) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica"; 
			notaDeDebito.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		jsonObjectReceptor.put(NotaDeDebito.NIT, partner.getTaxID().replace("-", ""));
		jsonObjectReceptor.put(NotaDeDebito.NRC, partner.getDUNS().trim().replace("-", ""));
		jsonObjectReceptor.put(NotaDeDebito.NOMBRE, partner.getName());
		
		if (bPartner_getE_Activity(partner).getE_Activity_ID()>0) {
			jsonObjectReceptor.put(NotaDeDebito.CODACTIVIDAD, bPartner_getE_Activity(partner).getValue());
			jsonObjectReceptor.put(NotaDeDebito.DESCACTIVIDAD, bPartner_getE_Activity(partner).getName());
		}

		JSONObject jsonDireccion = new JSONObject();
		String departamento = "";
		String municipio = "";
		String complemento = "";
		for (MBPartnerLocation partnerLocation : MBPartnerLocation.getForBPartner(contextProperties, partner.getC_BPartner_ID(), trxName)){
			if (partnerLocation.isBillTo()) {
				departamento = city_getRegionValue((MCity)partnerLocation.getC_Location().getC_City());
				municipio =  city_getValue((MCity)partnerLocation.getC_Location().getC_City());
				complemento = (partnerLocation.getC_Location().getAddress1() + " " + partnerLocation.getC_Location().getAddress2());
				jsonDireccion.put(NotaDeDebito.DEPARTAMENTO, departamento);
				jsonDireccion.put(NotaDeDebito.MUNICIPIO, municipio);
				jsonDireccion.put(NotaDeDebito.COMPLEMENTO, complemento);
				break;
			}
		}		
		
		// In case there is no address
		if (departamento == null) {
			jsonDireccion.put(NotaDeDebito.DEPARTAMENTO, departamento);
			jsonDireccion.put(NotaDeDebito.MUNICIPIO, municipio);
			jsonDireccion.put(NotaDeDebito.COMPLEMENTO, complemento);
		}		
		jsonObjectReceptor.put(NotaDeDebito.DIRECCION, jsonDireccion);
		
		jsonObjectReceptor.put(NotaDeDebito.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectReceptor.put(NotaDeDebito.CORREO, partner.get_ValueAsString("EMail"));		

		System.out.println("NotaDeDebito: end collecting JSON data for Receptor");
		return jsonObjectReceptor;
		
	}
	
	private JSONObject generateResumenInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Resumen");
		BigDecimal totalNoSuj 	  = Env.ZERO;
		BigDecimal totalExenta 	  = Env.ZERO;
		BigDecimal totalGravada   = Env.ZERO;	
		BigDecimal ivaRete1 	  = Env.ZERO;
		String numPagoElectronico = "";			// TODO: get correct data for this variable

		String totalLetras=Msg.getAmtInWords(client.getLanguage(), invoice.getGrandTotal().setScale(2).toString());

		List<MInvoiceTax> invoiceTaxes = new Query(contextProperties , MInvoiceTax.Table_Name , "C_Invoice_ID=?" , trxName)
				.setParameters(invoice.getC_Invoice_ID())
				.list();
		JSONObject jsonObjectResumen = new JSONObject();


		JSONArray jsonTributosArray = new JSONArray();
		for (MInvoiceTax invoiceTax:invoiceTaxes) {
			if (invoiceTax.getC_Tax().getTaxIndicator().equals("RET")) {
				ivaRete1 = ivaRete1.add(invoiceTax.getTaxAmt().multiply(new BigDecimal(-1)));
			}
			JSONObject jsonTributoItem = new JSONObject();		
			if (invoiceTax.getC_Tax().getTaxIndicator().equals("NSUJ")) {
				totalNoSuj = invoiceTax.getTaxBaseAmt();		
				jsonTributoItem.put(NotaDeDebito.CODIGO, tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getValue());
				jsonTributoItem.put(NotaDeDebito.DESCRIPCION,  tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getName());
				jsonTributoItem.put(NotaDeDebito.VALOR, invoiceTax.getTaxAmt());
			}
			else if (invoiceTax.getC_Tax().getTaxIndicator().equals("EXT")) {
				totalExenta = invoiceTax.getTaxBaseAmt();
				jsonTributoItem.put(NotaDeDebito.CODIGO,  tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getValue());
				jsonTributoItem.put(NotaDeDebito.DESCRIPCION,  tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getName());
				jsonTributoItem.put(NotaDeDebito.VALOR, invoiceTax.getTaxAmt());
			}
			else if (invoiceTax.getC_Tax().getTaxIndicator().equals("IVA")) {
				totalGravada = invoiceTax.getTaxBaseAmt();
				jsonTributoItem.put(NotaDeDebito.CODIGO,  tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getValue());
				jsonTributoItem.put(NotaDeDebito.DESCRIPCION,  tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getName());
				jsonTributoItem.put(NotaDeDebito.VALOR, invoiceTax.getTaxAmt());
			}
			jsonTributosArray.put(jsonTributoItem); //tributosItems.add("20");
		}
		jsonObjectResumen.put(NotaDeDebito.TRIBUTOS, jsonTributosArray);


		jsonObjectResumen.put(NotaDeDebito.TOTALNOSUJ, totalNoSuj);
		jsonObjectResumen.put(NotaDeDebito.TOTALEXENTA, totalExenta);
		jsonObjectResumen.put(NotaDeDebito.TOTALGRAVADA, totalGravada);
		jsonObjectResumen.put(NotaDeDebito.SUBTOTALVENTAS, totalGravada.add(totalNoSuj).add(totalExenta));
		jsonObjectResumen.put(NotaDeDebito.DESCUNOSUJ, Env.ZERO);
		jsonObjectResumen.put(NotaDeDebito.DESCUEXENTA, Env.ZERO);
		jsonObjectResumen.put(NotaDeDebito.DESCUGRAVADA, Env.ZERO);
		jsonObjectResumen.put(NotaDeDebito.PORCENTAJEDESCUENTO, Env.ZERO);
		jsonObjectResumen.put(NotaDeDebito.SUBTOTAL, totalGravada.add(totalNoSuj).add(totalExenta));
		jsonObjectResumen.put(NotaDeDebito.IVARETE1, ivaRete1);
		jsonObjectResumen.put(NotaDeDebito.MONTOTOTALOPERACION, invoice.getGrandTotal());
		jsonObjectResumen.put(NotaDeDebito.TOTALNOGRAVADO, totalExenta.add(totalNoSuj));
		jsonObjectResumen.put(NotaDeDebito.TOTALPAGAR, invoice.getGrandTotal());
		jsonObjectResumen.put(NotaDeDebito.TOTALLETRAS, totalLetras);
		jsonObjectResumen.put(NotaDeDebito.SALDOFAVOR, Env.ZERO);
		jsonObjectResumen.put(NotaDeDebito.CONDICIONOPERACION, NotaDeDebito.CONDICIONOPERACION_A_CREDITO);
		jsonObjectResumen.put(NotaDeDebito.TOTALDESCU, Env.ZERO);
		jsonObjectResumen.put(NotaDeDebito.RETERENTA, Env.ZERO);
		jsonObjectResumen.put(NotaDeDebito.IVAPERCI1, Env.ZERO);
		jsonObjectResumen.put(NotaDeDebito.NUMPAGOELECTRONICO, numPagoElectronico);

		JSONArray jsonArrayPagos = new JSONArray();
		JSONObject jsonPago = new JSONObject();
		jsonPago.put(NotaDeDebito.CODIGO, "05");
		jsonPago.put(NotaDeDebito.MONTOPAGO, invoice.getGrandTotal());
		jsonPago.put(NotaDeDebito.REFERENCIA, "Transferencia_ Dep??sito Bancario");
		jsonPago.put(NotaDeDebito.PLAZO, paymentterm_getE_TimeSpan((MPaymentTerm)invoice.getC_PaymentTerm()).getValue());
		jsonPago.put(NotaDeDebito.PERIODO, invoice.getC_PaymentTerm().getNetDays());
		jsonArrayPagos.put(jsonPago);

		jsonObjectResumen.put(NotaDeDebito.PAGOS, jsonArrayPagos);


		System.out.println("NotaDeDebito: end collecting JSON data for Resumen");
		return jsonObjectResumen;

	}
	
	private JSONObject generateCuerpoDocumentoInputData() {
		System.out.println("Start collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		JSONObject jsonCuerpoDocumento = new JSONObject();
		JSONArray jsonCuerpoDocumentoArray = new JSONArray();
		
		for (MInvoiceLine invoiceLine:invoice.getLines()) { 
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() );
			
			BigDecimal ventaNoSuj 	= Env.ZERO;
			BigDecimal ventaExenta 	= Env.ZERO;
			BigDecimal ventaGravada = Env.ONEHUNDRED;
			BigDecimal ivaItem 		= Env.ZERO;
			MTax tax = (MTax)invoiceLine.getC_Tax();
			
			if (invoiceLine.getC_Tax().getTaxIndicator().equals("NSUJ"))
				ventaNoSuj = invoiceLine.getLineNetAmt();
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
                
			jsonCuerpoDocumentoItem.put(NotaDeDebito.NUMITEM, invoiceLine.getLine()/10);
			jsonCuerpoDocumentoItem.put(NotaDeDebito.TIPOITEM, 2);
			jsonCuerpoDocumentoItem.put(NotaDeDebito.NUMERODOCUMENTO, numerodocumentno);
			jsonCuerpoDocumentoItem.put(NotaDeDebito.CANTIDAD, invoiceLine.getQtyInvoiced());
			jsonCuerpoDocumentoItem.put(NotaDeDebito.CODIGO, invoiceLine.getM_Product_ID()>0? invoiceLine.getProduct().getValue(): invoiceLine.getC_Charge().getName());
			jsonCuerpoDocumentoItem.put(NotaDeDebito.CODTRIBUTO, "");  // String codTributo = "20";
			jsonCuerpoDocumentoItem.put(NotaDeDebito.UNIMEDIDA, 1);
			jsonCuerpoDocumentoItem.put(NotaDeDebito.DESCRIPCION, invoiceLine.getM_Product_ID()>0?invoiceLine.getM_Product().getName():invoiceLine.getC_Charge().getName());
			jsonCuerpoDocumentoItem.put(NotaDeDebito.PRECIOUNI, invoiceLine.getPriceActual());
			jsonCuerpoDocumentoItem.put(NotaDeDebito.MONTODESCU, Env.ZERO);
			jsonCuerpoDocumentoItem.put(NotaDeDebito.VENTANOSUJ, ventaNoSuj);
			jsonCuerpoDocumentoItem.put(NotaDeDebito.VENTAEXENTA, ventaExenta);
			jsonCuerpoDocumentoItem.put(NotaDeDebito.VENTAGRAVADA, ventaGravada);	

			JSONArray jsonTributosArray = new JSONArray();
			if (ventaGravada.compareTo(Env.ZERO) != 0) {
				jsonTributosArray.put(tax_getE_Duties(tax).getValue());
			}
			jsonCuerpoDocumentoItem.put( NotaDeDebito.TRIBUTOS, jsonTributosArray);

			jsonCuerpoDocumentoArray.put(jsonCuerpoDocumentoItem);
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() + " Finished");

		}  
		
		jsonCuerpoDocumento.put(NotaDeDebito.CUERPODOCUMENTO, jsonCuerpoDocumentoArray);
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
			jsonDocumentoRelacionadoItem.put(NotaDeDebito.TIPODOCUMENTO, docType_getE_DocType((MDocType)invoiceOrginal.getC_DocType()).getValue());
			int tipoGeneracion = invoice_ei_codigoGeneracion(invoiceOrginal) == ""? 1:2;
			jsonDocumentoRelacionadoItem.put(NotaDeDebito.TIPOGENERACION, tipoGeneracion);
			String documentno = tipoGeneracion==2?   invoice_ei_codigoGeneracion(invoiceOrginal)   :invoiceOrginal.getDocumentNo();
			jsonDocumentoRelacionadoItem.put(NotaDeDebito.NUMERODOCUMENTO, documentno);
			jsonDocumentoRelacionadoItem.put(NotaDeDebito.FECEMI, invoiceOrginal.getDateAcct().toString().substring(0, 10));
			jsonDocumentoRelacionadoArray.put(jsonDocumentoRelacionadoItem);
		}
		
		jsonDocumentoRelacionado.put(NotaDeDebito.DOCUMENTORELACIONADO, jsonDocumentoRelacionadoArray);
		System.out.println("Finish collecting JSON data for Documento Relacionado. Document: " + invoice.getDocumentNo());
		
		return jsonDocumentoRelacionado;
	}

	public String createJsonString() throws Exception {
		System.out.println("Nota de Debito: start generating JSON object from Document");
		ObjectMapper objectMapper      = new ObjectMapper();
		String notaDeCreditoAsString   = objectMapper.writeValueAsString(notaDeDebito);
		JSONObject notaDeCreditoAsJson = new JSONObject(notaDeCreditoAsString);

		notaDeCreditoAsJson.remove(NotaDeDebito.ERRORMESSAGES);

		// Manipulate generated JSON string
		String notaDeCreditoAsStringFinal = notaDeCreditoAsJson.toString().
				replace(":[],", ":null,").
        		replace("\"periodo\":0,\"plazo\":\"01\"", "\"periodo\":null,\"plazo\":null").
				replace("\"documentoRelacionado\":[]", "\"documentoRelacionado\":null").
				replace("\"ventaTercero\":{\"nit\":null,\"nombre\":null},", "\"ventaTercero\":null,").
				replace("\"tributos\":[{\"descripcion\":null,\"codigo\":null,\"valor\":null}]", "\"tributos\":null").
				replace("\"extension\":{\"docuEntrega\":null,\"placaVehiculo\":null,\"observaciones\":null,\"nombRecibe\":null,\"nombEntrega\":null,\"docuRecibe\":null},", 
						"\"extension\":null,");

		System.out.println("Nota de Debito: generated JSON object from Document:");
		System.out.println(notaDeCreditoAsStringFinal);
		System.out.println("Nota de Debito: end generating JSON object from Document");
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
		 return notaDeDebito.errorMessages;
	 }
}

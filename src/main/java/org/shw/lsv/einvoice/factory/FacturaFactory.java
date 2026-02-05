package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.core.domains.models.X_C_UOM;
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
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.fefcfacturaelectronicav1.ApendiceItemFactura;
import org.shw.lsv.einvoice.fefcfacturaelectronicav1.CuerpoDocumentoItemFactura;
import org.shw.lsv.einvoice.fefcfacturaelectronicav1.EmisorFactura;
import org.shw.lsv.einvoice.fefcfacturaelectronicav1.ExtensionFactura;
import org.shw.lsv.einvoice.fefcfacturaelectronicav1.Factura;
import org.shw.lsv.einvoice.fefcfacturaelectronicav1.IdentificacionFactura;
import org.shw.lsv.einvoice.fefcfacturaelectronicav1.ReceptorFactura;
import org.shw.lsv.einvoice.fefcfacturaelectronicav1.ResumenFactura;
import org.shw.lsv.einvoice.utils.EDocument;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FacturaFactory extends EDocumentFactory {
	Factura factura;
	MInvoice invoice;
	
	public FacturaFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo, MInvoice invoice) {
		super(trxName, contextProperties, client, orgInfo);
		this.invoice = invoice;
		factura = new Factura();
		}

	public Factura generateEDocument() {
		System.out.println("Factura: start generating and filling the Document");
		String result="";
		factura = new Factura();

		System.out.println("Instantiate, fill and verify Identificacion");
		IdentificacionFactura identification = factura.getIdentificacion();
		if(identification!=null) {
			factura.errorMessages.append(factura.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				factura.errorMessages.append(result);
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
		EmisorFactura emisor = factura.getEmisor();
		if(emisor!=null) {
			factura.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				factura.errorMessages.append(result);
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
		List<CuerpoDocumentoItemFactura> cuerpoDocumento = factura.getCuerpoDocumento();
		if(cuerpoDocumento!=null) {
			factura.fillCuerpoDocumento(jsonInputToFactory);
			
			cuerpoDocumento.stream().forEach( cuerpoDocumentoItem -> { 
				String resultLambda = cuerpoDocumentoItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						factura.errorMessages.append(resultLambda);
					}
				} 
			);
		}

		System.out.println("Instantiate, fill and verify Resumen");
		ResumenFactura resumen = factura.getResumen();
		if(resumen!=null) {
			factura.fillResumen(jsonInputToFactory);
			result = resumen.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				factura.errorMessages.append(result);
			}
		}
		

		System.out.println("Instantiate, fill and verify Receptor");
		ReceptorFactura receptor = factura.getReceptor();
		if(receptor!=null) {
			factura.fillReceptor(jsonInputToFactory);
			result = receptor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				factura.errorMessages.append(result);
			}
		}
		
		System.out.println("Instantiate, fill and verify ExtensionFactura");
		ExtensionFactura extension = factura.getExtension();
		if(extension!=null) {
			factura.fillExtension(jsonInputToFactory);
			result = extension.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				factura.errorMessages.append(result);
			}
		}
		
		List<ApendiceItemFactura> apendice = factura.getApendice();
		if(apendice!=null) {
			factura.fillApendice(jsonInputToFactory);
			
			apendice.stream().forEach( apendiceItem -> { 
				String resultLambda = apendiceItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						factura.errorMessages.append(resultLambda);
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

		factura.validateValues();
		if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
			factura.errorMessages.append(result);
		}

		System.out.println("Factura: end generating and filling the Document");
		return factura;
	}

	@Override
	public void generateJSONInputData() {
		System.out.println("Factura: start collecting JSON data for all components");
		jsonInputToFactory = new JSONObject();

		jsonInputToFactory.put(Factura.IDENTIFICACION, generateIdentificationInputData());
		jsonInputToFactory.put(Factura.RECEPTOR, generateReceptorInputData());
		jsonInputToFactory.put(Factura.EMISOR, generateEmisorInputData());
		jsonInputToFactory.put(Factura.RESUMEN, generateResumenInputData());
		jsonInputToFactory.put(Factura.CUERPODOCUMENTO, generateCuerpoDocumentoInputData());
		jsonInputToFactory.put(Factura.APENDICE, generateApendiceInputData(invoice.getC_Invoice_ID()));
		jsonInputToFactory.put(Factura.EXTENSION, generateExtensionInputData());
		
		System.out.println("Generated JSON object from Invoice:");
		System.out.println(jsonInputToFactory.toString());
		System.out.println("Factura: end collecting JSON data for all components");
	}
	
	private JSONObject generateIdentificationInputData() {
		System.out.println("Factura: start collecting JSON data for Identificacion");

		String numeroControl = createNumeroControl(invoice, client);
		String codigoGeneracion =  createCodigoGeneracion(invoice);
		JSONObject jsonObjectIdentificacion = new JSONObject();
		Boolean isContigencia = false;
		if (TimeUtil.getDaysBetween(invoice.getDateAcct(), TimeUtil.getDay(0))>3) {
			isContigencia = true;
		}
		String fecEmi = invoice.getDateAcct().toString().substring(0, 10);
		String horEmi = gethorEmi();
		int tipoModelo = isContigencia?Factura.TIPOMODELO_CONTIGENCIA:Factura.TIPOMODELO_NOCONTIGENCIA;
		int tipoOperacion = isContigencia?Factura.TIPOOPERACION_CONTIGENCIA:Factura.TIPOOPERACION_NOCONTIGENCIA;
		jsonObjectIdentificacion.put(Factura.NUMEROCONTROL, numeroControl);
		jsonObjectIdentificacion.put(Factura.CODIGOGENERACION, codigoGeneracion);
		jsonObjectIdentificacion.put(Factura.TIPOMODELO, tipoModelo);
		jsonObjectIdentificacion.put(Factura.TIPOOPERACION, tipoOperacion);
		jsonObjectIdentificacion.put(Factura.FECEMI, fecEmi);
		jsonObjectIdentificacion.put(Factura.HOREMI, horEmi);
		jsonObjectIdentificacion.put(Factura.TIPOMONEDA, "USD");
		jsonObjectIdentificacion.put(Factura.AMBIENTE, client_getE_Enviroment(client).getValue());
		
		/*if (isContigencia) {
			jsonObjectIdentificacion.put(Factura.MOTIVOCONTIN, "Contigencia por fecha de factura");
			jsonObjectIdentificacion.put(Factura.TIPOCONTINGENCIA, 5);
		}*/
		System.out.println("Factura: end collecting JSON data for Identificacion");
		return jsonObjectIdentificacion;
		
	}
	
	private JSONObject generateEmisorInputData() {
		System.out.println("Factura: start collecting JSON data for Emisor");
		
		JSONObject jsonObjectEmisor = new JSONObject();
		jsonObjectEmisor.put(Factura.NIT, orgInfo.getTaxID().replace("-", ""));
		jsonObjectEmisor.put(Factura.NRC, StringUtils.leftPad(orgInfo.getDUNS().trim().replace("-", ""), 7));
		jsonObjectEmisor.put(Factura.NOMBRE, client.getDescription());
		jsonObjectEmisor.put(Factura.CODACTIVIDAD, client_getE_Activity(client).getValue());
		jsonObjectEmisor.put(Factura.DESCACTIVIDAD, client_getE_Activity(client).getName());
		jsonObjectEmisor.put(Factura.NOMBRECOMERCIAL, client.getName());
		jsonObjectEmisor.put(Factura.TIPOESTABLECIMIENTO, client_getE_PlantType(client).getValue());

		JSONObject jsonDireccion = new JSONObject();
		jsonDireccion.put(Factura.DEPARTAMENTO,  city_getRegionValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(Factura.MUNICIPIO,     city_getValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(Factura.COMPLEMENTO, orgInfo.getC_Location().getAddress1());
		jsonObjectEmisor.put(Factura.DIRECCION, jsonDireccion);
		
		jsonObjectEmisor.put(Factura.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectEmisor.put(Factura.CORREO,  client_getEmail(client));

		System.out.println("Factura: end collecting JSON data for Emisor");
		return jsonObjectEmisor;
		
	}
	
	private JSONObject generateReceptorInputData() {
		System.out.println("Factura: start collecting JSON data for Receptor");

		MBPartner partner = (MBPartner)invoice.getC_BPartner();
//		if (partner.getE_Activity_ID()<=0 || partner.getE_Recipient_Identification_ID() <= 0) {
//			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica"; 
//			factura.errorMessages.append(errorMessage);
//			System.out.println(errorMessage);
//		}
		
		JSONObject jsonObjectReceptor = new JSONObject();
		
		jsonObjectReceptor.put(Factura.TIPODOCUMENTO,  bPartner_getE_Recipient_Identification(partner).getValue());
		if (partner.getTaxID() != null) {
			jsonObjectReceptor.put(Factura.NUMDOCUMENTO, partner.getTaxID().replace("-", ""));
			jsonObjectReceptor.put(Factura.NOMBRE, partner.getName());			
		}
		else {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta NIT"; 
			factura.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		
		if (bPartner_getE_Activity(partner).getE_Activity_ID()>0) {
			jsonObjectReceptor.put(Factura.CODACTIVIDAD, bPartner_getE_Activity(partner).getValue());
			jsonObjectReceptor.put(Factura.DESCACTIVIDAD, bPartner_getE_Activity(partner).getName());
		} else  {
			jsonObjectReceptor.put(Factura.CODACTIVIDAD, "");
			jsonObjectReceptor.put(Factura.DESCACTIVIDAD, "");
		}
		JSONObject jsonDireccion = new JSONObject();
		String departamento = "";
		String municipio = "";
		String complemento = "";
		for (MBPartnerLocation partnerLocation : MBPartnerLocation.getForBPartner(contextProperties, partner.getC_BPartner_ID(), trxName)){
			if (partnerLocation.isBillTo() && partnerLocation.getC_Location().getC_Country_ID() == 173) {
				departamento =  city_getRegionValue((MCity)partnerLocation.getC_Location().getC_City());
				municipio =  city_getValue((MCity)partnerLocation.getC_Location().getC_City());
				complemento = (partnerLocation.getC_Location().getAddress1() + " " 
				+ partnerLocation.getC_Location().getAddress2());
				jsonDireccion.put(Factura.DEPARTAMENTO, departamento);
				jsonDireccion.put(Factura.MUNICIPIO, municipio);
				jsonDireccion.put(Factura.COMPLEMENTO, complemento.replace("null", ""));
				jsonObjectReceptor.put(Factura.DIRECCION, jsonDireccion);
				break;
			}
		}		
		
		// In case there is no address
		if (departamento == null) {
			jsonDireccion.put(Factura.DEPARTAMENTO, "");
			jsonDireccion.put(Factura.MUNICIPIO, "");
			jsonDireccion.put(Factura.COMPLEMENTO, complemento);
		}		

		jsonObjectReceptor.put(Factura.DIRECCION, jsonDireccion);
		String phone = partner.get_ValueAsString("phone").replace("-", "").trim();
		if (phone.length()==8)
		jsonObjectReceptor.put(Factura.TELEFONO, partner.get_ValueAsString("phone"));
		else
			jsonObjectReceptor.put(Factura.TELEFONO,"");
		jsonObjectReceptor.put(Factura.CORREO, partner.get_ValueAsString("EMail"));		

		System.out.println("Factura: end collecting JSON data for Receptor");
		return jsonObjectReceptor;
		
	}
	
	private JSONObject generateResumenInputData() {
		System.out.println("Factura: start collecting JSON data for Resumen");
		BigDecimal totalNoSuj 		= Env.ZERO;
		BigDecimal totalExenta 		= Env.ZERO;
		BigDecimal totalGravada 	= Env.ZERO;		
		BigDecimal totalNoGravada 	= Env.ZERO;		
		BigDecimal totalIVA 		= Env.ZERO;
		BigDecimal ivaRete1 		= Env.ZERO;
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
			if (invoiceTax.getC_Tax().getTaxIndicator().equals("NSUJ")) {
				if (invoiceTax.getC_Tax().getC_TaxCategory().getCommodityCode() != null &&
						invoiceTax.getC_Tax().getC_TaxCategory().getCommodityCode().equals("CTAJ"))
					totalNoGravada = invoiceTax.getTaxBaseAmt();	
				else {
					totalNoSuj = invoiceTax.getTaxBaseAmt();		
					//jsonTributoItem.put(Factura.CODIGO, invoiceTax.getC_Tax().getE_Duties().getValue());
					//jsonTributoItem.put(Factura.DESCRIPCION, invoiceTax.getC_Tax().getE_Duties().getName());
					//jsonTributoItem.put(Factura.VALOR, invoiceTax.getTaxAmt());
					//jsonTributosArray.put(jsonTributoItem); //tributosItems.add("20");
				}
			}
			else if (invoiceTax.getC_Tax().getTaxIndicator().equals("EXT")) {
				totalExenta = invoiceTax.getTaxBaseAmt();
				//jsonTributoItem.put(Factura.CODIGO, invoiceTax.getC_Tax().getE_Duties().getValue());
				//jsonTributoItem.put(Factura.DESCRIPCION, invoiceTax.getC_Tax().getE_Duties().getName());
				//jsonTributoItem.put(Factura.VALOR, invoiceTax.getTaxAmt());
				//jsonTributosArray.put(jsonTributoItem); //tributosItems.add("20");
			}
			else if (invoiceTax.getC_Tax().getTaxIndicator().equals("IVA")) {
				totalGravada = invoiceTax.getTaxBaseAmt().add(invoiceTax.getTaxAmt());
				totalIVA = invoiceTax.getTaxAmt();	
				//jsonTributoItem.put(Factura.CODIGO, invoiceTax.getC_Tax().getE_Duties().getValue());
				//jsonTributoItem.put(Factura.DESCRIPCION, invoiceTax.getC_Tax().getE_Duties().getName());
				//jsonTributoItem.put(Factura.VALOR, invoiceTax.getTaxAmt());
			}
		}
		// (!jsonTributosArray.isEmpty())
		jsonObjectResumen.put(Factura.TRIBUTOS, jsonTributosArray);
		
		
		jsonObjectResumen.put(Factura.TOTALNOSUJ, totalNoSuj);
		jsonObjectResumen.put(Factura.TOTALEXENTA, totalExenta);
		jsonObjectResumen.put(Factura.TOTALGRAVADA, totalGravada);
		jsonObjectResumen.put(Factura.SUBTOTALVENTAS, totalGravada.add(totalNoSuj).add(totalExenta));
		jsonObjectResumen.put(Factura.DESCUNOSUJ, Env.ZERO);
		jsonObjectResumen.put(Factura.DESCUEXENTA, Env.ZERO);
		jsonObjectResumen.put(Factura.DESCUGRAVADA, Env.ZERO);
		jsonObjectResumen.put(Factura.PORCENTAJEDESCUENTO, Env.ZERO);
		jsonObjectResumen.put(Factura.SUBTOTAL, totalGravada.add(totalNoSuj).add(totalExenta));
		jsonObjectResumen.put(Factura.IVARETE1, ivaRete1);
		jsonObjectResumen.put(Factura.MONTOTOTALOPERACION, totalGravada.add(totalNoSuj).add(totalExenta));
		jsonObjectResumen.put(Factura.TOTALNOGRAVADO, totalNoGravada);
		jsonObjectResumen.put(Factura.TOTALPAGAR, invoice.getGrandTotal());
		jsonObjectResumen.put(Factura.TOTALLETRAS, totalLetras);
		jsonObjectResumen.put(Factura.SALDOFAVOR, Env.ZERO);
		int condicionOperacion = 
		invoice.getC_PaymentTerm().getNetDays() == 0? Factura.CONDICIONOPERACION_AL_CONTADO:
			Factura.CONDICIONOPERACION_A_CREDITO;
		jsonObjectResumen.put(Factura.CONDICIONOPERACION, condicionOperacion);
		jsonObjectResumen.put(Factura.TOTALDESCU, Env.ZERO);
		jsonObjectResumen.put(Factura.RETERENTA, Env.ZERO);
		jsonObjectResumen.put(Factura.TOTALIVA, totalIVA);

		JSONArray jsonArrayPagos = new JSONArray();
			JSONObject jsonPago = new JSONObject();
			jsonPago.put(Factura.CODIGO, "05");
			jsonPago.put(Factura.MONTOPAGO, invoice.getGrandTotal());
			jsonPago.put(Factura.REFERENCIA, "Transferencia_ Deposito Bancario");
			jsonPago.put(Factura.PLAZO,  paymentterm_getE_TimeSpan((MPaymentTerm)invoice.getC_PaymentTerm()).getValue());
			jsonPago.put(Factura.PERIODO, invoice.getC_PaymentTerm().getNetDays());
		jsonArrayPagos.put(jsonPago);

		jsonObjectResumen.put(Factura.PAGOS, jsonArrayPagos);
		

		System.out.println("Factura: end collecting JSON data for Resumen");
		return jsonObjectResumen;
		
	}
	
	private JSONObject generateCuerpoDocumentoInputData() {
		System.out.println("Factura: start collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		JSONObject jsonCuerpoDocumento = new JSONObject();
		JSONArray jsonCuerpoDocumentoArray = new JSONArray();
		int i=0;
		
		for (MInvoiceLine invoiceLine:invoice.getLines()) { 
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() );
			i++;
			BigDecimal ventaNoSuj 		= Env.ZERO;
			BigDecimal ventaExenta 		= Env.ZERO;
			BigDecimal ventaGravada 	= Env.ZERO;
			BigDecimal ventaNoGravada 	= Env.ZERO;
			BigDecimal ivaItem 			= Env.ZERO;
			String codigo = invoiceLine.getM_Product_ID()>0? invoiceLine.getProduct().getValue()
					: invoiceLine.getC_Charge().getName().substring(0,3);
			boolean isventaNoGravada = (invoiceLine.getC_Tax().getTaxIndicator().equals("NSUJ") && 
					invoiceLine.getC_Charge_ID() > 0 
					&& invoiceLine.getC_Charge().getC_ChargeType().getValue().equals("CTAJ"))?true:false;
			if (invoiceLine.getC_Tax().getTaxIndicator().equals("NSUJ")) {
				if (isventaNoGravada)
					ventaNoGravada = invoiceLine.getLineNetAmt();
				else
					ventaNoSuj = invoiceLine.getLineNetAmt();
				
			}
			else if (invoiceLine.getC_Tax().getTaxIndicator().equals("EXT"))
				ventaExenta = invoiceLine.getLineNetAmt();
			else if (invoiceLine.getC_Tax().getTaxIndicator().equals("IVA") ) {
				ventaGravada = invoiceLine.getLineNetAmt(); 
				MTax tax = (MTax)invoiceLine.getC_Tax();
				if (invoiceLine.getTaxAmt().compareTo(Env.ZERO) == 0)
					ivaItem = tax.calculateTax(invoiceLine.getLineNetAmt(), invoice.getM_PriceList().isTaxIncluded(), 2);
				else ivaItem = invoiceLine.getTaxAmt();
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
                
			jsonCuerpoDocumentoItem.put(Factura.NUMITEM, i);
			jsonCuerpoDocumentoItem.put(Factura.TIPOITEM, invoiceLineProductType(invoiceLine.getM_Product_ID()));
			//jsonCuerpoDocumentoItem.put(Factura.NUMERODOCUMENTO, getNumeroControl(invoice.get_ID(), orgInfo, "DTE-01-"));
			jsonCuerpoDocumentoItem.put(Factura.CANTIDAD, invoiceLine.getQtyEntered());
			jsonCuerpoDocumentoItem.put(Factura.CODIGO, codigo);
			jsonCuerpoDocumentoItem.put(Factura.CODIGOTRIBUTO,  tax_getE_Duties((MTax)invoiceLine.getC_Tax()).getValue());  // String codTributo = "20";
			
			JSONArray jsonTributosArray = new JSONArray();
			jsonCuerpoDocumentoItem. put( Factura.TRIBUTOS, jsonTributosArray); //tributosItems.add("20");
			X_C_UOM uom = (X_C_UOM)invoiceLine.getC_UOM();
			jsonCuerpoDocumentoItem.put(Factura.UNIMEDIDA, uom_getValue(uom));
			//jsonCuerpoDocumentoItem.put(Factura.UNIMEDIDA, 59);
			jsonCuerpoDocumentoItem.put(Factura.DESCRIPCION, description);
			BigDecimal precioUnitario = ventaNoGravada.compareTo(Env.ZERO)!=0? Env.ZERO: invoiceLine.getPriceEntered();
			jsonCuerpoDocumentoItem.put(Factura.PRECIOUNI, precioUnitario);
			jsonCuerpoDocumentoItem.put(Factura.MONTODESCU, Env.ZERO);
			jsonCuerpoDocumentoItem.put(Factura.VENTANOSUJ, ventaNoSuj);
			jsonCuerpoDocumentoItem.put(Factura.VENTAEXENTA, ventaExenta);
			jsonCuerpoDocumentoItem.put(Factura.VENTAGRAVADA, ventaGravada);
			jsonCuerpoDocumentoItem.put(Factura.PSV, Env.ZERO);
			jsonCuerpoDocumentoItem.put(Factura.NOGRAVADO, ventaNoGravada);
			jsonCuerpoDocumentoItem.put(Factura.IVAITEM, ivaItem);

			jsonCuerpoDocumentoArray.put(jsonCuerpoDocumentoItem);
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() + " Finished");

		}  
		jsonCuerpoDocumento.put(Factura.CUERPODOCUMENTO, jsonCuerpoDocumentoArray);
		System.out.println("Factura: end collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		
		return jsonCuerpoDocumento;
	}

	private JSONObject generateExtensionInputData() {
		System.out.println("Credito Fiscal: start collecting JSON data for Extension. Document: " + invoice.getDocumentNo());
		JSONObject jsonExtension = new JSONObject();
		String observaciones = invoice.get_ValueAsString(MPackageExp.COLUMNNAME_Instructions);
		
		jsonExtension.put(EDocument.NOMBENTREGA, "");
		jsonExtension.put(EDocument.DOCUENTREGA, "");
		jsonExtension.put(EDocument.NOMBRECIBE, "");
		jsonExtension.put(EDocument.DOCURECIBE, "");
		jsonExtension.put(EDocument.OBSERVACIONES, observaciones);
		jsonExtension.put(EDocument.PLACAVEHICULO, "");
		System.out.println("Credito Fiscal: end collecting JSON data for Extension. Document: " + invoice.getDocumentNo());
		return jsonExtension;
	}

	public String createJsonString() throws Exception {
		System.out.println("Factura: start generating JSON object from Document");
    	ObjectMapper objectMapper = new ObjectMapper();
    	String facturaAsStringTmp = objectMapper.writeValueAsString(factura);
        JSONObject facturaAsJson  = new JSONObject(facturaAsStringTmp);
        
        facturaAsJson.remove(Factura.ERRORMESSAGES);

     // Manipulate generated JSON string
        String facturaAsStringFinal = facturaAsJson.toString().
        		replace(":[],", ":null,").
        		replace("\"periodo\":0,\"plazo\":\"01\"", "\"periodo\":null,\"plazo\":null").
        		replace("\"descActividad\":\"\"", "\"descActividad\":null").
        		replace("\"codActividad\":\"\"", "\"codActividad\":null").
        		replace("\"telefono\":\"\"", "\"telefono\":null").
        		replace("\"documentoRelacionado\":[]", "\"documentoRelacionado\":null").
        		replace("\"direccion\":{\"complemento\":null,\"municipio\":null,\"departamento\":null},", "\"direccion\":null,").
        		replace("\"ventaTercero\":{\"nit\":null,\"nombre\":null},", "\"ventaTercero\":null,").
        		replace("\"tributos\":[{\"descripcion\":null,\"codigo\":null,\"valor\":null}]", "\"tributos\":null")
				;
				//.
				//ToDo: Element "extension" ersetzen nur, wenn alle Felder null sind, oder auch dann veroeffentlichen?
				// replace("\"extension\":{\"docuEntrega\":null,\"placaVehiculo\":null,\"observaciones\":null,\"nombRecibe\":null,\"nombEntrega\":null,\"docuRecibe\":null},",
        		// 		"\"extension\":null,");

		System.out.println("Factura: generated JSON object from Document:");
		System.out.println(facturaAsStringFinal);
		System.out.println("Factura: end generating JSON object from Document");
		return facturaAsStringFinal;
	}

	public String getNumeroControl(Integer id, MOrgInfo orgInfo, String prefix) {
		String idIdentification  = StringUtils.leftPad(id.toString(), 15,"0");
		String duns = orgInfo.getDUNS().replace("-", "");
		String numeroControl = prefix + StringUtils.leftPad(duns.trim(), 8,"0") + "-"+ idIdentification;
		return numeroControl;
	}
	

	@Override
	public StringBuffer getEDocumentErrorMessages() {
		 return factura.errorMessages;
	 }
}

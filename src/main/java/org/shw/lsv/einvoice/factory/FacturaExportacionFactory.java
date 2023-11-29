package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.fefexfacturaexportacionv1.ApendiceItemFacturaExportacion;
import org.shw.lsv.einvoice.fefexfacturaexportacionv1.CuerpoDocumentoItemFacturaExportacion;
import org.shw.lsv.einvoice.fefexfacturaexportacionv1.EmisorFacturaExportacion;
import org.shw.lsv.einvoice.fefexfacturaexportacionv1.FacturaExportacion;
import org.shw.lsv.einvoice.fefexfacturaexportacionv1.IdentificacionFacturaExportacion;
import org.shw.lsv.einvoice.fefexfacturaexportacionv1.ReceptorFacturaExportacion;
import org.shw.lsv.einvoice.fefexfacturaexportacionv1.ResumenFacturaExportacion;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FacturaExportacionFactory extends EDocumentFactory {
	FacturaExportacion facturaExportacion;
	MInvoice invoice;
	
	public FacturaExportacionFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo, MInvoice invoice) {
		super(trxName, contextProperties, client, orgInfo);
		this.invoice = invoice;
		facturaExportacion = new FacturaExportacion();
	}

	public FacturaExportacion generateEDocument() {
		System.out.println("Factura Exportacion: start generating and filling the Document");
		String result="";

		System.out.println("Instantiate, fill and verify Identificacion");
		IdentificacionFacturaExportacion identification = facturaExportacion.getIdentificacion();
		if(identification!=null) {
			facturaExportacion.errorMessages.append(facturaExportacion.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				facturaExportacion.errorMessages.append(result);
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
		EmisorFacturaExportacion emisor = facturaExportacion.getEmisor();
		if(emisor!=null) {
			facturaExportacion.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				facturaExportacion.errorMessages.append(result);
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
		List<CuerpoDocumentoItemFacturaExportacion> cuerpoDocumento = facturaExportacion.getCuerpoDocumento();
		if(cuerpoDocumento!=null) {
			facturaExportacion.fillCuerpoDocumento(jsonInputToFactory);
			
			cuerpoDocumento.stream().forEach( cuerpoDocumentoItem -> { 
				String resultLambda = cuerpoDocumentoItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						facturaExportacion.errorMessages.append(resultLambda);
					}
				} 
			);
		}

		System.out.println("Instantiate, fill and verify Resumen");
		ResumenFacturaExportacion resumen = facturaExportacion.getResumen();
		if(resumen!=null) {
			facturaExportacion.fillResumen(jsonInputToFactory);
			result = resumen.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				facturaExportacion.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Receptor");
		ReceptorFacturaExportacion receptor = facturaExportacion.getReceptor();
		if(receptor!=null) {
			facturaExportacion.fillReceptor(jsonInputToFactory);
			result = receptor.validateValues();
			if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				facturaExportacion.errorMessages.append(result);
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
		
		List<ApendiceItemFacturaExportacion> apendice = facturaExportacion.getApendice();
		if(apendice!=null) {
			facturaExportacion.fillApendice(jsonInputToFactory);
			
			apendice.stream().forEach( apendiceItem -> { 
				String resultLambda = apendiceItem.validateValues();
					if(! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						facturaExportacion.errorMessages.append(resultLambda);
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

		facturaExportacion.validateValues();
		if(! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
			facturaExportacion.errorMessages.append(result);
		}

		System.out.println("Factura Exportacion: end generating and filling the Document");	
		return facturaExportacion;
	}

	@Override
	public void generateJSONInputData() {
		System.out.println("Factura de Exportacion: start collecting JSON data for all components");
		jsonInputToFactory = new JSONObject();

		jsonInputToFactory.put(FacturaExportacion.IDENTIFICACION, generateIdentificationInputData());
		jsonInputToFactory.put(FacturaExportacion.RECEPTOR, generateReceptorInputData());
		jsonInputToFactory.put(FacturaExportacion.EMISOR, generateEmisorInputData());
		jsonInputToFactory.put(FacturaExportacion.RESUMEN, generateResumenInputData());
		jsonInputToFactory.put(FacturaExportacion.CUERPODOCUMENTO, generateCuerpoDocumentoInputData());
		jsonInputToFactory.put(FacturaExportacion.APENDICE, generateApendiceInputData(invoice.getC_Invoice_ID()));
		
		System.out.println("Generated JSON object from Invoice:");
		System.out.println(jsonInputToFactory.toString());
		System.out.println("Factura de Exportacion: end collecting JSON data for all components");
	}
	
	private JSONObject generateIdentificationInputData() {
		System.out.println("Start collecting JSON data for Identificacion");


		String prefix = invoice.getC_DocType().getDefiniteSequence().getPrefix();
		String documentno = invoice.getDocumentNo().replace(prefix,"");
		String suffix = invoice.getC_DocType().getDefiniteSequence().getSuffix();		
		if (suffix != null && suffix.length()>0) {
			String firstsuffix = suffix.substring(0,1);
			int position = documentno.indexOf(firstsuffix);
			if (position >0)
				documentno = documentno.substring(0,position);
		}
		String idIdentification  = StringUtils.leftPad(documentno , 15,"0");
		String duns = orgInfo.getDUNS().replace("-", "");
		
		String numeroControl = "DTE-" + invoice.getC_DocType().getE_DocType().getValue()
				+ "-"+ StringUtils.leftPad(duns.trim(), 8,"0") + "-"+ idIdentification;
		Integer invoiceID = invoice.get_ID();
		
		Integer clientID = (Integer)client.getAD_Client_ID();
		String codigoGeneracion = StringUtils.leftPad(clientID.toString(), 8, "0") + "-0000-0000-0000-" + StringUtils.leftPad(invoiceID.toString(), 12,"0");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String horEmi = timeFormat.format(cal.getTime());
		JSONObject jsonObjectIdentificacion = new JSONObject();
		jsonObjectIdentificacion.put(FacturaExportacion.NUMEROCONTROL, numeroControl);
		
		jsonObjectIdentificacion.put(FacturaExportacion.CODIGOGENERACION, codigoGeneracion);
		jsonObjectIdentificacion.put(FacturaExportacion.TIPOMODELO, 1);
		jsonObjectIdentificacion.put(FacturaExportacion.TIPOOPERACION, 1);
		jsonObjectIdentificacion.put(FacturaExportacion.FECEMI, invoice.getDateAcct().toString().substring(0, 10));
		jsonObjectIdentificacion.put(FacturaExportacion.HOREMI,horEmi);
		jsonObjectIdentificacion.put(FacturaExportacion.TIPOMONEDA, "USD");
		jsonObjectIdentificacion.put(FacturaExportacion.AMBIENTE, client.getE_Enviroment().getValue());

		System.out.println("Finish collecting JSON data for Identificacion");
		return jsonObjectIdentificacion;
		
	}
	
	private JSONObject generateEmisorInputData() {
		System.out.println("Start collecting JSON data for Emisor");
		
		JSONObject jsonObjectEmisor = new JSONObject();
		jsonObjectEmisor.put(FacturaExportacion.NIT, orgInfo.getTaxID().replace("-", ""));
		jsonObjectEmisor.put(FacturaExportacion.NRC, StringUtils.leftPad(orgInfo.getDUNS().trim().replace("-", ""), 7));
		jsonObjectEmisor.put(FacturaExportacion.NOMBRE, client.getDescription());
		jsonObjectEmisor.put(FacturaExportacion.CODACTIVIDAD, client.getE_Activity().getValue());
		jsonObjectEmisor.put(FacturaExportacion.DESCACTIVIDAD, client.getE_Activity().getName());
		jsonObjectEmisor.put(FacturaExportacion.NOMBRECOMERCIAL, client.getName());
		jsonObjectEmisor.put(FacturaExportacion.TIPOESTABLECIMIENTO, client.getE_PlantType().getValue());

		JSONObject jsonDireccion = new JSONObject();
		jsonDireccion.put(FacturaExportacion.DEPARTAMENTO, orgInfo.getC_Location().getC_City().getC_Region().getValue());
		jsonDireccion.put(FacturaExportacion.MUNICIPIO, orgInfo.getC_Location().getC_City().getValue());
		jsonDireccion.put(FacturaExportacion.COMPLEMENTO, orgInfo.getC_Location().getAddress1());
		jsonObjectEmisor.put(FacturaExportacion.DIRECCION, jsonDireccion);
		
		jsonObjectEmisor.put(FacturaExportacion.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectEmisor.put(FacturaExportacion.CORREO, client.getEMail());
		jsonObjectEmisor.put(FacturaExportacion.TIPOITEMEXPOR, 2);

		System.out.println("Finish collecting JSON data for Emisor");
		return jsonObjectEmisor;
		
	}
	
	private JSONObject generateReceptorInputData() {
		System.out.println("Start collecting JSON data for Receptor");

		MBPartner partner = (MBPartner)invoice.getC_BPartner();
		if (partner.getE_Activity_ID()<=0 || partner.getE_Recipient_Identification_ID() <= 0) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica"; 
			facturaExportacion.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}


		JSONObject jsonObjectReceptor = new JSONObject();

		jsonObjectReceptor.put(FacturaExportacion.TIPODOCUMENTO, partner.getE_Recipient_Identification().getValue());
		if (partner.getE_Recipient_Identification().getValue().equals("36"))
		{
			if (partner.getTaxID() == null) {
				String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta NIT"; 
				facturaExportacion.errorMessages.append(errorMessage);
				System.out.println(errorMessage);
			}
			else {
				jsonObjectReceptor.put(FacturaExportacion.NUMDOCUMENTO, partner.getTaxID().replace("-", ""));
			}
		}
		else {
			if (partner.getDUNS() == null) {
				String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta No. Registro"; 
				facturaExportacion.errorMessages.append(errorMessage);
				System.out.println(errorMessage);
			}
			else {
				jsonObjectReceptor.put(FacturaExportacion.NUMDOCUMENTO,partner.getDUNS().trim().replace("-", ""));
			}
		}

		jsonObjectReceptor.put(FacturaExportacion.NOMBRE, partner.getName());
		jsonObjectReceptor.put(FacturaExportacion.NOMBRECOMERCIAL, partner.getName());
		jsonObjectReceptor.put(FacturaExportacion.TIPOPERSONA, Integer.valueOf(partner.getE_BPType().getValue()));
		

		if (partner.getE_Activity_ID()>0) {
			jsonObjectReceptor.put(FacturaExportacion.DESCACTIVIDAD, partner.getE_Activity().getName());
		} else  {
			jsonObjectReceptor.put(FacturaExportacion.DESCACTIVIDAD, "");
		}

		String complemento = "";
		for (MBPartnerLocation partnerLocation : MBPartnerLocation.getForBPartner(contextProperties, partner.getC_BPartner_ID(), trxName)){
			if (partnerLocation.isBillTo()) {
				String address = partnerLocation.getC_Location().getAddress2() == null?partnerLocation.getC_Location().getAddress1():
					partnerLocation.getC_Location().getAddress1() + " " + partnerLocation.getC_Location().getAddress2();
				complemento = (address);
				jsonObjectReceptor.put(FacturaExportacion.CODPAIS, partnerLocation.getC_Location().getC_Country().getValue());
				jsonObjectReceptor.put(FacturaExportacion.NOMBREPAIS,  partnerLocation.getC_Location().getC_Country().getName());
				jsonObjectReceptor.put(FacturaExportacion.COMPLEMENTO, complemento);
				break;
			}
		}		

		jsonObjectReceptor.put(FacturaExportacion.TELEFONO, partner.get_ValueAsString("phone"));
		jsonObjectReceptor.put(FacturaExportacion.CORREO, partner.get_ValueAsString("EMail"));		

		System.out.println("Finish collecting JSON data for Receptor");
		return jsonObjectReceptor;

	}
	
	private JSONObject generateResumenInputData() {
		System.out.println("Start collecting JSON data for Resumen");
		BigDecimal totalGravada = Env.ZERO;	
		String totalLetras=Msg.getAmtInWords(client.getLanguage(), invoice.getGrandTotal().setScale(2).toString());

		List<MInvoiceTax> invoiceTaxes = new Query(contextProperties , MInvoiceTax.Table_Name , "C_Invoice_ID=?" , trxName)
				.setParameters(invoice.getC_Invoice_ID())
				.list();
		
		for (MInvoiceTax invoiceTax:invoiceTaxes) {
			
				totalGravada = invoiceTax.getTaxBaseAmt();
				break;
			}
				
		JSONObject jsonObjectResumen = new JSONObject();
		jsonObjectResumen.put(FacturaExportacion.TOTALGRAVADA, totalGravada);
		jsonObjectResumen.put(FacturaExportacion.PORCENTAJEDESCUENTO, Env.ZERO);
		jsonObjectResumen.put(FacturaExportacion.TOTALNOGRAVADO, Env.ZERO);
		jsonObjectResumen.put(FacturaExportacion.TOTALPAGAR, invoice.getGrandTotal());
		jsonObjectResumen.put(FacturaExportacion.TOTALLETRAS, totalLetras);
		jsonObjectResumen.put(FacturaExportacion.CONDICIONOPERACION, FacturaExportacion.CONDICIONOPERACION_A_CREDITO);
		jsonObjectResumen.put(FacturaExportacion.TOTALDESCU, Env.ZERO);
		jsonObjectResumen.put(FacturaExportacion.DESCUENTO, Env.ZERO);
		jsonObjectResumen.put(FacturaExportacion.MONTOTOTALOPERACION, invoice.getGrandTotal());
		jsonObjectResumen.put(FacturaExportacion.SEGURO, Env.ZERO);
		jsonObjectResumen.put(FacturaExportacion.FLETE, Env.ZERO);
		
		


		JSONArray jsonArrayPagos = new JSONArray();
			JSONObject jsonPago = new JSONObject();
			jsonPago.put(FacturaExportacion.CODIGO, "05");
			jsonPago.put(FacturaExportacion.MONTOPAGO, invoice.getGrandTotal());
			jsonPago.put(FacturaExportacion.REFERENCIA, "Transferencia_ Deposito Bancario");
			jsonPago.put(FacturaExportacion.PLAZO, invoice.getC_PaymentTerm().getE_TimeSpan().getValue());
			jsonPago.put(FacturaExportacion.PERIODO, invoice.getC_PaymentTerm().getNetDays());
		jsonArrayPagos.put(jsonPago);

		jsonObjectResumen.put(FacturaExportacion.PAGOS, jsonArrayPagos);
		
		System.out.println("Finish collecting JSON data for Resumen");
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
			BigDecimal ventaGravada = Env.ONEHUNDRED;
			BigDecimal ivaItem 		= Env.ZERO;
			MTax tax = null;
			
			if (invoiceLine.getC_Tax().getTaxIndicator().equals("NSUJ"))
				ventaNoSuj = invoiceLine.getLineNetAmt();
			if (invoiceLine.getC_Tax().getTaxIndicator().equals("EXT"))
				ventaExenta = invoiceLine.getLineNetAmt();
			if (invoiceLine.getC_Tax().getTaxIndicator().equals("IVA") ) {
				ventaGravada = invoiceLine.getLineNetAmt(); 
				tax = (MTax)invoiceLine.getC_Tax();
				if (invoiceLine.getTaxAmt().compareTo(Env.ZERO) == 0)
					ivaItem = tax.calculateTax(invoiceLine.getLineNetAmt(), invoice.getM_PriceList().isTaxIncluded(), 2);
			}
			
			JSONObject jsonCuerpoDocumentoItem = new JSONObject();
                
			jsonCuerpoDocumentoItem.put(FacturaExportacion.NUMITEM, i);
			jsonCuerpoDocumentoItem.put(FacturaExportacion.CANTIDAD, invoiceLine.getQtyInvoiced());
			jsonCuerpoDocumentoItem.put(FacturaExportacion.CODIGO, invoiceLine.getM_Product_ID()>0? invoiceLine.getProduct().getValue(): invoiceLine.getC_Charge().getName());
			
			JSONArray jsonTributosArray = new JSONArray();

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
			jsonTributosArray.put("C3");
			jsonCuerpoDocumentoItem. put( FacturaExportacion.TRIBUTOS, jsonTributosArray); //tributosItems.add("20");
			
			jsonCuerpoDocumentoItem.put(FacturaExportacion.UNIMEDIDA, 59);
			jsonCuerpoDocumentoItem.put(FacturaExportacion.DESCRIPCION, description);
			jsonCuerpoDocumentoItem.put(FacturaExportacion.PRECIOUNI, invoiceLine.getPriceActual());
			jsonCuerpoDocumentoItem.put(FacturaExportacion.MONTODESCU, Env.ZERO);
			jsonCuerpoDocumentoItem.put(FacturaExportacion.VENTANOSUJ, ventaNoSuj);
			jsonCuerpoDocumentoItem.put(FacturaExportacion.VENTAEXENTA, ventaExenta);
			jsonCuerpoDocumentoItem.put(FacturaExportacion.VENTAGRAVADA, ventaGravada);
			jsonCuerpoDocumentoItem.put(FacturaExportacion.NOGRAVADO, ventaNoSuj.add(ventaNoSuj));

			jsonCuerpoDocumentoArray.put(jsonCuerpoDocumentoItem);
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() + " Finished");

		}  
		
		jsonCuerpoDocumento.put(FacturaExportacion.CUERPODOCUMENTO, jsonCuerpoDocumentoArray);
		System.out.println("Finish collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		
		return jsonCuerpoDocumento;
	}

	public String createJsonString() throws Exception {
		System.out.println("Factura de Exportacion: start generating JSON object from Document");
		ObjectMapper objectMapper = new ObjectMapper();
		String facturaExportacionAsString    = objectMapper.writeValueAsString(facturaExportacion);
		JSONObject facturaExportacionAsJson = new JSONObject(facturaExportacionAsString);

		facturaExportacionAsJson.remove(FacturaExportacion.ERRORMESSAGES);

		// Manipulate generated JSON string
		String facturaExportacionAsStringFinal = facturaExportacionAsJson.toString().
				replace(":[],", ":null,").
        		replace("\"periodo\":0,\"plazo\":\"01\"", "\"periodo\":null,\"plazo\":null").
        		replace("\"descActividad\":\"\"", "\"descActividad\":null").
        		replace("\"codActividad\":\"\"", "\"codActividad\":null").
				replace("\"documentoRelacionado\":[]", "\"documentoRelacionado\":null").
				replace("\"ventaTercero\":{\"nit\":null,\"nombre\":null},", "\"ventaTercero\":null,").
				replace("\"tributos\":[{\"descripcion\":null,\"codigo\":null,\"valor\":null}]", "\"tributos\":null").
				replace("\"extension\":{\"docuEntrega\":null,\"placaVehiculo\":null,\"observaciones\":null,\"nombRecibe\":null,\"nombEntrega\":null,\"docuRecibe\":null},", 
						"\"extension\":null,");

		System.out.println("Factura de Exportacion: generated JSON object from Document:");
		System.out.println(facturaExportacionAsStringFinal);
		System.out.println("Factura de Exportacion: end generating JSON object from Document");
		return facturaExportacionAsStringFinal;	
	}

	public String getNumeroControl(Integer id, MOrgInfo orgInfo, String prefix) {
		String idIdentification  = StringUtils.leftPad(id.toString(), 15,"0");
		String duns = orgInfo.getDUNS().replace("-", "");
		String numeroControl = prefix + StringUtils.leftPad(duns.trim(), 8,"0") + "-"+ idIdentification;
		return numeroControl;
	}
	

	@Override
	public StringBuffer getEDocumentErrorMessages() {
		 return facturaExportacion.errorMessages;
	 }
}

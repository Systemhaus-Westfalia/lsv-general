package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.core.domains.models.X_C_UOM;
import org.adempiere.core.domains.models.X_E_DocType;
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
import org.shw.lsv.einvoice.feccfcreditofiscalv3.CreditoFiscal;
import org.shw.lsv.einvoice.fefcfacturaelectronicav1.Factura;
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
		if (identification != null) {
			notaDeCredito.errorMessages.append(notaDeCredito.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if (! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeCredito.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Emisor");
		EmisorNotaDeCredito emisor = notaDeCredito.getEmisor();
		if (emisor != null) {
			notaDeCredito.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if (! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeCredito.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Documentos Relacionados");
		List<DocumentoRelacionadoItemNotaDeCredito> documentosRelacionados = notaDeCredito.getDocumentoRelacionado();
		if (documentosRelacionados != null) {
			notaDeCredito.fillDocumentosRelacionados(jsonInputToFactory);

			documentosRelacionados.stream().forEach( documentoRelacionadoItem -> {
				String resultLambda = documentoRelacionadoItem.validateValues();
					if (! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						notaDeCredito.errorMessages.append(resultLambda);
					}
				}
			);
		}

		System.out.println("Instantiate, fill and verify Cuerpo Documento");
		List<CuerpoDocumentoItemNotaDeCredito> cuerpoDocumento = notaDeCredito.getCuerpoDocumento();
		if (cuerpoDocumento != null) {
			notaDeCredito.fillCuerpoDocumento(jsonInputToFactory);

			cuerpoDocumento.stream().forEach( cuerpoDocumentoItem -> {
				String resultLambda = cuerpoDocumentoItem.validateValues();
					if (! resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
						notaDeCredito.errorMessages.append(resultLambda);
					}
				}
			);
		}

		System.out.println("Instantiate, fill and verify Resumen");
		ResumenNotaDeCredito resumen = notaDeCredito.getResumen();
		if (resumen != null) {
			notaDeCredito.fillResumen(jsonInputToFactory);
			result = resumen.validateValues();
			if (! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeCredito.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Receptor");
		ReceptorNotaDeCredito receptor = notaDeCredito.getReceptor();
		if (receptor != null) {
			notaDeCredito.fillReceptor(jsonInputToFactory);
			result = receptor.validateValues();
			if (! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				notaDeCredito.errorMessages.append(result);
			}
		}

		notaDeCredito.validateValues();
		if (! result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
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
		int daysContingencia = getContingenciaDays(invoice.getAD_Client_ID());
		if (TimeUtil.getDaysBetween(invoice.getDateAcct(), TimeUtil.getDay(0)) > daysContingencia) {
			tipoModelo       = 2;
			tipoOperacion    = 2;
			motivoContin     = "Contigencia por fecha de factura";
			tipoContingencia = 5;
		}

		String numeroControl    = createNumeroControl(invoice, client);
		String codigoGeneracion = createCodigoGeneracion(invoice);
		String horEmi           = gethorEmi();
		String fecEmi           = invoice.getDateAcct().toString().substring(0, 10);
		X_E_DocType e_DocType =   docType_getE_DocType((MDocType)invoice.getC_DocType()) ;
		String version = e_DocType.get_ValueAsString("E_Version");
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
		jsonObjectIdentificacion.put(NotaDeCredito.VERSION, version);
		jsonObjectIdentificacion.put(NotaDeCredito.AMBIENTE, client_getE_Enviroment(client).getValue());
		jsonObjectIdentificacion.put("fusion", JSONObject.NULL);

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

		JSONObject jsonDireccion = new JSONObject();
		jsonDireccion.put(Factura.DEPARTAMENTO,  city_getRegionValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(Factura.DISTRITO,     city_getValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(Factura.MUNICIPIO,     city_getMunicipioValue((MCity)orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(Factura.COMPLEMENTO, orgInfo.getC_Location().getAddress1());
		jsonObjectEmisor.put(Factura.DIRECCION, jsonDireccion);
		
		jsonObjectEmisor.put(NotaDeCredito.TELEFONO, client.get_ValueAsString("phone"));
		jsonObjectEmisor.put(NotaDeCredito.CORREO, client_getEmail(client));

		System.out.println("Finish collecting JSON data for Emisor");
		return jsonObjectEmisor;
	}

	private JSONObject generateReceptorInputData() {
		System.out.println("CreditoFiscal: start collecting JSON data for Receptor");

		MBPartner partner = (MBPartner)invoice.getC_BPartner();
		if (bPartner_getE_Activity(partner).getE_Activity_ID() <= 0 || bPartner_getE_Recipient_Identification(partner).getE_Recipient_Identification_ID() <= 0) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica";
			notaDeCredito.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}

		JSONObject jsonObjectReceptor = new JSONObject();
		if (partner.getTaxID() == null) {
			String errorMessage = "Socio de Negocio " + partner.getName() + ": Falta configuracion para Facturacion Electronica";
			notaDeCredito.errorMessages.append(errorMessage);
			System.out.println(errorMessage);
		}
		jsonObjectReceptor.put(NotaDeCredito.TIPODOCUMENTO, bPartner_getE_Recipient_Identification(partner).getValue());
		jsonObjectReceptor.put("numDocumento", partner.getTaxID() != null ? partner.getTaxID().replace("-", "") : "");
		jsonObjectReceptor.put(NotaDeCredito.NRC, partner.getDUNS() != null ? partner.getDUNS().trim().replace("-", "") : JSONObject.NULL);
		jsonObjectReceptor.put(NotaDeCredito.NOMBRE, partner.getName());

		if (bPartner_getE_Activity(partner).getE_Activity_ID() > 0) {
			jsonObjectReceptor.put(NotaDeCredito.CODACTIVIDAD, bPartner_getE_Activity(partner).getValue());
			jsonObjectReceptor.put(NotaDeCredito.DESCACTIVIDAD, bPartner_getE_Activity(partner).getName());
		}

		JSONObject jsonDireccion = new JSONObject();
		String departamento = "";
		String municipio = "";
		String distrito = "";
		String complemento = "";
		for (MBPartnerLocation partnerLocation : MBPartnerLocation.getForBPartner(contextProperties, partner.getC_BPartner_ID(), trxName)) {
			if (partnerLocation.isBillTo() && partnerLocation.getC_Location().getC_Country_ID() == 173) {
				departamento =  city_getRegionValue((MCity)partnerLocation.getC_Location().getC_City());
				distrito =  city_getValue((MCity)partnerLocation.getC_Location().getC_City());
				municipio = city_getMunicipioValue((MCity)partnerLocation.getC_Location().getC_City());
				complemento = (partnerLocation.getC_Location().getAddress1() + " " 
				+ partnerLocation.getC_Location().getAddress2());
				jsonDireccion.put(Factura.DEPARTAMENTO, departamento);
				jsonDireccion.put(Factura.MUNICIPIO, municipio);
				jsonDireccion.put(Factura.DISTRITO, distrito);
				jsonDireccion.put(Factura.COMPLEMENTO, complemento.replace("null", ""));
				jsonObjectReceptor.put(Factura.DIRECCION, jsonDireccion);
				break;
			}
		}

		// In case there is no billing address
		if (departamento.isEmpty()) {
			jsonDireccion.put(NotaDeCredito.DEPARTAMENTO, departamento);
			jsonDireccion.put(NotaDeCredito.MUNICIPIO, municipio);
			jsonDireccion.put("distrito", municipio);
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
		BigDecimal totalNoSuj   = Env.ZERO;
		BigDecimal totalExenta  = Env.ZERO;
		BigDecimal totalGravada = Env.ZERO;
		BigDecimal totalNoGravada = Env.ZERO;
		BigDecimal ivaRete      = Env.ZERO;
		BigDecimal totalIva     = Env.ZERO;
		BigDecimal ivaPerci     = Env.ZERO;

		// Compute totalIva from lines first — mirrors body computation to guarantee sum consistency with MH
		for (MInvoiceLine line : invoice.getLines()) {
			if (line.getC_Tax().getTaxIndicator().equals("IVA")) {
				MTax lineTax = (MTax) line.getC_Tax();
				BigDecimal ivaItem = line.getTaxAmt().compareTo(Env.ZERO) != 0
						? line.getTaxAmt()
						: lineTax.calculateTax(line.getLineNetAmt(), invoice.getM_PriceList().isTaxIncluded(), 2);
				totalIva = totalIva.add(ivaItem);
			}
		}

		List<MInvoiceTax> invoiceTaxes = new Query(contextProperties, MInvoiceTax.Table_Name, "C_Invoice_ID=?", trxName)
				.setParameters(invoice.getC_Invoice_ID())
				.list();
		JSONObject jsonObjectResumen = new JSONObject();

		JSONArray jsonTributosArray = new JSONArray();
		for (MInvoiceTax invoiceTax : invoiceTaxes) {
			if (invoiceTax.getC_Tax().getTaxIndicator().equals("RET")) {
				ivaRete = ivaRete.add(invoiceTax.getTaxAmt().multiply(new BigDecimal(-1)));
				continue;
			}
			JSONObject jsonTributoItem = new JSONObject();
			if (invoiceTax.getC_Tax().getTaxIndicator().equals("NSUJ")) {
				if (invoiceTax.getC_Tax().getC_TaxCategory().getCommodityCode() != null &&
					invoiceTax.getC_Tax().getC_TaxCategory().getCommodityCode().equals(CHARGETYPE_CTAJ))
					totalNoGravada = invoiceTax.getTaxBaseAmt();
				else
					totalNoSuj = invoiceTax.getTaxBaseAmt();
			} else if (invoiceTax.getC_Tax().getTaxIndicator().equals("EXT")) {
				totalExenta = invoiceTax.getTaxBaseAmt();
				jsonTributoItem.put(NotaDeCredito.CODIGO,      tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getValue());
				jsonTributoItem.put(NotaDeCredito.DESCRIPCION, tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getName());
				jsonTributoItem.put(NotaDeCredito.VALOR,       invoiceTax.getTaxAmt());
			} else if (invoiceTax.getC_Tax().getTaxIndicator().equals("IVA")) {
				totalGravada = invoiceTax.getTaxBaseAmt();
				// NCE v4: IVA stays in resumen.tributos; montoTotalOperacion includes it
				// resumen.totalIva is an additional informative field (MH validates: totalIva = montoTotalOperacion - subTotalVentas)
				// Use pre-computed line-level totalIva as valor to avoid rounding mismatch with MH
				jsonTributoItem.put(NotaDeCredito.CODIGO,      tax_getE_Duties((MTax)invoiceTax.getC_Tax()).getValue());
				jsonTributoItem.put(NotaDeCredito.DESCRIPCION, invoiceTax.getC_Tax().getName());
				jsonTributoItem.put(NotaDeCredito.VALOR,       totalIva);
				jsonTributosArray.put(jsonTributoItem);
			}
		}
		jsonObjectResumen.put(NotaDeCredito.TRIBUTOS, jsonTributosArray);

		jsonObjectResumen.put(NotaDeCredito.TOTALNOSUJ,          totalNoSuj);
		jsonObjectResumen.put(NotaDeCredito.TOTALEXENTA,         totalExenta);
		jsonObjectResumen.put(NotaDeCredito.TOTALGRAVADA,        totalGravada);
		BigDecimal subTotalVentas = totalGravada.add(totalNoSuj).add(totalExenta);
		jsonObjectResumen.put(NotaDeCredito.SUBTOTALVENTAS,      subTotalVentas);
		jsonObjectResumen.put(NotaDeCredito.TOTALDESCU,          Env.ZERO);
		// montoTotalOperacion includes IVA (via tributos.valor); MH validates: totalIva = montoTotalOperacion - subTotalVentas
		BigDecimal montoTotalOperacion = subTotalVentas.add(totalIva).add(totalNoGravada);
		BigDecimal totalPagar = montoTotalOperacion.add(ivaPerci).subtract(ivaRete);
		String totalLetras = Msg.getAmtInWords(client.getLanguage(), totalPagar.setScale(2).toString());

		jsonObjectResumen.put(NotaDeCredito.MONTOTOTALOPERACION, montoTotalOperacion);
		jsonObjectResumen.put("ivaPerci",                        ivaPerci);
		jsonObjectResumen.put("totalIva",                        Env.ZERO);
		jsonObjectResumen.put("ivaRete",                         ivaRete);
		jsonObjectResumen.put(NotaDeCredito.TOTALNOGRAVADO,      totalNoGravada);
		jsonObjectResumen.put(NotaDeCredito.TOTALPAGAR,          totalPagar);
		jsonObjectResumen.put(NotaDeCredito.TOTALLETRAS,         totalLetras);
		jsonObjectResumen.put(NotaDeCredito.CONDICIONOPERACION,  NotaDeCredito.CONDICIONOPERACION_A_CREDITO);
		jsonObjectResumen.put("observaciones",                   JSONObject.NULL);
		jsonObjectResumen.put("codigoRetencionMH",               JSONObject.NULL);

		System.out.println("NotaDeCredito: end collecting JSON data for Resumen");
		return jsonObjectResumen;
	}

	private JSONObject generateCuerpoDocumentoInputData() {
		System.out.println("Start collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		JSONObject jsonCuerpoDocumento = new JSONObject();
		JSONArray jsonCuerpoDocumentoArray = new JSONArray();
		int i = 0;

		for (MInvoiceLine invoiceLine : invoice.getLines()) {
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine());
			i++;
			BigDecimal ventaNoSuj     = Env.ZERO;
			BigDecimal ventaExenta    = Env.ZERO;
			BigDecimal ventaGravada   = Env.ZERO;
			BigDecimal ventaNoGravada = Env.ZERO;
			BigDecimal ivaItem        = Env.ZERO;
			MTax tax = (MTax)invoiceLine.getC_Tax();
			boolean ventanoGravada = (invoiceLine.getC_Tax().getTaxIndicator().equals("NSUJ") &&
					invoiceLine.getC_Charge_ID() > 0
					&& invoiceLine.getC_Charge().getC_ChargeType().getValue().equals("CTAJ")) ? true : false;

			if (invoiceLine.getC_Tax().getTaxIndicator().equals("NSUJ")) {
				if (ventanoGravada)
					ventaNoGravada = invoiceLine.getLineNetAmt();
				else
					ventaNoSuj = invoiceLine.getLineNetAmt();
			}
			if (invoiceLine.getC_Tax().getTaxIndicator().equals("EXT"))
				ventaExenta = invoiceLine.getLineNetAmt();
			if (invoiceLine.getC_Tax().getTaxIndicator().equals("IVA")) {
				ventaGravada = invoiceLine.getLineNetAmt();
				ivaItem = invoiceLine.getTaxAmt().compareTo(Env.ZERO) != 0
						? invoiceLine.getTaxAmt()
						: tax.calculateTax(invoiceLine.getLineNetAmt(), invoice.getM_PriceList().isTaxIncluded(), 2);
			}

			JSONObject jsonCuerpoDocumentoItem = new JSONObject();
			MInvoice refInvoice = invoiceLine_getRef_InvoiceLine_getC_Invoice(invoiceLine);
			String numerodocumentno = invoice_ei_codigoGeneracion(refInvoice).equals("") ?
					refInvoice.getDocumentNo() :
					invoice_ei_codigoGeneracion(refInvoice);

			jsonCuerpoDocumentoItem.put(NotaDeCredito.NUMITEM,       i);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.TIPOITEM,      invoiceLineProductType(invoiceLine.getM_Product_ID()));
			jsonCuerpoDocumentoItem.put(NotaDeCredito.NUMERODOCUMENTO, numerodocumentno);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.CANTIDAD,      invoiceLine.getQtyEntered());
			jsonCuerpoDocumentoItem.put(CreditoFiscal.CODIGO,        invoiceLine.getM_Product_ID() > 0 ?
					invoiceLine.getProduct().getValue() : invoiceLine.getC_Charge().getC_ChargeType().getValue());
			jsonCuerpoDocumentoItem.put(NotaDeCredito.CODTRIBUTO,    JSONObject.NULL);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.UNIMEDIDA,     uom_getValue((X_C_UOM)invoiceLine.getC_UOM()));
			jsonCuerpoDocumentoItem.put(NotaDeCredito.DESCRIPCION,   invoiceLine.getM_Product_ID() > 0 ?
					invoiceLine.getM_Product().getName() : invoiceLine.getC_Charge().getName());
			jsonCuerpoDocumentoItem.put(NotaDeCredito.PRECIOUNI,     invoiceLine.getPriceEntered());
			jsonCuerpoDocumentoItem.put(NotaDeCredito.MONTODESCU,    Env.ZERO);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.VENTANOSUJ,    ventaNoSuj);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.VENTAEXENTA,   ventaExenta);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.VENTAGRAVADA,  ventaGravada);

			JSONArray jsonTributosArray = new JSONArray();
			if (ventaGravada.compareTo(Env.ZERO) != 0) {
				jsonTributosArray.put(tax_getE_Duties(tax).getValue());
			}
			jsonCuerpoDocumentoItem.put(NotaDeCredito.TRIBUTOS,      jsonTributosArray);
			jsonCuerpoDocumentoItem.put(NotaDeCredito.NOGRAVADO,      ventaNoGravada);
			jsonCuerpoDocumentoItem.put("ivaPerci",                  Env.ZERO);
			jsonCuerpoDocumentoItem.put("totalIva",                  Env.ZERO);
			jsonCuerpoDocumentoItem.put("ivaRete",                   Env.ZERO);

			jsonCuerpoDocumentoArray.put(jsonCuerpoDocumentoItem);
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() + " Finished");
		}

		jsonCuerpoDocumento.put(NotaDeCredito.CUERPODOCUMENTO, jsonCuerpoDocumentoArray);
		System.out.println("Finish collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		return jsonCuerpoDocumento;
	}

	private JSONObject generateDocumentoRelacionadoInputData() {
		System.out.println("Start collecting JSON data for Documento Relacionado. Document: " + invoice.getDocumentNo());
		JSONObject jsonDocumentoRelacionado = new JSONObject();
		JSONArray jsonDocumentoRelacionadoArray = new JSONArray();

		HashMap<Integer,MInvoice> invoiceIds = new HashMap<Integer, MInvoice>();
		for (MInvoiceLine invoiceLine : invoice.getLines()) {
			MInvoiceLine invoiceLineOrg = new MInvoiceLine(contextProperties, invoiceLine.getRef_InvoiceLine_ID(), null);
			invoiceIds.put(invoiceLineOrg.getC_Invoice_ID(), (MInvoice)invoiceLineOrg.getC_Invoice());
		}
		for (MInvoice invoiceOrginal : invoiceIds.values()) {
			JSONObject jsonDocumentoRelacionadoItem = new JSONObject();
			jsonDocumentoRelacionadoItem.put(NotaDeCredito.TIPODOCUMENTO, docType_getE_DocType((MDocType)invoiceOrginal.getC_DocType()).getValue());
			int tipoGeneracion = invoice_ei_codigoGeneracion(invoiceOrginal) == "" ? 1 : 2;
			jsonDocumentoRelacionadoItem.put(NotaDeCredito.TIPOGENERACION, tipoGeneracion);
			String documentno = tipoGeneracion == 2 ? invoice_ei_codigoGeneracion(invoiceOrginal) : invoiceOrginal.getDocumentNo();
			jsonDocumentoRelacionadoItem.put(NotaDeCredito.NUMERODOCUMENTO, documentno);
			jsonDocumentoRelacionadoItem.put("fechaEmision", invoiceOrginal.getDateAcct().toString().substring(0, 10));
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
		String notaDeCreditoAsStringFinal = notaDeCreditoAsJson.toString()
				.replace(":[],", ":null,")
				.replace("\"telefono\":\"\"",       "\"telefono\":null")
				.replace("\"motivoContin\":\"\"",   "\"motivoContin\":null")
				.replace("\"tipoContingencia\":0",  "\"tipoContingencia\":null")
				.replace("\"documentoRelacionado\":[]", "\"documentoRelacionado\":null")
				.replace("\"ventaTercero\":{\"nit\":null,\"nombre\":null},", "\"ventaTercero\":null,")
				.replace("\"tributos\":[{\"descripcion\":null,\"codigo\":null,\"valor\":null}]", "\"tributos\":null");

		System.out.println("Nota de Credito: generated JSON object from Document:");
		System.out.println(notaDeCreditoAsStringFinal);
		System.out.println("Nota de Credito: end generating JSON object from Document");
		return notaDeCreditoAsStringFinal;
	}

	public String getNumeroControl(Integer id, MOrgInfo orgInfo, String prefix) {
		// TODO: update to v4 pattern: DTE-05-(M|B|S|P)NNNPnnn-NNNNNNNNNNNNNNN
		String idIdentification = StringUtils.leftPad(id.toString(), 15, "0");
		String duns = orgInfo.getDUNS().replace("-", "");
		String numeroControl = prefix + StringUtils.leftPad(duns.trim(), 8, "0") + "-" + idIdentification;
		return numeroControl;
	}

	@Override
	public StringBuffer getEDocumentErrorMessages() {
		return notaDeCredito.errorMessages;
	}
}

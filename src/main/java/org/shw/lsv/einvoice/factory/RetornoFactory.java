package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.core.domains.models.I_C_InvoiceLine;
import org.adempiere.core.domains.models.X_C_UOM;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MRMA;
import org.compiere.model.MRMALine;
import org.compiere.model.MTax;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.feccfcreditofiscalv3.CreditoFiscal;
import org.shw.lsv.einvoice.feretornov1.ApendiceItemRetorno;
import org.shw.lsv.einvoice.feretornov1.CuerpoDocumentoItemRetorno;
import org.shw.lsv.einvoice.feretornov1.DocumentoRelacionadoItemRetorno;
import org.shw.lsv.einvoice.feretornov1.EmisorRetorno;
import org.shw.lsv.einvoice.feretornov1.IdentificacionRetorno;
import org.shw.lsv.einvoice.feretornov1.ReceptorRetorno;
import org.shw.lsv.einvoice.feretornov1.ResumenRetorno;
import org.shw.lsv.einvoice.feretornov1.Retorno;
import org.shw.lsv.einvoice.utils.EDocumentFactory;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RetornoFactory extends EDocumentFactory {

	// Common JSON field names (mirrored here to avoid cross-package constant access)
	private static final String IDENTIFICACION       = "identificacion";
	private static final String DOCUMENTORELACIONADO = "documentoRelacionado";
	private static final String EMISOR               = "emisor";
	private static final String CUERPODOCUMENTO      = "cuerpoDocumento";
	private static final String RESUMEN              = "resumen";
	private static final String APENDICE             = "apendice";
	private static final String NIT                  = "nit";
	private static final String NOMBRE               = "nombre";
	private static final String CORREO               = "correo";
	private static final String TELEFONO             = "telefono";
	private static final String TIPODOCUMENTO        = "tipoDocumento";
	private static final String CODIGOGENERACION     = "codigoGeneracion";
	private static final String TIPOMODELO           = "tipoModelo";
	private static final String TIPOOPERACION        = "tipoOperacion";
	private static final String FECEMI               = "fecEmi";
	private static final String HOREMI               = "horEmi";
	private static final String TIPOMONEDA           = "tipoMoneda";
	private static final String AMBIENTE             = "ambiente";
	private static final String MOTIVOCONTIN         = "motivoContin";
	private static final String TIPOCONTINGENCIA     = "tipoContingencia";
	private static final String NUMITEM              = "numItem";
	private static final String TIPOITEM             = "tipoItem";
	private static final String CANTIDAD             = "cantidad";
	private static final String PRECIOUNI            = "precioUni";
	private static final String DESCRIPCION          = "descripcion";
	private static final String CODIGO               = "codigo";
	private static final String UNIMEDIDA            = "uniMedida";
	private static final String MONTODESCU           = "montoDescu";
	private static final String CODTRIBUTO           = "codTributo";
	private static final String VENTANOSUJ           = "ventaNoSuj";
	private static final String VENTAEXENTA          = "ventaExenta";
	private static final String VENTAGRAVADA         = "ventaGravada";
	private static final String TRIBUTOS             = "tributos";
	private static final String VALOR                = "valor";
	private static final String TOTALNOSUJ           = "totalNoSuj";
	private static final String TOTALEXENTA          = "totalExenta";
	private static final String TOTALGRAVADA         = "totalGravada";
	private static final String SUBTOTALVENTAS       = "subTotalVentas";
	private static final String MONTOTOTALOPERACION  = "montoTotalOperacion";
	private static final String TOTALLETRAS          = "totalLetras";
	private static final String TIPO_DE_EVENTO 		 = "tipoEvento";

	// Retorno-specific JSON field names
	private static final String FUSION               = "fusion";
	private static final String NUMDOCUMENTO         = "numDocumento";
	private static final String NOGRAVADO            = "noGravado";
	private static final String TOTALIVA             = "totalIva";
	private static final String IVARETE              = "ivaRete";
	private static final String TOTALNOGRAVADO       = "totalNoGravado";
	private static final String TOTALPAGAR           = "totalPagar";
	private static final String FECHAEMISION         = "fechaEmision";
	private static final String DOCUMENTO            = "documento";
	private static final String VENTATERCERO         = "ventaTercero";
	private static final String COMPRATERCERO        = "compraTercero";
	private static final String CODPAIS              = "codPais";
	private static final String NOMBREPAIS           = "nombrePais";
	private static final String CODESTABLEMH         = "codEstableMH";
	private static final String CODESTABLE           = "codEstable";
	private static final String CODPUNTOVENTAMH      = "codPuntoVentaMH";
	private static final String CODPUNTOVENTA        = "codPuntoVenta";
	private static final String RECINTOFISCAL        = "recintoFiscal";
	private static final String TIPOREGIMEN          = "tipoRegimen";
	private static final String REGIMEN              = "regimen";
	private static final String TIPOITEMEXPOR        = "tipoItemExpor";
	private static final String COMPRA               = "compra";
	private static final String PSV                  = "psv";
	private static final String IVAITEM              = "ivaItem";
	private static final String SEGURO               = "seguro";
	private static final String FLETE                = "flete";
	private static final String RETERRENTA           = "reteRenta";
	private static final String TOTALCOMPRAEXCLUIDOS = "totalCompraExcluidos";
	private static final String TOTALSEGURO          = "totalSeguro";
	private static final String TOTALFLETE           = "totalFlete";
	private static final String TOTALNOONEROSAS      = "totalNoOnerosas";
	private static final String SALDOFAVOR           = "saldoFavor";

	// tipoModelo / tipoOperacion values
	private static final int TIPOMODELO_NOCONTIGENCIA    = 1;
	private static final int TIPOMODELO_CONTIGENCIA      = 2;
	private static final int TIPOOPERACION_NOCONTIGENCIA = 1;
	private static final int TIPOOPERACION_CONTIGENCIA   = 4;

	Retorno retorno;
	MInvoice invoice;
	MInvoice refInvoice;

	public RetornoFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo, MInvoice invoice) {
		super(trxName, contextProperties, client, orgInfo);
		this.invoice = invoice;
		MRMA mrma = (MRMA)invoice.getM_RMA();
		MInOut inOut = (MInOut)mrma.getInOut();
		List<MInvoice>  invoices = MInvoice.getOfOrder(invoice.getCtx(), inOut.getC_Order_ID(), invoice.get_TrxName());
		refInvoice = invoices.get(0);
		retorno = new Retorno();
	}

	public Retorno generateEDocument() {
		System.out.println("Retorno: start generating and filling the Document");
		String result = "";

		System.out.println("Instantiate, fill and verify Identificacion");
		IdentificacionRetorno identification = retorno.getIdentificacion();
		if (identification != null) {
			retorno.errorMessages.append(retorno.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if (!result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				retorno.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify DocumentoRelacionado");
		List<DocumentoRelacionadoItemRetorno> documentoRelacionado = retorno.getDocumentoRelacionado();
		if (documentoRelacionado != null) {
			retorno.fillDocumentosRelacionados(jsonInputToFactory);
			documentoRelacionado.stream().forEach(item -> {
				String resultLambda = item.validateValues();
				if (!resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
					retorno.errorMessages.append(resultLambda);
				}
			});
		}

		System.out.println("Instantiate, fill and verify Emisor");
		EmisorRetorno emisor = retorno.getEmisor();
		if (emisor != null) {
			retorno.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if (!result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				retorno.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify Documento");
		retorno.fillDocumento(jsonInputToFactory);
		ReceptorRetorno documento = retorno.getDocumento();
		if (documento != null) {
			result = documento.validateValues();
			if (!result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				retorno.errorMessages.append(result);
			}
		}

		System.out.println("Instantiate, fill and verify VentaTercero");
		retorno.fillVentaTercero(jsonInputToFactory);

		System.out.println("Instantiate, fill and verify CompraTercero");
		retorno.fillCompraTercero(jsonInputToFactory);

		System.out.println("Instantiate, fill and verify Cuerpo Documento");
		List<CuerpoDocumentoItemRetorno> cuerpoDocumento = retorno.getCuerpoDocumento();
		if (cuerpoDocumento != null) {
			retorno.fillCuerpoDocumento(jsonInputToFactory);
			cuerpoDocumento.stream().forEach(item -> {
				String resultLambda = item.validateValues();
				if (!resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
					retorno.errorMessages.append(resultLambda);
				}
			});
		}

		System.out.println("Instantiate, fill and verify Resumen");
		ResumenRetorno resumen = retorno.getResumen();
		if (resumen != null) {
			retorno.fillResumen(jsonInputToFactory);
			result = resumen.validateValues();
			if (!result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
				retorno.errorMessages.append(result);
			}
		}

//		List<ApendiceItemRetorno> apendice = retorno.getApendice();
//		if (apendice != null) {
//			retorno.fillApendice(jsonInputToFactory);
//			apendice.stream().forEach(item -> {
//				String resultLambda = item.validateValues();
//				if (!resultLambda.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
//					retorno.errorMessages.append(resultLambda);
//				}
//			});
//		}

		retorno.validateValues();
		if (!result.equals(EDocumentUtils.VALIDATION_RESULT_OK)) {
			retorno.errorMessages.append(result);
		}

		System.out.println("Retorno: end generating and filling the Document");
		return retorno;
	}

	@Override
	public void generateJSONInputData() {
		System.out.println("Retorno: start collecting JSON data for all components");
		jsonInputToFactory = new JSONObject();

		jsonInputToFactory.put(IDENTIFICACION,       generateIdentificationInputData());
		jsonInputToFactory.put(DOCUMENTORELACIONADO, generateDocumentoRelacionadoInputData());
		jsonInputToFactory.put(EMISOR,               generateEmisorInputData());
		jsonInputToFactory.put(CUERPODOCUMENTO,      generateCuerpoDocumentoInputData());
		jsonInputToFactory.put(RESUMEN,              generateResumenInputData());
		jsonInputToFactory.put(APENDICE,             generateApendiceInputData(invoice.getC_Invoice_ID()));

		JSONObject docInput = generateDocumentoInputData();
		jsonInputToFactory.put(DOCUMENTO, docInput != null ? docInput : JSONObject.NULL);

		// ventaTercero and compraTercero are null for standard domestic returns
		jsonInputToFactory.put(VENTATERCERO,  JSONObject.NULL);
		jsonInputToFactory.put(COMPRATERCERO, JSONObject.NULL);

		System.out.println("Generated JSON object from Invoice:");
		System.out.println(jsonInputToFactory.toString());
		System.out.println("Retorno: end collecting JSON data for all components");
	}

	private JSONObject generateIdentificationInputData() {
		System.out.println("Retorno: start collecting JSON data for Identificacion");

		String codigoGeneracion = createCodigoGeneracion(invoice);
		JSONObject jsonIdentificacion = new JSONObject();
		Boolean isContigencia = false;
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String horEmi = timeFormat.format(cal.getTime());
		int daysContingencia = getContingenciaDays(invoice.getAD_Client_ID());
		if (TimeUtil.getDaysBetween(invoice.getDateAcct(), TimeUtil.getDay(0)) > daysContingencia) {
			isContigencia = true;
		}
		int tipoModelo    = isContigencia ? TIPOMODELO_CONTIGENCIA    : TIPOMODELO_NOCONTIGENCIA;
		int tipoOperacion = isContigencia ? TIPOOPERACION_CONTIGENCIA : TIPOOPERACION_NOCONTIGENCIA;

		jsonIdentificacion.put(CODIGOGENERACION, codigoGeneracion);
		jsonIdentificacion.put(TIPOMODELO,       tipoModelo);
		jsonIdentificacion.put(TIPOOPERACION,    tipoOperacion);
		jsonIdentificacion.put(FECEMI,           invoice.getDateAcct().toString().substring(0, 10));
		jsonIdentificacion.put(HOREMI,           horEmi);
		jsonIdentificacion.put(TIPOMONEDA,       "USD");
		jsonIdentificacion.put(TIPO_DE_EVENTO, "18");
		jsonIdentificacion.put(AMBIENTE,         client_getE_Enviroment(client).getValue());
		jsonIdentificacion.put(FUSION,           JSONObject.NULL);

		if (isContigencia) {
			jsonIdentificacion.put(MOTIVOCONTIN,    "Contingencia por fecha de factura");
			jsonIdentificacion.put(TIPOCONTINGENCIA, 5);
		} else {
			jsonIdentificacion.put(MOTIVOCONTIN,    "");
			jsonIdentificacion.put(TIPOCONTINGENCIA, "");
		}

		System.out.println("Retorno: end collecting JSON data for Identificacion");
		return jsonIdentificacion;
	}

	private JSONObject generateDocumentoRelacionadoInputData() {
		System.out.println("Retorno: start collecting JSON data for DocumentoRelacionado");

		JSONObject jsonWrapper = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		
		if (refInvoice != null) {
			JSONObject itemJson = new JSONObject();
			itemJson.put(TIPODOCUMENTO,    refInvoice.get_ValueAsString("ei_tipoDte"));
			itemJson.put(CODIGOGENERACION, refInvoice.get_ValueAsString("ei_codigoGeneracion"));
			itemJson.put(FECHAEMISION,     refInvoice.getDateAcct().toString().substring(0, 10));
			jsonArray.put(itemJson);
		}

		jsonWrapper.put(DOCUMENTORELACIONADO, jsonArray);
		System.out.println("Retorno: end collecting JSON data for DocumentoRelacionado");
		return jsonWrapper;
	}

	private JSONObject generateEmisorInputData() {
		System.out.println("Retorno: start collecting JSON data for Emisor");

		JSONObject jsonEmisor = new JSONObject();
		jsonEmisor.put(NIT,           client.get_ValueAsString("ei_nit"));
		jsonEmisor.put(NOMBRE,        client.getDescription());
		jsonEmisor.put(CODESTABLEMH,  StringUtils.leftPad(client.get_ValueAsString("ei_codEstableMH").trim(), 4, "0"));
		jsonEmisor.put(CODESTABLE,    JSONObject.NULL);
		jsonEmisor.put(CODPUNTOVENTAMH, StringUtils.leftPad(client.get_ValueAsString("ei_codPuntoVentaMH").trim(), 4, "0"));
		jsonEmisor.put(CODPUNTOVENTA, JSONObject.NULL);
		jsonEmisor.put(RECINTOFISCAL, JSONObject.NULL);
		jsonEmisor.put(TIPOREGIMEN,   JSONObject.NULL);
		jsonEmisor.put(REGIMEN,       JSONObject.NULL);
		jsonEmisor.put(TIPOITEMEXPOR, JSONObject.NULL);

		System.out.println("Retorno: end collecting JSON data for Emisor");
		return jsonEmisor;
	}

	private JSONObject generateDocumentoInputData() {
		System.out.println("Retorno: start collecting JSON data for Documento");

		MBPartner partner = (MBPartner) invoice.getC_BPartner();
		if (partner == null || partner.getTaxID() == null) {
			System.out.println("Retorno: Documento is null — no BPartner or TaxID");
			return null;
		}

		JSONObject jsonDocumento = new JSONObject();
		jsonDocumento.put(TIPODOCUMENTO, bPartner_getE_Recipient_Identification(partner).getValue());
		jsonDocumento.put(NUMDOCUMENTO,  partner.getTaxID().replace("-", ""));
		jsonDocumento.put(NOMBRE,        partner.getName());
		jsonDocumento.put(CODPAIS,       "SV");
		jsonDocumento.put(NOMBREPAIS,    "El Salvador");
		jsonDocumento.put(TELEFONO,      partner.get_ValueAsString("phone"));
		jsonDocumento.put(CORREO,        partner.get_ValueAsString("EMail"));

		System.out.println("Retorno: end collecting JSON data for Documento");
		return jsonDocumento;
	}

	private JSONObject generateCuerpoDocumentoInputData() {
		System.out.println("Retorno: start collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());

		JSONObject jsonWrapper = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		// codigoGeneracion of the original DTE being returned — stored on the referenced invoice
		String codigoGeneracionOrig = "";
		if (refInvoice != null) {
			codigoGeneracionOrig = refInvoice.get_ValueAsString("ei_codigoGeneracion");
		}

		int i = 0;
		for (MInvoiceLine invoiceLine : invoice.getLines()) {
			i++;
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine());

			BigDecimal ventaNoSuj   = Env.ZERO;
			BigDecimal ventaExenta  = Env.ZERO;
			BigDecimal ventaGravada = Env.ZERO;
			BigDecimal noGravado    = Env.ZERO;
			BigDecimal ivaItem      = Env.ZERO;
			BigDecimal precioUnitario = invoiceLine.getPriceEntered();
			String codTributo = "";
			MTax tax = (MTax) invoiceLine.getC_Tax();

			if (invoiceLine.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_NSUJ)) {
				ventaNoSuj = invoiceLine.getLineNetAmt();
			} else if (invoiceLine.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_EXT)) {
				ventaExenta = invoiceLine.getLineNetAmt();
			} else if (invoiceLine.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_IVA)) {
				ventaGravada = invoiceLine.getLineNetAmt();
				if (invoiceLine.getTaxAmt().compareTo(Env.ZERO) == 0)
					ivaItem = tax.calculateTax(invoiceLine.getLineNetAmt(), invoice.getM_PriceList().isTaxIncluded(), 2);
			}

			String name = "";
			String description = "";
			if (invoiceLine.getC_Charge_ID() > 0)
				name = invoiceLine.getC_Charge().getName();
			else if (invoiceLine.getM_Product_ID() > 0)
				name = invoiceLine.getM_Product().getName();
			if (invoiceLine.getDescription() != null && invoiceLine.getDescription().length() > 0)
				description = name + " " + invoiceLine.getDescription();
			else
				description = name;
			if (description.length() > 999)
				description = description.substring(0, 998);

			X_C_UOM uom = (X_C_UOM) invoiceLine.getC_UOM();

			JSONObject itemJson = new JSONObject();
			itemJson.put(NUMITEM,          i);
			itemJson.put(TIPOITEM,         invoiceLineProductType(invoiceLine.getM_Product_ID()));
			itemJson.put(CODIGOGENERACION, codigoGeneracionOrig);
			itemJson.put(CANTIDAD,         invoiceLine.getQtyEntered());
			itemJson.put(PRECIOUNI,        precioUnitario);
			itemJson.put(DESCRIPCION,      description);
			itemJson.put(CODIGO,           invoiceLine.getM_Product_ID() > 0
					? invoiceLine.getProduct().getValue()
					: invoiceLine.getC_Charge().getC_ChargeType().getValue());
			itemJson.put(UNIMEDIDA,        uom_getValue(uom));
			itemJson.put(MONTODESCU,       Env.ZERO);
			itemJson.put(CODTRIBUTO,       codTributo);
			itemJson.put(VENTANOSUJ,       ventaNoSuj);
			itemJson.put(VENTAEXENTA,      ventaExenta);
			itemJson.put(VENTAGRAVADA,     ventaGravada);
			itemJson.put(COMPRA,           invoiceLine.getLineNetAmt());
			itemJson.put(PSV,              Env.ZERO);
			itemJson.put(IVAITEM,          ivaItem);
			itemJson.put(NOGRAVADO,        noGravado);
			itemJson.put(SEGURO,           Env.ZERO);
			itemJson.put(FLETE,            Env.ZERO);
			itemJson.put(IVARETE,          Env.ZERO);
			itemJson.put(RETERRENTA,       Env.ZERO);

			JSONArray jsonTributosArray = new JSONArray();
			if (ventaGravada.compareTo(Env.ZERO) != 0) {
				jsonTributosArray.put(tax_getE_Duties(tax).getValue());
			}

			itemJson.put( CreditoFiscal.TRIBUTOS, jsonTributosArray);

			jsonArray.put(itemJson);
			System.out.println("Collect JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo() + ", Line: " + invoiceLine.getLine() + " Finished");
		}

		jsonWrapper.put(CUERPODOCUMENTO, jsonArray);
		System.out.println("Retorno: end collecting JSON data for Cuerpo Documento. Document: " + invoice.getDocumentNo());
		return jsonWrapper;
	}

	private JSONObject generateResumenInputData() {
		System.out.println("Retorno: start collecting JSON data for Resumen");

		BigDecimal totalNoSuj    = Env.ZERO;
		BigDecimal totalExenta   = Env.ZERO;
		BigDecimal totalGravada  = Env.ZERO;
		BigDecimal totalNoGravado = Env.ZERO;
		BigDecimal ivaRete       = Env.ZERO;
		BigDecimal montoTributos = Env.ZERO;
		BigDecimal totalIva      = Env.ZERO;

		String totalLetras = Msg.getAmtInWords(client.getLanguage(), invoice.getGrandTotal().setScale(2).toString());

		List<MInvoiceTax> invoiceTaxes = new Query(contextProperties, MInvoiceTax.Table_Name, "C_Invoice_ID=?", trxName)
				.setParameters(invoice.getC_Invoice_ID())
				.list();

		JSONObject jsonResumen = new JSONObject();
		JSONArray jsonTributosArray = new JSONArray();

		for (MInvoiceTax invoiceTax : invoiceTaxes) {
			if (invoiceTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_RET)) {
				ivaRete = ivaRete.add(invoiceTax.getTaxAmt().multiply(new BigDecimal(-1)));
				continue;
			}
			if (invoiceTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_NSUJ)) {
				totalNoSuj = invoiceTax.getTaxBaseAmt();
			} else if (invoiceTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_EXT)) {
				totalExenta = invoiceTax.getTaxBaseAmt();
			} else if (invoiceTax.getC_Tax().getTaxIndicator().equals(TAXINDICATOR_IVA)) {
				totalGravada = invoiceTax.getTaxBaseAmt();
				totalIva     = totalIva.add(invoiceTax.getTaxAmt());
				montoTributos = montoTributos.add(invoiceTax.getTaxAmt());
				JSONObject jsonTributoItem = new JSONObject();
				jsonTributoItem.put(CODIGO,     tax_getE_Duties((MTax) invoiceTax.getC_Tax()).getValue());
				jsonTributoItem.put(DESCRIPCION, tax_getE_Duties((MTax) invoiceTax.getC_Tax()).getName());
				jsonTributoItem.put(VALOR,       invoiceTax.getTaxAmt());
				jsonTributosArray.put(jsonTributoItem);
			}
		}

		jsonResumen.put(TRIBUTOS,             jsonTributosArray);
		jsonResumen.put(TOTALNOSUJ,           totalNoSuj);
		jsonResumen.put(TOTALEXENTA,          totalExenta);
		jsonResumen.put(TOTALGRAVADA,         totalGravada);
		jsonResumen.put(TOTALCOMPRAEXCLUIDOS, Env.ZERO);
		jsonResumen.put(SUBTOTALVENTAS,       totalGravada.add(totalNoSuj).add(totalExenta));
		jsonResumen.put(TOTALSEGURO,          JSONObject.NULL);
		jsonResumen.put(TOTALFLETE,           JSONObject.NULL);
		jsonResumen.put(MONTOTOTALOPERACION,  totalGravada.add(totalNoSuj).add(totalExenta).add(montoTributos));
		jsonResumen.put(IVARETE,              ivaRete);
		jsonResumen.put(RETERRENTA,           JSONObject.NULL);
		jsonResumen.put(TOTALNOGRAVADO,       totalNoGravado);
		jsonResumen.put(TOTALPAGAR,           invoice.getGrandTotal());
		jsonResumen.put(TOTALLETRAS,          totalLetras);
		jsonResumen.put(TOTALNOONEROSAS,      Env.ZERO);
		jsonResumen.put(TOTALIVA,             totalIva);
		jsonResumen.put(SALDOFAVOR,           Env.ZERO);

		System.out.println("Retorno: end collecting JSON data for Resumen");
		return jsonResumen;
	}

	public String createJsonString() throws Exception {
		System.out.println("Retorno: start generating JSON string from Document");
		ObjectMapper objectMapper = new ObjectMapper();
		String retornoAsStringTmp = objectMapper.writeValueAsString(retorno);
		JSONObject retornoAsJson  = new JSONObject(retornoAsStringTmp);

		retornoAsJson.remove("errorMessages");

		String retornoAsStringFinal = retornoAsJson.toString()
				.replace(":[],",          ":null,")
				.replace("\"telefono\":\"\"", "\"telefono\":null")
				.replace("\"codTributo\":\"\"", "\"codTributo\":null")
				.replace("\"ventaTercero\":{\"nit\":null,\"nombre\":null,\"codDomiciliado\":null},", "\"ventaTercero\":null,")
				.replace("\"compraTercero\":{\"numDocumento\":null,\"nombre\":null},", "\"compraTercero\":null,")
				.replace("\"tributos\":[{\"descripcion\":null,\"codigo\":null,\"valor\":null}]", "\"tributos\":null")
				;

		System.out.println("Retorno: end generating JSON string from Document");
		return retornoAsStringFinal;
	}

	@Override
	public StringBuffer getEDocumentErrorMessages() {
		return retorno.errorMessages;
	}
	
	private int getInvoiceLineId(MRMALine mrmaLine)
    {
    	int invoiceLine_ID = new Query(mrmaLine.getCtx(), I_C_InvoiceLine.Table_Name, "M_InOutLine_ID=?", mrmaLine.get_TrxName())
    	.setParameters(mrmaLine.getM_InOutLine_ID())
    	.firstIdOnly();
    	return invoiceLine_ID <= 0 ? 0 : invoiceLine_ID;
    }
}

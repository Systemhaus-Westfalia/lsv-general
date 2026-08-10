package org.shw.lsv.einvoice.factory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.core.domains.models.I_M_CostDetail;
import org.adempiere.core.domains.models.X_C_UOM;
import org.adempiere.core.domains.models.X_E_Activity;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCity;
import org.compiere.model.MClient;
import org.compiere.model.MCost;
import org.compiere.model.MCostDetail;
import org.compiere.model.MCostElement;
import org.compiere.model.MDocType;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MPOS;
import org.compiere.model.MProduct;
import org.compiere.model.MTransaction;
import org.compiere.model.ProductCost;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.json.JSONArray;
import org.json.JSONObject;
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

public class NotaRemisionMovementFactory extends EDocumentFactory {

	NotaRemision notaRemision;
	MMovement movement;

	public NotaRemisionMovementFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo, MMovement movement) {
		super(trxName, contextProperties, client, orgInfo);
		this.movement = movement;
		notaRemision = new NotaRemision();
	}

	@Override
	public NotaRemision generateEDocument() {
		System.out.println("NotaRemisionMovement: start generating and filling the Document");
		String result = "";

		System.out.println("Instantiate, fill and verify Identificacion");
		IdentificacionNotaRemision identification = notaRemision.getIdentificacion();
		if (identification != null) {
			notaRemision.errorMessages.append(notaRemision.fillIdentification(jsonInputToFactory));
			result = identification.validateValues();
			if (!result.equals(EDocumentUtils.VALIDATION_RESULT_OK))
				notaRemision.errorMessages.append(result);
		}

		System.out.println("Instantiate, fill and verify Emisor");
		EmisorNotaRemision emisor = notaRemision.getEmisor();
		if (emisor != null) {
			notaRemision.fillEmisor(jsonInputToFactory);
			result = emisor.validateValues();
			if (!result.equals(EDocumentUtils.VALIDATION_RESULT_OK))
				notaRemision.errorMessages.append(result);
		}

		System.out.println("Instantiate, fill and verify Receptor");
		ReceptorNotaRemision receptor = notaRemision.getReceptor();
		if (receptor != null) {
			notaRemision.fillReceptor(jsonInputToFactory);
			result = receptor.validateValues();
			if (!result.equals(EDocumentUtils.VALIDATION_RESULT_OK))
				notaRemision.errorMessages.append(result);
		}

		System.out.println("Instantiate, fill and verify Cuerpo Documento");
		List<CuerpoDocumentoItemNotaRemision> cuerpoDocumento = notaRemision.getCuerpoDocumento();
		if (cuerpoDocumento != null) {
			notaRemision.fillCuerpoDocumento(jsonInputToFactory);
			cuerpoDocumento.stream().forEach(item -> {
				String r = item.validateValues();
				if (!r.equals(EDocumentUtils.VALIDATION_RESULT_OK))
					notaRemision.errorMessages.append(r);
			});
		}

		System.out.println("Instantiate, fill and verify Resumen");
		ResumenNotaRemision resumen = notaRemision.getResumen();
		if (resumen != null) {
			notaRemision.fillResumen(jsonInputToFactory);
			result = resumen.validateValues();
			if (!result.equals(EDocumentUtils.VALIDATION_RESULT_OK))
				notaRemision.errorMessages.append(result);
		}

		notaRemision.validateValues();

		System.out.println("NotaRemisionMovement: end generating and filling the Document");
		return notaRemision;
	}

	@Override
	public void generateJSONInputData() {
		System.out.println("NotaRemisionMovement: start collecting JSON data for all components");
		jsonInputToFactory = new JSONObject();

		jsonInputToFactory.put(NotaRemision.IDENTIFICACION,  generateIdentificationInputData());
		jsonInputToFactory.put(NotaRemision.EMISOR,          generateEmisorInputData());
		jsonInputToFactory.put(NotaRemision.RECEPTOR,        generateReceptorInputData());
		jsonInputToFactory.put(NotaRemision.CUERPODOCUMENTO, generateCuerpoDocumentoInputData());
		jsonInputToFactory.put(NotaRemision.RESUMEN,         generateResumenInputData());

		System.out.println("Generated JSON object from Movement:");
		System.out.println(jsonInputToFactory.toString());
		System.out.println("NotaRemisionMovement: end collecting JSON data for all components");
	}

	private JSONObject generateIdentificationInputData() {
		System.out.println("NotaRemisionMovement: start collecting JSON data for Identificacion");
		String numeroControl    = createNumeroControl(movement, client);
		String codigoGeneracion = createCodigoGeneracion(movement);
		JSONObject json = new JSONObject();

		boolean isContigencia = false;
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String horEmi = timeFormat.format(Calendar.getInstance().getTime());

		int daysContingencia = getContingenciaDays(movement.getAD_Client_ID());
		if (TimeUtil.getDaysBetween(movement.getMovementDate(), TimeUtil.getDay(0)) > daysContingencia)
			isContigencia = true;

		int tipoModelo    = isContigencia ? NotaRemision.TIPOMODELO_CONTIGENCIA    : NotaRemision.TIPOMODELO_NOCONTIGENCIA;
		int tipoOperacion = isContigencia ? NotaRemision.TIPOOPERACION_CONTIGENCIA : NotaRemision.TIPOOPERACION_NOCONTIGENCIA;

		json.put(NotaRemision.NUMEROCONTROL,    numeroControl);
		json.put(NotaRemision.CODIGOGENERACION, codigoGeneracion);
		json.put(NotaRemision.TIPOMODELO,       tipoModelo);
		json.put(NotaRemision.TIPOOPERACION,    tipoOperacion);
		json.put(NotaRemision.FECEMI,           movement.getMovementDate().toString().substring(0, 10));
		json.put(NotaRemision.HOREMI,           horEmi);
		json.put(NotaRemision.TIPOMONEDA,       "USD");
		json.put(NotaRemision.AMBIENTE,         client_getE_Enviroment(client).getValue());

		if (isContigencia) {
			json.put(NotaRemision.MOTIVOCONTIN,    "Contigencia por fecha de movimiento");
			json.put(NotaRemision.TIPOCONTINGENCIA, 5);
		} else {
			json.put(NotaRemision.MOTIVOCONTIN,    "");
			json.put(NotaRemision.TIPOCONTINGENCIA, "");
		}

		System.out.println("NotaRemisionMovement: end collecting JSON data for Identificacion");
		return json;
	}

	private JSONObject generateEmisorInputData() {
		System.out.println("NotaRemisionMovement: start collecting JSON data for Emisor");

		int activityID = client.get_ValueAsInt(Columnname_E_Activity_ID);
		X_E_Activity e_Activity = new X_E_Activity(org.compiere.util.Env.getCtx(), activityID, trxName);
		JSONObject json = new JSONObject();
		json.put(NotaRemision.NIT,           client.get_ValueAsString("ei_nit"));
		json.put(NotaRemision.NRC,           StringUtils.leftPad(orgInfo.getDUNS().trim().replace("-", ""), 7));
		json.put(NotaRemision.NOMBRE,        client.getDescription());
		json.put(NotaRemision.CODACTIVIDAD,  e_Activity.getValue());
		json.put(NotaRemision.DESCACTIVIDAD, e_Activity.getName());
		json.put(NotaRemision.NOMBRECOMERCIAL, client.getName());
		json.put(NotaRemision.CODESTABLE,    getCodEstable(movement));
		json.put(NotaRemision.CODPUNTOVENTA, getCodPuntoVenta(movement));

		JSONObject jsonDireccion = new JSONObject();
		jsonDireccion.put(NotaRemision.DEPARTAMENTO, city_getRegionValue((MCity) orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(NotaRemision.DISTRITO,     city_getValue((MCity) orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(NotaRemision.MUNICIPIO,    city_getMunicipioValue((MCity) orgInfo.getC_Location().getC_City()));
		jsonDireccion.put(NotaRemision.COMPLEMENTO,  orgInfo.getC_Location().getAddress1());
		json.put(NotaRemision.DIRECCION, jsonDireccion);

		json.put(NotaRemision.TELEFONO, client.get_ValueAsString("phone"));
		json.put(NotaRemision.CORREO,   client_getEmail(client));

		System.out.println("NotaRemisionMovement: end collecting JSON data for Emisor");
		return json;
	}

	private JSONObject generateReceptorInputData() {
		System.out.println("NotaRemisionMovement: start collecting JSON data for Receptor");
		JSONObject json = new JSONObject();

		// TODO: Configure how the receptor is determined for MMovement.
		// Option 1: Read a C_BPartner_ID custom column on MMovement.
		// Option 2: Use the destination warehouse org info.
		int bPartnerID = movement.get_ValueAsInt("C_BPartner_ID");
		if (bPartnerID > 0) {
			MBPartner partner = new MBPartner(contextProperties, bPartnerID, trxName);
			json.put(NotaRemision.TIPODOCUMENTO,  bPartner_getE_Recipient_Identification(partner).getValue());
			json.put(NotaRemision.NUMDOCUMENTO,   partner.getTaxID() != null ? partner.getTaxID().replace("-", "") : "");
			json.put(NotaRemision.NOMBRE,         partner.getName());
			json.put(NotaRemision.NOMBRECOMERCIAL, partner.getName());
			json.put(NotaRemision.TELEFONO,       partner.get_ValueAsString("phone"));
			json.put(NotaRemision.CORREO,         partner.get_ValueAsString("EMail"));

			if (bPartner_getE_Activity(partner).getE_Activity_ID() > 0) {
				json.put(NotaRemision.CODACTIVIDAD,  bPartner_getE_Activity(partner).getValue());
				json.put(NotaRemision.DESCACTIVIDAD, bPartner_getE_Activity(partner).getName());
			}

			JSONObject jsonDireccion = new JSONObject();
			for (MBPartnerLocation loc : MBPartnerLocation.getForBPartner(contextProperties, partner.getC_BPartner_ID(), trxName)) {
				if (loc.isBillTo() && loc.getC_Location().getC_Country_ID() == 173) {
					jsonDireccion.put(NotaRemision.DEPARTAMENTO, city_getRegionValue((MCity) loc.getC_Location().getC_City()));
					jsonDireccion.put(NotaRemision.MUNICIPIO,    city_getMunicipioValue((MCity) loc.getC_Location().getC_City()));
					jsonDireccion.put(NotaRemision.DISTRITO,     city_getValue((MCity) loc.getC_Location().getC_City()));
					jsonDireccion.put(NotaRemision.COMPLEMENTO,  (loc.getC_Location().getAddress1() + " "
							+ loc.getC_Location().getAddress2()).replace("null", ""));
					break;
				}
			}
			json.put(NotaRemision.DIRECCION, jsonDireccion);
		} else {
			// Fallback: use org info as receptor (internal transfer)
			json.put(NotaRemision.TIPODOCUMENTO,  "36");
			json.put(NotaRemision.NUMDOCUMENTO,   client.get_ValueAsString("ei_nit"));
			json.put(NotaRemision.NOMBRE,         client.getDescription());
			json.put(NotaRemision.NOMBRECOMERCIAL, client.getName());
			json.put(NotaRemision.CODACTIVIDAD,   "");
			json.put(NotaRemision.DESCACTIVIDAD,  "Traslado interno");
			json.put(NotaRemision.TELEFONO,       JSONObject.NULL);
			json.put(NotaRemision.CORREO,         JSONObject.NULL);
			json.put(NotaRemision.DIRECCION,      JSONObject.NULL);
		}

		// TODO: Set BIENTITULO according to business rules.
		// "01" = Venta, "02" = Consignación, etc.
		json.put(NotaRemision.BIENTITULO, movement.get_ValueAsString("ei_BienTitulo").isEmpty()
				? "01"
				: movement.get_ValueAsString("ei_BienTitulo"));

		System.out.println("NotaRemisionMovement: end collecting JSON data for Receptor");
		return json;
	}

	private JSONObject generateResumenInputData() {
		System.out.println("NotaRemisionMovement: start collecting JSON data for Resumen");

		// TODO: Implement cost-based financial totals for movement lines.
		// Currently all amounts are zero (valid for internal transfers with no commercial value).
		BigDecimal totalNoSuj   = Env.ZERO;
		BigDecimal totalExenta  = Env.ZERO;
		BigDecimal totalGravada = Env.ZERO;

		for (MMovementLine line : movement.getLines(true)) {
			// TODO: Replace Env.ZERO with the appropriate cost or price per line.
			BigDecimal lineAmount = getProductCost(line.getProduct()).multiply(line.getMovementQty());
			totalGravada = totalGravada.add(lineAmount);
		}

		BigDecimal subTotal = totalNoSuj.add(totalExenta).add(totalGravada);
		String totalLetras = Msg.getAmtInWords(client.getLanguage(), subTotal.setScale(2).toString());

		JSONObject json = new JSONObject();
		json.put(NotaRemision.TRIBUTOS,             JSONObject.NULL);
		json.put(NotaRemision.TOTALNOSUJ,           totalNoSuj);
		json.put(NotaRemision.TOTALEXENTA,          totalExenta);
		json.put(NotaRemision.TOTALGRAVADA,         totalGravada);
		json.put(NotaRemision.SUBTOTALVENTAS,       subTotal);
		json.put(NotaRemision.DESCUNOSUJ,           Env.ZERO);
		json.put(NotaRemision.DESCUEXENTA,          Env.ZERO);
		json.put(NotaRemision.DESCUGRAVADA,         Env.ZERO);
		json.put(NotaRemision.PORCENTAJEDESCUENTO,  Env.ZERO);
		json.put(NotaRemision.TOTALDESCU,           Env.ZERO);
		json.put(NotaRemision.SUBTOTAL,             subTotal);
		json.put(NotaRemision.MONTOTOTALOPERACION,  subTotal);
		json.put(NotaRemision.TOTALLETRAS,          totalLetras);

		String observaciones = movement.get_ValueAsString("Description");
		json.put(NotaRemision.OBSERVACIONES, observaciones != null && observaciones.length() > 0
				? observaciones
				: JSONObject.NULL);

		System.out.println("NotaRemisionMovement: end collecting JSON data for Resumen");
		return json;
	}

	private JSONArray generateCuerpoDocumentoInputData() {
		System.out.println("NotaRemisionMovement: start collecting JSON data for Cuerpo Documento");
		JSONArray jsonArray = new JSONArray();
		int i = 0;

		for (MMovementLine line : movement.getLines(true)) {
			i++;
			System.out.println("Collect JSON data for line: " + line.getLine());

			// TODO: Replace Env.ZERO with cost/price lookup for this product.
			BigDecimal ventaNoSuj   = Env.ZERO;
			BigDecimal ventaExenta  = Env.ZERO;
			BigDecimal ventaGravada = getProductCost((MProduct)line.getProduct()).multiply(line.getMovementQty());

			String description = "";
			if (line.getM_Product_ID() > 0) {
				description = line.getProduct().getName();
				if (line.getDescription() != null && line.getDescription().length() > 0)
					description = description + " " + line.getDescription();
			} else if (line.getDescription() != null) {
				description = line.getDescription();
			}
			if (description.length() > 1499)
				description = description.substring(0, 1499);

			X_C_UOM uom = new X_C_UOM(contextProperties,
					line.getM_Product_ID() > 0 ? line.getProduct().getC_UOM_ID() : 0, trxName);

			JSONObject jsonItem = new JSONObject();
			jsonItem.put(NotaRemision.NUMITEM,       i);
			jsonItem.put(NotaRemision.TIPOITEM,      invoiceLineProductType(line.getM_Product_ID()));
			jsonItem.put(NotaRemision.NUMDOCUMENTO,  JSONObject.NULL);
			jsonItem.put(NotaRemision.CODIGO,        line.getM_Product_ID() > 0
					? line.getProduct().getValue()
					: JSONObject.NULL);
			jsonItem.put(NotaRemision.CODTRIBUTO,    JSONObject.NULL);
			jsonItem.put(NotaRemision.DESCRIPCION,   description);
			jsonItem.put(NotaRemision.CANTIDAD,      line.getMovementQty());
			jsonItem.put(NotaRemision.UNIMEDIDA,     uom_getValue(uom));
			jsonItem.put(NotaRemision.PRECIOUNI,     Env.ZERO);  // TODO: set cost/price
			jsonItem.put(NotaRemision.MONTODESCU,    Env.ZERO);
			jsonItem.put(NotaRemision.VENTANOSUJ,    ventaNoSuj);
			jsonItem.put(NotaRemision.VENTAEXENTA,   ventaExenta);
			jsonItem.put(NotaRemision.VENTAGRAVADA,  ventaGravada);
			jsonItem.put(NotaRemision.TRIBUTOS,      new JSONArray());

			jsonArray.put(jsonItem);
		}

		System.out.println("NotaRemisionMovement: end collecting JSON data for Cuerpo Documento");
		return jsonArray;
	}

	@Override
	public String createJsonString() throws Exception {
		System.out.println("NotaRemisionMovement: start generating JSON string from Document");
		ObjectMapper objectMapper = new ObjectMapper();
		String notaRemisionTmp   = objectMapper.writeValueAsString(notaRemision);
		JSONObject notaRemisionJson = new JSONObject(notaRemisionTmp);

		notaRemisionJson.remove(NotaRemision.ERRORMESSAGES);

		String result = notaRemisionJson.toString()
				.replace(":[],",         ":null,")
				.replace("\"telefono\":\"\"", "\"telefono\":null")
				.replace("\"documentoRelacionado\":[]", "\"documentoRelacionado\":null")
				.replace("\"ventaTercero\":{\"nit\":null,\"nombre\":null},", "\"ventaTercero\":null,")
				.replace("\"tributos\":[{\"descripcion\":null,\"codigo\":null,\"valor\":null}]", "\"tributos\":null");

		System.out.println("NotaRemisionMovement: end generating JSON string from Document");
		return result;
	}

	@Override
	public StringBuffer getEDocumentErrorMessages() {
		return notaRemision.errorMessages;
	}

	private String createNumeroControl(MMovement movement, MClient client) {
		String prefix     = Optional.ofNullable(movement.getC_DocType().getDefiniteSequence().getPrefix()).orElse("");
		String documentno = movement.getDocumentNo().replace(prefix, "");
		String suffix     = Optional.ofNullable(movement.getC_DocType().getDefiniteSequence().getSuffix()).orElse("");
		documentno = documentno.replace(suffix, "");
		String idIdentification = StringUtils.leftPad(documentno, 15, "0");

		MPOS mpos = new org.compiere.model.Query(movement.getCtx(), MPOS.Table_Name, "AD_Org_ID=?", trxName)
				.setParameters(movement.getAD_Org_ID())
				.setOnlyActiveRecords(true)
				.setOrderBy("C_POS_ID")
				.first();
		String idPosCompany = getCodEstable(movement) + mpos.get_ValueAsString("ei_POS");
		String numeroControl = "DTE-" + docType_getE_DocType((MDocType) movement.getC_DocType()).getValue()
				+ "-" + StringUtils.leftPad(idPosCompany, 8, "0") + "-" + idIdentification;
		return numeroControl;
	}
	
	private BigDecimal getProductCost(MProduct product) {

		MAcctSchema[]  acctSchemas = MAcctSchema.getClientAcctSchema(Env.getCtx(), product.getAD_Client_ID());
		MAcctSchema acctSchema = acctSchemas[0];
		MCostElement costElement = MCostElement.getMaterialCostElement(product);
		MCost dimension = MCost.getDimension(product, acctSchema.getC_AcctSchema_ID(), 0, 0, 0, acctSchema.getM_CostType_ID(),
				costElement.getM_CostElement_ID());
		return dimension.getCurrentCostPrice();
	}
	
	
}

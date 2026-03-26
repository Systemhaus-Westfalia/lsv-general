/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) by the Free Software Foundation. This program is       *
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;  *
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A      *
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.   *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net                                                  *
 * or https://github.com/adempiere/adempiere/blob/develop/license.html        *
 *****************************************************************************/

package org.shw.lsv.einvoice.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.adempiere.core.domains.models.I_C_BPartner;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocation;
import org.compiere.model.MPriceList;
import org.compiere.model.MTax;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.shw.lsv.einvoice.utils.CuerpoDocumentoItem;
import org.shw.lsv.einvoice.utils.DteRoot;
import org.shw.lsv.einvoice.utils.TributosItem;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Process for reading and importing El Salvador Electronic Invoices (DTE - Documento Tributario Electrónico)
 *
 * <p>This process reads JSON files containing electronic invoice data, validates the data,
 * creates or updates business partners, and generates vendor invoices in the system.</p>
 *
 * <p>Key features:</p>
 * <ul>
 *   <li>Handles Spanish language JSON files with proper ISO-8859-1 encoding</li>
 *   <li>Prevents duplicate invoice imports using codigoGeneracion</li>
 *   <li>Automatically creates business partners if they don't exist</li>
 *   <li>Maps DTE tax codes to system tax definitions</li>
 *   <li>Handles special taxes (FOVIAL, COTRANS) as separate invoice lines</li>
 * </ul>
 *
 * @author ADempiere (generated)
 * @version Release 3.9.4
 */
public class ei_readJson extends ei_readJsonAbstract {

	// ==================== Constants ====================

	/** Date format used in DTE JSON files */
	private static final String DTE_DATE_FORMAT = "yyyy-MM-dd";

	/** FOVIAL tax code (Fondo de Conservación Vial) */
	private static final String TAX_CODE_FOVIAL = "C8";

	/** COTRANS tax code (Contribución al Transporte) */
	private static final String TAX_CODE_COTRANS = "D1";

	/** Tax indicator for non-taxable items */
	private static final String TAX_INDICATOR_NOT_SUBJECT = "NSUJ";

	// SQL Queries
	private static final String SQL_CHECK_EXISTING_INVOICE =
		"SELECT COUNT(*) FROM C_Invoice WHERE ei_codigoGeneracion = ? AND AD_Client_ID = ?";

	private static final String SQL_GET_REGION_ID =
		"SELECT c_Region_ID FROM c_Region WHERE value = ?";

	private static final String SQL_GET_CITY_ID =
		"SELECT c_city_ID FROM c_city WHERE value = ?";

	private static final String SQL_GET_TAX_ID =
		"SELECT c_tax_ID FROM c_tax WHERE e_Duties_ID IN " +
		"(SELECT e_Duties_ID FROM e_Duties WHERE value = ?) " +
		"AND ad_Client_ID = ? ORDER BY isdefault DESC";

	private static final String SQL_GET_CHARGE_ID =
		"SELECT C_Charge_ID FROM C_Charge WHERE AD_Client_ID = ? AND Description = ?";

	// Error Messages
	private static final String MSG_DUPLICATE_INVOICE =
		"Una factura con este código de generación ya existe";

	private static final String MSG_MISSING_FILE =
		"No se especificó archivo JSON para procesar";

	private static final String MSG_FILE_NOT_FOUND =
		"Archivo JSON no encontrado: ";

	private static final String MSG_INVALID_JSON =
		"Error al leer el archivo JSON. Verifique el formato.";

	private static final String MSG_MISSING_NSUJ_TAX =
		"Falta la definición para Impuestos No Sujetos (NSUJ)";

	private static final String MSG_MISSING_CHARGE =
		"No se encontró el cargo para el código: ";

	private static final String MSG_SUCCESS =
		"Factura importada exitosamente. Código: ";

	// ==================== Preparation ====================

	@Override
	protected void prepare() {
		super.prepare();
	}

	// ==================== Main Process ====================

	/**
	 * Executes the main process of reading and importing the electronic invoice.
	 *
	 * <p>Process flow:</p>
	 * <ol>
	 *   <li>Validates the file path</li>
	 *   <li>Reads and parses the JSON file with proper encoding</li>
	 *   <li>Checks for duplicate invoices</li>
	 *   <li>Finds or creates the business partner</li>
	 *   <li>Creates the vendor invoice</li>
	 *   <li>Creates invoice lines including special taxes</li>
	 * </ol>
	 *
	 * @return Success message with invoice code or error message
	 * @throws Exception if a critical error occurs during processing
	 */
	@Override
	protected String doIt() throws Exception {
		// Validate input
		String filePath = getFilePathOrName();
		if (filePath == null || filePath.trim().isEmpty()) {
			throw new AdempiereException(MSG_MISSING_FILE);
		}

		File jsonFile = new File(filePath);
		if (!jsonFile.exists() || !jsonFile.isFile()) {
			throw new AdempiereException(MSG_FILE_NOT_FOUND + filePath);
		}

		log.info("Processing DTE file: " + filePath);

		try {
			// Read and parse JSON file with proper encoding for Spanish content
			DteRoot dte = parseDteFile(jsonFile);

			// Validate required data
			validateDteData(dte);

			String codigoGeneracion = dte.getIdentificacion().getCodigoGeneracion();
			log.info("DTE Generation Code: " + codigoGeneracion);

			// Check for duplicate invoice
			if (isDuplicateInvoice(codigoGeneracion)) {
				log.warning("Duplicate invoice detected: " + codigoGeneracion);
				return MSG_DUPLICATE_INVOICE;
			}

			// Find or create business partner
			MBPartner partner = findOrCreateBusinessPartner(dte);
			log.info("Business Partner: " + partner.getName() + " (ID: " + partner.getC_BPartner_ID() + ")");

			// Create invoice header
			MInvoice invoice = createInvoice(dte, partner);
			log.info("Invoice created: " + invoice.getDocumentNo());

			// Create invoice lines
			createInvoiceLines(invoice, dte);
			log.info("Invoice lines created successfully");

			// Log summary information
			logInvoiceSummary(dte);

			return MSG_SUCCESS + codigoGeneracion + " - " + invoice.getDocumentNo();

		} catch (JsonParseException e) {
			log.severe("JSON parsing error: " + e.getMessage());
			throw new AdempiereException(MSG_INVALID_JSON + " " + e.getMessage());
		} catch (AdempiereException e) {
			log.severe("Business error: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			log.severe("Unexpected error processing DTE: " + e.getMessage());
			e.printStackTrace();
			throw new AdempiereException("Error al procesar factura electrónica: " + e.getMessage());
		}
	}

	// ==================== JSON Parsing ====================

	/**
	 * Parses the DTE JSON file with proper encoding support for Spanish characters.
	 *
	 * <p>This method handles both UTF-8 and ISO-8859-1 encoded files. It first attempts
	 * to read the file as UTF-8, and if that fails with an encoding error, it falls back
	 * to ISO-8859-1 (Latin-1), which is the standard encoding for El Salvador DTEs.</p>
	 *
	 * @param jsonFile The JSON file to parse
	 * @return DteRoot object containing the parsed invoice data
	 * @throws Exception if the file cannot be read or parsed
	 */
	private DteRoot parseDteFile(File jsonFile) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		// Try UTF-8 first (modern standard)
		try (InputStreamReader reader = new InputStreamReader(
				new FileInputStream(jsonFile),
				StandardCharsets.UTF_8)) {
			return mapper.readValue(reader, DteRoot.class);
		} catch (JsonParseException e) {
			// Fallback to ISO-8859-1 for Spanish/Latin American documents
			if (e.getMessage() != null && e.getMessage().contains("Invalid UTF-8")) {
				log.info("UTF-8 decoding failed, trying ISO-8859-1 encoding");
				try (InputStreamReader reader = new InputStreamReader(
						new FileInputStream(jsonFile),
						StandardCharsets.ISO_8859_1)) {
					return mapper.readValue(reader, DteRoot.class);
				}
			}
			throw e;
		}
	}

	// ==================== Validation ====================

	/**
	 * Validates that the DTE contains all required data.
	 *
	 * @param dte The DTE root object to validate
	 * @throws AdempiereException if required data is missing
	 */
	private void validateDteData(DteRoot dte) {
		if (dte == null) {
			throw new AdempiereException("DTE data is null");
		}
		if (dte.getIdentificacion() == null) {
			throw new AdempiereException("Missing DTE identification section");
		}
		if (dte.getIdentificacion().getCodigoGeneracion() == null) {
			throw new AdempiereException("Missing generation code");
		}
		if (dte.getEmisor() == null) {
			throw new AdempiereException("Missing emisor (issuer) data");
		}
		if (dte.getCuerpoDocumento() == null || dte.getCuerpoDocumento().isEmpty()) {
			throw new AdempiereException("Missing document items");
		}
		if (dte.getResumen() == null) {
			throw new AdempiereException("Missing document summary");
		}
	}

	/**
	 * Checks if an invoice with the given generation code already exists.
	 *
	 * @param codigoGeneracion The unique generation code from the DTE
	 * @return true if a duplicate exists, false otherwise
	 */
	private boolean isDuplicateInvoice(String codigoGeneracion) {
		int count = DB.getSQLValueEx(
			get_TrxName(),
			SQL_CHECK_EXISTING_INVOICE,
			codigoGeneracion,
			Env.getAD_Client_ID(getCtx())
		);
		return count > 0;
	}

	// ==================== Business Partner Management ====================

	/**
	 * Finds an existing business partner or creates a new one based on DTE data.
	 *
	 * <p>Search priority:</p>
	 * <ol>
	 *   <li>Use provided BPartner ID parameter if specified</li>
	 *   <li>Search by NIT (Tax ID)</li>
	 *   <li>Search by NRC (Registry Number)</li>
	 *   <li>Create new business partner if not found</li>
	 * </ol>
	 *
	 * @param dte The DTE containing emisor information
	 * @return The found or newly created business partner
	 */
	private MBPartner findOrCreateBusinessPartner(DteRoot dte) {
		MBPartner partner = null;

		// Check if partner ID was provided as parameter
		if (getBPartnerId() > 0) {
			partner = new MBPartner(getCtx(), getBPartnerId(), get_TrxName());
			if (partner.getC_BPartner_ID() > 0) {
				log.info("Using provided Business Partner ID: " + getBPartnerId());
				return partner;
			}
		}

		// Search by NIT
		String nit = dte.getEmisor().getNit();
		if (nit != null && !nit.isEmpty()) {
			partner = findPartnerByNIT(nit);
			if (partner != null) {
				log.info("Found Business Partner by NIT: " + nit);
				return partner;
			}
		}

		// Search by NRC
		String nrc = dte.getEmisor().getNrc();
		if (nrc != null && !nrc.isEmpty()) {
			partner = findPartnerByNRC(nrc);
			if (partner != null) {
				log.info("Found Business Partner by NRC: " + nrc);
				return partner;
			}
		}

		// Create new business partner
		log.info("Creating new Business Partner");
		return createBpartner(dte);
	}

	/**
	 * Finds a business partner by NIT (Tax ID).
	 *
	 * @param nit The tax ID to search for (hyphens are ignored)
	 * @return The business partner if found, null otherwise
	 */
	private MBPartner findPartnerByNIT(String nit) {
		if (nit == null || nit.trim().isEmpty()) {
			return null;
		}

		final String whereClause = "TRIM(REPLACE(taxID, '-', '')) = ? AND AD_Client_ID = ?";
		return new Query(getCtx(), I_C_BPartner.Table_Name, whereClause, get_TrxName())
			.setParameters(nit.replace("-", "").trim(), Env.getAD_Client_ID(getCtx()))
			.setOnlyActiveRecords(true)
			.firstOnly();
	}

	/**
	 * Finds a business partner by NRC (Registry Number).
	 *
	 * @param nrc The registry number to search for (hyphens are ignored)
	 * @return The business partner if found, null otherwise
	 */
	private MBPartner findPartnerByNRC(String nrc) {
		if (nrc == null || nrc.trim().isEmpty()) {
			return null;
		}

		final String whereClause = "TRIM(REPLACE(duns, '-', '')) = ? AND AD_Client_ID = ?";
		return new Query(getCtx(), I_C_BPartner.Table_Name, whereClause, get_TrxName())
			.setParameters(nrc.replace("-", "").trim(), Env.getAD_Client_ID(getCtx()))
			.setOnlyActiveRecords(true)
			.firstOnly();
	}

	/**
	 * Creates a new business partner from DTE emisor data.
	 *
	 * @param dteRoot The DTE containing emisor information
	 * @return The newly created business partner
	 */
	private MBPartner createBpartner(DteRoot dteRoot) {
		MBPartner partner = new MBPartner(getCtx(), 0, get_TrxName());

		// Set basic information
		partner.setIsVendor(true);
		partner.setTaxID(dteRoot.getEmisor().getNit());
		partner.setDUNS(dteRoot.getEmisor().getNrc());
		partner.setName(dteRoot.getEmisor().getNombre());
		partner.setValue(dteRoot.getEmisor().getNit());

		// Set default business partner group
		MBPGroup defaultGroup = MBPGroup.getDefault(getCtx());
		if (defaultGroup != null) {
			partner.setC_BP_Group_ID(defaultGroup.get_ID());
		}

		partner.saveEx();

		// Create location
		createPartnerLocation(partner, dteRoot);

		log.info("Created new Business Partner: " + partner.getName());
		return partner;
	}

	/**
	 * Creates a business partner location from DTE address data.
	 *
	 * @param partner The business partner to create the location for
	 * @param dteRoot The DTE containing address information
	 */
	private void createPartnerLocation(MBPartner partner, DteRoot dteRoot) {
		MLocation location = new MLocation(getCtx(), 0, get_TrxName());

		// Set location details
		int cityID = getCityID(dteRoot);
		if (cityID > 0) {
			location.setC_City_ID(cityID);
		}

		int regionID = getRegionID(dteRoot);
		if (regionID > 0) {
			location.setC_Region_ID(regionID);
		}

		if (dteRoot.getEmisor().getDireccion() != null) {
			String address = dteRoot.getEmisor().getDireccion().getComplemento();
			if (address != null && !address.isEmpty()) {
				location.setAddress1(address);
			}
		}

		location.saveEx();

		// Create partner location
		MBPartnerLocation partnerLocation = new MBPartnerLocation(partner);
		partnerLocation.setC_Location_ID(location.getC_Location_ID());
		partnerLocation.saveEx();
	}

	// ==================== Invoice Creation ====================

	/**
	 * Creates a vendor invoice from DTE data.
	 *
	 * @param dteRoot The DTE containing invoice information
	 * @param partner The business partner (vendor)
	 * @return The created invoice
	 * @throws ParseException if date parsing fails
	 */
	private MInvoice createInvoice(DteRoot dteRoot, MBPartner partner) throws ParseException {
		log.info("Creating invoice for partner: " + partner.getName());

		MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());

		// Set document type
		setInvoiceDocType(invoice);

		// Set vendor information
		invoice.setIsSOTrx(false);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());

		// Set partner location
		setInvoicePartnerLocation(invoice, partner);

		// Set dates
		Timestamp invoiceDate = parseDate(dteRoot.getIdentificacion().getFecEmi());
		invoice.setDateAcct(invoiceDate);
		invoice.setDateInvoiced(invoiceDate);

		// Set organization
		invoice.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));

		// Set status
		invoice.setDocStatus(MInvoice.STATUS_Drafted);
		invoice.setDocAction(MInvoice.DOCACTION_Complete);

		// Set price list and currency
		setPriceListAndCurrency(invoice);

		// Set DTE specific fields
		invoice.set_ValueOfColumn("ei_codigoGeneracion", dteRoot.getIdentificacion().getCodigoGeneracion());
		invoice.set_ValueOfColumn("ei_numeroControl", dteRoot.getIdentificacion().getNumeroControl());
		invoice.set_ValueOfColumn("ei_selloRecibido", dteRoot.getSelloRecibido());

		invoice.saveEx();

		return invoice;
	}

	/**
	 * Sets the appropriate document type for the invoice.
	 *
	 * @param invoice The invoice to set the document type for
	 */
	private void setInvoiceDocType(MInvoice invoice) {
		Optional<MDocType> doctypeOptional = Arrays.stream(
				MDocType.getOfDocBaseType(getCtx(), MDocType.DOCBASETYPE_APInvoice))
			.sorted((docType1, docType2) -> Boolean.compare(docType2.isDefault(), docType1.isDefault()))
			.findFirst();

		doctypeOptional.ifPresent(docType -> invoice.setC_DocTypeTarget_ID(docType.getC_DocType_ID()));

		if (invoice.getC_DocTypeTarget_ID() <= 0) {
			throw new AdempiereException("@C_DocType_ID@ @FillMandatory@");
		}
	}

	/**
	 * Sets the business partner location for the invoice.
	 *
	 * @param invoice The invoice to set the location for
	 * @param partner The business partner
	 */
	private void setInvoicePartnerLocation(MInvoice invoice, MBPartner partner) {
		MBPartnerLocation[] locations = MBPartnerLocation.getForBPartner(
			getCtx(),
			partner.getC_BPartner_ID(),
			null,
			get_TrxName()
		);

		if (locations != null && locations.length > 0) {
			invoice.setC_BPartner_Location_ID(locations[0].getC_BPartner_Location_ID());
		}
	}

	/**
	 * Sets the price list and currency for the invoice.
	 *
	 * @param invoice The invoice to set the price list for
	 */
	private void setPriceListAndCurrency(MInvoice invoice) {
		MPriceList priceList = MPriceList.getDefault(getCtx(), false);
		if (priceList != null) {
			invoice.setM_PriceList_ID(priceList.get_ID());
			invoice.setC_Currency_ID(priceList.getC_Currency_ID());
		}
	}

	// ==================== Invoice Lines Creation ====================

	/**
	 * Creates all invoice lines including regular items and special taxes.
	 *
	 * @param invoice The invoice to create lines for
	 * @param dteRoot The DTE containing line items and taxes
	 */
	private void createInvoiceLines(MInvoice invoice, DteRoot dteRoot) {
		// Create regular invoice lines
		List<CuerpoDocumentoItem> items = dteRoot.getCuerpoDocumento();
		if (items != null && !items.isEmpty()) {
			items.forEach(item -> createInvoiceLine(invoice, item));
		}

		// Create special tax lines (FOVIAL, COTRANS)
		createSpecialTaxLines(invoice, dteRoot);
	}

	/**
	 * Creates a single invoice line from a DTE item.
	 *
	 * @param invoice The invoice to add the line to
	 * @param item The DTE item containing line details
	 */
	private void createInvoiceLine(MInvoice invoice, CuerpoDocumentoItem item) {
		MInvoiceLine invoiceLine = new MInvoiceLine(invoice);

		// Set quantity and pricing
		invoiceLine.setQty(BigDecimal.valueOf(item.getCantidad()));
		invoiceLine.setPrice(BigDecimal.valueOf(item.getPrecioUni()));

		// Set description
		if (item.getDescripcion() != null && !item.getDescripcion().isEmpty()) {
			invoiceLine.setDescription(item.getDescripcion());
		}

		// Set charge if provided
		if (getChargeId() > 0) {
			invoiceLine.setC_Charge_ID(getChargeId());
		}

		// Set tax
		setInvoiceLineTax(invoiceLine, item);

		invoiceLine.saveEx();
	}

	/**
	 * Sets the appropriate tax for an invoice line.
	 * Excludes special taxes (FOVIAL/COTRANS) which are handled separately.
	 *
	 * @param invoiceLine The invoice line to set the tax for
	 * @param item The DTE item containing tax codes
	 */
	private void setInvoiceLineTax(MInvoiceLine invoiceLine, CuerpoDocumentoItem item) {
		List<String> tributos = item.getTributos();
		if (tributos != null && !tributos.isEmpty()) {
			for (String tributo : tributos) {
				// Skip special taxes (handled separately)
				if (!TAX_CODE_COTRANS.equals(tributo) && !TAX_CODE_FOVIAL.equals(tributo)) {
					int taxID = getTaxID(tributo);
					if (taxID > 0) {
						invoiceLine.setC_Tax_ID(taxID);
						break; // Use first valid tax
					}
				}
			}
		}
	}

	/**
	 * Creates special invoice lines for FOVIAL and COTRANS taxes.
	 * These taxes are not applied to items but added as separate lines.
	 *
	 * @param invoice The invoice to add the lines to
	 * @param dteRoot The DTE containing tax information
	 */
	private void createSpecialTaxLines(MInvoice invoice, DteRoot dteRoot) {
		List<TributosItem> tributosItems = dteRoot.getResumen().getTributos();
		if (tributosItems == null || tributosItems.isEmpty()) {
			return;
		}

		// Get the NSUJ (Not Subject) tax ID
		final int nsujTaxID = getNonSubjectTaxID();
		if (nsujTaxID <= 0) {
			throw new AdempiereException(MSG_MISSING_NSUJ_TAX);
		}

		// Process FOVIAL (C8) and COTRANS (D1) taxes
		tributosItems.stream()
			.filter(item -> TAX_CODE_FOVIAL.equals(item.getCodigo()) ||
			                TAX_CODE_COTRANS.equals(item.getCodigo()))
			.forEach(tributoItem -> createSpecialTaxLine(invoice, tributoItem, nsujTaxID));
	}

	/**
	 * Creates a single special tax line (FOVIAL or COTRANS).
	 *
	 * @param invoice The invoice to add the line to
	 * @param tributoItem The tax item from the DTE
	 * @param nsujTaxID The ID of the non-subject tax
	 */
	private void createSpecialTaxLine(MInvoice invoice, TributosItem tributoItem, int nsujTaxID) {
		MInvoiceLine invoiceLine = new MInvoiceLine(invoice);

		invoiceLine.setQty(Env.ONE);
		invoiceLine.setPrice(tributoItem.getValor());
		invoiceLine.setDescription(tributoItem.getDescripcion());
		invoiceLine.setC_Tax_ID(nsujTaxID);

		// Get the charge ID for this tax type
		int chargeID = getChargeIDForTaxCode(invoice.getAD_Client_ID(), tributoItem.getCodigo());
		if (chargeID <= 0) {
			throw new AdempiereException(MSG_MISSING_CHARGE + tributoItem.getCodigo());
		}

		invoiceLine.setC_Charge_ID(chargeID);
		invoiceLine.saveEx();
	}

	// ==================== Lookup Methods ====================

	/**
	 * Gets the region ID from the DTE address data.
	 *
	 * @param dteRoot The DTE containing address information
	 * @return The region ID, or -1 if not found
	 */
	private int getRegionID(DteRoot dteRoot) {
		if (dteRoot.getEmisor() == null ||
		    dteRoot.getEmisor().getDireccion() == null ||
		    dteRoot.getEmisor().getDireccion().getDepartamento() == null) {
			return -1;
		}

		String departamento = dteRoot.getEmisor().getDireccion().getDepartamento();
		return DB.getSQLValueEx(get_TrxName(), SQL_GET_REGION_ID, departamento);
	}

	/**
	 * Gets the city ID from the DTE address data.
	 *
	 * @param dteRoot The DTE containing address information
	 * @return The city ID, or -1 if not found
	 */
	private int getCityID(DteRoot dteRoot) {
		if (dteRoot.getEmisor() == null ||
		    dteRoot.getEmisor().getDireccion() == null ||
		    dteRoot.getEmisor().getDireccion().getMunicipio() == null) {
			return -1;
		}

		String municipio = dteRoot.getEmisor().getDireccion().getMunicipio();
		return DB.getSQLValueEx(get_TrxName(), SQL_GET_CITY_ID, municipio);
	}

	/**
	 * Gets the tax ID for a given DTE tax code.
	 *
	 * @param taxCode The DTE tax code (e.g., "20" for IVA)
	 * @return The system tax ID, or -1 if not found
	 */
	private int getTaxID(String taxCode) {
		if (taxCode == null || taxCode.trim().isEmpty()) {
			return -1;
		}

		return DB.getSQLValueEx(
			get_TrxName(),
			SQL_GET_TAX_ID,
			taxCode,
			Env.getAD_Client_ID(getCtx())
		);
	}

	/**
	 * Gets the tax ID for non-subject (NSUJ) taxes.
	 * Used for FOVIAL and COTRANS.
	 *
	 * @return The NSUJ tax ID, or 0 if not found
	 */
	private int getNonSubjectTaxID() {
		return Arrays.stream(MTax.getAll(Env.getCtx()))
			.filter(tax -> tax != null && TAX_INDICATOR_NOT_SUBJECT.equals(tax.getTaxIndicator()))
			.findFirst()
			.map(MTax::getC_Tax_ID)
			.orElse(0);
	}

	/**
	 * Gets the charge ID for a special tax code (FOVIAL or COTRANS).
	 *
	 * @param clientID The client ID
	 * @param taxCode The tax code (C8 or D1)
	 * @return The charge ID, or -1 if not found
	 */
	private int getChargeIDForTaxCode(int clientID, String taxCode) {
		return DB.getSQLValueEx(
			get_TrxName(),
			SQL_GET_CHARGE_ID,
			clientID,
			taxCode
		);
	}

	// ==================== Utility Methods ====================

	/**
	 * Parses a date string into a Timestamp.
	 *
	 * @param dateString The date string in DTE format (yyyy-MM-dd)
	 * @return Timestamp object
	 * @throws ParseException if the date cannot be parsed
	 */
	private Timestamp parseDate(String dateString) throws ParseException {
		if (dateString == null || dateString.trim().isEmpty()) {
			throw new ParseException("Date string is null or empty", 0);
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(DTE_DATE_FORMAT);
		dateFormat.setLenient(false);
		Date date = dateFormat.parse(dateString);
		return new Timestamp(date.getTime());
	}

	/**
	 * Logs a summary of the imported invoice for debugging.
	 *
	 * @param dte The DTE data
	 */
	private void logInvoiceSummary(DteRoot dte) {
		if (dte == null) return;

		StringBuilder summary = new StringBuilder("\n=== DTE Import Summary ===\n");
		summary.append("Generation Code: ").append(dte.getIdentificacion().getCodigoGeneracion()).append("\n");
		summary.append("Control Number: ").append(dte.getIdentificacion().getNumeroControl()).append("\n");
		summary.append("Date: ").append(dte.getIdentificacion().getFecEmi()).append("\n");

		if (dte.getCuerpoDocumento() != null && !dte.getCuerpoDocumento().isEmpty()) {
			summary.append("Items: ").append(dte.getCuerpoDocumento().size()).append("\n");
			summary.append("First Item: ").append(dte.getCuerpoDocumento().get(0).getDescripcion()).append("\n");
		}

		if (dte.getResumen() != null) {
			summary.append("Total Gravada: ").append(dte.getResumen().getTotalGravada()).append("\n");
			summary.append("Total to Pay: ").append(dte.getResumen().getSubTotalVentas()).append("\n");
		}

		summary.append("==========================");

		log.info(summary.toString());
	}
}
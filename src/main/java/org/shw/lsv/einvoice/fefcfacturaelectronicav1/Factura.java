/**
 * 
 */
package org.shw.lsv.einvoice.fefcfacturaelectronicav1;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.utils.EDocument;
import org.shw.lsv.einvoice.utils.EDocumentUtils;
import org.shw.lsv.einvoice.utils.PagosItem;

/**
 * 
 */
public class Factura extends EDocument {
	static final int MINIMUM_TOTOTAL_OF_OPERATION = 1095;
	static final int CUERPODOCUMENTO_MAXIMUM_ITEMS = 2000;

	static final String VALIDATION_RESUMEN_OR_MONTO_IS_NULL   = "Documento: Factura, clase: FacturaElectronica. Validacion falló: valor de 'getResumen() y getMontoTotalOperacion()' no debe ser ='null'";
	static final String VALIDATION_TIPODOCUMENTO_IS_NULL      = "Documento: Factura, clase: FacturaElectronica. Validacion falló: valor de 'tipoDocumento' no debe ser ='null'";
	static final String VALIDATION_NUMDOCUMENTO_IS_NULL       = "Documento: Factura, clase: FacturaElectronica. Validacion falló: valor de 'numDocumento' no debe ser ='null'";
	static final String VALIDATION_NOMBRE_IS_NULL             = "Documento: Factura, clase: FacturaElectronica. Validacion falló: valor de 'NOMBRE' no debe ser ='null'";
	static final String VALIDATION_CUERPODOCUMENTO_MAX_ITEMS  = "Documento: Factura, clase: FacturaElectronica. Validacion falló: valor de 'cuerpoDocumento' debe  contener m??ximo " + CUERPODOCUMENTO_MAXIMUM_ITEMS + " elementos";

	static final String ERROR_FACTURA_IDENTIFICACION          = "Documento: Factura, error en fillIdentification(): ";
	static final String ERROR_FACTURA_EMISOR          		  = "Documento: Factura, error en fillEmisor(): ";
	static final String ERROR_FACTURA_RECEPTOR                = "Documento: Factura, error en fillReceptor(): ";
	static final String ERROR_FACTURA_CUERPO_DOCUMENTO        = "Documento: Factura, error en fillCuerpoDocumento(): ";
	static final String ERROR_FACTURA_RESUMEN				  = "Documento: Factura, error en fillResumen(): ";
	static final String ERROR_FACTURA_APENDICE            	  = "Documento: Factural, error en fillApendice(): ";
	static final String ERROR_FACTURA_EXTENSION               = "Documento: Factura, error en fillExtension(): ";
	
	IdentificacionFactura identificacion;
	EmisorFactura emisor;
	ReceptorFactura receptor;
	List<CuerpoDocumentoItemFactura> cuerpoDocumento;
	ResumenFactura resumen;

	ExtensionFactura extension = null;
	List<ApendiceItemFactura> apendice=null;  // null allowed
	List<DocumentoRelacionadoItemFactura> documentoRelacionado = null;
	List<OtrosDocumentosItemFactura> otrosDocumentos = null;
	VentaTerceroFactura ventaTercero = null;

	/**
	 * No parameters
	 */
	public Factura() {
		this.identificacion       	= new IdentificacionFactura();
		this.emisor               	= new EmisorFactura();
		this.receptor             	= new ReceptorFactura();
	    this.cuerpoDocumento      	= new ArrayList<CuerpoDocumentoItemFactura>();
		this.resumen              	= new ResumenFactura();
		this.extension				= new ExtensionFactura();
		
		//leer
		this.apendice				= new ArrayList<ApendiceItemFactura>();
		this.documentoRelacionado	= new ArrayList<DocumentoRelacionadoItemFactura>();
		this.ventaTercero			= new VentaTerceroFactura();
		this.otrosDocumentos		= new ArrayList<OtrosDocumentosItemFactura>();
	}

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		System.out.println("Factura: start validating values");
		if( (getResumen()==null) || (getResumen().getMontoTotalOperacion()==null) ) {
			return VALIDATION_RESUMEN_OR_MONTO_IS_NULL;
		}
		
		if(getResumen().getMontoTotalOperacion().compareTo(BigDecimal.valueOf(MINIMUM_TOTOTAL_OF_OPERATION))==1) {
			if ( getReceptor().getTipoDocumento()== null)
				return VALIDATION_TIPODOCUMENTO_IS_NULL;
			if ( getReceptor().getNumDocumento()== null)
				return VALIDATION_NUMDOCUMENTO_IS_NULL;
			if ( getReceptor().getNombre()== null)
				return VALIDATION_NOMBRE_IS_NULL;
		} 
		
		if( (getCuerpoDocumento()==null) ||  (getCuerpoDocumento().size()==0)  ||  (getCuerpoDocumento().size()>CUERPODOCUMENTO_MAXIMUM_ITEMS) ) {
			return VALIDATION_CUERPODOCUMENTO_MAX_ITEMS;
		}
		System.out.println("Factura: start validating values");
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}


	/**
	 * @return the identificacion
	 */
	public IdentificacionFactura getIdentificacion() {
		return identificacion;
	}


	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(IdentificacionFactura identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * @param factoryInput the input data as JSON object
	 */
	public StringBuffer fillIdentification(JSONObject factoryInput) {
		System.out.println("Start Factura.fillIdentificacion()"); 

		JSONObject identificationJson = factoryInput.getJSONObject(IDENTIFICACION);
		try {identificacion.setNumeroControl(identificationJson.getString(NUMEROCONTROL));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_IDENTIFICACION + e);}
		try {identificacion.setCodigoGeneracion(identificationJson.getString(CODIGOGENERACION));} 	catch (Exception e) {errorMessages.append(ERROR_FACTURA_IDENTIFICACION + e);}
		try {identificacion.setTipoModelo(identificationJson.getInt(TIPOMODELO));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_IDENTIFICACION + e);}
		try {identificacion.setTipoOperacion(identificationJson.getInt(TIPOOPERACION));} 			catch (Exception e) {errorMessages.append(ERROR_FACTURA_IDENTIFICACION + e);}
		try {identificacion.setFecEmi(identificationJson.getString(FECEMI));} 						catch (Exception e) {errorMessages.append(ERROR_FACTURA_IDENTIFICACION + e);}
		try {identificacion.setHorEmi(identificationJson.getString(HOREMI));} 						catch (Exception e) {errorMessages.append(ERROR_FACTURA_IDENTIFICACION + e);}
		try {identificacion.setTipoMoneda(identificationJson.getString(TIPOMONEDA));} 				catch (Exception e) {errorMessages.append(ERROR_FACTURA_IDENTIFICACION + e);}
		try {identificacion.setAmbiente(identificationJson.getString(AMBIENTE));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_IDENTIFICACION + e);}
		
		if (identificationJson.getInt(TIPOMODELO) == TIPOMODELO_CONTIGENCIA) {
			try{identificacion.setMotivoContin(identificationJson.getString(MOTIVOCONTIN));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_IDENTIFICACION + e);}
			try{identificacion.setTipoContingencia(identificationJson.getInt(TIPOCONTINGENCIA));} 	catch (Exception e) {errorMessages.append(ERROR_FACTURA_IDENTIFICACION + e);}
		}

		System.out.println("End Factura.fillIdentificacion()");
		return errorMessages;
	}

	/**
	 * @return the emisor
	 */
	public EmisorFactura getEmisor() {
		return emisor;
	}


	/**
	 * @param emisor the emisor to set
	 */
	public void setEmisor(EmisorFactura emisor) {
		this.emisor = emisor;
	}
	
	public StringBuffer fillEmisor(JSONObject factoryInput) {
		System.out.println("Start Factura.fillEmisor()"); 

		JSONObject emisorJson = factoryInput.getJSONObject(EMISOR);
		try {emisor.setNit(emisorJson.getString(NIT));} 									catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}
		try {emisor.setNrc(emisorJson.getString(NRC));} 									catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}
		try {emisor.setNombre(emisorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}
		try {emisor.setCodActividad(emisorJson.getString(CODACTIVIDAD));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}
		try {emisor.setDescActividad(emisorJson.getString(DESCACTIVIDAD));} 				catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}
		try {emisor.setNombreComercial(emisorJson.getString(NOMBRECOMERCIAL));} 			catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}		
		try {emisor.setTipoEstablecimiento(emisorJson.getString(TIPOESTABLECIMIENTO));}		catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}	

		JSONObject jsonDireccion = emisorJson.getJSONObject(DIRECCION);
		try {emisor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}
		try {emisor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}
		try {emisor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}

		try {emisor.setTelefono(emisorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}
		try {emisor.setCorreo(emisorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_FACTURA_EMISOR + e);}

		System.out.println("End Factura.fillEmisor()");
		return errorMessages;
	}


	/**
	 * @return the receptor
	 */
	public ReceptorFactura getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor the receptor to set
	 */
	public void setReceptor(ReceptorFactura receptor) {
		this.receptor = receptor;
	}

	public StringBuffer fillReceptor(JSONObject factoryInput) {
		System.out.println("Start Factura.fillReceptor()"); 

		JSONObject receptorJson = factoryInput.getJSONObject(RECEPTOR);
		try {receptor.setTipoDocumento(receptorJson.getString(TIPODOCUMENTO));} 				catch (Exception e) {errorMessages.append(ERROR_FACTURA_RECEPTOR + e);}
		try {receptor.setNumDocumento(receptorJson.getString(NUMDOCUMENTO));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_RECEPTOR + e);}
		try {receptor.setNombre(receptorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_FACTURA_RECEPTOR + e);}
		try {
			if (receptorJson.getString(CODACTIVIDAD).equals("")) {

				receptor.setCodActividad(null);
				receptor.setDescActividad(null);
			}
			else {
				receptor.setCodActividad(receptorJson.getString(CODACTIVIDAD)); 					
				receptor.setDescActividad(receptorJson.getString(DESCACTIVIDAD));
			}
		}
		catch (Exception e) {errorMessages.append(ERROR_FACTURA_RECEPTOR + e);}
		JSONObject jsonDireccion = receptorJson.getJSONObject(DIRECCION);
		if (jsonDireccion.length()>0) {

			try {
				if (jsonDireccion.getString(DEPARTAMENTO).length()>0)
					receptor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));
				}																				catch (Exception e) {errorMessages.append(ERROR_FACTURA_RECEPTOR + e);}
			try {if (jsonDireccion.getString(MUNICIPIO).length()>0)
				receptor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_RECEPTOR + e);}
			try {receptor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} catch (Exception e) {errorMessages.append(ERROR_FACTURA_RECEPTOR + e);}
		}
		try {if (receptorJson.getString(TELEFONO).equals(""))
			receptor.setTelefono(null);
		else
			receptor.setTelefono(receptorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_FACTURA_RECEPTOR + e);}
		try {receptor.setCorreo(receptorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_FACTURA_RECEPTOR + e);}

		System.out.println("End Factura.fillReceptor()"); 
		return errorMessages;
	}

	/**
	 * @return the cuerpoDocumento
	 */
	public List<CuerpoDocumentoItemFactura> getCuerpoDocumento() {
		return cuerpoDocumento;
	}


	/**
	 * @param cuerpoDocumento the cuerpoDocumento to set
	 */
	public void setCuerpoDocumento(List<CuerpoDocumentoItemFactura> cuerpoDocumento) {
		this.cuerpoDocumento = cuerpoDocumento;
	}


	public StringBuffer fillCuerpoDocumento(JSONObject factoryInput) {
		System.out.println("Start Factura.fillCuerpoDocumento()"); 

		JSONObject cuerpoDocumentoItemsJson = factoryInput.getJSONObject(CUERPODOCUMENTO);
		JSONArray cuerpoDocumentoArrayJson = cuerpoDocumentoItemsJson.getJSONArray(CUERPODOCUMENTO);
	
		for (int i=0; i< cuerpoDocumentoArrayJson.length(); i++) { 
			JSONObject cuerpoDocumentoItemJson = cuerpoDocumentoArrayJson.getJSONObject(i);
			CuerpoDocumentoItemFactura cuerpoDocumentoItemFactura = new CuerpoDocumentoItemFactura();
			try {cuerpoDocumentoItemFactura.setNumItem(cuerpoDocumentoItemJson.getInt(NUMITEM));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setTipoItem(cuerpoDocumentoItemJson.getInt(TIPOITEM));} 				catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			//try {cuerpoDocumentoItemFactura.setNumeroDocumento(cuerpoDocumentoItemJson.getString(NUMERODOCUMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setCantidad(cuerpoDocumentoItemJson.getBigDecimal(CANTIDAD));} 			catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setCodigo(cuerpoDocumentoItemJson.getString(CODIGO));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setCodTributo(null);} 													catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setUniMedida(cuerpoDocumentoItemJson.getInt(UNIMEDIDA));} 				catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setDescripcion(cuerpoDocumentoItemJson.getString(DESCRIPCION));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setPrecioUni(cuerpoDocumentoItemJson.getBigDecimal(PRECIOUNI));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setMontoDescu(cuerpoDocumentoItemJson.getBigDecimal(MONTODESCU));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setVentaNoSuj(cuerpoDocumentoItemJson.getBigDecimal(VENTANOSUJ));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setVentaExenta(cuerpoDocumentoItemJson.getBigDecimal(VENTAEXENTA));} 	catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setVentaGravada(cuerpoDocumentoItemJson.getBigDecimal(VENTAGRAVADA));} 	catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setTributos(null);} 													catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setPsv(cuerpoDocumentoItemJson.getBigDecimal(PSV));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setNoGravado(cuerpoDocumentoItemJson.getBigDecimal(NOGRAVADO));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFactura.setIvaItem(cuerpoDocumentoItemJson.getBigDecimal(IVAITEM));} 			catch (Exception e) {errorMessages.append(ERROR_FACTURA_CUERPO_DOCUMENTO + e);}

			cuerpoDocumento.add(cuerpoDocumentoItemFactura);						
		}

		System.out.println("End Factura.fillCuerpoDocumento()"); 
		return errorMessages;
	}

	/**
	 * @return the resumen
	 */
	public ResumenFactura getResumen() {
		return resumen;
	}


	/**
	 * @param resumen the resumen to set
	 */
	public void setResumen(ResumenFactura resumen) {
		this.resumen = resumen;
	}

	public StringBuffer fillResumen(JSONObject factoryInput) {
		System.out.println("Start Factura.fillResumen()"); 
		JSONObject resumenJson = factoryInput.getJSONObject(RESUMEN);		

		try {resumen.setTotalNoSuj(resumenJson.getBigDecimal(TOTALNOSUJ));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setTotalExenta(resumenJson.getBigDecimal(TOTALEXENTA));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setTotalGravada(resumenJson.getBigDecimal(TOTALGRAVADA));} 				catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setSubTotalVentas(resumenJson.getBigDecimal(SUBTOTALVENTAS));} 			catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setDescuNoSuj(resumenJson.getBigDecimal(DESCUNOSUJ));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setDescuExenta(resumenJson.getBigDecimal(DESCUEXENTA));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setDescuGravada(resumenJson.getBigDecimal(DESCUGRAVADA));} 				catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setPorcentajeDescuento(resumenJson.getBigDecimal(PORCENTAJEDESCUENTO));} 	catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setSubTotal(resumenJson.getBigDecimal(SUBTOTAL));} 						catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setIvaRete1(resumenJson.getBigDecimal(IVARETE1));} 						catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setMontoTotalOperacion(resumenJson.getBigDecimal(MONTOTOTALOPERACION));} 	catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setTotalNoGravado(resumenJson.getBigDecimal(TOTALNOGRAVADO));} 			catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setTotalPagar(resumenJson.getBigDecimal(TOTALPAGAR));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setTotalLetras(resumenJson.getString(TOTALLETRAS));} 						catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setSaldoFavor(resumenJson.getBigDecimal(SALDOFAVOR));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setCondicionOperacion(resumenJson.getInt(CONDICIONOPERACION));} 			catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setTotalDescu(resumenJson.getBigDecimal(TOTALDESCU));} 					catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setReteRenta(resumenJson.getBigDecimal(RETERENTA));} 						catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {resumen.setTotalIva(resumenJson.getBigDecimal(TOTALIVA));} 						catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}

		JSONArray pagosItemsJson = resumenJson.getJSONArray(PAGOS);
		JSONObject pagosItemJson = pagosItemsJson.getJSONObject(0);
		

		PagosItem newPagosItem = new PagosItem();
		try {newPagosItem.setCodigo(pagosItemJson.getString(CODIGO));} 				catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {newPagosItem.setMontoPago(pagosItemJson.getBigDecimal(MONTOPAGO));}	catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {newPagosItem.setReferencia(pagosItemJson.getString(REFERENCIA));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {newPagosItem.setPlazo(pagosItemJson.getString(PLAZO));} 				catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
		try {newPagosItem.setPeriodo(pagosItemJson.getInt(PERIODO));} 				catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}

		resumen.getPagos().add(newPagosItem);

		JSONArray tributosArrayJson = resumenJson.getJSONArray(TRIBUTOS);
		if (tributosArrayJson.length() >0) {		
			for (int i=0; i< tributosArrayJson.length(); i++) { 
				JSONObject tributosItemJson = tributosArrayJson.getJSONObject(i);
				TributosItemFactura tributosItemFactura = new TributosItemFactura();
				try {tributosItemFactura.setCodigo(tributosItemJson.getString(CODIGO));} 			catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
				try {tributosItemFactura.setDescripcion(tributosItemJson.getString(DESCRIPCION));}	catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}
				try {tributosItemFactura.setValor(tributosItemJson.getBigDecimal(VALOR));} 			catch (Exception e) {errorMessages.append(ERROR_FACTURA_RESUMEN + e);}

				resumen.getTributos().add(tributosItemFactura);		
			}
		}

		System.out.println("End Factura.fillResumen()"); 
		return errorMessages;
	}
	

	
	
	public ExtensionFactura getExtension() {
		return extension;
	}

	public void setExtension(ExtensionFactura extension) {
		this.extension = extension;
	}

	public StringBuffer fillExtension(JSONObject factoryInput) {
		System.out.println("Start Factura.fillExtension()"); 

		JSONObject extensionJson = factoryInput.getJSONObject(EXTENSION);
		if (!extensionJson.getString(NOMBENTREGA).equals("")) 
			try {extension.setNombEntrega(extensionJson.getString(NOMBENTREGA));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_EXTENSION + e);}
		
		if (!extensionJson.getString(DOCUENTREGA).equals("")) 
			try {extension.setDocuEntrega(extensionJson.getString(DOCUENTREGA));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_EXTENSION + e);}
		
		if (!extensionJson.getString(NOMBRECIBE).equals("")) 
			try {extension.setNombRecibe(extensionJson.getString(NOMBRECIBE));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_EXTENSION + e);}
		if (!extensionJson.getString(DOCURECIBE).equals("")) 
			try {extension.setDocuRecibe(extensionJson.getString(DOCURECIBE));} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_EXTENSION + e);}
		if (!extensionJson.getString(OBSERVACIONES).equals("")) 
			try {extension.setObservaciones(extensionJson.getString(OBSERVACIONES));} 	catch (Exception e) {errorMessages.append(ERROR_FACTURA_EXTENSION + e);}
		if (!extensionJson.getString(PLACAVEHICULO).equals("")) 
		try {extension.setPlacaVehiculo(extensionJson.getString(PLACAVEHICULO));} 	catch (Exception e) {errorMessages.append(ERROR_FACTURA_EXTENSION + e);}

		System.out.println("End CreditoFiscal.fillExtension()"); 
		return errorMessages;
	}



	public List<DocumentoRelacionadoItemFactura> getDocumentoRelacionado() {
		return documentoRelacionado;
	}

	public void setDocumentoRelacionado(List<DocumentoRelacionadoItemFactura> documentoRelacionado) {
		this.documentoRelacionado = documentoRelacionado;
	}

	public VentaTerceroFactura getVentaTercero() {
		return ventaTercero;
	}

	public void setVentaTercero(VentaTerceroFactura ventaTercero) {
		this.ventaTercero = ventaTercero;
	}

	public List<ApendiceItemFactura> getApendice() {
		return apendice;
	}

	public void setApendice(List<ApendiceItemFactura> apendice) {
		this.apendice = apendice;
	}
	
	public StringBuffer fillApendice(JSONObject factoryInput) {
		JSONObject apendiceJson = factoryInput.getJSONObject(APENDICE);
		JSONArray apendiceArrayJson = apendiceJson.getJSONArray(APENDICE);
	
		for (int i=0; i< apendiceArrayJson.length(); i++) {
			JSONObject apendiceItemJson = apendiceArrayJson.getJSONObject(i);
			ApendiceItemFactura apendiceItem = new ApendiceItemFactura();
			try {apendiceItem.setCampo(apendiceItemJson.getString(CAMPO)) ;} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_APENDICE + e);}
			try {apendiceItem.setEtiqueta(apendiceItemJson.getString(ETIQUETA));} 	catch (Exception e) {errorMessages.append(ERROR_FACTURA_APENDICE + e);}
			try {apendiceItem.setValor(apendiceItemJson.getString(VALOR)) ;} 		catch (Exception e) {errorMessages.append(ERROR_FACTURA_APENDICE + e);}
			apendice.add(apendiceItem);	
		}
		System.out.println("End CreditoFiscal.fillApendice()"); 
		return errorMessages;
	}


	public List<OtrosDocumentosItemFactura> getOtrosDocumentos() {
		return otrosDocumentos;
	}

	public void setOtrosDocumentos(List<OtrosDocumentosItemFactura> otrosDocumentos) {
		this.otrosDocumentos = otrosDocumentos;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	
}
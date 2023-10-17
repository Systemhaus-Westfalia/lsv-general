/**
 * 
 */
package org.shw.lsv.einvoice.fecrretencionv1;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.utils.EDocument;
import org.shw.lsv.einvoice.utils.EDocumentUtils;


/**
 * 
 */
public class Retencion extends EDocument {	
	static final int CUERPODOCUMENTO_MAXIMUM_ITEMS = 500;
	static final String VALIDATION_RESULT_OK = "OK";
	static final String VALIDATION_CUERPODOCUMENTO_MAX_ITEMS  = "Documento: Retencion, clase: Retencion. Validacion fall??: valor de 'cuerpoDocumento' debe  contener de 1 a 500 elementos";
	
	static final String ERROR_RETENCION_IDENTIFICACION     	= "Documento: Retencion, error en fillIdentification(): ";
	static final String ERROR_RETENCION_EMISOR          	= "Documento: Retencion, error en fillEmisor(): ";
	static final String ERROR_RETENCION_RECEPTOR           	= "Documento: Retencion, error en fillReceptor(): ";
	static final String ERROR_RETENCION_CUERPO_DOCUMENTO	= "Documento: Retencion, error en fillCuerpoDocumento(): ";
	static final String ERROR_RETENCION_RESUMEN            	= "Documento: Retencion, error en fillResumen(): ";
	static final String ERROR_RETENCION_EXTENSION			= "Documento: Retencion, error en fillExtension(): ";
	
	IdentificacionRetencion identificacion;
	EmisorRetencion emisor;
	ReceptorRetencion receptor;
	List<CuerpoDocumentoItemRetencion> cuerpoDocumento;
	ResumenRetencion resumen;
	ExtensionRetencion extension;
	List<ApendiceItemRetencion> apendice=null;  // null allowed

	/**
	 * No parameters
	 */
	public Retencion() {

		this.identificacion       =  new IdentificacionRetencion();
		this.emisor               = new EmisorRetencion();
		this.receptor             = new ReceptorRetencion();
	    this.cuerpoDocumento      = new ArrayList<CuerpoDocumentoItemRetencion>();
		this.resumen              = new ResumenRetencion();
		this.extension            = new ExtensionRetencion();
	    this.apendice             = new ArrayList<ApendiceItemRetencion>();
	}

	/**
	 * @return the identificacion
	 */
	public IdentificacionRetencion getIdentificacion() {
		return identificacion;
	}

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {		
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}


	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(IdentificacionRetencion identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * @param factoryInput the JSONObject that contains the information
	 */
	public StringBuffer fillIdentification(JSONObject factoryInput) {
		System.out.println("Start Retencion.fillIdentificacion()"); 

		JSONObject identificationJson = factoryInput.getJSONObject(IDENTIFICACION);
		try {identificacion.setAmbiente(identificationJson.getString(AMBIENTE));} 					catch (Exception e) {errorMessages.append(ERROR_RETENCION_IDENTIFICACION + e);}
		try {identificacion.setTipoDte(identificationJson.getString(TIPODTE));} 					catch (Exception e) {errorMessages.append(ERROR_RETENCION_IDENTIFICACION + e);}
		try {identificacion.setNumeroControl(identificationJson.getString(NUMEROCONTROL));} 		catch (Exception e) {errorMessages.append(ERROR_RETENCION_IDENTIFICACION + e);}
		try {identificacion.setCodigoGeneracion(identificationJson.getString(CODIGOGENERACION));} 	catch (Exception e) {errorMessages.append(ERROR_RETENCION_IDENTIFICACION + e);}
		try {identificacion.setTipoModelo(identificationJson.getInt(TIPOMODELO));} 					catch (Exception e) {errorMessages.append(ERROR_RETENCION_IDENTIFICACION + e);}
		try {identificacion.setTipoOperacion(identificationJson.getInt(TIPOOPERACION));} 			catch (Exception e) {errorMessages.append(ERROR_RETENCION_IDENTIFICACION + e);}
		try {identificacion.setTipoContingencia(identificationJson.getInt(TIPOCONTINGENCIA));} 		catch (Exception e) {errorMessages.append(ERROR_RETENCION_IDENTIFICACION + e);}
		try {identificacion.setMotivoContin(identificationJson.getString(MOTIVOCONTIN));} 			catch (Exception e) {errorMessages.append(ERROR_RETENCION_IDENTIFICACION + e);}		
		try {identificacion.setFecEmi(identificationJson.getString(FECEMI));} 						catch (Exception e) {errorMessages.append(ERROR_RETENCION_IDENTIFICACION + e);}
		try {identificacion.setHorEmi(identificationJson.getString(HOREMI));} 						catch (Exception e) {errorMessages.append(ERROR_RETENCION_IDENTIFICACION + e);}
		try {identificacion.setTipoMoneda(identificationJson.getString(TIPOMONEDA));} 				catch (Exception e) {errorMessages.append(ERROR_RETENCION_IDENTIFICACION + e);}
		System.out.println("End Retencion.fillIdentificacion()");
		return errorMessages;
	}

	/**
	 * @return the emisor
	 */
	public EmisorRetencion getEmisor() {
		return emisor;
	}

	/**
	 * @param emisor the emisor to set
	 */
	public void setEmisor(EmisorRetencion emisor) {
		this.emisor = emisor;
	}

	public StringBuffer fillEmisor(JSONObject factoryInput) {
		System.out.println("Start Retencion.fillEmisor()");

		JSONObject emisorJson = factoryInput.getJSONObject(EMISOR);
		try {emisor.setNit(emisorJson.getString(NIT));} 									catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.setNrc(emisorJson.getString(NRC));} 									catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.setNombre(emisorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.setCodActividad(emisorJson.getString(CODACTIVIDAD));} 					catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.setDescActividad(emisorJson.getString(DESCACTIVIDAD));} 				catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.setNombreComercial(emisorJson.getString(NOMBRECOMERCIAL));} 			catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}		
		try {emisor.setTipoEstablecimiento(emisorJson.getString(TIPOESTABLECIMIENTO));}		catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}	

		JSONObject jsonDireccion = emisorJson.getJSONObject(DIRECCION);
		try {emisor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}

		try {emisor.setTelefono(emisorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.setCodigoMH(emisorJson.getString(CODIGOMH));} 							catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.setCodigo(emisorJson.getString(CODIGO));} 								catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.setPuntoVentaMH(emisorJson.getString(PUNTOVENTAMH));} 					catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.setPuntoVenta(emisorJson.getString(PUNTOVENTA));} 						catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}
		try {emisor.setCorreo(emisorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_RETENCION_EMISOR + e);}

		System.out.println("End Retencion.fillEmisor()");
		return errorMessages;
	}

	/**
	 * @return the receptor
	 */
	public ReceptorRetencion getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor the receptor to set
	 */
	public void setReceptor(ReceptorRetencion receptor) {
		this.receptor = receptor;
	}

	public StringBuffer fillReceptor(JSONObject factoryInput) {
		System.out.println("Start Retencion.fillReceptor()"); 

		JSONObject receptorJson = factoryInput.getJSONObject(RECEPTOR);
		try {receptor.setTipoDocumento(receptorJson.getString(TIPODOCUMENTO));} 				catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}
		try {receptor.setNumDocumento(receptorJson.getString(NUMDOCUMENTO));} 					catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}
		try {receptor.setNrc(receptorJson.getString(NRC));} 									catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}
		try {receptor.setNombre(receptorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}
		try {receptor.setCodActividad(receptorJson.getString(CODACTIVIDAD));} 					catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}
		try {receptor.setDescActividad(receptorJson.getString(DESCACTIVIDAD));} 				catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}
		try {receptor.setNombreComercial(receptorJson.getString(NOMBRECOMERCIAL));} 			catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}
		
		JSONObject jsonDireccion = receptorJson.getJSONObject(DIRECCION);
		try {receptor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}
		try {receptor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}
		try {receptor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}

		try {receptor.setTelefono(receptorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}
		try {receptor.setCorreo(receptorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_RETENCION_RECEPTOR + e);}

		System.out.println("End Retencion.fillReceptor()"); 
		return errorMessages;
	}

	/**
	 * @return the cuerpoDocumento
	 */
	public List<CuerpoDocumentoItemRetencion> getCuerpoDocumento() {
		return cuerpoDocumento;
	}

	/**
	 * @param cuerpoDocumento the cuerpoDocumento to set
	 */
	public void setCuerpoDocumento(List<CuerpoDocumentoItemRetencion> cuerpoDocumento) {
		this.cuerpoDocumento = cuerpoDocumento;
	}


	public StringBuffer fillCuerpoDocumento(JSONObject factoryInput) {
		System.out.println("Start Retencion.fillCuerpoDocumento()"); 

		JSONObject cuerpoDocumentoItemsJson = factoryInput.getJSONObject(CUERPODOCUMENTO);
		JSONArray cuerpoDocumentoArrayJson = cuerpoDocumentoItemsJson.getJSONArray(CUERPODOCUMENTO);
	
		for (int i=0; i< cuerpoDocumentoArrayJson.length(); i++) {
			JSONObject cuerpoDocumentoItemJson = cuerpoDocumentoArrayJson.getJSONObject(i);
			CuerpoDocumentoItemRetencion cuerpoDocumentoItemRetencion = new CuerpoDocumentoItemRetencion();
			try {cuerpoDocumentoItemRetencion.setNumItem(cuerpoDocumentoItemJson.getInt(NUMITEM));} 						catch (Exception e) {errorMessages.append(ERROR_RETENCION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemRetencion.setTipoDte(cuerpoDocumentoItemJson.getString(NUMITEM));} 						catch (Exception e) {errorMessages.append(ERROR_RETENCION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemRetencion.setTipoDoc(cuerpoDocumentoItemJson.getInt(TIPODOC));} 						catch (Exception e) {errorMessages.append(ERROR_RETENCION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemRetencion.setNumDocumento(cuerpoDocumentoItemJson.getString(NUMDOCUMENTO));} 			catch (Exception e) {errorMessages.append(ERROR_RETENCION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemRetencion.setFechaEmision(cuerpoDocumentoItemJson.getString(FECHAEMISION));} 			catch (Exception e) {errorMessages.append(ERROR_RETENCION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemRetencion.setMontoSujetoGrav(cuerpoDocumentoItemJson.getBigDecimal(MONTOSUJETOGRAV));} 	catch (Exception e) {errorMessages.append(ERROR_RETENCION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemRetencion.setCodigoRetencionMH(cuerpoDocumentoItemJson.getString(CODIGORETENCIONMH));}	catch (Exception e) {errorMessages.append(ERROR_RETENCION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemRetencion.setIvaRetenido(cuerpoDocumentoItemJson.getBigDecimal(IVARETENIDO));} 			catch (Exception e) {errorMessages.append(ERROR_RETENCION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemRetencion.setDescripcion(cuerpoDocumentoItemJson.getString(DESCRIPCION));} 				catch (Exception e) {errorMessages.append(ERROR_RETENCION_CUERPO_DOCUMENTO + e);}

			cuerpoDocumento.add(cuerpoDocumentoItemRetencion);						
		}

		System.out.println("End Retencion.fillCuerpoDocumento()"); 
		return errorMessages;
	}

	/**
	 * @return the resumen
	 */
	public ResumenRetencion getResumen() {
		return resumen;
	}

	/**
	 * @param resumen the resumen to set
	 */
	public void setResumen(ResumenRetencion resumen) {
		this.resumen = resumen;
	}

	
	public StringBuffer fillResumen(JSONObject factoryInput) {
		System.out.println("Start Retencion.fillResumen()");
		JSONObject resumenJson = factoryInput.getJSONObject(RESUMEN);		

		try {resumen.setTotalSujetoRetencion(resumenJson.getBigDecimal(TOTALSUJETORETENCION));}	catch (Exception e) {errorMessages.append(ERROR_RETENCION_RESUMEN + e);}
		try {resumen.setTotalIVAretenido(resumenJson.getBigDecimal(TOTALIVARETENIDO));} 		catch (Exception e) {errorMessages.append(ERROR_RETENCION_RESUMEN + e);}
		try {resumen.setTotalIVAretenidoLetras(resumenJson.getString(TOTALIVARETENIDOLETRAS));} catch (Exception e) {errorMessages.append(ERROR_RETENCION_RESUMEN + e);}

		System.out.println("End Retencion.fillResumen()"); 
		return errorMessages;
	}

	/**
	 * @return the extension
	 */
	public ExtensionRetencion getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(ExtensionRetencion extension) {
		this.extension = extension;
	}

	
	public StringBuffer fillExtension(JSONObject factoryInput) {
		System.out.println("Start Retencion.fillExtension()");
		JSONObject resumenJson = factoryInput.getJSONObject(EXTENSION);		

		try {resumen.setTotalIVAretenido(resumenJson.getBigDecimal(TOTALIVARETENIDO));}	catch (Exception e) {errorMessages.append(ERROR_RETENCION_EXTENSION + e);}

		System.out.println("End Retencion.fillExtension()"); 
		return errorMessages;
	}

	/**
	 * @return the apendice
	 */
	public List<ApendiceItemRetencion> getApendice() {
		return apendice;
	}

	/**
	 * @param apendice the apendice to set
	 */
	public void setApendice(List<ApendiceItemRetencion> apendice) {
		this.apendice = apendice;
	}

	
	public StringBuffer fillApendice(JSONObject factoryInput) {
		System.out.println("Start Retencion.fillApendice()");
		//JSONObject resumenJson = factoryInput.getJSONObject(EXTENSION);

		System.out.println("End Retencion.fillApendice()"); 
		return errorMessages;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
}

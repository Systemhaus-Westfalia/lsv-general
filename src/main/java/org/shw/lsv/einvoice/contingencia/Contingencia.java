/**
 * 
 */
package org.shw.lsv.einvoice.contingencia;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.utils.EDocument;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * 
 */
public class Contingencia extends EDocument {
	static final String ERROR_CONTINGENCIA_IDENTIFICACION  = "Documento: Contingencia, error en fillIdentification(): ";
	static final String ERROR_CONTINGENCIA_EMISOR          = "Documento: Contingencia, error en fillEmisor(): ";
	static final String ERROR_CONTINGENCIA_DETALLE_DTE     = "Documento: Contingencia, error en fillDetalleDTE(): ";
	static final String ERROR_CONTINGENCIA_MOTIVO		   = "Documento: Contingencia, error en fillMotivo(): ";

	IdentificacionContingencia       identificacion;
	EmisorContingencia               emisor;
	List<DetalleDTEItemContingencia> detalleDTE;
	MotivoContingencia               motivo;

	/**
	 * No parameters
	 */
	public Contingencia() {
		this.identificacion = new IdentificacionContingencia();
		this.emisor         = new EmisorContingencia();
		this.detalleDTE     = new ArrayList<DetalleDTEItemContingencia>();
		this.motivo         = new MotivoContingencia();
	}


	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		System.out.println("Contingencia: start validating values");

		System.out.println("Contingencia: end validating values");
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}


	/**
	 * @return the identificacion
	 */
	public IdentificacionContingencia getIdentificacion() {
		return identificacion;
	}


	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(IdentificacionContingencia identificacion) {
		this.identificacion = identificacion;
	}


	public StringBuffer fillIdentification(JSONObject factoryInput) {
		System.out.println("Start Contingencia.fillIdentificacion()"); 

		JSONObject identificationJson = factoryInput.getJSONObject(IDENTIFICACION);
		//try {identificacion.setVersion(identificationJson.getInt(VERSION));} 						catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_IDENTIFICACION + e);}
		try {identificacion.setAmbiente(identificationJson.getString(AMBIENTE));} 					catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_IDENTIFICACION + e);}
		try {identificacion.setCodigoGeneracion(identificationJson.getString(CODIGOGENERACION));}	catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_IDENTIFICACION + e);}
		try {identificacion.setFTransmision(identificationJson.getString(FTRANSMISION));} 			catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_IDENTIFICACION + e);}
		try {identificacion.setHTransmision(identificationJson.getString(HTRANSMISION));} 			catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_IDENTIFICACION + e);}
		System.out.println("End Contingencia.fillIdentificacion()");
		return errorMessages;
	}


	/**
	 * @return the emisor
	 */
	public EmisorContingencia getEmisor() {
		return emisor;
	}


	/**
	 * @param emisor the emisor to set
	 */
	public void setEmisor(EmisorContingencia emisor) {
		this.emisor = emisor;
	}


	public StringBuffer fillEmisor(JSONObject factoryInput) {
		System.out.println("Start Contingencia.fillEmisor()"); 

		JSONObject emisorJson = factoryInput.getJSONObject(EMISOR);
		try {emisor.setNit(emisorJson.getString(NIT));} 								  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_EMISOR + e);}
		try {emisor.setNombre(emisorJson.getString(NOMBRE));} 							  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_EMISOR + e);}
		try {emisor.setNombreResponsable(emisorJson.getString(NOMBRERESPONSABLE));} 	  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_EMISOR + e);}
		try {emisor.setTipoDocResponsable(emisorJson.getString(TIPDOCRESPONSABLE));} 	  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_EMISOR + e);}
		try {emisor.setNumeroDocResponsable(emisorJson.getString(NUMERODOCRESPONSABLE));} catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_EMISOR + e);}
		try {emisor.setTipoEstablecimiento(emisorJson.getString(TIPOESTABLECIMIENTO));}	  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_EMISOR + e);}
		try {emisor.setCodEstableMH(emisorJson.getString(CODESTABLEMH));} 				  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_EMISOR + e);}
		try {emisor.setCodPuntoVenta(emisorJson.getString(CODPUNTOVENTA));} 			  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_EMISOR + e);}
		try {emisor.setTelefono(emisorJson.getString(TELEFONO));} 						  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_EMISOR + e);}
		try {emisor.setCorreo(emisorJson.getString(CORREO));} 							  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_EMISOR + e);}

		System.out.println("End Contingencia.fillEmisor()");
		return errorMessages;
	}


	/**
	 * @return the detalleDTE
	 */
	public List<DetalleDTEItemContingencia> getDetalleDTE() {
		return detalleDTE;
	}


	/**
	 * @param detalleDTE the detalleDTE to set
	 */
	public void setDetalleDTE(List<DetalleDTEItemContingencia> detalleDTE) {
		this.detalleDTE = detalleDTE;
	}


	public StringBuffer fillDetalleDTE(JSONObject factoryInput) {
		System.out.println("Start Contingencia.fillDetalleDTE()");

		JSONObject detalleDTEItemsJson = factoryInput.getJSONObject(DETALLEDTE);
		JSONArray  detalleDTEArrayJson = detalleDTEItemsJson.getJSONArray(DETALLEDTE);
	
		for (int i=0; i< detalleDTEArrayJson.length(); i++) {
			JSONObject detalleDTEItemJson = detalleDTEArrayJson.getJSONObject(i);
			DetalleDTEItemContingencia detalleDTE = new DetalleDTEItemContingencia();
			
			try {detalleDTE.setNoItem(detalleDTEItemJson.getInt(NOITEM));} 			                catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_DETALLE_DTE + e);}
			try {detalleDTE.setCodigoGeneracion(detalleDTEItemJson.getString(CODIGOGENERACION));} 	catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_DETALLE_DTE + e);}
			try {detalleDTE.setTipoDoc(detalleDTEItemJson.getString(TIPODOC));} 					catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_DETALLE_DTE + e);}
		}
		System.out.println("End Contingencia.fillDetalleDTE()");
		return errorMessages;
	}


	/**
	 * @return the motivo
	 */
	public MotivoContingencia getMotivo() {
		return motivo;
	}


	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(MotivoContingencia motivo) {
		this.motivo = motivo;
	}


	public StringBuffer fillMotivo(JSONObject factoryInput) {
		System.out.println("Start Contingencia.fillMotivo()");

		JSONObject motivoJson = factoryInput.getJSONObject(MOTIVO);
		try {motivo.setFInicio(motivoJson.getString(FINICIO));} 			          catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_MOTIVO + e);}
		try {motivo.setFFin(motivoJson.getString(FFIN));} 			                  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_MOTIVO + e);}
		try {motivo.setHInicio(motivoJson.getString(HINICIO));} 		 	          catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_MOTIVO + e);}
		try {motivo.setHFin(motivoJson.getString(HFIN));} 			                  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_MOTIVO + e);}
		try {motivo.setTipoContingencia(motivoJson.getInt(TIPOCONTINGENCIA));} 		  catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_MOTIVO + e);}
		try {motivo.setMotivoContingencia(motivoJson.getString(MOTIVOCONTINGENCIA));} catch (Exception e) {errorMessages.append(ERROR_CONTINGENCIA_MOTIVO + e);}

		System.out.println("End Contingencia.fillMotivo()");
		return errorMessages;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}

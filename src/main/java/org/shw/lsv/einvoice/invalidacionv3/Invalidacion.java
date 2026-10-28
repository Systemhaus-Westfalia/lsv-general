/**
 * 
 */
package org.shw.lsv.einvoice.invalidacionv3;

import org.json.JSONObject;
import org.shw.lsv.einvoice.utils.EDocument;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * 
 */
public class Invalidacion extends EDocument {
	static final String VALIDATION_CODIGOGENERACIONR_IS_NOT_NULL	= "Documento: Anulacion, clase: Anulacion. Validacion fall??: valor de 'codigoGeneracionR' debe ser ='null'";
	static final String VALIDATION_CODIGOGENERACIONR_IS_NULL		= "Documento: Anulacion, clase: Anulacion. Validacion fall??: valor de 'codigoGeneracionR' no deber ser ='null'";

	static final String ERROR_ANULACION_IDENTIFICACION	= "Documento: Anulacion, error en fillIdentification(): ";
	static final String ERROR_ANULACION_EMISOR          = "Documento: Anulacion, error en fillEmisor(): ";
	static final String ERROR_ANULACION_DOCUMENTO       = "Documento: Anulacion, error en fillDocumento(): ";
	static final String ERROR_ANULACION_MOTIVO			= "Documento: Anulacion, error en fillMotivo(): ";

	IdentificacionInvalidacion identificacion;
	EmisorInvalidacion emisor;
	DocumentoInvalidacion documento;
	MotivoInvalidacion motivo;
	/**
	 * No parameters
	 */
	public Invalidacion() {
		this.identificacion = new IdentificacionInvalidacion();
		this.emisor         = new EmisorInvalidacion();
		this.documento      = new DocumentoInvalidacion();
		this.motivo         = new MotivoInvalidacion();
	}


	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		System.out.println("Anulacion: start validating values");
		if(getMotivo().getTipoAnulacion()==2) {
			if ( getDocumento().getCodigoGeneracionR()!= null)
				return VALIDATION_CODIGOGENERACIONR_IS_NOT_NULL;
		} else {
			if ( getDocumento().getCodigoGeneracionR()== null)
				return VALIDATION_CODIGOGENERACIONR_IS_NULL;
		}

		System.out.println("Anulacion: end validating values");
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}


	/**
	 * @return the identificacion
	 */
	public IdentificacionInvalidacion getIdentificacion() {
		return identificacion;
	}


	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(IdentificacionInvalidacion identificacion) {
		this.identificacion = identificacion;
	}



	public StringBuffer fillIdentification(JSONObject factoryInput) {
		System.out.println("Start Anulacion.fillIdentificacion()"); 

		JSONObject identificationJson = factoryInput.getJSONObject(IDENTIFICACION);
		//try {identificacion.setVersion(identificationJson.getInt(VERSION));} 						catch (Exception e) {errorMessages.append(ERROR_ANULACION_IDENTIFICACION + e);}
		try {identificacion.setAmbiente(identificationJson.getString(AMBIENTE));} 					catch (Exception e) {errorMessages.append(ERROR_ANULACION_IDENTIFICACION + e);}
		try {identificacion.setCodigoGeneracion(identificationJson.getString(CODIGOGENERACION));}	catch (Exception e) {errorMessages.append(ERROR_ANULACION_IDENTIFICACION + e);}
		try {identificacion.setFecAnula(identificationJson.getString(FECANULA));} 					catch (Exception e) {errorMessages.append(ERROR_ANULACION_IDENTIFICACION + e);}
		try {identificacion.setHorAnula(identificationJson.getString(HORANULA));} 					catch (Exception e) {errorMessages.append(ERROR_ANULACION_IDENTIFICACION + e);}
		System.out.println("End Anulacion.fillIdentificacion()");
		return errorMessages;
	}



	/**
	 * @return the emisor
	 */
	public EmisorInvalidacion getEmisor() {
		return emisor;
	}


	/**
	 * @param emisor the emisor to set
	 */
	public void setEmisor(EmisorInvalidacion emisor) {
		this.emisor = emisor;
	}



	public StringBuffer fillEmisor(JSONObject factoryInput) {
		System.out.println("Start Anulacion.fillEmisor()"); 

		JSONObject emisorJson = factoryInput.getJSONObject(EMISOR);
		try {emisor.setNit(emisorJson.getString(NIT));} 								catch (Exception e) {errorMessages.append(ERROR_ANULACION_EMISOR + e);}
		try {emisor.setNombre(emisorJson.getString(NOMBRE));} 							catch (Exception e) {errorMessages.append(ERROR_ANULACION_EMISOR + e);}
		try {emisor.setTipoEstablecimiento(emisorJson.getString(TIPOESTABLECIMIENTO));}	catch (Exception e) {errorMessages.append(ERROR_ANULACION_EMISOR + e);}
		try {emisor.setNomEstablecimiento(emisorJson.getString(NOMESTABLECIMIENTO));} 	catch (Exception e) {errorMessages.append(ERROR_ANULACION_EMISOR + e);}
//		try {emisor.setCodEstableMH(emisorJson.getString(CODESTABLEMH));} 				catch (Exception e) {errorMessages.append(ERROR_ANULACION_EMISOR + e);}
//		try {emisor.setCodEstable(emisorJson.getString(CODESTABLE));} 					catch (Exception e) {errorMessages.append(ERROR_ANULACION_EMISOR + e);}
//		try {emisor.setCodPuntoVentaMH(emisorJson.getString(CODPUNTOVENTAMH));} 		catch (Exception e) {errorMessages.append(ERROR_ANULACION_EMISOR + e);}
//		try {emisor.setCodPuntoVenta(emisorJson.getString(CODPUNTOVENTA));} 			catch (Exception e) {errorMessages.append(ERROR_ANULACION_EMISOR + e);}
		try {emisor.setTelefono(emisorJson.getString(TELEFONO));} 						catch (Exception e) {errorMessages.append(ERROR_ANULACION_EMISOR + e);}
		try {emisor.setCorreo(emisorJson.getString(CORREO));} 							catch (Exception e) {errorMessages.append(ERROR_ANULACION_EMISOR + e);}

		System.out.println("End Anulacion.fillEmisor()");
		return errorMessages;
	}



	/**
	 * @return the documento
	 */
	public DocumentoInvalidacion getDocumento() {
		return documento;
	}


	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(DocumentoInvalidacion	documento) {
		this.documento = documento;
	}


	public StringBuffer fillDocumento(JSONObject factoryInput) {
		System.out.println("Start Anulacion.fillDocumento()");

		JSONObject documentoJson = factoryInput.getJSONObject(DOCUMENTO);
		try {documento.setTipoDte(documentoJson.getString(TIPODTE));} 						catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}
		try {documento.setCodigoGeneracion(documentoJson.getString(CODIGOGENERACION));} 	catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}
		try {documento.setSelloRecibido(documentoJson.getString(SELLORECIBIDO));} 			catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}
		try {documento.setNumeroControl(documentoJson.getString(NUMEROCONTROL));} 			catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}
		try {documento.setFecEmi(documentoJson.getString(FECEMI));} 						catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}
		try {documento.setMontoIva(documentoJson.getBigDecimal(MONTOIVA));} 				catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}
		//try {documento.setCodigoGeneracionR(documentoJson.getString(CODIGOGENERACIONR));}	catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}
		try {documento.setTipoDocumento(documentoJson.getString(TIPODOCUMENTO));} 			catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}
		try {documento.setNumDocumento(documentoJson.getString(NUMDOCUMENTO));} 			catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}
		try {documento.setNombre(documentoJson.getString(NOMBRE));} 						catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}
		
		try {if (documentoJson.getString(TELEFONO).length()>0)
				documento.setTelefono(documentoJson.getString(TELEFONO));} 					catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}
		try {documento.setCorreo(documentoJson.getString(CORREO));} 						catch (Exception e) {errorMessages.append(ERROR_ANULACION_DOCUMENTO + e);}

		System.out.println("End Anulacion.fillDocumento()");
		return errorMessages;
	}


	/**
	 * @return the motivo
	 */
	public MotivoInvalidacion getMotivo() {
		return motivo;
	}


	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(MotivoInvalidacion motivo) {
		this.motivo = motivo;
	}


	public StringBuffer fillMotivo(JSONObject factoryInput) {
		System.out.println("Start Anulacion.fillMotivo()");

		JSONObject motivoJson = factoryInput.getJSONObject(MOTIVO);
		try {motivo.setTipoAnulacion(motivoJson.getInt(TIPOANULACION));} 			catch (Exception e) {errorMessages.append(ERROR_ANULACION_MOTIVO + e);}
		try {motivo.setMotivoAnulacion(motivoJson.getString(MOTIVOANULACION));} 	catch (Exception e) {errorMessages.append(ERROR_ANULACION_MOTIVO + e);}
		try {motivo.setNombreResponsable(motivoJson.getString(NOMBRERESPONSABLE));}	catch (Exception e) {errorMessages.append(ERROR_ANULACION_MOTIVO + e);}
		try {motivo.setTipDocResponsable(motivoJson.getString(TIPDOCRESPONSABLE));} catch (Exception e) {errorMessages.append(ERROR_ANULACION_MOTIVO + e);}
		try {motivo.setNumDocResponsable(motivoJson.getString(NUMDOCRESPONSABLE));} catch (Exception e) {errorMessages.append(ERROR_ANULACION_MOTIVO + e);}
		try {motivo.setNombreSolicita(motivoJson.getString(NOMBRESOLICITA));} 		catch (Exception e) {errorMessages.append(ERROR_ANULACION_MOTIVO + e);}
		try {motivo.setTipDocSolicita(motivoJson.getString(TIPDOCSOLICITA));} 		catch (Exception e) {errorMessages.append(ERROR_ANULACION_MOTIVO + e);}
		try {motivo.setNumDocSolicita(motivoJson.getString(NUMDOCSOLICITA));} 		catch (Exception e) {errorMessages.append(ERROR_ANULACION_MOTIVO + e);}

		System.out.println("End Anulacion.fillMotivo()");
		return errorMessages;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}

package org.shw.lsv.einvoice.utils;

import java.util.Arrays;

/**
 * Just for class Anulacion
 */
public abstract class Motivo {
	static final String VALIDATION_RESULT_OK = "OK";
	static final String VALIDATION_MOTIVOANULACION_IS_NULL = "Documento: Anulacion, clase: Motivo. Validacion fall??: valor de 'motivoAnulacion' no debe ser ='null'";

	int tipoAnulacion;
	String motivoAnulacion=null;  // null permitted
	String nombreResponsable;
	String tipDocResponsable;
	String numDocResponsable;
	String nombreSolicita;
	String tipDocSolicita;
	String numDocSolicita;

	/**
	 * 
	 */
	public Motivo() {
	}



	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {

		if(getTipoAnulacion()==3) {
			if ( getMotivoAnulacion()== null)
				return VALIDATION_MOTIVOANULACION_IS_NULL;
		} 

		return VALIDATION_RESULT_OK;
	}


	/**
	 * @return the tipoAnulacion
	 */
	public int getTipoAnulacion() {
		return tipoAnulacion;
	}




	/**
	 * @param tipoAnulacion the tipoAnulacion to set<br>
	 * The parameter is validated.<br>
	 * "enum" : [1,2,3]
	 */
	public void setTipoAnulacion(int tipoAnulacion) {
		if (tipoAnulacion==1 || tipoAnulacion==2 || tipoAnulacion==3)
			this.tipoAnulacion = tipoAnulacion;
		else
			throw new IllegalArgumentException("Wrong parameter 'tipoAnulacion' in Anulacion.Motivo.setTipoAnulacion()");
	}




	/**
	 * @return the motivoAnulacion
	 */
	public String getMotivoAnulacion() {
		return motivoAnulacion;
	}




	/**
	 * @param motivoAnulacion the motivoAnulacion to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 5, "maxLength" : 250; null also possible
	 */
	public void setMotivoAnulacion(String motivoAnulacion) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 250;
		int length = motivoAnulacion==null?0:motivoAnulacion.length();

		if( (length>=MINLENGTH && length<=MAXLENGTH)  || (motivoAnulacion==null) )
			this.motivoAnulacion = motivoAnulacion;
		else
			throw new IllegalArgumentException("Wrong parameter 'motivoAnulacion' in Anulacion.Motivo.setMotivoAnulacion()");
	}




	/**
	 * @return the nombreResponsable
	 */
	public String getNombreResponsable() {
		return nombreResponsable;
	}




	/**
	 * @param nombreResponsable the nombreResponsable to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 5, "maxLength" : 100
	 */
	public void setNombreResponsable(String nombreResponsable) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 100;
		int length = nombreResponsable==null?0:nombreResponsable.length();

		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.nombreResponsable = nombreResponsable;
		else
			throw new IllegalArgumentException("Wrong parameter 'nombreResponsable' in Anulacion.Motivo.setNombreResponsable()");
	}




	/**
	 * @return the tipDocResponsable
	 */
	public String getTipDocResponsable() {
		return tipDocResponsable;
	}




	/**
	 * @param tipDocResponsable the tipDocResponsable to set<br>
	 * The parameter is validated.<br>
	 * Specific values allowed according to schema: ["36", "13", "02", .....].
	 * Tipo documento de identificaci??n CAT-22: 36 -> NIT, 13 -> DUI, 02 -> Carnet de residente, 03 -> PASAPORTE, 37 -> OTRO
	 * The enum was chosen for validation.
	 */
	public void setTipDocResponsable(String tipDocResponsable) {
		String[] validTipDocResponsable = { "36", "13", "02", "03", "37" };		
		boolean isTipoDocResponsableValid = Arrays.stream(validTipDocResponsable).anyMatch(tipDocResponsable::equals);

		if(isTipoDocResponsableValid)
			this.tipDocResponsable = tipDocResponsable;
		else
			throw new IllegalArgumentException("Wrong parameter 'tipDocResponsable' in Anulacion.Motivo.setTipDocResponsable()");
	}




	/**
	 * @return the numDocResponsable
	 */
	public String getNumDocResponsable() {
		return numDocResponsable;
	}




	/**
	 * @param numDocResponsable the numDocResponsable to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 3, "maxLength" : 20
	 */
	public void setNumDocResponsable(String numDocResponsable) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 20;
		int length = numDocResponsable==null?0:numDocResponsable.length();

		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.numDocResponsable = numDocResponsable;
		else
			throw new IllegalArgumentException("Wrong parameter 'numDocResponsable' in Anulacion.Motivo.setNumDocResponsable()");
	}




	/**
	 * @return the nombreSolicita
	 */
	public String getNombreSolicita() {
		return nombreSolicita;
	}




	/**
	 * @param nombreSolicita the nombreSolicita to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 5, "maxLength" : 100
	 */
	public void setNombreSolicita(String nombreSolicita) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 100;
		int length = nombreSolicita==null?0:nombreSolicita.length();

		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.nombreSolicita = nombreSolicita;
		else
			throw new IllegalArgumentException("Wrong parameter 'nombreSolicita' in Anulacion.Motivo.setNombreSolicita()");
	}




	/**
	 * @return the tipDocSolicita
	 */
	public String getTipDocSolicita() {
		return tipDocSolicita;
	}




	/**
	 * @param tipDocSolicita the tipDocSolicita to set<br>
	 * The parameter is validated.<br>
	 * Specific values allowed according to schema: ["36", "13", "02", .....].
	 * Tipo documento de identificaci??n CAT-22: 36 -> NIT, 13 -> DUI, 02 -> Carnet de residente, 03 -> PASAPORTE, 37 -> OTRO
	 * The enum was chosen for validation.
	 */
	public void setTipDocSolicita(String tipDocSolicita) {
		String[] validTipDocSolicita = { "36", "13", "02", "03", "37" };		
		boolean isTipoDocSolicitaValid = Arrays.stream(validTipDocSolicita).anyMatch(tipDocSolicita::equals);

		if(isTipoDocSolicitaValid)
			this.tipDocSolicita = tipDocSolicita;
		else
			throw new IllegalArgumentException("Wrong parameter 'tipDocSolicita' in Anulacion.Motivo.setTipDocSolicita()");
	}




	/**
	 * @return the numDocSolicita
	 */
	public String getNumDocSolicita() {
		return numDocSolicita;
	}




	/**
	 * @param numDocSolicita the numDocSolicita to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 3, "maxLength" : 20
	 */
	public void setNumDocSolicita(String numDocSolicita) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 20;
		int length = numDocSolicita==null?0:numDocSolicita.length();

		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.numDocSolicita = numDocSolicita;
		else
			throw new IllegalArgumentException("Wrong parameter 'numDocSolicita' in Anulacion.Motivo.setNumDocSolicita()");
	}




	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

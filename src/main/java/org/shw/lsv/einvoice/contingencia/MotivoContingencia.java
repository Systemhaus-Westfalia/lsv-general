package org.shw.lsv.einvoice.contingencia;

import java.util.regex.Pattern;

/**
 * Just for class Contingencia
 */
public class MotivoContingencia {
	static final String VALIDATION_RESULT_OK = "OK";
	static final String VALIDATION_MOTIVOCONTINGENCIA_IS_NULL = "Documento: Contingencia, clase: Motivo. Validacion fall??: valor de 'motivoContingencia' no debe ser ='null'";

	String fInicio;
	String fFin;
	String hInicio;
	String hFin;
	int    tipoContingencia;
	String motivoContingencia=null;  // null permitted

	/**
	 * 
	 */
	public MotivoContingencia() {
	}


	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {

		if(getTipoContingencia()==5) {
			if ( getMotivoContingencia()== null)
				return VALIDATION_MOTIVOCONTINGENCIA_IS_NULL;
		} 

		return VALIDATION_RESULT_OK;
	}


	/**
	 * @return the fInicio
	 */
	public String getFInicio() {
		return fInicio;
	}


	/**
	 * @param fInicio the voiding date to set<br>
	 * null not allowed
	 */
	public void setFInicio(String fInicio) {
		if(fInicio!=null)
			this.fInicio = fInicio;
		else
			throw new IllegalArgumentException("Wrong parameter 'fInicio' (" + fInicio +  ") in Contingencia.motivo.setFInicio()" + "\n");
	}
	

	/**
	 * @return the fFin
	 */
	public String getFFin() {
		return fFin;
	}


	/**
	 * @param fFin the voiding date to set<br>
	 * null not allowed
	 */
	public void setFFin(String fFin) {
		if(fFin!=null)
			this.fFin = fFin;
		else
			throw new IllegalArgumentException("Wrong parameter 'fFin' (" + fFin +  ") in Contingencia.motivo.setFFin()" + "\n");
	}


	/**
	 * @return the hInicio
	 */
	public String getHInicio() {
		return hInicio;
	}


	/**
	 * @param hInicio the voiding hour to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]?$"
	 */
	public void setHInicio(String hInicio) {
		final String PATTERN = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]?$";
		boolean patternOK = (hInicio!=null) && Pattern.matches(PATTERN, hInicio);  

		if(patternOK)
			this.hInicio = hInicio;
		else
			throw new IllegalArgumentException("Wrong expression 'hInicio' (" + hInicio +  ") in Contingencia.motivo.setHInicio()" + "\n");
	}


	/**
	 * @return the hFin
	 */
	public String getHFin() {
		return hFin;
	}


	/**
	 * @param hFin the voiding hour to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]?$"
	 */
	public void setHFin(String hFin) {
		final String PATTERN = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]?$";
		boolean patternOK = (hFin!=null) && Pattern.matches(PATTERN, hFin);  

		if(patternOK)
			this.hFin = hFin;
		else
			throw new IllegalArgumentException("Wrong expression 'hFin' (" + hFin +  ") in Contingencia.motivo.setHFin()" + "\n");
	}


	/**
	 * @return the tipoContingencia
	 */
	public int getTipoContingencia() {
		return tipoContingencia;
	}


	/**
	 * @param tipoContingencia the tipoContingencia to set<br>
	 * The parameter is validated.<br>
	 * "enum" : [1,2,3,4,5]
	 */
	public void setTipoContingencia(int tipoContingencia) {
		if (tipoContingencia==1 || tipoContingencia==2 || tipoContingencia==3 || tipoContingencia==4 || tipoContingencia==5)
			this.tipoContingencia = tipoContingencia;
		else
			throw new IllegalArgumentException("Wrong parameter 'tipoContingencia' (" + tipoContingencia +  ") in Contingencia.motivo.setTipoContingencia()");
	}


	/**
	 * @return the motivoContingencia
	 */
	public String getMotivoContingencia() {
		return motivoContingencia;
	}


	/**
	 * @param motivoContingencia the motivoContingencia to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 0, "maxLength" : 500; null also possible
	 */
	public void setMotivoContingencia(String motivoContingencia) {
		final int MINLENGTH = 0;
		final int MAXLENGTH = 500;
		int length = motivoContingencia==null?0:motivoContingencia.length();

		if( (length>=MINLENGTH && length<=MAXLENGTH)  || (motivoContingencia==null) )
			this.motivoContingencia = motivoContingencia;
		else
			throw new IllegalArgumentException("Wrong parameter 'motivoContingencia' (" + motivoContingencia +  ")  in Contingencia.motivo.setMotivoContingencia()");
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

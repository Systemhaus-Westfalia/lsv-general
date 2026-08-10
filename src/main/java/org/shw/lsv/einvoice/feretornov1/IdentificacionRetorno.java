package org.shw.lsv.einvoice.feretornov1;
import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.EDocumentUtils;


public class IdentificacionRetorno {

	static final int VERSION           = 1;
	static final String TIPO_DE_EVENTO = "18";
	static final String TIPOMONEDA     = "USD";

	static final String VALIDATION_TIPOMODELO_FAILED_1         = "Documento: Evento de Retorno, clase: Identificacion. Validacion fallo: valor de 'tipoModelo' no debe ser diferente a 1";
	static final String VALIDATION_TIPOCONTINGENCIA_NOT_NULL   = "Documento: Evento de Retorno, clase: Identificacion. Validacion fallo: valor de 'tipoContingencia' debe ser ='null'";
	static final String VALIDATION_MOTIVOCONTINGENCIA_NOT_NULL = "Documento: Evento de Retorno, clase: Identificacion. Validacion fallo: valor de 'motivoContingencia' debe ser ='null'";
	static final String VALIDATION_TIPOMODELO_FAILED_2         = "Documento: Evento de Retorno, clase: Identificacion. Validacion fallo: valor de 'tipoModelo' no debe ser diferente a 2";
	static final String VALIDATION_TIPOOCONTINGENCIA_IS_NULL   = "Documento: Evento de Retorno, clase: Identificacion. Validacion fallo: valor de 'tipoContingencia' no debe ser ='null'";
	static final String VALIDATION_MOTIVOCONTINGENCIA_IS_NULL  = "Documento: Evento de Retorno, clase: Identificacion. Validacion fallo: valor de 'motivoContingencia' no debe ser ='null'";

	int version;
	String ambiente;
	int tipoModelo;
	int tipoOperacion;
	String tipoEvento = TIPO_DE_EVENTO;
	Integer tipoContingencia = null; // null erlaubt
	String motivoContin = null;      // null erlaubt
	String codigoGeneracion;
	String fecEmi;
	String horEmi;
	String fusion = null;            // null allowed
	String tipoMoneda = TIPOMONEDA;

	/**
	 * No parameters
	 */
	public IdentificacionRetorno() {
		this.version    = VERSION;
		this.tipoEvento = TIPO_DE_EVENTO;
		this.tipoMoneda = TIPOMONEDA;
	}

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		if (getTipoOperacion() == 1) {
			if (getTipoModelo() != 1)
				return VALIDATION_TIPOMODELO_FAILED_1;
			if (getTipoContingencia() != null)
				return VALIDATION_TIPOCONTINGENCIA_NOT_NULL;
			if (getMotivoContin() != null)
				return VALIDATION_MOTIVOCONTINGENCIA_NOT_NULL;
		} else {
			if (getTipoModelo() != 2)
				return VALIDATION_TIPOMODELO_FAILED_2;
		}

		if (getTipoOperacion() == 2) {
			if (getTipoContingencia() == null)
		        return VALIDATION_TIPOOCONTINGENCIA_IS_NULL;
		}

		if (getTipoContingencia() != null && getTipoContingencia() == 5) {
			if (getMotivoContin() == null)
		        return VALIDATION_MOTIVOCONTINGENCIA_IS_NULL;
		}

		return EDocumentUtils.VALIDATION_RESULT_OK;
	}


	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getAmbiente() {
		return ambiente;
	}

	/**
	 * @param ambiente the ambiente to set<br>
	 * "enum" : ["00", "01"]
	 */
	public void setAmbiente(String ambiente) {
		if (ambiente.compareTo("00") == 0 || ambiente.compareTo("01") == 0)
			this.ambiente = ambiente;
		else
	        throw new IllegalArgumentException("Wrong parameter 'ambiente' in EventoDeRetorno.Identificacion.setAmbiente()" + "\n");
	}

	public int getTipoModelo() {
		return tipoModelo;
	}

	/**
	 * @param tipoModelo the tipoModelo to set<br>
	 * "enum" : [1,2]
	 */
	public void setTipoModelo(int tipoModelo) {
		if (tipoModelo == 1 || tipoModelo == 2)
			this.tipoModelo = tipoModelo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoModelo' in EventoDeRetorno.Identificacion.setTipoModelo()" + "\n");
	}

	public int getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion the tipoOperacion to set<br>
	 * "enum" : [1,2]
	 */
	public void setTipoOperacion(int tipoOperacion) {
		if (tipoOperacion == 1 || tipoOperacion == 2)
			this.tipoOperacion = tipoOperacion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoOperacion' in EventoDeRetorno.Identificacion.setTipoOperacion()" + "\n");
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * @param tipoEvento the tipoEvento to set<br>
	 * "const" : "18"
	 */
	public void setTipoEvento(String tipoEvento) {
		if (TIPO_DE_EVENTO.equals(tipoEvento))
			this.tipoEvento = tipoEvento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoEvento' in EventoDeRetorno.Identificacion.setTipoEvento() — must be '18'" + "\n");
	}

	public Integer getTipoContingencia() {
		return tipoContingencia;
	}

	/**
	 * @param tipoContingencia the tipoContingencia to set<br>
	 * "enum" : [1,2,3,4,5], null
	 */
	public void setTipoContingencia(Integer tipoContingencia) {
		if (tipoContingencia == null || tipoContingencia == 1 || tipoContingencia == 2 || tipoContingencia == 3 || tipoContingencia == 4 || tipoContingencia == 5)
			this.tipoContingencia = tipoContingencia;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoContingencia' in EventoDeRetorno.Identificacion.setTipoContingencia()" + "\n");
	}

	public String getMotivoContin() {
		return motivoContin;
	}

	/**
	 * @param motivoContin the motivoContin to set<br>
	 * "minLength" : 1, "maxLength" : 500; null also allowed
	 */
	public void setMotivoContin(String motivoContin) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 500;
		int length = motivoContin == null ? 0 : motivoContin.length();

		if ((length >= MINLENGTH && length <= MAXLENGTH) || (motivoContin == null))
			this.motivoContin = motivoContin;
		else
	        throw new IllegalArgumentException("Wrong parameter 'motivoContin' in EventoDeRetorno.Identificacion.setMotivoContin()" + "\n");
	}

	public String getCodigoGeneracion() {
		return codigoGeneracion;
	}

	/**
	 * @param codigoGeneracion the codigoGeneracion to set<br>
	 * "pattern" : "^[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}$"
	 */
	public void setCodigoGeneracion(String codigoGeneracion) {
		final String PATTERN = "^[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}$";
		boolean patternOK = (codigoGeneracion != null) && Pattern.matches(PATTERN, codigoGeneracion);

		if (patternOK)
			this.codigoGeneracion = codigoGeneracion;
		else
	        throw new IllegalArgumentException("Wrong expression 'codigoGeneracion' in EventoDeRetorno.Identificacion.setCodigoGeneracion()" + "\n");
	}

	public String getFecEmi() {
		return fecEmi;
	}

	public void setFecEmi(String fecEmi) {
		if (fecEmi != null)
			this.fecEmi = fecEmi;
		else
	        throw new IllegalArgumentException("Wrong parameter 'fecEmi' in EventoDeRetorno.Identificacion.setFecEmi()" + "\n");
	}

	public String getHorEmi() {
		return horEmi;
	}

	/**
	 * @param horEmi the horEmi to set<br>
	 * "pattern" : "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$"
	 */
	public void setHorEmi(String horEmi) {
		final String PATTERN = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
		boolean patternOK = (horEmi != null) && Pattern.matches(PATTERN, horEmi);

		if (patternOK)
			this.horEmi = horEmi;
		else
	        throw new IllegalArgumentException("Wrong expression 'horEmi' in EventoDeRetorno.Identificacion.setHorEmi()" + "\n");
	}

	public String getFusion() {
		return fusion;
	}

	/**
	 * @param fusion the fusion to set<br>
	 * "minLength" : 9, "maxLength" : 14; null also allowed
	 */
	public void setFusion(String fusion) {
		final int MINLENGTH = 9;
		final int MAXLENGTH = 14;
		int length = fusion == null ? 0 : fusion.length();

		if (fusion == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.fusion = fusion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'fusion' in EventoDeRetorno.Identificacion.setFusion()" + "\n");
	}

	public String getTipoMoneda() {
		return tipoMoneda;
	}

	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

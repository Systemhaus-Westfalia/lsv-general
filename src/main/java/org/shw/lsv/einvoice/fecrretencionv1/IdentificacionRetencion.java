package org.shw.lsv.einvoice.fecrretencionv1;
import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.EDocumentUtils; 

public class IdentificacionRetencion {

	static final int VERSION              = 1;
	static final String TIPO_DE_DOCUMENTO = "07";
	static final String TIPOMONEDA        = "USD";
	static final int TIPOMODELO           = 1;
	static final int TIPOOPERACION        = 1;
	
	int version=VERSION;
	String ambiente;
	String tipoDte=TIPO_DE_DOCUMENTO;
	String numeroControl;
	String codigoGeneracion;
	int tipoModelo=TIPOMODELO;
	int tipoOperacion=TIPOOPERACION;
	Integer tipoContingencia=null;  // null mandatory
	String motivoContin=null; // null mandatory
	String fecEmi;
	String horEmi;
	String tipoMoneda = TIPOMONEDA;

	/**
	 * No parameters
	 */
	public IdentificacionRetencion() {
		this.version            = VERSION;
		this.tipoDte            = TIPO_DE_DOCUMENTO;
		this.tipoModelo         = TIPOMODELO;
		this.tipoOperacion      = TIPOOPERACION;
		this.tipoContingencia   = null;
		this.motivoContin       = null;
		this.tipoMoneda         = TIPOMONEDA;
	}


	/**
	 * No validation required.
	 */
	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}


	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the ambiente
	 */
	public String getAmbiente() {
		return ambiente;
	}


	/**
	 * @param ambiente the ambiente to set<br>
	 * The parameter is validated.<br>
	 * "enum" : ["00", "01"]
	 */
	public void setAmbiente(String ambiente) {
		if (ambiente.compareTo("00")==0 || ambiente.compareTo("01")==0)
			this.ambiente = ambiente;
		else
	        throw new IllegalArgumentException("Wrong parameter 'ambiente' in Retencion.Identificacion.setAmbiente()" + "\n");
	}

	/**
	 * @return the tipoDte
	 */
	public String getTipoDte() {
		return tipoDte;
	}


	/**
	 * @param tipoDte the tipoDte to set
	 */
	public void setTipoDte(String tipoDte) {
		this.tipoDte = tipoDte;
	}


	/**
	 * @return the numeroControl
	 */
	public String getNumeroControl() {
		return numeroControl;
	}


	/**
	 * @param numeroControl the numeroControl to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^DTE-07-[A-Z0-9]{8}-[0-9]{15}$"
	 */
	public void setNumeroControl(String numeroControl) {
		final String PATTERN = "^DTE-07-[A-Z0-9]{8}-[0-9]{15}$";
		boolean patternOK = (numeroControl!=null) && Pattern.matches(PATTERN, numeroControl);  
		
		if(patternOK)
			this.numeroControl = numeroControl;
		else
	        throw new IllegalArgumentException("Wrong expression 'numeroControl' in Retencion.Identificacion.setNumeroControl()" + "\n");
	}


	/**
	 * @return the codigoGeneracion
	 */
	public String getCodigoGeneracion() {
		return codigoGeneracion;
	}

	/**
	 * @return the tipoContingencia
	 */
	public Integer getTipoContingencia() {
		return tipoContingencia;
	}

	/**
	 * @param tipoContingencia the tipoContingencia to set<br>
	 * The parameter is validated.<br>
	 * null mandatory
	 */
	public void setTipoContingencia(Integer tipoContingencia) {
		if (tipoContingencia==null)
			this.tipoContingencia = tipoContingencia;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoContingencia' in Retencion.Identificacion.setTipoContingencia()" + "\n");

		// Schema conditions
		//if(getTipoContingencia()==5) {
		//	// it should be a string, but how???
		//}
	}

	/**
	 * @param codigoGeneracion the codigoGeneracion to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}$"
	 */
	public void setCodigoGeneracion(String codigoGeneracion) {
		final String PATTERN = "^[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}$";
		boolean patternOK = (codigoGeneracion!=null) && Pattern.matches(PATTERN, codigoGeneracion);  
		
		if(patternOK)
			this.codigoGeneracion = codigoGeneracion;
		else
	        throw new IllegalArgumentException("Wrong expression 'codigoGeneracion' in Retencion.Identificacion.setCodigoGeneracion()" + "\n");
	}


	/**
	 * @return the motivoContin
	 */
	public String getMotivoContin() {
		return motivoContin;
	}


	/**
	 * @param motivoContin the motivoContin to set<br>
	 * The parameter is validated.<br>
	 * null mandatory
	 */
	public void setMotivoContin(String motivoContin) {
		if(motivoContin==null)
			this.motivoContin = motivoContin;
		else
	        throw new IllegalArgumentException("Wrong parameter 'motivoContin' in Retencion.Identificacion.setmotivoContin()" + "\n");
	}


	/**
	 * @return the fecEmi
	 */
	public String getFecEmi() {
		return fecEmi;
	}


	/**
	 * @param fecEmi the fecEmi to set<br>
	 * null not allowed
	 */
	public void setFecEmi(String fecEmi) {
		if (fecEmi!=null)
			this.fecEmi = fecEmi;
		else
	        throw new IllegalArgumentException("Wrong parameter 'fecEmi' in Retencion.Identificacion.setFecEmi()" + "\n");
	}


	/**
	 * @return the horEmi
	 */
	public String getHorEmi() {
		return horEmi;
	}


	/**
	 * @param horEmi the horEmi to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]?$"
	 */
	public void setHorEmi(String horEmi) {
		final String PATTERN = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]?$";
		boolean patternOK = (horEmi!=null) && Pattern.matches(PATTERN, horEmi);  
		
		if(patternOK)
			this.horEmi = horEmi;
		else
	        throw new IllegalArgumentException("Wrong expression 'horEmi' in Retencion.Identificacion.setHorEmi()" + "\n");
	}


	/**
	 * @return the tipoMoneda
	 */
	public String getTipoMoneda() {
		return tipoMoneda;
	}


	/**
	 * @param tipoMoneda the tipoMoneda to set
	 */
	public void setTipoMoneda(String tipoMoneda) {
		if (tipoMoneda==TIPOMONEDA)
			this.tipoMoneda = tipoMoneda;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoMoneda' in Retencion.Identificacion.setTipoMoneda()" + "\n");
		
	}

	/**
	 * @return the tipoModelo
	 */
	public int getTipoModelo() {
		return tipoModelo;
	}

	/**
	 * @param tipoModelo the tipoModelo to set<br>
	 * The parameter is validated.<br>
	 * "enum" : [1]
	 */
	public void setTipoModelo(int tipoModelo) {
		if (tipoModelo==TIPOMODELO)
			this.tipoModelo = tipoModelo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoModelo' in Retencion.Identificacion.setTipoModelo()" + "\n");
	}

	/**
	 * @return the tipoOperacion
	 */
	public int getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion the tipoOperacion to set<br>
	 * The parameter is validated.<br>
	 * "enum" : [1]
	 */
	public void setTipoOperacion(int tipoOperacion) {
		if (tipoOperacion==TIPOOPERACION)
			this.tipoOperacion = tipoOperacion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoOperacion' in Retencion.Identificacion.setTipoOperacion()" + "\n");
	}

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



}

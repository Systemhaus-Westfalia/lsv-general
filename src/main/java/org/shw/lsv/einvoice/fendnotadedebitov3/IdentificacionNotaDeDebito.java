package org.shw.lsv.einvoice.fendnotadedebitov3;
import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.EDocumentUtils;
 

public class IdentificacionNotaDeDebito {

	static final int VERSION              = 3;
	static final String TIPO_DE_DOCUMENTO = "06";
	static final String TIPOMONEDA        = "USD";
	
	static final String VALIDATION_TIPOMODELO_FAILED_1         = "Documento: Nota de Debito, clase: Identificacion. Validacion fall??: valor de 'tipoModelo' no debe ser diferente a 1";
	static final String VALIDATION_TIPOCONTINGENCIA_NOT_NULL   = "Documento: Nota de Debito, clase: Identificacion. Validacion fall??: valor de 'tipoContingencia' debe ser ='null'";
	static final String VALIDATION_MOTIVOCONTINGENCIA_NOT_NULL = "Documento: Nota de Debito, clase: Identificacion. Validacion fall??: valor de 'motivoContingencia' debe ser ='null'";
	static final String VALIDATION_TIPOMODELO_FAILED_2         = "Documento: Nota de Debito, clase: Identificacion. Validacion fall??: valor de 'tipoModelo' no debe ser diferente a 2";
	static final String VALIDATION_TIPOOCONTINGENCIA_IS_NULL   = "Documento: Nota de Debito, clase: Identificacion. Validacion fall??: valor de 'tipoContingencia' no debe ser ='null'";
	static final String VALIDATION_MOTIVOCONTINGENCIA_IS_NULL  = "Documento: Nota de Debito, clase: Identificacion. Validacion fall??: valor de 'motivoContingencia' no debe ser ='null'";
	
	int version;
	String ambiente;
	String tipoDte ;
	String numeroControl;
	String codigoGeneracion;
	int tipoModelo;
	int tipoOperacion;
	Integer tipoContingencia=null;  // null erlaubt
	String motivoContin=null;       // null erlaubt
	String fecEmi;
	String horEmi;
	String tipoMoneda = TIPOMONEDA;

	/**
	 * No parameters
	 */
	public IdentificacionNotaDeDebito() {
		this.version    = VERSION;
		this.tipoDte    = TIPO_DE_DOCUMENTO;
		this.tipoMoneda = TIPOMONEDA;
	}
	
	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		if(getTipoOperacion()==1) {
			if (getTipoModelo() != 1) 
				return VALIDATION_TIPOMODELO_FAILED_1;
			if (getTipoContingencia() != null) 
				return VALIDATION_TIPOCONTINGENCIA_NOT_NULL;
			if (getMotivoContin() != null) 
				return VALIDATION_MOTIVOCONTINGENCIA_NOT_NULL;
		} else  {
			if (getTipoModelo() != 2) 
				return VALIDATION_TIPOMODELO_FAILED_2;
		}
		
		if(getTipoOperacion()==2) {
			// In schema: "tipoContingencia" : {"type" : "integer"}
			if(getTipoContingencia()==null)
		        return VALIDATION_TIPOOCONTINGENCIA_IS_NULL;
		}
		
		if(getTipoContingencia()==5) {
			// In schema: "motivoContin" : {"type" : "string"}
			if(getMotivoContin()==null)
		        return VALIDATION_MOTIVOCONTINGENCIA_IS_NULL;
		}
		
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
	        throw new IllegalArgumentException("Wrong parameter 'ambiente' in NotaDeDebito.Identificacion.setAmbiente()");
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
	 * "pattern" : "^DTE-06-[A-Z0-9]{8}-[0-9]{15}$"
	 */
	public void setNumeroControl(String numeroControl) {
		final String PATTERN = "^DTE-06-[A-Z0-9]{8}-[0-9]{15}$";
		boolean patternOK = (numeroControl!=null) && Pattern.matches(PATTERN, numeroControl);  
		
		if(patternOK)
			this.numeroControl = numeroControl;
		else
	        throw new IllegalArgumentException("Wrong expression 'numeroControl' in NotaDeDebito.Identificacion.setNumeroControl()");
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
	 * "enum" : [1,2,3,4,5], null
	 */
	public void setTipoContingencia(Integer tipoContingencia) {
		if (tipoContingencia==null || tipoContingencia==1 || tipoContingencia==2 || tipoContingencia==3 || tipoContingencia==4 || tipoContingencia==5)
			this.tipoContingencia = tipoContingencia;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoContingencia' in NotaDeDebito.Identificacion.setTipoContingencia()");

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
	        throw new IllegalArgumentException("Wrong expression 'codigoGeneracion' in NotaDeDebito.Identificacion.setCodigoGeneracion()");
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
	 * "minLength" : 1, "maxLength" : 150
	 */
	public void setMotivoContin(String motivoContin) {
		final int MINLENGTH = 1;		
		final int MAXLENGTH = 150;
		int length = motivoContin==null?0:motivoContin.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH ) || (motivoContin==null) )
			this.motivoContin = motivoContin;
		else
	        throw new IllegalArgumentException("Wrong parameter 'motivoContin' in NotaDeDebito.Identificacion.setMotivoContin()");
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
	        throw new IllegalArgumentException("Wrong parameter 'fecEmi' in NotaDeDebito.Identificacion.setFecEmi()");
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
	        throw new IllegalArgumentException("Wrong expression 'horEmi' in NotaDeDebito.Identificacion.setHorEmi()");
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
		this.tipoMoneda = tipoMoneda;
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
	 * "enum" : [1,2]
	 */
	public void setTipoModelo(int tipoModelo) {
		if (tipoModelo==1 || tipoModelo==2)
			this.tipoModelo = tipoModelo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoModelo' in NotaDeDebito.Identificacion.setTipoModelo()");
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
	 * "enum" : [1,2]
	 */
	public void setTipoOperacion(int tipoOperacion) {
		if (tipoOperacion==1 || tipoOperacion==2)
			this.tipoOperacion = tipoOperacion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoOperacion' in NotaDeDebito.Identificacion.setTipoOperacion()");
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

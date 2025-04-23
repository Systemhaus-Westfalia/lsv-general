package org.shw.lsv.einvoice.contingencia;
import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * Just for class Contingencia
 */
public class IdentificacionContingencia {

	static final int VERSION  = 3;
	
	int    version;
	String ambiente;
	String codigoGeneracion;
	String fTransmision;
	String hTransmision; 

	/**
	 * No parameters
	 */
	public IdentificacionContingencia() {
		this.version = VERSION;
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
			throw new IllegalArgumentException("Wrong parameter 'ambiente' (" + ambiente +  ") in Contingencia.identificacion.setAmbiente()" + "\n");
	}


	/**
	 * @return the codigoGeneracion
	 */
	public String getCodigoGeneracion() {
		return codigoGeneracion;
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
			throw new IllegalArgumentException("Wrong expression 'codigoGeneracion' (" + codigoGeneracion +  ") in Contingencia.identificacion.setCodigoGeneracion()" + "\n");
	}


	/**
	 * @return the fTransmision
	 */
	public String getFTransmision() {
		return fTransmision;
	}


	/**
	 * @param fTransmision the voiding date to set<br>
	 * null not allowed
	 */
	public void setFTransmision(String fTransmision) {
		if(fTransmision!=null)
			this.fTransmision = fTransmision;
		else
			throw new IllegalArgumentException("Wrong parameter 'fTransmision' (" + fTransmision +  ") in Contingencia.identificacion.setfTransmision()" + "\n");
	}


	/**
	 * @return the hTransmision
	 */
	public String getHTransmision() {
		return hTransmision;
	}


	/**
	 * @param hTransmision the voiding hour to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]?$"
	 */
	public void setHTransmision(String hTransmision) {
		final String PATTERN = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]?$";
		boolean patternOK = (hTransmision!=null) && Pattern.matches(PATTERN, hTransmision);  

		if(patternOK)
			this.hTransmision = hTransmision;
		else
			throw new IllegalArgumentException("Wrong expression 'hTransmision' (" + hTransmision +  ") in Contingencia.identificacion.sethTransmision()" + "\n");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

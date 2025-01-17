package org.shw.lsv.einvoice.anulacionv2;
import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * Just for class Anulacion
 */
public class IdentificacionAnulacion {

	static final int VERSION  = 2;
	int version;
	String ambiente;
	String codigoGeneracion;
	String fecAnula;
	String horAnula; 

	/**
	 * No parameters
	 */
	public IdentificacionAnulacion() {
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
			throw new IllegalArgumentException("Wrong parameter 'ambiente' (" + ambiente +  ") in Anulacion.identificacion.setAmbiente()" + "\n");
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
			throw new IllegalArgumentException("Wrong expression 'codigoGeneracion' (" + codigoGeneracion +  ") in Anulacion.identificacion.setCodigoGeneracion()" + "\n");
	}



	/**
	 * @return the fecAnula
	 */
	public String getFecAnula() {
		return fecAnula;
	}


	/**
	 * @param fecAnula the voiding date to set<br>
	 * null not allowed
	 */
	public void setFecAnula(String fecAnula) {
		if(fecAnula!=null)
			this.fecAnula = fecAnula;
		else
			throw new IllegalArgumentException("Wrong parameter 'fecAnula' (" + fecAnula +  ") in Anulacion.Identificacion.setFecEmi()" + "\n");
	}


	/**
	 * @return the horAnula
	 */
	public String getHorAnula() {
		return horAnula;
	}


	/**
	 * @param horAnula the voiding hour to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]?$"
	 */
	public void setHorAnula(String horAnula) {
		final String PATTERN = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]?$";
		boolean patternOK = (horAnula!=null) && Pattern.matches(PATTERN, horAnula);  

		if(patternOK)
			this.horAnula = horAnula;
		else
			throw new IllegalArgumentException("Wrong expression 'horAnula' (" + horAnula +  ") in Anulacion.identificacion.setHorAnula()" + "\n");
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

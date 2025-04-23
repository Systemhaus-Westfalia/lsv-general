/**
 * 
 */
package org.shw.lsv.einvoice.contingencia;


import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * 
 */
public class DetalleDTEItemContingencia {	
	int    noItem;
	String codigoGeneracion;
	String tipoDoc;

    
    
    /**
	 * Constructor without parameters. 
	 */
	public DetalleDTEItemContingencia() {
		
	}


	/**
	 * @param noItem
	 */
	public DetalleDTEItemContingencia(int noItem, String codigoGeneracion,String tipoDoc) {
		this.noItem           = noItem;
		this.codigoGeneracion =  codigoGeneracion;
		this.tipoDoc          =  tipoDoc;
	}


	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}


	/**
	 * @return the numItem
	 */
	public int getNoItem() {
		return noItem;
	}


	/**
	 * @param noItem the numItem to set<br>
	 * The parameter is validated.<br>
	 * "minimum" : 1, "maximum" : 1000
	 */
	public void setNoItem(int noItem) {
		final int MINIMUM = 1;
		final int MAXIMUM = 1000;
		
		if(noItem>=MINIMUM && noItem<=MAXIMUM)
			this.noItem = noItem;
		else
	        throw new IllegalArgumentException("Wrong parameter 'noItem' (" + noItem +  ")  in Contingencia.detalleDTE.setNoItem()" + "\n");
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
			throw new IllegalArgumentException("Wrong expression 'codigoGeneracion' (" + codigoGeneracion +  ")  in Contingencia.detalleDTE.setCodigoGeneracion()" + "\n");
	}


	/**
	 * @return the tipoDoc
	 */
	public String getTipoDoc() {
		return tipoDoc;
	}


	/**
	 * @param tipoDoc the tipoDoc to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^0[1-9]|1[0-5]$"
	 */
	public void setTipoDoc(String tipoDoc) {
		final String PATTERN = "^0[1-9]|1[0-5]$";
		boolean patternOK = (tipoDoc!=null) && Pattern.matches(PATTERN, tipoDoc);  

		if(patternOK)
			this.tipoDoc = tipoDoc;
		else
			throw new IllegalArgumentException("Wrong expression 'tipoDoc' (" + tipoDoc +  ")  in Contingencia.detalleDTE.setTipoDoc()" + "\n");
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

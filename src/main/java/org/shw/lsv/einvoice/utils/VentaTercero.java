/**
 * 
 */
package org.shw.lsv.einvoice.utils;

import java.util.regex.Pattern;

/**
 * 
 */
public abstract class VentaTercero {

	public static final String VALIDATION_RESULT_OK = "OK";
	String nit;
	String nombre;
	
	/**
	 * No parameters
	 */
	public VentaTercero() {
	}
	
	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {		
		return VALIDATION_RESULT_OK;
	}

	
	/**
	 * @param nit
	 * @param nombre
	 */
	public VentaTercero(String nit, String nombre) {
		super();
		this.nit = nit;
		this.nombre = nombre;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 3, "maxLength" : 200
	 */
	public void setNombre(String nombre) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 200;
		int length = nombre==null?0:nombre.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.nombre = nombre;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombre' in (POJO).VentaTercero.setNombre()");
	}

	/**
	 * @return the nit
	 */
	public String getNit() {
		return nit;
	}

	/**
	 * @param nit the nit to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^([0-9]{14}|[0-9]{9})$"
	 */
	public void setNit(String nit) {
		final String PATTERN = "^([0-9]{14}|[0-9]{9})$";
		boolean patternOK = (nit!=null) && Pattern.matches(PATTERN, nit);  
		
		if(patternOK)
			this.nit = nit;
		else
	        throw new IllegalArgumentException("Wrong expression 'nit' in (POJO).VentaTercero.setNit()");
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

package org.shw.lsv.einvoice.feretornov1;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

public class CompraTerceroRetorno {

	String numDocumento = null; // null allowed
	String nombre = null;       // null allowed


	public CompraTerceroRetorno() {
	}

	/**
	 * @param numDocumento
	 * @param nombre
	 */
	public CompraTerceroRetorno(String numDocumento, String nombre) {
		this.numDocumento = numDocumento;
		this.nombre       = nombre;
	}

	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	public String getNumDocumento() {
		return numDocumento;
	}

	/**
	 * @param numDocumento the numDocumento to set<br>
	 * "minLength" : 1, "maxLength" : 20; null also allowed
	 */
	public void setNumDocumento(String numDocumento) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 20;
		int length = numDocumento == null ? 0 : numDocumento.length();

		if (numDocumento == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.numDocumento = numDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numDocumento' in EventoDeRetorno.CompraTercero.setNumDocumento()" + "\n");
	}

	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set<br>
	 * "minLength" : 1, "maxLength" : 250; null also allowed
	 */
	public void setNombre(String nombre) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 250;
		int length = nombre == null ? 0 : nombre.length();

		if (nombre == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.nombre = nombre;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombre' in EventoDeRetorno.CompraTercero.setNombre()" + "\n");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

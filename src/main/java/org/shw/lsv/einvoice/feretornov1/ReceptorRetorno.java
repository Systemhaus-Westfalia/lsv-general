package org.shw.lsv.einvoice.feretornov1;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * Maps to the "documento" section of the Evento de Retorno schema.
 * Describes the recipient/document identification (receptor).
 */
public class ReceptorRetorno {

	String tipoDocumento = null; // null allowed
	String numDocumento = null;  // null allowed
	String nombre = null;        // null allowed
	String codPais = null;       // null allowed
	String nombrePais = null;    // null allowed
	String telefono = null;      // null allowed
	String correo = null;        // null allowed


	/**
	 * No parameters
	 */
	public ReceptorRetorno() {
	}

	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento the tipoDocumento to set; null also allowed
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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
	        throw new IllegalArgumentException("Wrong parameter 'numDocumento' in EventoDeRetorno.Documento.setNumDocumento()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'nombre' in EventoDeRetorno.Documento.setNombre()" + "\n");
	}

	public String getCodPais() {
		return codPais;
	}

	/**
	 * @param codPais the codPais to set; null also allowed
	 */
	public void setCodPais(String codPais) {
		this.codPais = codPais;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	/**
	 * @param nombrePais the nombrePais to set<br>
	 * "minLength" : 3, "maxLength" : 50; null also allowed
	 */
	public void setNombrePais(String nombrePais) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 50;
		int length = nombrePais == null ? 0 : nombrePais.length();

		if (nombrePais == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.nombrePais = nombrePais;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombrePais' in EventoDeRetorno.Documento.setNombrePais()" + "\n");
	}

	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set<br>
	 * "minLength" : 8, "maxLength" : 30; null also allowed
	 */
	public void setTelefono(String telefono) {
		final int MINLENGTH = 8;
		final int MAXLENGTH = 30;
		int length = telefono == null ? 0 : telefono.length();

		if (telefono == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.telefono = telefono;
		else
	        throw new IllegalArgumentException("Wrong parameter 'telefono' in EventoDeRetorno.Documento.setTelefono()" + "\n");
	}

	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set<br>
	 * "minLength" : 1, "maxLength" : 100; null also allowed
	 */
	public void setCorreo(String correo) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 100;
		int length = correo == null ? 0 : correo.length();

		if (correo == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.correo = correo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'correo' in EventoDeRetorno.Documento.setCorreo()" + "\n");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

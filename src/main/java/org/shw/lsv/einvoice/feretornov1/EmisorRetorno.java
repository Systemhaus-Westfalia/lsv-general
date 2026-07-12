package org.shw.lsv.einvoice.feretornov1;

import org.shw.lsv.einvoice.utils.Direccion;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

public class EmisorRetorno {

	String nit;
	String nrc = null;             // null allowed
	String nombre;
	String codActividad;
	String descActividad;
	String nombreComercial = null; // null allowed
    Direccion direccion;
    String telefono;
    String correo;


	public EmisorRetorno() {
		this.direccion = new Direccion();
	}

	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	public String getNit() {
		return nit;
	}

	/**
	 * @param nit the nit to set — no pattern constraint in v4 schema
	 */
	public void setNit(String nit) {
		if (nit != null)
			this.nit = nit;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nit' in NotaDeCredito.Emisor.setNit()" + "\n");
	}

	public String getNrc() {
		return nrc;
	}

	/**
	 * @param nrc the nrc to set<br>
	 * "minLength" : 2, "maxLength" : 8; null also allowed
	 */
	public void setNrc(String nrc) {
		final int MINLENGTH = 2;
		final int MAXLENGTH = 8;
		int length = nrc == null ? 0 : nrc.length();

		if ((length >= MINLENGTH && length <= MAXLENGTH) || nrc == null)
			this.nrc = nrc;
		else
	        throw new IllegalArgumentException("Wrong expression 'nrc' in NotaDeCredito.Emisor.setNrc()" + "\n");
	}

	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set<br>
	 * "minLength" : 1, "maxLength" : 250
	 */
	public void setNombre(String nombre) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 250;
		int length = nombre == null ? 0 : nombre.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.nombre = nombre;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombre' in NotaDeCredito.Emisor.setNombre()" + "\n");
	}

	public String getCodActividad() {
		return codActividad;
	}

	/**
	 * @param codActividad the codActividad to set<br>
	 * "minLength" : 5, "maxLength" : 6
	 */
	public void setCodActividad(String codActividad) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 6;
		int length = codActividad == null ? 0 : codActividad.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.codActividad = codActividad;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codActividad' in NotaDeCredito.Emisor.setCodActividad()" + "\n");
	}

	public String getDescActividad() {
		return descActividad;
	}

	/**
	 * @param descActividad the descActividad to set<br>
	 * "minLength" : 5, "maxLength" : 150
	 */
	public void setDescActividad(String descActividad) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 150;
		int length = descActividad == null ? 0 : descActividad.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.descActividad = descActividad;
		else
	        throw new IllegalArgumentException("Wrong parameter 'descActividad' in NotaDeCredito.Emisor.setDescActividad()" + "\n");
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	/**
	 * @param nombreComercial the nombreComercial to set<br>
	 * "minLength" : 1, "maxLength" : 150; null also possible
	 */
	public void setNombreComercial(String nombreComercial) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 150;
		int length = nombreComercial == null ? 0 : nombreComercial.length();

		if ((length >= MINLENGTH && length <= MAXLENGTH) || nombreComercial == null)
			this.nombreComercial = nombreComercial;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombreComercial' in NotaDeCredito.Emisor.setNombreComercial()" + "\n");
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set<br>
	 * "minLength" : 8, "maxLength" : 30
	 */
	public void setTelefono(String telefono) {
		final int MINLENGTH = 8;
		final int MAXLENGTH = 30;
		int length = telefono == null ? 0 : telefono.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.telefono = telefono;
		else
	        throw new IllegalArgumentException("Wrong parameter 'telefono' in NotaDeCredito.Emisor.setTelefono()" + "\n");
	}

	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set<br>
	 * "minLength" : 1, "maxLength" : 100
	 */
	public void setCorreo(String correo) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 100;
		int length = correo == null ? 0 : correo.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.correo = correo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'correo' in NotaDeCredito.Emisor.setCorreo()" + "\n");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

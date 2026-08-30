/**
 * 
 */
package org.shw.lsv.einvoice.fefexfacturaexportacionv1;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * 
 */
public class ReceptorFacturaExportacion {
	static final String VALIDATION_CODPAIS_IS_NULL             = "Documento: Factura de Exportacion, clase: Receptor. Validacion fallo: valor de 'codPais' no debe ser = null";
	static final String VALIDATION_NUMDOCUMENTO_PATTERN_FAILED = "Documento: Factura de Exportacion, clase: Receptor. Validacion fallo: valor de 'numDocumento' no corresponde a patron";

	String nombre;
	String tipoDocumento;
	String numDocumento;
	String nombreComercial = null; // null possible
	String codPais;
	String nombrePais;
	String complemento;
	int tipoPersona;
	String descActividad;
	// Direccion direccion;  // Direccion not in schema
	String telefono = null; // null possible
	String correo = null; // null possible

	/**
	 * No parameters
	 */
	public ReceptorFacturaExportacion() {
	}

	/**
	 * Validate the Schema conditions
	 */

	public String validateValues() {
		String pattern;
		boolean patternOK;

		// In schema: "pattern" : "^([0-9]{14}|[0-9]{9})$"
		if (getTipoDocumento() != null && getTipoDocumento().equals("36")) {
			pattern = "^([0-9]{14}|[0-9]{9})$";
			patternOK = (getNumDocumento() != null) && Pattern.matches(pattern, getNumDocumento());
			if (!patternOK)
				return VALIDATION_NUMDOCUMENTO_PATTERN_FAILED;
		}

		// In schema: "pattern" : "^[0-9]{8}-[0-9]{1}$"
		if (getTipoDocumento() != null && getTipoDocumento().equals("13")) {
			pattern = "^[0-9]{8}-[0-9]{1}$";
			patternOK = (getNumDocumento() != null) && Pattern.matches(pattern, getNumDocumento());
			if (!patternOK)
				return VALIDATION_NUMDOCUMENTO_PATTERN_FAILED;
		}
		

		// codPais must have a value
		if (getCodPais()== null) {
			return VALIDATION_CODPAIS_IS_NULL;
		}
		
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	/**
	 * @return the tipoDocumento
	 */

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento the tipoDocumento to set<br>
	 *                      The parameter is validated.<br>
	 *                      "enum" : [null,"36","13","02","03","37"]
	 */

	public void setTipoDocumento(String tipoDocumento) {
		String[] validTipoDocumento = { "36", "13", "02", "03", "37" };

		if (Arrays.stream(validTipoDocumento).anyMatch(tipoDocumento::equals))
			this.tipoDocumento = tipoDocumento;
		else
			throw new IllegalArgumentException(
					"Wrong parameter 'tipoDocumento' in FacturaExportacion.Receptor.setTipoDocumento()" + "\n");
	}

	/**
	 * @return the numDocumento
	 */

	public String getNumDocumento() {
		return numDocumento;
	}

	/**
	 * @param numDocumento the numDocumento to set<br>
	 *                     The parameter is validated.<br>
	 *                     "minLength" : 3, "maxLength" : 20
	 */

	public void setNumDocumento(String numDocumento) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 20;
		int length = numDocumento == null ? 0 : numDocumento.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.numDocumento = numDocumento;
		else
			throw new IllegalArgumentException(
					"Wrong parameter 'numDocumento' in FacturaExportacion.Receptor.setNumDocumento()" + "\n");
	}

	/**
	 * @return the nombreComercial
	 */

	public String getNombreComercial() {
		return nombreComercial;
	}

	/**
	 * @param nombreComercial the nombreComercial to set<br>
	 *                        The parameter is validated.<br>
	 *                        "minLength" : 1, "maxLength" : 150
	 */

	public void setNombreComercial(String nombreComercial) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 150;
		int length = nombreComercial == null ? 0 : nombreComercial.length();

		if ((length >= MINLENGTH && length <= MAXLENGTH) || (nombreComercial == null))
			this.nombreComercial = nombreComercial;
		else
			throw new IllegalArgumentException(
					"Wrong parameter 'nombreComercial' in FacturaExportacion.Receptor.setNombreComercial()" + "\n");
	}

	/**
	 * @return the codPais
	 */

	public String getCodPais() {
		return codPais;
	}

	/**
	 * @param codPais the codPais to sett<br>
	 *                The parameter is validated.<br>
	 *                "enum" : ["9320","9539","9565","9905",....]
	 */

	public void setCodPais(String codPais) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 2;
		int length = codPais == null ? 0 : codPais.length();

		if ((length >= MINLENGTH && length <= MAXLENGTH) || (nombreComercial == null))
			this.codPais = codPais;
		else
					throw new IllegalArgumentException("Wrong parameter 'codPais' in FacturaExportacion.Receptor.setCodPais()" + "\n");

	}

	/**
	 * @return the nombrePais
	 */

	public String getNombrePais() {
		return nombrePais;
	}

	/**
	 * @param nombrePais the nombrePais to set<br>
	 *                   The parameter is validated.<br>
	 *                   "minLength" : 3, "maxLength" : 50
	 */

	public void setNombrePais(String nombrePais) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 50;
		int length = nombrePais == null ? 0 : nombrePais.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.nombrePais = nombrePais;
		else
			throw new IllegalArgumentException(
					"Wrong parameter 'nombrePais' in FacturaExportacion.Receptor.setNombrePais()" + "\n");
	}

	/**
	 * @return the complemento
	 */

	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemento the complemento to set<br>
	 *                    The parameter is validated.<br>
	 *                    "minLength" : 5, "maxLength" : 300
	 */

	public void setComplemento(String complemento) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 300;
		int length = complemento == null ? 0 : complemento.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.complemento = complemento;
		else
			throw new IllegalArgumentException(
					"Wrong parameter 'complemento' in FacturaExportacion.Receptor.setComplemento()" + "\n");
	}

	/**
	 * @return the tipoPersona
	 */

	public int getTipoPersona() {
		return tipoPersona;
	}

	/**
	 * @param tipoPersona the tipoPersona to set<br>
	 *                    The parameter is validated.<br>
	 *                    "enum" : [1,2]
	 */

	public void setTipoPersona(int tipoPersona) {
		if (tipoPersona == 1 || tipoPersona == 2)
			this.tipoPersona = tipoPersona;
		else
			throw new IllegalArgumentException(
					"Wrong parameter 'tipoPersona' in FacturaExportacion.Receptor.setTipoPersona()" + "\n");
	}

	/**
	 * @return the nombre
	 */

	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set<br>
	 *               The parameter is validated.<br>
	 *               "minLength" : 1, "maxLength" : 250
	 */

	public void setNombre(String nombre) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 250;
		int length = nombre == null ? 0 : nombre.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.nombre = nombre;
		else
			throw new IllegalArgumentException("Wrong parameter 'nombre' in FacturaExportacion.Receptor.setNombre()" + "\n");
	}

	/**
	 * @return the descActividad
	 */

	public String getDescActividad() {
		return descActividad;
	}

	/**
	 * @param descActividad the descActividad to set<br>
	 *                      The parameter is validated.<br>
	 *                      "minLength" : 5, "maxLength" : 150
	 */

	public void setDescActividad(String descActividad) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 150;
		int length = descActividad == null ? 0 : descActividad.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.descActividad = descActividad;
		else
			throw new IllegalArgumentException(
					"Wrong parameter 'descActividad' in FacturaExportacion.Receptor.setDescActividad()" + "\n");
	}
	/*
		*//**
			 * @return the direccion
			 */
	/*
	 * public Direccion getDireccion() { return direccion; }
	 * 
	 *//**
		 * @param direccion the direccion to set
		 *//*
			 * public void setDireccion(Direccion direccion) { this.direccion = direccion; }
			 */

	/**
	 * @return the telefono
	 */

	public String getTelefono() {
		return telefono;
	}

	/*
	 * /**
	 * 
	 * @param telefono the telefono to set<br> The parameter is validated.<br>
	 * "minLength" : 8, "maxLength" : 50; null also possible
	 */

	public void setTelefono(String telefono) {
		final int MINLENGTH = 8;
		final int MAXLENGTH = 50;
		int length = telefono == null ? 0 : telefono.length();

		if ((length >= MINLENGTH && length <= MAXLENGTH) || (telefono == null))
			this.telefono = telefono;
		else
			throw new IllegalArgumentException(
					"Wrong parameter 'telefono' in FacturaExportacion.Receptor.setTelefono()" + "\n");
	}

	/**
	 * @return the correo
	 */

	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set<br>
	 *               The parameter is validated.<br>
	 *               "minLength" : 3, "maxLength" : 100; null also possible
	 */

	public void setCorreo(String correo) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 100;
		int length = correo == null ? 0 : correo.length();

		if ((length >= MINLENGTH && length <= MAXLENGTH) || (correo == null))
			this.correo = correo;
		else
			throw new IllegalArgumentException("Wrong parameter 'correo' in FacturaExportacion.Receptor.setCorreo()" + "\n");
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

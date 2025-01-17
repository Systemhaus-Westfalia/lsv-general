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
		String[] validPais = { "9320", "9539", "9565", "9905", "9999", "9303", "9306", "9309", "9310", "9315", "9317",
				"9318", "9319", "9324", "9327", "9330", "9333", "9336", "9339", "9342", "9345", "9348", "9349", "9350",
				"9354", "9357", "9360", "9363", "9366", "9372", "9374", "9375", "9377", "9378", "9381", "9384", "9387",
				"9390", "9393", "9394", "9396", "9399", "9402", "9405", "9408", "9411", "9414", "9417", "9420", "9423",
				"9426", "9432", "9435", "9438", "9440", "9441", "9444", "9446", "9447", "9450", "9453", "9456", "9459",
				"9462", "9465", "9468", "9471", "9474", "9477", "9480", "9481", "9483", "9486", "9487", "9495", "9498",
				"9501", "9504", "9507", "9513", "9516", "9519", "9522", "9525", "9526", "9528", "9531", "9534", "9537",
				"9540", "9543", "9544", "9546", "9549", "9552", "9555", "9558", "9561", "9564", "9567", "9570", "9573",
				"9576", "9577", "9582", "9585", "9591", "9594", "9597", "9600", "9601", "9603", "9606", "9609", "9611",
				"9612", "9615", "9618", "9621", "9624", "9627", "9633", "9636", "9638", "9639", "9642", "9645", "9648",
				"9651", "9660", "9663", "9666", "9669", "9672", "9675", "9677", "9678", "9679", "9680", "9681", "9682",
				"9683", "9684", "9687", "9690", "9691", "9693", "9696", "9699", "9702", "9705", "9706", "9707", "9708",
				"9714", "9717", "9720", "9722", "9723", "9725", "9726", "9727", "9729", "9732", "9735", "9738", "9739",
				"9740", "9741", "9744", "9747", "9750", "9756", "9758", "9759", "9760", "9850", "9862", "9863", "9865",
				"9886", "9898", "9899", "9897", "9887", "9571", "9300", "9369", "9439", "9510", "9579", "9654", "9711",
				"9736", "9737", "9640", "9641", "9673", "9472", "9311", "9733", "9541", "9746", "9551", "9451", "9338",
				"9353", "9482", "9494", "9524", "9304", "9332", "9454", "9457", "9489", "9491", "9492", "9523", "9530",
				"9532", "9535", "9542", "9547", "9548", "9574", "9598", "9602", "9607", "9608", "9623", "9652", "9692",
				"9709", "9712", "9716", "9718", "9719", "9751", "9452", "9901", "9902", "9903", "9664", "9415", "9904",
				"9514", "9906", "9359", "9493", "9521", "9533", "9538", "9689", "9713", "9449", "9888", "9490", "9527",
				"9529", "9536", "9545", "9568", "9610", "9622", "9643", "9667", "9676", "9685", "9686", "9688", "9715",
				"9900", "9371", "9376", "9907" };

		if (Arrays.stream(validPais).anyMatch(codPais::equals))
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

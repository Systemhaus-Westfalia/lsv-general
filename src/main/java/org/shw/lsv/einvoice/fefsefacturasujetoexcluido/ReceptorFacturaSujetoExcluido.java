/**
 * 
 */
package org.shw.lsv.einvoice.fefsefacturasujetoexcluido;

import org.shw.lsv.einvoice.utils.Direccion;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * 
 */
public class ReceptorFacturaSujetoExcluido {

	String tipoDocumento=null; // null possible
	String numDocumento=null; // null possible
	String nombre=null; // null possible
	String codActividad=null; // null possible
	String descActividad=null; // null possible
	Direccion direccion;
    String telefono=null; // null possible
    String correo=null;  // null possible

    
	
	/**
	 * No parameters
	 */
	public ReceptorFacturaSujetoExcluido() {
		this.direccion = new Direccion();
	}


	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
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
	 * "minLength" : 1, "maxLength" : 250
	 */

	public void setNombre(String nombre) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 250;
		int length = nombre==null?0:nombre.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.nombre = nombre;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombre' (" + nombre +  ") in FacturaSujetoExcluido.Receptor.setNombre()" + "\n");
	}

	/**
	 * @return the codActividad
	 */

	public String getCodActividad() {
		return codActividad;
	}

	/**
	 * @param codActividad the codActividad to set<br>
	 * "type" : ["string", "null"]
	 */

	public void setCodActividad(String codActividad) {
		this.codActividad = codActividad;
	}

	/**
	 * @return the descActividad
	 */

	public String getDescActividad() {
		return descActividad;
	}

	/**
	 * @param descActividad the descActividad to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 5, "maxLength" : 150
	 */

	public void setDescActividad(String descActividad) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 150;
		int length = descActividad==null?0:descActividad.length();

		if(descActividad==null || (length>=MINLENGTH && length<=MAXLENGTH))
			this.descActividad = descActividad;
		else
	        throw new IllegalArgumentException("Wrong parameter 'descActividad' (" + descActividad +  ") in FacturaSujetoExcluido.Receptor.setDescActividad()" + "\n");
	}


	/**
	 * @return the direccion
	 */

	public Direccion getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the telefono
	 */

	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 8, "maxLength" : 30
	 */

	public void setTelefono(String telefono) {
		final int MINLENGTH = 8;
		final int MAXLENGTH = 30;
		int length = telefono==null?0:telefono.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.telefono = telefono;
		else
	        throw new IllegalArgumentException("Wrong parameter 'telefono' (" + telefono +  ") in FacturaSujetoExcluido.Receptor.setTelefono()" + "\n");
	}

	/**
	 * @return the correo
	 */

	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 6, "maxLength" : 100
	 */

	public void setCorreo(String correo) {
		final int MINLENGTH = 6;
		final int MAXLENGTH = 100;
		int length = correo==null?0:correo.length();

		if(correo==null || (length>=MINLENGTH && length<=MAXLENGTH))
			this.correo = correo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'correo' (" + correo +  ") in FacturaSujetoExcluido.Receptor.setCorreo()" + "\n");
	}


	
	public String getTipoDocumento() {
		return tipoDocumento;
	}


	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}


	public String getNumDocumento() {
		return numDocumento;
	}

	/**
	 * @param numDocumento the numDocumento to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 20
	 */
	public void setNumDocumento(String numDocumento) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 20;
		int length = numDocumento==null?0:numDocumento.length();

		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.numDocumento = numDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numDocumento' (" + numDocumento +  ") in FacturaSujetoExcluido.Receptor.setNumDocumento()" + "\n");
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

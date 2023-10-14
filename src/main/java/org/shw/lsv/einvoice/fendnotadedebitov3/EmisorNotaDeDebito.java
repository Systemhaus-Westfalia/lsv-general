package org.shw.lsv.einvoice.fendnotadedebitov3;

import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.Direccion;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

public class EmisorNotaDeDebito {

	String nit;
	String nrc;
	String nombre;
	String codActividad;
	String descActividad;
	String nombreComercial=null;  // null possible
	String tipoEstablecimiento;
    Direccion direccion;
    String telefono=null;  // null possible
    String correo;
    
    
	/**
	 * 
	 */
	public EmisorNotaDeDebito() {
		this.direccion = new Direccion();
	}


	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
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
	        throw new IllegalArgumentException("Wrong expression 'nit' in NotaDeDebito.Emisor.setNit()");
	}


	/**
	 * @return the nrc
	 */
	public String getNrc() {
		return nrc;
	}


	/**
	 * @param nrc the nrc to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^[0-9]{1,8}$"
	 */
	public void setNrc(String nrc) {
		final String PATTERN = "^[0-9]{1,8}$";
		boolean patternOK = (nrc!=null) && Pattern.matches(PATTERN, nrc);  
		
		if(patternOK)
			this.nrc = nrc;
		else
	        throw new IllegalArgumentException("Wrong expression 'nrc' in NotaDeDebito.Emisor.setNrc()");
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
	        throw new IllegalArgumentException("Wrong parameter 'nombre' in NotaDeDebito.Emisor.setNombre()");
	}


	/**
	 * @return the codActividad
	 */
	public String getCodActividad() {
		return codActividad;
	}


	/**
	 * @param codActividad the codActividad to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^[0-9]{2,6}$"
	 */
	public void setCodActividad(String codActividad) {
		final String PATTERN = "^[0-9]{2,6}$";
		boolean patternOK = (codActividad!=null) && Pattern.matches(PATTERN, codActividad);  
		
		if(patternOK)
			this.codActividad = codActividad;
		else
	        throw new IllegalArgumentException("Wrong expression 'codActividad' in NotaDeDebito.Emisor.setCodActividad()");
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
	 * "minLength" : 1, "maxLength" : 150
	 */
	public void setDescActividad(String descActividad) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 150;
		int length = descActividad==null?0:descActividad.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.descActividad = descActividad;
		else
	        throw new IllegalArgumentException("Wrong parameter 'descActividad' in NotaDeDebito.Emisor.setDescActividad()");
	}


	/**
	 * @return the nombreComercial
	 */
	public String getNombreComercial() {
		return nombreComercial;
	}


	/**
	 * @param nombreComercial the nombreComercial to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 150; null also possible
	 */
	public void setNombreComercial(String nombreComercial) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 150;
		int length = nombreComercial==null?0:nombreComercial.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (nombreComercial==null) )
			this.nombreComercial = nombreComercial;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombreComercial' in NotaDeDebito.Emisor.setNombreComercial()");
	}


	/**
	 * @return the tipoEstablecimiento
	 */
	public String getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}


	/**
	 * @param tipoEstablecimiento the tipoEstablecimiento to set<br>
	 * The parameter is validated.<br>
	 * "enum" : ["01", "02", "04", "07", "20"]
	 */
	public void setTipoEstablecimiento(String tipoEstablecimiento) {
		if (tipoEstablecimiento.compareTo("01")==0 || tipoEstablecimiento.compareTo("02")==0 || tipoEstablecimiento.compareTo("04")==0 || tipoEstablecimiento.compareTo("07")==0 || tipoEstablecimiento.compareTo("20")==0)
			this.tipoEstablecimiento = tipoEstablecimiento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoEstablecimiento' in NotaDeDebito.Emisor.setTipoEstablecimiento()");
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
	 * "minLength" : 8, "maxLength" : 30; null also possible
	 */
	public void setTelefono(String telefono) {
		final int MINLENGTH = 8;
		final int MAXLENGTH = 30;
		int length = telefono==null?0:telefono.length();

		if( (length>=MINLENGTH && length<=MAXLENGTH) || (telefono==null) )
			this.telefono = telefono;
		else
	        throw new IllegalArgumentException("Wrong parameter 'telefono' in NotaDeDebito.Emisor.setTelefono()");
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
	 * "minLength" : 3, "maxLength" : 100
	 */
	public void setCorreo(String correo) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 100;
		int length = correo==null?0:correo.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.correo = correo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'correo' in NotaDeDebito.Emisor.setCorreo()");
	}

    

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}

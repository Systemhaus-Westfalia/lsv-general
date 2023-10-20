package org.shw.lsv.einvoice.fefcfacturaelectronicav1;

import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.Direccion;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

public class EmisorFactura {

	String nit;
	String nrc;
	String nombre;
	String codActividad;
	String descActividad;
	String nombreComercial=null;  // null possible
	String tipoEstablecimiento;
    Direccion direccion;
    String telefono;
    String correo;
    String codEstableMH;
    String codEstable;
    String codPuntoVentaMH;
    String codPuntoVenta;
    
    
	/**
	 * 
	 */
	public EmisorFactura() {
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
	        throw new IllegalArgumentException("Wrong expression 'nit' in Factura.Emisor.setNit()" + "\n");
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
	        throw new IllegalArgumentException("Wrong expression 'nrc' in Factura.Emisor.setNrc()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'nombre' in Factura.Emisor.setNombre()" + "\n");
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
	        throw new IllegalArgumentException("Wrong expression 'codActividad' in Factura.Emisor.setCodActividad()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'descActividad' in Factura.Emisor.setDescActividad()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'nombreComercial' in Factura.Emisor.setNombreComercial()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'tipoEstablecimiento' in Factura.Emisor.setTipoEstablecimiento()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'telefono' in Factura.Emisor.setTelefono()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'correo' in Factura.Emisor.setCorreo()" + "\n");
	}


	/**
	 * @return the codEstableMH
	 */
	public String getCodEstableMH() {
		return codEstableMH;
	}


	/**
	 * @param codEstableMH the codEstableMH to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 4, "maxLength" : 4, null also possible
	 */
	public void setCodEstableMH(String codEstableMH) {
		final int MINLENGTH = 4;
		final int MAXLENGTH = 4;
		int length = codEstableMH==null?0:codEstableMH.length();

		if( (length>=MINLENGTH && length<=MAXLENGTH) || (codEstableMH==null) )
			this.codEstableMH = codEstableMH;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codEstableMH' in Factura.Emisor.setCodEstableMH()" + "\n");
	}


	/**
	 * @return the codEstable
	 */
	public String getCodEstable() {
		return codEstable;
	}


	/**
	 * @param codEstable the codEstable to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 10, null also possible
	 */
	public void setCodEstable(String codEstable) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 10;
		int length = codEstable==null?0:codEstable.length();

		if( (length>=MINLENGTH && length<=MAXLENGTH) || (codEstable==null) )
			this.codEstable = codEstable;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codEstable' in Factura.Emisor.setCodEstable()" + "\n");
	}


	/**
	 * @return the codPuntoVentaMH
	 */
	public String getCodPuntoVentaMH() {
		return codPuntoVentaMH;
	}


	/**
	 * @param codPuntoVentaMH the codPuntoVentaMH to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 4, "maxLength" : 4, null also possible
	 */
	public void setCodPuntoVentaMH(String codPuntoVentaMH) {
		final int MINLENGTH = 4;
		final int MAXLENGTH = 4;
		int length = codPuntoVentaMH==null?0:codPuntoVentaMH.length();

		if( (length>=MINLENGTH && length<=MAXLENGTH) || (codPuntoVentaMH==null) )
			this.codPuntoVentaMH = codPuntoVentaMH;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codPuntoVentaMH' in Factura.Emisor.setCodPuntoVentaMH()" + "\n");
	}


	/**
	 * @return the codPuntoVenta
	 */
	public String getCodPuntoVenta() {
		return codPuntoVenta;
	}


	/**
	 * @param codPuntoVenta the codPuntoVenta to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 15, null also possible
	 */
	public void setCodPuntoVenta(String codPuntoVenta) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 15;
		int length = codPuntoVenta==null?0:codPuntoVenta.length();

		if( (length>=MINLENGTH && length<=MAXLENGTH) || (codPuntoVenta==null) )
			this.codPuntoVenta = codPuntoVenta;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codPuntoVenta' in Factura.Emisor.setCodPuntoVenta()" + "\n");
	}

    

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}

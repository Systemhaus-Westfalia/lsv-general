package org.shw.lsv.einvoice.fefsefacturasujetoexcluido;

import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.Direccion;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

public class EmisorFacturaSujetoExcluido {

	String nit;
	String nrc;
	String nombre;
	String codActividad;
	String descActividad;
    Direccion direccion;
    String telefono;

    String codEstableMH;
    String codEstable;
    String codPuntoVentaMH;
    String codPuntoVenta;
    String correo;
    
    
    
    
	/**
	 * 
	 */
	public EmisorFacturaSujetoExcluido() {
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
	        throw new IllegalArgumentException("Wrong expression 'nit' (" + nit +  ") in FacturaSujetoExcluido.Emisor.setNit()" + "\n");
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
	        throw new IllegalArgumentException("Wrong expression 'nrc' (" + nrc +  ") in FacturaSujetoExcluido.Emisor.setNrc()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'nombre' (" + nombre +  ") in FacturaSujetoExcluido.Emisor.setNombre()" + "\n");
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
	        throw new IllegalArgumentException("Wrong expression 'codActividad' (" + codActividad +  ") in FacturaSujetoExcluido.Emisor.setCodActividad()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'descActividad' (" + descActividad +  ") in FacturaSujetoExcluido.Emisor.setDescActividad()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'telefono' (" + telefono +  ") in FacturaSujetoExcluido.Emisor.setTelefono()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'correo' (" + correo +  ") in FacturaSujetoExcluido.Emisor.setCorreo()" + "\n");
	}

    

	public String getCodEstableMH() {
		return codEstableMH;
	}


	public void setCodEstableMH(String codEstableMH) {
		this.codEstableMH = codEstableMH;
	}


	public String getCodEstable() {
		return codEstable;
	}


	public void setCodEstable(String codEstable) {
		this.codEstable = codEstable;
	}


	public String getCodPuntoVentaMH() {
		return codPuntoVentaMH;
	}


	public void setCodPuntoVentaMH(String codPuntoVentaMH) {
		this.codPuntoVentaMH = codPuntoVentaMH;
	}


	public String getCodPuntoVenta() {
		return codPuntoVenta;
	}


	public void setCodPuntoVenta(String codPuntoVenta) {
		this.codPuntoVenta = codPuntoVenta;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}

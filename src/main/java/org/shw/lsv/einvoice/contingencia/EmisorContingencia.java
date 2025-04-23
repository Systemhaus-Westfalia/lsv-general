package org.shw.lsv.einvoice.contingencia;

import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * Just for class Contingencia
 */
public class EmisorContingencia {

	String nit;
	String nombre;
	String nombreResponsable;
	String tipoDocResponsable;
	String numeroDocResponsable;
	String tipoEstablecimiento;
    String codEstableMH=null;       // null possible
    String codPuntoVenta=null;      // null possible
    String telefono;
    String correo;
    
    
	/**
	 * 
	 */
	public EmisorContingencia() {
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
	        throw new IllegalArgumentException("Wrong expression 'nit' (" + nit +  ") in Contingencia.emisor.setNit()" + "\n");
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
	 * "minLength" : 3, "maxLength" : 250
	 */
	public void setNombre(String nombre) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 250;
		int length = nombre==null?0:nombre.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.nombre = nombre;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombre' (" + nombre +  ") in Contingencia.emisor.setNombre()" + "\n");
	}


	/**
	 * @return the nombreResponsable
	 */
	public String getNombreResponsable() {
		return nombreResponsable;
	}


	/**
	 * @param nombreResponsable the nombreResponsable to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 5, "maxLength" : 100; null also possible
	 */
	public void setNombreResponsable(String nombreResponsable) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 100;
		int length = nombreResponsable==null?0:nombreResponsable.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (nombreResponsable==null) )
			this.nombreResponsable = nombreResponsable;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombreResponsable' (" + nombreResponsable +  ")  in Contingencia.emisor.setNombreResponsable()" + "\n");
	}


	/**
	 * @return the tipoDocResponsable
	 */
	public String getTipoDocResponsable() {
		return tipoDocResponsable;
	}


	/**
	 * @param tipoDocResponsable the tipoDocResponsable to set<br>
	 * The parameter is validated.<br>
	 * "enum" : ["36", "13", "02", "03", "37"]
	 */
	public void setTipoDocResponsable(String tipoDocResponsable) {
		if (tipoDocResponsable.compareTo("36")==0 || tipoDocResponsable.compareTo("13")==0 || tipoDocResponsable.compareTo("02")==0 || tipoDocResponsable.compareTo("03")==0 || tipoDocResponsable.compareTo("37")==0)
			this.tipoDocResponsable = tipoDocResponsable;
		else
			throw new IllegalArgumentException("Wrong parameter 'tipoDocResponsable' (" + tipoDocResponsable +  ") in Contingencia.emisor.setTipoDocResponsable()" + "\n");
	}


	/**
	 * @return the numeroDocResponsable
	 */
	public String getNumeroDocResponsable() {
		return numeroDocResponsable;
	}


	/**
	 * @param numeroDocResponsable the numeroDocResponsable to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 5, "maxLength" : 25; null also possible
	 */
	public void setNumeroDocResponsable(String numeroDocResponsable) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 25;
		int length = numeroDocResponsable==null?0:numeroDocResponsable.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (nombreResponsable==null) )
			this.numeroDocResponsable = numeroDocResponsable;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numeroDocResponsable' (" + numeroDocResponsable +  ")  in Contingencia.emisor.setNumeroDocResponsable()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'tipoEstablecimiento' (" + tipoEstablecimiento +  ") in Contingencia.emisor.setTipoEstablecimiento()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'codEstableMH' (" + codEstableMH +  ") in Contingencia.emisor.setCodEstableMH()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'codPuntoVenta' (" + codPuntoVenta +  ") in Contingencia.emisor.setCodPuntoVenta()" + "\n");
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
	 * "minLength" : 8, "maxLength" : 30<br>
	 */
	public void setTelefono(String telefono) {
		final int MINLENGTH = 8;
		final int MAXLENGTH = 30;
		int length = telefono==null?0:telefono.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.telefono = telefono;
		else
	        throw new IllegalArgumentException("Wrong parameter 'telefono' (" + telefono +  ") in Contingencia.Emisor.setTelefono()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'correo' (" + correo +  ") in Contingencia.Emisor.setCorreo()" + "\n");
	}

	
	public static void main(String[] args) {
		throw new UnsupportedOperationException("In Document Contingencia calling the method Emisor.getNrc() is not allowed");
	}

}

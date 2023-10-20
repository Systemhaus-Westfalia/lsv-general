package org.shw.lsv.einvoice.anulacionv2;

import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * Just for class Anulacion
 */
public class EmisorAnulacion {

	String nit;
	String nombre;
	String tipoEstablecimiento;
	String nomEstablecimiento=null; // null permitted
    String telefono;
    String correo;
    String codEstableMH;
    String codEstable;
    String codPuntoVentaMH;
    String codPuntoVenta;
    
    
	/**
	 * 
	 */
	public EmisorAnulacion() {
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
	        throw new IllegalArgumentException("Wrong expression 'nit' in Anulacion.Emisor.setNit()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'nombre' in Anulacion.Emisor.setNombre()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'tipoEstablecimiento' in Anulacion.Emisor.setTipoEstablecimiento()" + "\n");
	}


	/**
	 * @return the nomEstablecimiento
	 */
	public String getNomEstablecimiento() {
		return nomEstablecimiento;
	}


	/**
	 * @param nomEstablecimiento the nomEstablecimiento to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 3, "maxLength" : 150; null also possible
	 */
	public void setNomEstablecimiento(String nomEstablecimiento) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 150;
		int length = nomEstablecimiento==null?0:nomEstablecimiento.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH)  || (nomEstablecimiento==null) )
			this.nomEstablecimiento = nomEstablecimiento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nomEstablecimiento' in Anulacion.Emisor.setNomEstablecimiento()" + "\n");
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
	 * "minLength" : 8, "maxLength" : 26<br>
	 * "pattern" : "^[0-9+;]{8,26}$"
	 */
	public void setTelefono(String telefono) {
		final int MINLENGTH = 8;
		final int MAXLENGTH = 26;
		int length = telefono==null?0:telefono.length();

		final String PATTERN = "^[0-9+;]{8,26}$";
		boolean patternOK = (telefono!=null) && Pattern.matches(PATTERN, telefono);  
		
		if(length>=MINLENGTH && length<=MAXLENGTH && patternOK)
			this.telefono = telefono;
		else
	        throw new IllegalArgumentException("Wrong parameter 'telefono' in Anulacion.Emisor.setTelefono()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'correo' in Anulacion.Emisor.setCorreo()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'codEstableMH' in Anulacion.Emisor.setCodEstableMH()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'codEstable' in Anulacion.Emisor.setCodEstable()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'codPuntoVentaMH' in Anulacion.Emisor.setCodPuntoVentaMH()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'codPuntoVenta' in Anulacion.Emisor.setCodPuntoVenta()" + "\n");
	}


	
	public static void main(String[] args) {
		throw new UnsupportedOperationException("In Document Anulacion calling the method Emisor.getNrc() is not allowed");
	}

}

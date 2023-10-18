package org.shw.lsv.einvoice.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Just for class Anulacion
 */
public abstract class Documento {
	static final String VALIDATION_RESULT_OK = "OK";

	String tipoDte;
	String codigoGeneracion;
	String selloRecibido;
	String numeroControl;
	String fecEmi;
	BigDecimal montoIva;
	String codigoGeneracionR;
	String tipoDocumento;
	String numDocumento;
	String nombre;
	String telefono;
	String correo;

    
	/**
	 * 
	 */
	public Documento() {
	}
    

	/**
	 * @return the tipoDte
	 */
	public String getTipoDte() {
		return tipoDte;
	}
	
	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		return VALIDATION_RESULT_OK;
	}


	/**
	 * @param tipoDte the tipoDte to set<br>
	 * The parameter is validated.<br>
	 * Specific values allowed according to schema: ["01", "03", "04", .....].
	 * Pattern in schema is "^0[0-9]|1[0-5]$", but this contradicts the enum.
	 * The enum was chosen for validation.
	 */
	public void setTipoDte(String tipoDte) {
		String[] validTipoDtes = { "01", "03", "04", "05", "06",
				         "07", "08", "09","10","11", "14","15" };		
		boolean isTipoDteValid = Arrays.stream(validTipoDtes).anyMatch(tipoDte::equals);
		
		if(isTipoDteValid)
			this.tipoDte = tipoDte;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoDte' in Anulacion.Documento.setTipoDte()");
	}




	/**
	 * @return the codigoGeneracion
	 */
	public String getCodigoGeneracion() {
		return codigoGeneracion;
	}


	/**
	 * @param codigoGeneracion the codigoGeneracion to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 36, "maxLength" : 36<br>
	 * "pattern" : "^[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}$"
	 */
	public void setCodigoGeneracion(String codigoGeneracion) {
		final int MINLENGTH = 36;
		final int MAXLENGTH = 36;
		int length = codigoGeneracion==null?0:codigoGeneracion.length();
		
		final String PATTERN = "^[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}$";
		boolean patternOK = (codigoGeneracion!=null) && Pattern.matches(PATTERN, codigoGeneracion);  
				
		if(length>=MINLENGTH && length<=MAXLENGTH && patternOK)
			this.codigoGeneracion = codigoGeneracion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codigoGeneracion' in Anulacion.Documento.setCodigoGeneracion()");
	}


	/**
	 * @return the selloRecibido
	 */
	public String getSelloRecibido() {
		return selloRecibido;
	}


	/**
	 * @param selloRecibido the selloRecibido to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 40, "maxLength" : 40<br>
	 * "pattern" : "^[A-Z0-9]{40}$"
	 */
	public void setSelloRecibido(String selloRecibido) {
		final int MINLENGTH = 40;
		final int MAXLENGTH = 40;
		int length = selloRecibido==null?0:selloRecibido.length();
		
		final String PATTERN = "^[A-Z0-9]{40}$";
		boolean patternOK = (selloRecibido!=null) && Pattern.matches(PATTERN, selloRecibido);  
				
		if(length>=MINLENGTH && length<=MAXLENGTH && patternOK)
			this.selloRecibido = selloRecibido;
		else
	        throw new IllegalArgumentException("Wrong parameter 'selloRecibido' in Anulacion.Documento.setSelloRecibido()");
	}


	/**
	 * @return the numeroControl
	 */
	public String getNumeroControl() {
		return numeroControl;
	}


	/**
	 * @param numeroControl the numeroControl to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 31, "maxLength" : 31<br>
	 * "pattern" : "^DTE-0[0-9]|1[0-2]-[A-Z0-9]{8}-[0-9]{15}$"
	 */
	public void setNumeroControl(String numeroControl) {
		final int MINLENGTH = 31;
		final int MAXLENGTH = 31;
		int length = numeroControl==null?0:numeroControl.length();
		
		final String PATTERN = "^(DTE-0[0-9]|1[0-2])-[A-Z0-9]{8}-[0-9]{15}$";
		boolean patternOK = (numeroControl!=null) && Pattern.matches(PATTERN, numeroControl);  
				
		if(length>=MINLENGTH && length<=MAXLENGTH && patternOK)
			this.numeroControl = numeroControl;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numeroControl' in Anulacion.Documento.setNumeroControl()");
	}


	/**
	 * @return the fecEmi
	 */
	public String getFecEmi() {
		return fecEmi;
	}


	/**
	 * @param fecEmi the fecEmi to set<br>
	 * null not allowed
	 */
	public void setFecEmi(String fecEmi) {
		if(fecEmi!=null)
			this.fecEmi = fecEmi;
		else
	        throw new IllegalArgumentException("Wrong parameter 'fecEmi' in Anulacion.Documento.setFecEmi()");
	}


	/**
	 * @return the montoIva
	 */
	public BigDecimal getMontoIva() {
		return montoIva;
	}


	/**
	 * @param montoIva the montoIva to set
	 */
	public void setMontoIva(BigDecimal montoIva) {
		this.montoIva = montoIva;
	}


	/**
	 * @return the codigoGeneracionR
	 */
	public String getCodigoGeneracionR() {
		return codigoGeneracionR;
	}


	/**
	 * @param codigoGeneracionR the codigoGeneracionR to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 36, "maxLength" : 36<br>
	 * "pattern" : "^[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}$"
	 */
	public void setCodigoGeneracionR(String codigoGeneracionR) {
		final int MINLENGTH = 36;
		final int MAXLENGTH = 36;
		int length = codigoGeneracionR==null?0:codigoGeneracionR.length();
		
		final String PATTERN = "^[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}$";
		boolean patternOK = (codigoGeneracionR!=null) && Pattern.matches(PATTERN, codigoGeneracionR);  
				
		if(length>=MINLENGTH && length<=MAXLENGTH && patternOK)
			this.codigoGeneracionR = codigoGeneracionR;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codigoGeneracionR' in Anulacion.Documento.setCodigoGeneracionR()");
	}


	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}


	/**
	 * @param tipoDocumento the tipoDocumento to set<br>
	 * The parameter is validated.<br>
	 * Specific values allowed according to schema: ["36", "13", "02", .....].
	 * The enum was chosen for validation.
	 */
	public void setTipoDocumento(String tipoDocumento) {
		String[] validTipoDocumento = { "36", "13", "02", "03", "37" };		
		boolean isTipoDocumentoValid = Arrays.stream(validTipoDocumento).anyMatch(tipoDocumento::equals);

		if(isTipoDocumentoValid)
			this.tipoDocumento = tipoDocumento;
		else
			throw new IllegalArgumentException("Wrong parameter 'tipoDocumento' in Anulacion.Documento.setTipoDocumento()");
	}


	/**
	 * @return the numDocumento
	 */
	public String getNumDocumento() {
		return numDocumento;
	}


	/**
	 * @param numDocumento the numDocumento to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 3, "maxLength" : 20<br>
	 */
	public void setNumDocumento(String numDocumento) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 20;
		int length = numDocumento==null?0:numDocumento.length();
				
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.numDocumento = numDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numDocumento' in Anulacion.Documento.setNumDocumento()");
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
	 * "minLength" : 5, "maxLength" : 200<br>
	 */
	public void setNombre(String nombre) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 200;
		int length = nombre==null?0:nombre.length();
				
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.nombre = nombre;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombre' in Anulacion.Documento.setNombre()");
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
	 * "minLength" : 8, "maxLength" : 50<br>
	 * "pattern" : "^[0-9+;]{8,50}$"
	 */
	public void setTelefono(String telefono) {
		final int MINLENGTH = 8;
		final int MAXLENGTH = 50;
		int length = telefono==null?0:telefono.length();

		final String PATTERN = "^[0-9+;]{8,50}$";
		boolean patternOK = (telefono!=null) && Pattern.matches(PATTERN, telefono);  
		
		if(length>=MINLENGTH && length<=MAXLENGTH && patternOK)
			this.telefono = telefono;
		else
	        throw new IllegalArgumentException("Wrong parameter 'telefono' in Anulacion.Documento.setTelefono()");
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
	 * "maxLength" : 100
	 */
	public void setCorreo(String correo) {
		final int MAXLENGTH = 100;
		int length = correo==null?0:correo.length();
		
		if(length<=MAXLENGTH)
			this.correo = correo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'correo' in Anulacion.Documento.setCorreo()");
	}	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

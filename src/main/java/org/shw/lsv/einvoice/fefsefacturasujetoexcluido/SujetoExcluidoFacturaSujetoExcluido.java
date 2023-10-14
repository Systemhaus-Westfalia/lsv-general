package org.shw.lsv.einvoice.fefsefacturasujetoexcluido;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.Direccion;
import org.shw.lsv.einvoice.utils.EDocumentUtils;

public class SujetoExcluidoFacturaSujetoExcluido {
	
	static final String VALIDATION_NUMERODOCUMENTO_PATTERN_FAILED  = "Documento: FacturaNoSujeto, clase: SujetoExcluido. Validacion fall??: valor de 'numDocumento' no corresponde a patr??n";
	static final String VALIDATION_NRC_NOT_NULL                    = "Documento: FacturaNoSujeto, clase: SujetoExcluido. Validacion fall??: valor de 'nrc' debe ser ='null'";

	String tipoDocumento=null; // null possible
	String numDocumento=null; // null possible
	String nombre=null; // null possible
	String codActividad;
	String descActividad=null; // null possible
	Direccion direccion;
    String telefono=null; // null possible
    String correo;
	
	/**
	 * No parameters
	 */
	public SujetoExcluidoFacturaSujetoExcluido() {
		this.direccion = new Direccion();
	}
	

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		String pattern;
		boolean patternOK;

		// In schema: "pattern" : "^([0-9]{14}|[0-9]{9})$"
		if(getTipoDocumento() !=null &&   getTipoDocumento().equals("36")) {
			pattern = "^([0-9]{14}|[0-9]{9})$";
			patternOK = (getNumDocumento()!=null) && Pattern.matches(pattern, getNumDocumento());
			if(!patternOK)
				return VALIDATION_NUMERODOCUMENTO_PATTERN_FAILED;
		} else {
			
				return VALIDATION_NUMERODOCUMENTO_PATTERN_FAILED;
		}

		// In schema: "pattern" : "^[0-9]{8}-[0-9]{1}$"
		if(getTipoDocumento() !=null && getTipoDocumento().equals("13")) {
			pattern = "^[0-9]{8}-[0-9]{1}$";
			patternOK = (getNumDocumento()!=null) && Pattern.matches(pattern, getNumDocumento());
			if(!patternOK)
				return VALIDATION_NUMERODOCUMENTO_PATTERN_FAILED;
		}
		
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}
	
	

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		String[] validTipoDocumento = { "36", "13", "02", "03", "37" };
		
		if((tipoDocumento==null) || (Arrays.stream(validTipoDocumento).anyMatch(tipoDocumento::equals)) )
			this.tipoDocumento = tipoDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoDocumento' in FacturaSujetoExcluido.SujetoExcluido.setTipoDocumento()");
	}

	public String getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(String numDocumento) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 20;
		int length = numDocumento==null?0:numDocumento.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (numDocumento==null) )
			this.numDocumento = numDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numDocumento' in FacturaSujetoExcluido.SujetoExcluido.setNumDocumento()");
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 250;
		int length = nombre==null?0:nombre.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH)  || (nombre==null) )
			this.nombre = nombre;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombre' in FacturaSujetoExcluido.SujetoExcluido.setNombre()");
	}

	public String getCodActividad() {
		return codActividad;
	}

	public void setCodActividad(String codActividad) {
		final String PATTERN = "^[0-9]{2,6}$";
		boolean patternOK = (codActividad!=null) && Pattern.matches(PATTERN, codActividad);  
		
		if(patternOK)
			this.codActividad = codActividad;
		else
	        throw new IllegalArgumentException("Wrong expression 'codActividad' in FacturaSujetoExcluido.SujetoExcluido.setCodActividad()");
	}

	public String getDescActividad() {
		return descActividad;
	}

	public void setDescActividad(String descActividad) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 150;
		int length = descActividad==null?0:descActividad.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (descActividad==null) )
			this.descActividad = descActividad;
		else
	        throw new IllegalArgumentException("Wrong parameter 'descActividad' in FacturaSujetoExcluido.SujetoExcluido.setDescActividad()");
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

	public void setTelefono(String telefono) {
		final int MINLENGTH = 8;
		final int MAXLENGTH = 30;
		int length = telefono==null?0:telefono.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (telefono==null) )
			this.telefono = telefono;
		else
	        throw new IllegalArgumentException("Wrong parameter 'telefono' in FacturaSujetoExcluido.SujetoExcluido.setTelefono()");
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		final int MAXLENGTH = 100;
		int length = correo==null?0:correo.length();
		
		if(length<=MAXLENGTH)
			this.correo = correo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'correo' in FacturaSujetoExcluido.SujetoExcluido.setCorreo()");
	}


}

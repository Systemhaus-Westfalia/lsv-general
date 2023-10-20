package org.shw.lsv.einvoice.fefcfacturaelectronicav1;

import java.util.regex.Pattern;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

public class DocumentoRelacionadoItemFactura {
	static final String VALIDATION_NUMERODOCUMENTO_PATTERN_FAILED = "Documento: Factura, clase: DocumentoRelacionadoItem. Validacion fall??: valor de 'numeroDocumento' no corresponde a patr??n";
	static final String VALIDATION_NUMERODOCUMENTO_LENGTH_FAILED  = "Documento: Factura, clase: DocumentoRelacionadoItem. Validacion fall??: valor de 'numeroDocumento'  debe  contener de 1 a 20 caracteres";
	
	String tipoDocumento;
	int tipoGeneracion;
	String numeroDocumento;
	String fechaEmision;
	

	/**
	 */
	public DocumentoRelacionadoItemFactura() {
	}
	
	/**
	 * @param tipoDocumento
	 * @param tipoGeneracion
	 * @param numeroDocumento
	 * @param fechaEmision
	 */
	public DocumentoRelacionadoItemFactura(String tipoDocumento, int tipoGeneracion, String numeroDocumento,
			String fechaEmision) {
		this.tipoDocumento = tipoDocumento;
		this.tipoGeneracion = tipoGeneracion;
		this.numeroDocumento = numeroDocumento;
		this.fechaEmision = fechaEmision;
	}


	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		final String PATTERN = "^[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}$";
		if(getTipoGeneracion()==2) {
			boolean patternOK = (getNumeroDocumento()!=null) && (Pattern.matches(PATTERN, getNumeroDocumento()));  
			
			if(!patternOK)
				return VALIDATION_NUMERODOCUMENTO_PATTERN_FAILED;
		}
		if(getTipoGeneracion()==1) {
			final int MINLENGTH = 1;
			final int MAXLENGTH = 20;
			int length = getNumeroDocumento()==null?0:getNumeroDocumento().length();

			if(!(length>=MINLENGTH && length<=MAXLENGTH))
				return VALIDATION_NUMERODOCUMENTO_LENGTH_FAILED;
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
	 * The parameter is validated.<br>
	 * "enum" : ["04", "09"]
	 */
	public void setTipoDocumento(String tipoDocumento) {
		if (tipoDocumento.compareTo("04")==0 || tipoDocumento.compareTo("09")==0)
			this.tipoDocumento = tipoDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoDocumento' in Factura.DocumentoRelacionadoItem.setTipoDocumento()" + "\n");
	}
	
	/**
	 * @return the tipoGeneracion
	 */
	public int getTipoGeneracion() {
		return tipoGeneracion;
	}

	/**
	 * @param tipoGeneracion the tipoGeneracion to set.<br>
	 * The parameter is validated.<br>
	 * "enum" : [1,2]
	 */
	public void setTipoGeneracion(int tipoGeneracion) {	
		if (tipoGeneracion==1 || tipoGeneracion==2)
			this.tipoGeneracion = tipoGeneracion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoGeneracion' in Factura.DocumentoRelacionadoItem.setTipoGeneracion()" + "\n");
	}

	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento the numeroDocumento to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 36
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 36;
		int length = numeroDocumento==null?0:numeroDocumento.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.numeroDocumento = numeroDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numeroDocumento' in Factura.DocumentoRelacionadoItem.setNumeroDocumento()" + "\n");
	}

	/**
	 * @return the fechaEmision
	 */
	public String getFechaEmision() {
		return fechaEmision;
	}

	/**
	 * @param fechaEmision the fechaEmision to set<br>
	 * null not allowed
	 */
	public void setFechaEmision(String fechaEmision) {
		if (fechaEmision!=null)
			this.fechaEmision = fechaEmision;
		else
	        throw new IllegalArgumentException("Wrong parameter 'fechaEmision' in Factura.DocumentoRelacionadoItem.setFechaEmision()" + "\n");
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

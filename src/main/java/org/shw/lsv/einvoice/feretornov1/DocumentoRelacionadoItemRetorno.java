package org.shw.lsv.einvoice.feretornov1;

import org.shw.lsv.einvoice.utils.EDocumentUtils;


public class DocumentoRelacionadoItemRetorno {

	String tipoDocumento;
	String codigoGeneracion;
	String fechaEmision;


	public DocumentoRelacionadoItemRetorno() {
	}

	/**
	 * @param tipoDocumento
	 * @param codigoGeneracion
	 * @param fechaEmision
	 */
	public DocumentoRelacionadoItemRetorno(String tipoDocumento, String codigoGeneracion, String fechaEmision) {
		this.tipoDocumento     = tipoDocumento;
		this.codigoGeneracion  = codigoGeneracion;
		this.fechaEmision      = fechaEmision;
	}

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		if (tipoDocumento != null)
			this.tipoDocumento = tipoDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoDocumento' in EventoDeRetorno.DocumentoRelacionadoItem.setTipoDocumento()" + "\n");
	}

	public String getCodigoGeneracion() {
		return codigoGeneracion;
	}

	/**
	 * @param codigoGeneracion the codigoGeneracion to set<br>
	 * "minLength" : 1, "maxLength" : 36
	 */
	public void setCodigoGeneracion(String codigoGeneracion) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 36;
		int length = codigoGeneracion == null ? 0 : codigoGeneracion.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.codigoGeneracion = codigoGeneracion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codigoGeneracion' in EventoDeRetorno.DocumentoRelacionadoItem.setCodigoGeneracion()" + "\n");
	}

	public String getFechaEmision() {
		return fechaEmision;
	}

	/**
	 * @param fechaEmision the fechaEmision to set<br>
	 * null not allowed
	 */
	public void setFechaEmision(String fechaEmision) {
		if (fechaEmision != null)
			this.fechaEmision = fechaEmision;
		else
	        throw new IllegalArgumentException("Wrong parameter 'fechaEmision' in EventoDeRetorno.DocumentoRelacionadoItem.setFechaEmision()" + "\n");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

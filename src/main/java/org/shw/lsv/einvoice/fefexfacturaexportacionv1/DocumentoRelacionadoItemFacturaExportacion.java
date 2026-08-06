package org.shw.lsv.einvoice.fefexfacturaexportacionv1;

public class DocumentoRelacionadoItemFacturaExportacion {

	private String tipoDocumento;
	private Integer tipoGeneracion;
	private String numeroDocumento;
	private String fechaEmision;

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getTipoGeneracion() {
		return tipoGeneracion;
	}

	public void setTipoGeneracion(Integer tipoGeneracion) {
		this.tipoGeneracion = tipoGeneracion;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
}

package org.shw.lsv.einvoice.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // This prevents errors if the JSON has extra fields
public class DteRoot {

    @JsonProperty("identificacion")
    private Identificacion identificacion;

    @JsonProperty("cuerpoDocumento")
    private List<CuerpoDocumentoItem> cuerpoDocumento;

    @JsonProperty("resumen")
    private Resumen resumen;
    

    @JsonProperty("emisor")
    private Emisor emisor;

    public Emisor getEmisor() {
		return emisor;
	}

	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}

	// Default Constructor
    public DteRoot() {}

    // Getters and Setters
    public Identificacion getIdentificacion() { return identificacion; }
    public void setIdentificacion(Identificacion identificacion) { this.identificacion = identificacion; }

    public List<CuerpoDocumentoItem> getCuerpoDocumento() { return cuerpoDocumento; }
    public void setCuerpoDocumento(List<CuerpoDocumentoItem> cuerpoDocumento) { this.cuerpoDocumento = cuerpoDocumento; }

    public Resumen getResumen() { return resumen; }
    public void setResumen(Resumen resumen) { this.resumen = resumen; }
}
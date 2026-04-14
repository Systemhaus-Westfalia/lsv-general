package org.shw.lsv.einvoice.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CuerpoDocumentoItem {
	@JsonProperty("numItem")
    private int numItem;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("cantidad")
    private double cantidad;

    @JsonProperty("precioUni")
    private double precioUni;

    @JsonProperty("ventaGravada")
    private double ventaGravada; 


    @JsonProperty("codigoRetencionMH")
    private String codigoRetencionMH;
    
    @JsonProperty("numDocumento")
    private String numDocumento;
    

    @JsonProperty("ivaRetenido")
    private double ivaRetenido; 
    

    @JsonProperty("codigo")
    private String codigo;

    public String getCodigo() {
		return codigo;
	}

    public void setCodigo(String codigo) {
    	if (codigo == null)
    		this.setCodigo("");
    	else
    		this.codigo = codigo;
    }

	public String getCodigoRetencionMH() {
		return codigoRetencionMH;
	}

	public void setCodigoRetencionMH(String codigoRetencionMH) {
		this.codigoRetencionMH = codigoRetencionMH;
	}

	public String getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	public double getIvaRetenido() {
		return ivaRetenido;
	}

	public void setIvaRetenido(double ivaRetenido) {
		this.ivaRetenido = ivaRetenido;
	}
	

    @JsonProperty("noGravado")
    private double noGravado;

	public double getNoGravado() {
		return noGravado;
	}

	public void setNoGravado(double noGravado) {
		this.noGravado = noGravado;
	}


	// This is an array of Strings in your JSON ["D1", "C8", "20"]
    @JsonProperty("tributos")
    private List<String> tributos;

	public int getNumItem() {
		return numItem;
	}

	public void setNumItem(int numItem) {
		this.numItem = numItem;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioUni() {
		return precioUni;
	}

	public void setPrecioUni(double precioUni) {
		this.precioUni = precioUni;
	}

	public double getVentaGravada() {
		return ventaGravada;
	}

	public void setVentaGravada(double ventaGravada) {
		this.ventaGravada = ventaGravada;
	}

	public List<String> getTributos() {
		return tributos;
	}

	public void setTributos(List<String> tributos) {
		this.tributos = tributos;
	}
    
    

}

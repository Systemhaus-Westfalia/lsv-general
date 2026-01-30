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

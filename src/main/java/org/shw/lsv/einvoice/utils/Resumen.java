package org.shw.lsv.einvoice.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resumen {
    @JsonProperty("totalGravada")
    private BigDecimal totalGravada;

    @JsonProperty("subTotalVentas")
    private BigDecimal subTotalVentas;

    // This is how you treat the array:
    @JsonProperty("tributos")
    private List<TributosItem> tributos;
    
    

    @JsonProperty("totalNoSuj")
    private BigDecimal totalNoSuj;    

    @JsonProperty("totalExenta")
    private BigDecimal totalExenta;
    @JsonProperty("descuNoSuj")
    private BigDecimal descuNoSuj;
    @JsonProperty("descuExenta")
    private BigDecimal descuExenta;
    @JsonProperty("cuGravada")
    private BigDecimal cuGravada;
    @JsonProperty("porcentajeDescuento")
    private BigDecimal porcentajeDescuento;
    @JsonProperty("totalDescu")
    private BigDecimal totalDescu;

    public Resumen() {}

    // Getter and Setter for the list
    public List<TributosItem> getTributos() {
        return tributos;
    }

    public void setTributos(List<TributosItem> tributos) {
        this.tributos = tributos;
    }

	public BigDecimal getTotalGravada() {
		return totalGravada;
	}

	public void setTotalGravada(BigDecimal totalGravada) {
		this.totalGravada = totalGravada;
	}

	public BigDecimal getSubTotalVentas() {
		return subTotalVentas;
	}

	public void setSubTotalVentas(BigDecimal subTotalVentas) {
		this.subTotalVentas = subTotalVentas;
	}

	public BigDecimal getTotalNoSuj() {
		return totalNoSuj;
	}

	public void setTotalNoSuj(BigDecimal totalNoSuj) {
		this.totalNoSuj = totalNoSuj;
	}

	public BigDecimal getTotalExenta() {
		return totalExenta;
	}

	public void setTotalExenta(BigDecimal totalExenta) {
		this.totalExenta = totalExenta;
	}

	public BigDecimal getDescuNoSuj() {
		return descuNoSuj;
	}

	public void setDescuNoSuj(BigDecimal descuNoSuj) {
		this.descuNoSuj = descuNoSuj;
	}

	public BigDecimal getDescuExenta() {
		return descuExenta;
	}

	public void setDescuExenta(BigDecimal descuExenta) {
		this.descuExenta = descuExenta;
	}

	public BigDecimal getCuGravada() {
		return cuGravada;
	}

	public void setCuGravada(BigDecimal cuGravada) {
		this.cuGravada = cuGravada;
	}

	public BigDecimal getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public BigDecimal getTotalDescu() {
		return totalDescu;
	}

	public void setTotalDescu(BigDecimal totalDescu) {
		this.totalDescu = totalDescu;
	}
    
}
package org.shw.lsv.einvoice.utils;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Identificacion {
	@JsonProperty(value = "tipoDte", required = true)
	public String tipoDte;  // Document Type

	@JsonProperty(value = "numeroControl", required = true)
	public String numeroControl;  // 


	@JsonProperty(value = "codigoGeneracion", required = true)
	public String codigoGeneracion; 

	@JsonProperty(value = "fecEmi", required = true)
	public String fecEmi;  // Date and time at which the message was created.


	@JsonProperty(value = "tipoMoneda", required = true)
	public String tipoMoneda;  // Currency
	


	@JsonProperty(value = "version", required = false)
	public String version;  
	

	@JsonProperty(value = "ambiente", required = false)
	public String ambiente;  
	

	@JsonProperty(value = "tipoModelo", required = false)
	public String tipoModelo;  
	

	@JsonProperty(value = "tipoOperacion", required = false)
	public String tipoOperacion;  

	@JsonProperty(value = "horEmi", required = false)
	public String horEmi;  
	

	@JsonProperty(value = "tipoContingencia", required = false)
	public String tipoContingencia;  

	@JsonProperty(value = "motivoContin", required = false)
	public String motivoContin;  

	public Identificacion() {
	}




	public String getTipoDte() {
		return tipoDte;
	}


	public void setTipoDte(String tipoDte) {
		this.tipoDte = tipoDte;
	}


	public String getNumeroControl() {
		return numeroControl;
	}


	public void setNumeroControl(String numeroControl) {
		this.numeroControl = numeroControl;
	}


	public String getCodigoGeneracion() {
		return codigoGeneracion;
	}


	public void setCodigoGeneracion(String codigoGeneracion) {
		this.codigoGeneracion = codigoGeneracion;
	}


	public String getFecEmi() {
		return fecEmi;
	}


	public void setFecEmi(String fecEmi) {
		this.fecEmi = fecEmi;
	}


	public String getTipoMoneda() {
		return tipoMoneda;
	}


	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}



}

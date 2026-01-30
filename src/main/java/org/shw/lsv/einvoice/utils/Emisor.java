package org.shw.lsv.einvoice.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Emisor {
	@JsonProperty(value = "nit", required = false)
	public String nit;  // Document Type

	@JsonProperty(value = "nrc", required = true)
	public String nrc;  // 


	@JsonProperty(value = "nombre", required = true)
	public String nombre; 

	@JsonProperty(value = "codActividad", required = true)
	public String codActividad;  
	

	@JsonProperty(value = "descActividad", required = true)
	public String descActividad;  
	
	@JsonProperty(value = "nombreComercial", required = true)
	public String nombreComercial;  	

	@JsonProperty(value = "tipoEstablecimiento", required = true)
	public String tipoEstablecimiento;  	

	@JsonProperty(value = "codEstableMH", required = true)
	public String codEstableMH; 
	
	@JsonProperty(value = "codEstable", required = true)
	public String codEstable;  
	

	@JsonProperty(value = "codPuntoVentaMH", required = true)
	public String codPuntoVentaMH;  	

	@JsonProperty(value = "codPuntoVenta", required = false)
	public String codPuntoVenta;  


	@JsonProperty(value = "telefono", required = true)
	public String telefono;  // Currency
	
	@JsonProperty(value = "direccion", required = true)
	public Direccion direccion;  // Currency	

	
	@JsonProperty(value = "correo", required = true)
	public String correo;  // Currency

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodActividad() {
		return codActividad;
	}

	public void setCodActividad(String codActividad) {
		this.codActividad = codActividad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Emisor() {
	}


}

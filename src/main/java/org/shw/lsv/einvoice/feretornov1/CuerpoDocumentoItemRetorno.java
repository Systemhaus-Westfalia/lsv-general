package org.shw.lsv.einvoice.feretornov1;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

public class CuerpoDocumentoItemRetorno {

	int numItem;
	int tipoItem;
	String codigoGeneracion;
	BigDecimal cantidad;
	BigDecimal precioUni;
	String descripcion;
	String codigo = null;     // null allowed
	int uniMedida;
	BigDecimal montoDescu;
	String codTributo = null; // null allowed
	BigDecimal ventaNoSuj;
	BigDecimal ventaExenta;
	BigDecimal ventaGravada;
	BigDecimal compra;
	ArrayList<String> tributos = null; // null allowed
	BigDecimal psv;
	BigDecimal ivaItem;
	BigDecimal noGravado;
	BigDecimal seguro;
	BigDecimal flete;
	BigDecimal ivaRete;
	BigDecimal reteRenta;


	public CuerpoDocumentoItemRetorno() {
		tributos = new ArrayList<String>();
	}

	public CuerpoDocumentoItemRetorno(int numItem, int tipoItem, String codigoGeneracion, BigDecimal cantidad,
			BigDecimal precioUni, String descripcion, String codigo, int uniMedida, BigDecimal montoDescu,
			String codTributo, BigDecimal ventaNoSuj, BigDecimal ventaExenta, BigDecimal ventaGravada,
			BigDecimal compra, ArrayList<String> tributos, BigDecimal psv, BigDecimal ivaItem,
			BigDecimal noGravado, BigDecimal seguro, BigDecimal flete, BigDecimal ivaRete, BigDecimal reteRenta) {
		this.numItem          = numItem;
		this.tipoItem         = tipoItem;
		this.codigoGeneracion = codigoGeneracion;
		this.cantidad         = cantidad;
		this.precioUni        = precioUni;
		this.descripcion      = descripcion;
		this.codigo           = codigo;
		this.uniMedida        = uniMedida;
		this.montoDescu       = montoDescu;
		this.codTributo       = codTributo;
		this.ventaNoSuj       = ventaNoSuj;
		this.ventaExenta      = ventaExenta;
		this.ventaGravada     = ventaGravada;
		this.compra           = compra;
		this.tributos         = tributos;
		this.psv              = psv;
		this.ivaItem          = ivaItem;
		this.noGravado        = noGravado;
		this.seguro           = seguro;
		this.flete            = flete;
		this.ivaRete          = ivaRete;
		this.reteRenta        = reteRenta;
	}

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	public int getNumItem() {
		return numItem;
	}

	/**
	 * @param numItem the numItem to set<br>
	 * "minimum" : 1, "maximum" : 2000
	 */
	public void setNumItem(int numItem) {
		final int MINIMUM = 1;
		final int MAXIMUM = 2000;

		if (numItem >= MINIMUM && numItem <= MAXIMUM)
			this.numItem = numItem;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numItem' in EventoDeRetorno.CuerpoDocumentoItem.setNumItem()" + "\n");
	}

	public int getTipoItem() {
		return tipoItem;
	}

	/**
	 * @param tipoItem the tipoItem to set
	 */
	public void setTipoItem(int tipoItem) {
		this.tipoItem = tipoItem;
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
	        throw new IllegalArgumentException("Wrong parameter 'codigoGeneracion' in EventoDeRetorno.CuerpoDocumentoItem.setCodigoGeneracion()" + "\n");
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioUni() {
		return precioUni;
	}

	public void setPrecioUni(BigDecimal precioUni) {
		this.precioUni = precioUni;
	}

	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set<br>
	 * "minLength" : 1, "maxLength" : 1500
	 */
	public void setDescripcion(String descripcion) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 1500;
		int length = descripcion == null ? 0 : descripcion.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.descripcion = descripcion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'descripcion' in EventoDeRetorno.CuerpoDocumentoItem.setDescripcion()" + "\n");
	}

	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set<br>
	 * "minLength" : 1, "maxLength" : 25; null also allowed
	 */
	public void setCodigo(String codigo) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 25;
		int length = codigo == null ? 0 : codigo.length();

		if (codigo == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.codigo = codigo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codigo' in EventoDeRetorno.CuerpoDocumentoItem.setCodigo()" + "\n");
	}

	public int getUniMedida() {
		return uniMedida;
	}

	/**
	 * @param uniMedida the uniMedida to set<br>
	 * "minimum" : 1, "maximum" : 99
	 */
	public void setUniMedida(int uniMedida) {
		final int MINIMUM = 1;
		final int MAXIMUM = 99;

		if (uniMedida >= MINIMUM && uniMedida <= MAXIMUM)
			this.uniMedida = uniMedida;
		else
	        throw new IllegalArgumentException("Wrong parameter 'uniMedida' in EventoDeRetorno.CuerpoDocumentoItem.setUniMedida()" + "\n");
	}

	public BigDecimal getMontoDescu() {
		return montoDescu;
	}

	public void setMontoDescu(BigDecimal montoDescu) {
		this.montoDescu = montoDescu;
	}

	public String getCodTributo() {
		return codTributo;
	}

	/**
	 * @param codTributo the codTributo to set<br>
	 * "maxLength" : 2, "minLength" : 2; null also allowed
	 */
	public void setCodTributo(String codTributo) {
		final int EXACTLENGTH = 2;
		int length = codTributo == null ? 0 : codTributo.length();

		if (length == 0 || length == EXACTLENGTH)
			this.codTributo = codTributo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codTributo' in EventoDeRetorno.CuerpoDocumentoItem.setCodTributo()" + "\n");
	}

	public BigDecimal getVentaNoSuj() {
		return ventaNoSuj;
	}

	public void setVentaNoSuj(BigDecimal ventaNoSuj) {
		this.ventaNoSuj = ventaNoSuj;
	}

	public BigDecimal getVentaExenta() {
		return ventaExenta;
	}

	public void setVentaExenta(BigDecimal ventaExenta) {
		this.ventaExenta = ventaExenta;
	}

	public BigDecimal getVentaGravada() {
		return ventaGravada;
	}

	public void setVentaGravada(BigDecimal ventaGravada) {
		this.ventaGravada = ventaGravada;
	}

	public BigDecimal getCompra() {
		return compra;
	}

	public void setCompra(BigDecimal compra) {
		this.compra = compra;
	}

	public ArrayList<String> getTributos() {
		return tributos;
	}

	/**
	 * @param tributos the tributos to set — null or array of 2-char strings; "minItems" : 1
	 */
	public void setTributos(ArrayList<String> tributos) {
		final int MINLENGTH = 1;

		if (tributos == null || tributos.size() >= MINLENGTH)
			this.tributos = tributos;
		else
	        throw new IllegalArgumentException("Wrong expression 'tributos' in EventoDeRetorno.CuerpoDocumentoItem.setTributos()" + "\n");
	}

	public BigDecimal getPsv() {
		return psv;
	}

	public void setPsv(BigDecimal psv) {
		this.psv = psv;
	}

	public BigDecimal getIvaItem() {
		return ivaItem;
	}

	public void setIvaItem(BigDecimal ivaItem) {
		this.ivaItem = ivaItem;
	}

	public BigDecimal getNoGravado() {
		return noGravado;
	}

	public void setNoGravado(BigDecimal noGravado) {
		this.noGravado = noGravado;
	}

	public BigDecimal getSeguro() {
		return seguro;
	}

	public void setSeguro(BigDecimal seguro) {
		this.seguro = seguro;
	}

	public BigDecimal getFlete() {
		return flete;
	}

	public void setFlete(BigDecimal flete) {
		this.flete = flete;
	}

	public BigDecimal getIvaRete() {
		return ivaRete;
	}

	public void setIvaRete(BigDecimal ivaRete) {
		this.ivaRete = ivaRete;
	}

	public BigDecimal getReteRenta() {
		return reteRenta;
	}

	public void setReteRenta(BigDecimal reteRenta) {
		this.reteRenta = reteRenta;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}

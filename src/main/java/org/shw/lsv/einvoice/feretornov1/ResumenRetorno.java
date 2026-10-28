package org.shw.lsv.einvoice.feretornov1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.shw.lsv.einvoice.utils.EDocumentUtils;
import org.shw.lsv.einvoice.utils.TributosItem;

public class ResumenRetorno {

	BigDecimal totalNoSuj;
	BigDecimal totalExenta;
	BigDecimal totalGravada;
	BigDecimal totalCompraExcluidos;
	BigDecimal subTotalVentas;
	List<TributosItem> tributos;      // null allowed
	BigDecimal totalSeguro = null;    // null allowed
	BigDecimal totalFlete = null;     // null allowed
	BigDecimal montoTotalOperacion;
	BigDecimal ivaRete;
	BigDecimal reteRenta = null;      // null allowed
	BigDecimal totalNoGravado;
	BigDecimal totalPagar;
	String totalLetras = null;        // null allowed
	BigDecimal totalNoOnerosas;
	BigDecimal totalIva;
	BigDecimal saldoFavor;            // maximum: 0


	public ResumenRetorno() {
		this.tributos = new ArrayList<TributosItem>();
	}

	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
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

	public BigDecimal getTotalGravada() {
		return totalGravada;
	}

	public void setTotalGravada(BigDecimal totalGravada) {
		this.totalGravada = totalGravada;
	}

	public BigDecimal getTotalCompraExcluidos() {
		return totalCompraExcluidos;
	}

	public void setTotalCompraExcluidos(BigDecimal totalCompraExcluidos) {
		this.totalCompraExcluidos = totalCompraExcluidos;
	}

	public BigDecimal getSubTotalVentas() {
		return subTotalVentas;
	}

	public void setSubTotalVentas(BigDecimal subTotalVentas) {
		this.subTotalVentas = subTotalVentas;
	}

	public List<TributosItem> getTributos() {
		return tributos;
	}

	public void setTributos(List<TributosItem> tributos) {
		this.tributos = tributos;
	}

	public BigDecimal getTotalSeguro() {
		return totalSeguro;
	}

	public void setTotalSeguro(BigDecimal totalSeguro) {
		this.totalSeguro = totalSeguro;
	}

	public BigDecimal getTotalFlete() {
		return totalFlete;
	}

	public void setTotalFlete(BigDecimal totalFlete) {
		this.totalFlete = totalFlete;
	}

	public BigDecimal getMontoTotalOperacion() {
		return montoTotalOperacion;
	}

	public void setMontoTotalOperacion(BigDecimal montoTotalOperacion) {
		this.montoTotalOperacion = montoTotalOperacion;
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

	public BigDecimal getTotalNoGravado() {
		return totalNoGravado;
	}

	public void setTotalNoGravado(BigDecimal totalNoGravado) {
		this.totalNoGravado = totalNoGravado;
	}

	public BigDecimal getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(BigDecimal totalPagar) {
		this.totalPagar = totalPagar;
	}

	public String getTotalLetras() {
		return totalLetras;
	}

	/**
	 * @param totalLetras the totalLetras to set<br>
	 * "minLength" : 8, "maxLength" : 200; null also allowed
	 */
	public void setTotalLetras(String totalLetras) {
		final int MINLENGTH = 8;
		final int MAXLENGTH = 200;
		int length = totalLetras == null ? 0 : totalLetras.length();

		if (totalLetras == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.totalLetras = totalLetras;
		else
			throw new IllegalArgumentException("Wrong parameter 'totalLetras' in EventoDeRetorno.Resumen.setTotalLetras()" + "\n");
	}

	public BigDecimal getTotalNoOnerosas() {
		return totalNoOnerosas;
	}

	public void setTotalNoOnerosas(BigDecimal totalNoOnerosas) {
		this.totalNoOnerosas = totalNoOnerosas;
	}

	public BigDecimal getTotalIva() {
		return totalIva;
	}

	public void setTotalIva(BigDecimal totalIva) {
		this.totalIva = totalIva;
	}

	public BigDecimal getSaldoFavor() {
		return saldoFavor;
	}

	/**
	 * @param saldoFavor the saldoFavor to set<br>
	 * "maximum" : 0 (non-positive value)
	 */
	public void setSaldoFavor(BigDecimal saldoFavor) {
		if (saldoFavor == null || saldoFavor.compareTo(BigDecimal.ZERO) <= 0)
			this.saldoFavor = saldoFavor;
		else
			throw new IllegalArgumentException("Wrong parameter 'saldoFavor' in EventoDeRetorno.Resumen.setSaldoFavor() — must be <= 0" + "\n");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

/**
 *
 */
package org.shw.lsv.einvoice.feretornov1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.shw.lsv.einvoice.utils.EDocumentUtils;
import org.shw.lsv.einvoice.utils.TributosItem;

/**
 *
 */
public class ResumenRetorno {
	static final String VALIDATION_TOTALGRAVADA_IS_NULL  = "Documento: Nota de Credito, clase: Resumen. Validacion fallo: valor de 'totalGravada' no debe ser = null";
	static final String VALIDATION_TOTALGRAVADA_IVAPERCI = "Documento: Nota de Credito, clase: Resumen. Validacion fallo: valor de 'ivaPerci' no debe ser mayor que cero";
	static final String VALIDATION_TOTALGRAVADA_IVARETE  = "Documento: Nota de Credito, clase: Resumen. Validacion fallo: valor de 'ivaRete' no debe ser mayor que cero";

	BigDecimal totalNoSuj;
	BigDecimal totalExenta;
	BigDecimal totalGravada;
	BigDecimal subTotalVentas;
	BigDecimal totalDescu;
	List<TributosItem> tributos;
	BigDecimal montoTotalOperacion;
	BigDecimal ivaPerci;
	BigDecimal totalIva;
	BigDecimal ivaRete;
	BigDecimal totalNoGravado;
	BigDecimal totalPagar;
	String totalLetras = null;         // null allowed
	int condicionOperacion;
	String observaciones = null;       // null allowed
	String codigoRetencionMH = null;   // null allowed


	public ResumenRetorno() {
		this.tributos = new ArrayList<TributosItem>();
	}

	public String validateValues() {
		if (getTotalGravada() == null)
			return VALIDATION_TOTALGRAVADA_IS_NULL;

		if (getTotalGravada().compareTo(BigDecimal.ZERO) == 0) {
			if ((getIvaPerci() == null) || (getIvaPerci().compareTo(BigDecimal.ZERO) == 1))
				return VALIDATION_TOTALGRAVADA_IVAPERCI;
			if ((getIvaRete() == null) || (getIvaRete().compareTo(BigDecimal.ZERO) == 1))
				return VALIDATION_TOTALGRAVADA_IVARETE;
		}

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

	public BigDecimal getSubTotalVentas() {
		return subTotalVentas;
	}

	public void setSubTotalVentas(BigDecimal subTotalVentas) {
		this.subTotalVentas = subTotalVentas;
	}

	public BigDecimal getTotalDescu() {
		return totalDescu;
	}

	public void setTotalDescu(BigDecimal totalDescu) {
		this.totalDescu = totalDescu;
	}

	public List<TributosItem> getTributos() {
		return tributos;
	}

	public void setTributos(List<TributosItem> tributos) {
		this.tributos = tributos;
	}

	public BigDecimal getMontoTotalOperacion() {
		return montoTotalOperacion;
	}

	public void setMontoTotalOperacion(BigDecimal montoTotalOperacion) {
		this.montoTotalOperacion = montoTotalOperacion;
	}

	public BigDecimal getIvaPerci() {
		return ivaPerci;
	}

	public void setIvaPerci(BigDecimal ivaPerci) {
		this.ivaPerci = ivaPerci;
	}

	public BigDecimal getTotalIva() {
		return totalIva;
	}

	public void setTotalIva(BigDecimal totalIva) {
		this.totalIva = totalIva;
	}

	public BigDecimal getIvaRete() {
		return ivaRete;
	}

	public void setIvaRete(BigDecimal ivaRete) {
		this.ivaRete = ivaRete;
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

		if ((length >= MINLENGTH && length <= MAXLENGTH) || totalLetras == null)
			this.totalLetras = totalLetras;
		else
			throw new IllegalArgumentException("Wrong parameter 'totalLetras' in NotaDeCredito.Resumen.setTotalLetras()" + "\n");
	}

	public int getCondicionOperacion() {
		return condicionOperacion;
	}

	public void setCondicionOperacion(int condicionOperacion) {
		this.condicionOperacion = condicionOperacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set<br>
	 * "maxLength" : 3000; null also allowed
	 */
	public void setObservaciones(String observaciones) {
		final int MAXLENGTH = 3000;
		int length = observaciones == null ? 0 : observaciones.length();

		if (length <= MAXLENGTH)
			this.observaciones = observaciones;
		else
			throw new IllegalArgumentException("Wrong parameter 'observaciones' in NotaDeCredito.Resumen.setObservaciones()" + "\n");
	}

	public String getCodigoRetencionMH() {
		return codigoRetencionMH;
	}

	/**
	 * @param codigoRetencionMH the codigoRetencionMH to set<br>
	 * "maxLength" : 2; null also allowed
	 */
	public void setCodigoRetencionMH(String codigoRetencionMH) {
		final int MAXLENGTH = 2;
		int length = codigoRetencionMH == null ? 0 : codigoRetencionMH.length();

		if (length <= MAXLENGTH)
			this.codigoRetencionMH = codigoRetencionMH;
		else
			throw new IllegalArgumentException("Wrong parameter 'codigoRetencionMH' in NotaDeCredito.Resumen.setCodigoRetencionMH()" + "\n");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

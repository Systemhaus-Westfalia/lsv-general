/**
 * 
 */
package org.shw.lsv.einvoice.fenotaremisionv4;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.shw.lsv.einvoice.utils.EDocumentUtils;
import org.shw.lsv.einvoice.utils.PagosItem;
import org.shw.lsv.einvoice.utils.TributosItem;

/**
 * 
 */
public class ResumenNotaRemision {
	static final String VALIDATION_TOTALGRAVADA_IS_NULL  = "Documento: Credito Fiscal, clase: Resumen. Validacion fall??: valor de 'totlaGravada' no debe ser = null";
	static final String VALIDATION_PLAZO_IS_NULL         = "Documento: Credito Fiscal, clase: Resumen. Validacion fall??: valor de 'plazo' de pagos no debe ser ='null'";
	static final String VALIDATION_PERIODO_IS_NULL       = "Documento: Credito Fiscal, clase: Resumen. Validacion fall??: valor de 'periodo' de pagos no debe ser ='null'";
	static final String VALIDATION_TOTALGRAVADA_IVAPERC1 = "Documento: Credito Fiscal, clase: Resumen. Validacion fall??: valor de 'ivaPerci1' no debe ser mayor que cero";
	static final String VALIDATION_TOTALGRAVADA_IVARETE1 = "Documento: Credito Fiscal, clase: Resumen. Validacion fall??: valor de 'ivaRete1' no debe ser mayor que cero";
	static final String VALIDATION_TOTALGRAVADA_CONDOP   = "Documento: Credito Fiscal, clase: Resumen. Validacion fall??: valor de 'condicionOperacion' no debe ser diferente a 1";

	BigDecimal totalNoSuj;
	BigDecimal totalExenta;
	BigDecimal totalGravada;
	BigDecimal subTotalVentas;
	BigDecimal descuNoSuj;
	BigDecimal descuExenta;
	BigDecimal descuGravada;
	BigDecimal porcentajeDescuento;
	BigDecimal totalDescu;
	//BigDecimal reteRenta;
	List<TributosItem> tributos;
	BigDecimal subTotal;
	BigDecimal montoTotalOperacion;
	String totalLetras;
	String observaciones=null;  // null allowed
	



	/**
	 * No parameters
	 */
	public ResumenNotaRemision() {
		this.tributos = new ArrayList<TributosItem>();
	}

	/**
	 * Validate the Schema conditions
	 */

	public String validateValues() {
		

		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	/**
	 * @return the totalNoSuj
	 */

	public BigDecimal getTotalNoSuj() {
		return totalNoSuj;
	}


	/**
	 * @param totalNoSuj the totalNoSuj to set
	 */

	public void setTotalNoSuj(BigDecimal totalNoSuj) {
		this.totalNoSuj = totalNoSuj;
	}


	/**
	 * @return the totalExenta
	 */

	public BigDecimal getTotalExenta() {
		return totalExenta;
	}


	/**
	 * @param totalExenta the totalExenta to set
	 */

	public void setTotalExenta(BigDecimal totalExenta) {
		this.totalExenta = totalExenta;
	}


	/**
	 * @return the totalGravada
	 */

	public BigDecimal getTotalGravada() {
		return totalGravada;
	}


	/**
	 * @param totalGravada the totalGravada to set
	 */

	public void setTotalGravada(BigDecimal totalGravada) {
		this.totalGravada = totalGravada;
	}


	/**
	 * @return the subTotalVentas
	 */

	public BigDecimal getSubTotalVentas() {
		return subTotalVentas;
	}


	/**
	 * @param subTotalVentas the subTotalVentas to set
	 */

	public void setSubTotalVentas(BigDecimal subTotalVentas) {
		this.subTotalVentas = subTotalVentas;
	}


	/**
	 * @return the descuNoSuj
	 */

	public BigDecimal getDescuNoSuj() {
		return descuNoSuj;
	}


	/**
	 * @param descuNoSuj the descuNoSuj to set
	 */

	public void setDescuNoSuj(BigDecimal descuNoSuj) {
		this.descuNoSuj = descuNoSuj;
	}


	/**
	 * @return the descuExenta
	 */

	public BigDecimal getDescuExenta() {
		return descuExenta;
	}


	/**
	 * @param descuExenta the descuExenta to set
	 */

	public void setDescuExenta(BigDecimal descuExenta) {
		this.descuExenta = descuExenta;
	}


	/**
	 * @return the descuGravada
	 */

	public BigDecimal getDescuGravada() {
		return descuGravada;
	}


	/**
	 * @param descuGravada the descuGravada to set
	 */

	public void setDescuGravada(BigDecimal descuGravada) {
		this.descuGravada = descuGravada;
	}


	/**
	 * @return the porcentajeDescuento
	 */

	public BigDecimal getPorcentajeDescuento() {
		return porcentajeDescuento;
	}


	/**
	 * @param porcentajeDescuento the porcentajeDescuento to set
	 */

	public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}


	/**
	 * @return the totalDescu
	 */

	public BigDecimal getTotalDescu() {
		return totalDescu;
	}


	/**
	 * @param totalDescu the totalDescu to set
	 */

	public void setTotalDescu(BigDecimal totalDescu) {
		this.totalDescu = totalDescu;
	}


	/**
	 * @return the tributos
	 */

	public List<TributosItem> getTributos() {
		return tributos;
	}


	/**
	 * @param tributos the tributos to set
	 */

	public void setTributos(List<TributosItem> tributos) {
		this.tributos = tributos;
	}


	/**
	 * @return the subTotal
	 */

	public BigDecimal getSubTotal() {
		return subTotal;
	}


	/**
	 * @param subTotal the subTotal to set
	 */

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}



	/**
	 * @return the montoTotalOperacion
	 */

	public BigDecimal getMontoTotalOperacion() {
		return montoTotalOperacion;
	}


	/**
	 * @param montoTotalOperacion the montoTotalOperacion to set
	 */

	public void setMontoTotalOperacion(BigDecimal montoTotalOperacion) {
		this.montoTotalOperacion = montoTotalOperacion;
	}


	/**
	 * @return the totalLetras
	 */

	public String getTotalLetras() {
		return totalLetras;
	}


	/**
	 * @param totalLetras the totalLetras to set<br>
	 * The parameter is validated.<br>
	 * "maxLength" : 200
	 */

	public void setTotalLetras(String totalLetras) {
		final int MINLENGTH = 8;
		final int MAXLENGTH = 200;
		int length = totalLetras==null?0:totalLetras.length();

		if( (length>=MINLENGTH && length<=MAXLENGTH) || (totalLetras==null) )
			this.totalLetras = totalLetras;
		else
			throw new IllegalArgumentException("Wrong parameter 'totalLetras' in CreditoFiscal.Resumen.setTotalLetras()" + "\n");
	}


	/**


	/**
	 * @return the observaciones
	 */

	public String getObservaciones() {
		return observaciones;
	}


	/**
	 * @param observaciones the observaciones to set<br>
	 * "minLength" : 1, "maxLength" : 3000; null also possible
	 */

	public void setObservaciones(String observaciones) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 3000;
		int length = observaciones==null?0:observaciones.length();

		if( (length>=MINLENGTH && length<=MAXLENGTH) || (observaciones==null) )
			this.observaciones = observaciones;
		else
			throw new IllegalArgumentException("Wrong parameter 'observaciones' in CreditoFiscal.Resumen.setObservaciones()" + "\n");
	}


	/**


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}

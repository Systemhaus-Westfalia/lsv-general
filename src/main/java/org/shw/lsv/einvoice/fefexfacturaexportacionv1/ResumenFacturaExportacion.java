/**
 * 
 */
package org.shw.lsv.einvoice.fefexfacturaexportacionv1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.shw.lsv.einvoice.utils.EDocumentUtils;
import org.shw.lsv.einvoice.utils.PagosItem;

/**
 * 
 */
public class ResumenFacturaExportacion {

	BigDecimal totalGravada;
	BigDecimal descuento;
	BigDecimal porcentajeDescuento;
	BigDecimal totalDescu;
	BigDecimal seguro=null;  // null allowed
	BigDecimal flete=null;  // null allowed
	BigDecimal montoTotalOperacion;
	BigDecimal totalNoGravado;
	BigDecimal totalPagar;
	String totalLetras;
	int condicionOperacion;
	List<PagosItem> pagos ;  // there must be at least one item
	String codIncoterms=null;  // null allowed
	String descIncoterms=null;  // null allowed
	String numPagoElectronico=null;  // null allowed
	String observaciones=null;  // null allowed   


	/**
	 * No parameters
	 */
	public ResumenFacturaExportacion() {
		this.pagos = new ArrayList<PagosItem>();
	}
	
	/**
	 * Validate the Schema conditions
	 */

	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
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
	 * @return the descuento
	 */

	public BigDecimal getDescuento() {
		return descuento;
	}

	/**
	 * @param descuento the descuento to set
	 */

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
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
	 * @return the seguro
	 */

	public BigDecimal getSeguro() {
		return seguro;
	}

	/**
	 * @param seguro the seguro to set
	 */

	public void setSeguro(BigDecimal seguro) {
		this.seguro = seguro;
	}

	/**
	 * @return the flete
	 */

	public BigDecimal getFlete() {
		return flete;
	}

	/**
	 * @param flete the flete to set
	 */

	public void setFlete(BigDecimal flete) {
		this.flete = flete;
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
	 * @return the totalNoGravado
	 */

	public BigDecimal getTotalNoGravado() {
		return totalNoGravado;
	}


	/**
	 * @param totalNoGravado the totalNoGravado to set
	 */

	public void setTotalNoGravado(BigDecimal totalNoGravado) {
		this.totalNoGravado = totalNoGravado;
	}


	/**
	 * @return the totalPagar
	 */

	public BigDecimal getTotalPagar() {
		return totalPagar;
	}


	/**
	 * @param totalPagar the totalPagar to set
	 */

	public void setTotalPagar(BigDecimal totalPagar) {
		this.totalPagar = totalPagar;
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
		final int MAXLENGTH = 200;
		int length = totalLetras==null?0:totalLetras.length();

		if( length<=MAXLENGTH)
			this.totalLetras = totalLetras;
		else
			throw new IllegalArgumentException("Wrong parameter 'totalLetras' in Factura.Resumen.setTotalLetras()");
	}


	/**
	 * @return the condicionOperacion
	 */

	public int getCondicionOperacion() {
		return condicionOperacion;
	}


	/**
	 * @param condicionOperacion the condicionOperacion to set<br>
	 * The parameter is validated.<br>
	 * "enum" : [1,2,3]
	 */

	public void setCondicionOperacion(int condicionOperacion) {
		if (condicionOperacion==1 || condicionOperacion==2 || condicionOperacion==2)
			this.condicionOperacion = condicionOperacion;
		else
			throw new IllegalArgumentException("Wrong parameter 'condicionOperacion' in Factura.Resumen.setCondicionOperacion()");
	}


	/**
	 * @return the pagos
	 */

	public List<PagosItem> getPagos() {
		return pagos;
	}


	/**
	 * @param pagos the pagos to set
	 */

	public void setPagos(List<PagosItem> pagos) {
		this.pagos = pagos;
	}


	/**
	 * @return the codIncoterms
	 */

	public String getCodIncoterms() {
		return codIncoterms;
	}

	/**
	 * @param codIncoterms the codIncoterms to set<br>
	 * The parameter is validated.<br>
	 * null is possible
	 */

	public void setCodIncoterms(String codIncoterms) {
		this.codIncoterms = codIncoterms;
	}

	/**
	 * @return the descIncoterms
	 */

	public String getDescIncoterms() {
		return descIncoterms;
	}

	/**
	 * @param descIncoterms the descIncoterms to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 3, "maxLength" : 150; null also possible
	 */

	public void setDescIncoterms(String descIncoterms) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 150;
		int length = descIncoterms==null?0:descIncoterms.length();

		if( (length>=MINLENGTH && length<=MAXLENGTH) || (descIncoterms==null) )
			this.descIncoterms = descIncoterms;
		else
			throw new IllegalArgumentException("Wrong parameter 'descIncoterms' in Factura.Resumen.setDescIncoterms()");
	}

	/**
	 * @return the numPagoElectronico
	 */

	public String getNumPagoElectronico() {
		return numPagoElectronico;
	}


	/**
	 * @param numPagoElectronico the numPagoElectronico to set<br>
	 * The parameter is validated.<br>
	 * "maxLength" : 100; null also possible
	 */

	public void setNumPagoElectronico(String numPagoElectronico) {
		final int MAXLENGTH = 100;
		int length = numPagoElectronico==null?0:numPagoElectronico.length();

		if( (length<=MAXLENGTH) || (numPagoElectronico==null) )
			this.numPagoElectronico = numPagoElectronico;
		else
			throw new IllegalArgumentException("Wrong parameter 'numPagoElectronico' in Factura.Resumen.setNumPagoElectronico()");
	}


	/**
	 * @return the observaciones
	 */

	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set<br>
	 * The parameter is validated.<br>
	 * "maxLength" : 500; null also possible
	 */

	public void setObservaciones(String observaciones) {
		final int MAXLENGTH = 500;
		int length = observaciones==null?0:observaciones.length();

		if( (length<=MAXLENGTH) || (observaciones==null) )
			this.observaciones = observaciones;
		else
			throw new IllegalArgumentException("Wrong parameter 'observaciones' in Factura.Resumen.setObservaciones()");
	}

    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

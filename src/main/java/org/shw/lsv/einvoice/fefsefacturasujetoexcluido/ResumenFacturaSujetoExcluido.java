/**
 * 
 */
package org.shw.lsv.einvoice.fefsefacturasujetoexcluido;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.shw.lsv.einvoice.utils.EDocumentUtils;
import org.shw.lsv.einvoice.utils.PagosItem;

/**
 * 
 */
public class ResumenFacturaSujetoExcluido {
	static final String VALIDATION_TOTALGRAVADA_IS_NULL  = "Documento: Nota de Credito, clase: Resumen. Validacion fall??: valor de 'totlaGravada' no debe ser = null";
	static final String VALIDATION_PLAZO_IS_NULL         = "Documento: Nota de Credito, clase: Resumen. Validacion fall??: valor de 'plazo' de pagos no debe ser ='null'";
	static final String VALIDATION_PERIODO_IS_NULL       = "Documento: Nota de Credito, clase: Resumen. Validacion fall??: valor de 'periodo' de pagos no debe ser ='null'";
	static final String VALIDATION_TOTALGRAVADA_IVAPERC1 = "Documento: Nota de Credito, clase: Resumen. Validacion fall??: valor de 'ivaPerci1' no debe ser mayor que cero";
	static final String VALIDATION_TOTALGRAVADA_IVARETE1 = "Documento: Nota de Credito, clase: Resumen. Validacion fall??: valor de 'ivaRete1' no debe ser mayor que cero";
	static final String VALIDATION_TOTALGRAVADA_CONDOP   = "Documento: Nota de Credito, clase: Resumen. Validacion fall??: valor de 'condicionOperacion' no debe ser diferente a 1";

	BigDecimal 	totalDescu;
	BigDecimal 	ivaRete1;
	BigDecimal 	reteRenta;
	BigDecimal 	totalCompra;
	String 	   	totalLetras;
	BigDecimal	descu;
	BigDecimal	totalPagar;
	BigDecimal	subTotal;
	int 		condicionOperacion;
	List<PagosItem> pagos ;  // there must be at least one item
	String observaciones ;  // there must be at least one item
	
    //"pagos",
   // "observaciones"



	/**
	 * No parameters
	 */
	public ResumenFacturaSujetoExcluido() {
		this.pagos = new ArrayList<PagosItem>();
	}

	/**
	 * Validate the Schema conditions
	 */

	public String validateValues() {
		
//		if(getTotalGravada()==null) {
//			return VALIDATION_TOTALGRAVADA_IS_NULL;
//		}
//
//		if(getTotalGravada().compareTo(BigDecimal.ZERO)==0) {
//			if ( (getIvaPerci1()==null) || (getIvaPerci1().compareTo(BigDecimal.ZERO) == 1) )
//				return VALIDATION_TOTALGRAVADA_IVAPERC1;
//		} 
//
//		if(getTotalGravada().compareTo(BigDecimal.ZERO)==0) {
//			if (  (getIvaRete1()==null) || (getIvaRete1().compareTo(BigDecimal.ZERO) == 1) )
//				return VALIDATION_TOTALGRAVADA_IVARETE1;
//		} 
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	/**



    
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public BigDecimal getTotalDescu() {
		return totalDescu;
	}

	public void setTotalDescu(BigDecimal totalDescu) {
		this.totalDescu = totalDescu;
	}

	public BigDecimal getIvaRete1() {
		return ivaRete1;
	}

	public void setIvaRete1(BigDecimal ivaRete1) {
		this.ivaRete1 = ivaRete1;
	}

	public BigDecimal getReteRenta() {
		return reteRenta;
	}

	public void setReteRenta(BigDecimal reteRenta) {
		this.reteRenta = reteRenta;
	}

	public BigDecimal getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(BigDecimal totalCompra) {
		this.totalCompra = totalCompra;
	}

	public String getTotalLetras() {
		return totalLetras;
	}

	public void setTotalLetras(String totalLetras) {
		this.totalLetras = totalLetras;
	}

	public BigDecimal getDescu() {
		return descu;
	}

	public void setDescu(BigDecimal descu) {
		this.descu = descu;
	}

	public BigDecimal getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(BigDecimal totalPagar) {
		this.totalPagar = totalPagar;
	}

	public int getCondicionOperacion() {
		return condicionOperacion;
	}

	public void setCondicionOperacion(int condicionOperacion) {
		this.condicionOperacion = condicionOperacion;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public List<PagosItem> getPagos() {
		return pagos;
	}

	public void setPagos(List<PagosItem> pagos) {
		this.pagos = pagos;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


}

/**
 * 
 */
package org.shw.lsv.einvoice.fencnotadecreditov1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.shw.lsv.einvoice.utils.EDocumentUtils;
import org.shw.lsv.einvoice.utils.TributosItem;

/**
 * 
 */
public class ResumenNotaDeCredito {
	static final String VALIDATION_TOTALGRAVADA_IS_NULL  = "Documento: Nota de Credito, clase: Resumen. Validacion fallo: valor de 'totlaGravada' no debe ser = null";
	static final String VALIDATION_PLAZO_IS_NULL         = "Documento: Nota de Credito, clase: Resumen. Validacion fallo: valor de 'plazo' de pagos no debe ser ='null'";
	static final String VALIDATION_PERIODO_IS_NULL       = "Documento: Nota de Credito, clase: Resumen. Validacion fallo: valor de 'periodo' de pagos no debe ser ='null'";
	static final String VALIDATION_TOTALGRAVADA_IVAPERC1 = "Documento: Nota de Credito, clase: Resumen. Validacion fallo: valor de 'ivaPerci1' no debe ser mayor que cero";
	static final String VALIDATION_TOTALGRAVADA_IVARETE1 = "Documento: Nota de Credito, clase: Resumen. Validacion fallo: valor de 'ivaRete1' no debe ser mayor que cero";
	static final String VALIDATION_TOTALGRAVADA_CONDOP   = "Documento: Nota de Credito, clase: Resumen. Validacion fallo: valor de 'condicionOperacion' no debe ser diferente a 1";

	BigDecimal totalNoSuj;
	BigDecimal totalExenta;
	BigDecimal totalGravada;
	BigDecimal subTotalVentas;
	BigDecimal descuNoSuj;
	BigDecimal descuExenta;
	BigDecimal descuGravada;
	BigDecimal totalDescu;
	List<TributosItem> tributos;
	BigDecimal subTotal;
	BigDecimal ivaPerci1;
	BigDecimal ivaRete1;
	BigDecimal reteRenta;
	BigDecimal montoTotalOperacion;
	String totalLetras;
	int condicionOperacion;



	/**
	 * No parameters
	 */
	public ResumenNotaDeCredito() {
		this.tributos = new ArrayList<TributosItem>();
	}

	/**
	 * Validate the Schema conditions
	 */

	public String validateValues() {
		if(getTotalGravada()==null) {
			return VALIDATION_TOTALGRAVADA_IS_NULL;
		}

		if(getTotalGravada().compareTo(BigDecimal.ZERO)==0) {
			if ( (getIvaPerci1()==null) || (getIvaPerci1().compareTo(BigDecimal.ZERO) == 1) )
				return VALIDATION_TOTALGRAVADA_IVAPERC1;
		} 

		if(getTotalGravada().compareTo(BigDecimal.ZERO)==0) {
			if (  (getIvaRete1()==null) || (getIvaRete1().compareTo(BigDecimal.ZERO) == 1) )
				return VALIDATION_TOTALGRAVADA_IVARETE1;
		} 
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
	 * @return the ivaPerci1
	 */

	public BigDecimal getIvaPerci1() {
		return ivaPerci1;
	}


	/**
	 * @param ivaPerci1 the ivaPerci1 to set
	 */

	public void setIvaPerci1(BigDecimal ivaPerci1) {
		this.ivaPerci1 = ivaPerci1;
	}


	/**
	 * @return the ivaRete1
	 */

	public BigDecimal getIvaRete1() {
		return ivaRete1;
	}


	/**
	 * @param ivaRete1 the ivaRete1 to set
	 */

	public void setIvaRete1(BigDecimal ivaRete1) {
		this.ivaRete1 = ivaRete1;
	}


	/**
	 * @return the reteRenta
	 */

	public BigDecimal getReteRenta() {
		return reteRenta;
	}

	/**
	 * @param reteRenta the reteRenta to set
	 */

	public void setReteRenta(BigDecimal reteRenta) {
		this.reteRenta = reteRenta;
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
		final int MAXLENGTH = 200;
		int length = totalLetras==null?0:totalLetras.length();

		if( length<=MAXLENGTH)
			this.totalLetras = totalLetras;
		else
			throw new IllegalArgumentException("Wrong parameter 'totalLetras' in NotaDeCredito.Resumen.setTotalLetras()" + "\n");
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
	 * "enum" : [1,2, 3]
	 */

	public void setCondicionOperacion(int condicionOperacion) {
		if (condicionOperacion==1 || condicionOperacion==2 || condicionOperacion==2)
			this.condicionOperacion = condicionOperacion;
		else
			throw new IllegalArgumentException("Wrong parameter 'condicionOperacion' in NotaDeCredito.Resumen.setCondicionOperacion()" + "\n");
	}

	public List<TributosItem> getTributos() {
		return tributos;
	}

	public void setTributos(List<TributosItem> tributos) {
		this.tributos = tributos;
	}

    
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}

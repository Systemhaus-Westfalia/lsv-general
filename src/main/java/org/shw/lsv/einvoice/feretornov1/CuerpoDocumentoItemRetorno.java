/**
 *
 */
package org.shw.lsv.einvoice.feretornov1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 *
 */
public class CuerpoDocumentoItemRetorno {
	static final String VALIDATION_VENTAGRAVADA_IS_NULL     = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'ventaGravada' no debe ser = null";
	static final String VALIDATION_TRIBUTOS_NOT_NULL        = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'tributos' debe ser ='null'";
	static final String VALIDATION_TRIBUTOS_EMPTY           = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'tributos' no debe ser vacio";
	static final String VALIDATION_UDM_NOT_99               = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'uniMedida' debe ser =99";
	static final String VALIDATION_TRIBUTOS_IS_NULL         = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'tributos' no debe ser ='null'";
	static final String VALIDATION_TRIBUTOS_NOT_20          = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'tributos' debe ser ='20'";
	static final String VALIDATION_CODTRIBUTO_NOT_NULL      = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'codTributo' debe ser ='null'";
	static final String VALIDATION_TRIBUTOS_PATTERN_FAILED  = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'tributos' no corresponde a patron";

	int numItem;
	int tipoItem;
	String numeroDocumento;
	BigDecimal cantidad;
	String codigo = null;     // null allowed
	String codTributo = null; // null allowed
	int uniMedida;
	String descripcion;
	BigDecimal precioUni;
	BigDecimal montoDescu;
	BigDecimal ventaNoSuj;
	BigDecimal ventaExenta;
	BigDecimal ventaGravada;
	ArrayList<String> tributos = null; // null allowed
	BigDecimal noGravado;
	BigDecimal ivaPerci;
	BigDecimal totalIva;
	BigDecimal ivaRete;


	public CuerpoDocumentoItemRetorno() {
		tributos = new ArrayList<String>();
	}

	public CuerpoDocumentoItemRetorno(int numItem, int tipoItem, String numeroDocumento, BigDecimal cantidad,
			String codigo, String codTributo, int uniMedida, String descripcion, BigDecimal precioUni,
			BigDecimal montoDescu, BigDecimal ventaNoSuj, BigDecimal ventaExenta, BigDecimal ventaGravada,
			ArrayList<String> tributos, BigDecimal noGravado, BigDecimal ivaPerci, BigDecimal totalIva,
			BigDecimal ivaRete) {
		this.numItem = numItem;
		this.tipoItem = tipoItem;
		this.numeroDocumento = numeroDocumento;
		this.cantidad = cantidad;
		this.codigo = codigo;
		this.codTributo = codTributo;
		this.uniMedida = uniMedida;
		this.descripcion = descripcion;
		this.precioUni = precioUni;
		this.montoDescu = montoDescu;
		this.ventaNoSuj = ventaNoSuj;
		this.ventaExenta = ventaExenta;
		this.ventaGravada = ventaGravada;
		this.tributos = tributos;
		this.noGravado = noGravado;
		this.ivaPerci = ivaPerci;
		this.totalIva = totalIva;
		this.ivaRete = ivaRete;
	}

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {

		if (getVentaGravada() == null)
			return VALIDATION_VENTAGRAVADA_IS_NULL;

		Boolean existsTributo = getTributos() != null && !getTributos().isEmpty();

		if (getVentaGravada().compareTo(BigDecimal.ZERO) == 0) {
			if (existsTributo)
				return VALIDATION_TRIBUTOS_NOT_NULL;
		} else {
			if (!existsTributo)
				return VALIDATION_TRIBUTOS_EMPTY;
		}

		if (getTipoItem() == 4) {
			if (getUniMedida() != 99)
				return VALIDATION_UDM_NOT_99;
			if (getCodTributo() == null)
				return VALIDATION_TRIBUTOS_IS_NULL;
			if ((getTributos() == null) || (getTributos().isEmpty()) || (getTributos().get(0) != "20"))
				return VALIDATION_TRIBUTOS_NOT_20;
		} else {
			if (getCodTributo() != null)
				return VALIDATION_CODTRIBUTO_NOT_NULL;

			ArrayList<String> expectedValues = new ArrayList<>(List.of("20", "C3", "59", "71", "D1", "C8", "D5", "D4"));

			if (existsTributo && (expectedValues.indexOf(getTributos().get(0)) == -1))
				return VALIDATION_TRIBUTOS_PATTERN_FAILED;
		}

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
	        throw new IllegalArgumentException("Wrong parameter 'numItem' in NotaDeCredito.CuerpoDocumentoItem.setNumItem()" + "\n");
	}

	public int getTipoItem() {
		return tipoItem;
	}

	/**
	 * @param tipoItem the tipoItem to set<br>
	 * "enum" : [1,2,3,4]
	 */
	public void setTipoItem(int tipoItem) {
		if (tipoItem == 1 || tipoItem == 2 || tipoItem == 3 || tipoItem == 4)
			this.tipoItem = tipoItem;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoItem' in NotaDeCredito.CuerpoDocumentoItem.setTipoItem()" + "\n");
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento the numeroDocumento to set<br>
	 * "minLength" : 1, "maxLength" : 36
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 36;
		int length = numeroDocumento == null ? 0 : numeroDocumento.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.numeroDocumento = numeroDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numeroDocumento' in NotaDeCredito.CuerpoDocumentoItem.setNumeroDocumento()" + "\n");
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
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

		if ((length >= MINLENGTH && length <= MAXLENGTH) || codigo == null)
			this.codigo = codigo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codigo' in NotaDeCredito.CuerpoDocumentoItem.setCodigo()" + "\n");
	}

	public String getCodTributo() {
		return codTributo;
	}

	/**
	 * @param codTributo the codTributo to set<br>
	 * "maxLength" : 2, "minLength" : 2; null also allowed
	 */
	public void setCodTributo(String codTributo) {
		if (codTributo == null || codTributo.compareTo("A8") == 0 || codTributo.compareTo("57") == 0 || codTributo.compareTo("90") == 0 || codTributo.compareTo("D4") == 0 ||
				codTributo.compareTo("D5") == 0 || codTributo.compareTo("25") == 0 || codTributo.compareTo("A6") == 0)
			this.codTributo = codTributo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codTributo' in NotaDeCredito.CuerpoDocumentoItem.setCodTributo()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'uniMedida' in NotaDeCredito.CuerpoDocumentoItem.setUniMedida()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'descripcion' in NotaDeCredito.CuerpoDocumentoItem.setDescripcion()" + "\n");
	}

	public BigDecimal getPrecioUni() {
		return precioUni;
	}

	public void setPrecioUni(BigDecimal precioUni) {
		this.precioUni = precioUni;
	}

	public BigDecimal getMontoDescu() {
		return montoDescu;
	}

	public void setMontoDescu(BigDecimal montoDescu) {
		this.montoDescu = montoDescu;
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

	public ArrayList<String> getTributos() {
		return tributos;
	}

	/**
	 * @param tributos the tributos to set — null or array of 2-char strings
	 */
	public void setTributos(ArrayList<String> tributos) {
		final int MINLENGTH = 1;

		if ((tributos == null) || (tributos.size() >= MINLENGTH))
			this.tributos = tributos;
		else
	        throw new IllegalArgumentException("Wrong expression 'tributos' in NotaDeCredito.CuerpoDocumentoItem.setTributos()" + "\n");
	}

	public BigDecimal getNoGravado() {
		return noGravado;
	}

	public void setNoGravado(BigDecimal noGravado) {
		this.noGravado = noGravado;
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}

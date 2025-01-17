/**
 * 
 */
package org.shw.lsv.einvoice.fefsefacturasujetoexcluido;

import java.math.BigDecimal;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * 
 */
public class CuerpoDocumentoItemFacturaSujetoExcluido {
	static final String VALIDATION_VENTAGRAVADA_IS_NULL     = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fall??: valor de 'ventaGravada' no debe ser = null";
	static final String VALIDATION_TRIBUTOS_NOT_NULL        = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fall??: valor de 'tributos' debe ser ='null'";
	static final String VALIDATION_TRIBUTOS_EMPTY           = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fall??: valor de 'tributos' no debe ser vac??o";
	static final String VALIDATION_UDM_NOT_99               = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fall??: valor de 'uniMedida' debe ser =99";
	static final String VALIDATION_TRIBUTOS_IS_NULL         = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fall??: valor de 'tributos' no debe ser ='null'";
	static final String VALIDATION_TRIBUTOS_NOT_20          = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fall??: valor de 'tributos' debe ser ='20'";
	static final String VALIDATION_CODTRIBUTO_NOT_NULL      = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fall??: valor de 'codTributo' debe ser ='null'";
	static final String VALIDATION_TRIBUTOS_PATTERN_FAILED  = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fall??: valor de 'tributos' no corresponde a patr??n";
	
	int 		numItem;
	int 		tipoItem;
	BigDecimal 	cantidad;
	String 		codigo=null;  // null allowed
	int 		uniMedida;
	String 		descripcion;
	BigDecimal 	precioUni;
	BigDecimal 	montoDescu;
	BigDecimal 	compra;
	
    
    
    /**
	 * Constructor without parameters. 
	 */
	public CuerpoDocumentoItemFacturaSujetoExcluido() {
		
	}

	/**
	 * @param numItem
	 * @param tipoItem
	 * @param numeroDocumento
	 * @param cantidad
	 * @param codigo
	 * @param codTributo
	 * @param uniMedida
	 * @param descripcion
	 * @param precioUni
	 * @param montoDescu
	 * @param ventaNoSuj
	 * @param ventaExenta
	 * @param ventaGravada
	 * @param tributos
	 * @param psv
	 * @param noGravado
	 * @param ivaItem
	 */
	

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
//		if(getVentaGravada()==null) {
//			return VALIDATION_VENTAGRAVADA_IS_NULL;
//		}
//
//		if(getVentaGravada().compareTo(BigDecimal.ZERO)==0) {
//			if (getTributos()!=null)
//				return VALIDATION_TRIBUTOS_NOT_NULL;
//		} else {
//			if ( (getTributos()==null) || (getTributos().isEmpty()) )
//				return VALIDATION_TRIBUTOS_EMPTY;
//		}
//		
//		if(getTipoItem()==4) {
//			if (getUniMedida()!=99)
//				return VALIDATION_UDM_NOT_99;
//			if (getCodTributo()==null)
//				return VALIDATION_TRIBUTOS_IS_NULL;
//			if ( (getTributos()==null) || (getTributos().isEmpty())  || (getTributos().get(0) !="20"))
//				return VALIDATION_TRIBUTOS_NOT_20;
//		} else {
//			if (getCodTributo()!=null)
//				return VALIDATION_CODTRIBUTO_NOT_NULL;
//			
//			ArrayList<String> expectedValues=  new ArrayList<>(List.of( "20", "C3", "59", "71", "D1", "C8", "D5", "D4"));
//			// Here, is only ONE item expected; where there are MANY items expected, the query must be changed.
//			if ( (getTributos()!=null) && ( (getTributos().isEmpty()) || (expectedValues.indexOf(getTributos().get(0))==-1)) )
//				return VALIDATION_TRIBUTOS_PATTERN_FAILED;			
//		}
//		
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	
	public int getNumItem() {
		return numItem;
	}

	public void setNumItem(int numItem) {
		this.numItem = numItem;
	}

	public int getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(int tipoItem) {
		this.tipoItem = tipoItem;
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
	


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getUniMedida() {
		return uniMedida;
	}

	public void setUniMedida(int uniMedida) {
		this.uniMedida = uniMedida;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public BigDecimal getCompra() {
		return compra;
	}

	public void setCompra(BigDecimal compra) {
		this.compra = compra;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

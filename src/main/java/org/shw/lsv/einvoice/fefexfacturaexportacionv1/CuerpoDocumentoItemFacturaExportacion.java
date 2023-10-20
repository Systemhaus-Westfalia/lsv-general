/**
 * 
 */
package org.shw.lsv.einvoice.fefexfacturaexportacionv1;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * 
 */
public class CuerpoDocumentoItemFacturaExportacion {
	static final String VALIDATION_NOGRAVADO_IS_NULL = "Documento: Factura de Exportacion, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'noGravado' no debe ser ='null'";
	static final String VALIDATION_PRECIOUNI_IS_NULL = "Documento: Factura de Exportacion, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'precioUni' no debe ser ='null'";
	static final String VALIDATION_TRIBUTOS          = "Documento: Factura de Exportacion, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'tributos' debe ser ='C3'";
	
	int numItem;
	BigDecimal cantidad;
	String codigo=null;  // null allowed
	int uniMedida;
	String descripcion;
	BigDecimal precioUni;
	BigDecimal montoDescu;
	BigDecimal ventaGravada;
	ArrayList<String> tributos=null;  // null allowed
	BigDecimal noGravado;
    
    
    /**
	 * Constructor without parameters. 
	 */
	public CuerpoDocumentoItemFacturaExportacion() {
		this.tributos= new ArrayList<String>();
	}	

	/**
	 * @param numItem
	 * @param cantidad
	 * @param codigo
	 * @param uniMedida
	 * @param descripcion
	 * @param precioUni
	 * @param montoDescu
	 * @param ventaGravada
	 * @param tributos
	 * @param noGravado
	 */
	public CuerpoDocumentoItemFacturaExportacion(int numItem, BigDecimal cantidad, String codigo, int uniMedida, String descripcion,
			BigDecimal precioUni, BigDecimal montoDescu, BigDecimal ventaGravada, ArrayList<String> tributos,
			BigDecimal noGravado) {
		this.numItem = numItem;
		this.cantidad = cantidad;
		this.codigo = codigo;
		this.uniMedida = uniMedida;
		this.descripcion = descripcion;
		this.precioUni = precioUni;
		this.montoDescu = montoDescu;
		this.ventaGravada = ventaGravada;
		this.tributos = tributos;
		this.noGravado = noGravado;
	}



	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		if(getNoGravado()==null) {
			return VALIDATION_NOGRAVADO_IS_NULL;
		}

		if(getNoGravado().compareTo(BigDecimal.ZERO)==0) {
			if (getPrecioUni()== null)
				return VALIDATION_PRECIOUNI_IS_NULL;
			if ( (getTributos()==null) || (getTributos().size()!=1) || (getTributos().get(0) !="C3"))
				return VALIDATION_TRIBUTOS;
		} 
		
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	/**
	 * @return the numItem
	 */
	public int getNumItem() {
		return numItem;
	}

	/**
	 * @param numItem the numItem to set<br>
	 * The parameter is validated.<br>
	 * "minimum" : 1, "maximum" : 2000
	 */
	public void setNumItem(int numItem) {
		final int MINIMUM = 1;
		final int MAXIMUM = 2000;
		
		if(numItem>=MINIMUM && numItem<=MAXIMUM)
			this.numItem = numItem;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numItem' in FacturaExportacion.CuerpoDocumentoItem.setNumItem()" + "\n");
	}


	/**
	 * @return the cantidad
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 * Condition according to schema: "multipleOf" : 0.00000001
	 */
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 200; null also possible
	 */
	public void setCodigo(String codigo) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 200;
		int length = codigo==null?0:codigo.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (codigo==null) )
			this.codigo = codigo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codigo' in FacturaExportacion.CuerpoDocumentoItem.setCodigo()" + "\n");
	}

	/**
	 * @return the uniMedida
	 */
	public int getUniMedida() {
		return uniMedida;
	}

	/**
	 * @param uniMedida the uniMedida to set<br>
	 * The parameter is validated.<br>
	 * "minimum" : 1, "maximum" : 99
	 */
	public void setUniMedida(int uniMedida) {
		final int MINIMUM = 1;
		final int MAXIMUM = 99;
		
		if(uniMedida>=MINIMUM && uniMedida<=MAXIMUM)
			this.uniMedida = uniMedida;
		else
	        throw new IllegalArgumentException("Wrong parameter 'uniMedida' in FacturaExportacion.CuerpoDocumentoItem.setUniMedida()" + "\n");
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set<br>
	 * The parameter is validated.<br>
	 * "maxLength" : 1000
	 */
	public void setDescripcion(String descripcion) {
		final int MAXLENGTH = 1000;
		int length = descripcion==null?0:descripcion.length();
		
		if(length<=MAXLENGTH)
			this.descripcion = descripcion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'descripcion' in FacturaExportacion.CuerpoDocumentoItem.setDescripcion()" + "\n");
	}
	
	/**
	 * @return the precioUni
	 */
	public BigDecimal getPrecioUni() {
		return precioUni;
	}

	/**
	 * @param precioUni the precioUni to set
	 * Condition according to schema: "multipleOf" : 0.00000001
	 */
	public void setPrecioUni(BigDecimal precioUni) {
		this.precioUni = precioUni;
	}

	/**
	 * @return the montoDescu
	 */
	public BigDecimal getMontoDescu() {
		return montoDescu;
	}

	/**
	 * @param montoDescu the montoDescu to set
	 * Condition according to schema: "multipleOf" : 0.00000001
	 */
	public void setMontoDescu(BigDecimal montoDescu) {
		this.montoDescu = montoDescu;
	}

	/**
	 * @return the ventaGravada
	 */
	public BigDecimal getVentaGravada() {
		return ventaGravada;
	}

	/**
	 * @param ventaGravada the ventaGravada to set
	 * Condition according to schema: "multipleOf" : 0.00000001
	 */
	public void setVentaGravada(BigDecimal ventaGravada) {
		this.ventaGravada = ventaGravada;
	}


	/**
	 * @return the noGravado
	 */
	public BigDecimal getNoGravado() {
		return noGravado;
	}

	/**
	 * @param noGravado the noGravado to set
	 * Condition according to schema: "multipleOf" : 0.00000001
	 */
	public void setNoGravado(BigDecimal noGravado) {
		this.noGravado = noGravado;
	}

	/**
	 * @return the tributos
	 */
	public ArrayList<String> getTributos() {
		return tributos;
	}

	/**
	 * @param tributos the tributos to set
	 * Very complex logic: either null or a two-character string
	 * "type" : ["array", "null"], "items" : {"type" : "string", "maxLength" : 2, "minLength" : 2}, "minItems": 1
	 */
	public void setTributos(ArrayList<String> tributos) {
		final int MINLENGTH = 1;
		
		// TODO : Check contents of strings ("maxLength": 2,, "minLength": 2)
		if( (tributos==null) || (tributos.size()>=MINLENGTH) )
			this.tributos = tributos;
		else
	        throw new IllegalArgumentException("Wrong expression 'tributos' in FacturaExportacion.CuerpoDocumentoItem.setTributos()" + "\n");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

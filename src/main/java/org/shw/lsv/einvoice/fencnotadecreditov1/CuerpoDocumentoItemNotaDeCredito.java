/**
 * 
 */
package org.shw.lsv.einvoice.fencnotadecreditov1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * 
 */
public class CuerpoDocumentoItemNotaDeCredito {
	static final String VALIDATION_VENTAGRAVADA_IS_NULL     = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'ventaGravada' no debe ser = null";
	static final String VALIDATION_TRIBUTOS_NOT_NULL        = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'tributos' debe ser ='null'";
	static final String VALIDATION_TRIBUTOS_EMPTY           = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'tributos' no debe ser vacoo";
	static final String VALIDATION_UDM_NOT_99               = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'uniMedida' debe ser =99";
	static final String VALIDATION_TRIBUTOS_IS_NULL         = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'tributos' no debe ser ='null'";
	static final String VALIDATION_TRIBUTOS_NOT_20          = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'tributos' debe ser ='20'";
	static final String VALIDATION_CODTRIBUTO_NOT_NULL      = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'codTributo' debe ser ='null'";
	static final String VALIDATION_TRIBUTOS_PATTERN_FAILED  = "Documento: Nota de Credito, clase: CuerpoDocumentoItem. Validacion fallo: valor de 'tributos' no corresponde a patron";
	
	int numItem;
	int tipoItem;
	String numeroDocumento=null;  // null allowed
	BigDecimal cantidad;
	String codigo=null;  // null allowed
	String codTributo=null;  // null allowed
	int uniMedida;
	String descripcion;
	BigDecimal precioUni;
	BigDecimal montoDescu;
	BigDecimal ventaNoSuj;
	BigDecimal ventaExenta;
	BigDecimal ventaGravada;
	ArrayList<String> tributos=null;  // null allowed
    
    
    /**
	 * Constructor without parameters. 
	 */
	public CuerpoDocumentoItemNotaDeCredito() {
		tributos = new ArrayList<String>();
		
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
	 */
	public CuerpoDocumentoItemNotaDeCredito(int numItem, int tipoItem, String numeroDocumento, BigDecimal cantidad, String codigo,
			String codTributo, int uniMedida, String descripcion, BigDecimal precioUni, BigDecimal montoDescu,
			BigDecimal ventaNoSuj, BigDecimal ventaExenta, BigDecimal ventaGravada, ArrayList<String> tributos) {
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
	}

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		if(getVentaGravada()==null) {
			return VALIDATION_VENTAGRAVADA_IS_NULL;
		}

		if(getVentaGravada().compareTo(BigDecimal.ZERO)==0) {
			if (getTributos()!=null)
				return VALIDATION_TRIBUTOS_NOT_NULL;
		} else {
			if ( (getTributos()==null) || (getTributos().isEmpty()) )
				return VALIDATION_TRIBUTOS_EMPTY;
		}
		
		if(getTipoItem()==4) {
			if (getUniMedida()!=99)
				return VALIDATION_UDM_NOT_99;
			if (getCodTributo()==null)
				return VALIDATION_TRIBUTOS_IS_NULL;
			if ( (getTributos()==null) || (getTributos().isEmpty())  || (getTributos().get(0) !="20"))
				return VALIDATION_TRIBUTOS_NOT_20;
		} else {
			if (getCodTributo()!=null)
				return VALIDATION_CODTRIBUTO_NOT_NULL;
			
			ArrayList<String> expectedValues=  new ArrayList<>(List.of( "20", "C3", "59", "71", "D1", "C8", "D5", "D4"));
			// Here, is only ONE item expected; where there are MANY items expected, the query must be changed.
			if ( (getTributos()!=null) && ( (getTributos().isEmpty()) || (expectedValues.indexOf(getTributos().get(0))==-1)) )
				return VALIDATION_TRIBUTOS_PATTERN_FAILED;			
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
	        throw new IllegalArgumentException("Wrong parameter 'numItem' in NotaDeCredito.CuerpoDocumentoItem.setNumItem()" + "\n");
	}


	/**
	 * @return the tipoItem
	 */
	public int getTipoItem() {
		return tipoItem;
	}

	/**
	 * @param tipoItem the tipoItem to set<br>
	 * The parameter is validated.<br>
	 * "enum" : [1,2,3,4]
	 */
	public void setTipoItem(int tipoItem) {
		if (tipoItem==1 || tipoItem==2 || tipoItem==3 || tipoItem==4)
			this.tipoItem = tipoItem;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoItem' in NotaDeCredito.CuerpoDocumentoItem.setTipoItem()" + "\n");
	}

	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento the numeroDocumento to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 36
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 36;
		int length = numeroDocumento==null?0:numeroDocumento.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.numeroDocumento = numeroDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numeroDocumento' in NotaDeCredito.CuerpoDocumentoItem.setNumeroDocumento()" + "\n");
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
	 * "minLength" : 1, "maxLength" : 25; null also allowed
	 */
	public void setCodigo(String codigo) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 25;
		int length = codigo==null?0:codigo.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (codigo==null) )
			this.codigo = codigo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codigo' in NotaDeCredito.CuerpoDocumentoItem.setCodigo()" + "\n");
	}

	/**
	 * @return the codTributo
	 */
	public String getCodTributo() {
		return codTributo;
	}

	/**
	 * @param codTributo the codTributo to set<br>
	 * The parameter is validated.<br>
	 * "enum" : ["A8", "57", "90", "D4", "D5", "25", "A6"]; null also allowed
	 */
	public void setCodTributo(String codTributo) {
		if (codTributo==null || codTributo.compareTo("A8")==0 || codTributo.compareTo("57")==0 || codTributo.compareTo("90")==0 || codTributo.compareTo("D4")==0 || 
				codTributo.compareTo("D5")==0 || codTributo.compareTo("25")==0 || codTributo.compareTo("A6")==0)
			this.codTributo = codTributo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codTributo' in NotaDeCredito.CuerpoDocumentoItem.setCodTributo()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'uniMedida' in NotaDeCredito.CuerpoDocumentoItem.setUniMedida()" + "\n");
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
	 * "maxLength" : 1000; null also allowed
	 */
	public void setDescripcion(String descripcion) {
		final int MAXLENGTH = 1000;
		int length = descripcion==null?0:descripcion.length();
		
		if(length<=MAXLENGTH)
		if( (length<=MAXLENGTH) || (descripcion==null) )
			this.descripcion = descripcion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'descripcion' in NotaDeCredito.CuerpoDocumentoItem.setDescripcion()" + "\n");
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
	 * @return the ventaNoSuj
	 */
	public BigDecimal getVentaNoSuj() {
		return ventaNoSuj;
	}

	/**
	 * @param ventaNoSuj the ventaNoSuj to set
	 * Condition according to schema: "multipleOf" : 0.00000001
	 */
	public void setVentaNoSuj(BigDecimal ventaNoSuj) {
		this.ventaNoSuj = ventaNoSuj;
	}

	/**
	 * @return the ventaExenta
	 */
	public BigDecimal getVentaExenta() {
		return ventaExenta;
	}

	/**
	 * @param ventaExenta the ventaExenta to set
	 * Condition according to schema: "multipleOf" : 0.00000001
	 */
	public void setVentaExenta(BigDecimal ventaExenta) {
		this.ventaExenta = ventaExenta;
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
	        throw new IllegalArgumentException("Wrong expression 'tributos' in NotaDeCredito.CuerpoDocumentoItem.setTributos()" + "\n");
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

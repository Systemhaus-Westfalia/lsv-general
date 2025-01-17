/**
 * 
 */
package org.shw.lsv.einvoice.fefcfacturaelectronicav1;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 
 */
public class TributosItemFactura {
	
	String codigo;
	String descripcion;
	BigDecimal valor;
	
	
	/**
	 * @param codigo
	 * @param descripcion
	 * @param valor
	 */
	public TributosItemFactura(String codigo, String descripcion, BigDecimal valor) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.valor = valor;
	}
	
	public TributosItemFactura() {
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
	 * "minLength" : 2, "maxLength" : 2<br>
	 * Specific values allowed according to schema.
	 */
	public void setCodigo(String codigo) {
		final int MINLENGTH = 2;
		final int MAXLENGTH = 2;
		int length = codigo==null?0:codigo.length();		

		String[] validCodigos = { "20", "C3", "59", "71", "D1", "C8", "C5", "C6", "C7", "D5", "19", "28", "31", "32",
				"33", "34", "35", "36", "37", "38", "39", "42", "43", "44", "50", "51", "52", "53", "54", "55",
                "58", "77", "78", "79", "85", "86", "91", "92", "A1", "A5", "A7", "A9" };		
		boolean isCodigoValid = (!(codigo==null) && Arrays.stream(validCodigos).anyMatch(codigo::equals));
		
		if(length>=MINLENGTH && length<=MAXLENGTH && isCodigoValid)
			this.codigo = codigo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codigo' in Factura.TributosItem.setCodigo()" + "\n");
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
	 * "minLength" : 2, "maxLength" : 150
	 */
	public void setDescripcion(String descripcion) {
		final int MINLENGTH = 2;
		final int MAXLENGTH = 150;
		int length = descripcion==null?0:descripcion.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.descripcion = descripcion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'descripcion' in Factura.TributosItem.setDescripcion()" + "\n");
	}


	/**
	 * @return the valor
	 */
	public BigDecimal getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 * Condition according to schema: "multipleOf" : 0.01
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

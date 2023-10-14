/**
 * 
 */
package org.shw.lsv.einvoice.utils;

import java.math.BigDecimal;

/**
 * 
 */
public class TributosItem {
	
	String codigo;
	String descripcion;
	BigDecimal valor;
	
	
	/**
	 * @param codigo
	 * @param descripcion
	 * @param valor
	 */
	public TributosItem(String codigo, String descripcion, BigDecimal valor) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.valor = valor;
	}
	
	/**
	 * Constructor without parameters
	 */
	public TributosItem() {
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
	 * "minLength" : 2, "maxLength" : 2
	 */
	public void setCodigo(String codigo) {
		final int MINLENGTH = 2;
		final int MAXLENGTH = 2;
		int length = codigo==null?0:codigo.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.codigo = codigo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codigo' in CreditoFiscal.TributosItem.setCodigo()");
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
	        throw new IllegalArgumentException("Wrong parameter 'descripcion' in CreditoFiscal.TributosItem.setDescripcion()");
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

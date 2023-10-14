/**
 * 
 */
package org.shw.lsv.einvoice.utils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * 
 */
public class PagosItem {
	
	String codigo;
	BigDecimal montoPago;
	String referencia=null;  // null possible;
	String plazo;
	Integer periodo=null;  // null possible;

	/**
	 * no params
	 */
	public PagosItem() {
	}
	
	/**
	 * @param codigo
	 * @param montoPago
	 * @param referencia
	 * @param plazo
	 * @param periodo
	 */
	public PagosItem(String codigo, BigDecimal montoPago, String referencia, String plazo, Integer periodo) {
		super();
		this.codigo = codigo;
		this.montoPago = montoPago;
		this.referencia = referencia;
		this.plazo = plazo;
		this.periodo = periodo;
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
	 * "pattern" : "^(0[1-9]||1[0-4]||99)$"
	 */
	public void setCodigo(String codigo) {
		final String PATTERN = "^(0[1-9]||1[0-4]||99)$";
		final int LENGTH = 2;
		boolean patternOK = (codigo!=null) && Pattern.matches(PATTERN, codigo);  
		
		if(patternOK && codigo!=null && codigo.length()==LENGTH)
			this.codigo = codigo;
		else
	        throw new IllegalArgumentException("Wrong expression 'codigo' in (POJO).PagosItem.setCodigo()");
	}

	/**
	 * @return the montoPago
	 */
	public BigDecimal getMontoPago() {
		return montoPago;
	}

	/**
	 * @param montoPago the montoPago to set
	 */
	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}

	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}

	/**
	 * @param referencia the referencia to set<br>
	 * The parameter is validated.<br>
	 * "maxLength" : 50; null also possible
	 */
	public void setReferencia(String referencia) {
		final int MAXLENGTH = 50;
		int length = referencia==null?0:referencia.length();
		
		if( (length<=MAXLENGTH) || (referencia==null) )
			this.referencia = referencia;
		else
	        throw new IllegalArgumentException("Wrong parameter 'referencia' in (POJO).PagosItem.setReferencia()");
	}

	/**
	 * @return the plazo
	 */
	public String getPlazo() {
		return plazo;
	}

	/**
	 * @param plazo the plazo to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^0[1-3]$"
	 */
	public void setPlazo(String plazo) {
		final String PATTERN = "^0[1-3]$";
		boolean patternOK = (plazo!=null) && Pattern.matches(PATTERN, plazo);  
		
		if(patternOK)
			this.plazo = plazo;
		else
	        throw new IllegalArgumentException("Wrong expression 'plazo' in (POJO).PagosItem.setPlazo()");
	}

	/**
	 * @return the periodo
	 */
	public Integer getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

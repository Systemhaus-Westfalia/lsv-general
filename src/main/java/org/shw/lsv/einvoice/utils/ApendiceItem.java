/**
 * 
 */
package org.shw.lsv.einvoice.utils;

/**
 * 
 */
public abstract class ApendiceItem {
	static final String VALIDATION_RESULT_OK = "OK";
	
	String campo;
	String etiqueta;
	String valor;
	
	/**
	 * No Parameters Actualizado	
	 */
	public ApendiceItem() {

	}
	
	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		return VALIDATION_RESULT_OK;
	}

	/**
	 * @return the campo
	 */
	public String getCampo() {
		return campo;
	}

	/**
	 * @param campo the campo to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 2, "maxLength" : 25
	 */
	public void setCampo(String campo) {
		final int MINLENGTH = 2;
		final int MAXLENGTH = 25;
		int length = campo==null?0:campo.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.campo = campo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'campo' in (POJO).ApendiceItem.setCampo()");
	}

	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta the etiqueta to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 3, "maxLength" : 50
	 */
	public void setEtiqueta(String etiqueta) {
		final int MINLENGTH = 3;
		final int MAXLENGTH = 50;
		int length = etiqueta.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.etiqueta = etiqueta;
		else
	        throw new IllegalArgumentException("Wrong parameter 'etiqueta' in (POJO).ApendiceItem.setEtiqueta()");
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 150
	 */
	public void setValor(String valor) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 150;
		int length = valor.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.valor = valor;
		else
	        throw new IllegalArgumentException("Wrong parameter 'valor' in (POJO).ApendiceItem.setValor()");
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

/**
 * 
 */
package org.shw.lsv.einvoice.fefsefacturasujetoexcluido;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * 
 */
public class ExtensionFacturaSujetoExcluido {
	
	String nombEntrega=null;  // null allowed
	String docuEntrega=null;  // null allowed
	String nombRecibe=null;  // null allowed
	String docuRecibe=null;  // null allowed
	String observaciones=null;  // null allowed
	
	/**
	 * No parameters
	 */
	public ExtensionFacturaSujetoExcluido() {

	}
	
	/**
	 * Validate the Schema conditions
	 */

	public String validateValues() {		
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	/**
	 * @return the nombEntrega
	 */

	public String getNombEntrega() {
		return nombEntrega;
	}

	/**
	 * @param nombEntrega the nombEntrega to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 100; null also possible
	 */

	public void setNombEntrega(String nombEntrega) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 100;
		int length = nombEntrega==null?0:nombEntrega.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (nombEntrega==null) )
			this.nombEntrega = nombEntrega;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombEntrega' in NotaDeCredito.Extension.setNombEntrega()");
	}

	/**
	 * @return the docuEntrega
	 */

	public String getDocuEntrega() {
		return docuEntrega;
	}

	/**
	 * @param docuEntrega the docuEntrega to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 25; null also possible
	 */

	public void setDocuEntrega(String docuEntrega) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 25;
		int length = docuEntrega==null?0:docuEntrega.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (docuEntrega==null) )
			this.docuEntrega = docuEntrega;
		else
	        throw new IllegalArgumentException("Wrong parameter 'docuEntrega' in NotaDeCredito.Extension.setDocuEntrega()");
	}

	/**
	 * @return the nombRecibe
	 */

	public String getNombRecibe() {
		return nombRecibe;
	}

	/**
	 * @param nombRecibe the nombRecibe to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 100; null also possible
	 */

	public void setNombRecibe(String nombRecibe) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 100;
		int length = nombRecibe==null?0:nombRecibe.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (nombRecibe==null) )
			this.nombRecibe = nombRecibe;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombRecibe' in NotaDeCredito.Extension.setNombRecibe()");
	}

	/**
	 * @return the docuRecibe
	 */

	public String getDocuRecibe() {
		return docuRecibe;
	}

	/**
	 * @param docuRecibe the docuRecibe to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 25; null also possible
	 */

	public void setDocuRecibe(String docuRecibe) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 25;
		int length = docuRecibe==null?0:docuRecibe.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (docuRecibe==null) )
			this.docuRecibe = docuRecibe;
		else
	        throw new IllegalArgumentException("Wrong parameter 'docuRecibe' in NotaDeCredito.Extension.setDocuRecibe()");
	}

	/**
	 * @return the observaciones
	 */

	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set<br>
	 * The parameter is validated.<br>
	 * "maxLength" : 3000; null also possible
	 */

	public void setObservaciones(String observaciones) {
		final int MAXLENGTH = 3000;
		int length = observaciones==null?0:observaciones.length();
		
		if( (length<=MAXLENGTH) || (observaciones==null) )
			this.observaciones = observaciones;
		else
	        throw new IllegalArgumentException("Wrong parameter 'observaciones' in NotaDeCredito.Extension.setObservaciones()");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

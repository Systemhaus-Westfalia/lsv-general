/**
 * 
 */
package org.shw.lsv.einvoice.fefcfacturaelectronicav1;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * 
 */
public class ExtensionFactura {
	
	String nombEntrega=null;  // null allowed
	String docuEntrega=null;  // null allowed
	String nombRecibe=null;  // null allowed
	String docuRecibe=null;  // null allowed
	String observaciones=null;  // null allowed
	String placaVehiculo=null;  // null allowed
	
	/**
	 * No parameters
	 */
	public ExtensionFactura() {

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
	        throw new IllegalArgumentException("Wrong parameter 'nombEntrega' in Factura.Extension.setNombEntrega()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'docuEntrega' in Factura.Extension.setDocuEntrega()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'nombRecibe' in Factura.Extension.setNombRecibe()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'docuRecibe' in Factura.Extension.setDocuRecibe()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'observaciones' in Factura.Extension.setObservaciones()" + "\n");
	}

	/**
	 * @return the placaVehiculo
	 */
	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	/**
	 * @param placaVehiculo the placaVehiculo to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 2, "maxLength" : 10; null also possible
	 */
	public void setPlacaVehiculo(String placaVehiculo) {
		final int MINLENGTH = 2;
		final int MAXLENGTH = 10;
		int length = placaVehiculo==null?0:placaVehiculo.length();
		
		if( (length>=MINLENGTH && length<=MAXLENGTH) || (placaVehiculo==null) )
			this.placaVehiculo = placaVehiculo;
		else
	        throw new IllegalArgumentException("Wrong parameter 'placaVehiculo' in Factura.Extension.setPlacaVehiculo()" + "\n");
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

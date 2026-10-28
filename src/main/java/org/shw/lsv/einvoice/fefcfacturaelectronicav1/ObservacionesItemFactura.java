/**
 * 
 */
package org.shw.lsv.einvoice.fefcfacturaelectronicav1;


/**
 * 
 */
public class ObservacionesItemFactura {
	
	String descripcion;
	
	
	/**
	 * @param codigo
	 * @param descripcion
	 * @param valor
	 */
	public ObservacionesItemFactura(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public ObservacionesItemFactura() {
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
		final int MAXLENGTH = 3000;
		int length = descripcion==null?0:descripcion.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.descripcion = descripcion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'descripcion' in Factura.TributosItem.setDescripcion()" + "\n");
	}


	/**

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

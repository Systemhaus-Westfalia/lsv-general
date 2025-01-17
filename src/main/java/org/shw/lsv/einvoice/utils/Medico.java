/**
 * 
 */
package org.shw.lsv.einvoice.utils;

import java.util.regex.Pattern;

/**
 * 
 */
public class Medico {
	static final String VALIDATION_RESULT_OK = "OK";
	static final String VALIDATION_DOCIDENTIFICATION_IS_NULL = "Documento: POJO, clase: Medico. Validacion fall??: valor de 'docIdentificacion' no debe ser ='null'";
	static final String VALIDATION_NIT_IS_NULL               = "Documento: POJO, clase: Medico. Validacion fall??: valor de 'nit' no debe ser ='null'";
	
	String nombre;
	String nit;
	String docIdentificacion;
	int tipoServicio;  // en schema: "type" : "number", "minimum" : 1, "maximum" : 6. Das w??re double, w??re aber unsinnig 
	
	/**
	 * No parameters
	 */
	public Medico() {
	}

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		if(getNit()==null) {
			if (getDocIdentificacion()==null) {
				return VALIDATION_DOCIDENTIFICATION_IS_NULL;
			}
		}
		
		if(getDocIdentificacion()==null) {
			if (getNit()==null) {
				return VALIDATION_NIT_IS_NULL;
			}
		}
		return VALIDATION_RESULT_OK;
	}
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set<br>
	 * The parameter is validated.<br>
	 * "maxLength" : 100
	 */
	public void setNombre(String nombre) {
		final int MAXLENGTH = 100;
		int length = nombre==null?0:nombre.length();
		
		if(length<=MAXLENGTH)
			this.nombre = nombre;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombre' in (POJO).Medico.setNombre()");
	}

	/**
	 * @return the nit
	 */
	public String getNit() {
		return nit;
	}

	/**
	 * @param nit the nit to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^([0-9]{14}|[0-9]{9})$"
	 */
	public void setNit(String nit) {
		final String PATTERN = "^([0-9]{14}|[0-9]{9})$";
		boolean patternOK = (nit!=null) && Pattern.matches(PATTERN, nit);  
		
		if(patternOK)
			this.nit = nit;
		else
	        throw new IllegalArgumentException("Wrong expression 'nit' in (POJO).Medico.setNit()");
	}

	/**
	 * @return the docIdentificacion
	 */
	public String getDocIdentificacion() {
		return docIdentificacion;
	}

	/**
	 * @param docIdentificacion the docIdentificacion to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 2, "maxLength" : 25
	 */
	public void setDocIdentificacion(String docIdentificacion) {
		final int MINLENGTH = 2;
		final int MAXLENGTH = 25;
		int length = docIdentificacion==null?0:docIdentificacion.length();
		
		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.docIdentificacion = docIdentificacion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'docIdentificacion' in (POJO).Medico.setDocIdentificacion()");
	}


	/**
	 * @return the tipoServicio
	 */
	public int getTipoServicio() {
		return tipoServicio;
	}



	/**
	 * @param tipoServicio the tipoServicio to set<br>
	 * The parameter is validated.<br>
	 * "minimum" : 1, "maximum" : 6
	 */
	public void setTipoServicio(int tipoServicio) {
		final int MINIMUM = 1;
		final int MAXIMUM = 6;
		
		if(tipoServicio>=MINIMUM && tipoServicio<=MAXIMUM)
			this.tipoServicio = tipoServicio;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoServicio' in (POJO).Medico.setTipoServicio()");
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

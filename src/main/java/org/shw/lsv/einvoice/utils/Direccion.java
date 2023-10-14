package org.shw.lsv.einvoice.utils;

import java.util.regex.Pattern;

public class Direccion {
	static final String VALIDATION_RESULT_OK = "OK";
	static final String VALIDATION_MUNICIPIO_PATTERN_FAILED = "Documento: POJO, clase: Direccion. Validacion fall??: valor de 'municipio' no corresponde a patr??n";

	String departamento;
	String municipio;
	String complemento;

	/**
	 * @param departamento
	 * @param municipio
	 * @param complemento
	 */
	public Direccion(String departamento, String municipio, String complemento) {
		this.departamento = departamento;
		this.municipio = municipio;
		this.complemento = complemento;
	}
	

	public Direccion() {
	}
	
	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		String pattern;
		boolean patternOK;

		// In schema: "pattern" : "^0[1-9]|1[0-2]$"
		if(getDepartamento().equals("01")) {
			pattern = "^0[1-9]|1[0-2]$";
			patternOK = (getMunicipio()!=null) && Pattern.matches(pattern, getMunicipio());
			if(!patternOK)
				return VALIDATION_MUNICIPIO_PATTERN_FAILED;
		}

		// In schema: "pattern" : "^0[1-9]|1[0-3]$"
		if(getDepartamento().equals("02") || getDepartamento().equals("10")) {
			pattern = "^0[1-9]|1[0-3]$";
			patternOK = (getMunicipio()!=null) && Pattern.matches(pattern, getMunicipio());
			if(!patternOK)
				return VALIDATION_MUNICIPIO_PATTERN_FAILED;
		} 

		// In schema: "pattern" : "^0[1-9]|1[0-6]$"
		if(getDepartamento().equals("03") || getDepartamento().equals("07")) {
			pattern = "^0[1-9]|1[0-6]$";
			patternOK = (getMunicipio()!=null) && Pattern.matches(pattern, getMunicipio());
			if(!patternOK)
				return VALIDATION_MUNICIPIO_PATTERN_FAILED;
		}  
		
		// In schema: "pattern" : "^0[1-9]|[12][0-9]|3[0-3]$"
		if(getDepartamento().equals("04")) {
			pattern = "^0[1-9]|[12][0-9]|3[0-3]$";
			patternOK = (getMunicipio()!=null) && Pattern.matches(pattern, getMunicipio());
			if(!patternOK)
				return VALIDATION_MUNICIPIO_PATTERN_FAILED;
		} 
		
		// In schema: "pattern" : "^0[1-9]|1[0-9]|2[0-2]$"
		if(getDepartamento().equals("05") || getDepartamento().equals("08")) {
			pattern = "^0[1-9]|1[0-9]|2[0-2]$";
			patternOK = (getMunicipio()!=null) && Pattern.matches(pattern, getMunicipio());
			if(!patternOK)
				return VALIDATION_MUNICIPIO_PATTERN_FAILED;
		}  
		
		// In schema: "pattern" : "^0[1-9]|1[0-9]$"
		if(getDepartamento().equals("06")) {
			pattern = "^0[1-9]|1[0-9]$";
			patternOK = (getMunicipio()!=null) && Pattern.matches(pattern, getMunicipio());
			if(!patternOK)
				return VALIDATION_MUNICIPIO_PATTERN_FAILED;
		}  

		// In schema: "pattern" : "^0[1-9]$"
		if(getDepartamento().equals("09")) {
			pattern = "^0[1-9]$";
			patternOK = (getMunicipio()!=null) && Pattern.matches(pattern, getMunicipio());
			if(!patternOK)
				return VALIDATION_MUNICIPIO_PATTERN_FAILED;
		}

		// In schema: "pattern" : "^0[1-9]|1[0-9]|2[0-3]$"
		if(getDepartamento().equals("11")) {
			pattern = "^0[1-9]|1[0-9]|2[0-3]$";
			patternOK = (getMunicipio()!=null) && Pattern.matches(pattern, getMunicipio());
			if(!patternOK)
				return VALIDATION_MUNICIPIO_PATTERN_FAILED;
		}
		
		// In schema: "pattern" : "^0[1-9]|1[0-9]|20$"
		if(getDepartamento().equals("12")) {
			pattern = "^0[1-9]|1[0-9]|20$";
			patternOK = (getMunicipio()!=null) && Pattern.matches(pattern, getMunicipio());
			if(!patternOK)
				return VALIDATION_MUNICIPIO_PATTERN_FAILED;
		}

		// In schema: "pattern" : "^0[1-9]|1[0-9]|2[0-6]$"
		if(getDepartamento().equals("13")) {
			pattern = "^0[1-9]|1[0-9]|2[0-6]$";
			patternOK = (getMunicipio()!=null) && Pattern.matches(pattern, getMunicipio());
			if(!patternOK)
				return VALIDATION_MUNICIPIO_PATTERN_FAILED;
		}
		// In schema: "pattern" : "^0[1-9]|1[0-8]$"
		if(getDepartamento().equals("14")) {
			pattern = "^0[1-9]|1[0-8]$";
			patternOK = (getMunicipio()!=null) && Pattern.matches(pattern, getMunicipio());
			if(!patternOK)
				return VALIDATION_MUNICIPIO_PATTERN_FAILED;
		}

		return VALIDATION_RESULT_OK;
	}

	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^0[1-9]|1[0-4]$"
	 */
	public void setDepartamento(String departamento) {
		String pattern = "^0[1-9]|1[0-4]$";
		boolean patternOK = (departamento!=null) && Pattern.matches(pattern, departamento);  

		if(patternOK)
			this.departamento = departamento;
		else
			throw new IllegalArgumentException("Wrong expression 'departamento' in (POJO).Direccion.setDepartamento()");
	}

	/**
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio the municipio to set<br>
	 * The parameter is validated.<br>
	 * "pattern" : "^[0-9]{2}$"
	 */
	public void setMunicipio(String municipio) {
		final String PATTERN = "^[0-9]{2}$";
		boolean patternOK = (municipio!=null) && Pattern.matches(PATTERN, municipio);  

		if(patternOK)
			this.municipio = municipio;
		else
			throw new IllegalArgumentException("Wrong expression 'municipio' in (POJO).Direccion.setMunicipio()");
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemento the complemento to set<br>
	 * The parameter is validated.<br>
	 * "minLength" : 1, "maxLength" : 200
	 */
	public void setComplemento(String complemento) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 200;
		int length = complemento==null?0:complemento.length();

		if(length>=MINLENGTH && length<=MAXLENGTH)
			this.complemento = complemento;
		else
			throw new IllegalArgumentException("Wrong parameter 'complemento' in (POJO).Direccion.setComplemento()");
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

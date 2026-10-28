package org.shw.lsv.einvoice.feretornov1;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

public class EmisorRetorno {

	String nit;
	String nombre;
	String codEstableMH;
	String codEstable = null;     // null allowed
	String codPuntoVentaMH;
	String codPuntoVenta = null;  // null allowed
	String recintoFiscal = null;  // null allowed
	String tipoRegimen = null;    // null allowed
	String regimen = null;        // null allowed
	Integer tipoItemExpor = null; // null allowed


	public EmisorRetorno() {
	}

	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	public String getNit() {
		return nit;
	}

	/**
	 * @param nit the nit to set<br>
	 * "maxLength" : 14
	 */
	public void setNit(String nit) {
		final int MAXLENGTH = 14;
		int length = nit == null ? 0 : nit.length();

		if (nit != null && length <= MAXLENGTH)
			this.nit = nit;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nit' in EventoDeRetorno.Emisor.setNit()" + "\n");
	}

	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set<br>
	 * "minLength" : 1, "maxLength" : 250
	 */
	public void setNombre(String nombre) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 250;
		int length = nombre == null ? 0 : nombre.length();

		if (length >= MINLENGTH && length <= MAXLENGTH)
			this.nombre = nombre;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombre' in EventoDeRetorno.Emisor.setNombre()" + "\n");
	}

	public String getCodEstableMH() {
		return codEstableMH;
	}

	/**
	 * @param codEstableMH the codEstableMH to set<br>
	 * "minLength" : 4, "maxLength" : 4
	 */
	public void setCodEstableMH(String codEstableMH) {
		final int EXACTLENGTH = 4;
		int length = codEstableMH == null ? 0 : codEstableMH.length();

		if (length == EXACTLENGTH)
			this.codEstableMH = codEstableMH;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codEstableMH' in EventoDeRetorno.Emisor.setCodEstableMH()" + "\n");
	}

	public String getCodEstable() {
		return codEstable;
	}

	/**
	 * @param codEstable the codEstable to set<br>
	 * "minLength" : 1, "maxLength" : 10; null also allowed
	 */
	public void setCodEstable(String codEstable) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 10;
		int length = codEstable == null ? 0 : codEstable.length();

		if (codEstable == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.codEstable = codEstable;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codEstable' in EventoDeRetorno.Emisor.setCodEstable()" + "\n");
	}

	public String getCodPuntoVentaMH() {
		return codPuntoVentaMH;
	}

	/**
	 * @param codPuntoVentaMH the codPuntoVentaMH to set<br>
	 * "minLength" : 4, "maxLength" : 4
	 */
	public void setCodPuntoVentaMH(String codPuntoVentaMH) {
		final int EXACTLENGTH = 4;
		int length = codPuntoVentaMH == null ? 0 : codPuntoVentaMH.length();

		if (length == EXACTLENGTH)
			this.codPuntoVentaMH = codPuntoVentaMH;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codPuntoVentaMH' in EventoDeRetorno.Emisor.setCodPuntoVentaMH()" + "\n");
	}

	public String getCodPuntoVenta() {
		return codPuntoVenta;
	}

	/**
	 * @param codPuntoVenta the codPuntoVenta to set<br>
	 * "minLength" : 1, "maxLength" : 15; null also allowed
	 */
	public void setCodPuntoVenta(String codPuntoVenta) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 15;
		int length = codPuntoVenta == null ? 0 : codPuntoVenta.length();

		if (codPuntoVenta == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.codPuntoVenta = codPuntoVenta;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codPuntoVenta' in EventoDeRetorno.Emisor.setCodPuntoVenta()" + "\n");
	}

	public String getRecintoFiscal() {
		return recintoFiscal;
	}

	/**
	 * @param recintoFiscal the recintoFiscal to set<br>
	 * "minLength" : 2, "maxLength" : 2; null also allowed
	 */
	public void setRecintoFiscal(String recintoFiscal) {
		final int EXACTLENGTH = 2;
		int length = recintoFiscal == null ? 0 : recintoFiscal.length();

		if (recintoFiscal == null || length == EXACTLENGTH)
			this.recintoFiscal = recintoFiscal;
		else
	        throw new IllegalArgumentException("Wrong parameter 'recintoFiscal' in EventoDeRetorno.Emisor.setRecintoFiscal()" + "\n");
	}

	public String getTipoRegimen() {
		return tipoRegimen;
	}

	/**
	 * @param tipoRegimen the tipoRegimen to set<br>
	 * "minLength" : 1, "maxLength" : 4; null also allowed
	 */
	public void setTipoRegimen(String tipoRegimen) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 4;
		int length = tipoRegimen == null ? 0 : tipoRegimen.length();

		if (tipoRegimen == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.tipoRegimen = tipoRegimen;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoRegimen' in EventoDeRetorno.Emisor.setTipoRegimen()" + "\n");
	}

	public String getRegimen() {
		return regimen;
	}

	/**
	 * @param regimen the regimen to set<br>
	 * "minLength" : 1, "maxLength" : 13; null also allowed
	 */
	public void setRegimen(String regimen) {
		final int MINLENGTH = 1;
		final int MAXLENGTH = 13;
		int length = regimen == null ? 0 : regimen.length();

		if (regimen == null || (length >= MINLENGTH && length <= MAXLENGTH))
			this.regimen = regimen;
		else
	        throw new IllegalArgumentException("Wrong parameter 'regimen' in EventoDeRetorno.Emisor.setRegimen()" + "\n");
	}

	public Integer getTipoItemExpor() {
		return tipoItemExpor;
	}

	/**
	 * @param tipoItemExpor the tipoItemExpor to set; null also allowed
	 */
	public void setTipoItemExpor(Integer tipoItemExpor) {
		this.tipoItemExpor = tipoItemExpor;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

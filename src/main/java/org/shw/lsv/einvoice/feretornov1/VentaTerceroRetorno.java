package org.shw.lsv.einvoice.feretornov1;

import org.shw.lsv.einvoice.utils.VentaTercero;

public class VentaTerceroRetorno extends VentaTercero {

	Integer codDomiciliado = null; // null allowed

	public Integer getCodDomiciliado() {
		return codDomiciliado;
	}

	/**
	 * @param codDomiciliado the codDomiciliado to set; null also allowed
	 */
	public void setCodDomiciliado(Integer codDomiciliado) {
		this.codDomiciliado = codDomiciliado;
	}

}

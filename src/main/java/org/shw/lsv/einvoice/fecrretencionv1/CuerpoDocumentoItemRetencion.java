/**
 * 
 */
package org.shw.lsv.einvoice.fecrretencionv1;

import java.math.BigDecimal;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

/**
 * 
 */
public class CuerpoDocumentoItemRetencion {
	
	int numItem;
	String tipoDte;
	int tipoDoc;
	String numDocumento;
	String fechaEmision;
	BigDecimal montoSujetoGrav;
	String codigoRetencionMH;
	BigDecimal ivaRetenido;
	String descripcion;
	
    
    /**
	 * Constructor without parameters. 
	 */
	public CuerpoDocumentoItemRetencion() {
		
	}

	

	/**
	 * Validate the Schema conditions
	 * Schema is wrong.
	 * According to schema
	 * if(getTipoDoc()==1)  -> codGeneracion PATTERN = "^[a-zA-Z0-9]{1,20}$"
	 * if(getTipoDoc()==2)  -> codGeneracion PATTERN = "^[A-F0-9]{8}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{4}-[A-F0-9]{12}$"
	 * But there is no codGeneracion in this class.
	 */
	public String validateValues() {	
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	/**
	 * @return the numItem
	 */
	public int getNumItem() {
		return numItem;
	}

	/**
	 * @param numItem the numItem to set<br>
	 * The parameter is validated.<br>
	 * "minimum" : 1, "maximum" : 500
	 */
	public void setNumItem(int numItem) {
		final int MINIMUM = 1;
		final int MAXIMUM = 500;
		
		if(numItem>=MINIMUM && numItem<=MAXIMUM)
			this.numItem = numItem;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numItem' in Retencion.CuerpoDocumentoItem.setNumItem()");
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return the tipoDte
	 */
	public String getTipoDte() {
		return tipoDte;
	}



	/**
	 * @param tipoDte the tipoDte to set<br>
	 * The parameter is validated.<br>
	 * "enum" : ["14", "03", "01"]
	 */
	public void setTipoDte(String tipoDte) {
		if (tipoDte.compareTo("14")==0 || tipoDte.compareTo("03")==0 || tipoDte.compareTo("01")==0)
			this.tipoDte = tipoDte;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoDte' in Retencion.CuerpoDocumentoItem.setTipoDte()");
	}



	/**
	 * @return the tipoDoc
	 */
	public int getTipoDoc() {
		return tipoDoc;
	}



	/**
	 * @param tipoDoc the tipoDoc to set<br>
	 * The parameter is validated.<br>
	 * "enum" : [1,2]
	 */
	public void setTipoDoc(int tipoDoc) {
		if (tipoDoc==1 || tipoDoc==2)
			this.tipoDoc = tipoDoc;
		else
	        throw new IllegalArgumentException("Wrong parameter 'tipoDoc' in Retencion.CuerpoDocumentoItem.setTipoDoc()");
	}



	/**
	 * @return the numDocumento
	 */
	public String getNumDocumento() {
		return numDocumento;
	}



	/**
	 * @param numDocumento the numDocumento to set
	 * Schema allows only strings
	 */
	public void setNumDocumento(String numDocumento) {
		if (numDocumento!=null)
			this.numDocumento = numDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numDocumento' in Retencion.CuerpoDocumentoItem.setNumDocumento()");
	}



	/**
	 * @return the fechaEmision
	 */
	public String getFechaEmision() {
		return fechaEmision;
	}



	/**
	 * @param fechaEmision the fechaEmision to set<br>
	 * null not allowed
	 */
	public void setFechaEmision(String fechaEmision) {
		if (fechaEmision!=null)
			this.fechaEmision = fechaEmision;
		else
	        throw new IllegalArgumentException("Wrong parameter 'fechaEmision' in Retencion.CuerpoDocumentoItem.setFechaEmision()");
	}



	/**
	 * @return the montoSujetoGrav
	 */
	public BigDecimal getMontoSujetoGrav() {
		return montoSujetoGrav;
	}



	/**
	 * @param montoSujetoGrav the montoSujetoGrav to set
	 */
	public void setMontoSujetoGrav(BigDecimal montoSujetoGrav) {
		this.montoSujetoGrav = montoSujetoGrav;
	}



	/**
	 * @return the codigoRetencionMH
	 */
	public String getCodigoRetencionMH() {
		return codigoRetencionMH;
	}



	/**
	 * @param codigoRetencionMH the codigoRetencionMH to set<br>
	 * The parameter is validated.<br>
	 * "enum" : ["22", "C4", "C9"]
	 */
	public void setCodigoRetencionMH(String codigoRetencionMH) {
		if (codigoRetencionMH.compareTo("22")==0 || codigoRetencionMH.compareTo("C4")==0 || codigoRetencionMH.compareTo("C9")==0)
			this.codigoRetencionMH = codigoRetencionMH;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codigoRetencionMH' in Retencion.CuerpoDocumentoItem.setCodigoRetencionMH()");
	}



	/**
	 * @return the ivaRetenido
	 */
	public BigDecimal getIvaRetenido() {
		return ivaRetenido;
	}



	/**
	 * @param ivaRetenido the ivaRetenido to set
	 */
	public void setIvaRetenido(BigDecimal ivaRetenido) {
		this.ivaRetenido = ivaRetenido;
	}



	/**
	 * @param descripcion the descripcion to set<br>
	 * The parameter is validated.<br>
	 * "maxLength" : 1000
	 */
	public void setDescripcion(String descripcion) {
		final int MAXLENGTH = 1000;
		int length = descripcion==null?0:descripcion.length();
		
		if(length<=MAXLENGTH)
			this.descripcion = descripcion;
		else
	        throw new IllegalArgumentException("Wrong parameter 'descripcion' in Retencion.CuerpoDocumentoItem.setDescripcion()");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

/**
 * 
 */
package org.shw.lsv.einvoice.fefexfacturaexportacionv1;

import java.util.Objects;

import org.shw.lsv.einvoice.utils.EDocumentUtils;

;

/**
 * 
 */
public class OtrosDocumentosItemFacturaExportacion {
	static final String VALIDATION_MODOTRANSP_IS_NULL       = "Documento: Factura de Exportacion, clase: OtrosDocumentosItem. Validacion fall??: valor de 'modoTransp' no debe ser ='null'";
	static final String VALIDATION_NUMCONDUCTOR_IS_NULL     = "Documento: Factura de Exportacion, clase: OtrosDocumentosItem. Validacion fall??: valor de 'numCoductor' no debe ser ='null'";
	static final String VALIDATION_NOMBRECONDUCTOR_IS_NULL  = "Documento: Factura de Exportacion, clase: OtrosDocumentosItem. Validacion fall??: valor de 'nombreConductor' no debe ser ='null'";
	static final String VALIDATION_PLACATRANS_IS_NULL       = "Documento: Factura de Exportacion, clase: OtrosDocumentosItem. Validacion fall??: valor de 'placaTrans' no debe ser ='null'";
	static final String VALIDATION_NUMCONDUCTOR_NOT_NULL    = "Documento: Factura de Exportacion, clase: OtrosDocumentosItem. Validacion fall??: valor de 'numCoductor' debe ser ='null'";
	static final String VALIDATION_NOMBRECONDUCTOR_NOT_NULL = "Documento: Factura de Exportacion, clase: OtrosDocumentosItem. Validacion fall??: valor de 'nombreConductor' debe ser ='null'";
	static final String VALIDATION_PLACATRANS_NOT_NULL      = "Documento: Factura de Exportacion, clase: OtrosDocumentosItem. Validacion fall??: valor de 'placaTrans' debe ser ='null'";
	static final String VALIDATION_MODOTRANSP_NOT_NULL      = "Documento: Factura de Exportacion, clase: OtrosDocumentosItem. Validacion fall??: valor de 'modoTransp' no debe ser ='null'";
	static final String VALIDATION_DESCDOCUMENTO_IS_NULL    = "Documento: Factura de Exportacion, clase: OtrosDocumentosItem. Validacion fall??: valor de 'descDocumento' no debe ser ='null'";
	static final String VALIDATION_DETALLEDOCUMENTO_IS_NULL = "Documento: Factura de Exportacion, clase: OtrosDocumentosItem. Validacion fall??: valor de 'detalleDocumento' no debe ser ='null'";
	

	int codDocAsociado;
	String descDocumento=null;  // null allowed
	String detalleDocumento=null;  // null allowed
	String placaTrans=null;  // null allowed
	Integer modoTransp=null;  // null erlaubt
	String numConductor=null;  // null allowed
	String nombreConductor=null;  // null allowed
	
	/**
	 * No Parameters
	 */
	public OtrosDocumentosItemFacturaExportacion() {
	}

	/**
	 * Validate the Schema conditions
	 */

	public String validateValues() {
		if(getCodDocAsociado()==4){
			if (Objects.isNull(getModoTransp()))
				return VALIDATION_MODOTRANSP_IS_NULL;
			if (getNumConductor()==null)
				return VALIDATION_NUMCONDUCTOR_IS_NULL;
			if (getNombreConductor()==null)
				return VALIDATION_NOMBRECONDUCTOR_IS_NULL;
			if (getPlacaTrans()==null)
				return VALIDATION_PLACATRANS_IS_NULL;
		} else  {
			if (getNumConductor()!=null)
				return VALIDATION_NUMCONDUCTOR_NOT_NULL;
			if (getNombreConductor()!=null)
				return VALIDATION_NOMBRECONDUCTOR_NOT_NULL;
			if (getPlacaTrans()!=null)
				return VALIDATION_PLACATRANS_NOT_NULL;
			if (!(Objects.isNull(getModoTransp())) )
				return VALIDATION_MODOTRANSP_NOT_NULL;
		}
		
		if(getCodDocAsociado()==1 || getCodDocAsociado()==2 ){
			if (getDescDocumento()==null)
				return VALIDATION_DESCDOCUMENTO_IS_NULL;
			if (getDetalleDocumento()==null)
				return VALIDATION_DETALLEDOCUMENTO_IS_NULL;
		} 
		
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	/**
	 * @return the codDocAsociado
	 */

	public int getCodDocAsociado() {
		return codDocAsociado;
	}


	/**
	 * @param codDocAsociado the codDocAsociado to set<br>
	 * The parameter is validated.<br>
	 * "minimum" : 1, "maximum" : 4
	 */

	public void setCodDocAsociado(int codDocAsociado) {
		final int MINIMUM = 1;
		final int MAXIMUM = 4;
		
		if(codDocAsociado>=MINIMUM && codDocAsociado<=MAXIMUM)
			this.codDocAsociado = codDocAsociado;
		else
	        throw new IllegalArgumentException("Wrong parameter 'codDocAsociado' in FacturaExportacion.OtrosDocumentosItem.setCodDocAsociado()");
	}


	/**
	 * @return the descDocumento
	 */

	public String getDescDocumento() {
		return descDocumento;
	}

	/**
	 * @param descDocumento the descDocumento to set<br>
	 * The parameter is validated; null also possible.<br>
	 * "maxLength" : 100
	 */

	public void setDescDocumento(String descDocumento) {
		final int MAXLENGTH = 100;
		int length = descDocumento==null?0:descDocumento.length();
		
		if( (length<=MAXLENGTH)  || (descDocumento==null) )
			this.descDocumento = descDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'descDocumento' in FacturaExportacion.OtrosDocumentosItem.setDescDocumento()");
	}

	/**
	 * @return the detalleDocumento
	 */

	public String getDetalleDocumento() {
		return detalleDocumento;
	}

	/**
	 * @param detalleDocumento the detalleDocumento to set<br>
	 * The parameter is validated; null also possible.<br>.<br>
	 * "maxLength" : 300
	 */

	public void setDetalleDocumento(String detalleDocumento) {
		final int MAXLENGTH = 300;
		int length = detalleDocumento==null?0:detalleDocumento.length();
		
		if( (length<=MAXLENGTH)  || (detalleDocumento==null) )
			this.detalleDocumento = detalleDocumento;
		else
	        throw new IllegalArgumentException("Wrong parameter 'detalleDocumento' in FacturaExportacion.OtrosDocumentosItem.setDetalleDocumento()");
	}


	/**
	 * @return the placaTrans
	 */

	public String getPlacaTrans() {
		return placaTrans;
	}

	/**
	 * @param placaTrans the placaTrans to set<br>
	 * The parameter is validated; null also possible.<br>
	 * "minLength" : 5, "maxLength" : 70
	 */

	public void setPlacaTrans(String placaTrans) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 70;
		int length = placaTrans==null?0:placaTrans.length();
		
		if( (placaTrans==null) || (length>=MINLENGTH && length<=MAXLENGTH) )
			this.placaTrans = placaTrans;
		else
	        throw new IllegalArgumentException("Wrong parameter 'placaTrans' in FacturaExportacion.OtrosDocumentosItem.setPlacaTrans()");
	}

	/**
	 * @return the modoTransp
	 */

	public Integer getModoTransp() {
		return modoTransp;
	}

	/**
	 * @param modoTransp the modoTransp to set<br>
	 * The parameter is validated.<br>
	 * "enum" : [1,2,3,4,5,6,7], null
	 * "minimum" : 1, "maximum" : 4. This contradicts the Enum. The enum is taken.
	 */

	public void setModoTransp(Integer modoTransp) {
		if ( (modoTransp==null) || (modoTransp>=1 && modoTransp<=7) )
			this.modoTransp = modoTransp;
		else
	        throw new IllegalArgumentException("Wrong parameter 'modoTransp' in FacturaExportacion.OtrosDocumentosItem.setModoTransp()");
	}

	/**
	 * @return the numConductor
	 */

	public String getNumConductor() {
		return numConductor;
	}

	/**
	 * @param numConductor the numConductor to set<br>
	 * The parameter is validated; null also possible.<br>
	 * "minLength" : 5, "maxLength" : 100
	 */

	public void setNumConductor(String numConductor) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 100;
		int length = numConductor==null?0:placaTrans.length();
		
		if( (numConductor==null) || (length>=MINLENGTH && length<=MAXLENGTH) )
			this.numConductor = numConductor;
		else
	        throw new IllegalArgumentException("Wrong parameter 'numConductor' in FacturaExportacion.OtrosDocumentosItem.setNumConductor()");
	}

	/**
	 * @return the nombreConductor
	 */

	public String getNombreConductor() {
		return nombreConductor;
	}

	/**
	 * @param nombreConductor the nombreConductor to set<br>
	 * The parameter is validated; null also possible.<br>
	 * "minLength" : 5, "maxLength" : 200
	 */

	public void setNombreConductor(String nombreConductor) {
		final int MINLENGTH = 5;
		final int MAXLENGTH = 200;
		int length = nombreConductor==null?0:nombreConductor.length();
		
		if( (nombreConductor==null) || (length>=MINLENGTH && length<=MAXLENGTH) )
			this.nombreConductor = nombreConductor;
		else
	        throw new IllegalArgumentException("Wrong parameter 'nombreConductor' in FacturaExportacion.OtrosDocumentosItem.setNombreConductor()");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

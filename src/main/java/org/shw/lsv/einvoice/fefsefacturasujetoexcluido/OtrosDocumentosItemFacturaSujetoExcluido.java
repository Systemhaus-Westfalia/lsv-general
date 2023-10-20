/**
 * 
 */
package org.shw.lsv.einvoice.fefsefacturasujetoexcluido;

import java.util.Objects;

import org.shw.lsv.einvoice.utils.EDocumentUtils;
import org.shw.lsv.einvoice.utils.Medico;

/**
 * 
 */
public class OtrosDocumentosItemFacturaSujetoExcluido {
	static final String VALIDATION_MEDICO_IS_NULL            = "Documento: Factura, clase: OtrosDocumentosItem. Validacion fall??: valor de 'medico' no debe ser ='null'";
	static final String VALIDATION_DESCDOCUMENTO_NOT_NULL    = "Documento: Factura, clase: OtrosDocumentosItem. Validacion fall??: valor de 'descDocumento' debe ser ='null'";
	static final String VALIDATION_DETALLEDOCUMENTO_NOT_NULL = "Documento: Factura, clase: OtrosDocumentosItem. Validacion fall??: valor de 'detalleDocumento' debe ser ='null'";
	static final String VALIDATION_MEDICO_NOT_NULL           = "Documento: Factura, clase: OtrosDocumentosItem. Validacion fall??: valor de 'medico' debe ser ='null'";
	static final String VALIDATION_DESCDOCUMENTO_IS_NULL     = "Documento: Factura, clase: OtrosDocumentosItem. Validacion fall??: valor de 'descDocumento' no debe ser ='null'";
	static final String VALIDATION_DETALLEDOCUMENTO_IS_NULL  = "Documento: Factura, clase: OtrosDocumentosItem. Validacion fall??: valor de 'detalleDocumento' no debe ser ='null'";

	int codDocAsociado;
	String descDocumento=null;  // null allowed
	String detalleDocumento=null;  // null allowed
	Medico medico=null;  // null allowed
	
	/**
	 * No Parameters
	 */
	public OtrosDocumentosItemFacturaSujetoExcluido() {
		this.medico = new Medico();
	}

	/**
	 * Validate the Schema conditions
	 */

	public String validateValues() {
		if(getCodDocAsociado()==3) {
			if (Objects.isNull(getMedico()))
				return VALIDATION_MEDICO_IS_NULL;
			if (getDescDocumento()!=null)
				return VALIDATION_DESCDOCUMENTO_NOT_NULL;
			if (getDetalleDocumento()!=null)
				return VALIDATION_DETALLEDOCUMENTO_NOT_NULL;
		} else  {
			if (!Objects.isNull(getMedico()))
				return VALIDATION_MEDICO_NOT_NULL;
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
	        throw new IllegalArgumentException("Wrong parameter 'codDocAsociado' in FacturaSujetoExcluido.OtrosDocumentosItem.setCodDocAsociado()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'descDocumento' in FacturaSujetoExcluido.OtrosDocumentosItem.setDescDocumento()" + "\n");
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
	        throw new IllegalArgumentException("Wrong parameter 'detalleDocumento' in FacturaSujetoExcluido.OtrosDocumentosItem.setDetalleDocumento()" + "\n");
	}

	/**
	 * @return the medico
	 */

	public Medico getMedico() {
		return medico;
	}

	/**
	 * @param medico the medico to set
	 */

	public void setMedico(Medico medico) {
		this.medico = medico;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

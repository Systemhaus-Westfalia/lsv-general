/**
 * 
 */
package org.shw.lsv.einvoice.fefsefacturasujetoexcluido;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.utils.EDocument;
import org.shw.lsv.einvoice.utils.EDocumentUtils;
import org.shw.lsv.einvoice.utils.PagosItem;


/**
 * 
 */
public class FacturaSujetoExcluido extends EDocument {
	static final int MINIMUM_TOTOTAL_OF_OPERATION = 1095;
	static final int CUERPODOCUMENTO_MAXIMUM_ITEMS = 2000;

	static final String VALIDATION_RESUMEN_OR_MONTO_IS_NULL   = "Documento: Factura, clase: FacturaElectronica. Validacion fall??: valor de 'getResumen() y getMontoTotalOperacion()' no debe ser ='null'";
	static final String VALIDATION_TIPODOCUMENTO_IS_NULL      = "Documento: Factura, clase: FacturaElectronica. Validacion fall??: valor de 'tipoDocumento' no debe ser ='null'";
	static final String VALIDATION_NUMDOCUMENTO_IS_NULL       = "Documento: Factura, clase: FacturaElectronica. Validacion fall??: valor de 'numDocumento' no debe ser ='null'";
	static final String VALIDATION_NOMBRE_IS_NULL             = "Documento: Factura, clase: FacturaElectronica. Validacion fall??: valor de 'NOMBRE' no debe ser ='null'";
	static final String VALIDATION_CUERPODOCUMENTO_MAX_ITEMS  = "Documento: Factura, clase: FacturaElectronica. Validacion fall??: valor de 'cuerpoDocumento' debe  contener m??ximo " + CUERPODOCUMENTO_MAXIMUM_ITEMS + " elementos";
	
	static final String ERROR_SUJETO_EXCLUIDO_IDENTIFICACION     		= "Documento: Sujeto Excluido, error en fillIdentification(): ";
	static final String ERROR_SUJETO_EXCLUIDO_EMISOR          			= "Documento: Sujeto Excluido, error en fillEmisor(): ";
	static final String ERROR_SUJETO_EXCLUIDO_SUJETO_EXCLUIDO        	= "Documento: Sujeto Excluido, error en fillSujetoExcluido(): ";
	static final String ERROR_SUJETO_EXCLUIDO_DOCUMENTOS_RELACIONADOS	= "Documento: Sujeto Excluido, error en fillDocumentosRelacionados(): ";
	static final String ERROR_SUJETO_EXCLUIDO_CUERPO_DOCUMENTO			= "Documento: Sujeto Excluido, error en fillCuerpoDocumento(): ";
	static final String ERROR_SUJETO_EXCLUIDO_RESUMEN            		= "Documento: Sujeto Excluido, error en fillResumen(): ";
	
	IdentificacionFacturaSujetoExcluido identificacion;
	EmisorFacturaSujetoExcluido emisor;
	//ReceptorFacturaSujetoExcluido receptor;
	List<CuerpoDocumentoItemFacturaSujetoExcluido> cuerpoDocumento;
	ResumenFacturaSujetoExcluido resumen;
	SujetoExcluidoFacturaSujetoExcluido sujetoExcluido;

	List<ApendiceItemFacturaSujetoExcluido> apendice=null;  // null allowed
	List<DocumentoRelacionadoItemFacturaSujetoExcluido> documentoRelacionado = null;

	/**
	 * No parameters
	 */
	public FacturaSujetoExcluido() {
		this.identificacion       			= new IdentificacionFacturaSujetoExcluido();
		this.emisor               			= new EmisorFacturaSujetoExcluido();
		//this.receptor             			= new ReceptorFacturaSujetoExcluido();
	    this.cuerpoDocumento      			= new ArrayList<CuerpoDocumentoItemFacturaSujetoExcluido>();
		this.resumen              			= new ResumenFacturaSujetoExcluido();
		this.sujetoExcluido 				= new SujetoExcluidoFacturaSujetoExcluido();
		
		//leer
		this.apendice						= new ArrayList<ApendiceItemFacturaSujetoExcluido>();
		this.documentoRelacionado			= new ArrayList<DocumentoRelacionadoItemFacturaSujetoExcluido>();
		}

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {		
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	/**
	 * @return the identificacion
	 */
	public IdentificacionFacturaSujetoExcluido getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(IdentificacionFacturaSujetoExcluido identificacion) {
		this.identificacion = identificacion;
	}


	/**
	 * @param identificacion the (IdentificacionFactura) identificacion to set
	 */
	public StringBuffer fillIdentification(JSONObject factoryInput) {
		System.out.println("Start FacturaNoSujeto.fillIdentificacion()");

		JSONObject identificationJson = factoryInput.getJSONObject(IDENTIFICACION);
		try {identificacion.setMotivoContin(identificationJson.getString(MOTIVOCONTIN));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
		try {identificacion.setTipoContingencia(identificationJson.getInt(TIPOCONTINGENCIA));} 		catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
		try {identificacion.setNumeroControl(identificationJson.getString(NUMEROCONTROL));} 		catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
		try {identificacion.setCodigoGeneracion(identificationJson.getString(CODIGOGENERACION));} 	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
		try {identificacion.setTipoModelo(identificationJson.getInt(TIPOMODELO));} 					catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
		try {identificacion.setTipoOperacion(identificationJson.getInt(TIPOOPERACION));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
		try {identificacion.setFecEmi(identificationJson.getString(FECEMI));} 						catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
		try {identificacion.setHorEmi(identificationJson.getString(HOREMI));} 						catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
		try {identificacion.setTipoMoneda(identificationJson.getString(TIPOMONEDA));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
		try {identificacion.setAmbiente(identificationJson.getString(AMBIENTE));} 					catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}

		System.out.println("End FacturaNoSujeto.fillIdentificacion()");
		return errorMessages;
	}

	public List<DocumentoRelacionadoItemFacturaSujetoExcluido> getDocumentoRelacionado() {
		return documentoRelacionado;
	}

	public void setDocumentoRelacionado(List<DocumentoRelacionadoItemFacturaSujetoExcluido> documentoRelacionado) {
		this.documentoRelacionado = documentoRelacionado;
	}

	/**
	 * @return the emisor
	 */
	public EmisorFacturaSujetoExcluido getEmisor() {
		return emisor;
	}

	/**
	 * @param emisor the emisor to set
	 */
	public void setEmisor(EmisorFacturaSujetoExcluido emisor) {
		this.emisor = emisor;
	}

	public StringBuffer fillEmisor(JSONObject factoryInput) {
		System.out.println("Start FacturaNoSujeto.fillEmisor()");

		JSONObject emisorJson = factoryInput.getJSONObject(EMISOR);
		try {emisor.setNit(emisorJson.getString(NIT));} 									catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_EMISOR + e);}
		try {emisor.setNrc(emisorJson.getString(NRC));} 									catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_EMISOR + e);}
		try {emisor.setNombre(emisorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_EMISOR + e);}
		try {emisor.setCodActividad(emisorJson.getString(CODACTIVIDAD));} 					catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_EMISOR + e);}
		try {emisor.setDescActividad(emisorJson.getString(DESCACTIVIDAD));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_EMISOR + e);}

		JSONObject jsonDireccion = emisorJson.getJSONObject(DIRECCION);
		try {emisor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_EMISOR + e);}
		try {emisor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_EMISOR + e);}
		try {emisor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_EMISOR + e);}

		try {emisor.setTelefono(emisorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_EMISOR + e);}
		try {emisor.setCorreo(emisorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_EMISOR + e);}
		try {emisor.setCodEstable(emisorJson.getString(CODESTABLE));} 						catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_EMISOR + e);}

		System.out.println("End FacturaNoSujeto.fillEmisor()");
		return errorMessages;
	}
//
//	/**
//	 * @return the receptor
//	 */
//	public ReceptorFacturaSujetoExcluido getReceptor() {
//		return receptor;
//	}
//
//	/**
//	 * @param receptor the receptor to set
//	 */
//	public void setReceptor(ReceptorFacturaSujetoExcluido receptor) {
//		this.receptor = receptor;
//	}
//
//	public StringBuffer fillReceptor(JSONObject factoryInput) {
//		System.out.println("Start CreditoFiscal.fillReceptor()"); 
//
//		
//		JSONObject receptorJson = factoryInput.getJSONObject(RECEPTOR);
//		try {receptor.setNit(receptorJson.getString(NIT));} 									catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
//		try {receptor.setNrc(receptorJson.getString(NRC));} 									catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
//		try {receptor.setNombre(receptorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
//		try {receptor.setCodActividad(receptorJson.getString(CODACTIVIDAD));} 					catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
//		try {receptor.setDescActividad(receptorJson.getString(DESCACTIVIDAD));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
//
//		JSONObject jsonDireccion = receptorJson.getJSONObject(DIRECCION);
//		try {receptor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
//		try {receptor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
//		try {receptor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
//
//		try {receptor.setTelefono(receptorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
//		try {receptor.setCorreo(receptorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_IDENTIFICACION + e);}
//
//		System.out.println("End Factura.fillReceptor()"); 
//		return errorMessages;
//	}
	
	public StringBuffer fillSujetoExcluido(JSONObject factoryInput) {
		System.out.println("Start FacturaNoSujeto.fillSujetoExcluido()");
		
		JSONObject sujetoExcluidoJson = factoryInput.getJSONObject(SUJETOEXCLUIDO);
		try {sujetoExcluido.setNumDocumento(sujetoExcluidoJson.getString(NUMDOCUMENTO));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_SUJETO_EXCLUIDO + e);}
		try {sujetoExcluido.setNombre(sujetoExcluidoJson.getString(NOMBRE));} 						catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_SUJETO_EXCLUIDO + e);}
		try {sujetoExcluido.setCodActividad(sujetoExcluidoJson.getString(CODACTIVIDAD));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_SUJETO_EXCLUIDO + e);}
		try {sujetoExcluido.setDescActividad(sujetoExcluidoJson.getString(DESCACTIVIDAD));} 		catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_SUJETO_EXCLUIDO + e);}
		try {sujetoExcluido.setTipoDocumento(sujetoExcluidoJson.getString(TIPODOCUMENTO));} 		catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_SUJETO_EXCLUIDO + e);}

		JSONObject jsonDireccion = sujetoExcluidoJson.getJSONObject(DIRECCION);
		try {sujetoExcluido.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_SUJETO_EXCLUIDO + e);}
		try {sujetoExcluido.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_SUJETO_EXCLUIDO + e);}
		try {sujetoExcluido.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_SUJETO_EXCLUIDO + e);}

		try {sujetoExcluido.setTelefono(sujetoExcluidoJson.getString(TELEFONO));} 					catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_SUJETO_EXCLUIDO + e);}
		try {sujetoExcluido.setCorreo(sujetoExcluidoJson.getString(CORREO));} 						catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_SUJETO_EXCLUIDO + e);}

		System.out.println("End FacturaNoSujeto.fillSujetoExcluido()"); 
		return errorMessages;
	}



	/**
	 * @return the cuerpoDocumento
	 */
	public List<CuerpoDocumentoItemFacturaSujetoExcluido> getCuerpoDocumento() {
		return cuerpoDocumento;
	}

	public void setCuerpoDocumento(List<CuerpoDocumentoItemFacturaSujetoExcluido> cuerpoDocumento) {
		this.cuerpoDocumento = cuerpoDocumento;
	}


	public StringBuffer fillCuerpoDocumento(JSONObject factoryInput) {
		System.out.println("Start FacturaNoSujeto.fillCuerpoDocumento()");

		JSONObject cuerpoDocumentoItemsJson = factoryInput.getJSONObject(CUERPODOCUMENTO);
		JSONArray cuerpoDocumentoArrayJson = cuerpoDocumentoItemsJson.getJSONArray(CUERPODOCUMENTO);
	
		for (int i=0; i< cuerpoDocumentoArrayJson.length(); i++) { 
			JSONObject cuerpoDocumentoItemJson = cuerpoDocumentoArrayJson.getJSONObject(i);
			CuerpoDocumentoItemFacturaSujetoExcluido cuerpoDocumentoItemFacturaNoSujeto = new CuerpoDocumentoItemFacturaSujetoExcluido();
			try {cuerpoDocumentoItemFacturaNoSujeto.setNumItem(cuerpoDocumentoItemJson.getInt(NUMITEM));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFacturaNoSujeto.setTipoItem(cuerpoDocumentoItemJson.getInt(TIPOITEM));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFacturaNoSujeto.setCantidad(cuerpoDocumentoItemJson.getBigDecimal(CANTIDAD));} 		catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFacturaNoSujeto.setCodigo(cuerpoDocumentoItemJson.getString(CODIGO));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFacturaNoSujeto.setUniMedida(cuerpoDocumentoItemJson.getInt(UNIMEDIDA));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFacturaNoSujeto.setDescripcion(cuerpoDocumentoItemJson.getString(DESCRIPCION));} 	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFacturaNoSujeto.setPrecioUni(cuerpoDocumentoItemJson.getBigDecimal(PRECIOUNI));} 	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFacturaNoSujeto.setMontoDescu(cuerpoDocumentoItemJson.getBigDecimal(MONTODESCU));}	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemFacturaNoSujeto.setCompra(cuerpoDocumentoItemJson.getBigDecimal(COMPRA));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_CUERPO_DOCUMENTO + e);}

			cuerpoDocumento.add(cuerpoDocumentoItemFacturaNoSujeto);						
		}

		System.out.println("End FacturaNoSujeto.fillCuerpoDocumento()"); 
		return errorMessages;
	}
	
	public StringBuffer fillDocumentosRelacionados(JSONObject factoryInput) {
		System.out.println("Start FacturaNoSujeto.fillDocumentosRelacionados()");

		JSONObject documentosRelacionadosItemsJson = factoryInput.getJSONObject(DOCUMENTORELACIONADO);
		JSONArray DocumentosRelacionadosArrayJson = documentosRelacionadosItemsJson.getJSONArray(DOCUMENTORELACIONADO);
	
		for (int i=0; i< DocumentosRelacionadosArrayJson.length(); i++) { 
			JSONObject DocRelaciondadosItemJson = DocumentosRelacionadosArrayJson.getJSONObject(i);
			DocumentoRelacionadoItemFacturaSujetoExcluido documentoRelacionadoItem = new DocumentoRelacionadoItemFacturaSujetoExcluido();
			try {documentoRelacionadoItem.setNumeroDocumento(DocRelaciondadosItemJson.getString(NUMERODOCUMENTO));}	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_DOCUMENTOS_RELACIONADOS + e);}
			try {documentoRelacionadoItem.setTipoDocumento(DocRelaciondadosItemJson.getString(TIPODOCUMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_DOCUMENTOS_RELACIONADOS + e);}
			try {documentoRelacionadoItem.setTipoGeneracion(DocRelaciondadosItemJson.getInt(TIPOGENERACION));} 		catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_DOCUMENTOS_RELACIONADOS + e);}
			try {documentoRelacionadoItem.setFechaEmision(DocRelaciondadosItemJson.getString(FECEMI));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_DOCUMENTOS_RELACIONADOS + e);}

			documentoRelacionado.add(documentoRelacionadoItem);						
		}

		System.out.println("End FacturaNoSujeto.fillDocumentosRelacionados()"); 
		return errorMessages;
	}

	/**
	 * @return the resumen
	 */
	public ResumenFacturaSujetoExcluido getResumen() {
		return resumen;
	}

	/**
	 * @param resumen the resumen to set
	 */
	public void setResumen(ResumenFacturaSujetoExcluido resumen) {
		this.resumen = resumen;
	}

	public StringBuffer fillResumen(JSONObject factoryInput) {
		System.out.println("Start FacturaNoSujeto.fillResumen()");
		JSONObject resumenJson = factoryInput.getJSONObject(RESUMEN);		

		try {resumen.setIvaRete1(resumenJson.getBigDecimal(IVARETE1));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {resumen.setTotalLetras(resumenJson.getString(TOTALLETRAS));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {resumen.setCondicionOperacion(resumenJson.getInt(CONDICIONOPERACION));}	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {resumen.setTotalDescu(resumenJson.getBigDecimal(TOTALDESCU));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {resumen.setReteRenta(resumenJson.getBigDecimal(RETERENTA));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {resumen.setDescu(resumenJson.getBigDecimal(DESCU));} 						catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {resumen.setTotalCompra(resumenJson.getBigDecimal(TOTALCOMPRA));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {resumen.setTotalPagar(resumenJson.getBigDecimal(TOTALPAGAR));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {resumen.setSubTotal(resumenJson.getBigDecimal(SUBTOTAL));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {resumen.setObservaciones(resumenJson.getString(OBSERVACIONES));} 			catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}

		JSONArray pagosItemsJson = resumenJson.getJSONArray(PAGOS);
		JSONObject pagosItemJson = pagosItemsJson.getJSONObject(0);

		PagosItem newPagosItem = new PagosItem();
		try {newPagosItem.setCodigo(pagosItemJson.getString(CODIGO));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {newPagosItem.setMontoPago(pagosItemJson.getBigDecimal(MONTOPAGO));}	catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {newPagosItem.setReferencia(pagosItemJson.getString(REFERENCIA));} 		catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {newPagosItem.setPlazo(pagosItemJson.getString(PLAZO));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		try {newPagosItem.setPeriodo(pagosItemJson.getInt(PERIODO));} 				catch (Exception e) {errorMessages.append(ERROR_SUJETO_EXCLUIDO_RESUMEN + e);}
		
		resumen.getPagos().add(newPagosItem);

		System.out.println("End FacturaNoSujeto.fillResumen()"); 
		return errorMessages;
	}

	/**
	 * @return the apendice
	 */
	public List<ApendiceItemFacturaSujetoExcluido> getApendice() {
		return apendice;
	}

	public void setApendice(List<ApendiceItemFacturaSujetoExcluido> apendice) {
		this.apendice = apendice;
	}


	public SujetoExcluidoFacturaSujetoExcluido getSujetoExcluido() {
		return sujetoExcluido;
	}

	public void setSujetoExcluido(SujetoExcluidoFacturaSujetoExcluido sujetoExcluidoFacturaNoSujeto) {
		this.sujetoExcluido = sujetoExcluidoFacturaNoSujeto;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}	
}

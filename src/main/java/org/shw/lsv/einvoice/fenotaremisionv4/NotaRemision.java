/**
 * 
 */
package org.shw.lsv.einvoice.fenotaremisionv4;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.utils.EDocument;
import org.shw.lsv.einvoice.utils.EDocumentUtils;
import org.shw.lsv.einvoice.utils.PagosItem;
import org.shw.lsv.einvoice.utils.TributosItem;


/**
 * 
 */
public class NotaRemision extends EDocument {
	static final String ERROR_NOTA_REMISION_IDENTIFICACION     		= "Documento: Nota Remision, error en fillIdentification(): ";
	static final String ERROR_NOTA_REMISION_DOCUMENTO_RELACIONADO	= "Documento: Nota Remision, error en fillDocumentoRelacionado(): ";
	static final String ERROR_NOTA_REMISION_EMISOR          		= "Documento: Nota Remision, error en fillEmisor(): ";
	static final String ERROR_NOTA_REMISION_RECEPTOR           		= "Documento: Nota Remision, error en fillReceptor(): ";
	static final String ERROR_NOTA_REMISION_CUERPO_DOCUMENTO		= "Documento: Nota Remision, error en fillCuerpoDocumento(): ";
	static final String ERROR_NOTA_REMISION_RESUMEN            		= "Documento: Nota Remision, error en fillResumen(): ";
	static final String ERROR_NOTA_REMISION_APENDICE            	= "Documento: Nota Remision, error en fillApendice(): ";
	static final String ERROR_NOTA_REMISION_EXTENSION            	= "Documento: Nota Remision, error en fillExtension(): ";
	
	IdentificacionNotaRemision identificacion;
	List<DocumentoRelacionadoItemNotaRemision> documentoRelacionado = null;
	EmisorNotaRemision emisor;
	ReceptorNotaRemision receptor;
	VentaTerceroNotaRemision ventaTercero = null;
	List<CuerpoDocumentoItemNotaRemision> cuerpoDocumento;
	ResumenNotaRemision resumen;
	//ExtensionCreditoFiscal extension;
	List<ApendiceItemNotaRemision> apendice=null;  // null allowed

	/**
	 * No parameters
	 */
	public NotaRemision() {
		
		this.identificacion       = new IdentificacionNotaRemision();
		this.documentoRelacionado = new ArrayList<DocumentoRelacionadoItemNotaRemision>();
		this.emisor               = new EmisorNotaRemision();
		this.receptor             = new ReceptorNotaRemision();
		this.ventaTercero         = new VentaTerceroNotaRemision();
	    this.cuerpoDocumento      = new ArrayList<CuerpoDocumentoItemNotaRemision>();	
		this.resumen              = new ResumenNotaRemision();
		//this.extension            = new ExtensionCreditoFiscal();
	    this.apendice             = new ArrayList<ApendiceItemNotaRemision>();
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
	public IdentificacionNotaRemision getIdentificacion() {
		return identificacion;
	}


	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(IdentificacionNotaRemision identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * @param factoryInput the JSONObject that contains the information
	 */
	public StringBuffer fillIdentification(JSONObject factoryInput) {
		System.out.println("Start Credito Fiscal.fillIdentificacion()"); 

		JSONObject identificationJson = factoryInput.getJSONObject(IDENTIFICACION);
		try {identificacion.setNumeroControl(identificationJson.getString(NUMEROCONTROL));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_IDENTIFICACION + e);}
		try {identificacion.setCodigoGeneracion(identificationJson.getString(CODIGOGENERACION));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_IDENTIFICACION + e);}
		try {identificacion.setTipoModelo(identificationJson.getInt(TIPOMODELO));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_IDENTIFICACION + e);}
		try {identificacion.setTipoOperacion(identificationJson.getInt(TIPOOPERACION));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_IDENTIFICACION + e);}
		try {identificacion.setFecEmi(identificationJson.getString(FECEMI));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_IDENTIFICACION + e);}
		try {identificacion.setHorEmi(identificationJson.getString(HOREMI));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_IDENTIFICACION + e);}
		try {identificacion.setTipoMoneda(identificationJson.getString(TIPOMONEDA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_IDENTIFICACION + e);}
		try {identificacion.setAmbiente(identificationJson.getString(AMBIENTE));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_IDENTIFICACION + e);}
		if (!identificationJson.getString(MOTIVOCONTIN).equals("")) {		
			try {identificacion.setMotivoContin(identificationJson.getString(MOTIVOCONTIN));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_IDENTIFICACION + e);}
			try {identificacion.setTipoContingencia(identificationJson.getInt(TIPOCONTINGENCIA));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_IDENTIFICACION + e);}
		}
		System.out.println("End Credito Fiscal.fillIdentificacion()");
		return errorMessages;
	}


	/**
	 * @return the documentoRelacionado
	 */
	public List<DocumentoRelacionadoItemNotaRemision> getDocumentoRelacionado() {
		return documentoRelacionado;
	}



	/**
	 * @param documentoRelacionado the documentoRelacionado to set
	 */
	public void setDocumentoRelacionado(List<DocumentoRelacionadoItemNotaRemision> documentoRelacionado) {		
		this.documentoRelacionado = documentoRelacionado;
	}

	public StringBuffer fillDocumentoRelacionado(JSONObject factoryInput) {
		System.out.println("Start CreditoFiscal.fillDocumentoRelacionado()"); 

		JSONObject documentoRelacionadoItemsJson = factoryInput.getJSONObject(DOCUMENTORELACIONADO);
		JSONArray documentoRelacionadoArrayJson = documentoRelacionadoItemsJson.getJSONArray(DOCUMENTORELACIONADO);
	
		for (int i=0; i< documentoRelacionadoArrayJson.length(); i++) {
			JSONObject cuerpoDocumentoRelacionadoItemJson = documentoRelacionadoArrayJson.getJSONObject(i);
			DocumentoRelacionadoItemNotaRemision documentoRelacionadoItemNotaRemision = new DocumentoRelacionadoItemNotaRemision();
			try {documentoRelacionadoItemNotaRemision.setNumeroDocumento(cuerpoDocumentoRelacionadoItemJson.getString(NUMERODOCUMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_DOCUMENTO_RELACIONADO + e);}

			documentoRelacionado.add(documentoRelacionadoItemNotaRemision);						
		}

		System.out.println("End CreditoFiscal.fillDocumentoRelacionado()"); 
		return errorMessages;
	}


	/**
	 * @return the emisor
	 */
	public EmisorNotaRemision getEmisor() {
		return emisor;
	}


	/**
	 * @param emisor the emisor to set
	 */
	public void setEmisor(EmisorNotaRemision emisor) {
		this.emisor = emisor;
	}
	
	public StringBuffer fillEmisor(JSONObject factoryInput) {
		System.out.println("Start CreditoFiscal.fillEmisor()");

		JSONObject emisorJson = factoryInput.getJSONObject(EMISOR);
		try {emisor.setNit(emisorJson.getString(NIT));} 									catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}
		try {emisor.setNrc(emisorJson.getString(NRC));} 									catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}
		try {emisor.setNombre(emisorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}
		try {emisor.setCodActividad(emisorJson.getString(CODACTIVIDAD));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}
		try {emisor.setDescActividad(emisorJson.getString(DESCACTIVIDAD));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}
		try {emisor.setNombreComercial(emisorJson.getString(NOMBRECOMERCIAL));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}		
		//try {emisor.setTipoEstablecimiento(emisorJson.getString(TIPOESTABLECIMIENTO));}		catch (Exception e) {errorMessages.append(ERROR_CREDITO_FISCAL_EMISOR + e);}	

		JSONObject jsonDireccion = emisorJson.getJSONObject(DIRECCION);
		try {emisor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}
		try {emisor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}
		try {emisor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}
		try {emisor.getDireccion().setDistrito(jsonDireccion.getString(DISTRITO));}			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}

		try {emisor.setTelefono(emisorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}
		try {emisor.setCorreo(emisorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_EMISOR + e);}

		System.out.println("End CreditoFiscal.fillEmisor()");
		return errorMessages;
	}


	/**
	 * @return the receptor
	 */
	public ReceptorNotaRemision getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor the receptor to set
	 */
	public void setReceptor(ReceptorNotaRemision receptor) {
		this.receptor = receptor;
	}

	public StringBuffer fillReceptor(JSONObject factoryInput) {
		System.out.println("Start CreditoFiscal.fillReceptor()"); 

		JSONObject receptorJson = factoryInput.getJSONObject(RECEPTOR);
		try {receptor.setTipoDocumento(receptorJson.getString(TIPODOCUMENTO));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}
		try {receptor.setNumDocumento(receptorJson.getString(NUMDOCUMENTO));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}
		//try {receptor.setNrc(receptorJson.getString(NRC));} 									catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}
		try {receptor.setNombre(receptorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}
		try {receptor.setCodActividad(receptorJson.getString(CODACTIVIDAD));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}
		try {receptor.setDescActividad(receptorJson.getString(DESCACTIVIDAD));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}
		try {receptor.setBienTitulo(receptorJson.getString(BIENTITULO));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}

		JSONObject jsonDireccion = receptorJson.getJSONObject(DIRECCION);
		try {receptor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}
		try {receptor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}
		try {receptor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}
		try {receptor.getDireccion().setDistrito(jsonDireccion.getString(DISTRITO));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}

		try {receptor.setTelefono(receptorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}
		try {receptor.setCorreo(receptorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RECEPTOR + e);}

		System.out.println("End CreditoFiscal.fillReceptor()"); 
		return errorMessages;
	}


	/**
	 * @return the ventaTercero
	 */
	public VentaTerceroNotaRemision getVentaTercero() {
		return ventaTercero;
	}


	/**
	 * @param ventaTercero the ventaTercero to set
	 */
	public void setVentaTercero(VentaTerceroNotaRemision ventaTercero) {
		this.ventaTercero = ventaTercero;
	}


	/**
	 * @return the cuerpoDocumento
	 */
	public List<CuerpoDocumentoItemNotaRemision> getCuerpoDocumento() {
		return cuerpoDocumento;
	}


	/**
	 * @param cuerpoDocumento the cuerpoDocumento to set
	 */
	public void setCuerpoDocumento(List<CuerpoDocumentoItemNotaRemision> cuerpoDocumento) {
		this.cuerpoDocumento = cuerpoDocumento;
	}


	public StringBuffer fillCuerpoDocumento(JSONObject factoryInput) {
		System.out.println("Start CreditoFiscal.fillCuerpoDocumento()"); 

		JSONObject cuerpoDocumentoItemsJson = factoryInput.getJSONObject(CUERPODOCUMENTO);
		JSONArray cuerpoDocumentoArrayJson = cuerpoDocumentoItemsJson.getJSONArray(CUERPODOCUMENTO);
	
		for (int i=0; i< cuerpoDocumentoArrayJson.length(); i++) {
			JSONObject cuerpoDocumentoItemJson = cuerpoDocumentoArrayJson.getJSONObject(i);
			CuerpoDocumentoItemNotaRemision cuerpoDocumentoItemNotaRemision = new CuerpoDocumentoItemNotaRemision();
			try {cuerpoDocumentoItemNotaRemision.setNumItem(cuerpoDocumentoItemJson.getInt(NUMITEM));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaRemision.setTipoItem(cuerpoDocumentoItemJson.getInt(TIPOITEM));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			//try {cuerpoDocumentoItemCreditoFiscal.setNumeroDocumento(cuerpoDocumentoItemJson.getString(NUMERODOCUMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_CREDITO_FISCAL_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaRemision.setCantidad(cuerpoDocumentoItemJson.getBigDecimal(CANTIDAD));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaRemision.setCodigo(cuerpoDocumentoItemJson.getString(CODIGO));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			
			if (cuerpoDocumentoItemJson.getString(CODTRIBUTO).equals(""))
				try {cuerpoDocumentoItemNotaRemision.setCodTributo(null);} 												catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			else
				try {cuerpoDocumentoItemNotaRemision.setCodTributo(cuerpoDocumentoItemJson.getString(CODTRIBUTO));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}

			try {cuerpoDocumentoItemNotaRemision.setUniMedida(cuerpoDocumentoItemJson.getInt(UNIMEDIDA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaRemision.setDescripcion(cuerpoDocumentoItemJson.getString(DESCRIPCION));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaRemision.setPrecioUni(cuerpoDocumentoItemJson.getBigDecimal(PRECIOUNI));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaRemision.setMontoDescu(cuerpoDocumentoItemJson.getBigDecimal(MONTODESCU));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaRemision.setVentaNoSuj(cuerpoDocumentoItemJson.getBigDecimal(VENTANOSUJ));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaRemision.setVentaExenta(cuerpoDocumentoItemJson.getBigDecimal(VENTAEXENTA));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaRemision.setVentaGravada(cuerpoDocumentoItemJson.getBigDecimal(VENTAGRAVADA));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			//try {cuerpoDocumentoItemNotaRemision.setPsv(cuerpoDocumentoItemJson.getBigDecimal(PSV));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			//try {cuerpoDocumentoItemNotaRemision.setNoGravado(cuerpoDocumentoItemJson.getBigDecimal(NOGRAVADO));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}
			
			JSONArray tributosArrayJson = cuerpoDocumentoItemJson.getJSONArray(TRIBUTOS);
			for (int j=0; j< tributosArrayJson.length(); j++) { 
				String tributosItemJson = tributosArrayJson.getString(j);
				try {cuerpoDocumentoItemNotaRemision.getTributos().add(tributosItemJson);} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_CUERPO_DOCUMENTO + e);}	
			}

			cuerpoDocumento.add(cuerpoDocumentoItemNotaRemision);						
		}

		System.out.println("End CreditoFiscal.fillCuerpoDocumento()"); 
		return errorMessages;
	}

	/**
	 * @return the resumen
	 */
	public ResumenNotaRemision getResumen() {
		return resumen;
	}


	/**
	 * @param resumen the resumen to set
	 */
	public void setResumen(ResumenNotaRemision resumen) {
		this.resumen = resumen;
	}

	public StringBuffer fillResumen(JSONObject factoryInput) {
		System.out.println("Start CreditoFiscal.fillResumen()");
		JSONObject resumenJson = factoryInput.getJSONObject(RESUMEN);

		JSONArray tributosArrayJson = resumenJson.getJSONArray(TRIBUTOS);	
		for (int i=0; i< tributosArrayJson.length(); i++) {
			JSONObject tributosItemJson = tributosArrayJson.getJSONObject(i);
			TributosItem tributosItemCreditoFiscal = new TributosItem();
			try {tributosItemCreditoFiscal.setCodigo(tributosItemJson.getString(CODIGO));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
			try {tributosItemCreditoFiscal.setDescripcion(tributosItemJson.getString(DESCRIPCION));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
			try {tributosItemCreditoFiscal.setValor(tributosItemJson.getBigDecimal(VALOR));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
			resumen.getTributos().add(tributosItemCreditoFiscal);						
		}

		try {resumen.setTotalNoSuj(resumenJson.getBigDecimal(TOTALNOSUJ));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		try {resumen.setTotalExenta(resumenJson.getBigDecimal(TOTALEXENTA));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		try {resumen.setTotalGravada(resumenJson.getBigDecimal(TOTALGRAVADA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		try {resumen.setSubTotalVentas(resumenJson.getBigDecimal(SUBTOTALVENTAS));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		try {resumen.setDescuNoSuj(resumenJson.getBigDecimal(DESCUNOSUJ));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		try {resumen.setDescuExenta(resumenJson.getBigDecimal(DESCUEXENTA));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		try {resumen.setDescuGravada(resumenJson.getBigDecimal(DESCUGRAVADA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		try {resumen.setPorcentajeDescuento(resumenJson.getBigDecimal(PORCENTAJEDESCUENTO));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		try {resumen.setSubTotal(resumenJson.getBigDecimal(SUBTOTAL));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		//try {resumen.setIvaRete(resumenJson.getBigDecimal(IVARETE));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		try {resumen.setMontoTotalOperacion(resumenJson.getBigDecimal(MONTOTOTALOPERACION));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		//try {resumen.setTotalNoGravado(resumenJson.getBigDecimal(TOTALNOGRAVADO));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		//try {resumen.setTotalPagar(resumenJson.getBigDecimal(TOTALPAGAR));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		try {resumen.setTotalLetras(resumenJson.getString(TOTALLETRAS));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		//try {resumen.setSaldoFavor(resumenJson.getBigDecimal(SALDOFAVOR));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		//try {resumen.setCondicionOperacion(resumenJson.getInt(CONDICIONOPERACION));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		try {resumen.setTotalDescu(resumenJson.getBigDecimal(TOTALDESCU));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}
		//try {resumen.setReteRenta(resumenJson.getBigDecimal(RETERENTA));} 						catch (Exception e) {errorMessages.append(ERROR_CREDITO_FISCAL_RESUMEN + e);}
		//try {resumen.setIvaPerci(resumenJson.getBigDecimal(IVAPERCI));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_RESUMEN + e);}

		System.out.println("End CreditoFiscal.fillResumen()"); 
		return errorMessages;
	}


	/**
	 * @return the apendice
	 */
	public List<ApendiceItemNotaRemision> getApendice() {
		return apendice;
	}


	/**
	 * @param apendice the apendice to set
	 */
	public void setApendice(List<ApendiceItemNotaRemision> apendice) {		
		this.apendice = apendice;
	}
	
	/*
	 * public StringBuffer fillApendice(JSONObject factoryInput) { JSONObject
	 * apendiceJson = factoryInput.getJSONObject(APENDICE); JSONArray
	 * apendiceArrayJson = apendiceJson.getJSONArray(APENDICE);
	 * 
	 * for (int i=0; i< apendiceArrayJson.length(); i++) { JSONObject
	 * apendiceItemJson = apendiceArrayJson.getJSONObject(i);
	 * ApendiceItemNotaRemision apendiceItem = new ApendiceItemNotaRemision(); try
	 * {apendiceItem.setCampo(apendiceItemJson.getString(CAMPO)) ;} catch (Exception
	 * e) {errorMessages.append(ERROR_NOTA_REMISION_APENDICE + e);} try
	 * {apendiceItem.setEtiqueta(apendiceItemJson.getString(ETIQUETA));} catch
	 * (Exception e) {errorMessages.append(ERROR_NOTA_REMISION_APENDICE + e);} try
	 * {apendiceItem.setValor(apendiceItemJson.getString(VALOR)) ;} catch (Exception
	 * e) {errorMessages.append(ERROR_NOTA_REMISION_APENDICE + e);}
	 * apendice.add(apendiceItem); }
	 * System.out.println("End CreditoFiscal.fillApendice()"); return errorMessages;
	 * }
	 */


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	
}

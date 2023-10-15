/**
 * 
 */
package org.shw.lsv.einvoice.fencnotadecreditov1;
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
public class NotaDeCredito extends EDocument {

	static final String ERROR_NOTA_DE_CREDITO_IDENTIFICACION     		= "Documento: Nota de Credito, error en fillIdentification(): ";
	static final String ERROR_NOTA_DE_CREDITO_EMISOR          			= "Documento: Nota de Credito, error en fillEmisor(): ";
	static final String ERROR_NOTA_DE_CREDITO_RECEPTOR           		= "Documento: Nota de Credito, error en fillReceptor(): ";
	static final String ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO			= "Documento: Nota de Credito, error en fillCuerpoDocumento(): ";
	static final String ERROR_NOTA_DE_CREDITO_RESUMEN            		= "Documento: Nota de Credito, error en fillResumen(): ";
	static final String ERROR_NOTA_DE_CREDITO_DOCUMENTOS_RELACIONADOS	= "Documento: Nota de Credito, error en fillDocumentosRelacionados(): ";
	
	IdentificacionNotaDeCredito identificacion;
	List<DocumentoRelacionadoItemNotaDeCredito> documentoRelacionado;
	EmisorNotaDeCredito emisor;
	ReceptorNotaDeCredito receptor;
	VentaTerceroNotaDeCredito ventaTercero = null;
	List<CuerpoDocumentoItemNotaDeCredito> cuerpoDocumento;
	ResumenNotaDeCredito resumen;
	ExtensionNotaDeCredito extension;
	List<ApendiceItemNotaDeCredito> apendice=null;  // null allowed

	/**
	 * No parameters
	 */
	public NotaDeCredito() {
		this.identificacion       = new IdentificacionNotaDeCredito();
	    this.documentoRelacionado = new ArrayList<DocumentoRelacionadoItemNotaDeCredito>();
		this.emisor               = new EmisorNotaDeCredito();
		this.receptor             = new ReceptorNotaDeCredito();
		this.ventaTercero         = new VentaTerceroNotaDeCredito();
	    this.cuerpoDocumento      = new ArrayList<CuerpoDocumentoItemNotaDeCredito>();
		this.resumen              = new ResumenNotaDeCredito();
		this.extension            = new ExtensionNotaDeCredito();
	    this.apendice      		  = new ArrayList<ApendiceItemNotaDeCredito>();
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
	public IdentificacionNotaDeCredito getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(IdentificacionNotaDeCredito identificacion) {
		this.identificacion = identificacion;
	}


	/**
	 * @param identificacion the (IdentificacionFactura) identificacion to set
	 */
	public StringBuffer fillIdentification(JSONObject factoryInput) {
		System.out.println("Start NotaDeCredito.fillIdentificacion()");

		JSONObject identificationJson = factoryInput.getJSONObject(IDENTIFICACION);
		try {identificacion.setMotivoContin(identificationJson.getString(MOTIVOCONTIN));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e);}
		try {identificacion.setTipoContingencia(identificationJson.getInt(TIPOCONTINGENCIA));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e);}
		try {identificacion.setNumeroControl(identificationJson.getString(NUMEROCONTROL));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e);}
		try {identificacion.setCodigoGeneracion(identificationJson.getString(CODIGOGENERACION));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e);}
		try {identificacion.setTipoModelo(identificationJson.getInt(TIPOMODELO));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e);}
		try {identificacion.setTipoOperacion(identificationJson.getInt(TIPOOPERACION));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e);}
		try {identificacion.setFecEmi(identificationJson.getString(FECEMI));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e);}
		try {identificacion.setHorEmi(identificationJson.getString(HOREMI));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e);}
		try {identificacion.setTipoMoneda(identificationJson.getString(TIPOMONEDA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e);}
		try {identificacion.setAmbiente(identificationJson.getString(AMBIENTE));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e);}

		System.out.println("End NotaDeCredito.fillIdentificacion()");
		return errorMessages;
	}

	public List<DocumentoRelacionadoItemNotaDeCredito> getDocumentoRelacionado() {
		return documentoRelacionado;
	}

	public void setDocumentoRelacionado(List<DocumentoRelacionadoItemNotaDeCredito> documentoRelacionado) {
		this.documentoRelacionado = documentoRelacionado;
	}

	/**
	 * @return the emisor
	 */
	public EmisorNotaDeCredito getEmisor() {
		return emisor;
	}

	/**
	 * @param emisor the emisor to set
	 */
	public void setEmisor(EmisorNotaDeCredito emisor) {
		this.emisor = emisor;
	}

	public StringBuffer fillEmisor(JSONObject factoryInput) {
		System.out.println("Start NotaDeCredito.fillEmisor()");

		JSONObject emisorJson = factoryInput.getJSONObject(EMISOR);
		try {emisor.setNit(emisorJson.getString(NIT));} 									catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}
		try {emisor.setNrc(emisorJson.getString(NRC));} 									catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}
		try {emisor.setNombre(emisorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}
		try {emisor.setCodActividad(emisorJson.getString(CODACTIVIDAD));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}
		try {emisor.setDescActividad(emisorJson.getString(DESCACTIVIDAD));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}
		try {emisor.setNombreComercial(emisorJson.getString(NOMBRECOMERCIAL));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}		
		try {emisor.setTipoEstablecimiento(emisorJson.getString(TIPOESTABLECIMIENTO));}		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}	

		JSONObject jsonDireccion = emisorJson.getJSONObject(DIRECCION);
		try {emisor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}
		try {emisor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}
		try {emisor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}

		try {emisor.setTelefono(emisorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}
		try {emisor.setCorreo(emisorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e);}

		System.out.println("End NotaDeCredito.fillEmisor()");
		return errorMessages;
	}

	/**
	 * @return the receptor
	 */
	public ReceptorNotaDeCredito getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor the receptor to set
	 */
	public void setReceptor(ReceptorNotaDeCredito receptor) {
		this.receptor = receptor;
	}

	public StringBuffer fillReceptor(JSONObject factoryInput) {
		System.out.println("Start NotaDeCredito.fillReceptor()"); 

		JSONObject receptorJson = factoryInput.getJSONObject(RECEPTOR);
		try {receptor.setNit(receptorJson.getString(NIT));} 									catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e);}
		try {receptor.setNrc(receptorJson.getString(NRC));} 									catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e);}
		try {receptor.setNombre(receptorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e);}
		try {receptor.setCodActividad(receptorJson.getString(CODACTIVIDAD));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e);}
		try {receptor.setDescActividad(receptorJson.getString(DESCACTIVIDAD));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e);}

		JSONObject jsonDireccion = receptorJson.getJSONObject(DIRECCION);
		try {receptor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e);}
		try {receptor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e);}
		try {receptor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e);}

		try {receptor.setTelefono(receptorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e);}
		try {receptor.setCorreo(receptorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e);}

		System.out.println("End NotaDeCredito.fillReceptor()"); 
		return errorMessages;
	}

	/**
	 * @return the ventaTercero
	 */
	public VentaTerceroNotaDeCredito getVentaTercero() {
		return ventaTercero;
	}

	/**
	 * @param ventaTercero the ventaTercero to set
	 */
	public void setVentaTercero(VentaTerceroNotaDeCredito ventaTercero) {
		this.ventaTercero = ventaTercero;
	}

	/**
	 * @return the cuerpoDocumento
	 */
	public List<CuerpoDocumentoItemNotaDeCredito> getCuerpoDocumento() {
		return cuerpoDocumento;
	}

	public void setCuerpoDocumento(List<CuerpoDocumentoItemNotaDeCredito> cuerpoDocumento) {
		this.cuerpoDocumento = cuerpoDocumento;
	}


	public StringBuffer fillCuerpoDocumento(JSONObject factoryInput) {
		System.out.println("Start NotaDeCredito.fillCuerpoDocumento()");

		JSONObject cuerpoDocumentoItemsJson = factoryInput.getJSONObject(CUERPODOCUMENTO);
		JSONArray cuerpoDocumentoArrayJson = cuerpoDocumentoItemsJson.getJSONArray(CUERPODOCUMENTO);
	
		for (int i=0; i< cuerpoDocumentoArrayJson.length(); i++) {
			JSONObject cuerpoDocumentoItemJson = cuerpoDocumentoArrayJson.getJSONObject(i);
			CuerpoDocumentoItemNotaDeCredito cuerpoDocumentoItemNotaDeCredito = new CuerpoDocumentoItemNotaDeCredito();
			try {cuerpoDocumentoItemNotaDeCredito.setNumItem(cuerpoDocumentoItemJson.getInt(NUMITEM));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeCredito.setTipoItem(cuerpoDocumentoItemJson.getInt(TIPOITEM));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}
			//try {cuerpoDocumentoItemNotaDeCredito.setNumeroDocumento(cuerpoDocumentoItemJson.getString(NUMERODOCUMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeCredito.setCantidad(cuerpoDocumentoItemJson.getBigDecimal(CANTIDAD));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeCredito.setCodigo(cuerpoDocumentoItemJson.getString(CODIGO));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}

			if (cuerpoDocumentoItemJson.getString(CODTRIBUTO).equals(""))
				try {cuerpoDocumentoItemNotaDeCredito.setCodTributo(null);} 												catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}
			else
				try {cuerpoDocumentoItemNotaDeCredito.setCodTributo(cuerpoDocumentoItemJson.getString(CODTRIBUTO));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}

			try {cuerpoDocumentoItemNotaDeCredito.setUniMedida(cuerpoDocumentoItemJson.getInt(UNIMEDIDA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeCredito.setDescripcion(cuerpoDocumentoItemJson.getString(DESCRIPCION));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeCredito.setPrecioUni(cuerpoDocumentoItemJson.getBigDecimal(PRECIOUNI));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeCredito.setMontoDescu(cuerpoDocumentoItemJson.getBigDecimal(MONTODESCU));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeCredito.setVentaNoSuj(cuerpoDocumentoItemJson.getBigDecimal(VENTANOSUJ));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeCredito.setVentaExenta(cuerpoDocumentoItemJson.getBigDecimal(VENTAEXENTA));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeCredito.setVentaGravada(cuerpoDocumentoItemJson.getBigDecimal(VENTAGRAVADA));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}

			JSONArray tributosArrayJson = cuerpoDocumentoItemJson.getJSONArray(TRIBUTOS);
			for (int j=0; j< tributosArrayJson.length(); j++) { 
				String tributosItemJson = tributosArrayJson.getString(j);
				try {cuerpoDocumentoItemNotaDeCredito.getTributos().add(tributosItemJson);} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e);}	
			}

			cuerpoDocumento.add(cuerpoDocumentoItemNotaDeCredito);						
		}

		System.out.println("End NotaDeCredito.fillCuerpoDocumento()"); 
		return errorMessages;
	}
	
	public StringBuffer fillDocumentosRelacionados(JSONObject factoryInput) {
		System.out.println("Start NotaDeCredito.fillDocumentosRelacionados()");

		JSONObject documentosRelacionadosItemsJson = factoryInput.getJSONObject(DOCUMENTORELACIONADO);
		JSONArray DocumentosRelacionadosArrayJson = documentosRelacionadosItemsJson.getJSONArray(DOCUMENTORELACIONADO);
	
		for (int i=0; i< DocumentosRelacionadosArrayJson.length(); i++) { 
			JSONObject DocRelaciondadosItemJson = DocumentosRelacionadosArrayJson.getJSONObject(i);
			DocumentoRelacionadoItemNotaDeCredito documentoRelacionadoItem = new DocumentoRelacionadoItemNotaDeCredito();
			try {documentoRelacionadoItem.setNumeroDocumento(DocRelaciondadosItemJson.getString(NUMERODOCUMENTO));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_DOCUMENTOS_RELACIONADOS + e);}
			try {documentoRelacionadoItem.setTipoDocumento(DocRelaciondadosItemJson.getString(TIPODOCUMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_DOCUMENTOS_RELACIONADOS + e);}
			try {documentoRelacionadoItem.setTipoGeneracion(DocRelaciondadosItemJson.getInt(TIPOGENERACION));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_DOCUMENTOS_RELACIONADOS + e);}
			try {documentoRelacionadoItem.setFechaEmision(DocRelaciondadosItemJson.getString(FECEMI));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_DOCUMENTOS_RELACIONADOS + e);}
					
			documentoRelacionado.add(documentoRelacionadoItem);						
		}

		System.out.println("End NotaDeCredito.fillDocumentosRelacionados()"); 
		return errorMessages;
	}

	/**
	 * @return the resumen
	 */
	public ResumenNotaDeCredito getResumen() {
		return resumen;
	}

	/**
	 * @param resumen the resumen to set
	 */
	public void setResumen(ResumenNotaDeCredito resumen) {
		this.resumen = resumen;
	}

	public StringBuffer fillResumen(JSONObject factoryInput) {
		System.out.println("Start NotaDeCredito.fillResumen()");
		JSONObject resumenJson = factoryInput.getJSONObject(RESUMEN);

		JSONArray tributosArrayJson = resumenJson.getJSONArray(TRIBUTOS);	
		for (int i=0; i< tributosArrayJson.length(); i++) {
			JSONObject tributosItemJson = tributosArrayJson.getJSONObject(i);
			TributosItem tributosItemCreditoFiscal = new TributosItem();
			try {tributosItemCreditoFiscal.setCodigo(tributosItemJson.getString(CODIGO));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
			try {tributosItemCreditoFiscal.setDescripcion(tributosItemJson.getString(DESCRIPCION));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
			try {tributosItemCreditoFiscal.setValor(tributosItemJson.getBigDecimal(VALOR));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
			resumen.getTributos().add(tributosItemCreditoFiscal);						
		}

		try {resumen.setTotalNoSuj(resumenJson.getBigDecimal(TOTALNOSUJ));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setTotalExenta(resumenJson.getBigDecimal(TOTALEXENTA));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setTotalGravada(resumenJson.getBigDecimal(TOTALGRAVADA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setSubTotalVentas(resumenJson.getBigDecimal(SUBTOTALVENTAS));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setDescuNoSuj(resumenJson.getBigDecimal(DESCUNOSUJ));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setDescuExenta(resumenJson.getBigDecimal(DESCUEXENTA));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setDescuGravada(resumenJson.getBigDecimal(DESCUGRAVADA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setSubTotal(resumenJson.getBigDecimal(SUBTOTAL));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setIvaRete1(resumenJson.getBigDecimal(IVARETE1));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setMontoTotalOperacion(resumenJson.getBigDecimal(MONTOTOTALOPERACION));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setTotalLetras(resumenJson.getString(TOTALLETRAS));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setCondicionOperacion(resumenJson.getInt(CONDICIONOPERACION));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setTotalDescu(resumenJson.getBigDecimal(TOTALDESCU));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setReteRenta(resumenJson.getBigDecimal(RETERENTA));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {resumen.setIvaPerci1(resumenJson.getBigDecimal(IVAPERCI1));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}

		JSONArray pagosItemsJson = resumenJson.getJSONArray(PAGOS);
		JSONObject pagosItemJson = pagosItemsJson.getJSONObject(0);

		PagosItem newPagosItem = new PagosItem();
		try {newPagosItem.setCodigo(pagosItemJson.getString(CODIGO));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {newPagosItem.setMontoPago(pagosItemJson.getBigDecimal(MONTOPAGO));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {newPagosItem.setReferencia(pagosItemJson.getString(REFERENCIA));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {newPagosItem.setPlazo(pagosItemJson.getString(PLAZO));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}
		try {newPagosItem.setPeriodo(pagosItemJson.getInt(PERIODO));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e);}

		System.out.println("End NotaDeCredito.fillResumen()"); 
		return errorMessages;
	}

	/**
	 * @return the extension
	 */
	public ExtensionNotaDeCredito getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(ExtensionNotaDeCredito extension) {
		this.extension = extension;
	}

	/**
	 * @return the apendice
	 */
	public List<ApendiceItemNotaDeCredito> getApendice() {
		return apendice;
	}

	public void setApendice(List<ApendiceItemNotaDeCredito> apendice) {
		this.apendice = apendice;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}	
}

/**
 * 
 */
package org.shw.lsv.einvoice.fendnotadedebitov3;
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
public class NotaDeDebito extends EDocument {

	static final String ERROR_NOTA_DE_DEBITO_IDENTIFICACION     		= "Documento: Nota de Debito, error en fillIdentification(): ";
	static final String ERROR_NOTA_DE_DEBITO_EMISOR          			= "Documento: Nota de Debito, error en fillEmisor(): ";
	static final String ERROR_NOTA_DE_DEBITO_RECEPTOR           		= "Documento: Nota de Debito, error en fillReceptor(): ";
	static final String ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO			= "Documento: Nota de Debito, error en fillCuerpoDocumento(): ";
	static final String ERROR_NOTA_DE_DEBITO_RESUMEN            		= "Documento: Nota de Debito, error en fillResumen(): ";
	static final String ERROR_NOTA_DE_DEBITO_DOCUMENTOS_RELACIONADOS	= "Documento: Nota de Debito, error en fillDocumentosRelacionados(): ";
	
	IdentificacionNotaDeDebito identificacion;
	List<DocumentoRelacionadoItemNotaDeDebito> documentoRelacionado;
	EmisorNotaDeDebito emisor;
	ReceptorNotaDeDebito receptor;
	VentaTerceroNotaDeDebito ventaTercero = null;
	List<CuerpoDocumentoItemNotaDeDebito> cuerpoDocumento;
	ResumenNotaDeDebito resumen;
	ExtensionNotaDeDebito extension;
	List<ApendiceItemNotaDeDebito> apendice=null;  // null allowed

	/**
	 * No parameters
	 */
	public NotaDeDebito() {
		this.identificacion       = new IdentificacionNotaDeDebito();
	    this.documentoRelacionado = new ArrayList<DocumentoRelacionadoItemNotaDeDebito>();
		this.emisor               = new EmisorNotaDeDebito();
		this.receptor             = new ReceptorNotaDeDebito();
		this.ventaTercero         = new VentaTerceroNotaDeDebito();
	    this.cuerpoDocumento      = new ArrayList<CuerpoDocumentoItemNotaDeDebito>();
		this.resumen              = new ResumenNotaDeDebito();
		this.extension            = new ExtensionNotaDeDebito();
	    this.apendice      		  = new ArrayList<ApendiceItemNotaDeDebito>();
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
	public IdentificacionNotaDeDebito getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(IdentificacionNotaDeDebito identificacion) {
		this.identificacion = identificacion;
	}


	/**
	 * @param identificacion the (IdentificacionFactura) identificacion to set
	 */
	public StringBuffer fillIdentification(JSONObject factoryInput) {
		System.out.println("Start NotaDeDebito.fillIdentificacion()");

		JSONObject identificationJson = factoryInput.getJSONObject(IDENTIFICACION);
		try {identificacion.setMotivoContin(identificationJson.getString(MOTIVOCONTIN));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_IDENTIFICACION + e);}
		try {identificacion.setTipoContingencia(identificationJson.getInt(TIPOCONTINGENCIA));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_IDENTIFICACION + e);}
		try {identificacion.setNumeroControl(identificationJson.getString(NUMEROCONTROL));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_IDENTIFICACION + e);}
		try {identificacion.setCodigoGeneracion(identificationJson.getString(CODIGOGENERACION));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_IDENTIFICACION + e);}
		try {identificacion.setTipoModelo(identificationJson.getInt(TIPOMODELO));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_IDENTIFICACION + e);}
		try {identificacion.setTipoOperacion(identificationJson.getInt(TIPOOPERACION));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_IDENTIFICACION + e);}
		try {identificacion.setFecEmi(identificationJson.getString(FECEMI));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_IDENTIFICACION + e);}
		try {identificacion.setHorEmi(identificationJson.getString(HOREMI));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_IDENTIFICACION + e);}
		try {identificacion.setTipoMoneda(identificationJson.getString(TIPOMONEDA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_IDENTIFICACION + e);}
		try {identificacion.setAmbiente(identificationJson.getString(AMBIENTE));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_IDENTIFICACION + e);}

		System.out.println("End NotaDeDebito.fillIdentificacion()");
		return errorMessages;
	}

	public List<DocumentoRelacionadoItemNotaDeDebito> getDocumentoRelacionado() {
		return documentoRelacionado;
	}

	public void setDocumentoRelacionado(List<DocumentoRelacionadoItemNotaDeDebito> documentoRelacionado) {
		this.documentoRelacionado = documentoRelacionado;
	}

	/**
	 * @return the emisor
	 */
	public EmisorNotaDeDebito getEmisor() {
		return emisor;
	}

	/**
	 * @param emisor the emisor to set
	 */
	public void setEmisor(EmisorNotaDeDebito emisor) {
		this.emisor = emisor;
	}

	public StringBuffer fillEmisor(JSONObject factoryInput) {
		System.out.println("Start NotaDeDebito.fillEmisor()");

		JSONObject emisorJson = factoryInput.getJSONObject(EMISOR);
		try {emisor.setNit(emisorJson.getString(NIT));} 									catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}
		try {emisor.setNrc(emisorJson.getString(NRC));} 									catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}
		try {emisor.setNombre(emisorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}
		try {emisor.setCodActividad(emisorJson.getString(CODACTIVIDAD));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}
		try {emisor.setDescActividad(emisorJson.getString(DESCACTIVIDAD));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}
		try {emisor.setNombreComercial(emisorJson.getString(NOMBRECOMERCIAL));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}		
		try {emisor.setTipoEstablecimiento(emisorJson.getString(TIPOESTABLECIMIENTO));}		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}	

		JSONObject jsonDireccion = emisorJson.getJSONObject(DIRECCION);
		try {emisor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}
		try {emisor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}
		try {emisor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}

		try {emisor.setTelefono(emisorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}
		try {emisor.setCorreo(emisorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_EMISOR + e);}

		System.out.println("End NotaDeDebito.fillEmisor()");
		return errorMessages;
	}

	/**
	 * @return the receptor
	 */
	public ReceptorNotaDeDebito getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor the receptor to set
	 */
	public void setReceptor(ReceptorNotaDeDebito receptor) {
		this.receptor = receptor;
	}

	public StringBuffer fillReceptor(JSONObject factoryInput) {
		System.out.println("Start NotaDeDebito.fillReceptor()");
		
		JSONObject receptorJson = factoryInput.getJSONObject(RECEPTOR);
		try {receptor.setNit(receptorJson.getString(NIT));} 									catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RECEPTOR + e);}
		try {receptor.setNrc(receptorJson.getString(NRC));} 									catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RECEPTOR + e);}
		try {receptor.setNombre(receptorJson.getString(NOMBRE));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RECEPTOR + e);}
		try {receptor.setCodActividad(receptorJson.getString(CODACTIVIDAD));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RECEPTOR + e);}
		try {receptor.setDescActividad(receptorJson.getString(DESCACTIVIDAD));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RECEPTOR + e);}

		JSONObject jsonDireccion = receptorJson.getJSONObject(DIRECCION);
		try {receptor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RECEPTOR + e);}
		try {receptor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RECEPTOR + e);}
		try {receptor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RECEPTOR + e);}

		try {receptor.setTelefono(receptorJson.getString(TELEFONO));} 							catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RECEPTOR + e);}
		try {receptor.setCorreo(receptorJson.getString(CORREO));} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RECEPTOR + e);}

		System.out.println("End NotaDeDebito.fillReceptor()"); 
		return errorMessages;
	}

	/**
	 * @return the ventaTercero
	 */
	public VentaTerceroNotaDeDebito getVentaTercero() {
		return ventaTercero;
	}

	/**
	 * @param ventaTercero the ventaTercero to set
	 */
	public void setVentaTercero(VentaTerceroNotaDeDebito ventaTercero) {
		this.ventaTercero = ventaTercero;
	}

	/**
	 * @return the cuerpoDocumento
	 */
	public List<CuerpoDocumentoItemNotaDeDebito> getCuerpoDocumento() {
		return cuerpoDocumento;
	}

	public void setCuerpoDocumento(List<CuerpoDocumentoItemNotaDeDebito> cuerpoDocumento) {
		this.cuerpoDocumento = cuerpoDocumento;
	}


	public StringBuffer fillCuerpoDocumento(JSONObject factoryInput) {
		System.out.println("Start NotaDeDebito.fillCuerpoDocumento()");

		JSONObject cuerpoDocumentoItemsJson = factoryInput.getJSONObject(CUERPODOCUMENTO);
		JSONArray cuerpoDocumentoArrayJson = cuerpoDocumentoItemsJson.getJSONArray(CUERPODOCUMENTO);
	
		for (int i=0; i< cuerpoDocumentoArrayJson.length(); i++) { 
			JSONObject cuerpoDocumentoItemJson = cuerpoDocumentoArrayJson.getJSONObject(i);
			CuerpoDocumentoItemNotaDeDebito cuerpoDocumentoItemNotaDeDebito = new CuerpoDocumentoItemNotaDeDebito();
			try {cuerpoDocumentoItemNotaDeDebito.setNumItem(cuerpoDocumentoItemJson.getInt(NUMITEM));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeDebito.setTipoItem(cuerpoDocumentoItemJson.getInt(TIPOITEM));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			//try {cuerpoDocumentoItemNotaDeDebito.setNumeroDocumento(cuerpoDocumentoItemJson.getString(NUMERODOCUMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeDebito.setCantidad(cuerpoDocumentoItemJson.getBigDecimal(CANTIDAD));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeDebito.setCodigo(cuerpoDocumentoItemJson.getString(CODIGO));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			
			if (cuerpoDocumentoItemJson.getString(CODTRIBUTO).equals(""))
				try {cuerpoDocumentoItemNotaDeDebito.setCodTributo(null);} 												catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			else
				try {cuerpoDocumentoItemNotaDeDebito.setCodTributo(cuerpoDocumentoItemJson.getString(CODTRIBUTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}

			try {cuerpoDocumentoItemNotaDeDebito.setUniMedida(cuerpoDocumentoItemJson.getInt(UNIMEDIDA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeDebito.setDescripcion(cuerpoDocumentoItemJson.getString(DESCRIPCION));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeDebito.setPrecioUni(cuerpoDocumentoItemJson.getBigDecimal(PRECIOUNI));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeDebito.setMontoDescu(cuerpoDocumentoItemJson.getBigDecimal(MONTODESCU));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeDebito.setVentaNoSuj(cuerpoDocumentoItemJson.getBigDecimal(VENTANOSUJ));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeDebito.setVentaExenta(cuerpoDocumentoItemJson.getBigDecimal(VENTAEXENTA));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			try {cuerpoDocumentoItemNotaDeDebito.setVentaGravada(cuerpoDocumentoItemJson.getBigDecimal(VENTAGRAVADA));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}
			
			JSONArray tributosArrayJson = cuerpoDocumentoItemJson.getJSONArray(TRIBUTOS);
			for (int j=0; j< tributosArrayJson.length(); j++) { 
				String tributosItemJson = tributosArrayJson.getString(j);
				try {cuerpoDocumentoItemNotaDeDebito.getTributos().add(tributosItemJson);} 								catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_CUERPO_DOCUMENTO + e);}	
			}			

			cuerpoDocumento.add(cuerpoDocumentoItemNotaDeDebito);						
		}

		System.out.println("End NotaDeDebito.fillCuerpoDocumento()"); 
		return errorMessages;
	}
	
	public StringBuffer fillDocumentosRelacionados(JSONObject factoryInput) {
		System.out.println("Start NotaDeDebito.fillDocumentosRelacionados()");

		JSONObject documentosRelacionadosItemsJson = factoryInput.getJSONObject(DOCUMENTORELACIONADO);
		JSONArray DocumentosRelacionadosArrayJson = documentosRelacionadosItemsJson.getJSONArray(DOCUMENTORELACIONADO);
	
		for (int i=0; i< DocumentosRelacionadosArrayJson.length(); i++) { 
			JSONObject DocRelaciondadosItemJson = DocumentosRelacionadosArrayJson.getJSONObject(i);
			DocumentoRelacionadoItemNotaDeDebito documentoRelacionadoItem = new DocumentoRelacionadoItemNotaDeDebito();
			try {documentoRelacionadoItem.setNumeroDocumento(DocRelaciondadosItemJson.getString(NUMERODOCUMENTO));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_DOCUMENTOS_RELACIONADOS + e);}
			try {documentoRelacionadoItem.setTipoDocumento(DocRelaciondadosItemJson.getString(TIPODOCUMENTO));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_DOCUMENTOS_RELACIONADOS + e);}
			try {documentoRelacionadoItem.setTipoGeneracion(DocRelaciondadosItemJson.getInt(TIPOGENERACION));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_DOCUMENTOS_RELACIONADOS + e);}
			try {documentoRelacionadoItem.setFechaEmision(DocRelaciondadosItemJson.getString(FECEMI));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_DOCUMENTOS_RELACIONADOS + e);}

			documentoRelacionado.add(documentoRelacionadoItem);						
		}

		System.out.println("End NotaDeDebito.fillDocumentosRelacionados()"); 
		return errorMessages;
	}

	/**
	 * @return the resumen
	 */
	public ResumenNotaDeDebito getResumen() {
		return resumen;
	}

	/**
	 * @param resumen the resumen to set
	 */
	public void setResumen(ResumenNotaDeDebito resumen) {
		this.resumen = resumen;
	}

	public StringBuffer fillResumen(JSONObject factoryInput) {
		System.out.println("Start NotaDeDebito.fillResumen()");
		JSONObject resumenJson = factoryInput.getJSONObject(RESUMEN);

		JSONArray tributosArrayJson = resumenJson.getJSONArray(TRIBUTOS);	
		for (int i=0; i< tributosArrayJson.length(); i++) {
			JSONObject tributosItemJson = tributosArrayJson.getJSONObject(i);
			TributosItem tributosItemCreditoFiscal = new TributosItem();
			try {tributosItemCreditoFiscal.setCodigo(tributosItemJson.getString(CODIGO));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
			try {tributosItemCreditoFiscal.setDescripcion(tributosItemJson.getString(DESCRIPCION));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
			try {tributosItemCreditoFiscal.setValor(tributosItemJson.getBigDecimal(VALOR));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
			resumen.getTributos().add(tributosItemCreditoFiscal);						
		}

		try {resumen.setTotalNoSuj(resumenJson.getBigDecimal(TOTALNOSUJ));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setTotalExenta(resumenJson.getBigDecimal(TOTALEXENTA));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setTotalGravada(resumenJson.getBigDecimal(TOTALGRAVADA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setSubTotalVentas(resumenJson.getBigDecimal(SUBTOTALVENTAS));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setDescuNoSuj(resumenJson.getBigDecimal(DESCUNOSUJ));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setDescuExenta(resumenJson.getBigDecimal(DESCUEXENTA));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setDescuGravada(resumenJson.getBigDecimal(DESCUGRAVADA));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setSubTotal(resumenJson.getBigDecimal(SUBTOTAL));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setIvaRete1(resumenJson.getBigDecimal(IVARETE1));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setMontoTotalOperacion(resumenJson.getBigDecimal(MONTOTOTALOPERACION));} 	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setTotalLetras(resumenJson.getString(TOTALLETRAS));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setCondicionOperacion(resumenJson.getInt(CONDICIONOPERACION));} 			catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setTotalDescu(resumenJson.getBigDecimal(TOTALDESCU));} 					catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setReteRenta(resumenJson.getBigDecimal(RETERENTA));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setIvaPerci1(resumenJson.getBigDecimal(IVAPERCI1));} 						catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {resumen.setNumPagoElectronico(resumenJson.getString(NUMPAGOELECTRONICO));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}

		JSONArray pagosItemsJson = resumenJson.getJSONArray(PAGOS);
		JSONObject pagosItemJson = pagosItemsJson.getJSONObject(0);

		PagosItem newPagosItem = new PagosItem();
		try {newPagosItem.setCodigo(pagosItemJson.getString(CODIGO));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {newPagosItem.setMontoPago(pagosItemJson.getBigDecimal(MONTOPAGO));}	catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {newPagosItem.setReferencia(pagosItemJson.getString(REFERENCIA));} 		catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {newPagosItem.setPlazo(pagosItemJson.getString(PLAZO));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}
		try {newPagosItem.setPeriodo(pagosItemJson.getInt(PERIODO));} 				catch (Exception e) {errorMessages.append(ERROR_NOTA_DE_DEBITO_RESUMEN + e);}


		System.out.println("End NotaDeDebito.fillResumen()"); 
		return errorMessages;
	}

	/**
	 * @return the extension
	 */
	public ExtensionNotaDeDebito getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(ExtensionNotaDeDebito extension) {
		this.extension = extension;
	}

	/**
	 * @return the apendice
	 */
	public List<ApendiceItemNotaDeDebito> getApendice() {
		return apendice;
	}

	public void setApendice(List<ApendiceItemNotaDeDebito> apendice) {
		this.apendice = apendice;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}	
}

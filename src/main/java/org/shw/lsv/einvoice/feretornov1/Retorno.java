/**
 *
 */
package org.shw.lsv.einvoice.feretornov1;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.shw.lsv.einvoice.utils.EDocument;
import org.shw.lsv.einvoice.utils.EDocumentUtils;
import org.shw.lsv.einvoice.utils.TributosItem;


/**
 *
 */
public class Retorno extends EDocument {

	static final String ERROR_EVENTO_DE_RETORNO_IDENTIFICACION          = "Documento: Evento de Retorno, error en fillIdentification(): ";
	static final String ERROR_EVENTO_DE_RETORNO_EMISOR                  = "Documento: Evento de Retorno, error en fillEmisor(): ";
	static final String ERROR_EVENTO_DE_RETORNO_DOCUMENTO               = "Documento: Evento de Retorno, error en fillDocumento(): ";
	static final String ERROR_EVENTO_DE_RETORNO_VENTATERCERO            = "Documento: Evento de Retorno, error en fillVentaTercero(): ";
	static final String ERROR_EVENTO_DE_RETORNO_COMPRATERCERO           = "Documento: Evento de Retorno, error en fillCompraTercero(): ";
	static final String ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO        = "Documento: Evento de Retorno, error en fillCuerpoDocumento(): ";
	static final String ERROR_EVENTO_DE_RETORNO_RESUMEN                 = "Documento: Evento de Retorno, error en fillResumen(): ";
	static final String ERROR_EVENTO_DE_RETORNO_DOCUMENTOS_RELACIONADOS = "Documento: Evento de Retorno, error en fillDocumentosRelacionados(): ";

	// field name constants not present in EDocument base class
	private static final String FUSION               = "fusion";
	private static final String NUMDOCUMENTO         = "numDocumento";
	private static final String NOGRAVADO            = "noGravado";
	private static final String TOTALIVA             = "totalIva";
	private static final String IVARETE              = "ivaRete";
	private static final String TOTALNOGRAVADO       = "totalNoGravado";
	private static final String TOTALPAGAR           = "totalPagar";
	private static final String FECHAEMISION         = "fechaEmision";
	private static final String DOCUMENTO            = "documento";
	private static final String VENTATERCERO         = "ventaTercero";
	private static final String COMPRATERCERO        = "compraTercero";
	private static final String CODPAIS              = "codPais";
	private static final String NOMBREPAIS           = "nombrePais";
	private static final String CODESTABLEMH         = "codEstableMH";
	private static final String CODESTABLE           = "codEstable";
	private static final String CODPUNTOVENTAMH      = "codPuntoVentaMH";
	private static final String CODPUNTOVENTA        = "codPuntoVenta";
	private static final String RECINTOFISCAL        = "recintoFiscal";
	private static final String TIPOREGIMEN          = "tipoRegimen";
	private static final String REGIMEN              = "regimen";
	private static final String TIPOITEMEXPOR        = "tipoItemExpor";
	private static final String COMPRA               = "compra";
	private static final String PSV                  = "psv";
	private static final String IVAITEM              = "ivaItem";
	private static final String SEGURO               = "seguro";
	private static final String FLETE                = "flete";
	private static final String RETERRENTA           = "reteRenta";
	private static final String CODDOMICILIADO       = "codDomiciliado";
	private static final String TOTALCOMPRAEXCLUIDOS = "totalCompraExcluidos";
	private static final String TOTALSEGURO          = "totalSeguro";
	private static final String TOTALFLETE           = "totalFlete";
	private static final String TOTALNOONEROSAS      = "totalNoOnerosas";
	private static final String SALDOFAVOR           = "saldoFavor";

	IdentificacionRetorno identificacion;
	List<DocumentoRelacionadoItemRetorno> documentoRelacionado;
	EmisorRetorno emisor;
	ReceptorRetorno documento;
	VentaTerceroRetorno ventaTercero = null;   // null allowed
	CompraTerceroRetorno compraTercero = null; // null allowed
	List<CuerpoDocumentoItemRetorno> cuerpoDocumento;
	ResumenRetorno resumen;
	List<ApendiceItemRetorno> apendice = null; // null allowed

	/**
	 * No parameters
	 */
	public Retorno() {
		this.identificacion       = new IdentificacionRetorno();
		this.documentoRelacionado = new ArrayList<DocumentoRelacionadoItemRetorno>();
		this.emisor               = new EmisorRetorno();
		this.documento            = new ReceptorRetorno();
		this.cuerpoDocumento      = new ArrayList<CuerpoDocumentoItemRetorno>();
		this.resumen              = new ResumenRetorno();
		this.apendice             = new ArrayList<ApendiceItemRetorno>();
	}

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	public IdentificacionRetorno getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(IdentificacionRetorno identificacion) {
		this.identificacion = identificacion;
	}

	public StringBuffer fillIdentification(JSONObject factoryInput) {
		System.out.println("Start EventoDeRetorno.fillIdentificacion()");

		JSONObject identificationJson = factoryInput.getJSONObject(IDENTIFICACION);
		if (!identificationJson.isNull(MOTIVOCONTIN) && !identificationJson.getString(MOTIVOCONTIN).equals("")) {
			try { identificacion.setMotivoContin(identificationJson.getString(MOTIVOCONTIN)); }      catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_IDENTIFICACION + e); }
			try { identificacion.setTipoContingencia(identificationJson.getInt(TIPOCONTINGENCIA)); } catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_IDENTIFICACION + e); }
		}
		try { identificacion.setCodigoGeneracion(identificationJson.getString(CODIGOGENERACION)); } catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_IDENTIFICACION + e); }
		try { identificacion.setTipoModelo(identificationJson.getInt(TIPOMODELO)); }                catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_IDENTIFICACION + e); }
		try { identificacion.setTipoOperacion(identificationJson.getInt(TIPOOPERACION)); }          catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_IDENTIFICACION + e); }
		try { identificacion.setFecEmi(identificationJson.getString(FECEMI)); }                     catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_IDENTIFICACION + e); }
		try { identificacion.setHorEmi(identificationJson.getString(HOREMI)); }                     catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_IDENTIFICACION + e); }
		try { identificacion.setTipoMoneda(identificationJson.getString(TIPOMONEDA)); }             catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_IDENTIFICACION + e); }
		try { identificacion.setAmbiente(identificationJson.getString(AMBIENTE)); }                 catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_IDENTIFICACION + e); }
		try { identificacion.setFusion(identificationJson.isNull(FUSION) ? null : identificationJson.getString(FUSION)); } catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_IDENTIFICACION + e); }

		System.out.println("End EventoDeRetorno.fillIdentificacion()");
		return errorMessages;
	}

	public List<DocumentoRelacionadoItemRetorno> getDocumentoRelacionado() {
		return documentoRelacionado;
	}

	public void setDocumentoRelacionado(List<DocumentoRelacionadoItemRetorno> documentoRelacionado) {
		this.documentoRelacionado = documentoRelacionado;
	}

	public StringBuffer fillDocumentosRelacionados(JSONObject factoryInput) {
		System.out.println("Start EventoDeRetorno.fillDocumentosRelacionados()");

		JSONObject documentosRelacionadosItemsJson = factoryInput.getJSONObject(DOCUMENTORELACIONADO);
		JSONArray documentosRelacionadosArrayJson = documentosRelacionadosItemsJson.getJSONArray(DOCUMENTORELACIONADO);

		for (int i = 0; i < documentosRelacionadosArrayJson.length(); i++) {
			JSONObject docRelacionadoItemJson = documentosRelacionadosArrayJson.getJSONObject(i);
			DocumentoRelacionadoItemRetorno documentoRelacionadoItem = new DocumentoRelacionadoItemRetorno();
			try { documentoRelacionadoItem.setTipoDocumento(docRelacionadoItemJson.getString(TIPODOCUMENTO)); }       catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_DOCUMENTOS_RELACIONADOS + e); }
			try { documentoRelacionadoItem.setCodigoGeneracion(docRelacionadoItemJson.getString(CODIGOGENERACION)); } catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_DOCUMENTOS_RELACIONADOS + e); }
			try { documentoRelacionadoItem.setFechaEmision(docRelacionadoItemJson.getString(FECHAEMISION)); }         catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_DOCUMENTOS_RELACIONADOS + e); }

			documentoRelacionado.add(documentoRelacionadoItem);
		}

		System.out.println("End EventoDeRetorno.fillDocumentosRelacionados()");
		return errorMessages;
	}

	public EmisorRetorno getEmisor() {
		return emisor;
	}

	public void setEmisor(EmisorRetorno emisor) {
		this.emisor = emisor;
	}

	public StringBuffer fillEmisor(JSONObject factoryInput) {
		System.out.println("Start EventoDeRetorno.fillEmisor()");

		JSONObject emisorJson = factoryInput.getJSONObject(EMISOR);
		try { emisor.setNit(emisorJson.getString(NIT)); }                                                                                                                             catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_EMISOR + e); }
		try { emisor.setNombre(emisorJson.getString(NOMBRE)); }                                                                                                                       catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_EMISOR + e); }
		try { emisor.setCodEstableMH(emisorJson.getString(CODESTABLEMH)); }                                                                                                           catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_EMISOR + e); }
		try { emisor.setCodEstable(emisorJson.isNull(CODESTABLE) ? null : emisorJson.getString(CODESTABLE)); }                                                                        catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_EMISOR + e); }
		try { emisor.setCodPuntoVentaMH(emisorJson.getString(CODPUNTOVENTAMH)); }                                                                                                     catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_EMISOR + e); }
		try { emisor.setCodPuntoVenta(emisorJson.isNull(CODPUNTOVENTA) ? null : emisorJson.getString(CODPUNTOVENTA)); }                                                               catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_EMISOR + e); }
		try { emisor.setRecintoFiscal(emisorJson.isNull(RECINTOFISCAL) ? null : emisorJson.getString(RECINTOFISCAL)); }                                                               catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_EMISOR + e); }
		try { emisor.setTipoRegimen(emisorJson.isNull(TIPOREGIMEN) ? null : emisorJson.getString(TIPOREGIMEN)); }                                                                     catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_EMISOR + e); }
		try { emisor.setRegimen(emisorJson.isNull(REGIMEN) ? null : emisorJson.getString(REGIMEN)); }                                                                                 catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_EMISOR + e); }
		try { emisor.setTipoItemExpor(emisorJson.isNull(TIPOITEMEXPOR) ? null : emisorJson.getInt(TIPOITEMEXPOR)); }                                                                  catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_EMISOR + e); }

		System.out.println("End EventoDeRetorno.fillEmisor()");
		return errorMessages;
	}

	public ReceptorRetorno getDocumento() {
		return documento;
	}

	public void setDocumento(ReceptorRetorno documento) {
		this.documento = documento;
	}

	public StringBuffer fillDocumento(JSONObject factoryInput) {
		System.out.println("Start EventoDeRetorno.fillDocumento()");

		if (factoryInput.isNull(DOCUMENTO)) {
			this.documento = null;
			System.out.println("End EventoDeRetorno.fillDocumento() — documento is null");
			return errorMessages;
		}

		JSONObject documentoJson = factoryInput.getJSONObject(DOCUMENTO);
		try { documento.setTipoDocumento(documentoJson.isNull(TIPODOCUMENTO) ? null : documentoJson.getString(TIPODOCUMENTO)); } catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_DOCUMENTO + e); }
		try { documento.setNumDocumento(documentoJson.isNull(NUMDOCUMENTO) ? null : documentoJson.getString(NUMDOCUMENTO)); }     catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_DOCUMENTO + e); }
		try { documento.setNombre(documentoJson.isNull(NOMBRE) ? null : documentoJson.getString(NOMBRE)); }                      catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_DOCUMENTO + e); }
		try { documento.setCodPais(documentoJson.isNull(CODPAIS) ? null : documentoJson.getString(CODPAIS)); }                   catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_DOCUMENTO + e); }
		try { documento.setNombrePais(documentoJson.isNull(NOMBREPAIS) ? null : documentoJson.getString(NOMBREPAIS)); }          catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_DOCUMENTO + e); }
		try { documento.setTelefono(documentoJson.isNull(TELEFONO) ? null : documentoJson.getString(TELEFONO)); }                catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_DOCUMENTO + e); }
		try { documento.setCorreo(documentoJson.isNull(CORREO) ? null : documentoJson.getString(CORREO)); }                      catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_DOCUMENTO + e); }

		System.out.println("End EventoDeRetorno.fillDocumento()");
		return errorMessages;
	}

	public VentaTerceroRetorno getVentaTercero() {
		return ventaTercero;
	}

	public void setVentaTercero(VentaTerceroRetorno ventaTercero) {
		this.ventaTercero = ventaTercero;
	}

	public StringBuffer fillVentaTercero(JSONObject factoryInput) {
		System.out.println("Start EventoDeRetorno.fillVentaTercero()");

		if (factoryInput.isNull(VENTATERCERO)) {
			this.ventaTercero = null;
			System.out.println("End EventoDeRetorno.fillVentaTercero() — ventaTercero is null");
			return errorMessages;
		}

		ventaTercero = new VentaTerceroRetorno();
		JSONObject vtJson = factoryInput.getJSONObject(VENTATERCERO);
		try { ventaTercero.setNit(vtJson.isNull(NIT) ? null : vtJson.getString(NIT)); }                                             catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_VENTATERCERO + e); }
		try { ventaTercero.setNombre(vtJson.isNull(NOMBRE) ? null : vtJson.getString(NOMBRE)); }                                    catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_VENTATERCERO + e); }
		try { ventaTercero.setCodDomiciliado(vtJson.isNull(CODDOMICILIADO) ? null : vtJson.getInt(CODDOMICILIADO)); }               catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_VENTATERCERO + e); }

		System.out.println("End EventoDeRetorno.fillVentaTercero()");
		return errorMessages;
	}

	public CompraTerceroRetorno getCompraTercero() {
		return compraTercero;
	}

	public void setCompraTercero(CompraTerceroRetorno compraTercero) {
		this.compraTercero = compraTercero;
	}

	public StringBuffer fillCompraTercero(JSONObject factoryInput) {
		System.out.println("Start EventoDeRetorno.fillCompraTercero()");

		if (factoryInput.isNull(COMPRATERCERO)) {
			this.compraTercero = null;
			System.out.println("End EventoDeRetorno.fillCompraTercero() — compraTercero is null");
			return errorMessages;
		}

		compraTercero = new CompraTerceroRetorno();
		JSONObject ctJson = factoryInput.getJSONObject(COMPRATERCERO);
		try { compraTercero.setNumDocumento(ctJson.isNull(NUMDOCUMENTO) ? null : ctJson.getString(NUMDOCUMENTO)); } catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_COMPRATERCERO + e); }
		try { compraTercero.setNombre(ctJson.isNull(NOMBRE) ? null : ctJson.getString(NOMBRE)); }                  catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_COMPRATERCERO + e); }

		System.out.println("End EventoDeRetorno.fillCompraTercero()");
		return errorMessages;
	}

	public List<CuerpoDocumentoItemRetorno> getCuerpoDocumento() {
		return cuerpoDocumento;
	}

	public void setCuerpoDocumento(List<CuerpoDocumentoItemRetorno> cuerpoDocumento) {
		this.cuerpoDocumento = cuerpoDocumento;
	}

	public StringBuffer fillCuerpoDocumento(JSONObject factoryInput) {
		System.out.println("Start EventoDeRetorno.fillCuerpoDocumento()");

		JSONObject cuerpoDocumentoItemsJson = factoryInput.getJSONObject(CUERPODOCUMENTO);
		JSONArray cuerpoDocumentoArrayJson = cuerpoDocumentoItemsJson.getJSONArray(CUERPODOCUMENTO);

		for (int i = 0; i < cuerpoDocumentoArrayJson.length(); i++) {
			JSONObject itemJson = cuerpoDocumentoArrayJson.getJSONObject(i);
			CuerpoDocumentoItemRetorno item = new CuerpoDocumentoItemRetorno();
			try { item.setNumItem(itemJson.getInt(NUMITEM)); }                                                      catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setTipoItem(itemJson.getInt(TIPOITEM)); }                                                    catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setCodigoGeneracion(itemJson.getString(CODIGOGENERACION)); }                                 catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setCantidad(itemJson.getBigDecimal(CANTIDAD)); }                                             catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setPrecioUni(itemJson.getBigDecimal(PRECIOUNI)); }                                           catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setDescripcion(itemJson.getString(DESCRIPCION)); }                                           catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setCodigo(itemJson.isNull(CODIGO) ? null : itemJson.getString(CODIGO)); }                    catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setUniMedida(itemJson.getInt(UNIMEDIDA)); }                                                  catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setMontoDescu(itemJson.getBigDecimal(MONTODESCU)); }                                         catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setCodTributo(itemJson.isNull(CODTRIBUTO) ? null : itemJson.getString(CODTRIBUTO)); }        catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setVentaNoSuj(itemJson.getBigDecimal(VENTANOSUJ)); }                                         catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setVentaExenta(itemJson.getBigDecimal(VENTAEXENTA)); }                                       catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setVentaGravada(itemJson.getBigDecimal(VENTAGRAVADA)); }                                     catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setCompra(itemJson.getBigDecimal(COMPRA)); }                                                 catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }

			if (!itemJson.isNull(TRIBUTOS)) {
				JSONArray tributosArrayJson = itemJson.getJSONArray(TRIBUTOS);
				for (int j = 0; j < tributosArrayJson.length(); j++) {
					String tributosItemJson = tributosArrayJson.getString(j);
					try { item.getTributos().add(tributosItemJson); } 												catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
				}
				
			}

			try { item.setPsv(itemJson.getBigDecimal(PSV)); }                                                      catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setIvaItem(itemJson.getBigDecimal(IVAITEM)); }                                               catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setNoGravado(itemJson.getBigDecimal(NOGRAVADO)); }                                           catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setSeguro(itemJson.getBigDecimal(SEGURO)); }                                                 catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setFlete(itemJson.getBigDecimal(FLETE)); }                                                   catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setIvaRete(itemJson.getBigDecimal(IVARETE)); }                                               catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }
			try { item.setReteRenta(itemJson.getBigDecimal(RETERRENTA)); }                                          catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_CUERPO_DOCUMENTO + e); }

			cuerpoDocumento.add(item);
		}

		System.out.println("End EventoDeRetorno.fillCuerpoDocumento()");
		return errorMessages;
	}

	public ResumenRetorno getResumen() {
		return resumen;
	}

	public void setResumen(ResumenRetorno resumen) {
		this.resumen = resumen;
	}

	public StringBuffer fillResumen(JSONObject factoryInput) {
		System.out.println("Start EventoDeRetorno.fillResumen()");
		JSONObject resumenJson = factoryInput.getJSONObject(RESUMEN);

		if (!resumenJson.isNull(TRIBUTOS)) {
			JSONArray tributosArrayJson = resumenJson.getJSONArray(TRIBUTOS);
			for (int i = 0; i < tributosArrayJson.length(); i++) {
				JSONObject tributosItemJson = tributosArrayJson.getJSONObject(i);
				TributosItem tributosItem = new TributosItem();
				try { tributosItem.setCodigo(tributosItemJson.getString(CODIGO)); }           catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
				try { tributosItem.setDescripcion(tributosItemJson.getString(DESCRIPCION)); } catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
				try { tributosItem.setValor(tributosItemJson.getBigDecimal(VALOR)); }         catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
				resumen.getTributos().add(tributosItem);
			}
		}

		try { resumen.setTotalNoSuj(resumenJson.getBigDecimal(TOTALNOSUJ)); }                                                                              catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setTotalExenta(resumenJson.getBigDecimal(TOTALEXENTA)); }                                                                            catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setTotalGravada(resumenJson.getBigDecimal(TOTALGRAVADA)); }                                                                          catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setTotalCompraExcluidos(resumenJson.getBigDecimal(TOTALCOMPRAEXCLUIDOS)); }                                                          catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setSubTotalVentas(resumenJson.getBigDecimal(SUBTOTALVENTAS)); }                                                                      catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setTotalSeguro(resumenJson.isNull(TOTALSEGURO) ? null : resumenJson.getBigDecimal(TOTALSEGURO)); }                                   catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setTotalFlete(resumenJson.isNull(TOTALFLETE) ? null : resumenJson.getBigDecimal(TOTALFLETE)); }                                      catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setMontoTotalOperacion(resumenJson.getBigDecimal(MONTOTOTALOPERACION)); }                                                            catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setIvaRete(resumenJson.getBigDecimal(IVARETE)); }                                                                                    catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setReteRenta(resumenJson.isNull(RETERRENTA) ? null : resumenJson.getBigDecimal(RETERRENTA)); }                                       catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setTotalNoGravado(resumenJson.getBigDecimal(TOTALNOGRAVADO)); }                                                                      catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setTotalPagar(resumenJson.getBigDecimal(TOTALPAGAR)); }                                                                              catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setTotalLetras(resumenJson.isNull(TOTALLETRAS) ? null : resumenJson.getString(TOTALLETRAS)); }                                       catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setTotalNoOnerosas(resumenJson.getBigDecimal(TOTALNOONEROSAS)); }                                                                    catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setTotalIva(resumenJson.getBigDecimal(TOTALIVA)); }                                                                                  catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }
		try { resumen.setSaldoFavor(resumenJson.getBigDecimal(SALDOFAVOR)); }                                                                              catch (Exception e) { errorMessages.append(ERROR_EVENTO_DE_RETORNO_RESUMEN + e); }

		System.out.println("End EventoDeRetorno.fillResumen()");
		return errorMessages;
	}

	public List<ApendiceItemRetorno> getApendice() {
		return apendice;
	}

	public void setApendice(List<ApendiceItemRetorno> apendice) {
		this.apendice = apendice;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}

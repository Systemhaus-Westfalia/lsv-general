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
import org.shw.lsv.einvoice.utils.TributosItem;


/**
 *
 */
public class NotaDeCredito extends EDocument {

	static final String ERROR_NOTA_DE_CREDITO_IDENTIFICACION          = "Documento: Nota de Credito, error en fillIdentification(): ";
	static final String ERROR_NOTA_DE_CREDITO_EMISOR                  = "Documento: Nota de Credito, error en fillEmisor(): ";
	static final String ERROR_NOTA_DE_CREDITO_RECEPTOR                = "Documento: Nota de Credito, error en fillReceptor(): ";
	static final String ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO        = "Documento: Nota de Credito, error en fillCuerpoDocumento(): ";
	static final String ERROR_NOTA_DE_CREDITO_RESUMEN                 = "Documento: Nota de Credito, error en fillResumen(): ";
	static final String ERROR_NOTA_DE_CREDITO_DOCUMENTOS_RELACIONADOS = "Documento: Nota de Credito, error en fillDocumentosRelacionados(): ";

	// v4 field name constants not present in EDocument base class

	IdentificacionNotaDeCredito identificacion;
	List<DocumentoRelacionadoItemNotaDeCredito> documentoRelacionado;
	EmisorNotaDeCredito emisor;
	ReceptorNotaDeCredito receptor;
	VentaTerceroNotaDeCredito ventaTercero = null;
	List<CuerpoDocumentoItemNotaDeCredito> cuerpoDocumento;
	ResumenNotaDeCredito resumen;
	List<ApendiceItemNotaDeCredito> apendice = null; // null allowed

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
		this.apendice             = new ArrayList<ApendiceItemNotaDeCredito>();
	}

	/**
	 * Validate the Schema conditions
	 */
	public String validateValues() {
		return EDocumentUtils.VALIDATION_RESULT_OK;
	}

	public IdentificacionNotaDeCredito getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(IdentificacionNotaDeCredito identificacion) {
		this.identificacion = identificacion;
	}

	public StringBuffer fillIdentification(JSONObject factoryInput) {
		System.out.println("Start NotaDeCredito.fillIdentificacion()");

		JSONObject identificationJson = factoryInput.getJSONObject(IDENTIFICACION);
		if (!identificationJson.isNull(MOTIVOCONTIN) && !identificationJson.getString(MOTIVOCONTIN).equals("")) {
			try { identificacion.setMotivoContin(identificationJson.getString(MOTIVOCONTIN)); }      catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e); }
			try { identificacion.setTipoContingencia(identificationJson.getInt(TIPOCONTINGENCIA)); } catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e); }
		}
		try { identificacion.setNumeroControl(identificationJson.getString(NUMEROCONTROL)); }    catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e); }
		try { identificacion.setCodigoGeneracion(identificationJson.getString(CODIGOGENERACION)); } catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e); }
		try { identificacion.setTipoModelo(identificationJson.getInt(TIPOMODELO)); }             catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e); }
		try { identificacion.setTipoOperacion(identificationJson.getInt(TIPOOPERACION)); }       catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e); }
		try { identificacion.setFecEmi(identificationJson.getString(FECEMI)); }                  catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e); }
		try { identificacion.setHorEmi(identificationJson.getString(HOREMI)); }                  catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e); }
		try { identificacion.setTipoMoneda(identificationJson.getString(TIPOMONEDA)); }          catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e); }
		try { identificacion.setAmbiente(identificationJson.getString(AMBIENTE)); }              catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e); }
		try { identificacion.setFusion(identificationJson.isNull(FUSION) ? null : identificationJson.getString(FUSION)); } catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_IDENTIFICACION + e); }

		System.out.println("End NotaDeCredito.fillIdentificacion()");
		return errorMessages;
	}

	public List<DocumentoRelacionadoItemNotaDeCredito> getDocumentoRelacionado() {
		return documentoRelacionado;
	}

	public void setDocumentoRelacionado(List<DocumentoRelacionadoItemNotaDeCredito> documentoRelacionado) {
		this.documentoRelacionado = documentoRelacionado;
	}

	public EmisorNotaDeCredito getEmisor() {
		return emisor;
	}

	public void setEmisor(EmisorNotaDeCredito emisor) {
		this.emisor = emisor;
	}

	public StringBuffer fillEmisor(JSONObject factoryInput) {
		System.out.println("Start NotaDeCredito.fillEmisor()");

		JSONObject emisorJson = factoryInput.getJSONObject(EMISOR);
		try { emisor.setNit(emisorJson.getString(NIT)); }                                                                                         catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }
		try { emisor.setNrc(emisorJson.isNull(NRC) ? null : emisorJson.getString(NRC)); }                                                         catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }
		try { emisor.setNombre(emisorJson.getString(NOMBRE)); }                                                                                   catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }
		try { emisor.setCodActividad(emisorJson.getString(CODACTIVIDAD)); }                                                                       catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }
		try { emisor.setDescActividad(emisorJson.getString(DESCACTIVIDAD)); }                                                                     catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }
		try { emisor.setNombreComercial(emisorJson.isNull(NOMBRECOMERCIAL) ? null : emisorJson.getString(NOMBRECOMERCIAL)); }                     catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }

		JSONObject jsonDireccion = emisorJson.getJSONObject(DIRECCION);
		try { emisor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO)); } catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }
		try { emisor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO)); }       catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }
		try { emisor.getDireccion().setDistrito(jsonDireccion.getString(DISTRITO)); }         catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }
		try { emisor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO)); }   catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }

		try { emisor.setTelefono(emisorJson.getString(TELEFONO)); }                                                                              catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }
		try { emisor.setCorreo(emisorJson.getString(CORREO)); }                                                                                  catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_EMISOR + e); }

		System.out.println("End NotaDeCredito.fillEmisor()");
		return errorMessages;
	}

	public ReceptorNotaDeCredito getReceptor() {
		return receptor;
	}

	public void setReceptor(ReceptorNotaDeCredito receptor) {
		this.receptor = receptor;
	}

	public StringBuffer fillReceptor(JSONObject factoryInput) {
		System.out.println("Start NotaDeCredito.fillReceptor()");

		JSONObject receptorJson = factoryInput.getJSONObject(RECEPTOR);
		try { receptor.setTipoDocumento(receptorJson.getString(TIPODOCUMENTO)); }                                                                 catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }
		try { receptor.setNumDocumento(receptorJson.getString(NUMDOCUMENTO)); }                                                                   catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }
		try { receptor.setNrc(receptorJson.isNull(NRC) ? null : receptorJson.getString(NRC)); }                                                   catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }
		try { receptor.setNombre(receptorJson.getString(NOMBRE)); }                                                                               catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }
		try { receptor.setCodActividad(receptorJson.getString(CODACTIVIDAD)); }                                                                   catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }
		try { receptor.setDescActividad(receptorJson.getString(DESCACTIVIDAD)); }                                                                 catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }
		try { receptor.setNombreComercial(receptorJson.isNull(NOMBRECOMERCIAL) ? null : receptorJson.getString(NOMBRECOMERCIAL)); }               catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }

		JSONObject jsonDireccion = receptorJson.getJSONObject(DIRECCION);
		try { receptor.getDireccion().setDepartamento(jsonDireccion.getString(DEPARTAMENTO)); } catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }
		try { receptor.getDireccion().setMunicipio(jsonDireccion.getString(MUNICIPIO)); }       catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }
		try { receptor.getDireccion().setDistrito(jsonDireccion.getString(DISTRITO)); }         catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }
		try { receptor.getDireccion().setComplemento(jsonDireccion.getString(COMPLEMENTO)); }   catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }

		try { receptor.setTelefono(receptorJson.isNull(TELEFONO) ? null : receptorJson.getString(TELEFONO)); }                                    catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }
		try { receptor.setCorreo(receptorJson.isNull(CORREO) ? null : receptorJson.getString(CORREO)); }                                          catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RECEPTOR + e); }

		System.out.println("End NotaDeCredito.fillReceptor()");
		return errorMessages;
	}

	public VentaTerceroNotaDeCredito getVentaTercero() {
		return ventaTercero;
	}

	public void setVentaTercero(VentaTerceroNotaDeCredito ventaTercero) {
		this.ventaTercero = ventaTercero;
	}

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

		for (int i = 0; i < cuerpoDocumentoArrayJson.length(); i++) {
			JSONObject itemJson = cuerpoDocumentoArrayJson.getJSONObject(i);
			CuerpoDocumentoItemNotaDeCredito item = new CuerpoDocumentoItemNotaDeCredito();
			try { item.setNumItem(itemJson.getInt(NUMITEM)); }                                    catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setTipoItem(itemJson.getInt(TIPOITEM)); }                                  catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setNumeroDocumento(itemJson.getString(NUMERODOCUMENTO)); }                 catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setCantidad(itemJson.getBigDecimal(CANTIDAD)); }                           catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setCodigo(itemJson.isNull(CODIGO) ? null : itemJson.getString(CODIGO)); }  catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }

			if (itemJson.isNull(CODTRIBUTO))
				try { item.setCodTributo(null); }                                                 catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			else
				try { item.setCodTributo(itemJson.getString(CODTRIBUTO)); }                       catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }

			try { item.setUniMedida(itemJson.getInt(UNIMEDIDA)); }                               catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setDescripcion(itemJson.getString(DESCRIPCION)); }                        catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setPrecioUni(itemJson.getBigDecimal(PRECIOUNI)); }                        catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setMontoDescu(itemJson.getBigDecimal(MONTODESCU)); }                      catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setVentaNoSuj(itemJson.getBigDecimal(VENTANOSUJ)); }                      catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setVentaExenta(itemJson.getBigDecimal(VENTAEXENTA)); }                    catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setVentaGravada(itemJson.getBigDecimal(VENTAGRAVADA)); }                  catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }

			if (!itemJson.isNull(TRIBUTOS)) {
				JSONArray tributosArrayJson = itemJson.getJSONArray(TRIBUTOS);
				for (int j = 0; j < tributosArrayJson.length(); j++) {
					String tributosItemJson = tributosArrayJson.getString(j);
					try { item.getTributos().add(tributosItemJson); } catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
				}
			}
			try { item.setDescripcion(itemJson.getString(DESCRIPCION)); }                            catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }

			try { item.setNoGravado(itemJson.getBigDecimal(NOGRAVADO)); }                        catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setIvaPerci(itemJson.getBigDecimal(IVAPERCI)); }                          catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setTotalIva(itemJson.getBigDecimal(TOTALIVA)); }                          catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }
			try { item.setIvaRete(itemJson.getBigDecimal(IVARETE)); }                            catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_CUERPO_DOCUMENTO + e); }

			cuerpoDocumento.add(item);
		}

		System.out.println("End NotaDeCredito.fillCuerpoDocumento()");
		return errorMessages;
	}

	public StringBuffer fillDocumentosRelacionados(JSONObject factoryInput) {
		System.out.println("Start NotaDeCredito.fillDocumentosRelacionados()");

		JSONObject documentosRelacionadosItemsJson = factoryInput.getJSONObject(DOCUMENTORELACIONADO);
		JSONArray documentosRelacionadosArrayJson = documentosRelacionadosItemsJson.getJSONArray(DOCUMENTORELACIONADO);

		for (int i = 0; i < documentosRelacionadosArrayJson.length(); i++) {
			JSONObject docRelacionadoItemJson = documentosRelacionadosArrayJson.getJSONObject(i);
			DocumentoRelacionadoItemNotaDeCredito documentoRelacionadoItem = new DocumentoRelacionadoItemNotaDeCredito();
			try { documentoRelacionadoItem.setNumeroDocumento(docRelacionadoItemJson.getString(NUMERODOCUMENTO)); } catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_DOCUMENTOS_RELACIONADOS + e); }
			try { documentoRelacionadoItem.setTipoDocumento(docRelacionadoItemJson.getString(TIPODOCUMENTO)); }     catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_DOCUMENTOS_RELACIONADOS + e); }
			try { documentoRelacionadoItem.setTipoGeneracion(docRelacionadoItemJson.getInt(TIPOGENERACION)); }      catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_DOCUMENTOS_RELACIONADOS + e); }
			try { documentoRelacionadoItem.setFechaEmision(docRelacionadoItemJson.getString(FECHAEMISION)); }       catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_DOCUMENTOS_RELACIONADOS + e); }

			documentoRelacionado.add(documentoRelacionadoItem);
		}

		System.out.println("End NotaDeCredito.fillDocumentosRelacionados()");
		return errorMessages;
	}

	public ResumenNotaDeCredito getResumen() {
		return resumen;
	}

	public void setResumen(ResumenNotaDeCredito resumen) {
		this.resumen = resumen;
	}

	public StringBuffer fillResumen(JSONObject factoryInput) {
		System.out.println("Start NotaDeCredito.fillResumen()");
		JSONObject resumenJson = factoryInput.getJSONObject(RESUMEN);

		if (!resumenJson.isNull(TRIBUTOS)) {
			JSONArray tributosArrayJson = resumenJson.getJSONArray(TRIBUTOS);
			for (int i = 0; i < tributosArrayJson.length(); i++) {
				JSONObject tributosItemJson = tributosArrayJson.getJSONObject(i);
				TributosItem tributosItem = new TributosItem();
				try { tributosItem.setCodigo(tributosItemJson.getString(CODIGO)); }         catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
				try { tributosItem.setDescripcion(tributosItemJson.getString(DESCRIPCION)); } catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
				try { tributosItem.setValor(tributosItemJson.getBigDecimal(VALOR)); }       catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
				resumen.getTributos().add(tributosItem);
			}
		}

		try { resumen.setTotalNoSuj(resumenJson.getBigDecimal(TOTALNOSUJ)); }                                                                    catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setTotalExenta(resumenJson.getBigDecimal(TOTALEXENTA)); }                                                                  catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setTotalGravada(resumenJson.getBigDecimal(TOTALGRAVADA)); }                                                                catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setSubTotalVentas(resumenJson.getBigDecimal(SUBTOTALVENTAS)); }                                                            catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setTotalDescu(resumenJson.getBigDecimal(TOTALDESCU)); }                                                                    catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setMontoTotalOperacion(resumenJson.getBigDecimal(MONTOTOTALOPERACION)); }                                                  catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setIvaPerci(resumenJson.getBigDecimal(IVAPERCI)); }                                                                        catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setTotalIva(resumenJson.getBigDecimal(TOTALIVA)); }                                                                        catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setIvaRete(resumenJson.getBigDecimal(IVARETE)); }                                                                          catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setTotalNoGravado(resumenJson.getBigDecimal(TOTALNOGRAVADO)); }                                                            catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setTotalPagar(resumenJson.getBigDecimal(TOTALPAGAR)); }                                                                    catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setTotalLetras(resumenJson.isNull(TOTALLETRAS) ? null : resumenJson.getString(TOTALLETRAS)); }                             catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setCondicionOperacion(resumenJson.getInt(CONDICIONOPERACION)); }                                                           catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setObservaciones(resumenJson.isNull(OBSERVACIONES) ? null : resumenJson.getString(OBSERVACIONES)); }                       catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }
		try { resumen.setCodigoRetencionMH(resumenJson.isNull(CODIGORETENCIONMH) ? null : resumenJson.getString(CODIGORETENCIONMH)); }           catch (Exception e) { errorMessages.append(ERROR_NOTA_DE_CREDITO_RESUMEN + e); }

		System.out.println("End NotaDeCredito.fillResumen()");
		return errorMessages;
	}

	public List<ApendiceItemNotaDeCredito> getApendice() {
		return apendice;
	}

	public void setApendice(List<ApendiceItemNotaDeCredito> apendice) {
		this.apendice = apendice;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}

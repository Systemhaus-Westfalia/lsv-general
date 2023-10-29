/**
 * 
 */
package org.shw.lsv.einvoice.utils;

import org.compiere.model.MSysConfig;

/**
 * 
 */
public class EDocument {
	public StringBuffer errorMessages = new StringBuffer();
	public static String ABSDIRECTORY = MSysConfig.getValue("EI_PATH");	
	
	public static final String IDENTIFICACION 		= "identificacion";
	public static final String EMISOR 				= "emisor";
	public static final String RESUMEN 				= "resumen";
	public static final String CUERPODOCUMENTO 		= "cuerpoDocumento";
	public static final String DOCUMENTORELACIONADO = "documentoRelacionado";
	public static final String OTROSDOCUMENTOS 		= "otrosDocumentos";
	public static final String RECEPTOR 			= "receptor";
	public static final String VENTATERCERO 		= "ventaTercero";        
	public static final String EXTENSION 			= "extension";
	public static final String APENDICE 			= "apendice";
	public static final String DOCUMENTO 			= "documento";
	public static final String MOTIVO 				= "motivo";
	public static final String SUJETOEXCLUIDO		= "sujetoExcluido";
	public static final String ETIQUETA				= "etiqueta";
	public static final String CAMPO				= "campo";
	
	
	public static final String NUMEROCONTROL 		=  "numeroControl";
	public static final String CODIGOGENERACION 	=  "codigoGeneracion";
	public static final String TIPOMODELO 			=  "tipoModelo";
	public static final String TIPOOPERACION 		=  "tipoOperacion";
	public static final String FECEMI 				=  "fecEmi";
	public static final String HOREMI 				=  "horEmi";
	public static final String TIPOMONEDA 			=  "tipoMoneda";
	public static final String NIT 					=  "nit";
	public static final String NRC 					=  "nrc";
	public static final String NOMBRE 				=  "nombre";
	public static final String CODACTIVIDAD 		=  "codActividad";
	public static final String DESCACTIVIDAD 		=  "descActividad";
	public static final String NOMBRECOMERCIAL 		=  "nombreComercial";
	public static final String TIPOESTABLECIMIENTO =  "tipoEstablecimiento";
	public static final String TIPOPERSONA 			=  "tipoPersona";
	public static final String DEPARTAMENTO 		=  "departamento";
	public static final String MUNICIPIO 			=  "municipio";
	public static final String COMPLEMENTO 			=  "complemento";
	public static final String TELEFONO 			=  "telefono";
	public static final String CORREO 				=  "correo";
	public static final String TIPODOCUMENTO 		=  "tipoDocumento";
	public static final String NUMDOCUMENTO 		=  "numDocumento";
	public static final String NUMITEM 				= "numItem";
	public static final String TIPOITEM 			= "tipoItem";
	public static final String NUMERODOCUMENTO 		= "numeroDocumento";
	public static final String CANTIDAD 			= "cantidad";
	public static final String CODIGO 				= "codigo";
	public static final String CODIGOTRIBUTO 		= "codigoTributo";
	public static final String AMBIENTE 			= "ambiente";
	public static final String TIPOGENERACION 		=  "tipoGeneracion";
	public static final String COMPRA				= "compra";
	public static final String OBSERVACIONES 		= "observaciones";

	public static final String UNIMEDIDA 			= "uniMedida";
	public static final String DESCRIPCION 			= "descripcion";
	public static final String PRECIOUNI 			= "precioUni";
	public static final String MONTODESCU 			= "montoDescu";
	public static final String VENTANOSUJ 			= "ventaNoSuj";
	public static final String VENTAEXENTA 			= "ventaExenta";
	public static final String VENTAGRAVADA 		= "ventaGravada";
	public static final String IVAITEM 				= "ivaItem";
	public static final String PSV 					= "psv";
	public static final String NOGRAVADO 			= "noGravado";

	public static final String TOTALNOSUJ 			= "totalNoSuj";
	public static final String TOTALEXENTA 			= "totalExenta";
	public static final String TOTALGRAVADA 		= "totalGravada";
	public static final String SUBTOTALVENTAS 		= "subTotalVentas";
	public static final String DESCUNOSUJ 			= "descuNoSuj";
	public static final String DESCUEXENTA 			= "descuExenta";
	public static final String DESCUGRAVADA 		= "descuGravada";
	public static final String PORCENTAJEDESCUENTO 	= "porcentajeDescuento";
	public static final String SUBTOTAL 			= "subTotal";
	public static final String IVARETE1 			= "ivaRete1";
	public static final String MONTOTOTALOPERACION 	= "montoTotalOperacion";
	public static final String TOTALNOGRAVADO 		= "totalNoGravado";
	public static final String TOTALPAGAR 			= "totalPagar";
	public static final String TOTALCOMPRA 			= "totalCompra";
	public static final String TOTALLETRAS 			= "totalLetras";
	public static final String SALDOFAVOR 			= "saldoFavor";
	public static final String CONDICIONOPERACION 	= "condicionOperacion";
	public static final String TOTALDESCU 			= "totalDescu";
	public static final String DESCU 				= "descu";
	public static final String RETERENTA 			= "reteRenta";
	public static final String TOTALIVA 			= "totalIva";
	public static final String MONTOPAGO 			= "montoPago";
	public static final String REFERENCIA 			= "referencia";
	public static final String PLAZO 				= "plazo";
	public static final String PERIODO 				= "periodo";
	public static final String TRIBUTOS				= "tributos";
	public static final String PAGOS				= "pagos";
	public static final String DIRECCION			= "direccion";
	public static final String IVAPERCI1			= "ivaPerci1";
	public static final String FLETE				= "flete";
	public static final String SEGURO				= "seguro";

	public static final String VERSION				= "version";	
	
	public static final String CODESTABLE			  = "codEstable";
	public static final String MOTIVOANULACION		  = "motivoAnulacion";	
	public static final String TOTALIVARETENIDO 	  = "totalIVAretenido";	
	public static final String ERRORMESSAGES 		  = "errorMessages";
	public static final String MOTIVOCONTIN			  = "motivoContin";
	public static final String TIPOCONTINGENCIA		  = "tipoContingencia";
	public static final String VALOR				  = "valor";
	public static final String TIPOITEMEXPOR		  = "tipoItemExpor";
	public static final String CODPAIS				  = "codPais";
	public static final String NOMBREPAIS			  = "nombrePais";
	public static final String DESCUENTO			  = "descuento";
	public static final String CODTRIBUTO 			  = "codTributo";
	public static final String NUMPAGOELECTRONICO	  = "numPagoElectronico";
	public static final String FECANULA				  = "fecAnula";
	public static final String HORANULA				  = "horAnula";
	public static final String NOMESTABLECIMIENTO	  = "nomEstablecimiento";
	public static final String CODESTABLEMH			  = "codEstableMH";
	public static final String CODPUNTOVENTAMH		  = "codPuntoVentaMH";
	public static final String CODPUNTOVENTA		  = "codPuntoVenta";
	public static final String TIPODTE				  = "tipoDte";
	public static final String SELLORECIBIDO		  = "selloRecibido";
	public static final String MONTOIVA				  = "montoIva";
	public static final String CODIGOGENERACIONR	  = "codigoGeneracionR";
	public static final String TIPOANULACION 		  = "tipoAnulacion";
	public static final String NOMBRERESPONSABLE	  = "nombreResponsable";
	public static final String TIPDOCRESPONSABLE	  = "tipDocResponsable";
	public static final String NUMDOCRESPONSABLE	  = "numDocResponsable";
	public static final String NOMBRESOLICITA		  = "nombreSolicita";
	public static final String TIPDOCSOLICITA		  = "tipDocSolicita";
	public static final String NUMDOCSOLICITA		  = "numDocSolicita";
	public static final String CODIGOMH				  = "codigoMH";
	public static final String PUNTOVENTAMH			  = "puntoVentaMH";
	public static final String PUNTOVENTA			  = "puntoVenta";
	public static final String TOTALSUJETORETENCION	  = "totalSujetoRetencion";
	public static final String TOTALIVARETENIDOLETRAS = "totalIVAretenidoLetras";
	public static final String TIPODOC                = "tipoDoc";
	public static final String FECHAEMISION           = "fechaEmision";
	public static final String MONTOSUJETOGRAV        = "montoSujetoGrav";
	public static final String CODIGORETENCIONMH      = "codigoRetencionMH";
	public static final String IVARETENIDO 			  = "ivaRetenido";
	
	
	public static final int		TIPOMODELO_NOCONTIGENCIA			=1;
	public static final int		TIPOMODELO_CONTIGENCIA				=2;
	public static final int		TIPOOPERACION_NOCONTIGENCIA			=1;
	public static final int		TIPOOPERACION_CONTIGENCIA			=2;
	public static final int		CONDICIONOPERACION_AL_CONTADO		=1;
	public static final int		CONDICIONOPERACION_A_CREDITO		=2;
	public static final int		TIPOITEM_SERVICIO					=2;
	
}

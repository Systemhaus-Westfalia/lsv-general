/**
 * 
 */
package org.shw.lsv.einvoice.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrgInfo;
import org.compiere.util.Language;
import org.compiere.util.Util;
import org.json.JSONObject;

/**
 * 
 */
public abstract class EDocumentFactory {
	public abstract void generateJSONInputData();
	public abstract StringBuffer getEDocumentErrorMessages(); 
	public abstract EDocument generateEDocument();
	public abstract String createJsonString() throws Exception;

	protected MClient	client = null;
	protected MOrgInfo orgInfo = null;
	protected String trxName;
	protected Properties contextProperties;
	protected JSONObject jsonInputToFactory;  // Will contain data passed to factory
	protected Language languageUsed = null;
	
	
	public EDocumentFactory(String trxName, Properties contextProperties, MClient client, MOrgInfo orgInfo) {
		this.trxName = trxName;
		this.contextProperties = contextProperties;
		this.client = client;
		this.orgInfo = orgInfo;
		String lang = client.getAD_Language();	
		this.languageUsed = Language.getLanguage(lang);
			
			
	}
	
	public String getCodigoGeneracion (String eDocumentAsJsonString) {

        JSONObject eDocumentAsJson  = new JSONObject(eDocumentAsJsonString);
        JSONObject identification = (JSONObject)eDocumentAsJson.get(EDocument.IDENTIFICACION);
        String codigoGeneracion = identification.getString(EDocument.CODIGOGENERACION);
		return codigoGeneracion;
	}
	
	public String getNumeroControl (String eDocumentAsJsonString) {

        JSONObject eDocumentAsJson  = new JSONObject(eDocumentAsJsonString);
        JSONObject identification = (JSONObject)eDocumentAsJson.get(EDocument.IDENTIFICACION);
        String codigoGeneracion = identification.getString(EDocument.NUMEROCONTROL);
		return codigoGeneracion;
	}
	
	public boolean writeToFile (String json, MInvoice invoice, String directory) {
		System.out.println("Anulacion: start writing to file");
		try
		{
			Path rootpath = Paths.get(directory);
			if (!Files.exists(rootpath)) {
				return false;
			}    	

			directory = (directory.endsWith("/")
					|| directory.endsWith("\\"))
					? directory:directory + "/";
			Path path = Paths.get(directory + invoice.getDateAcct().toString().substring(0, 10) + "/");
			Files.createDirectories(path);
			//java.nio.file.Files;
			Files.createDirectories(path);
			String filename = path +"/" + invoice.getDocumentNo().replace(" ", "") + ".json"; 
			File out = new File (filename);
			Writer fw = new OutputStreamWriter(new FileOutputStream(out, false), "UTF-8");
			fw.write(json);
			fw.flush ();
			fw.close ();
			float size = out.length();
			size /= 1024;
			System.out.println("File size: " + out.getAbsolutePath() + " - " + size + " kB");
			System.out.println("Printed To: " + filename);
			System.out.println("Anulacion: end writing to file");
			return true;
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

}

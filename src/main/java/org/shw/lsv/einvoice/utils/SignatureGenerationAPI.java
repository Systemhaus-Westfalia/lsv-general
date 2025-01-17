package org.shw.lsv.einvoice.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.compiere.model.MClient;

/**
 * 
 * Copia modificada de sv.mh.fe.SHWElectronicSignature, de la Aplicación de SpringBoot svfe-api-firmador.
 * @author Mario Calderon, mario.calderon@westfalia-it.com, Systemhaus Westfalia.
 * 
 */
public class SignatureGenerationAPI {
	// Folgende Zeile ist zur Verwendung beim Testen auf dem Laptop.
	//static final String SPRINGBOOT_PACKAGE_PATH     	   = "/home/westfalia/downloads/Adempiere/Adempiere_400/Historie/20230820_Elektronische_Fakturierung/20240429_Programme-MMH/02-Repositories-geliefert_vom_MMHH/svfe-api-firmador/target/";
	// Folgende Zeile muss beim Testen auskommentiert werden. Sie enthált das Verzeichnis des SpringBoot-Packages im ZK-Container. 
	static final String SPRINGBOOT_PACKAGE_PATH     	   = "C:\\Users\\SHW_User\\ElectronicPublishing\\springboot_package";

	static final String SPRINGBOOT_PACKAGE_NAME     	   = "svfe-api-firmador-0.1.1.jar";
	static final String SPRINGBOOT_PACKAGE_MAIN_CLASS     	= "sv.mh.fe.SHWElectronicSignature";

	// Folgende Zeile ist zur Verwendung beim Testen auf dem Laptop.
	//static final String SPRINGBOOT_APPLICATION_CONTEXT_PATH = "/home/westfalia/downloads/Adempiere/Adempiere_400/Historie/20240809-Elektronische-Fakturierung/Z_Temp/";
	// Folgende Zeile muss beim Testen auskommentiert werden. Sie enthált das Verzeichnis des SpringBoot-Packages im ZK-Container.
	//static final String SPRINGBOOT_APPLICATION_CONTEXT_PATH = "/home/adempiere/persistent_files/SHW_electronic_invoicing/";
	static final String SPRINGBOOT_APPLICATION_CONTEXT_PATH = "C:\\Users\\SHW_User\\ElectronicPublishing\\";
	static final String SPRINGBOOT_APPLICATION_LOGFILE_NAME = "SHW_SpringBoot_Process.log";
	static final String SPRINGBOOT_APPLICATION_ARGUMENT    	= "-Dlogging.file=";
	
	static final String SPRINGBOOT_SIGNATURE_RELATIVE_PATH  = "signatures\\";
	static final String SIGNATURE_SUFFIX    	            = "_signature.txt";

	private String passwordPub;
	private String passwordPri;
	private String nit;
	private String nombreDocumento;
	private String nombreFirma;
	private String compactSerialization;
	private String dteJson;
	private String dte;
	private boolean activo = true;
	private String documentno;

	private String springBootApplicationContext;
	private String springBootPackagePath;
	private String springBootPackageFullPath;

	private String springBootPackageName;
	private String springBootPackageMainClass;
	private String springBootLogFileFullPath;

	private String springBootLogFileName;

	private String springBootLogFileArgument;
	
	private String signatureFilePrefix;
	private String signatureFileSuffix;
	private String signatureFilePath;
	private String signatureFileName;
	private String signatureFullFilePath;

	public SignatureGenerationAPI(){
		setActivo(true);
	}
	
	public SignatureGenerationAPI(MClient client, String documentno, String eInvoiceAsJsonString){
		
		//setNit("06140904181038");  // Nur zu Testzwecken
		setNit(client.get_ValueAsString("ei_nit"));
		
		// setPasswordPri("Port465fer");  // Nur zu Testzwecken
		setPasswordPri(client.get_ValueAsString("ei_privatePassword"));
		
		//setPasswordPub("DTEcofia");  // Nur zu Testzwecken
		setPasswordPub(client.get_ValueAsString("ei_publicPassword"));
		
		//setNombreFirma("COFIA, S.A. de C.V");  // Nur zu Testzwecken
		setNombreFirma(client.get_ValueAsString("ei_nameCompany"));
		
		setDteJson(eInvoiceAsJsonString);
		setActivo(true);
		setDocumentno(documentno.replace(" ", ""));  // Trimmed Invoice

		// Ab hier fest verdrahtet
		setSpringBootPackagePath(SPRINGBOOT_PACKAGE_PATH);
		setSpringBootPackageName(SPRINGBOOT_PACKAGE_NAME);
		setSpringBootPackageFullPath(getSpringBootPackagePath() + getSpringBootPackagename());  // Das wird aufgerufen
		setSpringBootPackageMainClass(SPRINGBOOT_PACKAGE_MAIN_CLASS);  // Das wird aufgerufen
		
		setSpringBootApplicationContext(SPRINGBOOT_APPLICATION_CONTEXT_PATH);
		setSpringBootLogFileName(SPRINGBOOT_APPLICATION_LOGFILE_NAME);
		setSpringBootLogFileFullPath(getSpringBootApplicationContext() + getSpringBootLogFileName());
		setSpringBootLogFileArgument(SPRINGBOOT_APPLICATION_ARGUMENT + getSpringBootLogFileFullPath());  // Das wird aufgerufen
		
		setSignatureFilePath(getSpringBootApplicationContext() + getNit() + "\\" + SPRINGBOOT_SIGNATURE_RELATIVE_PATH);
		String dateAsString = getDateAsString().replace(":", "_");  // Manche OSs erlauben keine ":" im Dateinamen
		setSignatureFilePrefix(getNit() + "_" + dateAsString + "_" + getDocumentno());
		setSignatureFileSuffix(SIGNATURE_SUFFIX);
		setSignatureFileName(getSignatureFilePrefix() + getSignatureFileSuffix());
		setSignatureFullFilePath(getSignatureFilePath() + getSignatureFileName());  // In dieser Datei wird die elektronische Unterschrift gespeichert.
	}
	
	public SignatureGenerationAPI(String passwordPub, String passwordPri, String nit, String nombreDocumento,
			String nombreFirma, String compactSerialization, String dteJson, String dte, boolean activo,
			String applicationContext, String signaturePrefix) {
		setPasswordPub(passwordPub);
		setPasswordPri(passwordPri);
		setNit(nit);
		setNombreDocumento(nombreDocumento);
		setNombreFirma(nombreFirma);
		setCompactSerialization(compactSerialization);
		setDteJson(dteJson);
		setDte(dte);
		setActivo(activo);
		setSpringBootApplicationContext(applicationContext);  // To read certificate: applicationContext/nit/nit.crt
		setSignatureFilePrefix(signaturePrefix); // Prefix for signature file "NIT_YYYYMMDD-MMSS_DOCUMENTNO"
												 // In SpringBoot application, it will be added "_signature.txt"
	}
	
	public String getDateAsString() {
		String dateAsString;
		Date dt = new Date();
		
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	    dateAsString = dateFormat.format(dt);
		return dateAsString;
	}
	
	public String getPasswordPub() {
		return passwordPub;
	}
	public void setPasswordPub(String passwordPub) {
		this.passwordPub = passwordPub;
	}
	public String getPasswordPri() {
		return passwordPri;
	}
	public void setPasswordPri(String passwordPri) {
		this.passwordPri = passwordPri;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public boolean getActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	public String getNombreFirma() {
		return nombreFirma;
	}
	public void setNombreFirma(String nombreFirma) {
		this.nombreFirma = nombreFirma;
	}
	public String getCompactSerialization() {
		return compactSerialization;
	}
	public void setCompactSerialization(String compactSerialization) {
		this.compactSerialization = compactSerialization;
	}
	
	public String getDteJson() {
		return dteJson;
	}
	public void setDteJson(String dteJson) {
		this.dteJson = dteJson;
	}

	public String getDte() {
		return dte;
	}
	public void setDte(String dte) {
		this.dte = dte;
	}

	public String getSpringBootApplicationContext() {
		return springBootApplicationContext;
	}

	public void setSpringBootApplicationContext(String springBootApplicationContext) {
		this.springBootApplicationContext = springBootApplicationContext;
	}
	
	public String getSpringBootPackagePath() {
		return springBootPackagePath;
	}

	public void setSpringBootPackagePath(String springBootPackagePath) {
		this.springBootPackagePath = springBootPackagePath;
	}

	public String getSpringBootPackagename() {
		return springBootPackageName;
	}

	public void setSpringBootPackageName(String springBootPackage) {
		this.springBootPackageName = springBootPackage;
	}
	public String getSpringBootPackageFullPath() {
		return springBootPackageFullPath;
	}

	public void setSpringBootPackageFullPath(String springBootPackageFullPath) {
		this.springBootPackageFullPath = springBootPackageFullPath;
	}
	
	public String getSpringBootPackageMainClass() {
		return springBootPackageMainClass;
	}

	public void setSpringBootPackageMainClass(String springBootPackageMainClass) {
		this.springBootPackageMainClass = springBootPackageMainClass;
	}
	
	public String getSpringBootLogFileFullPath() {
		return springBootLogFileFullPath;
	}

	public void setSpringBootLogFileFullPath(String springBootLogFileFullPath) {
		this.springBootLogFileFullPath = springBootLogFileFullPath;
	}

	public String getSignatureFileName() {
		return signatureFileName;
	}

	public void setSignatureFileName(String signatureFileName) {
		this.signatureFileName = signatureFileName;
	}

	public String getSpringBootLogFileName() {
		return springBootLogFileName;
	}

	public void setSpringBootLogFileName(String springBootLogFileName) {
		this.springBootLogFileName = springBootLogFileName;
	}

	public String getSpringBootLogFileArgument() {
		return springBootLogFileArgument;
	}

	public void setSpringBootLogFileArgument(String springBootLogFileArgument) {
		this.springBootLogFileArgument = springBootLogFileArgument;
	}

	public String getSignatureFilePrefix() {
		return signatureFilePrefix;
	}

	public void setSignatureFilePrefix(String signatureFilePrefix) {
		this.signatureFilePrefix = signatureFilePrefix;
	}
	
	public String getSignatureFileSuffix() {
		return signatureFileSuffix;
	}

	public void setSignatureFileSuffix(String signatureFileSuffix) {
		this.signatureFileSuffix = signatureFileSuffix;
	}

	public String getSignatureFilePath() {
		return signatureFilePath;
	}

	public void setSignatureFilePath(String signatureFilePath) {
		this.signatureFilePath = signatureFilePath;
	}

	public String getSignatureFullFilePath() {
		return signatureFullFilePath;
	}

	public void setSignatureFullFilePath(String signatureFullFilePath) {
		this.signatureFullFilePath = signatureFullFilePath;
	}

	public String getDocumentno() {
		return documentno;
	}

	public void setDocumentno(String documentno) {
		this.documentno = documentno;
	}


}

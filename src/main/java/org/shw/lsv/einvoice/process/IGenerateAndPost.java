package org.shw.lsv.einvoice.process;

public interface  IGenerateAndPost {
	
	public final static String whereClauseInvoices = "AD_CLIENT_ID = ?  "
			+ " AND Exists (select 1 from c_Doctype dt where dt.c_Doctype_ID=c_Invoice.c_Doctype_ID AND E_DocType_ID is not null) "
			+ " AND processed = 'Y' AND dateacct>=?  AND processing = 'N' "
			+ " AND ei_Processing = 'N' "
			+ " AND (docstatus IN ('CO','CL') OR coalesce(reversal_ID,0) > c_Invoice_ID)"
			+ " AND (ei_Status_Extern is NULL OR ei_Status_Extern <> 'Firmado')";
	
	public final static String whereClauseInvoicesVoided = "AD_CLIENT_ID = ?  "
			+ " AND Exists (select 1 from c_Doctype dt where dt.c_Doctype_ID=c_Invoice.c_Doctype_ID AND E_DocType_ID is not null) "
			+ " AND processed = 'Y' AND dateacct>=?  AND processing = 'N' "
			+ " AND ei_Processing = 'N' "
			+ " AND (docstatus IN ('RE','VO') AND reversal_ID is not null and reversal_ID<c_Invoice_ID)"
			+ " AND (select ei_Status_Extern from c_Invoice i where c_Invoice.reversal_ID=i.c_Invoice_ID) = 'Firmado' "
			+ " AND (ei_Status_Extern is NULL OR ei_Status_Extern not in ('Anulado', 'Firmado'))";
	
	public final static String APPLICATIONTYPEINVOICE 	= "LSV";	
	
	
	
	public static String getWhereclause(Boolean isVoided) {
		String result = isVoided? whereClauseInvoicesVoided:whereClauseInvoices;
		return result;
	}
	

	public static String getApplicationType() {
		String result = APPLICATIONTYPEINVOICE;
		return result;
	}



}

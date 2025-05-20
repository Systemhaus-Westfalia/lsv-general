		/**
 * 
 */
package org.shw.lsv.ebanking.bac.sv.misc;

/**
 * 
 */
public class EBankingConstants {
	public StringBuffer errorMessages = new StringBuffer();
	
	public static final String ITAV 		= "ITAV";  // InterimAvailable
	public static final String ITBD 		= "ITBD";  // InterimBooked
	public static final String OPAV 		= "OPAV";  // OpeningAvailable
	public static final String OPBD 		= "OPBD";  // OpeningBooked

	public static final String CRDT 		= "CRDT";  // Credit (T (zero balance is considered to be a credit balance))
	public static final String DBIT 		= "DBIT";  // Debit

	
	public static final int		IRGEND_EIN_INTEGER			=1;

	public static final String ERROR_NULL_NOT_ALLOWED          = "Parameter with value 'null' is not allowed";
	public static final String ERROR_EMPTY_OR_NULL_NOT_ALLOWED = "Parameter with empty string or with value 'null' is not allowed";
	public static final String ERROR_PATTERN_MISMATCH          = "Parameter value does not match pattern";
	public static final String ERROR_WRONG_LENGTH              = "Parameter length not in required boundaries";
	public static final String ERROR_INVALID_CD_VALUE          = "The Cd value can only be one of the following: ITAV, ITBD, OPAV, OPBD";
	public static final String ERROR_INVALID_DTTM              = "The Dttm value is invalid";
	public static final String ERROR_INVALID_AMT_FORMAT        = "The Amt format is invalid";
	public static final String ERROR_INVALID_CCY_FORMAT        = "The Ccy format is invalid";

	public static final String ERROR_CAMT052REQUEST_BUILDING   = "Error while building the CAMT052 Request";
	public static final String ERROR_CAMT052REQUEST_PARAM      = "One or more parameters for the CAMT052 Request are invalid";
	public static final String ERROR_CAMT052REQUEST_INIT       = "CAMT052Request initialization";
	public static final String ERROR_REQUEST_ENVELOP_INIT      = "Request Envelope initialization";
	public static final String ERROR_APPHDR_INIT               = "AppHdr initialization";
	public static final String ERROR_FIID_INIT                 = "FIId initialization";
	public static final String ERROR_FININSTN_INIT             = "FinInstnId initialization";
	public static final String ERROR_FR_INIT                   = "Fr initialization";
	public static final String ERROR_IDOTHR_INIT               = "IdOthr initialization";
	public static final String ERROR_IDORGID_INIT              = "IDOrgID initialization";
	public static final String ERROR_ORGID_INIT                = "OrgId initialization";
	public static final String ERROR_ACCTRPTGREQ_INIT          = "AcctRptgReq initialization";
	public static final String ERROR_ACCT_OWNER_INIT           = "AcctOwner initialization";
	public static final String ERROR_RPTGREQ_INIT              = "RptgReq initialization";
	public static final String ERROR_ACCT_INIT                 = "Acct initialization";
	public static final String ERROR_ACCTID_INIT               = "AcctId initialization";
	public static final String ERROR_ACCTIDOTHR_INIT           = "AcctIdIthr initialization";
	public static final String ERROR_PTY_INIT                  = "Pty initialization";
	public static final String ERROR_PTYID_INIT                = "PtyId initialization";
	public static final String ERROR_AGT_INIT                  = "Agt initialization";
	public static final String ERROR_FRTODT_INIT               = "FrToDt initialization";
	public static final String ERROR_RPTGPRD_INIT              = "RptgPrd initialization";


	public static final String PATTERN_BIZSVC      = "[a-z0-9]{1,10}.([a-z0-9]{1,10}.)+dd";
	public static final String PATTERN_CREDT       = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9]$";
	public static final String PATTERN_BICFI       = "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}";
	public static final String PATTERN_TODT        = "^\\d{4}-\\d{2}-\\d{2}$";
	public static final String PATTERN_FRDT        = "^\\d{4}-\\d{2}-\\d{2}$";
	public static final String PATTERN_ANYBIC      = "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}";
	public static final String PATTERN_OTHER_ID    = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
	public static final String PATTERN_PRTRY       = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
	public static final String PATTERN_CREDTM      = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9]$";
	public static final String PATTERN_MSG_ID      = "[0-9a-zA-Z/\\-\\?:\\(\\)\\.,'\\+ ]+";
	public static final String PATTERN_REQDMSGNMID = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
	public static final String PATTERN_IBAN        = "[A-Z]{2,2}[0-9]{2,2}[a-zA-Z0-9]{1,30}";
	public static final String PATTERN_ACCT_ID     = "([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]*(/[0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ])?)*)";
	public static final String PATTERN_TP          = "ALLL";
	public static final String PATTERN_EQSEQ       = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
	public static final String PATTERN_RPTID       = "([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]*(/[0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ])?)*)";
	public static final String PATTERN_DTTM        = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9])$";
	public static final String PATTERN_AMT         = "^\\d+\\.\\d{2}$";
	public static final String PATTERN_CCY         = "^[A-Z]{3}$";


	public static final String CONTEXT_FR   = "FR";
	public static final String CONTEXT_TO   = "TO";
	public static final String CONTEXT_AGT  = "AGT";

	
}

package org.shw.lsv.ebanking.bac.sv.misc;

import java.time.format.DateTimeFormatter;

/**
 * 
 */
public class EBankingConstants {
    public static final DateTimeFormatter DATETIME_FORMATTER             = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter ISO_OFFSET_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    public static final long TIMEZONE_SHIFT_EL_SALVADOR                  = 6; // 6 hours shift for the time zone (e.g., CET to UTC)
    public static final long TIMEZONE_SHIFT_PANAMA                       = 5; // 4 hours shift for the time zone (e.g., CET to UTC)

	public static final StringBuffer errorMessages = new StringBuffer();

	public static final String ITAV 		= "ITAV";  // InterimAvailable
	public static final String ITBD 		= "ITBD";  // InterimBooked
	public static final String OPAV 		= "OPAV";  // OpeningAvailable
	public static final String OPBD 		= "OPBD";  // OpeningBooked


	public static final String TRF  		= "TRF";  // Transaccion
	public static final String CHK  		= "CHK";  // Cheque

	public static final String CRDT 		= "CRDT";  // Credit (T (zero balance is considered to be a credit balance))
	public static final String DBIT 		= "DBIT";  // Debit

	public static final String SUPP  		= "SUPP";  // Supplier
	public static final String SALA  		= "SALA";  // Salario
	public static final String TREA  		= "TREA";  // Trasury

	
	public static final int		IRGEND_EIN_INTEGER			=1;

	public static final String ERROR_NULL_NOT_ALLOWED          = "Parameter with value 'null' is not allowed";
	public static final String ERROR_EMPTY_OR_NULL_NOT_ALLOWED = "Parameter with empty string or with value 'null' is not allowed";
	public static final String ERROR_PATTERN_MISMATCH          = "Parameter value does not match pattern";
	public static final String ERROR_WRONG_LENGTH              = "Parameter length not in required boundaries";
	public static final String ERROR_INVALID_CD_VALUE          = "The Cd value can only be one of the following: ITAV, ITBD, OPAV, OPBD";
	public static final String ERROR_INVALID_DTTM              = "The Dttm value is invalid";
	public static final String ERROR_INVALID_AMT_FORMAT        = "The Amt format is invalid";
	public static final String ERROR_INVALID_CCY_FORMAT        = "The Ccy format is invalid";
	public static final String ERROR_INVALID_BIC_FORMAT        = "The BIC format is invalid";
	public static final String ERROR_INVALID_MMBID_FORMAT      = "The MmbId format is invalid";

	public static final String ERROR_ENVELOPE_NOT_NULL         = "Envelope cannot be null";
	public static final String ERROR_REQUEST_BUILDING          = "Error while building the Request";
	public static final String ERROR_REQUEST_PARAM             = "One or more parameters for the Request are invalid";
	public static final String ERROR_CAMT052REQUEST_INIT       = "CAMT052Request initialization";
	public static final String ERROR_DOCUMENT_INIT             = "Document initialization";
	public static final String ERROR_REQUEST_ENVELOP_INIT      = "Request Envelope initialization";
	public static final String ERROR_APPHDR_INIT               = "AppHdr initialization";
	public static final String ERROR_FIID_INIT                 = "FIId initialization";
	public static final String ERROR_FININSTN_INIT             = "FinInstnId initialization";
	public static final String ERROR_FR_INIT                   = "Fr initialization";
	public static final String ERROR_IDOTHR_INIT               = "IdOthr initialization";
	public static final String ERROR_IDORGID_INIT              = "IDOrgID initialization";
	public static final String ERROR_TOID_INIT                 = "ToId initialization";
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
	public static final String ERROR_PMTINF_INIT               = "PmtInf initialization";
	public static final String ERROR_PMTTPINF_INIT             = "PmtTpInf initialization";
	public static final String ERROR_DBTRAGT_INIT              = "DbtrAgt initialization";
	public static final String ERROR_CDTRAGT_INIT              = "CdtrAgt initialization";
	public static final String ERROR_CDTRAGT_FININSTNID_INIT   = "CdtrAgtFininstnId initialization";
	public static final String ERROR_CLRSYSMMBID_INIT          = "CltSysMmbId initialization";
    public static final String ERROR_CDTRACCT_INIT             = "CdtrAcct initialization";
    public static final String ERROR_PURP_INIT                 = "Purp initialization";
    public static final String ERROR_RMTINF_INIT               = "Rmtinf initialization";
    public static final String ERROR_STRD_INIT                 = "Strd initialization";
    public static final String ERROR_RFRDDOCINF_INIT           = "RfrdDocInf initialization";
    public static final String ERROR_SYSEVTNTFCTN_INIT         = "SysEvtNtfctn initialization";
    public static final String ERROR_EVTINF_INIT               = "EvtInf initialization";
    public static final String ERROR_STS_INIT                  = "Sts initialization";
    public static final String ERROR_STSRPTRSP_INIT            = "StsRptRsp initialization";
	public static final String ERROR_CAMT053REQUEST_INIT       = "CAMT053Request initialization";


	public static final String ERROR_PAIN001REQUEST_INIT       = "PAIN001Request initialization";
	public static final String ERROR_CSTMRCDTTRFINITN_INIT     = "CstmrCdtTrfInitn initialization";
	public static final String ERROR_CTGYPURP_INIT             = "CtgyPurp initialization";
	public static final String ERROR_DBTR_INIT                 = "Dbtr initialization";
	public static final String ERROR_CDTR_INIT                 = "Cdtr initialization";
	public static final String ERROR_CDTRID_INIT               = "CdtrId initialization";
	public static final String ERROR_CDTRORGID_INIT            = "CdtroRGID initialization";
	public static final String ERROR_DBTRACCT_INIT             = "DbtrAcct initialization";
	public static final String ERROR_CDTRACCTID_INIT           = "CdtrAcctId initialization";
	public static final String ERROR_FININSTDBTR_INIT          = "FinInstnIdDbtr initialization";
	public static final String ERROR_PAYMENT_ELEMENT_INIT      = "PaymentElement initialization";
	public static final String ERROR_INSTDAMT_INIT             = "InstdAmt initialization";
	public static final String ERROR_INITGPTY_INIT             = "InitgPty initialization";
	public static final String ERROR_GRPHDRID_INIT             = "GrpHdrId initialization";
	public static final String ERROR_INVALID_BIC_OR_BEI_FORMAT = "Invalid BIC or BEI format";
	public static final String ERROR_TMST0039RESPONSE_INIT     = "TMST0039Response initialization";
	public static final String ERROR_STMT_OF_ACCT_ELEMENT_INIT = "StmtOfAccountElement initialization";
	public static final String ERROR_BOOKGORVALDT_INIT         = "BookgOrValDt initialization";
	public static final String ERROR_DOMN_INIT                 = "Domn initialization";
	public static final String ERROR_BKTXCD_INIT               = "BkTxCd initialization";
	public static final String ERROR_FMLY_INIT                 = "Fmly initialization";
	public static final String ERROR_STMT_INIT                 = "Stmt initialization";
    public static final String ERROR_NTRYDTLS_INIT             = "NtryDtls initialization";
    public static final String ERROR_TXDTLS_INIT               = "TxDtls initialization";


	public static final String PATTERN_BIZSVC       = "[a-z0-9]{1,10}.([a-z0-9]{1,10}.)+\\d{2}";
	public static final String PATTERN_BIC          = "[A-Z0-9]{4,4}[A-Z]{2,2}[A-Z0-9]{2,2}([A-Z0-9]{3,3}){0,1}";
	//public static final String PATTERN_OTHER_ID     = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
	public static final String PATTERN_OTHER_ID     = "[0-9a-zA-Z/\\-?:().,'+ ]{1,35}";
	public static final String PATTERN_PRTRY        = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
	public static final String PATTERN_MSG_ID       = "[0-9a-zA-Z/\\-\\?:\\(\\)\\.,'\\+ ]+";
	//public static final String PATTERN_REQDMSGNMID  = "[0-9a-zA-Z/\\\\-\\?:\\(\\)\\.,'\\+ ]+";
	public static final String PATTERN_REQDMSGNMID  = "[0-9a-zA-Z/\\-?:().,'+ ]+";
	public static final String PATTERN_ACCT_ID      = "([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]*(/[0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ])?)*)";
	public static final String PATTERN_TP           = "ALLL";
	public static final String PATTERN_EQSEQ        = "[0-9a-zA-Z/\\\\?:\\(\\)\\.,'\\+ \\-]+";
	public static final String PATTERN_RPTID        = "([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]([0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ]*(/[0-9a-zA-Z\\-\\?:\\(\\)\\.,'\\+ ])?)*)";
	public static final String PATTERN_DATETIME_TZ  = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]([0][0-9]|[1][0-1]):[0-5][0-9]$";
	public static final String PATTERN_DATETIME     = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$";
	public static final String PATTERN_DATE         = "^\\d{4}-\\d{2}-\\d{2}$";
	public static final String PATTERN_ENDTOENDID   = "[0-9a-zA-Z/\\-?:().,'+ ]{1,35}";
	public static final String PATTERN_BICORBEI     = "[A-Z0-9]{8,11}";
	public static final String PATTERN_MMBID        = "[A-Z0-9]{1,35}";

	public static final String PATTERN_CURRENCY_AMT = "\\d+(\\.\\d{1,2})?";
	// Laut copilot sind folgende Eigenschaften dieses Patterns:
	// Matches: Integers (123), one decimal (123.4), two decimals (123.45)
	//  - Does NOT match: More than two decimals (123.456)
	//  - Flexible: Allows whole numbers and up to two decimals.
	//
	// Folgendes Pattern habe ich nicht verwendet:
	// "^\\d+\\.\\d{2}$"
	//  - Matches: Only numbers with exactly two decimal places (e.g., 123.45, 0.99)
	//  - Does NOT match: Integers (123), one decimal (123.4), more than two decimals (123.456)
	// Strict: Enforces exactly two decimals, which is standard for most currencies.

	public static final String PATTERN_CURRENCY     = "^[A-Z]{3}$";
	public static final String PATTERN_INTEGER      = "^[0-9]+$";
	public static final String PATTERN_IBAN         = "[A-Z]{2}[0-9]{2}[a-zA-Z0-9]{1,30}";
	public static final String PATTERN_COUNTRY      = "[A-Z]{2}";
	public static final String PATTERN_INSTR_PRTY   = "(HIGH|NORM)";
	public static final String PATTERN_EVENTCODE    = "[A-Z0-9]{1,35}";
	public static final String PATTERN_XMLNS        = "^urn:iso:std:iso:20022:tech:xsd:[a-z0-9\\.]+$";
	public static final String PATTERN_NTRYREF      = "[0-9A-Za-z/\\-\\?:().,'\\+ ]{1,35}";
	public static final String PATTERN_CDTDBTIND    = "CRDT|DBIT";
	public static final String PATTERN_ADDTLNTRYINF = "[0-9A-Za-z/\\-\\?:().,'\\+ ]{1,140}";
	public static final String PATTERN_CD_STATUS    = "[A-Z]{4}";
	public static final String PATTERN_ELCTRNCSEQNB = "[0-9]{1,5}";
	public static final String PATTERN_PGNB         = "^[1-9][0-9]{0,4}$"; // 1-5 digits, no leading zeros
	public static final String PATTERN_PGNB_ZERO    = "^[0-9]{1,5}$"; // 1-5 digits, leading zeros allowed


	public static final String CONTEXT_FR           = "FR";
	public static final String CONTEXT_TO           = "TO";

	public static final String CONTEXT_AGT          = "AGT";
	public static final String CONTEXT_PTYORGID     = "PTYORGID";
	public static final String CONTEXT_RPTGREQ      = "RPTGREQ";
	public static final String CONTEXT_STMT         = "STMT";

	public static final String CONTEXT_DBTR          = "DBTR";
	public static final String CONTEXT_DBTRACCT      = "DBTRACCT";
	public static final String CONTEXT_CDTR          = "CDTR";
	public static final String CONTEXT_CDTRACCT      = "CDTRACCT";
	public static final String CONTEXT_PMTTPINF      = "PMTTPINF";

	public static final String CONTEXT_BOOKING_DATE  = "BOOKING DATE";
	public static final String CONTEXT_VALID_DATE    = "VALID DATE";
	
}

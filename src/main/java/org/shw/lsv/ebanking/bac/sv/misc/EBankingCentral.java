		/**
 * 
 */
package org.shw.lsv.ebanking.bac.sv.misc;

/**
 * 
 */
public class EBankingCentral {
	public StringBuffer errorMessages = new StringBuffer();
	
	public static final String ITAV 		= "ITAV";  // InterimAvailable
	public static final String ITBD 		= "ITBD";  // InterimBooked
	public static final String OPAV 		= "OPAV";  // OpeningAvailable
	public static final String OPBD 		= "OPBD";  // OpeningBooked

	public static final String CRDT 		= "CRDT";  // Credit (T (zero balance is considered to be a credit balance))
	public static final String DBIT 		= "DBIT";  // Debit

	
	public static final int		IRGEND_EIN_INTEGER			=1;
	
}

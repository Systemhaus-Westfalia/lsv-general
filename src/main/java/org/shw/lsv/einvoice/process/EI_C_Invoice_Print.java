/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.                                     *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net                                                  *
 * or https://github.com/adempiere/adempiere/blob/develop/license.html        *
 *****************************************************************************/

package org.shw.lsv.einvoice.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.adempiere.core.domains.models.I_AD_PrintFormat;
import org.adempiere.core.domains.models.X_E_InvoiceElectronic;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MMailText;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;
import org.eevolution.services.dsl.ProcessBuilder;
import org.json.JSONObject;
import org.spin.queue.notification.DefaultNotifier;
import org.spin.queue.util.QueueLoader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/** Generated Process for (EI_C_Invoice_Print)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.4
 */
public class EI_C_Invoice_Print extends EI_C_Invoice_PrintAbstract
{
	@Override
	protected void prepare()
	{
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
        log.info("R_MailText_ID=" + getMailTextId());
        //	Mail Test
        MMailText mailText = new MMailText(getCtx(), getMailTextId(), get_TrxName());
        if (mailText.getR_MailText_ID() == 0)
            throw new Exception("@R_MailText_ID@=" + getMailTextId() + " @NotFound@ ");
        //	Client Info
        MClient client = MClient.get(getCtx());
        if (client.getAD_Client_ID() == 0)
            throw new Exception(" @AD_Client_ID@  @NotFound@ ");
        if (client.getSMTPHost() == null || client.getSMTPHost().length() == 0)
            throw new Exception("@SMTPHost@  @NotFound@ ");
        //
        MInvoice invoice = null;
        if (getRecord_ID()>0)
        	invoice = new MInvoice(getCtx(), getRecord_ID(), get_TrxName());
        else
        	invoice = new MInvoice(getCtx(), getInvoiceId(), get_TrxName());
        sendEMail(mailText,invoice);
        return "@Created@=" ;
    }
	
	
	private Boolean sendIndividualMail(MMailText mailText, MInvoice invoice) {
        try {
            MBPartner customer = (MBPartner) invoice.getC_BPartner();
           
            

            String message = mailText.getMailText(true);

            StringBuffer whereClause = new StringBuffer();
            whereClause.append(MBPartnerLocation.COLUMNNAME_C_BPartner_ID)
                    .append(" = ? AND ")
                    .append(MBPartnerLocation.COLUMNNAME_ContactType)
                    .append("=?");

            MBPartnerLocation location = new Query(getCtx(), MBPartnerLocation.Table_Name, whereClause.toString(), get_TrxName())
                    .setOnlyActiveRecords(true)
                    .setParameters(customer.getC_BPartner_ID(), MBPartnerLocation.CONTACTTYPE_Emergency)
                    .first();
			//	Get instance for notifier
			DefaultNotifier notifier = (DefaultNotifier) QueueLoader.getInstance().getQueueManager(DefaultNotifier.QUEUETYPE_DefaultNotifier)
					.withContext(Env.getCtx())
					.withTransactionName(get_TrxName());
			//	Send notification to queue
			notifier
				.clearMessage()
				.withApplicationType(DefaultNotifier.DefaultNotificationType_EMail)
				.withText(message)
				.withUserId(Env.getAD_User_ID(getCtx()))
				.withDescription(mailText.getMailHeader())
				.withEntity(MInvoice.Table_ID, invoice.getC_Invoice_ID());
            //	EMail
            String eMail = customer.get_ValueAsString("email");
            //	Get from Bank Account
            
            //	Attachment
            notifier.addAttachment(getPDF(invoice));
            //	Add EMail
            notifier.addRecipient(eMail);
            String filename = invoice.get_ValueAsString("ei_codigoGeneracion");
			File jsonfile = File.createTempFile(filename, ".json");
			
			String jsonwhereClause = "C_Invoice_ID=? AND json is not null AND ei_Validationstatus = '01'";
			
			X_E_InvoiceElectronic invoiceElectronic = new Query(getCtx(), X_E_InvoiceElectronic.Table_Name, jsonwhereClause, get_TrxName())
                    .setOnlyActiveRecords(true)
                    .setParameters(invoice.getC_Invoice_ID())
                    .setOrderBy(" created desc")
                    .first();

			if (invoiceElectronic == null)
				return false;
			JSONObject jsonorg = new JSONObject(invoiceElectronic.getjson());

			ObjectMapper mapper = new ObjectMapper();

	        // enable the pretty print globally
	        mapper.enable(SerializationFeature.INDENT_OUTPUT);Object jsonObject = mapper.readValue(invoiceElectronic.getjson(), Object.class);
	        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
	        
	        Writer fw = new OutputStreamWriter(new FileOutputStream(jsonfile, false), "UTF-8");
			fw.write(prettyJson);
			fw.flush ();
			fw.close ();

            notifier.addAttachment(jsonfile);
			notifier.addToQueue();
			invoice.setIsPrinted(true);
			invoice.saveEx();
            return true;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }    //	sendIndividualMail
 

    /**
     * Get PDF document
     * @param recordId
     * @return
     */
    private File getPDF(MInvoice invoice) {
    	
		//ReportCtl.startDocumentPrint(ReportEngine.INVOICE, invoice.getC_Invoice_ID(),false);
    	MDocType docType = (MDocType)invoice.getC_DocType();
    	int jasperProcessID = docType.get_ValueAsInt(I_AD_PrintFormat.COLUMNNAME_JasperProcess_ID);
        ProcessInfo processInfo = ProcessBuilder.create(getCtx()).process(jasperProcessID)
                .withTitle(getProcessName())
                .withRecordId(MInvoice.Table_ID, invoice.getC_Invoice_ID())
               // .withPrintPreview()
                .execute();

        return processInfo.getPDFReport();
    }
    
    private void sendEMail(MMailText mailText, MInvoice invoice) {

    	Boolean ok = sendIndividualMail(mailText, invoice);

    }    //	sendEMail

    
}
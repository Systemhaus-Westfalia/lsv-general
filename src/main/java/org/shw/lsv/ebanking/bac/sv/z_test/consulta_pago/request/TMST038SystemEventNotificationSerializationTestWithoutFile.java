package org.shw.lsv.ebanking.bac.sv.z_test.consulta_pago.request;

import java.time.LocalDateTime;

import org.shw.lsv.ebanking.bac.sv.handling.JsonProcessor;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationException;
import org.shw.lsv.ebanking.bac.sv.handling.JsonValidationExceptionCollector;
import org.shw.lsv.ebanking.bac.sv.handling.RequestBuilder;
import org.shw.lsv.ebanking.bac.sv.handling.RequestParams;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;
import org.shw.lsv.ebanking.bac.sv.tmst038.request.TMST038RequestEvtNtfn;
import org.shw.lsv.ebanking.bac.sv.z_test.util.TestDateUtils;
import org.shw.lsv.ebanking.bac.sv.z_test.util.TestRequestParamsFactory;

public class TMST038SystemEventNotificationSerializationTestWithoutFile {
    public static void main(String[] args) {
        String jsonOutput = "";

        LocalDateTime now = LocalDateTime.now();
        System.err.println("TMST038 System Event Notification serialization started at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        // 1. Create collector for test diagnostics
        JsonValidationExceptionCollector collector = new JsonValidationExceptionCollector();
        collector.setPrintImmediately(true); // See errors as they happen

        // 2. Build test parameters
        RequestParams params = TestRequestParamsFactory.createTmst038SysEvtNtfnParams();

        try {
            // 3. Build request with test's collector
            // A System Event Notification is represented by PAIN001ResponseEvtNtfn
            TMST038RequestEvtNtfn request = RequestBuilder.build(TMST038RequestEvtNtfn.class, params, collector);

            // 4. Serialization test
            JsonProcessor processor = new JsonProcessor(collector);
            jsonOutput = processor.serialize(request);

            // 5. Print Json
            System.out.println("\nGenerated JSON:");
            System.out.println(jsonOutput);

            System.out.println("TMST038 System Event Notification serialization succeeded without errors.\n");

            now = LocalDateTime.now();
            System.err.println("TMST038 System Event Notification serialization finished at: " + now.format(EBankingConstants.DATETIME_FORMATTER));

        } catch (JsonValidationException e) {
            System.err.println("TMST038 System Event Notification serialization Test failed: " + e.getMessage());
            System.err.println(e.getValidationErrors());
            System.err.println("********************************************");
        }
    }

    /**
     * Creates test parameters for a System Event Notification message.
     * This is typically an ADMIN.004 message.
     * @return RequestParams for the test.
     */
    private static RequestParams createTestParams() {
        String PYMT_MESSAGE_ID  = "SysEvt-ADClientName/(CuentaNr)";
        String currentTimestamp = TestDateUtils.getCurrentApiTimestamp();

        return new RequestParams()

            // AppHdr
            .setBicfiFr("BAMCSVSS")
            .setBicfiTo("AMERICA3PLX")
            .setBizMsgIdr(PYMT_MESSAGE_ID)
            .setMsgDefIdr("ADMIN.004.001.02") // Message definition for System Event Notification
            .setBizSvc("swift.cbprplus.02")
            .setCreDt(currentTimestamp)

            // Document -> SysEvtNtfctn -> EvtInf
            .setEvtCd("RCVD")
            .setEvtDesc("Solicitud recibida. Su pago se esta procesando o se procesara en la fecha indicada en el mensaje. Consulte mas tarde.")
            .setEvtTm(currentTimestamp);
    }
}
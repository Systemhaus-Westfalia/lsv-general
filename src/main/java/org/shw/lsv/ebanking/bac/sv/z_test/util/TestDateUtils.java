package org.shw.lsv.ebanking.bac.sv.z_test.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.shw.lsv.ebanking.bac.sv.misc.EBankingConstants;

/**
 * Utility class for generating dynamic and valid dates for testing purposes.
 */
public final class TestDateUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TestDateUtils() {}

    /**
     * Gets the current timestamp, formatted correctly for the API with the El Salvador timezone offset.
     * @return A formatted string like "2024-08-07T10:30:00-06:00".
     */
    public static String getCurrentApiTimestamp() {
        return LocalDateTime.now()
            .atOffset(EBankingConstants.ELSALVADOR_OFFSET)
            .format(EBankingConstants.ISO_OFFSET_DATE_TIME_FORMATTER);
    }

    /**
     * Gets the first day of the previous month as a formatted string.
     * @return A formatted string like "2024-07-01".
     */
    public static String getPreviousMonthStartDate() {
        return LocalDate.now().minusMonths(1).withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Gets the last day of the previous month as a formatted string.
     * @return A formatted string like "2024-07-31".
     */
    public static String getPreviousMonthEndDate() {
        LocalDate previousMonth = LocalDate.now().minusMonths(1);
        return previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Gets today's date as a formatted string, suitable for a required execution date.
     * @return A formatted string like "2024-08-07".
     */
    public static String getTodayDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
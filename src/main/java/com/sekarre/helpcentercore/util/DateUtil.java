package com.sekarre.helpcentercore.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static String getCurrentDateTimeFormatted() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public static String getDateTimeFormatted(LocalDateTime toFormatDateTime) {
        return toFormatDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}

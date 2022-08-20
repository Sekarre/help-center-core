package com.sekarre.helpcentercore.util;

import java.time.LocalDateTime;

public class DateUtil {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}

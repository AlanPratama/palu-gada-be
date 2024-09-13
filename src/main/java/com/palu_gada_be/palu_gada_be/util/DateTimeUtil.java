package com.palu_gada_be.palu_gada_be.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static LocalDateTime convertStringToLocalDateTime(String timestamp, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(timestamp, formatter);
    }
}

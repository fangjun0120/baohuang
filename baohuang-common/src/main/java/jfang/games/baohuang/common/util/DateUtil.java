package jfang.games.baohuang.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 时间相关的，单位毫秒
 *
 * @author jfang
 */
public class DateUtil {

    public static final ZoneOffset DEFAULT_ZONE = ZoneOffset.of("+8");
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Long localDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTimeToLong(localDateTime, DEFAULT_ZONE);
    }

    public static Long localDateTimeToLong(LocalDateTime localDateTime, ZoneOffset timezone) {
        return localDateTime.toInstant(timezone).toEpochMilli();
    }

    public static LocalDateTime longToLocalDateTime(Long timestamp) {
        return longToLocalDateTime(timestamp, DEFAULT_ZONE);
    }

    public static LocalDateTime longToLocalDateTime(Long timestamp, ZoneOffset timezone) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), timezone);
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTimeToString(localDateTime, DEFAULT_FORMAT);
    }

    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static LocalDateTime stringToLocalDateTime(String time) {
        return stringToLocalDateTime(time, DEFAULT_FORMAT);
    }

    public static LocalDateTime stringToLocalDateTime(String time, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, formatter);
    }
}

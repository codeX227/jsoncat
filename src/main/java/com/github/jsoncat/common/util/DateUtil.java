package com.github.jsoncat.common.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * @Description 时间工具类
 * @Author Goodenough
 * @Date 2021/3/2 18:28
 */
public class DateUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    .withLocale(Locale.CHINA)
                    .withZone(ZoneId.systemDefault());

    public static String now() {
        return FORMATTER.format(Instant.now());
    }
}

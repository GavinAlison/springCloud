package com.alison;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class TimeTest {

    @Test
    public void test1() throws Exception {
        DateTimeFormatter SQL_TIME_FORMAT = (new DateTimeFormatterBuilder()).appendPattern("HH:mm:ss").appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).toFormatter();
        DateTimeFormatter SQL_TIMESTAMP_FORMAT = (new DateTimeFormatterBuilder()).append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral(' ').append(SQL_TIME_FORMAT).toFormatter();
        SQL_TIMESTAMP_FORMAT.parse("2017-11-26T01:00:00Z");
        String text = "2017-11-26T01:00:00Z";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        LocalDateTime localDateTime = LocalDateTime.parse(text, dateTimeFormatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }
}

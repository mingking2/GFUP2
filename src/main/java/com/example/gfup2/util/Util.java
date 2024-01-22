package com.example.gfup2.util;

import java.time.*;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
    public static Date getCurrentDatePlusMinutes(long minutes){
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC).plusMinutes(minutes);
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

    public static Date getCurrentLocalDatePlusMinutes(long minutes){
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(minutes);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public static Date getCurrentDate(){
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

    public static Date getCurrentLocalDate(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public static java.sql.Date getSqlLocalDateFromSeconds(Long second){
        return java.sql.Date.valueOf(
                Instant.ofEpochMilli(second)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
        );
    }

    public static java.sql.Date getSqlDateFromSeconds(Long second){
        return java.sql.Date.valueOf(
                Instant.ofEpochMilli(second)
                        .atZone(ZoneOffset.UTC)
                        .toLocalDate()
        );
    }

    public static java.sql.Time getSqlLocalTimeFromSeconds(Long second){
        return java.sql.Time.valueOf(
                Instant.ofEpochMilli(second)
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime()
        );
    }

    public static java.sql.Time getSqlTimeFromSeconds(Long second){
        return java.sql.Time.valueOf(
                Instant.ofEpochMilli(second)
                        .atZone(ZoneOffset.UTC)
                        .toLocalTime()
        );
    }

    public static Long getLocalSecondsFromSqlTimeAndDate(java.sql.Date date, java.sql.Time time){
        return LocalDateTime.of(date.toLocalDate(), time.toLocalTime())
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    public static Long getSecondsFromSqlTimeAndDate(java.sql.Date date, java.sql.Time time){
        return LocalDateTime.of(date.toLocalDate(), time.toLocalTime())
                .atZone(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();
    }

    public static String getRandomNum(){
//        Random random = new Random();
//        int randomNum = random.nextInt(900000) + 100000;
//        return String.valueOf(randomNum);
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000,900000));
    }
}

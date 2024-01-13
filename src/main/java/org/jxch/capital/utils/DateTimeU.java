package org.jxch.capital.utils;

import lombok.NonNull;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeU {

    @NonNull
    public static Date datetimeTo(@NonNull Date date, String sourceTimezone, String targetTimezone) {
        Instant instant = date.toInstant();
        ZonedDateTime sourceZonedDateTime = instant.atZone(ZoneId.of(sourceTimezone));
        ZonedDateTime targetZonedDateTime = sourceZonedDateTime.withZoneSameLocal(ZoneId.of(targetTimezone));

        return new Date(targetZonedDateTime.toInstant().toEpochMilli());
    }

    @NonNull
    public static Date datetimeTo(@NonNull Date date, String targetTimezone) {
        return datetimeTo(date, TimeZone.getDefault().getID(), targetTimezone);
    }

    public static LocalTime americaToBeijingStockStartWinterTime() {
        return LocalTime.of(22, 30, 0);
    }

    public static LocalTime americaToBeijingStockEndWinterTime() {
        return LocalTime.of(5, 0, 0);
    }



}

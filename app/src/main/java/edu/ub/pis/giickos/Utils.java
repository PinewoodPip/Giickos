package edu.ub.pis.giickos;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Utils {
    private Utils() {};

    public static ZonedDateTime localDateTimeToUTC(LocalDateTime date) {
        ZonedDateTime ldtZoned = date.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));

        return utcZoned;
    }

    public static ZonedDateTime localDateToUTC(LocalDate date) {
        return localDateTimeToUTC(LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0));
    }

    public static LocalDateTime instantToLocalDateTime(long instance) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(instance), ZoneId.systemDefault()).toLocalDateTime();
    }
}

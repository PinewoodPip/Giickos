package edu.ub.pis.giickos;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Utils {
    private Utils() {};

    public static ZonedDateTime localDateToUTC(LocalDateTime date) {
        ZonedDateTime ldtZoned = date.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));

        return utcZoned;
    }

    public static LocalDateTime instantToLocalDateTime(long instance) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(instance), ZoneId.systemDefault()).toLocalDateTime();
    }
}

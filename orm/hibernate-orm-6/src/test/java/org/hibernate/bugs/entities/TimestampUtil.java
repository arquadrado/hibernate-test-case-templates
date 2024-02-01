package org.hibernate.bugs.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimestampUtil {

    private TimestampUtil() {

    }

    /**
     * The database supports nano seconds but application using the service implement optimistic locking based on millis, therefore ensuring nano seconds are always 0000
     *
     * @param instant
     * @return
     */
    public static Date lastModifiedAtAsDate(Instant instant) {
        return Date.from(lastModifiedAt(instant));
    }

    public static Instant lastModifiedAt(Instant instant) {
        return instant.truncatedTo(ChronoUnit.MILLIS);
    }

    public static Timestamp lastModifiedAtAsTimestamp(Instant instant) {
        return new Timestamp(lastModifiedAt(instant).toEpochMilli());
    }
}

package org.hibernate.bugs.entities;

public final class Constants {

    public static final String QUERY_STATE_FILTER = "CASE " +
        "WHEN DELETETS IS NOT NULL AND DELETETS != '1970-01-01 01:00:00' THEN 'DELETED' " +
        "WHEN DISABLETS IS NOT NULL AND DISABLETS != '1970-01-01 01:00:00' THEN 'DISABLED' " +
        "ELSE 'ACTIVE' end";

    private Constants() {
    }


}

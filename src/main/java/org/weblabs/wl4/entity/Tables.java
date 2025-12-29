package org.weblabs.wl4.entity;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Tables {
    
    // users
    public static final Table<Record> USERS = DSL.table("users");
    public static final Field<Long> USERS_ID = DSL.field("id", SQLDataType.BIGINT);
    public static final Field<String> USERS_EMAIL = DSL.field("email", SQLDataType.VARCHAR);
    public static final Field<String> USERS_PASSWORD_HASH = DSL.field("password_hash", SQLDataType.VARCHAR);
    
    // point_checks
    public static final Table<Record> POINT_CHECKS = DSL.table("point_checks");
    public static final Field<Long> POINT_CHECKS_ID = DSL.field("id", SQLDataType.BIGINT);
    public static final Field<Double> POINT_CHECKS_R = DSL.field("r", SQLDataType.DOUBLE);
    public static final Field<Double> POINT_CHECKS_X = DSL.field("x", SQLDataType.DOUBLE);
    public static final Field<Double> POINT_CHECKS_Y = DSL.field("y", SQLDataType.DOUBLE);
    public static final Field<Boolean> POINT_CHECKS_HIT = DSL.field("hit", SQLDataType.BOOLEAN);
    public static final Field<Timestamp> POINT_CHECKS_CHECKED_AT = DSL.field("checked_at", SQLDataType.TIMESTAMP);
    public static final Field<Long> POINT_CHECKS_EXECUTION_TIME_MS = DSL.field("execution_time_ms", SQLDataType.BIGINT);
    public static final Field<Long> POINT_CHECKS_USER_ID = DSL.field("user_id", SQLDataType.BIGINT);
}
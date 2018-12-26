package com.tmj.tms.utility;

import org.springframework.data.auditing.DateTimeProvider;

import java.util.Calendar;

public class DateAuditingProvider implements DateTimeProvider {

    @Override
    public Calendar getNow() {
        return Calendar.getInstance();
    }
}

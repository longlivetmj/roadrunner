package com.tmj.tms.utility;

import java.util.Calendar;
import java.util.Date;

public class NDaysBefore {

    public static boolean isTheDayBeforeWeek(Date previousDate) {
        boolean status = false;
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, -7);
        if (previousDate.after(today.getTime())) {
            status = true;
        }
        return status;
    }

    public Date getDateNDaysBeforeCurrentDate(int n) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, -n);
        return today.getTime();
    }

    public Date getDateNDaysAfterCurrentDate(int n) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, +n);
        return today.getTime();
    }

    public Date getDateNDaysBeforeDate(Date date, int n) {
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        today.add(Calendar.DATE, -n);
        return today.getTime();
    }

    public Date getDateNDaysAfterDate(Date date, int n) {
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        today.add(Calendar.DATE, +n);
        return today.getTime();
    }

    public Date getDateNMonthsBeforeCurrentDate(int n) {
        if (n == 0) {
            n = 1000;
        }
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH, -n);
        return today.getTime();
    }

    public Date getStartOf2013() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        return cal.getTime();
    }

    public boolean isEqualToStartOf2013(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        return cal.getTime().equals(date);
    }

}

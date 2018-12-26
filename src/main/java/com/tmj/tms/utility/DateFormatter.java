package com.tmj.tms.utility;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DateFormatter {
    private Format formatter;
    private NumberFormatter numberFormatter = new NumberFormatter();

    public static Date getStartOfTheDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfTheDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getTimeDifferenceSigned(Date dateOne, Date dateTwo) {
        String diff = "";
        Calendar calendar = Calendar.getInstance();
        if (dateOne == null || dateTwo == null) {
            return diff;
        } else {
            calendar.setTime(dateOne);
            int year1 = calendar.get(Calendar.YEAR);
            calendar.setTime(dateTwo);
            int year2 = calendar.get(Calendar.YEAR);
            if (year1 == 1900 || year2 == 1900) {
                return diff;
            } else {
                long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
                diff = String.format("%d h %d m", TimeUnit.MILLISECONDS.toHours(timeDiff),
                        TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
                if ((dateOne.getTime() - dateTwo.getTime()) < 0) {
                    diff = "- " + diff;
                }
            }
        }
        return diff;
    }

    public static String getTimeDifferenceSpecial(Date dateOne, Date dateTwo) {
        String diff = "";
        Calendar calendar = Calendar.getInstance();
        if (dateOne == null || dateTwo == null) {
            return diff;
        } else {
            calendar.setTime(dateOne);
            int year1 = calendar.get(Calendar.YEAR);
            calendar.setTime(dateTwo);
            int year2 = calendar.get(Calendar.YEAR);
            if (year1 == 1900 || year2 == 1900) {
                return diff;
            } else {
                long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
                diff = String.format("%d d %d h", TimeUnit.MILLISECONDS.toDays(timeDiff), TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.HOURS.toHours(TimeUnit.MILLISECONDS.toHours(timeDiff)));
            }
        }
        return diff;
    }

    public static String getTimeDifference(Date dateOne, Date dateTwo) {
        String diff = "";
        Calendar calendar = Calendar.getInstance();
        if (dateOne == null || dateTwo == null) {
            return diff;
        } else {
            calendar.setTime(dateOne);
            int year1 = calendar.get(Calendar.YEAR);
            calendar.setTime(dateTwo);
            int year2 = calendar.get(Calendar.YEAR);
            if (year1 == 1900 || year2 == 1900) {
                return diff;
            } else {
                long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
                diff = String.format("%d h %d m", TimeUnit.MILLISECONDS.toHours(timeDiff),
                        TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
            }
        }
        return diff;
    }

    public static long getTimeDifferenceLong(Date dateOne, Date dateTwo) {
        long diff = 0;
        Calendar calendar = Calendar.getInstance();
        if (dateOne == null || dateTwo == null) {
            return diff;
        } else {
            calendar.setTime(dateOne);
            int year1 = calendar.get(Calendar.YEAR);
            calendar.setTime(dateTwo);
            int year2 = calendar.get(Calendar.YEAR);
            if (year1 == 1900 || year2 == 1900) {
                return diff;
            } else {
                diff = Math.abs(dateOne.getTime() - dateTwo.getTime());
            }
        }
        return diff;
    }

    public static String calculateDemurrageTime(Date dateOne, Date dateTwo) {
        String returnString = "";
        long timeGap = DateFormatter.getTimeDifferenceLong(dateOne, dateTwo);
        long basicTime = 86400000;
        if (timeGap > basicTime) {
            returnString = convertMillisecondsToHoursAndMin(timeGap - basicTime);
        }
        return returnString;
    }

    public static long calculateDemurrageTimeLong(Date dateOne, Date dateTwo) {
        long timeGap = DateFormatter.getTimeDifferenceLong(dateOne, dateTwo);
        long basicTime = 86400000;
        if (timeGap > basicTime) {
            return (timeGap - basicTime);
        } else {
            return 0;
        }
    }

    public static String percentageOfTwoLongValues(long divisor, long divider) {
        return new NumberFormatter().convertToTwoDigits(Double.longBitsToDouble(divisor * 100) / Double.longBitsToDouble(divider));
    }

    public static String convertMillisecondsToHoursAndMin(long diff) {
        String format;
        format = String.format("%d h %d m", TimeUnit.MILLISECONDS.toHours(diff),
                TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff)));
        return format;
    }

    public static boolean getOnTimePlacedStatus(Date dateOne, Date dateTwo) {
        boolean status = false;
        Calendar calendar = Calendar.getInstance();
        if (dateOne == null || dateTwo == null) {
            return status;
        } else {
            calendar.setTime(dateOne);
            int year1 = calendar.get(Calendar.YEAR);
            calendar.setTime(dateTwo);
            int year2 = calendar.get(Calendar.YEAR);
            if (year1 == 1900 || year2 == 1900) {
                return status;
            } else {
                DateFormatter dateFormatter = new DateFormatter();
                Date dateTwoPlusThirty = dateFormatter.addMinutesToDate(dateTwo, 30);
                if ((dateOne.getTime() <= dateTwoPlusThirty.getTime())) {
                    status = true;
                }
            }
        }
        return status;
    }

    public static String replaceSquareBrackets(String string) {
        return string.replaceAll("\\[", "").replaceAll("\\]", "");
    }

    public static void main(String[] args) throws ParseException {
        DateFormatter dateFormatter = new DateFormatter();
        Date start = dateFormatter.convertToDateLongWebService("2018-01-05 11:30 am");
        Date end = dateFormatter.convertToDateLongWebService("2018-01-06 10:30 am");
        System.out.println(">>>>>>>>>>" + DateFormatter.getDateDiff(start, end, TimeUnit.HOURS));
    }

    public static String convertMinutesToHumanReadable(Integer totalMinutes) {
        String format;
        format = String.format("%d h %d m", TimeUnit.MINUTES.toHours(totalMinutes),
                TimeUnit.MINUTES.toMinutes(totalMinutes) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(totalMinutes)));
        return format;
    }

    public static Date getStartOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateFormatter.getStartOfTheDay(calendar.getTime());
    }

    public static Date getLastDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return DateFormatter.getStartOfTheDay(calendar.getTime());
    }

    public static Date getEndOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return DateFormatter.getEndOfTheDay(calendar.getTime());
    }

    public static String getMonth(Integer month) {
        String monthName = "";
        try {
            monthName = Month.of(month).name();
        } catch (Exception e) {
            System.out.println("Month Convertion Errror");
        }
        return monthName;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static Integer getMonthOfDate(Date date) {
        Integer month = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            month = calendar.get(Calendar.MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return month;
    }

    public static Date getStartOfThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateFormatter.getStartOfTheDay(calendar.getTime());
    }

    public static Date getEndOfThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return DateFormatter.getEndOfTheDay(calendar.getTime());
    }

    public String returnSortFormattedDate(Date date) {
        String formattedDate = null;
        try {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = formatter.format(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return formattedDate;
    }

    public String returnLongFormattedDateTime(Date date) {
        String formattedDate = null;
        try {
            if (date != null) {
                formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
                formattedDate = formatter.format(date);
            } else {
                formattedDate = "";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return formattedDate;
    }

    public String returnLongFormattedDateTime24(Date date) {
        String formattedDate = null;
        try {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedDate = formatter.format(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return formattedDate;
    }

    public Date convertToDate(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date convertToDateSpecial(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public String returnFormattedDateIncito(Date date) {
        String formattedDate = "";
        try {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public String returnFormattedDateGeneral(Date date) {
        String formattedDate = "";
        try {
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            formattedDate = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public String returnFormattedDate(Date date) {
        String formattedDate = "";
        try {
            formatter = new SimpleDateFormat("dd MMMM, yyyy");
            formattedDate = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public Date convertToDateLong(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date convertToDateLongWebService(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date convertToDateDayEnd(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getBaseDateForSql() {
        String dateString = "1900-01-01";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getTimeSpendInFactory(Date arrivedAtFactory, Date departedFromFactory) {
        String time = "";
        Long diff = DateFormatter.getTimeDifferenceLong(arrivedAtFactory, departedFromFactory);
        Double diffInHours = diff.doubleValue() / 3600000;
        time = numberFormatter.convertToTwoDecimal(diffInHours);
        return time;
    }

    public String getTimeSpendInYard(Date arrivedAtYard, Date departedFromYard) {
        String time = "";
        Long diff = DateFormatter.getTimeDifferenceLong(arrivedAtYard, departedFromYard);
        Double diffInHours = diff.doubleValue() / 3600000;
        time = numberFormatter.convertToTwoDecimal(diffInHours);
        return time;
    }

    public String getTravellingTime(Date departedFromFactory, Date arrivedAtYard) {
        String time = "";
        Long diff = DateFormatter.getTimeDifferenceLong(departedFromFactory, arrivedAtYard);
        Double diffInHours = diff.doubleValue() / 3600000;
        time = numberFormatter.convertToTwoDecimal(diffInHours);
        return time;
    }

    public String getChargeableDemmuraageHours(Date AED_AET, Date ACD_ACT) {
        String time = "";
        Long diff = DateFormatter.calculateDemurrageTimeLong(AED_AET, ACD_ACT);
        if (diff.equals(0)) {
            time = "0.0";
        } else {
            Double diffInHours = diff.doubleValue() / 3600000;
            time = numberFormatter.convertToTwoDecimal(diffInHours);
        }
        return time;
    }

    public Date changeDateToTomorrow(Date enteredDate, Date comparisonDate) {
        Calendar toDay = Calendar.getInstance();
        int dayOfYear = toDay.get(Calendar.DAY_OF_YEAR);
        Calendar enteredCal = Calendar.getInstance();
        enteredCal.setTime(enteredDate);
        enteredCal.set(Calendar.DAY_OF_YEAR, dayOfYear + 1);
        enteredCal.set(Calendar.YEAR, toDay.get(Calendar.YEAR));
        if (comparisonDate.after(enteredCal.getTime())) {
            enteredCal.add(Calendar.DAY_OF_YEAR, 1);
        }
        return enteredCal.getTime();
    }

    public boolean isTheDateToday(Date searchDate) {
        boolean flag;
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 00);
        today.set(Calendar.MINUTE, 00);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        Date getTodayDate = today.getTime();
        Integer difference = getTodayDate.compareTo(searchDate);
        if (difference == 0) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public Date convertToDateDotFormat(String dateString) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
    }

    public Date convertToDateLongWithTime(String dateString) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(dateString);
    }

    public Date addMinutesToDate(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public String returnSortFormattedDateSpecial(Date date) {
        String formattedDate = null;
        try {
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            formattedDate = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public String returnYearAndMonthName() {
        String formattedDate = null;
        try {
            Date today = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM - YYYY");
            formattedDate = dateFormat.format(today);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public String returnFormattedDateTime(Date date) {
        String formattedDate = null;
        try {
            formatter = new SimpleDateFormat("dd/MM/yy hh:mm a");
            formattedDate = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public String getFormattedTimePortion(Date collectionTime) {
        String formattedTime = null;
        try {
            formatter = new SimpleDateFormat("hh:mm a");
            formattedTime = formatter.format(collectionTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedTime;

    }

    public Map<Integer, String> getDaysOfWeek() {
        Map<Integer, String> weekMap = new HashMap<Integer, String>() {{
            put(1, "Sunday");
            put(2, "Monday");
            put(3, "Tuesday");
            put(4, "Wednesday");
            put(5, "Thursday");
            put(6, "Friday");
            put(7, "Saturday");

        }};
        return weekMap;
    }

    public Integer getDayOfWeekFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public Integer getMinutesBetweenTwoTime(Date deliveryDate, Date arrivalDate) {
        Integer timeInMin = 0;
        long timeGap = DateFormatter.getTimeDifferenceLong(deliveryDate, arrivalDate);
        if (timeGap == 0) {
            timeInMin = 0;
        } else {
            Double diffInHours = Math.ceil(timeGap / 60000.0);
            timeInMin = diffInHours.intValue();
        }
        return timeInMin;
    }

    public Date getAmendedTimeFromArrivalTime(Date deliveryDate, Date arrivalDate) {
        DateFormatter dateFormatter = new DateFormatter();
        Integer timeInMin = dateFormatter.getMinutesBetweenTwoTime(deliveryDate, arrivalDate);
        Date tomorrowDate = dateFormatter.changeDateToTomorrow(arrivalDate, new Date());
        return dateFormatter.addMinutesToDate(tomorrowDate, timeInMin);
    }

    public Date addNDaysToADate(int n, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, n);
        return calendar.getTime();
    }

    public Date addMonthsToADate(int n, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, n);
        return calendar.getTime();
    }

    public Date setHourAndMinute(String hourAndMinute, Date date) {
        String[] hourAndMinuteArray = hourAndMinute.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourAndMinuteArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(hourAndMinuteArray[1]));
        return calendar.getTime();
    }

    public Integer getCurrentWeekNo() {
        Integer weekNo = 0;
        try {
            Calendar cal = Calendar.getInstance();
            weekNo = cal.get(Calendar.WEEK_OF_YEAR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weekNo;
    }

    public String dateRangeFromWeekNo(Integer weekNo) {
        String dateRange = "";
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.WEEK_OF_YEAR, weekNo);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            String startDate = this.returnSortFormattedDate(cal.getTime());
            cal.add(Calendar.DATE, 6);
            String endDate = this.returnSortFormattedDate(cal.getTime());
            dateRange = startDate + " to " + endDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateRange;
    }

    public Date getDateByWeekNoDayOfWeek(Integer weekNo, Integer dayOfWeek) {
        Date date = null;
        try {
            Calendar cal = Calendar.getInstance();
            if (dayOfWeek == 1) {
                cal.set(Calendar.WEEK_OF_YEAR, weekNo + 1);
                cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            } else {
                cal.set(Calendar.WEEK_OF_YEAR, weekNo);
                cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            }
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            date = cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public List<String> dateListFromWeekNo(Integer weekNo) {
        List<String> dateList = new ArrayList<String>();
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.WEEK_OF_YEAR, weekNo);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            dateList.add(this.returnSortFormattedDate(cal.getTime()));
            for (int i = 0; i < 6; i++) {
                cal.add(Calendar.DATE, 1);
                dateList.add(this.returnSortFormattedDate(cal.getTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateList;
    }

    public Date changeDate(Date arrivalDate, Date arrivalTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(arrivalDate);
        Calendar arrivalTimeCalendar = Calendar.getInstance();
        arrivalTimeCalendar.setTime(arrivalDate);
        calendar.set(Calendar.HOUR_OF_DAY, arrivalTimeCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, arrivalTimeCalendar.get(Calendar.MINUTE));
        return calendar.getTime();
    }

    public Date changeDateToAnother(Date originalDate, Date newDate) {
        Date amendedDate = null;
        try {
            Calendar originalCalender = Calendar.getInstance();
            originalCalender.setTime(originalDate);

            Calendar newDateCalender = Calendar.getInstance();
            newDateCalender.setTime(newDate);

            originalCalender.set(Calendar.MONTH, newDateCalender.get(Calendar.MONTH));
            originalCalender.set(Calendar.DAY_OF_MONTH, newDateCalender.get(Calendar.DAY_OF_MONTH));
            amendedDate = originalCalender.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amendedDate;
    }

    public String dayOfWeek(Date date) {
        String dayOfWeek = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
            dayOfWeek = formatter.format(date);
        } catch (Exception e) {
            System.out.println("Parse Exception");
        }
        return dayOfWeek;
    }

    public boolean currentTimeBetweenTwoHours(int from, int to) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int t = c.get(Calendar.HOUR_OF_DAY) * 100 + c.get(Calendar.MINUTE);
        boolean isBetween = to > from && t >= from && t <= to || to < from && (t >= from || t <= to);
        return isBetween;
    }

    public String getCurrentMonthName() {
        String formattedDate = null;
        try {
            Date today = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");
            formattedDate = dateFormat.format(today);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
}

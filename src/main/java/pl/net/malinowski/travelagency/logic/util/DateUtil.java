package pl.net.malinowski.travelagency.logic.util;

import sun.util.resources.cldr.ta.CalendarData_ta_IN;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    private static final DateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    private DateUtil(){}

    public static String formatDate(Date date) {
        return FORMATTER.format(date);
    }

    public static long daysBetween(Date from, Date to) {
        long diff = to.getTime() - from.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}

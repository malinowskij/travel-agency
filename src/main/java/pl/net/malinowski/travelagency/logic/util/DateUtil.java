package pl.net.malinowski.travelagency.logic.util;

import sun.util.resources.cldr.ta.CalendarData_ta_IN;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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

    public static Date buildDate(int d, int m, int y) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m, d);
        return calendar.getTime();
    }

    public static Date toDate(String s) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMAN);
        try {
            return format.parse(s);
        } catch (ParseException e) {
            return new Date();
        }
    }
}

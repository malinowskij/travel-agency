package pl.net.malinowski.travelagency.logic.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final DateFormat FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    private DateUtil(){}

    public static String formatDate(Date date) {
        return FORMATTER.format(date);
    }
}

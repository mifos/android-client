package org.mifos.androidclient.util.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    private static DateFormat sFormatter;

    private static DateFormat getInstance() {
        if (sFormatter == null) {
            sFormatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        }
        return sFormatter;
    }

    public static String format(long dateAsNumber) {
        return getInstance().format(new Date(dateAsNumber));
    }

    public static String format(Date date) {
        return getInstance().format(date);
    }

}

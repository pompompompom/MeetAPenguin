package com.penguin.meetapenguin.util;

import java.text.SimpleDateFormat;

/**
 * Created by urbano on 4/21/16.
 */
public class DateFormatter {

    /**
     * Use this convert to guarantee that all dates are using the same format MM/dd/yyyy
     *
     * @param timestamp
     * @return
     */
    public static String convertTimeStampToDate(long timestamp) {
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp);
    }
}

package com.softcorridor.attendance.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AttendanceUtil {
    public static Timestamp getCurrentSQLDateTimestamp() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdfAmerica.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
        String sDateInAmerica = sdfAmerica.format(date);
        Timestamp currentSQLDateTimestamp = Timestamp.valueOf(sDateInAmerica);
        return currentSQLDateTimestamp;
    }

    public static LocalDateTime now() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdfAmerica.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
        String sDateInAmerica = sdfAmerica.format(date);
        Timestamp currentSQLDateTimestamp = Timestamp.valueOf(sDateInAmerica);
        return currentSQLDateTimestamp.toLocalDateTime();
    }

    public static Date getCurrentDateTime(String timezone) {
        ZoneId defaultZoneId = ZoneId.of(timezone);
        LocalDateTime now = LocalDateTime.now(defaultZoneId);
        Timestamp valueOf = Timestamp.valueOf(now);
        return valueOf;
    }
    public static String getPrintableDateTime(String seperator){
        Date currentDateTime = getCurrentDateTime("Africa/Lagos");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
        String date = dateFormat.format(currentDateTime);

        //hh:mm:ss

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        timeFormat.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
        String time = timeFormat.format(currentDateTime);

        return date.concat(seperator).concat(time);
    }

}

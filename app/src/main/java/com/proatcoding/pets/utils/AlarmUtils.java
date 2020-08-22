package com.proatcoding.pets.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import androidx.core.app.ActivityCompat;

import com.proatcoding.pets.models.AlarmDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.proatcoding.pets.database.DatabaseHelper._ID;
import static com.proatcoding.pets.database.DatabaseHelper.COL_DAY;
import static com.proatcoding.pets.database.DatabaseHelper.COL_IS_ENABLED;
import static com.proatcoding.pets.database.DatabaseHelper.COL_LABEL;
import static com.proatcoding.pets.database.DatabaseHelper.COL_TIME;

public final class AlarmUtils {

    private static final SimpleDateFormat TIME_FORMAT =
            new SimpleDateFormat("h:mm", Locale.getDefault());
    private static final SimpleDateFormat AM_PM_FORMAT =
            new SimpleDateFormat("a", Locale.getDefault());

    private static final int REQUEST_ALARM = 1;
    private static final String[] PERMISSIONS_ALARM = {
            Manifest.permission.VIBRATE
    };

    private AlarmUtils() { throw new AssertionError(); }

    public static void checkAlarmPermissions(Activity activity) {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        final int permission = ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.VIBRATE
        );

        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_ALARM,
                    REQUEST_ALARM
            );
        }

    }

    public static ContentValues toContentValues(AlarmDTO alarm) {

        final ContentValues cv = new ContentValues(10);

        cv.put(COL_TIME, alarm.getDatetime());
        cv.put(COL_LABEL, alarm.getLabel());
        cv.put(COL_DAY, alarm.getDay());
        cv.put(COL_IS_ENABLED, alarm.isEnabled());

        return cv;

    }

    public static ArrayList<AlarmDTO> buildAlarmList(Cursor c) {

        if (c == null) return new ArrayList<>();

        final int size = c.getCount();

        final ArrayList<AlarmDTO> alarms = new ArrayList<>(size);

        if (c.moveToFirst()){
            do {

                final long id = c.getLong(c.getColumnIndex(_ID));
                final long time = c.getLong(c.getColumnIndex(COL_TIME));
                final String label = c.getString(c.getColumnIndex(COL_LABEL));
                final boolean day = c.getInt(c.getColumnIndex(COL_DAY)) == 1;
                final boolean isEnabled = c.getInt(c.getColumnIndex(COL_IS_ENABLED)) == 1;

                final AlarmDTO alarm = new AlarmDTO(id, time, label);
                alarm.setDay(String.valueOf(day));
                alarm.setEnabled(isEnabled);
                alarms.add(alarm);

            } while (c.moveToNext());
        }

        return alarms;

    }

    public static String getReadableTime(long time) {
        return TIME_FORMAT.format(time);
    }

    public static String getAmPm(long time) {
        return AM_PM_FORMAT.format(time);
    }

    public static boolean isAlarmActive(AlarmDTO alarm) {

        boolean isActive = false;
        if(alarm.getDay().equalsIgnoreCase("true")){
            isActive = true;
        }else{
            isActive =false;
        }

        return isActive;

    }

    public static String getActiveDaysAsString(AlarmDTO alarm) {

        StringBuilder builder = new StringBuilder("Active Days: ");

        if(alarm.getDay().equalsIgnoreCase("true")) builder.append("dayActive, ");

        if(builder.substring(builder.length()-2).equals(", ")) {
            builder.replace(builder.length()-2,builder.length(),".");
        }

        return builder.toString();

    }

}

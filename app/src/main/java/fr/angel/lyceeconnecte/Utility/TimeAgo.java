package fr.angel.lyceeconnecte.Utility;

import android.content.Context;
import android.content.res.Resources;

import java.util.Calendar;
import java.util.Date;

import fr.angel.lyceeconnecte.R;

public class TimeAgo {

    private static final long SECOND_MILLIS = 1000;
    private static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final long DAY_MILLIS = 24 * HOUR_MILLIS;

    private static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getTimeAgo(Date date, Context context) {
        long time = date.getTime();
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = currentDate().getTime();
        if (time > now || time <= 0) {
            return context.getString(R.string.in_the_future);
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return context.getString(R.string.moments_ago);
        } else if (diff < 2 * MINUTE_MILLIS) {
            return context.getString(R.string.a_minute_ago);
        } else if (diff < 60 * MINUTE_MILLIS) {
            return context.getString(R.string.time_ago_prefix) + diff / MINUTE_MILLIS + context.getString(R.string.minutes);
        } else if (diff < 2 * HOUR_MILLIS) {
            return context.getString(R.string.an_hour_ago);
        } else if (diff < 24 * HOUR_MILLIS) {
            return context.getString(R.string.time_ago_prefix) + diff / HOUR_MILLIS + context.getString(R.string.hours_ago);
        } else if (diff < 48 * HOUR_MILLIS) {
            return context.getString(R.string.yesterday);
        } else {
            return context.getString(R.string.time_ago_prefix) + diff / DAY_MILLIS + context.getString(R.string.days_ago);
        }
    }

    public static String getTimeAgo(long time, Context context) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = currentDate().getTime();
        if (time > now || time <= 0) {
            return context.getString(R.string.in_the_future);
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return context.getString(R.string.moments_ago);
        } else if (diff < 2 * MINUTE_MILLIS) {
            return context.getString(R.string.a_minute_ago);
        } else if (diff < 60 * MINUTE_MILLIS) {
            return context.getString(R.string.time_ago_prefix) + diff / MINUTE_MILLIS + context.getString(R.string.minutes);
        } else if (diff < 2 * HOUR_MILLIS) {
            return context.getString(R.string.an_hour_ago);
        } else if (diff < 24 * HOUR_MILLIS) {
            return context.getString(R.string.time_ago_prefix) + diff / HOUR_MILLIS + context.getString(R.string.hours_ago);
        } else if (diff < 48 * HOUR_MILLIS) {
            return context.getString(R.string.yesterday);
        } else {
            return context.getString(R.string.time_ago_prefix) + diff / DAY_MILLIS + context.getString(R.string.days_ago);
        }
    }

}
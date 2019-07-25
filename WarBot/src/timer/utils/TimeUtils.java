package timer.utils;

import timer.models.TimeIntervalModel;

import java.time.LocalTime;

public class TimeUtils {
    public static boolean isCurrentTimeBetweenTimeInterval(TimeIntervalModel dateRange) {
        LocalTime currentDate = LocalTime.now();
        return currentDate.isAfter(dateRange.getBegin()) && currentDate.isBefore(dateRange.getEnd());
    }
}

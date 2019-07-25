package timer.models;

import java.time.LocalTime;

public class TimeIntervalModel {
    private LocalTime begin;
    private LocalTime end;

    public TimeIntervalModel(LocalTime begin, LocalTime end) {
        if (begin.isAfter(end)) {
            throw new IllegalArgumentException("Begin time can´t be higher than end time");
        }
        this.begin = begin;
        this.end = end;
    }

    public LocalTime getBegin() {
        return begin;
    }

    public void setBegin(LocalTime begin) {
        this.begin = begin;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}

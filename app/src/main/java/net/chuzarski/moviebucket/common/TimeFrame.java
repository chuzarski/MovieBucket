package net.chuzarski.moviebucket.common;

import org.threeten.bp.LocalDate;

public class TimeFrame {

    private LocalDate from;
    private LocalDate to;

    public TimeFrame(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    // todo profile these, memory usage?
    public static TimeFrame thisWeek() {
        return new TimeFrame(LocalDate.now(), LocalDate.now().plusDays(StaticValues.DAYS_IN_WEEK));
    }

    public static TimeFrame thisMonth() {
        return new TimeFrame(LocalDate.now().withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()));
    }

    public static TimeFrame nextMonth() {
        return new TimeFrame(LocalDate.now().plusMonths(1).withDayOfMonth(1),
                LocalDate.now().plusMonths(1).withDayOfMonth(LocalDate.now().lengthOfMonth()));
    }

    public static TimeFrame nextThreeMonths() {
        return new TimeFrame(LocalDate.now().plusMonths(1).withDayOfMonth(1),
                LocalDate.now().plusMonths(3).withDayOfMonth(LocalDate.now().lengthOfMonth()));
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}

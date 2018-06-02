package net.chuzarski.moviebucket.common;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public class StaticHelpers {

    public static LocalDate parseStringDateToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

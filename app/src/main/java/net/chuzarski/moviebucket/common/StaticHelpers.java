package net.chuzarski.moviebucket.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public class StaticHelpers {

    public static LocalDate parseStringDateToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * @return Pixel value derived from DP
     */
    public static float dpToPx(float dp, Context ctx) {
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        return dp * ( (float) displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}

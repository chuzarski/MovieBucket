package net.chuzarski.moviebucket.common;

/**
 * Created by cody on 3/26/18.
 */

/**
 * This class is a helper for creating URLs to the movie poster or backdrop images
 *
 * TODO Currently only "original" size images will be fetched. There needs to be a mechanic for fetching different sized images based on device.
 * TMDB has support for images of multiple sizes. The API has an endpoint for "configuration"
 * Which helps
 */
public class MovieImagePathHelper {

    private static String backdropDefaultSize = "original";
    private static String backdropUrlBase = "https://image.tmdb.org/t/p/";

    public enum Size {
        small, medium, xlarge, xxlarge, xxxlarge
    }

    public static String createURLForBackdrop(String path) {
        // TODO refactor this to do a better job at checking for escaped slashes and obviously dynamic image sizes.
        return backdropUrlBase + backdropDefaultSize + path;
    }

    public static String getBackdropURL() {
        return "";
    }

    public static String getPosterURL() {
        return "";
    }

    private static String resolvePosterSize(Size sz) {
        return "";
    }

    private static String resolveBackdropSize(Size sz) {
        return "";
    }
}

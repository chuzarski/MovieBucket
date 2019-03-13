package net.chuzarski.moviebucket.common;

import net.chuzarski.moviebucket.models.ServiceConfigurationModel;

import net.chuzarski.moviebucket.models.ServiceConfigurationModel.ImagesConfigurationModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a helper for creating URLs to the movie poster or backdrop images
 *
 * TODO Currently only "original" size images will be fetched. There needs to be a mechanic for fetching different sized images based on device.
 * TMDB has support for images of multiple sizes. The API has an endpoint for "configuration"
 * Which helps
 */
public class MovieImagePathHelper {

    private static String backdropDefaultSize = "w500";
    private static String backdropUrlBase = "https://image.tmdb.org/t/p/";


    private Map<String, Integer> backdropSizes;
    private Map<String, Integer> posterSizes;

    public enum Size {
        small, medium, xlarge, xxlarge, xxxlarge
    }

    public static String createURLForBackdrop(String path) {
        // TODO refactor this to do a better job at checking for escaped slashes and obviously dynamic image sizes.
        // See https://developer.android.com/reference/android/net/Uri.Builder
        return backdropUrlBase + backdropDefaultSize + path;
    }

    public MovieImagePathHelper(ImagesConfigurationModel configModel) {
        backdropSizes = new HashMap<>();
        posterSizes = new HashMap<>();

        parseSizesFromString(configModel.getBackdropSizes(), backdropSizes);
        parseSizesFromString(configModel.getPosterSizes(), posterSizes);
    }

    private void parseSizesFromString(List<String> sizes, Map<String, Integer> outMap) {
        for(String strSize : sizes) {
            if(strSize.equals("original")) {
                outMap.put(strSize, Integer.MAX_VALUE);
            } else {
                outMap.put(strSize, Integer.parseInt(strSize.replaceAll("[^\\d]", "")));
            }
        }
    }

    public Map<String, Integer> getBackdropSizes() {
        return backdropSizes;
    }

    public Map<String, Integer> getPosterSizes() {
        return posterSizes;
    }

    private void resolveSize(int dp, int dpi, Map<String, Integer> map) {
        int px = (int) (dp * dpi + 0.5f);

    }
}

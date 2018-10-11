package net.chuzarski.moviebucket.common;

public class StaticValues {


    // Required URLS
    public static final String networkApiUrlBase = "https://api.themoviedb.org/3/";

    // Listing Fragment and friends
    public static int listingPageSize = 1;
    public static int listingPrefetchDistance = 8;


    // Bundle var names
    public static final String BUNDLE_ATTR_MOVIE_ID = "movie_id";


    // Feed Types
    public static final int INTERNET_LISTING_UPCOMING = 0;
    public static final int INTERNET_LISTING_NOW_PLAYING = 1;
    public static final int INTERNET_LISTING_TOP_RATED = 2;
    public static final int INTERNET_LISTING_POPULAR = 3;
    public static final int INTERNET_LISTING_SEARCH = 100;

    // Listing Types
    public static final int LISTING_TYPE_WATCH_LIST = 200;

    // Load states
    public static final int LOAD_STATE_LOADING = 100;
    public static final int LOAD_STATE_LOADED = 200;
    public static final int LOAD_STATE_FAILED = 500;
    public static final int LOAD_STATE_NOT_FOUND = 404;

    // Keys
    public static final String KEY_LISTING_REPO_LOAD_SOURCE = "REPO_SOURCE";
}

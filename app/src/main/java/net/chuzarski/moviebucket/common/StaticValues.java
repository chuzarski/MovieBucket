package net.chuzarski.moviebucket.common;

public class StaticValues {

    // Listing Fragment and friends
    public static int listingPageSize = 1;
    public static int listingPrefetchDistance = 8;


    // Bundle var names
    public static final String BUNDLE_ATTR_MOVIE_ID = "movie_id";


    // Feed Types
    public static final int FEED_TYPE_UPCOMING = 0;
    public static final int FEED_TYPE_NOW_PLAYING = 1;
    public static final int FEED_TYPE_TOP_RATED = 2;
    public static final int FEED_TYPE_POPULAR = 3;

    // Listing Types
    public static final int LISTING_TYPE_FRESH = 10;
    public static final int LISTING_TYPE_SEARCH = 11;
    public static final int LISTING_TYPE_WATCH_LIST = 12;

    // Load states
    public static final int LOAD_STATE_LOADING = 100;
    public static final int LOAD_STATE_LOADED = 200;
    public static final int LOAD_STATE_FAILED = 300;
    public static final int LOAD_STATE_NOT_FOUND = 400;
}

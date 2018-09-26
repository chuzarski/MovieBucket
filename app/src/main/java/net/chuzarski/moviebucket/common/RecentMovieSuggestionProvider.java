package net.chuzarski.moviebucket.common;

import android.content.SearchRecentSuggestionsProvider;
import android.provider.SearchRecentSuggestions;

public class RecentMovieSuggestionProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "net.chuzarski.moviebucket.common.RecentMovieSuggestionProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;
    public RecentMovieSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}

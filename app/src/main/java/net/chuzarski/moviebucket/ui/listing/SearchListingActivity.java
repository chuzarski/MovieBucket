package net.chuzarski.moviebucket.ui.listing;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.RecentMovieSuggestionProvider;
import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.ui.detail.DetailActivity;
import net.chuzarski.moviebucket.ui.listing.ListingFragment.ListingFragmentInteractor;

public class SearchListingActivity extends AppCompatActivity implements ListingFragmentInteractor {

    ListingFragment fragment;

    public static final String KEY_FRAGMENT = "FRAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        if (savedInstanceState == null) {
            fragment = createSearchListingFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_roll_fragment_frame, fragment).commit();
        } else {
            fragment = (ListingFragment) getSupportFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listing_activity_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, fragment);
        super.onSaveInstanceState(outState);
    }

    private ListingFragment createSearchListingFragment() {
        Bundle fragmentArgs = new Bundle();
        String searchQuery = handleSearchIntent();

        fragmentArgs.putInt(ListingFragment.KEY_FRAGMENT_MODE,
                ListingFragment.MODE_FLAG_SEARCH_LISTING);
        fragmentArgs.putString(ListingFragment.KEY_SEARCH_QUERY, searchQuery);
        return ListingFragment.newInstance(fragmentArgs);
    }


    private String handleSearchIntent() {
        SearchRecentSuggestions searchSuggestions;
        String searchQuery;

        searchQuery = getIntent().getStringExtra(SearchManager.QUERY);
        searchSuggestions = new SearchRecentSuggestions(this,
                RecentMovieSuggestionProvider.AUTHORITY,
                RecentMovieSuggestionProvider.MODE);
        searchSuggestions.saveRecentQuery(searchQuery, null);

        return searchQuery;
    }

    @Override
    public void disableReloadAction() {

    }

    @Override
    public void enabledReloadAction() {

    }

    @Override
    public void startMovieDetail(@NonNull int id) {
        Intent detailActivityIntent = new Intent(this, DetailActivity.class);
        detailActivityIntent.putExtra(StaticValues.BUNDLE_ATTR_MOVIE_ID, id);
        startActivity(detailActivityIntent);
    }
}

package net.chuzarski.moviebucket.ui.listing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.ui.detail.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListingActivity extends AppCompatActivity implements ListingFragment.ListingFragmentInteractor {

    // constants
    public static final String FRAGMENT_KEY_CURRENT = "FRAG_CURRENT";
    public static final String MOVIE_FEED_KEY = "MOVIE_FEED_TYPE";

    // UI references
    ListingFragment fragment;

    @BindView(R.id.listing_activity_feed_toolbar)
    Toolbar feedUIToolbar;

    Toolbar localUIToolbar;
    @BindView(R.id.listing_feed_spinner)
    Spinner movieFeedSpinner;

    // state
    private int movieFeedType;

    ///////////////////////////////////////////////////////////////////////////
    // Activity callbacks
    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        ButterKnife.bind(this);

        // todo UI setUI methods are going to be handling fragment management in the future
        if(savedInstanceState == null) {
            initUI();

            // todo maybe the user wants to set what listing the app opens to?
            setUIFeedListing();
        } else {
            fragment = (ListingFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY_CURRENT);
            movieFeedType = savedInstanceState.getInt(MOVIE_FEED_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        movieFeedSpinner.setOnItemSelectedListener(feedSelectionListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, FRAGMENT_KEY_CURRENT, fragment);
        outState.putInt(MOVIE_FEED_KEY, movieFeedType);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listing_activity_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                fragment.refreshList();
                return true;
            case R.id.action_search:
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal setup functions
    ///////////////////////////////////////////////////////////////////////////
    /**
     * This method decides what kind of listing this activity will be
     * i.e. is the activity being opened on fresh start, saved movies, search?
     */
    ///////////////////////////////////////////////////////////////////////////
    // UI Setup functions
    ///////////////////////////////////////////////////////////////////////////
    private void setUIFeedListing() {
        setSupportActionBar(feedUIToolbar);
        getSupportActionBar().setTitle("");

    }

    private void setUILocalListing() {
        setSupportActionBar(localUIToolbar);
        getSupportActionBar().setTitle(getResources()
                .getString(R.string.listing_activity_local_listing_toolbar_label));

        fragment = ListingFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_roll_fragment_frame, fragment).commit();
    }

    private void initUI() {
        initFeedSpinner();
    }

    private void initFeedSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.movie_feeds, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        movieFeedSpinner.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Fragment Management
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // UI Listeners
    ///////////////////////////////////////////////////////////////////////////

    OnItemSelectedListener feedSelectionListener = new OnItemSelectedListener() {
        private boolean allowSelections = false;
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            movieFeedType = position;
            if(allowSelections) {
                if (fragment != null && fragment.getNetworkFeedConfiguration() != null) {
                    fragment.getNetworkFeedConfiguration().setFeedType(position);
                    fragment.refreshList();
                }
            } else {
                allowSelections = true;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    ///////////////////////////////////////////////////////////////////////////
    // ListingFragmentInteractor interface implementation
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void disableReloadAction() {
        // TODO implement
        Timber.d("Implement disabling reload action");
    }

    @Override
    public void enabledReloadAction() {
        // TODO implement
        Timber.d("Implement enabling reload action");
    }

    @Override
    public void startMovieDetail(@NonNull int id) {
        Intent detailActivityIntent = new Intent(this, DetailActivity.class);
        detailActivityIntent.putExtra(StaticValues.BUNDLE_ATTR_MOVIE_ID, id);
        startActivity(detailActivityIntent);
    }
}

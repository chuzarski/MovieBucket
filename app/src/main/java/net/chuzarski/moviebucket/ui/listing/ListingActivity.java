package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.ViewModelProviders;
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
import net.chuzarski.moviebucket.common.FeedListingCriteria;
import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.ui.detail.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListingActivity extends AppCompatActivity implements ListingFragment.ListingFragmentInteractor {

    // constants
    public static final String FRAGMENT_KEY = "FRAG_LISTING";
    public static final String MOVIE_FEED_KEY = "MOVIE_FEED_TYPE";

    // UI references
    ListingFragment fragment;
    @BindView(R.id.listing_activity_toolbar)
    Toolbar activityToolbar;
    @BindView(R.id.listing_feed_spinner)
    Spinner movieFeedSpinner;

    // state
    private int movieFeedType;
    ListingActivityViewModel viewModel;

    ///////////////////////////////////////////////////////////////////////////
    // Activity callbacks
    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_roll);
        Timber.tag("ListingActivity");
        Timber.d("Activity Created");

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(ListingActivityViewModel.class);

        if(savedInstanceState == null) {
            fragment = ListingFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_roll_fragment_frame, fragment).commit();
        } else {
            fragment = (ListingFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
            movieFeedType = savedInstanceState.getInt(MOVIE_FEED_KEY, 0); //todo possibly set this to default user vaule?
        }

        configureListingType();

        initDefaultToolbar();
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
        getSupportFragmentManager().putFragment(outState, FRAGMENT_KEY, fragment);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal Config functions
    ///////////////////////////////////////////////////////////////////////////

    private void configureListingType() {
        configInternetListing();
    }
    private void configInternetListing() {
        initFeedSpinner();
        if (viewModel.getFeedListingCriteria() == null) {
            FeedListingCriteria criteria = new FeedListingCriteria();
            criteria.setFeedType(StaticValues.INTERNET_LISTING_UPCOMING);
            criteria.setIsoLanguage("en");
            criteria.setIsoRegion("US");
            // setup default critera
            viewModel.setFeedListingCriteria(criteria);
        }
    }
    ///////////////////////////////////////////////////////////////////////////
    // UI Setup functions
    ///////////////////////////////////////////////////////////////////////////
    private void initDefaultToolbar() {
        setSupportActionBar(activityToolbar);
        // spinner needs to be setup
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
    }

    private void initFeedSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.movie_feeds, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        movieFeedSpinner.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // UI Listeners
    ///////////////////////////////////////////////////////////////////////////

    OnItemSelectedListener feedSelectionListener = new OnItemSelectedListener() {
        private boolean allowSelections = false;
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            movieFeedType = position;
            if(allowSelections) {
                if(viewModel.getFeedListingCriteria() != null) {
                    viewModel.getFeedListingCriteria().setFeedType(position);
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

    @Override
    public FeedListingCriteria getInternetListingCriteria() {
        return viewModel.getFeedListingCriteria();
    }
}

package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

        //todo this is probably a good spot to set UI defaults based on user prefs
        if(savedInstanceState == null) {
            Timber.d("Saved Instance missing..");
            fragment = ListingFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_roll_fragment_frame, fragment).commit();
        } else {
            fragment = (ListingFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
            movieFeedType = savedInstanceState.getInt(MOVIE_FEED_KEY, 0); //todo possibly set this to default user vaule?
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        setupDefaultToolbar();
        setupMovieFeedSpinner();
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
    // UI Setup functions
    ///////////////////////////////////////////////////////////////////////////
    private void setupDefaultToolbar() {
        setSupportActionBar(activityToolbar);
        // spinner needs to be setup
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
    }

    private void setupMovieFeedSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.movie_feeds, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        movieFeedSpinner.setAdapter(adapter);
        movieFeedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private boolean allowSelections = false;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                movieFeedType = position;
                if(allowSelections) {
                    fragment.setListingType(movieFeedType);
                    fragment.refreshList();
                } else {
                    allowSelections = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

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

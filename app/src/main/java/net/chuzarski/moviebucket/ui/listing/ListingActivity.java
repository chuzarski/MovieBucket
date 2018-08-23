package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.ui.detail.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListingActivity extends AppCompatActivity implements ListingFragment.ListingFragmentInteractor {

    public static final String FRAGMENT_KEY = "FRAG_LISTING";

    ListingActivityViewModel viewModel;

    ListingFragment fragment;
    @BindView(R.id.listing_activity_toolbar)
    Toolbar activityToolbar;
    @BindView(R.id.listing_feed_spinner)
    Spinner toolbarSpinner;

    Observer<Integer> feedStateObserver = feedType -> {
        if(fragment != null) {
            fragment.setListingType(feedType);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_roll);
        Timber.tag("ListingActivity");
        Timber.d("Activity Created");
        viewModel = ViewModelProviders.of(this).get(ListingActivityViewModel.class);

        ButterKnife.bind(this);
        setupDefaultToolbar();
        setupToolbarSpinner();

        if(savedInstanceState == null) {
            fragment = ListingFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_roll_fragment_frame, fragment).commit();
        } else {
            fragment = (ListingFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
        }

        viewModel.getListingFeedState().observe(this, feedStateObserver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, FRAGMENT_KEY, fragment);
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
            // TODO This needs to be refactored.. Talk about memory usage?!
            // Wayyy too much object creation
            case R.id.action_set_listing_upcoming:
                setListingType(StaticValues.LISTING_TYPE_UPCOMING);
                return true;
            case R.id.action_set_listing_popular:
                setListingType(StaticValues.LISTING_TYPE_POPULAR);
                return true;
            case R.id.action_set_listing_top_rated:
                setListingType(StaticValues.LISTING_TYPE_TOP_RATED);
                return true;
            case R.id.action_set_listing_now_playing:
                setListingType(StaticValues.LISTING_TYPE_NOW_PLAYING);
                return true;
            case R.id.action_refresh:
                fragment.refreshList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setListingType(int listingType) {
        switch (listingType) {
            case StaticValues.LISTING_TYPE_UPCOMING:
                getSupportActionBar().setTitle(R.string.action_set_listing_upcoming);
                break;
            case StaticValues.LISTING_TYPE_POPULAR:
                getSupportActionBar().setTitle(R.string.action_set_listing_popular);
                break;
            case StaticValues.LISTING_TYPE_NOW_PLAYING:
                getSupportActionBar().setTitle(R.string.action_set_listing_now_playing);
                break;
            case StaticValues.LISTING_TYPE_TOP_RATED:
                getSupportActionBar().setTitle(R.string.action_set_listing_top_rated);
                break;
        }

        fragment.setListingType(listingType);
    }

    private void setupDefaultToolbar() {
        setSupportActionBar(activityToolbar);
        // spinner needs to be setup
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
    }

    private void setupToolbarSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.movie_feeds, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toolbarSpinner.setAdapter(adapter);
        toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setListingFeedState(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // listing fragment options
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

    public static class ListingActivityViewModel extends ViewModel {
        private MutableLiveData<Integer> listingFeedState;

        public ListingActivityViewModel() {
            listingFeedState = new MutableLiveData();
        }

        public LiveData<Integer> getListingFeedState() {
            return listingFeedState;
        }

        public void setListingFeedState(int val) {
            Timber.d("Posting value: %d", val);
            listingFeedState.postValue(val);
        }
    }
 }

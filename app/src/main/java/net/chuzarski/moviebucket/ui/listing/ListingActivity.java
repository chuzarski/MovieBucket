package net.chuzarski.moviebucket.ui.listing;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v4.widget.DrawerLayout.SimpleDrawerListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.chuzarski.moviebucket.BucketApplication;
import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.ui.detail.DetailActivity;
import net.chuzarski.moviebucket.ui.listing.ListingFragment.ListingFragmentInteractor;
import net.chuzarski.moviebucket.ui.listing.ListingPreferencesFragment.ListingPreferencesInteractor;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListingActivity extends AppCompatActivity implements ListingFragmentInteractor, ListingPreferencesInteractor {

    // constants
    public static final String FRAGMENT_STATE_KEY_NETWORK_LISTING = "netlist";
    public static final String FRAGMENT_STATE_KEY_LOCAL_LISTING = "locallist";
    public static final String FRAGMENT_STATE_KEY_LISTING_PREF = "pref";

    // UI Fragments
    ListingFragment networkListingFragment;
    ListingFragment localListingFragment;
    ListingPreferencesFragment listingPreferencesFragment;

    @BindView(R.id.listing_activity_toolbar)
    Toolbar activityToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    ///////////////////////////////////////////////////////////////////////////
    // Listeners
    ///////////////////////////////////////////////////////////////////////////
    DrawerListener drawerListener = new SimpleDrawerListener() {
        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
            // so when the drawer is open, we can close it naturally ;)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    };

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle callbacks
    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        initFragmentAutoInjection();

        ButterKnife.bind(this);
        if(savedInstanceState == null) {

            // fresh ui
            initUI();

            // fragments
            createAllFragments();
            attachFragmentsToFrames();

        } else {
            restoreFragmentReferences(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        saveFragmentReferences(outState);
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
                networkListingFragment.refreshList();
                return true;
            case R.id.action_search:
                onSearchRequested();
                return true;
            case R.id.action_sort:
                drawerLayout.openDrawer(Gravity.END);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // UI Functions
    ///////////////////////////////////////////////////////////////////////////

    private void initUI() {
        // deal with toolbar
        setSupportActionBar(activityToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        } else {
            Timber.w("Action bar is null? Strange behavior might occur");
        }

        // deal with nav drawer
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.addDrawerListener(drawerListener);

    }

    ///////////////////////////////////////////////////////////////////////////
    // Fragment Management
    ///////////////////////////////////////////////////////////////////////////
    private void initFragmentAutoInjection() {
        BucketApplication app;
        app = (BucketApplication) getApplication();

        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
                super.onFragmentAttached(fm, f, context);
                if(f instanceof ListingFragment) {
                    app.getAppComponentInjector().inject((ListingFragment) f);
                } else if (f instanceof ListingPreferencesFragment) {
                    app.getAppComponentInjector().inject((ListingPreferencesFragment) f);
                }
            }
        }, false);
    }

    private void createAllFragments() {
        networkListingFragment = new ListingFragment();
        listingPreferencesFragment = new ListingPreferencesFragment();
    }

    private void attachFragmentsToFrames() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.drawer_listing_preferences, listingPreferencesFragment)
                .add(R.id.listing_activity_network_listing_frame, networkListingFragment)
                .commit();

    }

    private void saveFragmentReferences(Bundle outState) {
        getSupportFragmentManager().putFragment(outState,
                FRAGMENT_STATE_KEY_NETWORK_LISTING, networkListingFragment);
        getSupportFragmentManager().putFragment(outState,
                FRAGMENT_STATE_KEY_LISTING_PREF, listingPreferencesFragment);
    }
    private void restoreFragmentReferences(Bundle savedInstanceState) {
        networkListingFragment = (ListingFragment) getSupportFragmentManager()
                .getFragment(savedInstanceState, FRAGMENT_STATE_KEY_NETWORK_LISTING);
        listingPreferencesFragment = (ListingPreferencesFragment) getSupportFragmentManager()
                .getFragment(savedInstanceState, FRAGMENT_STATE_KEY_LISTING_PREF);
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
    ///////////////////////////////////////////////////////////////////////////
    // ListingPreferencesInteractor implementation
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void closePreferences() {
        drawerLayout.closeDrawer(Gravity.END);
    }
}

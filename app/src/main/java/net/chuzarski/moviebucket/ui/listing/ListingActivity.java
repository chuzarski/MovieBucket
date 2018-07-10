package net.chuzarski.moviebucket.ui.listing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.common.TimeFrame;
import net.chuzarski.moviebucket.ui.detail.DetailActivity;

import org.threeten.bp.LocalDate;

import timber.log.Timber;

public class ListingActivity extends AppCompatActivity implements ListingFragment.ListingFragmentInteractor {

    ListingFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_roll);
        Timber.tag("ListingActivity");
        Timber.d("Activity Created");

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(savedInstanceState == null) {
            fragment = ListingFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_roll_fragment_frame, fragment).commit();
        }
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
            case R.id.action_timeframe_thisweek:
                fragment.setTimeframe(TimeFrame.thisWeek());
                return true;
            case R.id.action_timeframe_thismonth:
                fragment.setTimeframe(TimeFrame.thisMonth());
                return true;
            case R.id.action_timeframe_nextmonth:
                fragment.setTimeframe(TimeFrame.nextMonth());
                return true;
            case R.id.action_timeframe_nextthree:
                fragment.setTimeframe(TimeFrame.nextThreeMonths());
                return true;
            case R.id.action_refresh:
                fragment.refreshList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        detailActivityIntent.putExtra("movie_id", id);
        startActivity(detailActivityIntent);
    }


}

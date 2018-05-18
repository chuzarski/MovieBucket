package net.chuzarski.moviebucket.ui.listing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.chuzarski.moviebucket.R;

import timber.log.Timber;

public class ListingActivity extends AppCompatActivity implements ListingFragment.MovieRollFragmentInteractor {

    ListingFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_roll);
        Timber.tag("ListingActivity");
        Timber.d("Activity Created");

        fragment = ListingFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_roll_fragment_frame, fragment).commit();
    }
}

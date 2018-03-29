package net.chuzarski.moviebucket.ui.movieroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.chuzarski.moviebucket.R;

import timber.log.Timber;

public class MovieRollActivity extends AppCompatActivity implements MovieRollFragment.MovieRollFragmentInteractor {

    MovieRollFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_roll);
        Timber.tag("MovieRollActivity");
        Timber.d("Activity Created");

        fragment = MovieRollFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_movie_roll_fragment_frame, fragment).commit();
    }
}

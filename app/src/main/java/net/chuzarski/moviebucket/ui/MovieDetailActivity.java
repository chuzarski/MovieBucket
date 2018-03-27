package net.chuzarski.moviebucket.ui;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import net.chuzarski.moviebucket.R;

public class MovieDetailActivity extends FragmentActivity implements MovieDetailFragment.MovieDetailInteractor {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

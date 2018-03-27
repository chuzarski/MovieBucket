package net.chuzarski.moviebucket.ui.moviedetail;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import net.chuzarski.moviebucket.R;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailFragment.MovieDetailInteractor {

    private MovieDetailFragment fragment;
    private FrameLayout fragmentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        fragmentFrame = findViewById(R.id.activity_fragment_movie_detail);

        initFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void initFragment() {
        fragment = MovieDetailFragment.newInstance(263115);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_fragment_movie_detail, fragment).commit();
    }
}

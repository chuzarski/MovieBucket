package net.chuzarski.moviebucket.ui.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import net.chuzarski.moviebucket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity implements DetailFragment.MovieDetailInteractor {

    private DetailFragment fragment;

    @BindView(R.id.activity_fragment_movie_detail)
    public FrameLayout fragmentFrame;

    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        movieId = getIntent().getExtras().getInt("movie_id", -1);
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
        // TODO there has to be a better way
        if (movieId == -1) {
            Timber.e("A movie id was not passed to the activity, fragment will not be added");
            return;
        }


        fragment = DetailFragment.newInstance(movieId);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_fragment_movie_detail, fragment).commit();
    }
}

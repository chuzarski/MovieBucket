package net.chuzarski.moviebucket.ui.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.MovieImagePathHelper;
import net.chuzarski.moviebucket.common.StaticValues;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity implements DetailFragment.MovieDetailInteractor {

    private DetailFragment fragment;

    @BindView(R.id.movie_detail_activity_backdrop)
    ImageView backdropImage;
    @BindView(R.id.movie_detail_activity_toolbar)
    Toolbar toolbar;

    private int movieId;

    private final int DEFAULT_MOVIE = 135397; // Tomb Raider default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // todo default movie mechanism is for testing only. Need a better solution for release.
        if(getIntent().getExtras() != null) {
            movieId = getIntent().getExtras().getInt(StaticValues.BUNDLE_ATTR_MOVIE_ID, DEFAULT_MOVIE);
        } else {
            movieId = DEFAULT_MOVIE;
        }

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initFragment();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void addBackdropToActivity(String uri) {

        //todo uri needs to be prepped to probably load the correct size
        uri = MovieImagePathHelper.createURLForBackdrop(uri);
        Glide.with(this)
                .load(uri)
                .into(backdropImage);
    }

    private void initFragment() {

        fragment = DetailFragment.newInstance(movieId);
        getSupportFragmentManager().beginTransaction().add(R.id.movie_detail_activity_fragment_frame, fragment).commit();
    }
}

package net.chuzarski.moviebucket.ui.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import net.chuzarski.moviebucket.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailFragment.MovieDetailInteractor {

    private DetailFragment fragment;

    @BindView(R.id.activity_fragment_movie_detail)
    public FrameLayout fragmentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

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
        fragment = DetailFragment.newInstance(198663);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_fragment_movie_detail, fragment).commit();
    }
}

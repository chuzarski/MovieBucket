package net.chuzarski.moviebucket.ui.movielisting;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.viewmodels.MovieListingViewModel;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private MovieListingViewModel viewModel;
    private RecyclerView movieRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.tag("MainActivity");
        Timber.d("Activity Created");

        movieRecyclerView = findViewById(R.id.move_listing);
        viewModel = ViewModelProviders.of(this).get(MovieListingViewModel.class);

    }
}

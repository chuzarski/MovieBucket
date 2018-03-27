package net.chuzarski.moviebucket.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import net.chuzarski.moviebucket.network.NetworkState;
import net.chuzarski.moviebucket.network.models.MovieModel;
import net.chuzarski.moviebucket.repository.MovieRepository;

import timber.log.Timber;

/**
 * Created by tassit on 3/25/18.
 */

public class MovieDetailViewModel extends ViewModel {

    private LiveData<MovieModel> movieModel;
    private MovieRepository repo;
    private LiveData<NetworkState> networkState;
    private int movieId;

    public MovieDetailViewModel() {
        Timber.tag("MovieDetail Viewmodel");
        repo = MovieRepository.getInstance();
        networkState = repo.getNetworkState();
    }


    public void setMovieId(int id) {
        movieId = id;
    }

    public LiveData<MovieModel> getMovieModel() {

        if (movieModel == null) {
            movieModel = repo.getMovieById(movieId);
        }

        return movieModel;
    }
}

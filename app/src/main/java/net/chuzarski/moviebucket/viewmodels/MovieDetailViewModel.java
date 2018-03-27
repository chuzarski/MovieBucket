package net.chuzarski.moviebucket.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import net.chuzarski.moviebucket.network.NetworkState;
import net.chuzarski.moviebucket.network.models.DetailedMovieModel;
import net.chuzarski.moviebucket.repository.MovieDetailRepository;

import timber.log.Timber;


public class MovieDetailViewModel extends ViewModel {

    private LiveData<DetailedMovieModel> movieModel;
    private MovieDetailRepository repo;
    private LiveData<NetworkState> networkState;
    private int movieId;

    public MovieDetailViewModel() {
        Timber.tag("MovieDetail Viewmodel");
        repo = new MovieDetailRepository();
        networkState = repo.getNetworkState();
    }


    public void setMovieId(int id) {
        movieId = id;
    }

    public LiveData<NetworkState> getNetworkState() { return networkState; }

    public LiveData<DetailedMovieModel> getMovieModel() {
        if (movieModel == null) {
            movieModel = repo.getMovieById(movieId);
        }

        return movieModel;
    }
}

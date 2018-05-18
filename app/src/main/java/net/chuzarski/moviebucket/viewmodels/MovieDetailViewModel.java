package net.chuzarski.moviebucket.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import net.chuzarski.moviebucket.network.NetworkState;
import net.chuzarski.moviebucket.models.CollectionModel;
import net.chuzarski.moviebucket.models.DetailedMovieModel;
import net.chuzarski.moviebucket.repository.MovieDetailRepository;

import timber.log.Timber;


public class MovieDetailViewModel extends ViewModel {

    private LiveData<DetailedMovieModel> movieModel;
    private LiveData<CollectionModel> movieCollection;
    private MovieDetailRepository repo;
    private LiveData<NetworkState> networkState;

    public MovieDetailViewModel() {
        Timber.tag("MovieDetail Viewmodel");
        repo = new MovieDetailRepository();
        networkState = repo.getNetworkState();
    }

    public LiveData<NetworkState> getNetworkState() { return networkState; }

    public LiveData<DetailedMovieModel> getMovieModel(int movieId) {
        if (movieModel == null ) {
            movieModel =  repo.getMovieById(movieId);
        }

        return movieModel;
    }
}

package net.chuzarski.moviebucket.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import net.chuzarski.moviebucket.network.NetworkState;
import net.chuzarski.moviebucket.models.CollectionModel;
import net.chuzarski.moviebucket.models.DetailedMovieModel;
import net.chuzarski.moviebucket.repository.DetailRepository;

import timber.log.Timber;


public class DetailViewModel extends ViewModel {

    private LiveData<DetailedMovieModel> movieModel;
    private LiveData<CollectionModel> movieCollection;
    private DetailRepository repo;
    private LiveData<NetworkState> networkState;

    public DetailViewModel() {
        Timber.tag("MovieDetail Viewmodel");
        repo = new DetailRepository();
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

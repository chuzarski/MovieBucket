package net.chuzarski.moviebucket.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;
import net.chuzarski.moviebucket.network.NetworkState;
import net.chuzarski.moviebucket.network.models.MovieModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by cody on 3/21/18.
 */

public class MovieRepository {

    private MovieNetworkService movieNetworkService;
    private MutableLiveData<NetworkState> networkState;
    private static MovieRepository instance;

    private MovieRepository() {
        Timber.tag("MovieRepository");
        movieNetworkService = MovieNetworkServiceFactory.create();
        networkState = new MutableLiveData<>();
        networkState.postValue(NetworkState.FRESH);
    }

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }

        return instance;
    }

    public LiveData<NetworkState> getNetworkState() {

        return networkState;
    }

    public LiveData<MovieModel> getMovieById(int id) {
        final MutableLiveData<MovieModel> model = new MutableLiveData<>();

        networkState.postValue(NetworkState.LOADING);
        movieNetworkService.getMovieById(id).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                model.postValue(response.body());
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                networkState.postValue(NetworkState.FAILED);
                Timber.d("Failed with fetching movie by ID");
            }
        });

        return model;
    }




}

package net.chuzarski.moviebucket.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import net.chuzarski.moviebucket.common.ServiceHolder;
import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;
import net.chuzarski.moviebucket.common.LoadState;
import net.chuzarski.moviebucket.models.DetailedMovieModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DetailRepository {

    private MovieNetworkService movieNetworkService;
    private MutableLiveData<LoadState> networkState;

    public DetailRepository() {
        Timber.tag("DetailRepository");
        movieNetworkService = ServiceHolder.getInstance().getNetworkService();
        networkState = new MutableLiveData<>();
        networkState.postValue(LoadState.LOADING);
    }

    public LiveData<LoadState> getNetworkState() {
        return networkState;
    }

    /**
     * As method states, this loads information on a movie
     * Currently this loads information on a movie from NETWORK ONLY
     * TODO Caching of movie results can be put here
     * @param id
     * @return
     */
    public LiveData<DetailedMovieModel> getMovieById(int id) {
        final MutableLiveData<DetailedMovieModel> model = new MutableLiveData<>();

        networkState.postValue(LoadState.LOADING);
        movieNetworkService.getMovieDetail(id).enqueue(new Callback<DetailedMovieModel>() {
            @Override
            public void onResponse(Call<DetailedMovieModel> call, Response<DetailedMovieModel> response) {
                model.postValue(response.body());
                networkState.postValue(LoadState.LOADED);
            }

            @Override
            public void onFailure(Call<DetailedMovieModel> call, Throwable t) {
                networkState.postValue(LoadState.NETWORK_FAILED);
                Timber.d("Failed with fetching movie by ID");
            }
        });

        return model;
    }




}

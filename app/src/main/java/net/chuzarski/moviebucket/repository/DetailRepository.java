package net.chuzarski.moviebucket.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import net.chuzarski.moviebucket.network.NetworkService;
import net.chuzarski.moviebucket.common.LoadState;
import net.chuzarski.moviebucket.models.DetailedMovieModel;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DetailRepository {

    private NetworkService networkService;
    private MutableLiveData<LoadState> networkState;

    @Inject
    public DetailRepository(NetworkService networkService) {
        Timber.tag("DetailRepository");
        this.networkService = networkService;
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
        networkService.getMovieDetail(id).enqueue(new Callback<DetailedMovieModel>() {
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

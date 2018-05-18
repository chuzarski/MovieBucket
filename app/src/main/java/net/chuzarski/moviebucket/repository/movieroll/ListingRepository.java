package net.chuzarski.moviebucket.repository.movieroll;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;
import net.chuzarski.moviebucket.network.NetworkState;
import net.chuzarski.moviebucket.network.UpcomingMoviesParams;
import net.chuzarski.moviebucket.models.MovieListingItemModel;

/**
 * Created by cody on 3/27/18.
 */

public class ListingRepository {

    private MutableLiveData<NetworkState> networkState;
    private MovieNetworkService networkService;

    public ListingRepository() {
        networkState = new MutableLiveData<>();
        networkState.postValue(NetworkState.LOADING);

        networkService = MovieNetworkServiceFactory.getInstance();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<MovieListingItemModel>> getMovieListing(UpcomingMoviesParams requestParams) {
        ListingDataSourceFactory factory;
        DataSource<Integer, MovieListingItemModel> ds;

        factory = new ListingDataSourceFactory(networkService, requestParams);

        return new LivePagedListBuilder(factory, 1).build();
    }
}

package net.chuzarski.moviebucket.repository.movieroll;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.network.UpcomingMoviesParams;
import net.chuzarski.moviebucket.models.MovieListingItemModel;

/**
 * Created by cody on 3/27/18.
 */

public class MovieRollDataSourceFactory extends DataSource.Factory<Integer, MovieListingItemModel> {

    private MovieNetworkService networkService;
    private UpcomingMoviesParams requestParams;
    private MutableLiveData<MovieRollDataSource> source;

    public MovieRollDataSourceFactory(MovieNetworkService service, UpcomingMoviesParams requestParams) {
        networkService = service;
        this.requestParams = requestParams;
        source = new MutableLiveData<>();
    }
    @Override
    public DataSource<Integer, MovieListingItemModel> create() {
        MovieRollDataSource ds = new MovieRollDataSource(networkService, requestParams);
        source.postValue(ds);
        return ds;
    }

    public LiveData<MovieRollDataSource> getDataSource() {
        return source;
    }
}
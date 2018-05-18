package net.chuzarski.moviebucket.repository.movieroll;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.network.UpcomingMoviesParams;
import net.chuzarski.moviebucket.models.DiscoverModel;
import net.chuzarski.moviebucket.models.MovieListingItemModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by cody on 3/27/18.
 */

public class MovieRollDataSource extends PageKeyedDataSource<Integer, MovieListingItemModel>  {

    private MovieNetworkService service;
    private UpcomingMoviesParams requestParams;

    public MovieRollDataSource(MovieNetworkService service, UpcomingMoviesParams requestParams) {
        this.service = service;
        this.requestParams = requestParams;
        Timber.tag("MovieRollDataSource");
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MovieListingItemModel> callback) {
        service.getUpcomingMovies(requestParams.getReleaseDateRangeFrom(),
                requestParams.getReleaseDateRangeTo(),
                requestParams.getLanguage(),
                requestParams.getRegion(),
                1).enqueue(new Callback<DiscoverModel>() {
            @Override
            public void onResponse(Call<DiscoverModel> call, Response<DiscoverModel> response) {
                DiscoverModel responseModel = response.body();
                // figure out some stuff here
                // TODO Refactor initial load for MovieRollDataSource
                callback.onResult(responseModel.getMovieListing(), null, (responseModel.getPage() + 1));
            }

            @Override
            public void onFailure(Call<DiscoverModel> call, Throwable t) {
                // TODO handle error / retry case here
                Timber.e("Failed to retrieve MovieListing");
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieListingItemModel> callback) {
        return; // This never happens
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieListingItemModel> callback) {

        service.getUpcomingMovies(requestParams.getReleaseDateRangeFrom(),
                requestParams.getReleaseDateRangeTo(),
                requestParams.getLanguage(),
                requestParams.getRegion(),
                params.key).enqueue(new Callback<DiscoverModel>() {
            @Override
            public void onResponse(Call<DiscoverModel> call, Response<DiscoverModel> response) {
                DiscoverModel responseModel = response.body();
                // figure out some stuff here
                // TODO Refactor additional load for MovieRollDataSource
                Timber.d("Saying hello from MovieRollDataSource, we got some data");
                callback.onResult(responseModel.getMovieListing(), (responseModel.getPage() + 1));
            }

            @Override
            public void onFailure(Call<DiscoverModel> call, Throwable t) {
                // TODO handle error / retry case here
            }
        });
    }
}

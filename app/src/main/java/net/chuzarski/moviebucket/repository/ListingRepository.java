package net.chuzarski.moviebucket.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.paging.PagedList.Config;
import android.support.annotation.NonNull;

import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.db.listing.ListingCacheDb;
import net.chuzarski.moviebucket.models.DiscoverModel;
import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.common.LoadState;
import net.chuzarski.moviebucket.network.ListingNetworkRequestParams;
import net.chuzarski.moviebucket.models.ListingItemModel;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cody on 3/27/18.
 */

public class ListingRepository {

    private MutableLiveData<LoadState> loadState;
    private MovieNetworkService networkService;
    private ListingCacheDb db;
    private Executor ioExectuor;

    public ListingRepository(MovieNetworkService networkService, ListingCacheDb db, Executor ioExecutor) {
        loadState = new MutableLiveData<>();
        loadState.postValue(LoadState.LOADING);

        this.networkService = networkService;
        this.db = db;
        this.ioExectuor = ioExecutor;
    }

    public LiveData<LoadState> getLoadState() {
        return loadState;
    }

    public LiveData<PagedList<ListingItemModel>> getMovieListing(ListingNetworkRequestParams requestParams) {
        Config listConfig = new Config.Builder()
                .setPageSize(StaticValues.listingPageSize)
                .setPrefetchDistance(StaticValues.listingPrefetchDistance)
                .build();

        return new LivePagedListBuilder<Integer, ListingItemModel>(db.listingDao().getAllDataSource(), listConfig)
                .setFetchExecutor(ioExectuor)
                .setBoundaryCallback(new ListBoundaryNetworkLoader(requestParams))
                .build();
    }

    public void refresh() {
        ioExectuor.execute(() -> {
            db.clearAllTables();
        });
    }

    private void asyncInsertAllIntoListingCache(List<ListingItemModel> models) {
        ioExectuor.execute(() -> {
            db.listingDao().insertAll(models);
        });
    }

    private class ListBoundaryNetworkLoader extends PagedList.BoundaryCallback<ListingItemModel> {
        private int currentPage = 0;
        private int totalPages = 0;
        private ListingNetworkRequestParams requestParams;

        ListBoundaryNetworkLoader(ListingNetworkRequestParams params) {
            this.requestParams = params;
        }

        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            loadState.postValue(LoadState.LOADING);
            networkService.getUpcomingMovies(requestParams.getReleaseDateRangeFrom(),
                    requestParams.getReleaseDateRangeTo(),
                    requestParams.getLanguage(),
                    requestParams.getRegion(), 1)
                    .enqueue(new Callback<DiscoverModel>() {
                        @Override
                        public void onResponse(Call<DiscoverModel> call, Response<DiscoverModel> response) {
                            asyncInsertAllIntoListingCache(response.body().getMovieListing());
                            totalPages = response.body().getNumPages();
                            loadState.postValue(LoadState.LOADED);
                            currentPage++;
                        }

                        @Override
                        public void onFailure(Call<DiscoverModel> call, Throwable t) {

                        }
                    });
        }
        @Override
        public void onItemAtFrontLoaded(@NonNull ListingItemModel itemAtFront) {
            super.onItemAtFrontLoaded(itemAtFront);
        }

        @Override
        public void onItemAtEndLoaded(@NonNull ListingItemModel itemAtEnd) {
            super.onItemAtEndLoaded(itemAtEnd);

            if(currentPage + 1 <= totalPages) {
                loadState.postValue(LoadState.LOADING);
                networkService.getUpcomingMovies(requestParams.getReleaseDateRangeFrom(),
                        requestParams.getReleaseDateRangeTo(),
                        requestParams.getLanguage(),
                        requestParams.getRegion(), ++currentPage)
                        .enqueue(new Callback<DiscoverModel>() {
                            @Override
                            public void onResponse(Call<DiscoverModel> call, Response<DiscoverModel> response) {
                                asyncInsertAllIntoListingCache(response.body().getMovieListing());
                                loadState.postValue(LoadState.LOADED);
                                currentPage++;
                            }

                            @Override
                            public void onFailure(Call<DiscoverModel> call, Throwable t) {

                            }
                        });
            }
        }
    }
}

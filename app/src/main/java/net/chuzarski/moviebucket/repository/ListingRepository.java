package net.chuzarski.moviebucket.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.paging.PagedList.Config;
import android.support.annotation.NonNull;

import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.db.listing.ListingCacheDb;
import net.chuzarski.moviebucket.models.ListingResponseModel;
import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.common.LoadState;
import net.chuzarski.moviebucket.models.ListingItemModel;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by cody on 3/27/18.
 */

public class ListingRepository {

    private MutableLiveData<LoadState> loadState;
    private MovieNetworkService networkService;
    private ListingCacheDb db;
    private Executor ioExectuor;
    private ListingBoundaryNetworkLoader networkLoader;

    private int movieListingType = StaticValues.LISTING_TYPE_UPCOMING; // hard default

    public ListingRepository(MovieNetworkService networkService, ListingCacheDb db, Executor ioExecutor) {
        Timber.tag("ListingRepository");

        loadState = new MutableLiveData<>();
        loadState.postValue(LoadState.LOADING);

        this.networkService = networkService;
        this.db = db;
        this.ioExectuor = ioExecutor;

        // todo fix isoLanguage and isoRegion defaults
        networkLoader = new ListingBoundaryNetworkLoader("en", "US");

        Timber.d("Ready to work");
    }

    public LiveData<LoadState> getLoadState() {
        return loadState;
    }

    public LiveData<PagedList<ListingItemModel>> getMovieListing() {
        Config listConfig = new Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(StaticValues.listingPageSize)
                .setPrefetchDistance(StaticValues.listingPrefetchDistance)
                .build();

        return new LivePagedListBuilder<Integer, ListingItemModel>(db.listingDao().getAllDataSource(), listConfig)
                .setFetchExecutor(ioExectuor)
                .setBoundaryCallback(networkLoader)
                .build();
    }

    public void setListingType(int type) {
        movieListingType = type;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Database Operations
    ///////////////////////////////////////////////////////////////////////////
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

    ///////////////////////////////////////////////////////////////////////////
    // Internal Classes
    ///////////////////////////////////////////////////////////////////////////
    private class ListingBoundaryNetworkLoader extends PagedList.BoundaryCallback<ListingItemModel> {
        private int page = 0;
        private int totalPages = 0;
        private String language;
        private String region;

        private Callback<ListingResponseModel> responseCallback =
                new Callback<ListingResponseModel>() {
            @Override
            public void onResponse(Call<ListingResponseModel> call, Response<ListingResponseModel> response) {
                asyncInsertAllIntoListingCache(response.body().getItems());
                totalPages = response.body().getTotalPages();
                page = response.body().getPage();
                loadState.postValue(LoadState.LOADED);
            }

            @Override
            public void onFailure(Call<ListingResponseModel> call, Throwable t) {
                Timber.w("We had a loading failure");
            }
        };

        /**
         * @param isoLanguage language specifier in ISO 639-1 format
         * @param isoRegion region specifier in ISO 3166-1 format
         */
        public ListingBoundaryNetworkLoader(String isoLanguage, String isoRegion) {
            language = isoLanguage;
            region = isoRegion;
        }

        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            loadState.postValue(LoadState.LOADING);
            page = 1;
            dispatchLoadingMethod();
        }

        @Override
        public void onItemAtEndLoaded(@NonNull ListingItemModel itemAtEnd) {
            super.onItemAtEndLoaded(itemAtEnd);
            if((page + 1 < totalPages)) {
                page++;
                dispatchLoadingMethod();
            }
        }

        // unused
        @Override
        public void onItemAtFrontLoaded(@NonNull ListingItemModel itemAtFront) {
            super.onItemAtFrontLoaded(itemAtFront);
        }

        private void dispatchLoadingMethod() {
            switch (movieListingType) {
                case StaticValues.LISTING_TYPE_UPCOMING:
                    networkService.getUpcomingListing(language, region, page).enqueue(responseCallback);
                    break;
                case StaticValues.LISTING_TYPE_POPULAR:
                    networkService.getPopularListing(language, region, page).enqueue(responseCallback);
                    break;
                case StaticValues.LISTING_TYPE_NOW_PLAYING:
                    networkService.getNowPlayingListing(language, region, page).enqueue(responseCallback);
                    break;
                case StaticValues.LISTING_TYPE_TOP_RATED:
                    networkService.getTopRatedListing(language, region, page).enqueue(responseCallback);
                    break;
            }
        }

        public int getPage() {
            return page;
        }

        public int getTotalPages() {
            return totalPages;
        }
    }
}

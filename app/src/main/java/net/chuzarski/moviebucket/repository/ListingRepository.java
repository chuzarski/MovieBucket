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
import net.chuzarski.moviebucket.models.ListingResponseModel;
import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.common.LoadState;
import net.chuzarski.moviebucket.network.ListingNetworkRequestConfig;
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
    private ListingNetworkRequestConfig networkRequestConfig;
    private ListingNetworkBoundaryLoader methodedLoader;

    public ListingRepository(MovieNetworkService networkService, ListingCacheDb db, Executor ioExecutor, ListingNetworkRequestConfig networkRequestConfig) {
        Timber.tag("ListingRepository");

        loadState = new MutableLiveData<>();
        loadState.postValue(LoadState.LOADING);

        this.networkService = networkService;
        this.db = db;
        this.ioExectuor = ioExecutor;
        this.networkRequestConfig = networkRequestConfig;


        // This makes some assumptions, for now this will work.
        // todo fix this
        methodedLoader = new ListingNetworkBoundaryLoader("en", "US", StaticValues.LISTING_TYPE_UPCOMING);
    }

    public LiveData<LoadState> getLoadState() {
        return loadState;
    }

    public LiveData<PagedList<ListingItemModel>> getMovieListing() {
        Config listConfig = new Config.Builder()
                .setPageSize(StaticValues.listingPageSize)
                .setPrefetchDistance(StaticValues.listingPrefetchDistance)
                .build();

        return new LivePagedListBuilder<Integer, ListingItemModel>(db.listingDao().getAllDataSource(), listConfig)
                .setFetchExecutor(ioExectuor)
                .setBoundaryCallback(methodedLoader)
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

    public void setNetworkListingType(int meth) {
        methodedLoader.setListingType(meth);
        refresh(); // trigger refresh
    }

    private class ListingNetworkBoundaryLoader extends PagedList.BoundaryCallback<ListingItemModel> {
        private int page = 0;
        private int totalPages = 0;

        private String language;
        private String region;
        private int listingType;

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

            }
        };

        /**
         * @param isoLanguage language specifier in ISO 639-1 format
         * @param isoRegion region specifier in ISO 3166-1 format
         * @param listingType Method for how this class is going to load data, see StaticValues.java
         */
        public ListingNetworkBoundaryLoader(String isoLanguage, String isoRegion, int listingType) {
            language = isoLanguage;
            region = isoRegion;
            this.listingType = listingType;
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

            if(page + 1 > totalPages) {
                Timber.d("End of page set reached");
                return;
            } else {
                page++;
                Timber.d("Requesting page %d of %d", page, totalPages);
            }

            dispatchLoadingMethod();
        }

        // unused
        @Override
        public void onItemAtFrontLoaded(@NonNull ListingItemModel itemAtFront) {
            super.onItemAtFrontLoaded(itemAtFront);
        }

        private void dispatchLoadingMethod() {
            switch (listingType) {
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

        public void setListingType(int type) {
            listingType = type;
        }

        public int getPage() {
            return page;
        }

        public int getTotalPages() {
            return totalPages;
        }
    }

    private class ListBoundaryNetworkLoader extends PagedList.BoundaryCallback<ListingItemModel> {
        private int currentPage = 0;
        private int totalPages = 0;
        private ListingNetworkRequestConfig requestConfig;

        ListBoundaryNetworkLoader(ListingNetworkRequestConfig requestConfig) {
            this.requestConfig = requestConfig;
        }

        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            loadState.postValue(LoadState.LOADING);
            networkService.getUpcomingMovies(requestConfig.getReleaseDateRangeFrom(),
                    requestConfig.getReleaseDateRangeTo(),
                    requestConfig.getLanguage(),
                    requestConfig.getRegion(), 1)
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
                networkService.getUpcomingMovies(requestConfig.getReleaseDateRangeFrom(),
                        requestConfig.getReleaseDateRangeTo(),
                        requestConfig.getLanguage(),
                        requestConfig.getRegion(), ++currentPage)
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

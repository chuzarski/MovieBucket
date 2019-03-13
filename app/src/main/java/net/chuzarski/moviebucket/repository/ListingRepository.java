package net.chuzarski.moviebucket.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.paging.PagedList.Config;
import android.support.annotation.NonNull;

import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.db.ApplicationDatabase;
import net.chuzarski.moviebucket.models.ListingResponseModel;
import net.chuzarski.moviebucket.network.NetworkService;
import net.chuzarski.moviebucket.models.ListingItemModel;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class ListingRepository {

    private MutableLiveData<Integer> loadState;
    private NetworkService networkService;
    private ApplicationDatabase db;
    private Executor ioExectuor;

    private Config listConfig = new Config.Builder()
            .setPageSize(StaticValues.listingPageSize)
            .setPrefetchDistance(StaticValues.listingPrefetchDistance)
            .build();

    @Inject
    public ListingRepository(NetworkService networkService,
                             ApplicationDatabase db,
                             @Named("ioExecutor") Executor ioExecutor) {

        loadState = new MutableLiveData<>();
        loadState.postValue(StaticValues.LOAD_STATE_LOADING);

        this.networkService = networkService;
        this.db = db;
        this.ioExectuor = ioExecutor;
    }

    public LiveData<Integer> getLoadState() {
        return loadState;
    }

    public LiveData<PagedList<ListingItemModel>> getListing(Map<String, String> listingConfiguration) {
        return new LivePagedListBuilder<Integer, ListingItemModel>(db.listingDao().getAllDataSource(), listConfig)
                .setFetchExecutor(ioExectuor)
                .setBoundaryCallback(new ListingBoundaryNetworkLoader(listingConfiguration)) // todo update this for Map
                .build();
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
        private Map<String, String> config;


        private Callback<ListingResponseModel> responseCallback =
                new Callback<ListingResponseModel>() {
            @Override
            public void onResponse(Call<ListingResponseModel> call, Response<ListingResponseModel> response) {
                asyncInsertAllIntoListingCache(response.body().getItems());
                totalPages = response.body().getTotalPages();
                page = response.body().getPage();
                loadState.postValue(StaticValues.LOAD_STATE_LOADING);
            }

            @Override
            public void onFailure(Call<ListingResponseModel> call, Throwable t) {
                Timber.w("We had a loading failure");
                loadState.postValue(StaticValues.LOAD_STATE_FAILED);
            }
        };

        ListingBoundaryNetworkLoader(Map<String, String> loadConfiguration) {
            this.config = loadConfiguration;
        }


        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            loadState.postValue(StaticValues.LOAD_STATE_LOADING);
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
            switch (Integer.parseInt(config.get(StaticValues.KEY_LISTING_REPO_LOAD_SOURCE))) {
                case StaticValues.INTERNET_LISTING_UPCOMING:
                    networkService.getUpcomingListing(config.get("ISO_LANG"),
                            config.get("ISO_REGION"), page)
                            .enqueue(responseCallback);
                    break;
                case StaticValues.INTERNET_LISTING_POPULAR:
                    networkService.getPopularListing(config.get("ISO_LANG"),
                            config.get("ISO_REGION"), page)
                            .enqueue(responseCallback);
                    break;
                case StaticValues.INTERNET_LISTING_NOW_PLAYING:
                    networkService.getNowPlayingListing(config.get("ISO_LANG"),
                            config.get("ISO_REGION"), page)
                            .enqueue(responseCallback);
                    break;
                case StaticValues.INTERNET_LISTING_TOP_RATED:
                    networkService.getTopRatedListing(config.get("ISO_LANG"),
                            config.get("ISO_REGION"), page)
                            .enqueue(responseCallback);
                    break;
                case StaticValues.INTERNET_LISTING_SEARCH:
                    networkService.getSearchListing(config.get("ISO_LANG"),
                            config.get("ISO_LANG"),
                            config.get("ISO_REGION"),
                            page)
                            .enqueue(responseCallback);
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

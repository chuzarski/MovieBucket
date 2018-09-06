package net.chuzarski.moviebucket.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import net.chuzarski.moviebucket.BucketApplication;
import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.db.listing.ListingCacheDb;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;
import net.chuzarski.moviebucket.network.ListingNetworkRequestConfig;
import net.chuzarski.moviebucket.models.ListingItemModel;
import net.chuzarski.moviebucket.repository.ListingRepository;

import timber.log.Timber;

/**
 * Created by cody on 3/21/18.
 */

public class ListingViewModel extends AndroidViewModel {

    private LiveData<PagedList<ListingItemModel>> pagedListing;
    private ListingRepository repo;
    private int listingType = StaticValues.LISTING_TYPE_FRESH; // hard default
    private int currentListingType = listingType;

    public ListingViewModel(@NonNull Application app) {
        super(app);

        repo = new ListingRepository(MovieNetworkServiceFactory.getInstance(),
                Room.inMemoryDatabaseBuilder(app.getApplicationContext(), ListingCacheDb.class)
                        .build(),
                BucketApplication.getIoExectuor());

    }

    public void setFeed(int feedType) {
        repo.setFeed(feedType);
    }

    public void refresh() {
        Timber.d("Triggering refresh..");
        repo.refresh();
    }

    public void setListingType(int listingType) {
        this.listingType = listingType;
    }

    public LiveData<PagedList<ListingItemModel>> getPagedListing() {
        if(pagedListing == null) {
          pagedListing = repo.getFreshListing();
        }

        return pagedListing;
    }

    public LiveData<Integer> getLoadState() {
        return repo.getLoadState();
    }

}

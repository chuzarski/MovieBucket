package net.chuzarski.moviebucket.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import net.chuzarski.moviebucket.BucketApplication;
import net.chuzarski.moviebucket.db.listing.ListingCacheDb;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;
import net.chuzarski.moviebucket.common.LoadState;
import net.chuzarski.moviebucket.network.ListingNetworkRequestParams;
import net.chuzarski.moviebucket.models.ListingItemModel;
import net.chuzarski.moviebucket.repository.ListingRepository;

import org.threeten.bp.LocalDate;

import timber.log.Timber;

/**
 * Created by cody on 3/21/18.
 */

public class ListingViewModel extends AndroidViewModel {

    private LiveData<PagedList<ListingItemModel>> movieList;
    private ListingRepository repo;
    private ListingNetworkRequestParams requestParams;

    public ListingViewModel(@NonNull Application app) {
        super(app);

        repo = new ListingRepository(MovieNetworkServiceFactory.getInstance(),
                Room.inMemoryDatabaseBuilder(app.getApplicationContext(), ListingCacheDb.class)
                        .build(),
                BucketApplication.getIoExectuor()
                );

    }

    public void setRequestParams(@NonNull  ListingNetworkRequestParams request) {
        requestParams = request;
    }

    public void initRequestParams(@NonNull ListingNetworkRequestParams request) {
        if (requestParams != null) {
            return;
        }

        requestParams = request;
    }

    public ListingNetworkRequestParams getRequestParams() {
        return requestParams;
    }

    public void updateTimeFrame(LocalDate from, LocalDate to) {
        Timber.d("Updating request time frame");
        requestParams.setReleaseDateRangeFrom(from.toString());
        requestParams.setReleaseDateRangeTo(to.toString());

        refresh();
    }

    public void refresh() {
        Timber.d("Triggering refresh..");
        repo.refresh();
    }


    public LiveData<PagedList<ListingItemModel>> getMovieList() {
        if(movieList == null) {
            movieList = repo.getMovieListing(requestParams);
        }

        return movieList;
    }

    public LiveData<LoadState> getLoadState() {
        return repo.getLoadState();
    }
}

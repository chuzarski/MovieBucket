package net.chuzarski.moviebucket.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import net.chuzarski.moviebucket.BucketApplication;
import net.chuzarski.moviebucket.common.TimeFrame;
import net.chuzarski.moviebucket.db.listing.ListingCacheDb;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;
import net.chuzarski.moviebucket.common.LoadState;
import net.chuzarski.moviebucket.network.ListingNetworkRequestConfig;
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
    private ListingNetworkRequestConfig networkRequestConfig;

    public ListingViewModel(@NonNull Application app) {
        super(app);

        TimeFrame defaultTimeframe = TimeFrame.thisWeek();

        networkRequestConfig = new ListingNetworkRequestConfig.Builder(defaultTimeframe.getFrom().toString(),
                defaultTimeframe.getTo().toString())
                .build();

        repo = new ListingRepository(MovieNetworkServiceFactory.getInstance(),
                Room.inMemoryDatabaseBuilder(app.getApplicationContext(), ListingCacheDb.class)
                        .build(),
                BucketApplication.getIoExectuor(),
                networkRequestConfig);

    }

    public void setNetworkRequestConfig(@NonNull ListingNetworkRequestConfig request) {
        networkRequestConfig = request;
    }

    public ListingNetworkRequestConfig getNetworkRequestConfig() {
        return networkRequestConfig;
    }

    public void updateTimeFrame(LocalDate from, LocalDate to) {
        Timber.d("Updating request time frame");
        networkRequestConfig.setReleaseDateRangeFrom(from.toString());
        networkRequestConfig.setReleaseDateRangeTo(to.toString());

        refresh();
    }

    public void refresh() {
        Timber.d("Triggering refresh..");
        repo.refresh();
    }


    public LiveData<PagedList<ListingItemModel>> getMovieList() {
        if(movieList == null) {
            movieList = repo.getMovieListing();
        }

        return movieList;
    }

    public LiveData<LoadState> getLoadState() {
        return repo.getLoadState();
    }
}

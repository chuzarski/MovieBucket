package net.chuzarski.moviebucket.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import net.chuzarski.moviebucket.network.UpcomingMoviesParams;
import net.chuzarski.moviebucket.models.MovieListingItemModel;
import net.chuzarski.moviebucket.repository.movieroll.ListingRepository;

/**
 * Created by cody on 3/21/18.
 */

public class ListingViewModel extends ViewModel {

    private LiveData<PagedList<MovieListingItemModel>> movieList;
    private ListingRepository repo;
    private UpcomingMoviesParams requestParams;


    public ListingViewModel() {
        repo = new ListingRepository();
    }

    public void setRequestParams(UpcomingMoviesParams request) {
        requestParams = request;
    }

    public LiveData<PagedList<MovieListingItemModel>> getMovieList() {
        if(movieList == null) {
            // TODO This is unsafe because we might not have params, definitely refactor
            movieList = repo.getMovieListing(requestParams);
        }

        return movieList;
    }
}

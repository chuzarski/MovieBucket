package net.chuzarski.moviebucket.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import net.chuzarski.moviebucket.network.models.MovieModel;

/**
 * Created by cody on 3/21/18.
 */

public class MovieListingViewModel extends ViewModel {

    LiveData<PagedList<MovieModel>> movieList;

    public MovieListingViewModel() {
    }

    public LiveData<PagedList<MovieModel>> getMovieList() {
        return movieList;
    }
}

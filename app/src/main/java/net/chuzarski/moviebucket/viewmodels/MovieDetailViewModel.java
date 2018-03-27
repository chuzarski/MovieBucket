package net.chuzarski.moviebucket.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import net.chuzarski.moviebucket.network.models.MovieModel;
import net.chuzarski.moviebucket.repository.MovieRepository;

/**
 * Created by tassit on 3/25/18.
 */

public class MovieDetailViewModel extends ViewModel {

    private LiveData<MovieModel> movieModel;
    private MovieRepository repo;

    public MovieDetailViewModel() {
        repo = MovieRepository.getInstance();
    }


    public LiveData<MovieModel> getMovieModel() {

        // ensure that the model is set!
        return movieModel;
    }
}

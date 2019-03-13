package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import net.chuzarski.moviebucket.models.GenreModel;
import net.chuzarski.moviebucket.models.RatingModel;
import net.chuzarski.moviebucket.repository.ListingPreferencesRepository;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class ListingPreferencesViewModel extends ViewModel {
    private ListingPreferencesRepository repo;

    // requires loaded data
    private LiveData<List<GenreModel>> genreList;
    private LiveData<List<RatingModel>> ratingsList;

    // does not require loaded data
    private String sortBy;
    private Set<Integer> years; // if empty: Any, if one: primary_release, if multiple: gte SMALLEST and lte LARGEST

    @Inject
    public ListingPreferencesViewModel(ListingPreferencesRepository repo) {
        this.repo = repo;
    }

    public LiveData<List<GenreModel>> getGenres() {
        if (genreList == null) {
            genreList = repo.getGenres();
        }
        return genreList;
    }

    public LiveData<List<RatingModel>> getRatings() {
        if (ratingsList == null) {
            ratingsList = repo.getRatings();
        }
        return ratingsList;
    }
}

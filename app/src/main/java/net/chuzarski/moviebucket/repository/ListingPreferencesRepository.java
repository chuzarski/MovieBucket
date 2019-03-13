package net.chuzarski.moviebucket.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import net.chuzarski.moviebucket.db.GenreDao;
import net.chuzarski.moviebucket.db.RatingDao;
import net.chuzarski.moviebucket.models.GenreModel;
import net.chuzarski.moviebucket.models.GenreResponseModel;
import net.chuzarski.moviebucket.models.RatingModel;
import net.chuzarski.moviebucket.models.RatingResponseModel;
import net.chuzarski.moviebucket.network.NetworkService;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@Singleton
public class ListingPreferencesRepository {

    NetworkService network;
    Executor ioExecutor;
    GenreDao genreDao;
    RatingDao ratingDao;

    @Inject
    public ListingPreferencesRepository(NetworkService network,
                                        @Named("ioExecutor") Executor ioExecutor,
                                        GenreDao genreDao,
                                        RatingDao ratingDao) {
        this.network = network;
        this.ioExecutor = ioExecutor;
        this.genreDao = genreDao;
        this.ratingDao = ratingDao;
    }

    public LiveData<List<GenreModel>> getGenres() {
        LiveData<List<GenreModel>> genres = Transformations
                .map(genreDao.getAllGenres(), genreList -> {
                    if(genreList.size() == 0) {
                        // Abusing transformation to check for empty result
                        // todo isoLanguage needs to be configurable
                        network.getGenreList("en-US")
                                .enqueue(new Callback<GenreResponseModel>() {
                            @Override
                            public void onResponse(Call<GenreResponseModel> call, Response<GenreResponseModel> response) {
                                if(response.body() != null) {
                                    ioExecutor.execute(() -> {
                                        genreDao.insertAll(response.body().genres);
                                    });
                                } else {
                                    Timber.e("Unable to add genres from internet!");
                                }
                            }

                            @Override
                            public void onFailure(Call<GenreResponseModel> call, Throwable t) {
                                Timber.e("Failure to retrieve genres");
                            }
                        });
                    }
                    return genreList;
                });
        return genres;
    }

    public LiveData<List<RatingModel>> getRatings() {
        LiveData<List<RatingModel>> ratings = Transformations
                .map(ratingDao.getAllRatings(), inList -> {
                    if (inList.size() == 0) {
                        network.getRatingsList().enqueue(new Callback<RatingResponseModel>() {
                            @Override
                            public void onResponse(Call<RatingResponseModel> call, Response<RatingResponseModel> response) {
                                if (response.body() != null) {
                                    ioExecutor.execute(() -> {
                                        // todo the "get(US)" part needs to be a parameter
                                        ratingDao.insertAllRatings(response.body().getResults().get("US"));
                                    });
                                } else {
                                    Timber.e("Unable to add ratings from internet!");
                                }
                            }

                            @Override
                            public void onFailure(Call<RatingResponseModel> call, Throwable t) {

                            }
                        });
                    }

                    return inList;
                });

        return ratings;
    }

}

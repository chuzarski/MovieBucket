package net.chuzarski.moviebucket.network;

import net.chuzarski.moviebucket.network.models.DiscoverResponseModel;
import net.chuzarski.moviebucket.network.models.MovieModel;
import net.chuzarski.moviebucket.network.models.ServiceConfigurationModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by cody on 3/21/18.
 */

public interface MovieNetworkService {


    /**
     * Fetches individual movie from TMDB
     * @param id Movie ID
     * @return
     */
    @GET("movie/{id}")
    Call<MovieModel> getMovieById(@Path("id") int id);

    @GET("discover/movie?sort_by=primary_release_date.asc&language=en-US&with_release_type=2%7C3")
    Call<DiscoverResponseModel> getUpcomingMovies(@Query("primary_release_date.gte") String releaseDateRangeFrom,
                                                  @Query("primary_release_date.lte") String releaseDateRangeTo,
                                                  @Query("with_original_language") String language,
                                                  @Query("region") String region);

    @GET("configuration")
    Call<ServiceConfigurationModel> getServiceConfiguration();

}

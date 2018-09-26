package net.chuzarski.moviebucket.network;

import net.chuzarski.moviebucket.models.CollectionModel;
import net.chuzarski.moviebucket.models.DiscoverModel;
import net.chuzarski.moviebucket.models.DetailedMovieModel;
import net.chuzarski.moviebucket.models.ListingResponseModel;
import net.chuzarski.moviebucket.models.ServiceConfigurationModel;

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
     * @param movieId
     * @return
     */
    @GET("movie/{id}?append_to_response=videos")
    Call<DetailedMovieModel> getMovieDetail(@Path("id") int movieId);

    @GET("discover/movie?sort_by=primary_release_date.asc&language=en-US&with_release_type=2%7C3")
    Call<DiscoverModel> getUpcomingMovies(@Query("primary_release_date.gte") String releaseDateRangeFrom,
                                          @Query("primary_release_date.lte") String releaseDateRangeTo,
                                          @Query("with_original_language") String language,
                                          @Query("region") String region,
                                          @Query("page") int page);

    @GET("configuration")
    Call<ServiceConfigurationModel> getServiceConfiguration();

    @GET("collection/{id}")
    Call<CollectionModel> getCollection(@Path("id") int collectionId);

    @GET("movie/upcoming")
    Call<ListingResponseModel> getUpcomingListing(@Query("language") String isoLanguage,
                                                  @Query("region") String isoRegion, @Query("page") int page);

    @GET("movie/top_rated")
    Call<ListingResponseModel> getTopRatedListing(@Query("language") String isoLanguage,
                                                  @Query("region") String isoRegion, @Query("page") int page);

    @GET("movie/popular")
    Call<ListingResponseModel> getPopularListing(@Query("language") String isoLanguage,
                                                  @Query("region") String isoRegion, @Query("page") int page);

    @GET("movie/now_playing")
    Call<ListingResponseModel> getNowPlayingListing(@Query("language") String isoLanguage,
                                                  @Query("region") String isoRegion, @Query("page") int page);

    @GET("search/movie")
    Call<ListingResponseModel> getSearchListing(@Query("query") String query,
                                                @Query("language") String isoLanguage,
                                                @Query("region") String isoRegion,
                                                @Query("page") int page);

}

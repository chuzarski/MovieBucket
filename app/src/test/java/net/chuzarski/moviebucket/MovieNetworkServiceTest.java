package net.chuzarski.moviebucket;

import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;
import net.chuzarski.moviebucket.network.UpcomingMoviesParams;
import net.chuzarski.moviebucket.network.models.CollectionModel;
import net.chuzarski.moviebucket.network.models.DetailedMovieModel;
import net.chuzarski.moviebucket.network.models.DiscoverModel;
import net.chuzarski.moviebucket.network.models.ServiceConfigurationModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by cody on 3/21/18.
 */

@RunWith(RobolectricTestRunner.class)
public class MovieNetworkServiceTest {

    private MovieNetworkService service;


    @Before
    public void setup() {
        service = MovieNetworkServiceFactory.getInstance();
    }

    @Test
    public void validateSimpleMovieFetch() {
        int targetId = 338970;
        String targetTitle = "Tomb Raider";
        String targetReleaseDate = "2018-03-08";

        Response<DetailedMovieModel> response = null;
        Call<DetailedMovieModel> call = service.getMovieDetail(targetId);
        DetailedMovieModel model;

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(response.isSuccessful()).isTrue();

        model = response.body();
        assertThat(model.getTitle()).isEqualTo(targetTitle);
        assertThat(model.getReleaseDate()).isEqualTo(targetReleaseDate);
    }

    @Test
    public void validateUpcomingDiscoverFetchWithParams() {
        UpcomingMoviesParams requestParams;

        Response<DiscoverModel> response = null;
        DiscoverModel model;
        Call<DiscoverModel> call;
        int numResults;

        requestParams = new UpcomingMoviesParams.Builder("2018-03-23", "2018-03-30").build();

        call = service.getUpcomingMovies(requestParams.getReleaseDateRangeFrom(),
                requestParams.getReleaseDateRangeTo(),
                requestParams.getLanguage(),
                requestParams.getRegion(),
                1);

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(response.isSuccessful()).isTrue();
        model = response.body();
        assertThat(model).isNotNull();
        assertThat(model.getMovieListing()).isNotEmpty();
    }

    @Test
    public void validateUpcomingDiscoverFetch() {
        String language = "en";
        String region = "US";
        String releaseFrom = "2018-03-23";
        String releaseTo = "2018-03-30";

        Response<DiscoverModel> response = null;
        DiscoverModel model;
        Call<DiscoverModel> call = service.getUpcomingMovies(releaseFrom, releaseTo, language, region, 1);

        int numberResults;

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(response.isSuccessful()).isTrue();

        model = response.body();

        assertThat(model).isNotNull();

        numberResults = model.getNumResults();

        assertThat(model.getMovieListing()).hasSize(numberResults);

    }


    @Test
    public void validateServiceConfigurationFetch() {
        Response<ServiceConfigurationModel> response = null;
        ServiceConfigurationModel model;
        Call<ServiceConfigurationModel> call = service.getServiceConfiguration();

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(response.isSuccessful()).isTrue();

        model = response.body();

        assertThat(model).isNotNull();

        assertThat(model.getImageConfigurations().getBaseUrl()).isNotBlank();
        assertThat(model.getImageConfigurations().getPosterSizes()).contains("original");
        assertThat(model.getImageConfigurations().getBackdropSizes()).contains("original");
    }

    @Test
    public void fullMovieDetailTest() {
        int targetId = 198663;
        String targetTitle = "The Maze Runner";
        String targetCollectionName = "The Maze Runner Collection";

        Response<DetailedMovieModel> response = null;
        Call<DetailedMovieModel> call = service.getMovieDetail(targetId);
        DetailedMovieModel model;

        Response<CollectionModel> collectionResponse = null;
        Call<CollectionModel> collectionCall;
        CollectionModel collectionModel;

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(response.isSuccessful()).isTrue();

        model = response.body();
        assertThat(model.getTitle()).isEqualTo(targetTitle);

        assertThat(model.getVideoListing().getVideos()).isNotEmpty();

        // cool lets get the collection
        collectionCall = service.getCollection(model.getCollection().getId());

        try {
            collectionResponse = collectionCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(collectionResponse.isSuccessful());

        collectionModel = collectionResponse.body();

        assertThat(collectionModel).isNotNull();

        assertThat(collectionModel.getName()).isEqualTo(targetCollectionName);
    }

}

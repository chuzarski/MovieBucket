package net.chuzarski.moviebucket;

import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;
import net.chuzarski.moviebucket.network.models.DiscoverModel;
import net.chuzarski.moviebucket.network.models.MovieModel;
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
        int targetId = 263115;
        String targetTitle = "Logan";
        String targetReleaseDate = "2017-02-28";

        Response<MovieModel> response = null;
        Call<MovieModel> call = service.getMovieById(targetId);
        MovieModel model;

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

        assertThat(model.getMovies()).hasSize(numberResults);

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

}

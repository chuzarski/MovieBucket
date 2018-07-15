package net.chuzarski.moviebucket;

import net.chuzarski.moviebucket.common.MovieImagePathHelper;
import net.chuzarski.moviebucket.models.DetailedMovieModel;
import net.chuzarski.moviebucket.models.ServiceConfigurationModel;
import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
public class MovieImagePathHelperTests {

    MovieNetworkService network;
    ServiceConfigurationModel configModel;
    DetailedMovieModel model;


    @Before
    public void setup() throws IOException {
        network = MovieNetworkServiceFactory.getInstance();
        configModel = network.getServiceConfiguration().execute().body();
        model = network.getMovieDetail(338970).execute().body();
    }

    @Test
    public void validateSizeParsing() {
        MovieImagePathHelper helper = new MovieImagePathHelper(configModel.getImageConfigurations());


    }



}

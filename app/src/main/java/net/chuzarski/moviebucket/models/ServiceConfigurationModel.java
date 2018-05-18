package net.chuzarski.moviebucket.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cody on 3/21/18.
 */

public class ServiceConfigurationModel {


    @SerializedName("images")
    private ImagesConfigurationModel imageConfigurations;

    public ImagesConfigurationModel getImageConfigurations() {
        return imageConfigurations;
    }

    public void setImageConfigurations(ImagesConfigurationModel imageConfigurations) {
        this.imageConfigurations = imageConfigurations;
    }

    public class ImagesConfigurationModel {

        @SerializedName("secure_base_url")
        private String baseUrl;

        @SerializedName("poster_sizes")
        private List<String> posterSizes;

        @SerializedName("backdrop_sizes")
        private List<String> backdropSizes;

        public String getBaseUrl() {
            return baseUrl;
        }

        public List<String> getPosterSizes() {
            return posterSizes;
        }

        public List<String> getBackdropSizes() {
            return backdropSizes;
        }
    }

}

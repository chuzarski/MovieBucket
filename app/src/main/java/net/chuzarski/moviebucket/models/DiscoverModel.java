package net.chuzarski.moviebucket.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cody on 3/21/18.
 */

public class DiscoverModel {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int numPages;

    @SerializedName("total_results")
    private int numResults;

    @SerializedName("results")
    private List<MovieListingItemModel> movieListing;

    public int getPage() {
        return page;
    }

    public int getNumPages() {
        return numPages;
    }

    public int getNumResults() {
        return numResults;
    }

    public List<MovieListingItemModel> getMovieListing() {
        return movieListing;
    }
}

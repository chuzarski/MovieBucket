package net.chuzarski.moviebucket.network.models;

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

    public void setPage(int page) {
        this.page = page;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public int getNumResults() {
        return numResults;
    }

    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }

    public List<MovieListingItemModel> getMovieListing() {
        return movieListing;
    }

    public void setMovieListing(List<MovieListingItemModel> movieListing) {
        this.movieListing = movieListing;
    }
}

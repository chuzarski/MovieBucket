package net.chuzarski.moviebucket.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DiscoverModel {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int numPages;

    @SerializedName("total_results")
    private int numResults;

    @SerializedName("results")
    private List<ListingItemModel> movieListing;

    public int getPage() {
        return page;
    }

    public int getNumPages() {
        return numPages;
    }

    public int getNumResults() {
        return numResults;
    }

    public List<ListingItemModel> getMovieListing() {
        return movieListing;
    }
}

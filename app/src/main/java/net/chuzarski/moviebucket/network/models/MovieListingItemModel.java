package net.chuzarski.moviebucket.network.models;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

/**
 * Created by cody on 3/27/18.
 */

public class MovieListingItemModel {

    @SerializedName("id")
    private int id;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("original_title")
    private String title;

    @SerializedName("poster_path")
    private String posterImagePath;

    @SerializedName("adult")
    private boolean adult;

    public int getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public boolean isAdult() {
        return adult;
    }
}

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

    public void setId(int id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public void setPosterImagePath(String posterImagePath) {
        this.posterImagePath = posterImagePath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }
}

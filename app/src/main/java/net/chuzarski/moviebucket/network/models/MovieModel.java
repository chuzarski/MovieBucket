package net.chuzarski.moviebucket.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by cody on 3/21/18.
 */

public class MovieModel {

    @SerializedName("id")
    int id;

    @SerializedName("original_title")
    private String title;

    @SerializedName("adult")
    private boolean isAdult;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterUrl;

    @SerializedName("release_date")
    private String releaseDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}

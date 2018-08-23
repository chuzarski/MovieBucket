package net.chuzarski.moviebucket.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


/**
 * Created by cody on 3/27/18.
 */

@Entity
public class ListingItemModel {

    @SerializedName("id")
    @PrimaryKey
    private int id;

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterImagePath;

    @SerializedName("adult")
    @ColumnInfo(name = "adult")
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

    public void setId(int id) {
        this.id = id;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterImagePath(String posterImagePath) {
        this.posterImagePath = posterImagePath;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

}

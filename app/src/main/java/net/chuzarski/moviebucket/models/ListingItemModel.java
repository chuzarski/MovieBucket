package net.chuzarski.moviebucket.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


/**
 * Created by cody on 3/27/18.
 */

@Entity
public class ListingItemModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entry_id")
    private int entryId;

    @SerializedName("movieId")
    @ColumnInfo(name = "movie_id")
    private int movieId;

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

    public int getMovieId() {
        return movieId;
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

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }
}

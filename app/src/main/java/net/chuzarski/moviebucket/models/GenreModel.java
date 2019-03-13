package net.chuzarski.moviebucket.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "genres")
public class GenreModel {

    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    private  int genreId;
    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String genreName;

    @Ignore
    private boolean selected;

    public GenreModel(int id, String name) {
        this.genreId = id;
        this.genreName = name;
    }

    public GenreModel() {}

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}

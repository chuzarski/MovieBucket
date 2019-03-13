package net.chuzarski.moviebucket.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ratings")
public class RatingModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("order")
    @ColumnInfo(name = "rating_order")
    private int ratingOrder;

    @SerializedName("certification")
    @ColumnInfo(name = "rating_string")
    private String rating;

    public RatingModel(int order, String rating) {
        this.ratingOrder = order;
        this.rating = rating;
    }

    public RatingModel() {

    }

    public int getRatingOrder() {
        return ratingOrder;
    }

    public void setRatingOrder(int ratingOrder) {
        this.ratingOrder = ratingOrder;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

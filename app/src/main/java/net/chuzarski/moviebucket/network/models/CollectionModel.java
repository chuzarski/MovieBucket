package net.chuzarski.moviebucket.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cody on 3/27/18.
 */

public class CollectionModel {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("parts")
    private List<MovieListingItemModel> members;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MovieListingItemModel> getMembers() {
        return members;
    }
}

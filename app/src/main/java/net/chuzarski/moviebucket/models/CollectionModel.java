package net.chuzarski.moviebucket.models;

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
    private List<ListingItemModel> members;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ListingItemModel> getMembers() {
        return members;
    }
}

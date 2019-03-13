package net.chuzarski.moviebucket.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


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

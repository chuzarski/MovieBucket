package net.chuzarski.moviebucket.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreResponseModel {

    @SerializedName("genres")
    public List<GenreModel> genres;
}

package net.chuzarski.moviebucket.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class RatingResponseModel {

    @SerializedName("certifications")
    private Map<String, List<RatingModel>> results;

    public Map<String, List<RatingModel>> getResults() {
        return results;
    }

    public void setResults(Map<String, List<RatingModel>> results) {
        this.results = results;
    }
}

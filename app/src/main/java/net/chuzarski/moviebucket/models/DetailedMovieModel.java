package net.chuzarski.moviebucket.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;


/**
 * Created by cody on 3/21/18.
 */

@Entity
public class DetailedMovieModel {

    @SerializedName("id")
    @PrimaryKey
    int id;

    @SerializedName("original_title")
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("adult")
    @ColumnInfo(name = "is_adult")
    private boolean isAdult;

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    private String overview;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterUrl;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @SerializedName("videos")
    private VideoListingModel videoListing;

    @SerializedName("belongs_to_collection")
    private CollectionBelongModel collection;

    

    public int getId() {
        return id;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public VideoListingModel getVideoListing() { return videoListing; }

    public CollectionBelongModel getCollection() {
        return collection;
    }

    public class VideoListingModel {
        @SerializedName("results")
        List<VideoModel> videos;

        public List<VideoModel> getVideos() {
            return videos;
        }

        public void setVideos(List<VideoModel> videos) {
            this.videos = videos;
        }
    }
    public class VideoModel {

        @SerializedName("key")
        private String videoKey;
        @SerializedName("site")
        private String site;
        @SerializedName("type")
        private String type;
        @SerializedName("name")
        private String name;

        public String getVideoKey() {
            return videoKey;
        }
        public String getSite() {
            return site;
        }
        public String getType() {
            return type;
        }
        public String getName() {
            return name;
        }

    }

    public class CollectionBelongModel {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}

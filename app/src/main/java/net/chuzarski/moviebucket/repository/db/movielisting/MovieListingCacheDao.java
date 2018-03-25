package net.chuzarski.moviebucket.repository.db.movielisting;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import net.chuzarski.moviebucket.network.models.MovieModel;

import java.util.List;

/**
 * Created by cody on 3/23/18.
 */

@Dao
public interface MovieListingCacheDao {

    @Query("select * from moviemodel")
    List<MovieModel> getAll();




}

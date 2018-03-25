package net.chuzarski.moviebucket.repository.db.movielisting;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

import net.chuzarski.moviebucket.network.models.MovieModel;

/**
 * Created by cody on 3/23/18.
 */

@Database(entities = {MovieModel.class}, version = 1)
public abstract class MovieListingCacheDatabase extends RoomDatabase {

    public abstract MovieListingCacheDao cacheDao();
}

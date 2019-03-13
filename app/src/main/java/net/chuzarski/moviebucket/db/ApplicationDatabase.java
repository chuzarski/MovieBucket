package net.chuzarski.moviebucket.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import net.chuzarski.moviebucket.db.ListingCacheDao;
import net.chuzarski.moviebucket.models.GenreModel;
import net.chuzarski.moviebucket.models.ListingItemModel;
import net.chuzarski.moviebucket.models.RatingModel;

@Database(entities = {ListingItemModel.class,
        GenreModel.class,
        RatingModel.class},
        version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract ListingCacheDao listingDao();
    public abstract GenreDao genreDao();
    public abstract RatingDao ratingDao();
}

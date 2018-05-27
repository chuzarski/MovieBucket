package net.chuzarski.moviebucket.db.listing;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import net.chuzarski.moviebucket.models.ListingItemModel;

@Database(entities = {ListingItemModel.class}, version = 1)
public abstract class ListingCacheDb extends RoomDatabase {
    public abstract ListingCacheDao listingDao();
}

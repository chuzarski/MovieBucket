package net.chuzarski.moviebucket.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import net.chuzarski.moviebucket.db.listing.ListingCacheDao;
import net.chuzarski.moviebucket.db.listing.ListingCacheDb;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    public ListingCacheDb provideMemoryCacheDb(Application application) {
        return Room.inMemoryDatabaseBuilder(application, ListingCacheDb.class).build();
    }

    @Singleton
    @Provides
    public ListingCacheDao provideListingCacheDao(ListingCacheDb db) {
        return db.listingDao();
    }
}

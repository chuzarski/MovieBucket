package net.chuzarski.moviebucket.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import net.chuzarski.moviebucket.db.listing.ListingCacheDao;
import net.chuzarski.moviebucket.db.listing.ListingCacheDb;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private ListingCacheDb memoryCacheDb;

    public DatabaseModule(Context context) {
        memoryCacheDb = Room.inMemoryDatabaseBuilder(context, ListingCacheDb.class).build();
    }

    @Singleton
    @Provides
    public ListingCacheDb provideMemoryCacheDb() {
        return memoryCacheDb;
    }

    @Singleton
    @Provides
    public ListingCacheDao provideListingCacheDao() {
        return memoryCacheDb.listingDao();
    }
}

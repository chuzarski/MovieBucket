package net.chuzarski.moviebucket.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import net.chuzarski.moviebucket.db.ApplicationDatabase;
import net.chuzarski.moviebucket.db.GenreDao;
import net.chuzarski.moviebucket.db.ListingCacheDao;
import net.chuzarski.moviebucket.db.RatingDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    public ApplicationDatabase provideMemoryCacheDb(Application application) {
        return Room.inMemoryDatabaseBuilder(application, ApplicationDatabase.class).build();
    }

    @Singleton
    @Provides
    public ListingCacheDao provideListingCacheDao(ApplicationDatabase db) {
        return db.listingDao();
    }

    @Singleton
    @Provides
    public GenreDao provideGenreDao(ApplicationDatabase db) {
        return db.genreDao();
    }

    @Singleton
    @Provides
    public RatingDao provideRatingDao(ApplicationDatabase db) { return db.ratingDao(); }
}

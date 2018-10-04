package net.chuzarski.moviebucket.di;

import android.content.Context;

import net.chuzarski.moviebucket.db.listing.ListingCacheDao;
import net.chuzarski.moviebucket.db.listing.ListingCacheDb;
import net.chuzarski.moviebucket.network.NetworkService;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ContextModule.class,
        NetworkServiceModule.class,
        DatabaseModule.class,
        ExecutorModule.class})
@Singleton
public interface ApplicationServices {
    Context applicationContext();
    ListingCacheDb memoryCacheDb();
    ListingCacheDao listingCacheDao();
    Executor ioExecutor();
    NetworkService networkService();
}

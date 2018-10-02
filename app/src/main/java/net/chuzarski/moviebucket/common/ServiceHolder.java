package net.chuzarski.moviebucket.common;

import android.arch.persistence.room.Room;
import android.content.Context;

import net.chuzarski.moviebucket.db.listing.ListingCacheDb;
import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServiceHolder {
    private static ServiceHolder instance;

    private Executor ioExecutor;
    private ListingCacheDb cacheDb;
    private MovieNetworkService networkService;

    private ServiceHolder(Context ctx) {
        ioExecutor = Executors.newFixedThreadPool(3);
        cacheDb = Room.inMemoryDatabaseBuilder(ctx, ListingCacheDb.class).build();
        networkService = MovieNetworkServiceFactory.create();
    }

    public static ServiceHolder newInstance(Context ctx) {
        instance = new ServiceHolder(ctx);
        return instance;
    }


    public static ServiceHolder getInstance() {
        if (instance != null) {
            return instance;
        }
        return null;
    }

    public Executor getIoExecutor() {
        return ioExecutor;
    }

    public ListingCacheDb getCacheDb() {
        return cacheDb;
    }

    public MovieNetworkService getNetworkService() {
        return networkService;
    }
}

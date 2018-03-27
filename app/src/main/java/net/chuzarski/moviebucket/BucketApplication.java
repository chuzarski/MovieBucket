package net.chuzarski.moviebucket;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import timber.log.Timber;

/**
 * Created by cody on 3/21/18.
 */

public class BucketApplication extends Application {

    private static Executor networkExecutor;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.tag("Application");
        Timber.d("We have logging");

        networkExecutor = Executors.newFixedThreadPool(3); // Magic number that might be controlled by something else
    }

    public static Executor getNetworkExecutor() {
        return networkExecutor;
    }
}

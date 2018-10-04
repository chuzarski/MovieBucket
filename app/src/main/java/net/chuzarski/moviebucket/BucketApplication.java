package net.chuzarski.moviebucket;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import net.chuzarski.moviebucket.di.ApplicationServices;
import net.chuzarski.moviebucket.di.ContextModule;
import net.chuzarski.moviebucket.di.DaggerApplicationServices;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import timber.log.Timber;

/**
 * Created by cody on 3/21/18.
 */

public class BucketApplication extends Application {

    private ApplicationServices applicationServices;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.tag("Application");
        Timber.d("We have logging");

        applicationServices = DaggerApplicationServices
                .builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();

        AndroidThreeTen.init(this);
    }

    public ApplicationServices getApplicationServices() {
        return applicationServices;
    }
}

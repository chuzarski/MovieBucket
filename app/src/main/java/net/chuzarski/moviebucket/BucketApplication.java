package net.chuzarski.moviebucket;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import net.chuzarski.moviebucket.di.AppComponentInjector;
import net.chuzarski.moviebucket.di.DaggerAppComponentInjector;

import timber.log.Timber;


public class BucketApplication extends Application {

    private AppComponentInjector appComponentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.tag("Application");
        Timber.d("We have logging");

        appComponentInjector = DaggerAppComponentInjector
                .builder()
                .application(this)
                .build();

        AndroidThreeTen.init(this);
    }

    public AppComponentInjector getAppComponentInjector() {
        return appComponentInjector;
    }
}

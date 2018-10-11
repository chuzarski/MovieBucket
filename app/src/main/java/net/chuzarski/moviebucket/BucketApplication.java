package net.chuzarski.moviebucket;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import net.chuzarski.moviebucket.di.AppComponent;
import net.chuzarski.moviebucket.di.DaggerAppComponent;

import timber.log.Timber;


public class BucketApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.tag("Application");
        Timber.d("We have logging");

        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();

        AndroidThreeTen.init(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}

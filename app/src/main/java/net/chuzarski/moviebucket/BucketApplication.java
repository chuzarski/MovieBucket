package net.chuzarski.moviebucket;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import net.chuzarski.moviebucket.common.ServiceHolder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import timber.log.Timber;

/**
 * Created by cody on 3/21/18.
 */

public class BucketApplication extends Application {

    private static Executor ioExectuor;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.tag("Application");
        Timber.d("We have logging");

        ioExectuor = Executors.newFixedThreadPool(3);

        ServiceHolder.newInstance(getApplicationContext());

        AndroidThreeTen.init(this);
    }

    public static Executor getIoExectuor() {
        return ioExectuor;
    }
}

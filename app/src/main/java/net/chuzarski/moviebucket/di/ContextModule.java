package net.chuzarski.moviebucket.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context appContext;

    public ContextModule(Context context) {
        appContext = context;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return appContext;
    }
}

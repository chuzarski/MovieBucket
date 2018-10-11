package net.chuzarski.moviebucket.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import net.chuzarski.moviebucket.db.listing.ListingCacheDao;
import net.chuzarski.moviebucket.db.listing.ListingCacheDb;
import net.chuzarski.moviebucket.network.NetworkService;
import net.chuzarski.moviebucket.ui.listing.ListingFragment;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
        NetworkServiceModule.class,
        DatabaseModule.class,
        ExecutorModule.class,
        ViewModelModule.class})
@Singleton
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(ListingFragment fragment);

    ViewModelProvider.Factory appViewModelFactory();
}

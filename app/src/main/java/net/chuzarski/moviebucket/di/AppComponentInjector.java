package net.chuzarski.moviebucket.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;

import net.chuzarski.moviebucket.ui.listing.ListingFragment;
import net.chuzarski.moviebucket.ui.listing.ListingPreferencesFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
        NetworkServiceModule.class,
        DatabaseModule.class,
        ExecutorModule.class,
        ViewModelModule.class})
@Singleton
public interface AppComponentInjector {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponentInjector build();
    }

    void inject(ListingFragment fragment);
    void inject(ListingPreferencesFragment fragment);

    ViewModelProvider.Factory appViewModelFactory();
}

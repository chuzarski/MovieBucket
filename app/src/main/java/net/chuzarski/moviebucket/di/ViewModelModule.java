package net.chuzarski.moviebucket.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import net.chuzarski.moviebucket.common.AppViewModelFactory;
import net.chuzarski.moviebucket.ui.listing.ListingPreferencesViewModel;
import net.chuzarski.moviebucket.ui.listing.ListingViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListingViewModel.class)
    abstract ViewModel bindListingViewModel(ListingViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ListingPreferencesViewModel.class)
    abstract ViewModel bindListingPreferencesViewModel(ListingPreferencesViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(AppViewModelFactory factory);
}

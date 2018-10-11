package net.chuzarski.moviebucket.common;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class AppViewModelFactory implements ViewModelProvider.Factory{
    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> providers;

    @Inject
    public AppViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providers) {
        this.providers = providers;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends ViewModel> provider = providers.get(modelClass);
        if(provider == null) {
            for(Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : providers.entrySet()) {
                if(modelClass.isAssignableFrom(entry.getKey())) {
                    provider = entry.getValue();
                }
            }
        }

        if (provider == null) {
            // ViewModel class still cannot be found
            Timber.e("ViewModel %s was requested but is not a provided ViewModel", modelClass);
            throw new IllegalArgumentException("Unmapped ViewModel has been requested");
        }

        try {
            return (T) provider.get();
        } catch (Exception e) {
            Timber.e(e, "Failed to create ViewModel");
            throw new RuntimeException(e);
        }
    }
}

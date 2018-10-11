package net.chuzarski.moviebucket.di;

import android.arch.lifecycle.ViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@MapKey
public @interface ViewModelKey {
    Class<? extends ViewModel> value();
}

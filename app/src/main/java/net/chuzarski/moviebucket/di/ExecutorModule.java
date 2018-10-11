package net.chuzarski.moviebucket.di;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ExecutorModule {

    @Provides
    @Singleton
    @Named("ioExecutor")
    public Executor provideIoExecutor() {
        return Executors.newFixedThreadPool(3);
    }
}

package net.chuzarski.moviebucket.di;

import net.chuzarski.moviebucket.BuildConfig;
import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.network.NetworkService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkServiceModule {

    @Singleton
    @Provides
    public OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder() // inserts API key into all requests
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalUrl = original.url();

                    HttpUrl url = originalUrl.newBuilder()
                            .addQueryParameter("api_key", BuildConfig.TMDBAPIKEY)
                            .build();

                    Request request = original.newBuilder()
                            .url(url)
                            .build();

                    return chain.proceed(request);
                })
                .build();
    }


    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(StaticValues.networkApiUrlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    @Singleton
    @Provides
    public NetworkService provideNetworkService(Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }
}

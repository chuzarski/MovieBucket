package net.chuzarski.moviebucket.network;

import com.google.gson.Gson;

import net.chuzarski.moviebucket.BucketApplication;
import net.chuzarski.moviebucket.BuildConfig;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cody on 3/21/18.
 */

public class MovieNetworkServiceFactory {

    public static final String urlBase = "https://api.themoviedb.org/3/";
    private static MovieNetworkService instance;

    /**
     * Creates a MovieNetworkService that returns deserialized POJO objects
     * @return MovieNetworkService Retrofit instance
     */
    public static MovieNetworkService getInstance() {
        if (instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(urlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getCustomHttpClient())
                    .build();
            instance = retrofit.create(MovieNetworkService.class);
        }

        return instance;
    }

    /**
     * This method builds an OkHttpClient that will automatically apply the API key to each request
     * This is to simplify the MovieNetworkService
     * Thank you https://futurestud.io/tutorials/retrofit-2-how-to-add-query-parameters-to-every-request
     * @return Custom OkHttpClient
     */
    private static OkHttpClient getCustomHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalUrl = original.url();

                        HttpUrl url = originalUrl.newBuilder()
                                .addQueryParameter("api_key", BuildConfig.TMDBAPIKEY)
                                .build();

                        Request request = original.newBuilder()
                                .url(url)
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();
    }
}

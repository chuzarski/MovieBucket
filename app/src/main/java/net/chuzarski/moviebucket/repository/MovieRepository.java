package net.chuzarski.moviebucket.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.Network;

import net.chuzarski.moviebucket.network.MovieNetworkService;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;
import net.chuzarski.moviebucket.network.NetworkState;
import net.chuzarski.moviebucket.network.models.DiscoverModel;
import net.chuzarski.moviebucket.repository.db.movielisting.MovieListingCacheDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by cody on 3/21/18.
 */

public class MovieRepository {

    private MovieNetworkService movieNetworkService;
    private MutableLiveData<NetworkState> networkState;
    private static MovieRepository instance;

    private MovieRepository() {
        Timber.tag("MovieRepository");
    }

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }

        return instance;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }


}

package net.chuzarski.moviebucket.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import net.chuzarski.moviebucket.models.ListingItemModel;

import java.util.Map;

public interface ListingRepository {

    void refresh();
    LiveData<PagedList<ListingItemModel>> getListing(Map<String, String> configMap);
    LiveData<Integer> getLoadState();
}

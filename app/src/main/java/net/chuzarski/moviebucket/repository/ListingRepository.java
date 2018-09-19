package net.chuzarski.moviebucket.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import net.chuzarski.moviebucket.models.ListingItemModel;

public interface ListingRepository {

    void refresh();
    LiveData<PagedList<ListingItemModel>> getListing();
    LiveData<Integer> getLoadState();
}

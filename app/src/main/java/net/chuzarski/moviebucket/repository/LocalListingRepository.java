package net.chuzarski.moviebucket.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import net.chuzarski.moviebucket.models.ListingItemModel;

public class LocalListingRepository implements ListingRepository {
    @Override
    public void refresh() {

    }

    @Override
    public LiveData<PagedList<ListingItemModel>> getListing() {
        return null;
    }

    @Override
    public LiveData<Integer> getLoadState() {
        return null;
    }
}

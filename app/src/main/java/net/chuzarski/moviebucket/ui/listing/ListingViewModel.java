package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.models.ListingItemModel;
import net.chuzarski.moviebucket.repository.ListingRepository;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class ListingViewModel extends ViewModel {

    private LiveData<PagedList<ListingItemModel>> pagedListing;
    private ListingRepository repo;
    private Map<String, String> listConfigMap;

    @Inject
    public ListingViewModel(ListingRepository repository) {
        repo = repository;
        listConfigMap = new HashMap<>();
        defaultListConfig();
    }

    public void refresh() {
        Timber.d("Triggering refresh..");
        repo.refresh();
    }

    public LiveData<PagedList<ListingItemModel>> getPagedListing() {
        if(pagedListing == null) {
          pagedListing = repo.getListing(listConfigMap);
        }
        return pagedListing;
    }

    public LiveData<Integer> getLoadState() {
        return repo.getLoadState();
    }


    public Map<String, String> getListConfigMap() {
        return listConfigMap;
    }

    private void defaultListConfig() {
        listConfigMap.put(StaticValues.KEY_LISTING_REPO_LOAD_SOURCE, String.format("%d", StaticValues.INTERNET_LISTING_UPCOMING));
    }
}

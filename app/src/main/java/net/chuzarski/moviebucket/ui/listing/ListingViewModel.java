package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import net.chuzarski.moviebucket.common.InternetListingCriteria;
import net.chuzarski.moviebucket.models.ListingItemModel;
import net.chuzarski.moviebucket.repository.ListingRepository;

import timber.log.Timber;

/**
 * Created by cody on 3/21/18.
 */

public class ListingViewModel extends ViewModel {

    private LiveData<PagedList<ListingItemModel>> pagedListing;
    private ListingRepository repo;
    private InternetListingCriteria internetListingCriteria;

    public ListingViewModel() {
        internetListingCriteria = new InternetListingCriteria();
    }

    public void refresh() {
        Timber.d("Triggering refresh..");
        repo.refresh();
    }

    public LiveData<PagedList<ListingItemModel>> getPagedListing() {
        if(pagedListing == null) {
          pagedListing = repo.getListing();
        }
        return pagedListing;
    }

    public LiveData<Integer> getLoadState() {
        return repo.getLoadState();
    }

    public void setRepository(ListingRepository repository) {
        if (repo != null) {
            Timber.w("Do not attempt to set a repo on a ViewModel that already has one, request denied");
            return;
        }
        this.repo = repository;
    }

    public ListingRepository getRepository() {
        return repo;
    }

    public InternetListingCriteria getInternetListingCriteria() {
        return internetListingCriteria;
    }
}

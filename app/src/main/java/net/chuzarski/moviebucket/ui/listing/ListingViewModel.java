package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import net.chuzarski.moviebucket.models.ListingItemModel;
import net.chuzarski.moviebucket.repository.NetworkListingRepository;
import net.chuzarski.moviebucket.repository.NetworkListingRepository.NetworkFeedConfiguration;
import net.chuzarski.moviebucket.repository.ListingRepository;

import timber.log.Timber;

public class ListingViewModel extends ViewModel {

    private LiveData<PagedList<ListingItemModel>> pagedListing;
    private ListingRepository repo;

    public ListingViewModel() {
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

    /**
     * Sets this ViewModels repository
     * Repository can only be set ONCE on a view model, if one is set, this call is ignored
     * @param repository
     */
    public void setRepository(ListingRepository repository) {
        if (repo != null) {
            return;
        }
        this.repo = repository;
    }

    public ListingRepository getRepository() {
        return repo;
    }

    public NetworkFeedConfiguration getNetworkListingConfiguration() {
        if (repo != null && repo instanceof NetworkListingRepository) {
            return ((NetworkListingRepository) repo).getFeedConfiguration();
        }

        Timber.w("Attempt to retrieve ListingConfiguration failed because" +
                "\n\t the viewmodel holds an incorrect instance of ListingRepository");
        return null;
    }
}

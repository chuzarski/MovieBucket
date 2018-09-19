package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.ViewModel;

import net.chuzarski.moviebucket.common.FeedListingCriteria;

public class ListingActivityViewModel extends ViewModel {
    private FeedListingCriteria feedListingCriteria;

    public FeedListingCriteria getFeedListingCriteria() {
        return feedListingCriteria;
    }

    public void setFeedListingCriteria(FeedListingCriteria feedListingCriteria) {
        this.feedListingCriteria = feedListingCriteria;
    }
}

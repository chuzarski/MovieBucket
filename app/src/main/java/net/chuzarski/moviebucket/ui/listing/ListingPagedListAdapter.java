package net.chuzarski.moviebucket.ui.listing;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.models.ListingItemModel;
import net.chuzarski.moviebucket.ui.listing.ListingViewHolder;

/**
 * Created by cody on 3/23/18.
 */

public class ListingPagedListAdapter extends PagedListAdapter<ListingItemModel, ListingViewHolder> {

    private ListingItemInteractor interactor;

    public static final DiffUtil.ItemCallback<ListingItemModel> diffCallback = new DiffUtil.ItemCallback<ListingItemModel>() {

        @Override
        public boolean areItemsTheSame(ListingItemModel oldItem, ListingItemModel newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(ListingItemModel oldItem, ListingItemModel newItem) {
            return (oldItem.getId() == newItem.getId());
        }
    };

    public ListingPagedListAdapter(ListingItemInteractor interactor) {
        super(diffCallback);
        this.interactor = interactor;
    }

    @NonNull
    @Override
    public ListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_view, parent, false);
        return new ListingViewHolder(view, interactor);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}

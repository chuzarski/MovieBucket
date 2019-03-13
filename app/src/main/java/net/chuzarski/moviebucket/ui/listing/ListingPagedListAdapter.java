package net.chuzarski.moviebucket.ui.listing;

import android.arch.paging.PagedListAdapter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.MovieImagePathHelper;
import net.chuzarski.moviebucket.models.ListingItemModel;
import net.chuzarski.moviebucket.ui.listing.ListingViewHolder;


public class ListingPagedListAdapter extends PagedListAdapter<ListingItemModel, ListingViewHolder> {

    private ListingItemInteractor interactor;
    private RequestManager glide;

    public static final DiffUtil.ItemCallback<ListingItemModel> diffCallback = new DiffUtil.ItemCallback<ListingItemModel>() {

        @Override
        public boolean areItemsTheSame(ListingItemModel oldItem, ListingItemModel newItem) {
            return oldItem.getEntryId() == oldItem.getEntryId();
        }

        @Override
        public boolean areContentsTheSame(ListingItemModel oldItem, ListingItemModel newItem) {
            return oldItem.getMovieId() == newItem.getMovieId();
        }
    };

    public ListingPagedListAdapter(ListingItemInteractor interactor, RequestManager glideRequestManager) {
        super(diffCallback);
        this.interactor = interactor;
        this.glide = glideRequestManager;
    }

    @NonNull
    @Override
    public ListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_item_view, parent, false);
        return new ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingViewHolder holder, int position) {
        ListingItemModel movieItem = getItem(position);

        if(movieItem != null) {
            holder.title.setText(movieItem.getTitle());
            holder.releaseDate.setText(movieItem.getReleaseDate());
            glide.load(MovieImagePathHelper.createURLForBackdrop(movieItem.getPosterImagePath()))
                    .apply(new RequestOptions().placeholder(new ColorDrawable(Color.RED)))
                    .into(holder.poster);
            holder.poster.setOnClickListener(view -> interactor.openMovieDetail(movieItem.getMovieId()));
        }
    }

    @Override
    public void onViewRecycled(@NonNull ListingViewHolder holder) {
        super.onViewRecycled(holder);
        glide.clear(holder.poster);
    }
}

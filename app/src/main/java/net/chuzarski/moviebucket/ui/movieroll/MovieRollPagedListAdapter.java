package net.chuzarski.moviebucket.ui.movieroll;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.network.models.MovieListingItemModel;

/**
 * Created by cody on 3/23/18.
 */

public class MovieRollPagedListAdapter extends PagedListAdapter<MovieListingItemModel, MovieRollViewHolder> {

    public static final DiffUtil.ItemCallback<MovieListingItemModel> diffCallback = new DiffUtil.ItemCallback<MovieListingItemModel>() {

        @Override
        public boolean areItemsTheSame(MovieListingItemModel oldItem, MovieListingItemModel newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(MovieListingItemModel oldItem, MovieListingItemModel newItem) {
            return (oldItem.getId() == newItem.getId());
        }
    };

    protected MovieRollPagedListAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MovieRollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_view, parent, false);
        return new MovieRollViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRollViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}

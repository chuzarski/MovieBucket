package net.chuzarski.moviebucket.ui.movielisting;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.ViewGroup;

import net.chuzarski.moviebucket.network.models.MovieModel;

/**
 * Created by cody on 3/23/18.
 */

public class MovieListingPagedListAdapter extends PagedListAdapter<MovieModel, MovieListingViewHolder> {

    public static final DiffUtil.ItemCallback<MovieModel> diffCallback = new DiffUtil.ItemCallback<MovieModel>() {

        @Override
        public boolean areItemsTheSame(MovieModel oldItem, MovieModel newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(MovieModel oldItem, MovieModel newItem) {
            return (oldItem.getId() == newItem.getId());
        }
    };

    protected MovieListingPagedListAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MovieListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null; // TODO inflate view
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListingViewHolder holder, int position) {
        return; // TODO Call viewholder action here
    }
}

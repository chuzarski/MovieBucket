package net.chuzarski.moviebucket.ui.listing;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.models.ListingItemModel;
import net.chuzarski.moviebucket.common.MovieImagePathHelper;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.listing_title)
    public TextView title;

    @BindView(R.id.listing_release_date)
    public TextView releaseDate;

    @BindView(R.id.listing_poster)
    public ImageView poster;

    public ListingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

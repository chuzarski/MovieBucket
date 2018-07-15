package net.chuzarski.moviebucket.ui.listing;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.models.ListingItemModel;
import net.chuzarski.moviebucket.common.MovieImagePathHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by cody on 3/21/18.
 */

public class ListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ListingItemInteractor itemInteractor;

    @BindView(R.id.movie_item_title)
    public TextView title;

    @BindView(R.id.movie_poster_image)
    public ImageView poster;

    @BindView(R.id.listing_item_action_open)
    public ImageButton openButton;

    @BindView(R.id.listing_item_action_share)
    public ImageButton shareButton;

    private RequestManager glideRequestManager;

    // data for this view
    private int movieId;

    public ListingViewHolder(View itemView, ListingItemInteractor interactor) {
        super(itemView);
        itemInteractor = interactor;

        ButterKnife.bind(this, itemView);
        glideRequestManager = Glide.with(poster.getContext());

        openButton.setOnClickListener(this);
    }

    public void bind(ListingItemModel movie) {
        if(movie == null)
            return;

        title.setText(movie.getTitle());
        glideRequestManager.load(MovieImagePathHelper.createURLForBackdrop(movie.getPosterImagePath())).into(poster);


        movieId = movie.getId();
    }

    public void unbind() {
        glideRequestManager.clear(poster);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.listing_item_action_open:
                itemInteractor.openMovieDetail(movieId);
                break;
        }
    }
}

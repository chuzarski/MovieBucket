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

    private Unbinder unbinder;
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
        unbinder = ButterKnife.bind(this, itemView);
        glideRequestManager = Glide.with(itemView);
        itemInteractor = interactor;
    }

    public void bind(ListingItemModel movie) {
        //for some reason database is returning null objects, but everything looks accounted for
        // TODO fix database null issue?
        if(movie == null) {
            Timber.d("ViewHolder received a null object on bind");
            return;
        }

        title.setText(movie.getTitle());
        glideRequestManager.load(MovieImagePathHelper.createURLForBackdrop(movie.getPosterImagePath())).into(poster);

        if (!openButton.hasOnClickListeners()) {
            openButton.setOnClickListener(this);
        }

        movieId = movie.getId();
    }

    public void unbind() {
        unbinder.unbind();
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

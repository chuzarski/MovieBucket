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

/**
 * Created by cody on 3/21/18.
 */

public class ListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ListingItemInteractor itemInteractor;

    @BindView(R.id.movie_item_title)
    public TextView title;

    @BindView(R.id.movie_poster_image)
    public ImageView poster;

    private RequestManager glideRequestManager;

    // data for this view
    private int movieId;

    public ListingViewHolder(View itemView, ListingItemInteractor interactor) {
        super(itemView);
        itemInteractor = interactor;

        ButterKnife.bind(this, itemView);
        glideRequestManager = Glide.with(poster.getContext());

        poster.setOnClickListener(this);
    }

    public void bind(ListingItemModel movie) {
        if(movie == null)
            return;

        title.setText(movie.getTitle());
        glideRequestManager.load(MovieImagePathHelper.createURLForBackdrop(movie.getPosterImagePath())).into(poster);


        movieId = movie.getMovieId();
    }

    public void unbind() {
        glideRequestManager.clear(poster);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_poster_image:
                itemInteractor.openMovieDetail(movieId);
                break;
        }
    }
}

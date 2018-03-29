package net.chuzarski.moviebucket.ui.movieroll;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.network.models.MovieListingItemModel;
import net.chuzarski.moviebucket.util.MovieImagePathHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cody on 3/21/18.
 */

public class MovieRollViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.movie_item_title)
    public TextView title;

    @BindView(R.id.movie_poster_image)
    public ImageView poster;

    private RequestManager glideRequestManager;

    public MovieRollViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        glideRequestManager = Glide.with(itemView);
    }

    public void bind(MovieListingItemModel movie) {
        title.setText(movie.getTitle());
        glideRequestManager.load(MovieImagePathHelper.createURLForBackdrop(movie.getPosterImagePath())).into(poster);
    }

}

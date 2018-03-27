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

/**
 * Created by cody on 3/21/18.
 */

public class MovieRollViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private ImageView poster;
    private RequestManager glideRequestManager;

    public MovieRollViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.movie_item_title);
        poster = itemView.findViewById(R.id.movie_poster_image);
        glideRequestManager = Glide.with(itemView);
    }

    public void bind(MovieListingItemModel movie) {
        title.setText(movie.getTitle());
        glideRequestManager.load(MovieImagePathHelper.createURLForBackdrop(movie.getPosterImagePath())).into(poster);
    }

}

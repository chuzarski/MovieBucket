package net.chuzarski.moviebucket.ui.movielisting;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.network.models.MovieModel;

import java.util.List;

/**
 * Created by cody on 3/21/18.
 */

public class MovieListingViewHolder extends RecyclerView.ViewHolder {

    TextView title;

    public MovieListingViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.movie_item_title);
    }

    public void bind(MovieModel movie) {
        title.setText(movie.getTitle());
    }


}

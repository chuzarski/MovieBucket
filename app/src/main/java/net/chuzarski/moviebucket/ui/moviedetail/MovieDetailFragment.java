package net.chuzarski.moviebucket.ui.moviedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.util.MovieImagePathHelper;
import net.chuzarski.moviebucket.viewmodels.MovieDetailViewModel;

import timber.log.Timber;

public class MovieDetailFragment extends Fragment {

    private MovieDetailInteractor mListener;
    private MovieDetailViewModel viewModel;

    private ImageView movieHeadingImageView;
    private TextView movieTitleTextView;
    private TextView movieSummaryTextView;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt("MOVIE_ID", movieId);

        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MovieDetailInteractor) {
            mListener = (MovieDetailInteractor) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MovieRollFragmentInteractor");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // references to UI
        movieHeadingImageView = getView().findViewById(R.id.movie_detail_heading_image);
        movieTitleTextView = getView().findViewById(R.id.movie_detail_title);
        movieSummaryTextView = getView().findViewById(R.id.movie_detail_summary);

        viewModel.setMovieId(getArguments().getInt("MOVIE_ID")); // TODO We blindly make the assumption that this value is in the bundle. This cannot be.);

        viewModel.getMovieModel().observe(this, model -> {
            movieTitleTextView.setText(model.getTitle());
            movieSummaryTextView.setText(model.getOverview());

            Glide.with(this)
                    .load(MovieImagePathHelper.createURLForBackdrop(model.getBackdropPath()))
                    .into(movieHeadingImageView);
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface MovieDetailInteractor {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package net.chuzarski.moviebucket.ui.moviedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.network.models.DetailedMovieModel;
import net.chuzarski.moviebucket.util.MovieImagePathHelper;
import net.chuzarski.moviebucket.viewmodels.MovieDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class MovieDetailFragment extends Fragment {

    private MovieDetailInteractor mListener;
    private MovieDetailViewModel viewModel;

    @BindView(R.id.movie_detail_fragment_heading_image)
    public ImageView movieHeadingImageView;

    @BindView(R.id.movie_detail_fragment_title)
    public TextView movieTitleTextView;

    @BindView(R.id.movie_detail_fragment_summary)
    public TextView movieSummaryTextView;

    @BindView(R.id.movie_detail_fragment_layout_trailer)
    public LinearLayout trailerGroupLayout;

    private Unbinder unbinder;


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
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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

        viewModel.getMovieModel(getArguments().getInt("MOVIE_ID")).observe(this, model -> { // TODO We blindly make the assumption that this value is in the bundle. This cannot be.);
            movieTitleTextView.setText(model.getTitle());
            movieSummaryTextView.setText(model.getOverview());

            Glide.with(this)
                    .load(MovieImagePathHelper.createURLForBackdrop(model.getBackdropPath()))
                    .into(movieHeadingImageView);

            if(model.getVideoListing() != null) {
                trailerGroupLayout.setVisibility(View.VISIBLE);
                for (DetailedMovieModel.VideoModel video : model.getVideoListing().getVideos()) {
                    if (video.getSite().equals("YouTube")) {
                        trailerGroupLayout.addView(createTrailerViewButton());
                    }
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    private Button createTrailerViewButton() {
        Button viewButton = new Button(getView().getContext());
        viewButton.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
        );
        viewButton.setText("View Trailer on YouTube");

        return viewButton;
    }
}

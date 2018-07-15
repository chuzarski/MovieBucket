package net.chuzarski.moviebucket.ui.detail;

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
import com.bumptech.glide.RequestManager;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.StaticHelpers;
import net.chuzarski.moviebucket.models.DetailedMovieModel;
import net.chuzarski.moviebucket.common.MovieImagePathHelper;
import net.chuzarski.moviebucket.viewmodels.DetailViewModel;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class DetailFragment extends Fragment {

    private MovieDetailInteractor mListener;
    private DetailViewModel viewModel;

    //TODO DetailFragment: Figure out what we are going to do with this layout group
    public LinearLayout trailerGroupLayout;

    @BindView(R.id.movie_detail_movie_title)
    public TextView movieTitleTextView;

    @BindView(R.id.movie_detail_movie_summary)
    public TextView movieSummaryTextView;

    @BindView(R.id.movie_detail_heading_imageview)
    public ImageView movieHeadingImageView;

    private Unbinder unbinder;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt("MOVIE_ID", movieId);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

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
                    + " must implement ListingFragmentInteractor");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getMovieModel(getArguments().getInt("MOVIE_ID")).observe(this, model -> { // TODO We blindly make the assumption that this value is in the bundle. This cannot be.);
            String backdropPath;
            movieTitleTextView.setText(model.getTitle());
            movieSummaryTextView.setText(model.getOverview());

            Glide.with(movieHeadingImageView).load(MovieImagePathHelper.createURLForBackdrop(model.getBackdropPath()));
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
        // TODO DetailFragment: Update argument type and name
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

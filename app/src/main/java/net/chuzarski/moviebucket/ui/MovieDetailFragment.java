package net.chuzarski.moviebucket.ui;

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

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.viewmodels.MovieDetailViewModel;

public class MovieDetailFragment extends Fragment {

    private MovieDetailInteractor mListener;
    private MovieDetailViewModel viewModel;

    private ImageView movieHeadingImageView;
    private TextView movieTitleTextView;
    private TextView movieSummaryTextView;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    + " must implement OnFragmentInteractionListener");
        }

        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

    }

    @Override
    public void onStart() {
        super.onStart();

        // references to UI
        movieHeadingImageView = getView().findViewById(R.id.movie_detail_heading_image);
        movieTitleTextView = getView().findViewById(R.id.movie_detail_title);
        movieSummaryTextView = getView().findViewById(R.id.movie_detail_summary);


        viewModel.getMovieModel().observe(this, model -> {
            // change in MovieModel i.e. it finally arrived from the internet. Time to update.
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface MovieDetailInteractor {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

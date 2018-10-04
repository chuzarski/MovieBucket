package net.chuzarski.moviebucket.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.models.DetailedMovieModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class DetailFragment extends Fragment {

    private MovieDetailInteractor detailActivityInteractor;
    private DetailViewModel viewModel;

    @BindView(R.id.movie_detail_fragment_title)
    TextView movieTitle;
    @BindView(R.id.movie_detail_fragment_summary)
    TextView movieSummary;

    @BindView(R.id.card_release_date_label)
    TextView movieReleaseDateLabel;
    @BindView(R.id.card_release_runtime_label)
    TextView movieRuntimeLabel;
    @BindView(R.id.card_release_genre_label)
    TextView movieGenresLabel;

    @BindView(R.id.trailer_card_trailers_list)
    LinearLayout trailersList;

    private Unbinder unbinder;

    private boolean hasTrailers = false;

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
            detailActivityInteractor = (MovieDetailInteractor) context;
        } else {
            // TODO show imageview since we aren't loaded into the DetailActivity
            Timber.i("Loaded into Activity that does not support backdrop in Toolbar");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getMovieModel(getArguments().getInt("MOVIE_ID")).observe(this, model -> { // TODO We blindly make the assumption that this value is in the bundle. This cannot be.);
            loadBackdropImage(model.getBackdropPath());
            movieTitle.setText(model.getTitle());
            movieSummary.setText(model.getOverview());

            displayMovieRelease(model);
            displayTrailers(model);

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
        detailActivityInteractor = null;
    }

    private void loadBackdropImage(String uri) {
        if(detailActivityInteractor != null) {
            detailActivityInteractor.addBackdropToActivity(uri);
            return;
        } else {
            //todo this will load into the backdrop imageview for the fragment
            return;
        }
    }

    private void displayMovieRelease(DetailedMovieModel model) {
        if(model.getReleaseDate() != null) {
            movieReleaseDateLabel.setText(model.getReleaseDate());
        }

        if(model.getRuntime() != 0) {
            movieRuntimeLabel.setText(String.format("%d minutes", model.getRuntime()));
        }

        if(model.getGenres() != null) {
            StringBuilder genreString = new StringBuilder();

            // this could be better
            for (DetailedMovieModel.GenreModel genre : model.getGenres()) {
                genreString.append(genre.getGenreName());
                genreString.append(", ");
            }

            movieGenresLabel.setText(genreString.toString());
        }
    }

    // TODO replace with ListView
    // call invalidate on parent view to force re-draw
    private void displayTrailers(DetailedMovieModel model) {
        if(hasTrailers)
            return;

        for (DetailedMovieModel.VideoModel vidModel : model.getVideoListing().getVideos()) {
            trailersList.addView(createViewForTrailerList(vidModel));
        }
        hasTrailers = true;
    }

    private View createViewForTrailerList(DetailedMovieModel.VideoModel model) {
        View itemView = getLayoutInflater().inflate(R.layout.trailer_list_item, null);
        TextView itemLabel = itemView.findViewById(R.id.trailer_list_item_label);
        itemLabel.setText("Trailer");
        itemLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTrailerVideo(model.getVideoKey());
            }
        });
        return itemView;
    }


    private void openTrailerVideo(String uri) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://www.youtube.com/watch?v=%s", uri)));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);

    }
    public interface MovieDetailInteractor {
        // TODO DetailFragment: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void addBackdropToActivity(String uri);
    }

}

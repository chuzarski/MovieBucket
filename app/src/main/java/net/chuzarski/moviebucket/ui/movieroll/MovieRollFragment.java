package net.chuzarski.moviebucket.ui.movieroll;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.network.UpcomingMoviesParams;
import net.chuzarski.moviebucket.viewmodels.MovieRollViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class MovieRollFragment extends Fragment {


    private MovieRollFragmentInteractor mListener;
    private Unbinder unbinder;


    private MovieRollViewModel viewModel;
    private MovieRollPagedListAdapter adapter;
    private LinearLayoutManager layoutManager;

    // UI elements
    @BindView(R.id.fragment_movie_roll_recylerview)
    public RecyclerView movieRecyclerView;

    public MovieRollFragment() {
        // Required empty public constructor
    }

    public static MovieRollFragment newInstance() {
        return new MovieRollFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MovieRollFragmentInteractor) {
            mListener = (MovieRollFragmentInteractor) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MovieRollFragmentInteractor");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag("Movie Roll");

        viewModel = ViewModelProviders.of(this).get(MovieRollViewModel.class);
        // TODO POSSIBLY make this passed as a fragment argument?
        viewModel.setRequestParams(new UpcomingMoviesParams.Builder("2018-03-23", "2018-03-30").build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_roll, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new MovieRollPagedListAdapter();
        layoutManager = new LinearLayoutManager(getContext());

        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getMovieList().observe(this, list -> {
            adapter.submitList(list);
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

    public interface MovieRollFragmentInteractor {
        // TODO add interface for fragment interaction
    }
}

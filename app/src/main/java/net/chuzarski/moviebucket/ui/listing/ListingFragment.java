package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.LoadState;
import net.chuzarski.moviebucket.common.TimeFrame;
import net.chuzarski.moviebucket.viewmodels.ListingViewModel;

import org.threeten.bp.LocalDate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class ListingFragment extends Fragment implements ListingItemInteractor {


    private ListingFragmentInteractor mListener;
    private Unbinder unbinder;


    private ListingViewModel viewModel;
    private ListingPagedListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;

    // UI elements
    @BindView(R.id.fragment_movie_roll_recylerview)
    public RecyclerView movieRecyclerView;

    public ListingFragment() {
        // Required empty public constructor
    }

    public static ListingFragment newInstance() {
        return new ListingFragment();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Fragment specific
    ///////////////////////////////////////////////////////////////////////////

    public void setTimeframe(TimeFrame time) {
        viewModel.updateTimeFrame(time.getFrom(), time.getTo());
    }

    public void setListingType(int listingType) {
        viewModel.setListingType(listingType);
    }

    public void setTimeframe(LocalDate from, LocalDate to) {
        viewModel.updateTimeFrame(from, to);
    }

    public void refreshList() {
        viewModel.refresh();
    }


    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListingFragmentInteractor) {
            mListener = (ListingFragmentInteractor) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ListingFragmentInteractor");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag("ListingFragment");

        viewModel = ViewModelProviders.of(this).get(ListingViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_roll, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter = new ListingPagedListAdapter(this);
        layoutManager = new LinearLayoutManager(getContext());
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        movieRecyclerView.setLayoutManager(gridLayoutManager);
        movieRecyclerView.setAdapter(adapter);


        viewModel.getMovieList().observe(this, list -> {
            adapter.submitList(list);
        });

        viewModel.getLoadState().observe(this, networkState -> {

            if(networkState == LoadState.LOADING) {
                mListener.disableReloadAction();
            } else if (networkState == LoadState.LOADED){
                mListener.enabledReloadAction();
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

    ///////////////////////////////////////////////////////////////////////////
    // List item interaction
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void openMovieDetail(int id) {
        mListener.startMovieDetail(id);
    }

    public interface ListingFragmentInteractor {
        // TODO add interface for fragment interaction
        void disableReloadAction();
        void enabledReloadAction();

        // opening movies
        void startMovieDetail(@NonNull int id);
    }
}

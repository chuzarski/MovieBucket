package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.viewmodels.ListingViewModel;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class
ListingFragment extends Fragment implements ListingItemInteractor {


    private ListingFragmentInteractor mListener;
    private Unbinder unbinder;

    private ListingViewModel viewModel;
    private ListingPagedListAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private RequestManager glideRequestManager;

    // UI elements
    @BindView(R.id.fragment_movie_roll_recylerview)
    public RecyclerView movieRecyclerView;

    public static final String KEY_LIST_POSITION = "LIST_POSITION";

    public ListingFragment() {
        // Required empty public constructor
    }

    public static ListingFragment newInstance() {
        return new ListingFragment();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Fragment specific
    ///////////////////////////////////////////////////////////////////////////

    public void setListingType(int listingType) {
        viewModel.setFeed(listingType);
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
        glideRequestManager = Glide.with(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_roll, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapter = new ListingPagedListAdapter(this, glideRequestManager);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        movieRecyclerView.setLayoutManager(gridLayoutManager);
        movieRecyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            movieRecyclerView.getLayoutManager()
                    .onRestoreInstanceState(savedInstanceState.getParcelable(KEY_LIST_POSITION));
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        viewModel.getPagedListing().observe(this, list -> {
            adapter.submitList(list);
        });

        viewModel.getLoadState().observe(this, networkState -> {

            if(networkState == StaticValues.LOAD_STATE_LOADING) {
                mListener.disableReloadAction();
            } else if (networkState == StaticValues.LOAD_STATE_LOADED){
                mListener.enabledReloadAction();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(KEY_LIST_POSITION,
                movieRecyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
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
    // Utility methods
    ///////////////////////////////////////////////////////////////////////////


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

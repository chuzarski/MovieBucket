package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.AppViewModelFactory;
import net.chuzarski.moviebucket.common.StaticValues;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class
ListingFragment extends Fragment implements ListingItemInteractor {

    private ListingFragmentInteractor hostInteractor;
    private Unbinder unbinder;

    private ListingViewModel viewModel;
    @Inject
    public AppViewModelFactory vmFactory;

    private ListingPagedListAdapter adapter;
    private RequestManager glideRequestManager;

    // UI elements
    @BindView(R.id.fragment_movie_roll_recylerview)
    public RecyclerView movieRecyclerView;

    // Attribute Keys
    public static final String KEY_LIST_POSITION = "LIST_POSITION";
    public static final String KEY_FRAGMENT_MODE = "MODE";
    public static final String KEY_SEARCH_QUERY = "QUERY";


    public ListingFragment() {
        // Required empty public constructor
    }

    public static ListingFragment newInstance() {
        return new ListingFragment();
    }

    /**
     * Creates this networkListingFragment using arguments
     * if null is passed for the bundle, the networkListingFragment will setup the same way if newInstance() is called
     * @param args
     * @return
     */
    public static ListingFragment newInstance(Bundle args) {
        ListingFragment frag = new ListingFragment();
        if (args != null) {
            frag.setArguments(args);
        }
        return frag;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListingFragmentInteractor) {
            hostInteractor = (ListingFragmentInteractor) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ListingFragmentInteractor");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, vmFactory).get(ListingViewModel.class);
        glideRequestManager = Glide.with(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);


        configureRecycler();
        if (savedInstanceState != null) {
            movieRecyclerView.getLayoutManager()
                    .onRestoreInstanceState(savedInstanceState.getParcelable(KEY_LIST_POSITION));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getLoadState().observe(this, loadStateObserver);
        viewModel.getPagedListing().observe(this, list -> adapter.submitList(list));
    }

    @Override
    public void onResume() {
        super.onResume();
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
        hostInteractor = null;
    }
    ///////////////////////////////////////////////////////////////////////////
    // UI Configuration
    ///////////////////////////////////////////////////////////////////////////
    private void configureRecycler() {
        adapter = new ListingPagedListAdapter(this, glideRequestManager);
        movieRecyclerView.setAdapter(adapter);
        movieRecyclerView.setItemAnimator(null);
        configureRecyclerLayout();
    }

    private void configureRecyclerLayout() {
        if (movieRecyclerView == null) {
            Timber.e("Cannot configure a null RecyclerView");
            return;
        }

        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                movieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                movieRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                break;
        }

    }
    ///////////////////////////////////////////////////////////////////////////
    // Observers
    ///////////////////////////////////////////////////////////////////////////
    Observer<Integer> loadStateObserver = loadState -> {
        if(loadState == null) {
            Timber.w("Load state notification may be broken");
            return;
        }
        switch (loadState){
            case StaticValues.LOAD_STATE_LOADING:
                hostInteractor.disableReloadAction();
                break;
            case StaticValues.LOAD_STATE_LOADED:
                hostInteractor.enabledReloadAction();
                break;
            default:
                Timber.w("Unmapped loadstate passed");
                break;
        }
    };

    ///////////////////////////////////////////////////////////////////////////
    // List item interaction
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void openMovieDetail(int id) {
        hostInteractor.startMovieDetail(id);
    }

    public void refreshList() {
        viewModel.refresh();
    }

    public interface ListingFragmentInteractor {
        // TODO add interface for networkListingFragment interaction
        void disableReloadAction();
        void enabledReloadAction();

        // opening movies
        void startMovieDetail(@NonNull int id);
    }
}

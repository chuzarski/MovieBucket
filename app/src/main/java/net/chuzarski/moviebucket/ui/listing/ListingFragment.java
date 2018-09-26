package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import net.chuzarski.moviebucket.BucketApplication;
import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.InternetListingCriteria;
import net.chuzarski.moviebucket.common.StaticValues;
import net.chuzarski.moviebucket.db.listing.ListingCacheDb;
import net.chuzarski.moviebucket.network.MovieNetworkServiceFactory;
import net.chuzarski.moviebucket.repository.FeedListingRepository;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class
ListingFragment extends Fragment implements ListingItemInteractor {

    private ListingFragmentInteractor hostInteractor;
    private Unbinder unbinder;

    private ListingViewModel viewModel;
    private ListingPagedListAdapter adapter;
    private RequestManager glideRequestManager;

    // instance
    private boolean searchableListing = false;
    private String searchQuery;

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

        configureViewModel();
        glideRequestManager = Glide.with(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_roll, container, false);
        unbinder = ButterKnife.bind(this, view);
        configureRecycler();
        if (savedInstanceState != null) {
            movieRecyclerView.getLayoutManager()
                    .onRestoreInstanceState(savedInstanceState.getParcelable(KEY_LIST_POSITION));
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getLoadState().observe(this, loadStateObserver);
        viewModel.getPagedListing().observe(this, list -> adapter.submitList(list));
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
    // internal
    ///////////////////////////////////////////////////////////////////////////

    public void refreshList() {
        viewModel.refresh();
    }

    private void configureRecycler() {
        adapter = new ListingPagedListAdapter(this, glideRequestManager);
        movieRecyclerView.setAdapter(adapter);
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

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this).get(ListingViewModel.class);

        if(viewModel.getRepository() == null) {
            // view model is not ready, needs some settin!
            initDefaultListingCriteria();
            dispatchRepositoryConfiguration();
        }
    }

    private void dispatchRepositoryConfiguration() {
        // todo logic to determine what KIND of listing we are
        injectFeedRepository();
    }

    private void injectFeedRepository() {
        viewModel.setRepository(new FeedListingRepository(MovieNetworkServiceFactory.getInstance(),
                Room.inMemoryDatabaseBuilder(getContext(),
                        ListingCacheDb.class)
                        .build(),
                BucketApplication.getIoExectuor(),
                viewModel.getInternetListingCriteria()));
    }

    private void initDefaultListingCriteria() {
        //todo good place to set listing defaults based on user preferences
        if(viewModel.getInternetListingCriteria() != null) {
            if(searchableListing) {
                viewModel.getInternetListingCriteria().setFeedType(StaticValues.INTERNET_LISTING_SEARCH);
                viewModel.getInternetListingCriteria().setSearchQuery(searchQuery);
            } else {
                viewModel.getInternetListingCriteria().setFeedType(StaticValues.INTERNET_LISTING_UPCOMING);
            }
            viewModel.getInternetListingCriteria().setIsoLanguage("en");
            viewModel.getInternetListingCriteria().setIsoRegion("US");
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

    public InternetListingCriteria getInternetListingCriteria() {
        return viewModel.getInternetListingCriteria();
    }

    public void setSearchQuery(String query) {
        searchableListing = true;
        searchQuery = query;
    }
    public interface ListingFragmentInteractor {
        // TODO add interface for fragment interaction
        void disableReloadAction();
        void enabledReloadAction();

        // opening movies
        void startMovieDetail(@NonNull int id);
    }
}

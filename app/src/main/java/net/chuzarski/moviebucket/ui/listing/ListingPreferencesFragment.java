package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.common.AppViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

import static net.chuzarski.moviebucket.ui.listing.ListingPreferenceModal.*;

public class ListingPreferencesFragment extends Fragment {

    private ListingPreferencesInteractor hostInteractor;

    @Inject
    public AppViewModelFactory vmFactory;
    private ListingPreferencesViewModel viewModel;

    // UI References
    private Unbinder viewUnbinder;
    @BindView(R.id.listingpref_done_button)
    Button doneButton;
    @BindView(R.id.listingpref_year_button)
    Button yearButton;
    @BindView(R.id.listingpref_genre_button)
    Button genreButton;
    @BindView(R.id.listingpref_rating_button)
    Button ratingButton;

    @BindView(R.id.listingpref_sort_radio_group)
    RadioGroup radios;

    RadioGroup.OnCheckedChangeListener radioListener = (radioGroup, id) -> {
        switch (id) {
            case R.id.radiobutton_sort_upcoming:
                Toast.makeText(getActivity(),"Upcoming selected!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radiobutton_sort_in_theaters:
                Toast.makeText(getActivity(),"In Theaters selected!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radiobutton_sort_popular:
                Toast.makeText(getActivity(),"Popular selected!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radiobutton_sort_top_rated:
                Toast.makeText(getActivity(),"Top Rated selected!", Toast.LENGTH_SHORT).show();
                break;
        }
    };

    public ListingPreferencesFragment() {
        // Required empty public constructor
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle Callbacks
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, vmFactory).get(ListingPreferencesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listing_preferences, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewUnbinder = ButterKnife.bind(this, view);
        radios.setOnCheckedChangeListener(radioListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewUnbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListingPreferencesInteractor) {
            hostInteractor = (ListingPreferencesInteractor) context;
        } else {
            Timber.w("Activity that hosts this networkListingFragment will not receive updates on user listing preferences");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostInteractor = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////
    @OnClick(R.id.listingpref_done_button)
    public void handlePreferencesClose() {
        if (hostInteractor != null) {
            hostInteractor.closePreferences();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Dialog methods
    ///////////////////////////////////////////////////////////////////////////
    @OnClick(R.id.listingpref_genre_button)
    public void showGenreDialog() {
        GenrePreferenceAdapter adapter = new GenrePreferenceAdapter();
        ListingPreferenceModal sheet;
        sheet = new ListingPreferenceModal();
        sheet.setAdapter(adapter);
        sheet.show(getFragmentManager(), "genreDialog");
        viewModel.getGenres().observe(sheet, adapter::submitList);
    }

    @OnClick(R.id.listingpref_rating_button)
    public void showRatingDialog() {
        RatingPreferenceAdapter adapter = new RatingPreferenceAdapter();
        ListingPreferenceModal sheet;
        sheet = new ListingPreferenceModal();
        sheet.setAdapter(adapter);
        sheet.show(getFragmentManager(), "genreDialog");
        viewModel.getRatings().observe(sheet, adapter::submitList);
    }

    public interface ListingPreferencesInteractor {
        void closePreferences();
    }
}

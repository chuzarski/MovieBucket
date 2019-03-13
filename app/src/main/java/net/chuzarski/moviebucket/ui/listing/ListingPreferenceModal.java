package net.chuzarski.moviebucket.ui.listing;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.TextView;

import net.chuzarski.moviebucket.R;
import net.chuzarski.moviebucket.models.GenreModel;
import net.chuzarski.moviebucket.models.RatingModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ListingPreferenceModal extends BottomSheetDialogFragment {

    private Unbinder viewUnbinder;

    @BindView(R.id.listingpref_dialog_list)
    RecyclerView list;

    private PreferenceListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preference_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        viewUnbinder = ButterKnife.bind(this, view);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        if (adapter != null) {
            list.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        viewUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setAdapter(PreferenceListAdapter adapter) {
        this.adapter = adapter;
        if (list != null) {
            list.setAdapter(this.adapter);
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final CheckBox text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_preference_list_dialog_item, parent, false));
            text = (CheckBox) itemView.findViewById(R.id.fragment_preference_list_dialog_item);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Handlers
    ///////////////////////////////////////////////////////////////////////////
    @OnClick(R.id.done_button)
    public void handleDoneButton() {
        dismiss();
    }

    static abstract class PreferenceListAdapter<T> extends ListAdapter<T, ViewHolder> {

        protected PreferenceListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
            super(diffCallback);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public abstract void onBindViewHolder(ViewHolder holder, int position);
    }

    public static class GenrePreferenceAdapter extends PreferenceListAdapter<GenreModel> {
        public static final DiffUtil.ItemCallback<GenreModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<GenreModel>() {
            @Override
            public boolean areItemsTheSame(GenreModel oldItem, GenreModel newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(GenreModel oldItem, GenreModel newItem) {
                return oldItem.getGenreId() == newItem.getGenreId();
            }
        };

        public GenrePreferenceAdapter() {
            super(DIFF_CALLBACK);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItem(position) != null) {
                holder.text.setText(getItem(position).getGenreName());
            }
        }
    }

    public static class RatingPreferenceAdapter extends PreferenceListAdapter<RatingModel> {
        public static final DiffUtil.ItemCallback<RatingModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<RatingModel>() {
            @Override
            public boolean areItemsTheSame(RatingModel oldItem, RatingModel newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(RatingModel oldItem, RatingModel newItem) {
                return oldItem.getRating().equals(newItem);
            }
        };

        public RatingPreferenceAdapter() {
            super(DIFF_CALLBACK);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItem(position) != null) {
                holder.text.setText(getItem(position).getRating());
            }
        }
    }

}

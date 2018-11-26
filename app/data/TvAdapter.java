package com.derrick.aad.app.data;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.derrick.aad.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class TvAdapter extends ListAdapter<TvEntry, TvAdapter.MyViewHolder> {

    private static final String LOG_TAG = TvAdapter.class.getSimpleName();

    public TvAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TvEntry tv = getItem(position);

        Log.d(LOG_TAG, "@saved tv object::" + tv.getOriginal_name());

        holder.mTvTitle.setText(tv.getOriginal_name());

    }

    private static DiffUtil.ItemCallback<TvEntry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TvEntry>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(TvEntry oldConcert, TvEntry newConcert) {
                    return oldConcert.getTv_id() == newConcert.getTv_id();
                }

                @Override
                public boolean areContentsTheSame(TvEntry oldConcert,
                                                  TvEntry newConcert) {
                    return oldConcert.equals(newConcert);
                }
            };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;

        public MyViewHolder(@NonNull View v) {
            super(v);
            mTvTitle = v.findViewById(R.id.tv_title);
        }
    }
}

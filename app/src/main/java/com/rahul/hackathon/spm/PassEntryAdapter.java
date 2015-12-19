package com.rahul.hackathon.spm;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahul.hackathon.spm.model.Entry;

import java.util.List;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class PassEntryAdapter extends RecyclerView.Adapter<PassEntryAdapter.EntryViewHolder> {

    private List<Entry> items;

    public PassEntryAdapter(List<Entry> items) {
        this.items = items;
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entry_item_layout, viewGroup, false);

        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EntryViewHolder itemViewHolder, int position) {

        //Here you can fill your row view
        Entry entry = items.get(position);
        itemViewHolder.tvTitle.setText(entry.getTitle());
        itemViewHolder.tvUsername.setText(entry.getUsername());
        itemViewHolder.tvPassword.setText(entry.getPassword());
    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle = null;
        TextView tvUsername = null;
        TextView tvPassword = null;

        public EntryViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
            tvUsername = (TextView) itemView.findViewById(R.id.username);
            tvPassword = (TextView) itemView.findViewById(R.id.password);
        }
    }
}

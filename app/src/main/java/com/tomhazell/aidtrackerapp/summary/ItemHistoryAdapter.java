package com.tomhazell.aidtrackerapp.summary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tomhazell.aidtrackerapp.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tom Hazell on 07/03/2017.
 */

public class ItemHistoryAdapter extends RecyclerView.Adapter<ItemHistoryAdapter.ViewHolder> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    private List<ItemTracking> items;

    public ItemHistoryAdapter(List<ItemTracking> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemTracking tracking = items.get(position);

        holder.date.setText(dateFormat.format(tracking.getDate()));

        holder.location.setText(tracking.getStatus() + " at " + tracking.getLocation());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder used to store the recyclable views
        @BindView(R.id.historyDate)
        TextView date;
        @BindView(R.id.historyLocation)
        TextView location;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}

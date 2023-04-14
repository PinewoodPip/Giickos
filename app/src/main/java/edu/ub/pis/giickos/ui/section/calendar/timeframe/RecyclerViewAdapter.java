package edu.ub.pis.giickos.ui.section.calendar.timeframe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.section.calendar.ViewModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_calendar_timeframe, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getTimeLabel(position));
    }

    private String getTimeLabel(int position) {
        return String.format(Locale.getDefault(), "%s:00", Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        return ViewModel.HOURS_IN_DAY;
    }
}

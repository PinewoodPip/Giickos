package edu.ub.pis.giickos.ui.section.calendar.timeframe;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.ub.pis.giickos.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView labelTextView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        labelTextView = itemView.findViewById(R.id.label_time);
    }

    public void bind(String timeLabel) {
        labelTextView.setText(timeLabel);
    }
}

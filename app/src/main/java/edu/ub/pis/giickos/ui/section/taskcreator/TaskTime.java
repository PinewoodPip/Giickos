package edu.ub.pis.giickos.ui.section.taskcreator;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Time;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.TimePicker;
import edu.ub.pis.giickos.ui.main.TimePickerListener;

// A task creation field with a time input.
public class TaskTime extends Fragment {
    private static final String ARG_ID = "ID";
    private static final String ARG_LABEL = "Label";
    private TimePickerListener listener;

    public TaskTime() {} // Required empty public constructor

    public static TaskTime newInstance(String id, String label) {
        TaskTime fragment = new TaskTime();
        Bundle args = new Bundle();

        args.putString(ARG_LABEL, label);
        args.putString(ARG_ID, id);
        fragment.setArguments(args);

        return fragment;
    }

    public void setListener(TimePickerListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_time, container, false);
        TextView textField = view.findViewById(R.id.label_time);

        textField.setText(getArguments().getString(ARG_LABEL));

        textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePicker dialog = new TimePicker(getArguments().getString(ARG_ID));
                dialog.setListener(listener);
                dialog.show(getParentFragmentManager(), "");
            }
        });

        return view;
    }

    public void setText(String label) {
        TextView text = getView().findViewById(R.id.label_time);

        text.setText(label);
    }
}
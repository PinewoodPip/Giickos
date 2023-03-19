package edu.ub.pis.giickos.ui.section.taskcreator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.DatePicker;
import edu.ub.pis.giickos.ui.main.DatePickerListener;
import edu.ub.pis.giickos.ui.main.TimePicker;
import edu.ub.pis.giickos.ui.main.TimePickerListener;

// A task creator field with a date input.
public class TaskDate extends Fragment {
    private static final String ARG_ID = "ID";
    private static final String ARG_LABEL = "Label";

    private DatePickerListener listener;

    public TaskDate() {} // Required empty public constructor

    public static TaskDate newInstance(String id, String label) {
        TaskDate fragment = new TaskDate();
        Bundle args = new Bundle();

        args.putString(ARG_ID, id);
        args.putString(ARG_LABEL, label);
        fragment.setArguments(args);

        return fragment;
    }

    public void setListener(DatePickerListener listener) {
        this.listener = listener;
    }

    public void setText(String label) {
        TextView text = getView().findViewById(R.id.label_date);

        text.setText(label);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_date, container, false);
        Bundle args = getArguments();
        TextView textField = view.findViewById(R.id.label_date);

        textField.setText(args.getString(ARG_LABEL));
        textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker dialog = new DatePicker(getArguments().getString(ARG_ID));
                dialog.setListener(listener);
                dialog.show(getParentFragmentManager(), "");
            }
        });

        return view;
    }
}
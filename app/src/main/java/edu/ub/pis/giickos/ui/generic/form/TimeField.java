package edu.ub.pis.giickos.ui.generic.form;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.TimePicker;
import edu.ub.pis.giickos.ui.generic.TimePickerListener;

// A task creation field with a time input.
public class TimeField extends Fragment {
    private static final String ARG_ID = "ID";
    private static final String ARG_LABEL = "Label";

    private TimePickerListener listener;

    public TimeField() {} // Required empty public constructor

    public static TimeField newInstance(String id, String label) {
        TimeField fragment = new TimeField();
        Bundle args = new Bundle();

        args.putString(ARG_LABEL, label);
        args.putString(ARG_ID, id);
        fragment.setArguments(args);

        return fragment;
    }

    public void setListener(TimePickerListener listener) {
        this.listener = listener;
    }

    public void setText(String label) {
        TextView text = getView().findViewById(R.id.label_time);

        text.setText(label);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_field_time, container, false);
        TextView textField = view.findViewById(R.id.label_time);

        textField.setText(getArguments().getString(ARG_LABEL));

        textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePicker dialog = new TimePicker(getArguments().getString(ARG_ID));
                dialog.setListener(new TimePickerListener() {
                    @Override
                    public void timeSet(String pickerID, int hour, int minute) {
                        setText(String.format("%d:%d", hour, minute)); // TODO display nicely

                        // Forward event
                        if (listener != null) {
                            listener.timeSet(pickerID, hour, minute);
                        }
                    }
                });
                dialog.show(getParentFragmentManager(), "");
            }
        });

        return view;
    }
}
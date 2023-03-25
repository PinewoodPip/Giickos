package edu.ub.pis.giickos.ui.generic.form;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.DatePicker;
import edu.ub.pis.giickos.ui.main.DatePickerListener;

// A task creator field with a date input.
public class DateField extends Fragment {
    private static final String ARG_ID = "ID";
    private static final String ARG_LABEL = "Label";

    private DatePickerListener listener;

    public DateField() {} // Required empty public constructor

    public static DateField newInstance(String id, String label) {
        DateField fragment = new DateField();
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
        View view = inflater.inflate(R.layout.fragment_form_field_date, container, false);
        Bundle args = getArguments();
        TextView textField = view.findViewById(R.id.label_date);

        textField.setText(args.getString(ARG_LABEL));
        textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker dialog = new DatePicker(getArguments().getString(ARG_ID));
                dialog.setListener(new DatePickerListener() {
                    @Override
                    public void dateSet(String id, int year, int month, int day) {
                        setText(String.format("%d/%d/%d", day, month, year)); // TODO display nicely

                        // Forward event
                        if (listener != null) {
                            listener.dateSet(id, year, month, day);
                        }
                    }
                });
                dialog.show(getParentFragmentManager(), "");
            }
        });

        return view;
    }
}
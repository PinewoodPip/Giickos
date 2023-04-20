package edu.ub.pis.giickos.ui.generic.form;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import edu.ub.pis.giickos.R;

// Fragment for a form field that accepts numbers.
public class NumberField extends Fragment {

    private static final String ARG_VALUE = "Value";

    private NumberPicker.OnValueChangeListener listener;

    public NumberField() {
        // Required empty public constructor
    }

    public static NumberField newInstance(int value) {
        NumberField fragment = new NumberField();
        Bundle args = new Bundle();
        args.putInt(ARG_VALUE, value);
        fragment.setArguments(args);

        return fragment;
    }

    public void setListener(NumberPicker.OnValueChangeListener listener) {
        View view = getView();

        this.listener = listener;

        if (view != null) {
            getPickerView().setOnValueChangedListener(listener);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_number_field, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        NumberPicker picker = getPickerView();
        Bundle arguments = getArguments();
        int value = 0;
        if (arguments != null) {
            value = arguments.getInt(ARG_VALUE);
        }

        // Set picker value and listener
        picker.setMinValue(0);
        picker.setMaxValue(60 * 24); // TODO extract
        picker.setWrapSelectorWheel(false);
        picker.setValue(value);
        setListener(listener);
    }

    private NumberPicker getPickerView() {
        return getView().findViewById(R.id.picker);
    }
}
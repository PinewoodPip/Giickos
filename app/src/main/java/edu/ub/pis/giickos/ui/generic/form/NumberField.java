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
    private static final String ARG_MIN_VALUE = "MinValue";
    private static final String ARG_MAX_VALUE = "MaxValue";

    private NumberPicker.OnValueChangeListener listener;
    private NumberPicker.Formatter formatter;

    public NumberField() {
        // Required empty public constructor
    }

    public static NumberField newInstance(int value, int minValue, int maxValue) {
        NumberField fragment = new NumberField();
        Bundle args = new Bundle();
        args.putInt(ARG_VALUE, value);
        args.putInt(ARG_MIN_VALUE, minValue);
        args.putInt(ARG_MAX_VALUE, maxValue);
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

    public void setFormatter(NumberPicker.Formatter formatter) {
        View view = getView();

        this.formatter = formatter;

        if (view != null) {
            getPickerView().setFormatter(formatter);
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
        picker.setMinValue(arguments.getInt(ARG_MIN_VALUE, 0));
        picker.setMaxValue(arguments.getInt(ARG_MAX_VALUE, 99));
        picker.setWrapSelectorWheel(false);
        picker.setValue(value);
        setFormatter(formatter);
        setListener(listener);
    }

    private NumberPicker getPickerView() {
        return getView().findViewById(R.id.picker);
    }
}
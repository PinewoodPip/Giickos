package edu.ub.pis.giickos.ui.generic.form;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.List;

import edu.ub.pis.giickos.R;

// A wrapper fragment for a combobox / dropdown.
public class FormSpinner extends Fragment {

    private List<Object> items;
    private int selectedIndex = 0;
    private AdapterView.OnItemSelectedListener listener;

    public FormSpinner() {
        // Required empty public constructor
    }

    public static FormSpinner newInstance(List<Object> items, int selectedIndex) {
        FormSpinner fragment = new FormSpinner();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        fragment.items = items;
        fragment.selectedIndex = selectedIndex;

        return fragment;
    }

    public void setSelection(int index) {
        Spinner spinner = getSpinnerView();

        selectedIndex = index;

        if (spinner != null) {
            spinner.setSelection(index);
        }
    }

    public void setListener(AdapterView.OnItemSelectedListener listener) {
        Spinner spinner = getSpinnerView();

        this.listener = listener;

        if (spinner != null) {
            spinner.setOnItemSelectedListener(listener);
        }
    }

    private Spinner getSpinnerView() {
        View view = getView();
        Spinner spinner = null;

        if (view != null) {
            spinner = view.findViewById(R.id.spinner);
        }

        return spinner;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_spinner, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        Spinner spinner = getSpinnerView();

        spinner.setAdapter(new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items));

        setListener(listener);
        setSelection(selectedIndex);
    }
}
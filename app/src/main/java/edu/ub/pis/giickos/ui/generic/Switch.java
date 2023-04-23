package edu.ub.pis.giickos.ui.generic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import edu.ub.pis.giickos.R;

// Wrapper fragment for a Switch view.
public class Switch extends Fragment {
    public static final String ARG_STATE = "State";

    private CompoundButton.OnCheckedChangeListener listener = null;

    public Switch() {
        // Required empty public constructor
    }

    public static Switch newInstance(boolean state) {
        Switch fragment = new Switch();
        Bundle args = new Bundle();

        args.putBoolean(ARG_STATE, state);
        fragment.setArguments(args);

        return fragment;
    }

    public void setListener(CompoundButton.OnCheckedChangeListener listener) {
        View view = getView();

        this.listener = listener;

        if (view != null) {
            android.widget.Switch switchView = view.findViewById(R.id.view_switch);

            switchView.setOnCheckedChangeListener(listener);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generic_switch, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedBundle) {
        android.widget.Switch switchView = view.findViewById(R.id.view_switch);

        // Set state
        switchView.setChecked(getArguments().getBoolean(ARG_STATE));

        // Set checked listener, if one was provided
        setListener(listener);
    }

    public void setChecked(boolean b) {
        View view = getView();

        if (view != null) {
            android.widget.Switch switchView = view.findViewById(R.id.view_switch);

            switchView.setChecked(b);
        }
    }
    public boolean isChecked() {
        View view = getView();

        if (view != null) {
            android.widget.Switch switchView = view.findViewById(R.id.view_switch);

            return switchView.isChecked();
        }

        return false;
    }
}
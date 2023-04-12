package edu.ub.pis.giickos.ui.section.miscellaneous;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.MainViewModel;
import edu.ub.pis.giickos.ui.section.Section;

// Section that contains miscellanous funcionality:
// statistics view, teams, settings.
public class MiscellaneousSection extends Section {

    public MiscellaneousSection() {
        // Required empty public constructor
    }

    public static MiscellaneousSection newInstance() {
        MiscellaneousSection fragment = new MiscellaneousSection();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_section_miscellaneous, container, false);

        return view;
    }

    @Override
    public MainViewModel.TYPE getType() {
        return MainViewModel.TYPE.MISCELLANEOUS;
    }
}
package edu.ub.pis.giickos.ui.section.timer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.activities.main.MainViewModel;
import edu.ub.pis.giickos.ui.section.Section;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerSection#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerSection extends Section {


    public TimerSection() {
        // Required empty public constructor
    }


    public static TimerSection newInstance() {
        TimerSection fragment = new TimerSection();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_timer, container, false);
    }

    @Override
    public MainViewModel.SECTION_TYPE getType() {
        return MainViewModel.SECTION_TYPE.TIMER;
    }
}
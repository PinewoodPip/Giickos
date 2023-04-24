package edu.ub.pis.giickos.ui.section.bamboogarden;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.MainViewModel;
import edu.ub.pis.giickos.ui.section.Section;

// Section that the bamboo garden functionality:
// plant bamboo, harvest bamboo, storage etc.
public class BambooGarden extends Section {

    public BambooGarden() {
        // Required empty public constructor
    }

    public static BambooGarden newInstance() {
        BambooGarden fragment = new BambooGarden();
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
        View view = inflater.inflate(R.layout.fragment_section_bamboogarden, container, false);

        return view;
    }

    @Override
    public MainViewModel.TYPE getType() {
        return MainViewModel.TYPE.BAMBOO;
    }
}
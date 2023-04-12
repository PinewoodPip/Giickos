package edu.ub.pis.giickos.ui.main.sectionbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.MainViewModel;

// Fragment for the section navigation bar.
public class SectionBar extends GiickosFragment {

    public SectionBar() {
        // Required empty public constructor
    }

    public static SectionBar newInstance() {
        SectionBar fragment = new SectionBar();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // Adds a new SectionBarItem to the list.
    public void addItem(MainViewModel.TYPE sectionType) {
        SectionBarItem sectionItem = SectionBarItem.newInstance(sectionType);

        addChildFragment(sectionItem, R.id.list_buttons);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section_bar, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceBundle) {
        for (int x = 0; x < MainViewModel.TYPE.values().length; x++) {
            MainViewModel.TYPE sectionType = MainViewModel.TYPE.values()[x];

            addItem(sectionType);
        }
    }
}
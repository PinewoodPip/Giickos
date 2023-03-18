package edu.ub.pis.giickos.ui.main.sectionbar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ub.pis.giickos.ui.observer.ObservableEvent;
import edu.ub.pis.giickos.ui.observer.Observable;
import edu.ub.pis.giickos.ui.observer.Observer;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.section.Section;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SectionBar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SectionBar extends Observable<SectionBarEvents> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public SectionBar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SectionBar.
     */
    public static SectionBar newInstance() {
        SectionBar fragment = new SectionBar();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // Adds a new SectionBarItem to the list.
    public void addItem(Section.TYPE sectionType, int iconID) {
        SectionBarItem sectionItem = SectionBarItem.newInstance(sectionType, iconID);

        addChildFragment(sectionItem, R.id.button_list);

        // Forward the events of the item
        sectionItem.subscribe(SectionBarEvents.SECTION_PRESSED, new Observer() {
            @Override
            public void update(ObservableEvent eventData) {
                notifyObservers(SectionBarEvents.SECTION_PRESSED, eventData);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO add arguments (sections?)
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section_bar, container, false);

        // TODO extract; section types should be defined by viewmodel(?) and use some struct to bind icons to it
        for (int x = 0; x < Section.TYPE.values().length; x++) {
            Section.TYPE sectionType = Section.TYPE.values()[x];

            addItem(sectionType, R.drawable.placeholder_notebook);
        }

        return view;
    }
}
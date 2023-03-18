package edu.ub.pis.giickos.ui.main.sectionbar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import edu.ub.pis.giickos.ui.observer.Observable;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.section.Section;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SectionBarItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SectionBarItem extends Observable<SectionBarEvents> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_SECTION_ID = "SectionID";
    public static final String ARG_ICON = "IconID";

    private int iconID;
    private Section.TYPE sectionType;

    public SectionBarItem() {} // Required empty public constructor

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SectionBarItem.
     */
    public static SectionBarItem newInstance(Section.TYPE sectionType, int iconID) {
        SectionBarItem fragment = new SectionBarItem();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_ID, sectionType.ordinal());
        args.putInt(ARG_ICON, iconID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionType = Section.TYPE.values()[getArguments().getInt(ARG_SECTION_ID)];
            iconID = getArguments().getInt(ARG_ICON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section_bar_item, container, false);

        // Set button icon
        ImageButton button = view.findViewById(R.id.icon);
        button.setImageResource(iconID);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("D", "SectionBarItem clicked");

                notifyObservers(SectionBarEvents.SECTION_PRESSED, new SectionBarItemEvent(SectionBarItem.this, sectionType));
            }
        });

        return view;
    }
}
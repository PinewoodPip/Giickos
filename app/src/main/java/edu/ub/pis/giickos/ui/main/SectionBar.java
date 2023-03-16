package edu.ub.pis.giickos.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import edu.ub.pis.giickos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SectionBar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SectionBar extends Fragment {

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
    public void addItem(int iconID) {
        FragmentManager childrenManager = getChildFragmentManager();
        FragmentTransaction childFragTrans = childrenManager.beginTransaction();
        SectionBarItem sectionItem = SectionBarItem.newInstance(iconID);

        childFragTrans.add(R.id.button_list, sectionItem);
        childFragTrans.addToBackStack(null);
        childFragTrans.commit();
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
        // TODO extract
        for (int i = 0; i < 4; i++)
        {
            addItem(R.drawable.placeholder_notebook);
        }

        return view;
    }
}
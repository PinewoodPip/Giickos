package edu.ub.pis.giickos.ui.main.sectionbar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.MainFragment;
import edu.ub.pis.giickos.ui.main.MainViewModel;

// Fragment for the navigation buttons in the section bar.
public class SectionBarItem extends GiickosFragment {

    public static final String ARG_SECTION_ID = "SectionID";

    private MainViewModel.TYPE sectionType;
    private boolean selected;

    private MainViewModel viewModel;

    public SectionBarItem() {} // Required empty public constructor

    public static SectionBarItem newInstance(MainViewModel.TYPE sectionType) {
        SectionBarItem fragment = new SectionBarItem();
        Bundle args = new Bundle();

        args.putInt(ARG_SECTION_ID, sectionType.ordinal());
        fragment.setArguments(args);

        return fragment;
    }

    private void setSelected(boolean selected) {
        ImageButton button = getButtonView();

        this.selected = selected;

        if (button != null) {
            // Set button icon
            button.setImageResource(selected ? sectionType.getSelectedIconResource() : sectionType.getIconResource());
        }
    }

    private ImageButton getButtonView() {
        View view = getView();
        ImageButton button = null;

        if (view != null) {
            button = view.findViewById(R.id.icon);
        }

        return button;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionType = MainViewModel.TYPE.values()[getArguments().getInt(ARG_SECTION_ID)];
        }

        viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section_bar_item, container, false);

        return view;
    }

    public void onViewCreated(View view, Bundle savedInstance) {
        getButtonView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("UI", "SectionBarItem clicked");

                viewModel.setCurrentSection(sectionType);
            }
        });

        // Listen for the section changing to update views
        final Observer observer = new Observer<MainViewModel.TYPE>() {
            @Override
            public void onChanged(MainViewModel.TYPE section) {
                setSelected(section == sectionType);
            }
        };
        viewModel.getCurrentSection().observe(getViewLifecycleOwner(), observer);
    }
}
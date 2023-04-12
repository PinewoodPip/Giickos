package edu.ub.pis.giickos.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.sectionbar.SectionBar;
import edu.ub.pis.giickos.ui.section.Section;
import edu.ub.pis.giickos.ui.section.calendar.CalendarSection;
import edu.ub.pis.giickos.ui.section.miscellaneous.MiscellaneousSection;
import edu.ub.pis.giickos.ui.section.taskcreator.TaskCreator;
import edu.ub.pis.giickos.ui.section.taskexplorer.TaskExplorer;

public class MainFragment extends GiickosFragment {

    public static String INTENT_EXTRA_SECTION = "Section";

    private MainViewModel viewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
    }

    // Changes the active section.
    private void changeSection(Section section) {
        replaceFragment(R.id.fragmentcontainer_main, section);
    }

    // Enum overload for changeSection().
    private void changeSection(MainViewModel.TYPE sectionType) {
        Section section = null;

        switch (sectionType) {
            case TASK_CREATOR:
            {
                section = TaskCreator.newInstance();
                break;
            }
            case TASK_EXPLORER:
            {
                section = TaskExplorer.newInstance();
                break;
            }
            case MISCELLANEOUS:
                section = MiscellaneousSection.newInstance();
                break;
            case CALENDAR:
                section = CalendarSection.newInstance();
                break;
            default:
            {
                Log.e("NotImplemented", "MainFragment.changeSection(): section type not implemented " + sectionType.toString());
            }
        }

        if (section != null) {
            changeSection(section);
        }
    }

    private void createSectionBar() {
        SectionBar sectionBar = SectionBar.newInstance();
        replaceFragment(R.id.section_bar, sectionBar);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        createSectionBar();

        // Set section from intent (if any)
        // or default to task explorer
        MainViewModel.TYPE sectionID = MainViewModel.TYPE.TASK_EXPLORER;
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                int sectionIntegerID = extras.getInt(INTENT_EXTRA_SECTION);

                if (sectionIntegerID != -1) {
                    sectionID = MainViewModel.TYPE.values()[sectionIntegerID];
                    viewModel.setCurrentSection(sectionID);
                }
            }
        }
        changeSection(sectionID);

        viewModel.getCurrentSection().observe(getViewLifecycleOwner(), new Observer<MainViewModel.TYPE>() {
            @Override
            public void onChanged(MainViewModel.TYPE type) {
                changeSection(type);
            }
        });
    }
}
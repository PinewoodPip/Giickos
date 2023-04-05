package edu.ub.pis.giickos.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.ui.observer.ObservableEvent;
import edu.ub.pis.giickos.ui.observer.Observer;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.sectionbar.SectionBar;
import edu.ub.pis.giickos.ui.main.sectionbar.SectionBarEvents;
import edu.ub.pis.giickos.ui.main.sectionbar.SectionBarItemEvent;
import edu.ub.pis.giickos.ui.section.miscellaneous.MiscellaneousSection;
import edu.ub.pis.giickos.ui.section.taskcreator.TaskCreator;
import edu.ub.pis.giickos.ui.section.taskexplorer.TaskExplorer;
import edu.ub.pis.giickos.ui.section.Section;

public class MainFragment extends GiickosFragment {

    public static String INTENT_EXTRA_SECTION = "Section";

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    // Changes the active section.
    private void changeSection(Section section) {
        replaceFragment(R.id.fragmentcontainer_main, section);
    }

    // Enum overload for changeSection().
    private void changeSection(Section.TYPE sectionType) {
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

        // Listen for presses
        sectionBar.subscribe(SectionBarEvents.SECTION_PRESSED, new Observer() {
            @Override
            public void update(ObservableEvent eventData) {
                SectionBarItemEvent event = (SectionBarItemEvent) eventData;

                changeSection(event.getSectionID());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        createSectionBar();

        // Set section from intent (if any)
        // or default to task explorer
        Section.TYPE sectionID = Section.TYPE.TASK_EXPLORER;
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                int sectionIntegerID = extras.getInt(INTENT_EXTRA_SECTION);

                if (sectionIntegerID != -1) {
                    sectionID = Section.TYPE.values()[sectionIntegerID];
                }
            }
        }
        changeSection(sectionID);

        return view;
    }
}
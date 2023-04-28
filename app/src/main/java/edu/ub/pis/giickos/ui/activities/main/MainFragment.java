package edu.ub.pis.giickos.ui.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.activities.main.sectionbar.SectionBar;
import edu.ub.pis.giickos.ui.section.Section;
import edu.ub.pis.giickos.ui.section.bamboogarden.BambooGarden;
import edu.ub.pis.giickos.ui.section.calendar.CalendarSection;
import edu.ub.pis.giickos.ui.section.miscellaneous.MiscellaneousSection;
import edu.ub.pis.giickos.ui.section.taskexplorer.TaskExplorer;
import edu.ub.pis.giickos.ui.section.timer.TimerSection;

public class MainFragment extends GiickosFragment {

    public static String INTENT_EXTRA_SECTION = "Section";

    private MainViewModel viewModel;
    private int previousBackStackCount = 0; // Used to determine if changes in the backstack are pops or pushes
    private boolean initialized = false;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Janky implementation of back-navigation
        // We need to listen for pops in the fragment manager to update the variables that hold which section the app is in
        getParentFragmentManager().addOnBackStackChangedListener(
            new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager manager = getParentFragmentManager();
                int backStackCount = manager.getBackStackEntryCount();

                if (backStackCount != 0) {
                    FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(backStackCount - 1);
                    MainViewModel.SECTION_TYPE section;

                    // If a pop occurred
                    if (backStackCount < previousBackStackCount) {
                        // Catch section back navigation
                        try {
                            section = MainViewModel.SECTION_TYPE.valueOf(entry.getName());
                            viewModel.setPoppingStack(true);
                            viewModel.setCurrentSection(section);
                            viewModel.setPoppingStack(false);
                        }
                        catch (Exception e) {

                        }
                    }
                    previousBackStackCount = backStackCount;
                }
            }
        });

        viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        // Set section from intent, if any
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                int sectionIntegerID = extras.getInt(INTENT_EXTRA_SECTION);

                if (sectionIntegerID != -1) {
                    MainViewModel.SECTION_TYPE sectionID = MainViewModel.SECTION_TYPE.values()[sectionIntegerID];
                    viewModel.setCurrentSection(sectionID);

                    // Do not set the section again a config change
                    intent.removeExtra(INTENT_EXTRA_SECTION);
                }
            }
        }
    }

    // Changes the active section.
    private void changeSection(Section section) {
        replaceFragment(R.id.fragmentcontainer_main, section, initialized ? section.getType().toString() : null);
    }

    // Enum overload for changeSection().
    private void changeSection(MainViewModel.SECTION_TYPE sectionType) {
        Section section = null;

        switch (sectionType) {
            case TASK_EXPLORER:
                section = TaskExplorer.newInstance();
                break;
            case MISCELLANEOUS:
                section = MiscellaneousSection.newInstance();
                break;
            case CALENDAR:
                section = CalendarSection.newInstance();
                break;
            case TIMER:
                section = TimerSection.newInstance();
                break;
            case BAMBOO:
                section = BambooGarden.newInstance();
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

        changeSection(viewModel.getCurrentSection().getValue());

        viewModel.getCurrentSection().observe(getViewLifecycleOwner(), new Observer<MainViewModel.SECTION_TYPE>() {
            @Override
            public void onChanged(MainViewModel.SECTION_TYPE type) {
                if (!viewModel.isPoppingStack()) {
                    changeSection(type);
                }
            }
        });

        initialized = true;
    }
}
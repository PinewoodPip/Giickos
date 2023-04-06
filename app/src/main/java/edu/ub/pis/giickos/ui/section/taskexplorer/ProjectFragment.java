package edu.ub.pis.giickos.ui.section.taskexplorer;

import android.os.Bundle;

import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.MainActivity;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.ViewModelHelpers.*;
import edu.ub.pis.giickos.ui.section.Section;
import edu.ub.pis.giickos.ui.section.taskcreator.TaskCreator;

/*
    Fragment for displaying projects in TaskExplorer.
 */
public class ProjectFragment extends GiickosFragment {

    private static final String ARG_PROJECT_ID = "ProjectID";
    private static final String ARG_LABEL = "Label";
    private static final String ARG_OPEN = "Open";

    public ProjectFragment() {
        // Required empty public constructor
    }

    public static ProjectFragment newInstance(String projectID, String label, boolean open) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PROJECT_ID, projectID);
        args.putString(ARG_LABEL, label);
        args.putBoolean(ARG_OPEN, open);
        fragment.setArguments(args);

        return fragment;
    }

    // Toggles the visibility of the project's task list.
    public void toggleList(boolean open) {
        View view = getView();
        Bundle args = getArguments();

        args.putBoolean(ARG_OPEN, open);
        setArguments(args);

        if (view != null) {
            LinearLayout list = view.findViewById(R.id.list_tasks);

            list.setVisibility(open ? View.VISIBLE : View.GONE);
        }
    }

    // Overload that toggles to the opposite state.
    public void toggleList() {
        boolean isOpen = getArguments().getBoolean(ARG_OPEN);

        toggleList(!isOpen);
    }

    // Adds a task view to the project's list of tasks.
    public void addTask(TaskData task) {
        Task fragment = Task.newInstance(getProjectID(), task.id, R.drawable.placeholder_notebook, task.name);

        addChildFragment(fragment, R.id.list_tasks);
    }

    public String getProjectID() {
        return getArguments().getString(ARG_PROJECT_ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_taskexplorer_project, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        TextView titleLabel = view.findViewById(R.id.label_title);
        Button addTaskButton = view.findViewById(R.id.button_add_task);
        Bundle arguments = getArguments();
        CardView card = view.findViewById(R.id.card_main);

        // Set label and visibility of task list
        titleLabel.setText(arguments.getString(ARG_LABEL));
        toggleList(arguments.getBoolean(ARG_OPEN));

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleList();
            }
        });

        // Transition to task creator screen when the + button is pressed
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(TaskCreator.INTENT_EXTRA_PROJECT_ID, getArguments().getString(ARG_PROJECT_ID));

                MainActivity.transitionToSection(getActivity(), Section.TYPE.TASK_CREATOR, bundle);
            }
        });
    }
}
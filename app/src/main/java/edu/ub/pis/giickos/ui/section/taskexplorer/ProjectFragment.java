package edu.ub.pis.giickos.ui.section.taskexplorer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.ViewModelHelpers;
import edu.ub.pis.giickos.ui.ViewModelHelpers.*;
import edu.ub.pis.giickos.ui.dialogs.Alert;
import edu.ub.pis.giickos.ui.activities.taskcreator.Activity;
import edu.ub.pis.giickos.ui.activities.taskcreator.TaskCreator;

/*
    Fragment for displaying projects in TaskExplorer.
 */
public class ProjectFragment extends GiickosFragment {

    private static final String ARG_PROJECT_ID = "ProjectID";
    private static final String ARG_LABEL = "Label";

    private ViewModel viewModel;

    public ProjectFragment() {
        // Required empty public constructor
    }

    public static ProjectFragment newInstance(String projectID, String label) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PROJECT_ID, projectID);
        args.putString(ARG_LABEL, label);
        fragment.setArguments(args);

        return fragment;
    }

    // Toggles the visibility of the project's task list.
    public void toggleList(boolean open) {
        View view = getView();

        viewModel.setProjectOpen(getProjectID(), open);

        if (view != null) {
            LinearLayout list = view.findViewById(R.id.list_tasks);
            ImageView icon = view.findViewById(R.id.icon_folder);

            list.setVisibility(open ? View.VISIBLE : View.GONE);
            icon.setImageResource(open ? R.drawable.folder_open : R.drawable.folder);
        }
    }

    // Overload that toggles to the opposite state.
    public void toggleList() {
        boolean isOpen = viewModel.isProjectOpen(getProjectID());

        toggleList(!isOpen);
    }

    // Adds a task view to the project's list of tasks.
    public void addTask(TaskData task) {
        Task fragment = Task.newInstance(getProjectID(), task.id, R.drawable.task_icon, task.name);

        addChildFragment(fragment, R.id.list_tasks);
    }

    public String getProjectID() {
        return getArguments().getString(ARG_PROJECT_ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
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
        toggleList(viewModel.isProjectOpen(getProjectID()));

        // Clicking the card expands/collapses the task list
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleList();
            }
        });

        // Long-press on the card prompts to delete the project
        card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String msg = String.format(getString(R.string.taskexplorer_msg_deleteproject_body), arguments.getString(ARG_LABEL));
                Alert alert = new Alert(getActivity(), getString(R.string.taskexplorer_msg_deleteproject_title), msg);

                alert.setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean success = viewModel.deleteProject(getProjectID());

                        showOperationResultToast(success, getString(R.string.taskexplorer_msg_deleteproject_success), getString(R.string.taskexplorer_msg_deleteproject_error));
                    }
                });

                alert.show();

                return true;
            }
        });

        // Transition to task creator screen when the + button is pressed
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(TaskCreator.INTENT_EXTRA_PROJECT_ID, getArguments().getString(ARG_PROJECT_ID));

                Intent intent = new Intent(ProjectFragment.this.getContext(), Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
package edu.ub.pis.giickos.ui.section.taskexplorer;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.ViewModelHelpers;
import edu.ub.pis.giickos.ui.activities.taskcreator.TaskCreator;

// Displays a task within the task explorer.
public class Task extends GiickosFragment {
    public static final String ARG_PROJECT_ID = "ProjectID";
    public static final String ARG_TASK_ID = "TaskID";
    public static final String ARG_ICON = "Icon";
    public static final String ARG_LABEL = "Label";

    private int iconID;
    private String label;
    private String taskID;

    private ViewModel viewModel;

    public Task() {} // Required empty public constructor

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param iconID
     * @param label
     * @return A new instance of fragment Task.
     */
    public static Task newInstance(String projectID, String taskID, int iconID, String label) {
        Task fragment = new Task();
        Bundle args = new Bundle();

        args.putString(ARG_PROJECT_ID, projectID);
        args.putString(ARG_TASK_ID, taskID);
        args.putInt(ARG_ICON, iconID);
        args.putString(ARG_LABEL, label);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            iconID = arguments.getInt(ARG_ICON);
            label = arguments.getString(ARG_LABEL);
            taskID = arguments.getString(ARG_TASK_ID);
        }

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_section_taskexplorer_task, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        ImageView iconView = view.findViewById(R.id.icon_task);
        TextView labelView = view.findViewById(R.id.label_task_name);
        CardView card = view.findViewById(R.id.card_main);
        CardView priorityCard = view.findViewById(R.id.card_priority);
        ImageView completedIcon = view.findViewById(R.id.icon_completed);
        TextView priorityLabel = view.findViewById(R.id.label_priority);
        ViewModelHelpers.TASK_PRIORITY taskPriority = viewModel.getTaskPriority(taskID);

        iconView.setImageResource(iconID);
        labelView.setText(label);
        completedIcon.setVisibility(viewModel.isTaskCompleted(taskID) ? View.VISIBLE : View.INVISIBLE); // Show checkmark icon for completed tasks

        // Set priority badge color and label
        if (taskPriority != ViewModelHelpers.TASK_PRIORITY.NONE) {
            priorityCard.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(taskPriority.colorResource)));
            priorityLabel.setText(getString(taskPriority.stringResource));
            priorityCard.setVisibility(View.VISIBLE);
        }
        else {
            priorityCard.setVisibility(View.INVISIBLE);
        }

        // Transition to task creator on click
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskCreator.openEditActivity(Task.this.getActivity(), getArguments().getString(ARG_PROJECT_ID), getArguments().getString(ARG_TASK_ID), LocalDate.now());
            }
        });
    }
}
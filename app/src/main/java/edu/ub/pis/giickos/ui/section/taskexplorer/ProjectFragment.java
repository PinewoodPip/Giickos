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
import edu.ub.pis.giickos.R;

/*
    Fragment for displaying projects in TaskExplorer.
 */
public class ProjectFragment extends GiickosFragment {

    private static final String ARG_LABEL = "Label";
    private static final String ARG_OPEN = "Open";

    public ProjectFragment() {
        // Required empty public constructor
    }

    public static ProjectFragment newInstance(String label, boolean open) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();

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

    // TODO add params once ViewModel is implemented
    private void addTask() {
        addChildFragment(Task.newInstance(R.drawable.placeholder_notebook, "Testing"), R.id.list_tasks);
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

        // TODO remove mock code
        for (int x = 0; x < 5; x++) {
            addTask();
        }

        // TODO add task button listener
    }
}
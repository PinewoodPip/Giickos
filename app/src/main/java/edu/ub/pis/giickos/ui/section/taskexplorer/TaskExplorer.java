package edu.ub.pis.giickos.ui.section.taskexplorer;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.section.Section;

// Main fragment for the task explorer.
public class TaskExplorer extends Section {

    // TODO: ViewModel to fetch tasks

    public TaskExplorer() {} // Required empty public constructor

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TaskExplorer.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskExplorer newInstance() {
        TaskExplorer fragment = new TaskExplorer();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public TYPE getType() {
        return TYPE.TASK_EXPLORER;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_explorer, container, false);

        return view;
    }
}
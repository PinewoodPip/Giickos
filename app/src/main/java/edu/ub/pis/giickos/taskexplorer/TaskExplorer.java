package edu.ub.pis.giickos.taskexplorer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ub.pis.giickos.R;

// Main fragment for the task explorer.
public class TaskExplorer extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_explorer, container, false);

        return view;
    }
}
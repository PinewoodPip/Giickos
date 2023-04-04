package edu.ub.pis.giickos.ui.section.taskexplorer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;

// Container for tasks within TaskExplorer.
public class TaskList extends GiickosFragment {


    public TaskList() {} // Required empty public constructor

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TaskList.
     */
    public static TaskList newInstance() {
        TaskList fragment = new TaskList();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // TODO add params once viewmodel is implemented
    private void addProject() {
        ProjectFragment fragment = ProjectFragment.newInstance("Testing", false);

        addChildFragment(fragment, R.id.list_main);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        // TODO remove once ViewModel is added - just a placeholder
        for (int i = 0; i < 5; i++) {
            addProject();
        }

        return view;
    }
}
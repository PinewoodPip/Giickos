package edu.ub.pis.giickos.ui.section.taskexplorer;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.ViewModelHelpers.*;

// Container for tasks within TaskExplorer.
public class TaskList extends GiickosFragment {

    private ViewModel viewModel;

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

        // VM is shared with parent fragment (TaskExplorer)
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
    }

    private void addProject(String id) {
        ProjectData project = viewModel.getProject(id);
        List<TaskData> tasks = viewModel.getTasks(id);
        ProjectFragment fragment = ProjectFragment.newInstance(id, project.name, false); // TODO keep track of open projects in viewmodel

        addChildFragment(fragment, R.id.list_main, true);

        for (TaskData task : tasks) {
            fragment.addTask(task);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        // Render projects and tasks
        for (ProjectData project : viewModel.getProjects()) {
            addProject(project.id);
        }

        return view;
    }
}
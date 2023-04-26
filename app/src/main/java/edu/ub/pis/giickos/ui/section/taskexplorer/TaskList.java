package edu.ub.pis.giickos.ui.section.taskexplorer;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.ViewModelHelpers;
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
        ProjectFragment fragment = ProjectFragment.newInstance(id, project.name);

        addChildFragment(fragment, R.id.list_main, true);

        for (TaskData task : tasks) {
            fragment.addTask(task);
        }
    }

    private void updateProjectList() {
        View view = getView();
        LinearLayout list = view.findViewById(R.id.list_main);

        list.removeAllViews();

        // Render projects and tasks
        for (ProjectData project : viewModel.getProjects().getValue()) {
            addProject(project.id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        viewModel.getProjects().observe(getViewLifecycleOwner(), new Observer<List<ProjectData>>() {
            @Override
            public void onChanged(List<ViewModelHelpers.ProjectData> projectData) {
                updateProjectList();
            }
        });
    }
}
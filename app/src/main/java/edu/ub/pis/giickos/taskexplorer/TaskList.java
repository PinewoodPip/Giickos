package edu.ub.pis.giickos.taskexplorer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.SectionBarItem;

// Container for tasks within TaskExplorer.
public class TaskList extends Fragment {


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

    // TODO add params once ViewModel is implemented
    private void addTask() {
        FragmentManager childrenManager = getChildFragmentManager();
        FragmentTransaction childFragTrans = childrenManager.beginTransaction();
        Task task = Task.newInstance(R.drawable.placeholder_notebook, "Testing");

        childFragTrans.add(R.id.list_main, task);
        childFragTrans.addToBackStack(null);
        childFragTrans.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        // TODO remove once ViewModel is added - just a placeholder
        for (int i = 0; i < 5; i++) {
            addTask();
        }

        return view;
    }
}
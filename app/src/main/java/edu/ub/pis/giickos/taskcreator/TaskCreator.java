package edu.ub.pis.giickos.taskcreator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.section.Section;

// Section for creating tasks.
public class TaskCreator extends Section {

    // TODO viewmodel

    public TaskCreator() {} // Required empty public constructor

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TaskCreator.
     */
    public static TaskCreator newInstance() {
        TaskCreator fragment = new TaskCreator();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void addTextField(int iconID, String label) {
        TaskField field = TaskField.newInstance(iconID, label);

        addChildFragment(field, R.id.list_main);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_creator, container, false);

        // TODO remove - mock up
        addTextField(R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_title));
        addTextField(R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_title));

        return view;
    }
}
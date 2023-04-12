package edu.ub.pis.giickos.ui.section.taskexplorer;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProvider;

import edu.ub.pis.giickos.MainActivity;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.form.TextField;
import edu.ub.pis.giickos.ui.section.Section;

// Main fragment for the task explorer.
public class TaskExplorer extends Section {

    private ViewModel viewModel;

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

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
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

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        Button addProjectButton = view.findViewById(R.id.button_newproject);

        // Open dialog for creating projects
        addProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.taskexplorer_label_addproject));

                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_createproject, (ViewGroup) getView(), false);
                EditText textField = viewInflated.findViewById(R.id.textfield_name);
                builder.setView(viewInflated);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String projectName = textField.getText().toString();
                        boolean success = viewModel.createProject(projectName);

                        showOperationResultToast(success, getString(R.string.taskexplorer_msg_addproject_success), getString(R.string.taskexplorer_msg_addproject_error));

                        if (success) {
                            // TODO use an event / livedata instead to trigger a refresh of the UI
                            MainActivity.transitionToSection(getActivity(), TYPE.TASK_EXPLORER, null, true);
                        }

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }
}
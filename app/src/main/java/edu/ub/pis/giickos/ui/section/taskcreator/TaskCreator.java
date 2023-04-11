package edu.ub.pis.giickos.ui.section.taskcreator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.ub.pis.giickos.MainActivity;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.ViewModelHelpers.*;
import edu.ub.pis.giickos.ui.dialogs.Alert;
import edu.ub.pis.giickos.ui.generic.form.FormCard;
import edu.ub.pis.giickos.ui.generic.form.FormSpinner;
import edu.ub.pis.giickos.ui.main.DatePickerListener;
import edu.ub.pis.giickos.ui.main.TimePickerListener;
import edu.ub.pis.giickos.ui.section.Section;

// Section for creating tasks.
public class TaskCreator extends Section {

    public static String INTENT_EXTRA_PROJECT_ID = "ProjectID";
    public static String INTENT_EXTRA_TASK_ID = "TaskID"; // If present the UI will be in edit mode
    // TODO edit mode
    private ViewModel viewModel;

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

        this.viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        // Initialize values from intent
        Optional<String> projectID = getIntentString(INTENT_EXTRA_PROJECT_ID);
        if (projectID.isPresent()) {
            viewModel.setProjectID(projectID.get());
        }
    }

    @Override
    public TYPE getType() {
        return TYPE.TASK_CREATOR;
    }

    // Returns whether the fragment is being used to create a task (instead of editing one)
    private boolean isCreating() {
        return !getIntentString(INTENT_EXTRA_TASK_ID).isPresent();
    }

    private String getPriorityName(ViewModel.TASK_PRIORITY priority) {
        return getString(priority.stringResource);
    }

    private FormCard addField(int iconID, String label, int backgroundColor) {
        FormCard field = FormCard.newInstance(iconID, label, backgroundColor);

        addChildFragment(field, R.id.list_main, true);

        return field;
    }

    // Overload with no background color override.
    private FormCard addField(int iconID, String label) {
        return addField(iconID, label, -1);
    }

    private void addTextField(int iconID, String label, String inputLabel, int inputType, @Nullable TextWatcher listener) {
        FormCard field = addField(iconID, label);

        field.addTextField(inputType, inputLabel, listener);
    }

    // Adds a field with a click listener.
    private void addClickableField(int iconID, String label, int backgroundColor, View.OnClickListener listener) {
        FormCard field = addField(iconID, label, backgroundColor);

        field.setClickListener(listener);
    }

    private void addTimeField(String id, int iconID, String label, String timeLabel, TimePickerListener listener) {
        FormCard field = addField(iconID, label);

        field.addTimeField(id, timeLabel, listener);
    }

    private void addDateField(String id, int iconID, String label, String dateLabel, DatePickerListener listener) {
        FormCard field = addField(iconID, label);

        field.addDateField(id, dateLabel, listener);
    }

    private FormSpinner addSpinnerField(int iconID, String label, List<Object> items, int selectedIndex) {
        FormCard field = addField(iconID, label);
        List<String> itemStrings = new ArrayList<>();
        for (Object item : items) {
            itemStrings.add(item.toString());
        }

        return field.addSpinner(itemStrings, selectedIndex);
    }

    private void setupProjectSpinner() {
        Bundle extras = getActivity().getIntent().getExtras();
        List<ProjectData> projects = viewModel.getProjects();
        List<Object> projectObjects = new ArrayList<>();
        projectObjects.addAll(projects);
        FormSpinner spinner;

        // Restore spinner choice
        String projectGUID = viewModel.getProjectID();
        int spinnerIndex = 0;
        if (projectGUID != null) {
            for (int i = 0; i < projects.size(); i++) {
                if (projects.get(i).id.equals(projectGUID)) {
                    spinnerIndex = i;
                    break;
                }
            }
        }
        else {
            viewModel.setProjectID(projects.get(0).id); // TODO what if there are no projects!
        }

        spinner = addSpinnerField(R.drawable.placeholder, getString(R.string.taskcreator_label_project), projectObjects, spinnerIndex);
        spinner.setListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TODO", "Project selected " + projects.get(i).name);

                viewModel.setProjectID(projects.get(i).id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.e("UI", "Project unselected; this should not happen");
            }
        });
    }

    private void setupPrioritySpinner() {
        List options = new ArrayList<>();
        FormSpinner spinner;

        for (int x = 0; x < ViewModel.TASK_PRIORITY.values().length; x++) {
            options.add(getPriorityName(ViewModel.TASK_PRIORITY.values()[x]));
        }

        spinner = addSpinnerField(R.drawable.placeholder, getString(R.string.taskcreator_label_priority), options, viewModel.getPriority());
        spinner.setListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("View", "Priority selected " + Integer.toString(i));
                viewModel.setPriority(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.e("UI", "Current priority removed from spinner; this should not happen");
            }
        });
    }

    private String formatDate(ViewModel.TaskDate date) {
        String str = date == null ? "" : String.format("%d/%d/%d", date.day, date.month, date.year);

        return str;
    }

    private String formatTime(ViewModel.TaskTime time) {
        String str = time == null ? "" : String.format("%d:%d", time.hour, time.minute); // TODO prettify

        return str;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_creator, container, false);

        if (!isCreating()) {
            viewModel.setTaskID(getIntentString(INTENT_EXTRA_TASK_ID).get());
        }

        setupProjectSpinner();

        addTextField(R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_title), viewModel.getTaskName(), InputType.TYPE_TEXT_VARIATION_PERSON_NAME, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.setTaskName(editable.toString());
            }
        });

        addDateField("Date", R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_date), formatDate(viewModel.getStartDate()), new DatePickerListener() {
            @Override
            public void dateSet(String id, int year, int month, int day) {
                Log.d("View", "Date set");
                viewModel.setStartDate(new ViewModel.TaskDate(day, month, year));
            }
        });

        setupPrioritySpinner();

        TimePickerListener timePickerListener = new TimePickerListener() {
            @Override
            public void timeSet(String pickerID, int hour, int minute) {
                Log.d("View", "Time set for " + pickerID);
                ViewModel.TaskTime time = new ViewModel.TaskTime(hour, minute);

                if (pickerID.equals("StartTime")) {
                    viewModel.setStartTime(time);
                }
                else if (pickerID.equals("EndTime")) {
                    viewModel.setEndTime(time);
                }
                else {
                    Log.e("View", "Unknown time picker listener ID in TaskCreator: " + pickerID);
                }
            }
        };

        addTimeField("StartTime", R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_time_start), formatTime(viewModel.getStartTime()), timePickerListener);
        addTimeField("EndTime", R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_time_end), formatTime(viewModel.getEndTime()), timePickerListener);

        // TODO move to separate tab
        addTextField(R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_details), viewModel.getTaskDescription(), InputType.TYPE_TEXT_FLAG_MULTI_LINE, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.setTaskDescription(editable.toString());
            }
        });

        // Only add delete and complete buttons while editing
        if (!isCreating()) {
            // Save button
            addClickableField(R.drawable.placeholder, getString(R.string.generic_label_save), getResources().getColor(R.color.positive_action), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean success = viewModel.updateTask();
                    int stringID = success ? R.string.taskcreator_msg_update_success : R.string.taskcreator_msg_update_error;

                    Toast.makeText(getContext(), getString(stringID), Toast.LENGTH_SHORT).show();
                }
            });

            // "Mark as complete" button
            addClickableField(R.drawable.placeholder, getString(R.string.taskcreator_label_complete), getResources().getColor(R.color.positive_action), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TODO", "Complete button clicked");
                }
            });

            // Delete button
            addClickableField(R.drawable.placeholder, getString(R.string.generic_label_delete), getResources().getColor(R.color.destructive_action), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Alert alert = new Alert(getActivity(), getString(R.string.taskcreator_msg_delete_title), getString(R.string.taskcreator_msg_delete_body));

                    alert.setPositiveButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int _) {
                            boolean success = viewModel.deleteTask();

                            showOperationResultToast(success, getString(R.string.taskcreator_msg_deleted_success), getString(R.string.taskcreator_msg_deleted_error));

                            // Return to task explorer on success, and prevent returning to this activity
                            if (success) {
                                MainActivity.transitionToSection(getActivity(), TYPE.TASK_EXPLORER, null, true);
                            }

                            dialog.dismiss();
                        }
                    });

                    alert.show();
                }
            });
        }
        else {
            // Otherwise add button to create the task
            addClickableField(R.drawable.placeholder, getString(R.string.generic_label_create), getResources().getColor(R.color.positive_action), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean success = viewModel.createTask();
                    int stringID = success ? R.string.taskcreator_msg_creation_success : R.string.taskcreator_msg_creation_error;

                    Toast.makeText(getContext(), getString(stringID), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
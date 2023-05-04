package edu.ub.pis.giickos.ui.activities.taskcreator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.ViewModelHelpers.ProjectData;
import edu.ub.pis.giickos.ui.ViewModelHelpers.TaskDate;
import edu.ub.pis.giickos.ui.ViewModelHelpers.TaskTime;
import edu.ub.pis.giickos.ui.dialogs.Alert;
import edu.ub.pis.giickos.ui.generic.Switch;
import edu.ub.pis.giickos.ui.generic.form.FormCard;
import edu.ub.pis.giickos.ui.generic.form.FormSpinner;
import edu.ub.pis.giickos.ui.generic.form.TextField;
import edu.ub.pis.giickos.ui.generic.DatePickerListener;
import edu.ub.pis.giickos.ui.generic.TimePickerListener;

// Section for creating tasks.
public class TaskCreator extends GiickosFragment {

    public static String INTENT_EXTRA_PROJECT_ID = "ProjectID";
    public static String INTENT_EXTRA_TASK_ID = "TaskID"; // If present, the UI will open in edit mode
    public static String INTENT_EXTRA_TASK_SETDATETIME = "TaskSetDateTime";
    public static String INTENT_EXTRA_TASK_DATE_DAY = "TaskDateDay";
    public static String INTENT_EXTRA_TASK_DATE_MONTH = "TaskDateMonth";
    public static String INTENT_EXTRA_TASK_DATE_YEAR = "TaskDateYear";
    public static String INTENT_EXTRA_TASK_TIME_HOUR = "TaskTimeHour";

    private ViewModel viewModel;

    public TaskCreator() {} // Required empty public constructor

    public static TaskCreator newInstance() {
        TaskCreator fragment = new TaskCreator();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public static void openEditActivity(FragmentActivity source, String projectID, String taskID) {
        Bundle bundle = new Bundle();
        bundle.putString(TaskCreator.INTENT_EXTRA_PROJECT_ID, projectID);
        bundle.putString(TaskCreator.INTENT_EXTRA_TASK_ID, taskID);

        Intent intent = new Intent(source, Activity.class);
        intent.putExtras(bundle);
        source.startActivity(intent);
    }

    public static void openCreateActivity(FragmentActivity source, TaskDate date, TaskTime time) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(INTENT_EXTRA_TASK_SETDATETIME, true);
        bundle.putInt(TaskCreator.INTENT_EXTRA_TASK_DATE_YEAR, date.year);
        bundle.putInt(TaskCreator.INTENT_EXTRA_TASK_DATE_MONTH, date.month);
        bundle.putInt(TaskCreator.INTENT_EXTRA_TASK_DATE_DAY, date.day);
        bundle.putInt(TaskCreator.INTENT_EXTRA_TASK_TIME_HOUR, time.hour);

        Intent intent = new Intent(source, Activity.class);
        intent.putExtras(bundle);
        source.startActivity(intent);
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
        else if (getIntentBoolean(INTENT_EXTRA_TASK_SETDATETIME)) {
            int year = getIntentInteger(INTENT_EXTRA_TASK_DATE_YEAR).get().intValue();
            int month = getIntentInteger(INTENT_EXTRA_TASK_DATE_MONTH).get().intValue();
            int day = getIntentInteger(INTENT_EXTRA_TASK_DATE_DAY).get().intValue();
            int hour = getIntentInteger(INTENT_EXTRA_TASK_TIME_HOUR).get().intValue();
            TaskDate date = new TaskDate(day, month, year);
            TaskTime time = new TaskTime(hour, 0);

            viewModel.setStartDate(date);
            viewModel.setStartTime(time);
        }
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

    private void addSwitchField(int iconID, String label, int backgroundColor, boolean checked, CompoundButton.OnCheckedChangeListener listener) {
        FormCard card = addField(iconID, label, backgroundColor);
        edu.ub.pis.giickos.ui.generic.Switch switchFragment = Switch.newInstance(true);
        switchFragment.setListener(listener);
        switchFragment.setChecked(checked);

        card.addElement(switchFragment);
        card.setListDirection(LinearLayout.LAYOUT_DIRECTION_RTL);
    }

    private void addTimeField(String id, int iconID, String label, String timeLabel, TimePickerListener listener) {
        FormCard field = addField(iconID, label);

        field.addTimeField(id, timeLabel, listener);
    }

    private void addDateField(String id, int iconID, String label, String dateLabel, DatePickerListener listener) {
        FormCard field = addField(iconID, label);

        field.addDateField(id, dateLabel, listener);
    }

    private FormCard addNumberField(int value, int minValue, int maxValue, int iconID, String label, NumberPicker.OnValueChangeListener listener, NumberPicker.Formatter formatter) {
        FormCard card = addField(iconID, label);
        card.addNumberField(value, minValue, maxValue, listener, formatter);

        return card;
    }

    private FormSpinner addSpinnerField(int iconID, String label, List<Object> items, int selectedIndex) {
        FormCard field = addField(iconID, label);
        List<String> itemStrings = new ArrayList<>();
        for (Object item : items) {
            itemStrings.add(item.toString());
        }

        return field.addSpinner(itemStrings, selectedIndex);
    }

    private void setupRepeatModeSpinner() {
        List<Object> options = new ArrayList<>();
        FormSpinner spinner;

        // Create an option for each repeat mode
        for (int i = 0; i < ViewModel.TASK_REPEAT_MODE.values().length; i++) {
            options.add(getString(ViewModel.TASK_REPEAT_MODE.values()[i].stringResource));
        }

        spinner = addSpinnerField(R.drawable.reuse, getString(R.string.taskcreator_label_time_repeat), options, viewModel.getRepeatMode().ordinal());

        spinner.setListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("UI", "Repeat mode set to " + Integer.toString(i));

                viewModel.setRepeatMode(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.e("UI", "Repeat mode unselected; this should not happen");
            }
        });
    }

    private void setupProjectSpinner() {
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

        spinner = addSpinnerField(R.drawable.folder, getString(R.string.taskcreator_label_project), projectObjects, spinnerIndex);
        spinner.setListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("UI", "Project selected " + projects.get(i).name);

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

        spinner = addSpinnerField(R.drawable.priority, getString(R.string.taskcreator_label_priority), options, viewModel.getPriority());
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

    private String formatDate(TaskDate date) {
        String str = date == null ? "" : String.format("%d/%d/%d", date.day, date.month, date.year);

        return str;
    }

    private String formatTime(TaskTime time) {
        return time != null ? new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(2000, 1, 1, time.hour, time.minute)) : "";
    }

    private void finishActivity() {
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_creator, container, false);

        if (!isCreating()) {
            viewModel.setTaskID(getIntentString(INTENT_EXTRA_TASK_ID).get());
        }

        setupProjectSpinner();

        addTextField(R.drawable.title, getString(R.string.taskcreator_label_title), viewModel.getTaskName(), InputType.TYPE_TEXT_VARIATION_PERSON_NAME, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.setTaskName(editable.toString());
            }
        });

        addDateField("Date", R.drawable.timer, getString(R.string.taskcreator_label_date), formatDate(viewModel.getStartDate()), new DatePickerListener() {
            @Override
            public void dateSet(String id, int year, int month, int day) {
                Log.d("View", "Date set");
                viewModel.setStartDate(new TaskDate(day, month, year));
            }
        });

        setupPrioritySpinner();

        TimePickerListener timePickerListener = new TimePickerListener() {
            @Override
            public void timeSet(String pickerID, int hour, int minute) {
                Log.d("View", "Time set for " + pickerID);
                TaskTime time = new TaskTime(hour, minute);

                if (pickerID.equals("StartTime")) {
                    viewModel.setStartTime(time);
                }
                else {
                    Log.e("View", "Unknown time picker listener ID in TaskCreator: " + pickerID);
                }
            }
        };

        addTimeField("StartTime", R.drawable.timer, getString(R.string.taskcreator_label_time_start), formatTime(viewModel.getStartTime()), timePickerListener);

        FormCard durationCard = addNumberField(viewModel.getDurationInMinutes(), ViewModel.MIN_DURATION, ViewModel.MAX_DURATION, R.drawable.timer, getString(R.string.taskcreator_label_duration), new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int minutes) {
                viewModel.setDurationInMinutes(minutes);
            }
        }, null);
        durationCard.addElement(TextField.newInstance(getString(R.string.taskcreator_label_duration_field), InputType.TYPE_TEXT_VARIATION_PERSON_NAME, false));

        setupRepeatModeSpinner();

        // TODO move to separate tab
        addTextField(R.drawable.description_white, getString(R.string.taskcreator_label_details), viewModel.getTaskDescription(), InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE, new TextWatcher() {
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
            addClickableField(R.drawable.description_white, getString(R.string.generic_label_save), getResources().getColor(R.color.positive_action), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean success = viewModel.updateTask();
                    int stringID = success ? R.string.taskcreator_msg_update_success : R.string.taskcreator_msg_update_error;

                    Toast.makeText(getContext(), getString(stringID), Toast.LENGTH_SHORT).show();

                    if (success) {
                        finishActivity();
                    }
                }
            });

            // "Mark as complete" button
            addSwitchField(R.drawable.folder_task_completed, getString(R.string.taskcreator_label_complete), getResources().getColor(R.color.positive_action), viewModel.isCompleted(), new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    viewModel.setCompleted(b);
                }
            });

            // Delete button
            addClickableField(R.drawable.delete_account, getString(R.string.generic_label_delete), getResources().getColor(R.color.destructive_action), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Alert alert = new Alert(getActivity(), getString(R.string.taskcreator_msg_delete_title), getString(R.string.taskcreator_msg_delete_body));

                    alert.setPositiveButton(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int _) {
                            boolean success = viewModel.deleteTask();

                            showOperationResultToast(success, getString(R.string.taskcreator_msg_deleted_success), getString(R.string.taskcreator_msg_deleted_error));

                            if (success) {
                                finishActivity();
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

                    if (success) {
                        finishActivity();
                    }
                }
            });
        }

        return view;
    }
}
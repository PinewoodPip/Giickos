package edu.ub.pis.giickos.ui.section.taskcreator;

import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.form.FormCard;
import edu.ub.pis.giickos.ui.generic.form.FormSpinner;
import edu.ub.pis.giickos.ui.main.DatePickerListener;
import edu.ub.pis.giickos.ui.main.TimePickerListener;
import edu.ub.pis.giickos.ui.section.Section;

// Section for creating tasks.
public class TaskCreator extends Section {

    public static String INTENT_EXTRA_PROJECT_ID = "ProjectID";
    public static String INTENT_EXTRA_TASK_ID = "TaskID"; // If present the UI will be in edit mode

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

    @Override
    public TYPE getType() {
        return TYPE.TASK_CREATOR;
    }

    // Returns whether the fragment is being used to create a task (instead of editing one)
    private boolean isCreating() {
        return !getIntentString(INTENT_EXTRA_TASK_ID).isPresent();
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

    private void addTextField(int iconID, String label, String inputLabel, int inputType) {
        FormCard field = addField(iconID, label);

        field.addTextField(inputType, inputLabel);
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




    private FormSpinner addTag(int iconID, String label, List<Object> items, int selectedIndex) {
        FormCard field = addField(iconID, label);

        return field.addSpinner(items, selectedIndex);
    }




    private void addDateField(String id, int iconID, String label, String dateLabel, DatePickerListener listener) {
        FormCard field = addField(iconID, label);

        field.addDateField(id, dateLabel, listener);
    }

    private FormSpinner addSpinnerField(int iconID, String label, List<Object> items, int selectedIndex) {
        FormCard field = addField(iconID, label);

        return field.addSpinner(items, selectedIndex);
    }

    private void setupProjectSpinner() {
        List projectDescriptors = new ArrayList<>();
        List Tags = new ArrayList<>();
        Bundle extras = getActivity().getIntent().getExtras();
        FormSpinner spinner,spinnerTag;

        // TODO replace once viewmodel is implemented
        projectDescriptors.add(new ProjectDescriptor("1", "TEMP1"));
        projectDescriptors.add(new ProjectDescriptor("2", "TEMP2"));
        projectDescriptors.add(new ProjectDescriptor("3", "TEMP3"));


        Tags.add(new TagDescriptor("1", "HIGH"));
        Tags.add(new TagDescriptor("2", "MEDIUM"));
        Tags.add(new TagDescriptor("3", "LOW"));


        spinnerTag = addSpinnerField(R.drawable.placeholder, getString(R.string.taskcreator_label_priority), Tags, 0);

        spinnerTag.setListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TODO", "Priority selected " + Tags.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("TODO", "Priority unselected ");
            }
        });

        spinner = addSpinnerField(R.drawable.placeholder, getString(R.string.taskcreator_label_project), projectDescriptors, 0);
        spinner.setListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TODO", "Project selected " + projectDescriptors.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("TODO", "Project unselected ");
            }
        });

        // Set selection
        if (extras != null) {
            String projectID = extras.getString(INTENT_EXTRA_PROJECT_ID);
            Optional<ProjectDescriptor> desc = projectDescriptors.stream().filter(new Predicate() {
                @Override
                public boolean test(Object o) {
                    ProjectDescriptor desc = (ProjectDescriptor) o;
                    return desc.getId().equals(projectID);
                }
            }).findFirst();

            if (desc.isPresent()) {
                int index = projectDescriptors.indexOf(desc.get());

                spinner.setSelection(index);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_creator, container, false);

        setupProjectSpinner();

        addTextField(R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_title), "", InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        addDateField("Date", R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_date), "", new DatePickerListener() {
            @Override
            public void dateSet(String id, int year, int month, int day) {
                Log.d("TODO", "Date set");
            }
        });

        TimePickerListener timePickerListener = new TimePickerListener() {
            @Override
            public void timeSet(String pickerID, int hour, int minute) {
                Log.d("TODO", "Time set");
            }
        };

        addTimeField("StartTime", R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_time_start), "", timePickerListener);
        addTimeField("EndTime", R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_time_end), "", timePickerListener);
        addTextField(R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_details), "", InputType.TYPE_TEXT_FLAG_MULTI_LINE); // TODO move to separate tab

        // Only add delete button while editing
        if (!isCreating()) {
            addClickableField(R.drawable.placeholder, getString(R.string.generic_label_delete), getResources().getColor(R.color.destructive_action), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TODO", "Delete button clicked");
                }
            });
        }

        return view;
    }
}
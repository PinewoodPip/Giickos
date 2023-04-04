package edu.ub.pis.giickos.ui.section.taskcreator;

import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.form.FormCard;
import edu.ub.pis.giickos.ui.generic.form.FormSpinner;
import edu.ub.pis.giickos.ui.main.DatePickerListener;
import edu.ub.pis.giickos.ui.main.TimePickerListener;
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

    @Override
    public TYPE getType() {
        return TYPE.TASK_CREATOR;
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

    private void addDateField(String id, int iconID, String label, String dateLabel, DatePickerListener listener) {
        FormCard field = addField(iconID, label);

        field.addDateField(id, dateLabel, listener);
    }

    private FormSpinner addSpinnerField(int iconID, String label, List<Object> items) {
        FormCard field = addField(iconID, label);

        return field.addSpinner(items);
    }

    private void setupProjectSpinner() {
        List projectDescriptors = new ArrayList<>();
        FormSpinner spinner;

        // TODO replace once viewmodel is implemented
        projectDescriptors.add(new ProjectDescriptor("1", "TEMP"));
        projectDescriptors.add(new ProjectDescriptor("2", "TEMP2"));
        projectDescriptors.add(new ProjectDescriptor("3", "TEMP3"));

        spinner = addSpinnerField(R.drawable.placeholder, getString(R.string.taskcreator_label_project), projectDescriptors);
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

        addClickableField(R.drawable.placeholder, getString(R.string.generic_label_delete), getResources().getColor(R.color.destructive_action), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TODO", "Delete button clicked");
            }
        });

        return view;
    }
}
package edu.ub.pis.giickos.ui.section.taskcreator;

import android.graphics.Color;
import android.os.Bundle;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.DatePickerListener;
import edu.ub.pis.giickos.ui.main.TimePicker;
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

    private TaskField addField(int iconID, String label, int backgroundColor) {
        TaskField field = TaskField.newInstance(iconID, label, backgroundColor);

        addChildFragment(field, R.id.list_main, true);

        return field;
    }

    // Overload with no background color override.
    private TaskField addField(int iconID, String label) {
        return addField(iconID, label, -1);
    }

    private void addTextField(int iconID, String label, int inputType) {
        TaskField field = addField(iconID, label);

        // Add text field
        TaskTextField textField = TaskTextField.newInstance("", inputType);
        field.addElement(textField);
    }

    private void addTimeField(String id, int iconID, String label) {
        TaskField field = addField(iconID, label);

        // Add text field
        TaskTime textField = TaskTime.newInstance(id, "");
        field.addElement(textField);

        // Open time picker on tap
        textField.setListener(new TimePickerListener() {
            @Override
            public void timeSet(String pickerID, int hour, int minute) {
                // TODO update viewmodel
                textField.setText(String.format("%d:%d", hour, minute)); // TODO display nicely
            }
        });
    }

    private void addDateField(String id, int iconID, String label) {
        TaskField field = addField(iconID, label);

        // Add date field
        TaskDate dateField = TaskDate.newInstance(id, "");
        field.addElement(dateField);

        dateField.setListener(new DatePickerListener() {
            @Override
            public void dateSet(String id, int year, int month, int day) {
                // TODO update viewmodel
                dateField.setText(String.format("%d/%d/%d", day, month, year)); // TODO display nicely
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_creator, container, false);

        addTextField(R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_title), InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        addDateField("Date", R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_date));
        addTimeField("StartTime", R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_time_start));
        addTimeField("EndTime", R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_time_end));
        addField(R.drawable.placeholder, getString(R.string.generic_label_delete), getResources().getColor(R.color.destructive_action));

        return view;
    }
}
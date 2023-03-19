package edu.ub.pis.giickos.ui.section.taskcreator;

import android.os.Bundle;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import edu.ub.pis.giickos.R;
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

    private void addTextField(int iconID, String label, int inputType) {
        TaskField field = TaskField.newInstance(iconID, label);

        addChildFragment(field, R.id.list_main, true);

        // Add text field
        TaskTextField textField = TaskTextField.newInstance("", inputType);
        field.addElement(textField);
    }

    private void addTimeField(String id, int iconID, String label) {
        TaskField field = TaskField.newInstance(iconID, label);

        addChildFragment(field, R.id.list_main, true);

        // Add text field
        TaskTime textField = TaskTime.newInstance(id, "");
        field.addElement(textField);

        textField.setListener(new TimePickerListener() {
            @Override
            public void timeSet(String pickerID, int hour, int minute) {
                // TODO update viewmodel
                textField.setText(String.format("%d:%d", hour, minute)); // TODO display nicely
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_creator, container, false);

        addTextField(R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_title), InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        addTimeField("StartTime", R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_time_start));
        addTimeField("EndTime", R.drawable.placeholder_notebook, getString(R.string.taskcreator_label_time_end));

        return view;
    }
}
package edu.ub.pis.giickos.ui.generic.form;

import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.fragment.app.Fragment;

import java.util.List;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.DatePickerListener;
import edu.ub.pis.giickos.ui.generic.TimePickerListener;

public abstract class ContainerCard extends GiickosFragment {

    protected int listDirection;

    protected abstract int getContainerID();

    public void addElement(Fragment fragment) {
        addChildFragment(fragment, getContainerID(), true);
    }

    public TextField addTextField(int inputType, String label, TextWatcher listener, String hintLabel) {
        TextField textField = TextField.newInstance(label, inputType, true, hintLabel);
        addElement(textField);

        textField.setListener(listener);

        return textField;
    }
    public TextField addTextFieldColor(int inputType, String label, TextWatcher listener, String hintLabel, int color, boolean editable) {
        TextField textField = TextField.newInstance(label, inputType, editable, color, hintLabel);
        addElement(textField);

        textField.setListener(listener);

        return textField;
    }

    public TextField addTextField(int inputType, String label, TextWatcher listener) {
        return addTextField(inputType, label, listener, "");
    }

    public TimeField addTimeField(String id, String label, TimePickerListener listener) {
        TimeField timeField = TimeField.newInstance(id, label);
        addElement(timeField);

        timeField.setListener(listener);

        return timeField;
    }

    public DateField addDateField(String id, String label, DatePickerListener listener) {
        DateField dateField = DateField.newInstance(id, label);
        addElement(dateField);

        dateField.setListener(listener);

        return dateField;
    }

    public NumberField addNumberField(int value, int minValue, int maxValue, NumberPicker.OnValueChangeListener listener, NumberPicker.Formatter formatter) {
        NumberField field = NumberField.newInstance(value, minValue, maxValue);
        addElement(field);

        field.setListener(listener);
        field.setFormatter(formatter);

        return field;
    }

    public FormSpinner addSpinner(List<String> items, int selectedIndex) {
        FormSpinner spinner = FormSpinner.newInstance(items, selectedIndex);
        addElement(spinner);

        return spinner;
    }

    public FormSpinner addSpinnerColor(List<String> items, int selectedIndex, int color) {

        FormSpinner spinner = FormSpinner.newInstance(items, selectedIndex,color);
        addElement(spinner);
        return addSpinner(items, 0);
    }

    // Sets the direction of the list of child elements added via addElement().
    public void setListDirection(int direction) {
        View view = getView();

        this.listDirection = direction;

        if (view != null) {
            LinearLayout listView = view.findViewById(getContainerID());

            listView.setLayoutDirection(listDirection);
        }
    }
}

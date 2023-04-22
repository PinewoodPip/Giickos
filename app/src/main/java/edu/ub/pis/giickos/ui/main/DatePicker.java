package edu.ub.pis.giickos.ui.main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

// Dialog for picking a date.
// https://developer.android.com/develop/ui/views/components/pickers#java
public class DatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private String id;
    private DatePickerListener listener;

    public DatePicker(String id) {
        super();
        this.id = id;
    }

    public void setListener(DatePickerListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        if (listener != null) {
            listener.dateSet(id, year, month + 1, day); // Month is changed to be 1-based
        }
    }
}

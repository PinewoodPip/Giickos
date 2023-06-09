package edu.ub.pis.giickos.ui.generic;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.text.format.DateFormat;

import java.util.Calendar;

// Dialog for picking a time (hour and minutes).
// https://developer.android.com/develop/ui/views/components/pickers#java
public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private String id;
    private TimePickerListener listener;

    public TimePicker(String id) {
        super();
        this.id = id;
    }

    public void setListener(TimePickerListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        if (listener != null) {
            listener.timeSet(id, hourOfDay, minute);
        }
    }
}
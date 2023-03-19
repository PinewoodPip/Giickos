package edu.ub.pis.giickos.ui.main;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import edu.ub.pis.giickos.R;

// Dialog for picking a time (hour and minutes)
public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private String id;
    private TimePickerListener listener;

    public TimePicker(String id) {
        super();
        this.id = id;
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

    public void setListener(TimePickerListener listener) {
        this.listener = listener;
    }
}
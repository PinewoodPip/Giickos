package edu.ub.pis.giickos.ui.section.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kizitonwose.calendar.core.Week;
import com.kizitonwose.calendar.view.ViewContainer;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

import edu.ub.pis.giickos.R;

public class WeekHeaderHolder extends ViewContainer {

    public WeekHeaderHolder(View view) {
        super(view);
    }

    public void bind(Context context, @NonNull ViewContainer container, Week week) {
        View view = container.getView();
        TextView monthLabel = view.findViewById(R.id.label_month);
        TextView weekLabel = view.findViewById(R.id.label_week);

        // The first day of the week is used for the week check
        LocalDate date = week.getDays().get(0).getDate();

        Calendar calendarData = Calendar.getInstance();
        calendarData.set(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
        calendarData.setMinimalDaysInFirstWeek(1);

        TypedArray monthArray = context.getResources().obtainTypedArray(R.array.months);
        String monthString = String.format(Locale.getDefault(), "%s %d", monthArray.getString(date.getMonth().getValue() - 1), date.getYear()); // month.getValue() is 1-based
        String weekString = String.format(Locale.getDefault(), "%s %d", context.getString(R.string.generic_label_week), calendarData.get(Calendar.WEEK_OF_MONTH));

        monthLabel.setText(monthString);
        weekLabel.setText(weekString);
    }
}

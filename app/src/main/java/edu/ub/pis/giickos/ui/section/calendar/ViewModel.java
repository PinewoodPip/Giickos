package edu.ub.pis.giickos.ui.section.calendar;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;

public class ViewModel extends androidx.lifecycle.ViewModel {

    // Maximum amount of weeks the calendar can be used in the past/future
    public static final int MAX_FUTURE_WEEKS = 5;
    public static final int MAX_PAST_WEEKS = 5;

    public static final int HOURS_IN_DAY = 24;

    @Nullable private MutableLiveData<LocalDate> selectedDate; // The day selected by the user
    private LocalDate currentWeekDate; // The date that the calendar is displaying the week of

    public ViewModel() {
        selectedDate = new MutableLiveData<LocalDate>(null);

        setCurrentWeekDate(LocalDate.now());
    }

    @Nullable
    public LiveData<LocalDate> getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(@Nullable LocalDate selectedDate) {
        this.selectedDate.setValue(selectedDate);
    }

    public LocalDate getCurrentWeekDate() {
        return currentWeekDate;
    }

    public void setCurrentWeekDate(LocalDate currentWeekDate) {
        this.currentWeekDate = currentWeekDate;
    }
}

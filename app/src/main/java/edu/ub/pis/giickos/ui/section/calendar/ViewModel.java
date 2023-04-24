package edu.ub.pis.giickos.ui.section.calendar;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import edu.ub.pis.giickos.model.ModelHolder;
import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.ui.ViewModelHelpers;

public class ViewModel extends androidx.lifecycle.ViewModel {

    // Maximum amount of weeks the calendar can be used in the past/future
    public static final int MAX_FUTURE_WEEKS = 5;
    public static final int MAX_PAST_WEEKS = 5;

    public static final int HOURS_IN_DAY = 24;

    private ProjectManager model;

    @Nullable private MutableLiveData<LocalDate> selectedDate; // The day selected by the user
    private LocalDate currentWeekDate; // The date that the calendar is displaying the week of

    public ViewModel() {
        model = ModelHolder.INSTANCE.getProjectManager();
        LocalDate currentDate = model.getCurrentTime();

        // Defaults to current date
        selectedDate = new MutableLiveData<LocalDate>(currentDate);
        setCurrentWeekDate(currentDate);
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

    // Returns the tasks for the selected day.
    public Set<ViewModelHelpers.TaskData> getTasks() {
        Set<ViewModelHelpers.TaskData> tasks = new HashSet<>();

        for (Task task : model.getTasksForDay(getSelectedDate().getValue().atStartOfDay())) {
            tasks.add(new ViewModelHelpers.TaskData(task));
        }

        return tasks;
    }
}

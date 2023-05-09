package edu.ub.pis.giickos.ui.section.calendar;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import edu.ub.pis.giickos.model.ModelHolder;
import edu.ub.pis.giickos.model.observer.ObservableEvent;
import edu.ub.pis.giickos.model.observer.Observer;
import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.ui.ViewModelHelpers;

public class ViewModel extends androidx.lifecycle.ViewModel {

    // Maximum amount of weeks the calendar can be used in the past/future
    public static final int MAX_FUTURE_WEEKS = 5;
    public static final int MAX_PAST_WEEKS = 5;

    public static final int HOURS_IN_DAY = 24;

    private ProjectManager model;
    private Observer tasksObserver;

    @Nullable private MutableLiveData<LocalDate> selectedDate; // The day selected by the user
    private LocalDate currentWeekDate; // The date that the calendar is displaying the week of
    private MutableLiveData<Set<ViewModelHelpers.TaskData>> tasks; // Tasks within the selected day

    public ViewModel() {
        model = ModelHolder.INSTANCE.getProjectManager();
        LocalDate currentDate = model.getCurrentTime();

        // Defaults to current date
        selectedDate = new MutableLiveData<LocalDate>(currentDate);
        tasks = new MutableLiveData<>(new HashSet<>());
        setCurrentWeekDate(currentDate);
        updateTasks();

        // Update the tasks livedata whenever tasks change within the model
        this.tasksObserver = new Observer() {
            @Override
            public void update(ObservableEvent eventData) {
                updateTasks();
            }
        };
        model.subscribe(ProjectManager.Events.TASKS_UPDATED, tasksObserver);
    }
    @Override
    public void onCleared() {
        // Unsubscribe listeners to prevent memory leaks
        model.unsubscribe(ProjectManager.Events.TASKS_UPDATED, tasksObserver);
    }

    @Nullable
    public LiveData<LocalDate> getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(@Nullable LocalDate selectedDate) {
        this.selectedDate.setValue(selectedDate);
        updateTasks();
    }

    public LocalDate getCurrentWeekDate() {
        return currentWeekDate;
    }

    public void setCurrentWeekDate(LocalDate currentWeekDate) {
        this.currentWeekDate = currentWeekDate;
    }

    // Returns the tasks for the selected day.
    public LiveData<Set<ViewModelHelpers.TaskData>> getTasks() {
        return tasks;
    }

    public boolean hasProjects() {
        return model.getProjects().size() > 0;
    }

    // Updates the tasks livedata based on the selected day in VM.
    private void updateTasks() {
        Set<ViewModelHelpers.TaskData> tasks = new HashSet<>();
        ProjectManager.TaskPredicate predicate = new ProjectManager.TaskPredicate() {
            @Override
            public boolean isValid(Task task) {
                // Do not show completed tasks.
                return !task.isCompletedOnDay(selectedDate.getValue());
            }
        };

        for (Task task : model.getTasksForDay(getSelectedDate().getValue().atStartOfDay(), predicate)) {
            tasks.add(new ViewModelHelpers.TaskData(task));
        }

        this.tasks.setValue(tasks);
    }
}

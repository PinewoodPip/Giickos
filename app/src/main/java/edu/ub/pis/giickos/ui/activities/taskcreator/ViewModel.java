package edu.ub.pis.giickos.ui.activities.taskcreator;

import android.util.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.Utils;
import edu.ub.pis.giickos.model.ModelHolder;
import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.project.Project;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.ui.ViewModelHelpers;
import edu.ub.pis.giickos.ui.ViewModelHelpers.*;

public class ViewModel extends androidx.lifecycle.ViewModel {

    // These enums mirror the model ones, and provide labels for the UI.
    public enum TASK_PRIORITY {
        NONE(R.string.task_priority_none, R.color.task_priority_none),
        LOW(R.string.task_priority_low, R.color.task_priority_low),
        MEDIUM(R.string.task_priority_medium, R.color.task_priority_medium),
        HIGH(R.string.task_priority_high, R.color.task_priority_high),
        ;

        public final int stringResource;
        public final int colorResource;

        TASK_PRIORITY(int stringResource, int colorResource) {
            this.stringResource = stringResource;
            this.colorResource = colorResource;
        }
    }

    public enum TASK_REPEAT_MODE {
        NONE(R.string.task_repeatmode_none),
        DAILY(R.string.task_repeatmode_daily),
        WEEKLY(R.string.task_repeatmode_weekly),
        ;

        final int stringResource;

        TASK_REPEAT_MODE(int stringResource) {
            this.stringResource = stringResource;
        }
    }

    public static final int MIN_DURATION = 0;
    public static final int MAX_DURATION = 300;

    private ProjectManager model;

    private String projectID = null;
    private String taskID = null; // For edit mode
    private String taskName = "";
    private String taskDescription = "";
    private Task.PRIORITY priority = Task.PRIORITY.NONE;
    private Task.REPEAT_MODE repeatMode = Task.REPEAT_MODE.NONE;
    private boolean completed = false;

    // Once submitted, these should be converted to a proper date type
    private TaskDate startDate = null;
    private TaskTime startTime = null;
    private int durationInMinutes = -1;

    private TaskDate selectedDate; // The date the task editor has been opened for. Relevant for edit mode only.

    private boolean taskEditInitialized = false; // Used to only load data for task editing the first time the view renders

    public ViewModel() {
        this.model = ModelHolder.INSTANCE.getProjectManager();
    }

    // Creates a task with the currently inputted data
    // Returns false if task creation failed
    public boolean createTask() {
        boolean success = true;

        if (projectID == null || taskName.isEmpty()) {
            success = false;
        }
        else {
            Task task = new Task(UUID.randomUUID().toString(), taskName);
            task.setPriority(priority);
            task.setDescription(taskDescription);
            task.setRepeatMode(Task.REPEAT_MODE.values()[repeatMode.ordinal()]);

            updateTaskTime(task);

            model.addTask(projectID, task);
        }

        return success;
    }

    public boolean deleteTask() {
        boolean success = false;

        if (taskID != null) {
            success = model.deleteTask(taskID);
        }

        return success;
    }

    // Returns false if the operation fails
    public boolean updateTask() {
        boolean success = false;

        if (taskID != null) {
            Task task = model.getTask(taskID);
            Project previousProject = model.getTaskProject(task.getID());
            Project newProject = model.getProject(projectID);
            task.setName(taskName);
            task.setDescription(taskDescription);
            task.setPriority(priority);
            task.setRepeatMode(Task.REPEAT_MODE.values()[repeatMode.ordinal()]);
            task.setCompletedOnDay(LocalDate.of(selectedDate.year, selectedDate.month, selectedDate.day), completed);
            // TODO repeat mode should error if start time of the task is unset

            updateTaskTime(task);

            // TODO this sucks, at least extract it to ProjectManager
            List<String> previousTasks = previousProject.getTasks();
            previousTasks.remove(taskID);
            newProject.addElement(taskID);

            success = model.updateTask(task);
            success = success && model.updateProject(previousProject);
            success = success && model.updateProject(newProject);
        }

        return success;
    }

    public List<ProjectData> getProjects() {
        return ViewModelHelpers.sortProjects(model.getProjects());
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID, TaskDate date) {
        this.taskID = taskID;

        if (!taskEditInitialized) {
            Task task = model.getTask(taskID);

            if (task != null) {
                setTaskName(task.getName());
                setTaskDescription(task.getDescription());
                setPriority(task.getPriority().ordinal());
                setRepeatMode(TASK_REPEAT_MODE.values()[task.getRepeatMode().ordinal()]);

                durationInMinutes = task.getDuration();
                LocalDateTime localTime = task.getStartTime();

                startDate = new TaskDate(localTime.getDayOfMonth(), localTime.getMonthValue(), localTime.getYear());

                if (!task.takesAllDay()) {
                    startTime = new TaskTime(localTime.getHour(), localTime.getMinute());
                }

                selectedDate = date;

                completed = task.isCompletedOnDay(LocalDate.of(date.year, date.month, date.day));

                taskEditInitialized = true;
            }
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getPriority() {
        return priority.ordinal();
    }

    public void setPriority(int priority) {
        if (priority >= 0 && priority < Task.PRIORITY.values().length) {
            this.priority = Task.PRIORITY.values()[priority];
        }
        else {
            Log.e("ViewModel", "Received setPriority with invalid index " + Integer.toString(priority) + ", check spinner");
        }
    }

    public TASK_REPEAT_MODE getRepeatMode() {
        return TASK_REPEAT_MODE.values()[repeatMode.ordinal()];
    }

    public void setRepeatMode(TASK_REPEAT_MODE repeatMode) {
        this.repeatMode = Task.REPEAT_MODE.values()[repeatMode.ordinal()];
    }

    public void setRepeatMode(int i) {
        setRepeatMode(TASK_REPEAT_MODE.values()[i]);
    }

    public TaskDate getStartDate() {
        return startDate;
    }

    public void setStartDate(TaskDate startDate) {
        this.startDate = startDate;
    }

    public TaskTime getStartTime() {
        return startTime;
    }

    public void setStartTime(TaskTime startTime) {
        this.startTime = startTime;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public TaskDate getSelectedDate() {
        return selectedDate;
    }

    private void updateTaskTime(Task task) {
        task.setDuration(durationInMinutes);

        if (startDate != null) {
            int hour = 0;
            int minute = 0;

            if (startTime != null) {
                hour = startTime.hour;
                minute = startTime.minute;
                task.setTakesAllDay(false);
            }
            else {
                task.setTakesAllDay(true);
            }

            LocalDateTime startTimeObj = LocalDateTime.of(startDate.year, startDate.month, startDate.day, hour, minute);

            task.setStartTime(Utils.localDateToUTC(startTimeObj).toInstant().toEpochMilli());
        }
        else {
            task.setStartTime(-1);
            task.setTakesAllDay(true);
        }
    }
}

package edu.ub.pis.giickos.ui.section.taskcreator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.Controller;
import edu.ub.pis.giickos.model.managers.ProjectManager;
import edu.ub.pis.giickos.model.projectfunctions.Project;
import edu.ub.pis.giickos.model.projectfunctions.Task;
import edu.ub.pis.giickos.ui.ViewModelHelpers;
import edu.ub.pis.giickos.ui.ViewModelHelpers.*;

public class ViewModel extends androidx.lifecycle.ViewModel {

    public enum TASK_PRIORITY {
        NONE(R.string.task_priority_none),
        LOW(R.string.task_priority_low),
        MEDIUM(R.string.task_priority_medium),
        HIGH(R.string.task_priority_high);

        final int stringResource;

        TASK_PRIORITY(int stringResource) {
            this.stringResource = stringResource;
        }
    }

    private ProjectManager model;

    private String projectID = null;
    private String taskID = null; // For edit mode
    private String taskName = "";
    private String taskDescription = "";
    private Task.PRIORITY priority = Task.PRIORITY.NONE;

    // Once submitted, these should be converted to a proper date type
    private TaskDate startDate = null;
    private TaskTime startTime = null;
    private TaskTime endTime = null;

    private boolean taskEditInitialized = false; // Used to only load data for task editing the first time the view renders

    public ViewModel() {
        this.model = Controller.INSTANCE.getProjectManager();
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

            // TODO date and time

            model.addTask(projectID, task);
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
            // TODO other setters

            // TODO this sucks, at least extract it to ProjectManager
            ArrayList<String> previousTasks = previousProject.getElements();
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

    public void setTaskID(String taskID) {
        this.taskID = taskID;

        if (!taskEditInitialized) {
            Task task = model.getTask(taskID);

            if (task != null) {
                setTaskName(task.getName()); // TODO other setters (date, time)
                setTaskDescription(task.getDescription());
                setPriority(task.getPriority().ordinal());

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

    public TaskTime getEndTime() {
        return endTime;
    }

    public void setEndTime(TaskTime endTime) {
        this.endTime = endTime;
    }

    /*
        Auxiliary classes
    */

    static class TaskDate {
        public final int day, month, year;

        public TaskDate(int day, int month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    static class TaskTime {
        public final int hour, minute;

        public TaskTime(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }
    }
}

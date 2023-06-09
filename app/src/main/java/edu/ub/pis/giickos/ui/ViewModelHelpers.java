package edu.ub.pis.giickos.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.project.Project;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.ui.activities.taskcreator.ViewModel;

public class ViewModelHelpers {
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

    public enum SUBSCRIPTION_STATUS {
        NOT_SUBSCRIBED(R.string.subscription_inactive),
        SUBSCRIBED(R.string.subscription_active),
        ;

        public final int stringResource;

        SUBSCRIPTION_STATUS(int stringResource) {
            this.stringResource = stringResource;
        }
    }

    public static List<ProjectData> sortProjects(Set<Project> projects) {
        List<ProjectData> orderedProjects = new ArrayList<>();

        for (Project project : projects) {
            orderedProjects.add(new ProjectData(project.getId(), project.getName()));
        }

        orderedProjects.sort(new Comparator<ProjectData>() {
            @Override
            public int compare(ProjectData project1, ProjectData project2) {
                return project1.name.compareTo(project2.name);
            }
        });

        return orderedProjects;
    }

    public static List<TaskData> sortTasks(Set<Task> tasks) {
        List<TaskData> orderedTasks = new ArrayList<>();

        for (Task task : tasks) {
            orderedTasks.add(new TaskData(task));
        }

        // Order by name
        // TODO allow ordering by date
        orderedTasks.sort(new Comparator<TaskData>() {
            @Override
            public int compare(TaskData task1, TaskData task2) {
                return task1.name.compareTo(task2.name);
            }
        });

        return orderedTasks;
    }

    /*
        Auxiliary classes
    */

    // Classes for providing data to the view without coupling with the actual model classes
    // These mirror only the data the view could care about
    public static class ProjectData {
        public final String id;
        public final String name;

        public ProjectData(String guid, String name) {
            this.id = guid;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class TaskData {
        public final String id;
        public final String projectID;
        public final String name;
        public final LocalDateTime startTime;
        public final int durationInMinutes;
        public final boolean takesAllDay;
        public final TASK_PRIORITY priority;

        public TaskData(Task task) {
            this.id = task.getID();
            this.projectID = task.getProjectID();
            this.name = task.getName();
            this.startTime = task.getStartTime();
            this.durationInMinutes = task.getDuration();
            this.takesAllDay = task.takesAllDay();
            this.priority = TASK_PRIORITY.values()[task.getPriority().ordinal()];
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class TaskDate {
        public final int day, month, year;

        public TaskDate(int day, int month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        public TaskDate(LocalDateTime time) {
            this.day = time.getDayOfMonth();
            this.month = time.getMonthValue();
            this.year = time.getYear();
        }

        @Override
        public String toString() {
            return LocalDate.of(year, month, day).format(DateTimeFormatter.ISO_DATE);
        }
    }

    public static class TaskTime {
        public final int hour, minute;

        public TaskTime(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }
    }
}

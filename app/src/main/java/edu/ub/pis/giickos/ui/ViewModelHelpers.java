package edu.ub.pis.giickos.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import edu.ub.pis.giickos.model.projectfunctions.Project;
import edu.ub.pis.giickos.model.projectfunctions.Task;
import edu.ub.pis.giickos.ui.section.taskcreator.TaskCreator;
import edu.ub.pis.giickos.ui.section.taskcreator.ViewModel;

public class ViewModelHelpers {
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
        public final ViewModel.TASK_PRIORITY priority; // TODO move enum here?

        public TaskData(Task task) {
            this.id = task.getID();
            this.projectID = task.getProjectID();
            this.name = task.getName();
            this.startTime = task.getStartTime();
            this.durationInMinutes = task.getDuration();
            this.takesAllDay = task.takesAllDay();
            this.priority = ViewModel.TASK_PRIORITY.values()[task.getPriority().ordinal()];
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
    }

    public static class TaskTime {
        public final int hour, minute;

        public TaskTime(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }
    }
}

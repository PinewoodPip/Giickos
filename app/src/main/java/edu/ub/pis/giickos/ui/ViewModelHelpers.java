package edu.ub.pis.giickos.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import edu.ub.pis.giickos.model.projectfunctions.Project;
import edu.ub.pis.giickos.model.projectfunctions.Task;

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
            orderedTasks.add(new TaskData(task.getID(), task.getName()));
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
        public final String name;

        public TaskData(String guid, String name) {
            this.id = guid;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

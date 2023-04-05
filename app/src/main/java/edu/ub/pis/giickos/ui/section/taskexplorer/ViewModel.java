package edu.ub.pis.giickos.ui.section.taskexplorer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import edu.ub.pis.giickos.model.Controller;
import edu.ub.pis.giickos.model.data.project.ProjectDataProvider;
import edu.ub.pis.giickos.model.managers.ProjectManager;
import edu.ub.pis.giickos.model.projectfunctions.Task;
import edu.ub.pis.giickos.model.projectfunctions.Project;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private ProjectManager model;

    public ViewModel() {
        this.model = Controller.INSTANCE.getProjectManager();
    }

    public List<ProjectData> getProjects() {
        Set<Project> projects = model.getProjects();
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

    public List<TaskData> getTasks(String projectGUID) {
        Set<Task> tasks = model.getTasks(projectGUID);
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

    public ProjectData getProject(String id) {
        ProjectData data = null;
        Project project = model.getProject(id);

        if (project != null) {
            data = new ProjectData(project.getId(), project.getName());
        }

        return data;
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
    }

    public static class TaskData {
        public final String id;
        public final String name;

        public TaskData(String guid, String name) {
            this.id = guid;
            this.name = name;
        }
    }
}

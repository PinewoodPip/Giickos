package edu.ub.pis.giickos.ui.section.taskexplorer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import edu.ub.pis.giickos.model.Controller;
import edu.ub.pis.giickos.model.managers.ProjectManager;
import edu.ub.pis.giickos.model.projectfunctions.Task;
import edu.ub.pis.giickos.model.projectfunctions.Project;
import edu.ub.pis.giickos.ui.ViewModelHelpers;
import edu.ub.pis.giickos.ui.ViewModelHelpers.*;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private ProjectManager model;

    public ViewModel() {
        this.model = Controller.INSTANCE.getProjectManager();
    }

    public List<ProjectData> getProjects() {
        Set<Project> projects = model.getProjects();

        return ViewModelHelpers.sortProjects(projects);
    }

    public List<TaskData> getTasks(String projectGUID) {
        Set<Task> tasks = model.getTasks(projectGUID);

        return ViewModelHelpers.sortTasks(tasks);
    }

    public ProjectData getProject(String id) {
        ProjectData data = null;
        Project project = model.getProject(id);

        if (project != null) {
            data = new ProjectData(project.getId(), project.getName());
        }

        return data;
    }
}

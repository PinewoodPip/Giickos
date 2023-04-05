package edu.ub.pis.giickos.model.managers;

import java.util.Set;

import edu.ub.pis.giickos.model.data.project.ProjectDataProvider;
import edu.ub.pis.giickos.model.projectfunctions.Project;
import edu.ub.pis.giickos.model.projectfunctions.Task;

public class ProjectManager {
    //Handler of projects
    //TODO create a UserCases excel in order to spot the interactions.

    private ProjectDataProvider dataProvider;

    public ProjectManager(ProjectDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public Set<Project> getProjects() {
        return dataProvider.getProjects();
    }

    public Project getProject(String id) {
        return dataProvider.getProject(id);
    }

    public Set<Task> getProjectTasks(String projectGUID) {
        return dataProvider.getTasks(projectGUID);
    }

    public Set<Task> getTasks(String projectGUID) {
        return dataProvider.getTasks(projectGUID);
    }

    public Task getTask(String taskGUID) {
        return dataProvider.getTask(taskGUID);
    }
}

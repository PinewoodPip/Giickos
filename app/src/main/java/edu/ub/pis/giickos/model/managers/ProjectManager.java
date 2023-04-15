package edu.ub.pis.giickos.model.managers;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import edu.ub.pis.giickos.model.projectfunctions.Project;
import edu.ub.pis.giickos.model.projectfunctions.Task;
import edu.ub.pis.giickos.resources.dao.DAOProject;

public class ProjectManager {
    //Handler of projects
    private DAOProject daoProject;

    public ProjectManager(DAOProject daoProject) {
        this.daoProject = daoProject;
    }

    public Set<Project> getProjects() {
        return daoProject.getProjects();
    }

    public Project getProject(String id) {
        return daoProject.getProject(id);
    }

    public Set<Task> getProjectTasks(String projectGUID) {
        return daoProject.getTasks(projectGUID);
    }

    public Set<Task> getTasks(String projectGUID) {
        return daoProject.getTasks(projectGUID);
    }

    public Task getTask(String taskGUID) {
        return daoProject.getTask(taskGUID);
    }

    public boolean addTask(String projectGUID, Task task) {
        return daoProject.addTask(projectGUID, task);
    }

    public boolean updateTask(Task task) {
        return daoProject.updateTask(task);
    }

    public Project getTaskProject(String taskID) {
        return daoProject.getTaskProject(taskID);
    }

    public boolean updateProject(Project project) {
        return daoProject.updateProject(project);
    }

    public boolean deleteTask(String taskID) {
        return daoProject.deleteTask(taskID);
    }

    public boolean createProject(String name) {
        boolean success = false;

        // Cannot create projects with blank name
        if (!name.isEmpty()) {
            Project project = new Project(UUID.randomUUID(), name);

            success = daoProject.addProject(project);
        }

        return success;
    }

    // TODO move elsewhere
    public LocalDate getCurrentTime() {
        return LocalDate.now();
    }
}

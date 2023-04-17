package edu.ub.pis.giickos.model.managers;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import edu.ub.pis.giickos.model.observer.EmptyEvent;
import edu.ub.pis.giickos.model.observer.Observable;
import edu.ub.pis.giickos.model.projectfunctions.Project;
import edu.ub.pis.giickos.model.projectfunctions.Task;
import edu.ub.pis.giickos.resources.dao.DAOProject;

public class ProjectManager extends Observable<ProjectManager.Events> {

    public enum Events {
        PROJECTS_UPDATED,
        TASKS_UPDATED,
        ;
    }

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
        boolean success = daoProject.addTask(projectGUID, task);

        if (success) {
            notifyTasksUpdated();
        }

        return success;
    }

    public boolean updateTask(Task task) {
        boolean success = daoProject.updateTask(task);

        if (success) {
            notifyTasksUpdated();
        }

        return success;
    }

    public Project getTaskProject(String taskID) {
        return daoProject.getTaskProject(taskID);
    }

    public boolean updateProject(Project project) {
        boolean success = daoProject.updateProject(project);

        if (success) {
            notifyProjectsUpdated();
        }

        return success;
    }

    public boolean deleteTask(String taskID) {
        boolean success = daoProject.deleteTask(taskID);

        if (success) {
            notifyTasksUpdated();
        }

        return success;
    }

    public boolean createProject(String name) {
        boolean success = false;

        // Cannot create projects with blank name
        if (!name.isEmpty()) {
            Project project = new Project(UUID.randomUUID(), name);

            success = daoProject.addProject(project);
        }

        if (success) {
            notifyProjectsUpdated();
        }

        return success;
    }

    public boolean deleteProject(String projectID) {
        boolean success = daoProject.deleteProject(projectID);

        if (success) {
            notifyTasksUpdated();
        }

        return success;
    }

    // TODO move elsewhere
    public LocalDate getCurrentTime() {
        return LocalDate.now();
    }

    private void notifyProjectsUpdated() {
        notifyObservers(Events.PROJECTS_UPDATED, new EmptyEvent(this, Events.PROJECTS_UPDATED));
    }

    private void notifyTasksUpdated() {
        notifyObservers(Events.TASKS_UPDATED, new EmptyEvent(this, Events.TASKS_UPDATED));
    }
}

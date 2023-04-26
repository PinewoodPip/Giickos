package edu.ub.pis.giickos.resources.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.ub.pis.giickos.model.project.Project;
import edu.ub.pis.giickos.model.project.Task;

// A ProjectDAO implementation that caches Projects and Tasks in memory.
public abstract class CachedProjectDAO implements ProjectDAO {

    protected HashMap<String, Project> projects;
    protected HashMap<String, Task> tasks;

    public CachedProjectDAO() {
        this.projects = new HashMap<>();
        this.tasks = new HashMap<>();
    }

    @Override
    public Project getProject(String guid) {
        return projects.get(guid);
    }

    @Override
    public Set<Project> getProjects() {
        return new HashSet<>(projects.values());
    }

    @Override
    public boolean addProject(Project project) {
        boolean success = false;
        String projectID = project.getId();

        if (getProject(projectID) == null) {
            projects.put(projectID, project);

            success = true;
        }

        return success;
    }

    @Override
    public Task getTask(String guid) {
        return tasks.get(guid);
    }

    @Override
    public Set<Task> getTasks(String projectGUID) {
        Set<Task> tasks = new HashSet<>();
        Project project = getProject(projectGUID);

        for (String taskGUID : project.getTasks()) {
            Task task = getTask(taskGUID);

            if (task != null) {
                tasks.add(task);
            }
        }

        return tasks;
    }

    public Set<Task> getTasks() {
        Set<Task> tasks = new HashSet<>(this.tasks.values());

        return tasks;
    }

    @Override
    public boolean addTask(String projectGUID, Task task) {
        boolean success = false;
        String projectID = task.getID();
        Project project = getProject(projectGUID);

        if (project != null && getTask(projectID) == null) {
            project.addElement(task.getID());
            tasks.put(projectID, task);

            task.setProjectID(projectGUID);

            success = true;
        }

        return success;
    }

    @Override
    public Project getTaskProject(String taskID) {
        Project project = null;
        Task task = getTask(taskID);

        if (task != null) {
            for (Project p : projects.values()) {
                if (p.getTasks().contains(task.getID())) {
                    project = p;
                    break;
                }
            }
        }

        return project;
    }

    @Override
    public boolean updateTask(Task task) {
        boolean success = false;
        Task existingTask = getTask(task.getID());

        if (existingTask != null) {
            tasks.replace(existingTask.getID(), task);

            success = true;
        }

        return success;
    }

    @Override
    public boolean deleteTask(String taskID) {
        boolean success = false;
        Task existingTask = getTask(taskID);

        if (existingTask != null) {
            Project project = getTaskProject(taskID);

            project.removeElement(taskID);
            tasks.remove(taskID);

            success = updateProject(project);
        }

        return success;
    }

    @Override
    public boolean deleteProject(String projectID) {
        boolean success = false;
        Project project = getProject(projectID);

        if (project != null) {
            success = true;

            // Delete all tasks assigned to the project
            for (Task task : getTasks(projectID)) {
                success = success && deleteTask(task.getID());
            }

            // Only delete the project if all tasks were deleted without issues
            if (success) {
                projects.remove(projectID);
            }
        }

        return success;
    }

    @Override
    public boolean updateProject(Project newData) {
        Project oldData = getProject(newData.getId());
        boolean success = false;

        if (oldData != null) {
            projects.replace(oldData.getId(), newData);

            success = true;
        }

        return success;
    }
}

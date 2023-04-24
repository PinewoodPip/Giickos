package edu.ub.pis.giickos.resources.dao;

import java.util.Set;

import edu.ub.pis.giickos.model.project.Project;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.model.user.User;

public interface ProjectDAO {

    void loadDataForUser(User user, DataLoadedListener listener);
    Project getProject(String guid);
    Set<Project> getProjects();
    boolean addProject(Project project); // Should return false if operation fails

    Task getTask(String guid);
    Set<Task> getTasks(String projectGUID);
    Set<Task> getTasks(); // Should return all tasks
    boolean addTask(String projectGUID, Task task); // Should return false if operation fails

    boolean updateTask(Task task);

    // Returns the project of a task.
    Project getTaskProject(String taskID);

    boolean updateProject(Project project);

    boolean deleteTask(String taskID);

    boolean deleteProject(String projectID);

    public static abstract class DataLoadedListener {
        public abstract void onLoad(boolean success);
    }
}

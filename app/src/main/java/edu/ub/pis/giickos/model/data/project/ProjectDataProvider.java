package edu.ub.pis.giickos.model.data.project;

import java.util.Set;

import edu.ub.pis.giickos.model.projectfunctions.Project;
import edu.ub.pis.giickos.model.projectfunctions.Task;

public interface ProjectDataProvider {
    Project getProject(String guid);
    Set<Project> getProjects();
    boolean addProject(Project project); // Should return false if operation fails

    Task getTask(String guid);
    Set<Task> getTasks(String projectGUID);
    boolean addTask(String projectGUID, Task task); // Should return false if operation fails
}

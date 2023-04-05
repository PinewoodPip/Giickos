package edu.ub.pis.giickos.model.data.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import edu.ub.pis.giickos.model.projectfunctions.Project;
import edu.ub.pis.giickos.model.projectfunctions.Task;

// Mock data provider for projects.
public class MockProvider implements ProjectDataProvider {
    private HashMap<String, Project> projects;
    private HashMap<String, Task> tasks;

    public MockProvider() {
        this.projects = new HashMap<>();
        this.tasks = new HashMap<>();

        addMockData();
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

        for (String taskGUID : project.getElements()) {
            Task task = getTask(taskGUID);

            if (task != null) {
                tasks.add(task);
            }
        }

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

            success = true;
        }

        return success;
    }

    // Creates mock projects and tasks.
    private void addMockData() {
        Project project1 = new Project(UUID.randomUUID(), "Test Project 1");
        Project project2 = new Project(UUID.randomUUID(), "Test Project 2");
        Project project3 = new Project(UUID.randomUUID(), "Empty Test Project");
        Task task1 = new Task(UUID.randomUUID().toString(), "Task 1");
        Task task2 = new Task(UUID.randomUUID().toString(), "Task 2");
        Task task3 = new Task(UUID.randomUUID().toString(), "Task 3");

        addProject(project1);
        addTask(project1.getId(), task1);
        addTask(project1.getId(), task2);

        addProject(project2);
        addTask(project2.getId(), task3);

        // Test project with no tasks
        addProject(project3);
    }
}

package edu.ub.pis.giickos.model.project;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Represents a project, which aggregates tasks.
public class Project {
    private String id;
    private String name;
    private List<String> tasks; // IDs of tasks assigned to the project

    public Project(String id, String name) {
        initialize(id, name);
    }

    public Project(UUID guid, String name) {
        initialize(guid.toString(), name);
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    public void addElement(String id) {
        this.tasks.add(id);
    }

    private void initialize(String id, String name) {
        this.tasks = new ArrayList<>();

        this.id = id;
        setName(name);
    }

    public void removeElement(String taskID) {
        tasks.remove(taskID);
    }
}

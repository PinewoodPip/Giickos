package edu.ub.pis.giickos.model.projectfunctions;

import java.util.ArrayList;
import java.util.UUID;

public class Project {
    //In this class, we define a project with elements that we verify, create, and erase.
    private String id;
    private String name;
    private ArrayList<String> elements;//We use strings to identify the elements (for now we only have tasks). Or maybe we can use (projectName + elementName) as elements ID instead of generated UUID.

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


    //TODO add, remove and other functions like checking...
    public ArrayList<String> getElements() {
        return elements;
    }

    public void setElements(ArrayList<String> elements) {
        this.elements = elements;
    }

    public void addElement(String id) {
        this.elements.add(id);
    }

    private void initialize(String id, String name) {
        this.elements = new ArrayList<>();

        this.id = id;
        setName(name);
    }

    public void removeElement(String taskID) {
        elements.remove(taskID);
    }
}

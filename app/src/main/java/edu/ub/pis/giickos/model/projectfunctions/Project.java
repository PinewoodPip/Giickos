package edu.ub.pis.giickos.model.projectfunctions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class Project {
    //In this class, we define a project with elements that we verify, create, and erase.
    private String id;
    private String name;
    private ArrayList<String> elements;//We use strings to identify the elements (for now we only have tasks)
    // Or maybe we can use (projectName + elementName) as elements ID instead of generated UUID.

    public Project(String name) {
        setId(UUID.randomUUID().toString());
        setName(name);
        this.elements = new ArrayList<>();
    }

    public void setId(String id) {
        this.id = id;
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


}

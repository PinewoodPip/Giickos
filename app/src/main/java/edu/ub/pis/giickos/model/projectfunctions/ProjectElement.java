package edu.ub.pis.giickos.model.projectfunctions;


import java.util.UUID;

//Generic class for elemnts inside a project, for now, just "task", but in the future, maybe other elemnts like reminder...
public abstract class ProjectElement {

    private String id;
    private String name;

    public ProjectElement(String name)
    {
        this.id = UUID.randomUUID().toString();
        setName(name);

    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

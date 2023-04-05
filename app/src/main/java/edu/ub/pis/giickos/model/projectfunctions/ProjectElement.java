package edu.ub.pis.giickos.model.projectfunctions;


import java.util.UUID;

//Generic class for elemnts inside a project, for now, just "task", but in the future, maybe other elemnts like reminder...
public abstract class ProjectElement {

    protected String id;
    protected String name;

    public ProjectElement(String id, String name)
    {
        this.id = id;
        setName(name);
    }

    public String getID() {
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

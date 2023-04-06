package edu.ub.pis.giickos.model.projectfunctions;

public class Task extends ProjectElement {

    public enum PRIORITY {
        NONE,
        LOW,
        MEDIUM,
        HIGH,
    }

    private PRIORITY priority = PRIORITY.NONE;
    private String description = "";

    public Task(String id, String name) // Kamil: I'm not a fan of bloated constructor params, so try to delegate initialization to setters instead (ex. how it's done with priority) with the exception of essential fields like name/id.
    {
        super(id, name);
    }

    public PRIORITY getPriority() {
        return priority;
    }

    public void setPriority(PRIORITY priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    /*
    * Methods to fulfill the next functions:
    *
    * Start from x time
    * End at x time
    * Notify x time before start
    * Repeatable (default - no repeat, custom, select repeatable days)
    * Description
    * Add labels
    * Add members
    * Task priority level (("no pritority?"), low priority, medium priority, high pritority)
    * */










}

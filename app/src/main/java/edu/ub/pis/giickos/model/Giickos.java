package edu.ub.pis.giickos.model;

import edu.ub.pis.giickos.model.managers.AbstractManagerFactory;
import edu.ub.pis.giickos.model.managers.ProjectManager;

public class Giickos {

    private ProjectManager taskManager;

    public Giickos(AbstractManagerFactory factory)
    {
        try {
            this.taskManager = factory.createTaskManager();
        }
        catch (Exception e) {
            throw new RuntimeException("Initialization failed");
        }
    }

    public ProjectManager getTaskManager() {return taskManager;}
}

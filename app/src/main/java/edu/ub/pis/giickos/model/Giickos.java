package edu.ub.pis.giickos.model;

import edu.ub.pis.giickos.model.managers.AbstractManagerFactory;
import edu.ub.pis.giickos.model.managers.ProjectManager;

public class Giickos {

    private ProjectManager projectManager;

    public Giickos(AbstractManagerFactory factory)
    {
        try {
            this.projectManager = factory.createProjectManager();
        }
        catch (Exception e) {
            throw new RuntimeException("Initialization failed");
        }
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }
}

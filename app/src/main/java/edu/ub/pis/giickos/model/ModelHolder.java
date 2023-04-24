package edu.ub.pis.giickos.model;

import edu.ub.pis.giickos.model.factories.AbstractManagerFactory;
import edu.ub.pis.giickos.model.factories.GiickosMockManagerFactory;
import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.user.UserManager;
import edu.ub.pis.giickos.resources.dao.firebase.FirebaseDAOFactory;
import edu.ub.pis.giickos.resources.dao.DAOFactory;
import edu.ub.pis.giickos.resources.dataservice.DataService;

// Provides a singleton for the model.
public enum ModelHolder {
    INSTANCE; // Singleton instance

    private DataService dataService; // Data provider
    private Giickos model;

    ModelHolder()
    {
        setModel(new FirebaseDAOFactory()); // Uses mock DAOs for now
    }

    public void setModel(DAOFactory factory) {
        // Uses GiickosMockManagerFactory by default - use the overload to inject others
        setModel(factory, new GiickosMockManagerFactory());
    }

    public void setModel(DAOFactory dataFactory, AbstractManagerFactory managerFactory) {
        dataService = new DataService(dataFactory);
        managerFactory.setDataService(dataService);
        try {
            model = new Giickos(managerFactory);

        } catch (Exception e) {
            throw e;
        }
    }

    public Giickos getModel() {
        return model;
    }

    // Shorthand method for fetching the project manager
    public ProjectManager getProjectManager() {
        return model.getProjectManager();
    }

    public UserManager getUserManager() {
        return model.getUserManager();
    }
}

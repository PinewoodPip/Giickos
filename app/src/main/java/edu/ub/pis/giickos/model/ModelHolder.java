package edu.ub.pis.giickos.model;

import edu.ub.pis.giickos.model.factories.AbstractManagerFactory;
import edu.ub.pis.giickos.model.factories.ManagerFactory;
import edu.ub.pis.giickos.model.garden.GardenManager;
import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.statistics.StatisticsManager;
import edu.ub.pis.giickos.model.user.UserManager;
import edu.ub.pis.giickos.resources.dao.firebase.FirebaseDAOFactory;
import edu.ub.pis.giickos.resources.dao.mock.MockDAOFactory;

// Provides a singleton for the model.
public enum ModelHolder {
    INSTANCE; // Singleton instance
    private Giickos model;

    ModelHolder()
    {
        setModel(new ManagerFactory(new FirebaseDAOFactory()));
        //setModel(new ManagerFactory(new MockDAOFactory())); // Use this line to use mock data
    }

    public void setModel(AbstractManagerFactory managerFactory) {
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

    public StatisticsManager getStatisticsManager() {
        return model.getStatisticsManager();
    }

    public GardenManager getGardenManager() {
        return model.getGardenManager();
    }

}

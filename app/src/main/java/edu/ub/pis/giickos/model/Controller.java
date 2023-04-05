package edu.ub.pis.giickos.model;

import edu.ub.pis.giickos.model.managers.AbstractManagerFactory;
import edu.ub.pis.giickos.model.managers.GiickosMockManagerFactory;
import edu.ub.pis.giickos.model.managers.ProjectManager;
import edu.ub.pis.giickos.resources.service.AbstractFactoryData;
import edu.ub.pis.giickos.resources.service.DataService;
import edu.ub.pis.giickos.resources.service.FactoryMock;

//Not sure if this should be in model...
public enum Controller {
    INSTANCE; //Singleton

    private DataService dataService; // Data provider
    private Giickos model;

    Controller()
    {
        setModel(new FactoryMock()); // Usar mock per defecte
    }

    public void setModel(AbstractFactoryData factory) {
        // Uses GiickosComponentFactory by default - use the overload to inject others
        setModel(factory, new GiickosMockManagerFactory(dataService));
    }
    public void setModel(AbstractFactoryData dataFactory, AbstractManagerFactory managerFactory) {
        dataService = new DataService(dataFactory);

        try {
            model = new Giickos(managerFactory);
            //View-Model that uses the managers...
            //Example: a Project needs to load its info, so it uses ProjectManager..

        } catch (Exception e) {
            throw e;
        }
    }

    public Giickos getModel() {
        return model;
    }

    public ProjectManager getProjectManager() {
        return model.getProjectManager();
    }
}

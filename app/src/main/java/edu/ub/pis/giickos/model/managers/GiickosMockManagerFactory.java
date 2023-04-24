package edu.ub.pis.giickos.model.managers;

import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.resources.dataservice.DataService;

public class GiickosMockManagerFactory extends AbstractManagerFactory {

    private DataService data;

    // DataService is needed to inject the DAOs
    @Override
    public void setDataService(DataService data) {
        this.data = data;
    }

    @Override
    public ProjectManager createProjectManager() {
        return new ProjectManager(data.getDAOProject());
    }
}

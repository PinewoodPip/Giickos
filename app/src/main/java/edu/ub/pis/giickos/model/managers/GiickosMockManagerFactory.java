package edu.ub.pis.giickos.model.managers;

import edu.ub.pis.giickos.model.data.project.MockProvider;
import edu.ub.pis.giickos.resources.service.DataService;

public class GiickosMockManagerFactory extends AbstractManagerFactory {

    private DataService data;
    //We pass the data in order to retrieve the necessary things for the managers.
    @Override
    public void setDataService(DataService data) {
        this.data = data;
    }

    @Override
    public ProjectManager createProjectManager() {
        return new ProjectManager(data.getDAOProject());
    }

}

package edu.ub.pis.giickos.model.managers;

import edu.ub.pis.giickos.resources.service.DataService;

public class GiickosManagerFactory extends AbstractManagerFactory {

    private DataService data;
    //We pass the data in order to retrieve the necessary things for the managers.
    public GiickosManagerFactory(DataService data)
    {
        this.data = data;
    }


    @Override
    public ProjectManager createTaskManager() {
        return new ProjectManager();
    }
}

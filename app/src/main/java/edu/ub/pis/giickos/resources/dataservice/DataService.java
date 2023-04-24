package edu.ub.pis.giickos.resources.dataservice;

import edu.ub.pis.giickos.resources.dao.DAOFactory;
import edu.ub.pis.giickos.resources.dao.ProjectDAO;

public class DataService { // TODO remove - unnecessary, confusing wrapper
    //We use this class to retrieve the data from Mock, Firebase...
    private ProjectDAO daoProject;

    //Retrieve data from the factory
    public DataService(DAOFactory data)
    {
        this.daoProject = data.getDAOProject();
    }
    public ProjectDAO getDAOProject()
    {
        return daoProject;
    }
}

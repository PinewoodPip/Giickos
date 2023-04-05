package edu.ub.pis.giickos.resources.service;

import edu.ub.pis.giickos.resources.dao.DAOProject;

public class DataService {
    //We use this class to retrieve the data from Mock, Firebase...
    private DAOProject daoProject;


    //Retrieve data from the factory
    public DataService(AbstractFactoryData data)
    {
        this.daoProject = data.getDAOProject();
    }



}

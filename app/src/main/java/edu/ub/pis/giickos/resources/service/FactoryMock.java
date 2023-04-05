package edu.ub.pis.giickos.resources.service;

import edu.ub.pis.giickos.resources.dao.DAOProject;
import edu.ub.pis.giickos.resources.daoMock.DAOProjectMock;

public class FactoryMock implements AbstractFactoryData {
    //Implementation of the factory using mock DAOs
    @Override
    public DAOProject getDAOProject() {
        return new DAOProjectMock();
    }



}

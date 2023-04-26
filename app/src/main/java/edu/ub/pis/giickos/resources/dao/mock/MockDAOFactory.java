package edu.ub.pis.giickos.resources.dao.mock;

import edu.ub.pis.giickos.resources.dao.DAOFactory;
import edu.ub.pis.giickos.resources.dao.ProjectDAO;

public class MockDAOFactory implements DAOFactory {
    //Implementation of the factory using mock DAOs
    @Override
    public ProjectDAO getProjectDAO() {
        return new MockProjectDAO();
    }
}

package edu.ub.pis.giickos.resources.service;

import edu.ub.pis.giickos.resources.dao.DAOProject;

//Interface that defines the methods that the factory must implement
public interface AbstractFactoryData {
    DAOProject getDAOProject();
}

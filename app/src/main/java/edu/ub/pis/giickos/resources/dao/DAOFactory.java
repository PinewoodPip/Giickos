package edu.ub.pis.giickos.resources.dao;

// Interface for creating all the DAOs the application needs.
public interface DAOFactory {
    ProjectDAO getProjectDAO();

    GardenDAO getGardenDAO();
}

package edu.ub.pis.giickos.resources.dao.firebase;

import edu.ub.pis.giickos.resources.dao.ProjectDAO;
import edu.ub.pis.giickos.resources.dao.DAOFactory;

public class FirebaseDAOFactory implements DAOFactory {
    @Override
    public ProjectDAO getProjectDAO() {
        return new edu.ub.pis.giickos.resources.dao.firebase.ProjectDAO();
    }
}

package edu.ub.pis.giickos.model.factories;

import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.statistics.StatisticsManager;
import edu.ub.pis.giickos.model.user.UserManager;
import edu.ub.pis.giickos.resources.dao.DAOFactory;

// Class for constructing and initializing managers.
public abstract class AbstractManagerFactory {
    protected DAOFactory daoFactory;

    public AbstractManagerFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public abstract ProjectManager createProjectManager(UserManager userManager);

    public abstract UserManager createUserManager();

    public abstract StatisticsManager createStatisticsManager();
}

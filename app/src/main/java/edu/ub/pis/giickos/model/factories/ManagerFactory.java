package edu.ub.pis.giickos.model.factories;

import edu.ub.pis.giickos.model.garden.GardenManager;
import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.statistics.StatisticsManager;
import edu.ub.pis.giickos.model.user.User;
import edu.ub.pis.giickos.model.user.UserManager;
import edu.ub.pis.giickos.resources.dao.DAOFactory;

public class ManagerFactory extends AbstractManagerFactory {

    public ManagerFactory(DAOFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public ProjectManager createProjectManager(UserManager userManager) {
        return new ProjectManager(daoFactory.getProjectDAO(), userManager);
    }

    @Override
    public UserManager createUserManager() {
        return new UserManager();
    }

    @Override
    public StatisticsManager createStatisticsManager() {
        return new StatisticsManager();
    }

    @Override
    public GardenManager createGardenManager(UserManager userManager) { return new GardenManager(daoFactory.getGardenDAO(), userManager); }
}

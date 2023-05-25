package edu.ub.pis.giickos.model;

import edu.ub.pis.giickos.model.factories.AbstractManagerFactory;
import edu.ub.pis.giickos.model.garden.GardenManager;
import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.statistics.StatisticsManager;
import edu.ub.pis.giickos.model.team.TeamsManager;
import edu.ub.pis.giickos.model.user.UserManager;

public class Giickos {

    private ProjectManager projectManager;
    private UserManager userManager;
    //private TeamsManager teamsManager;
    private GardenManager gardenManager;
    private StatisticsManager statisticsManager;

    public Giickos(AbstractManagerFactory factory)
    {
        try {
            this.userManager = factory.createUserManager();
            this.projectManager = factory.createProjectManager(this.userManager);
            this.gardenManager = factory.createGardenManager(this.userManager);
            this.statisticsManager = factory.createStatisticsManager();
            this.statisticsManager.registerProvider(projectManager);
            this.statisticsManager.registerProvider(gardenManager);
        }
        catch (Exception e) {
            throw new RuntimeException("Initialization failed");
        }
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public StatisticsManager getStatisticsManager() {
        return statisticsManager;
    }

    public GardenManager getGardenManager() {
        return gardenManager;
    }
}

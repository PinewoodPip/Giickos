package edu.ub.pis.giickos.model;

import edu.ub.pis.giickos.model.factories.AbstractManagerFactory;
import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.statistics.StatisticsManager;
import edu.ub.pis.giickos.model.team.TeamsManager;
import edu.ub.pis.giickos.model.user.UserManager;

public class Giickos {

    private ProjectManager projectManager;
    private UserManager userManager;
    private TeamsManager teamsManager;
    private StatisticsManager statisticsManager;

    public Giickos(AbstractManagerFactory factory)
    {
        try {
            this.userManager = factory.createUserManager();
            this.projectManager = factory.createProjectManager(this.userManager);
            //this.teamsManager = factory.createTeamsManager(this.userManager);
            this.statisticsManager = factory.createStatisticsManager();
            this.statisticsManager.registerProvider(projectManager);
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
}

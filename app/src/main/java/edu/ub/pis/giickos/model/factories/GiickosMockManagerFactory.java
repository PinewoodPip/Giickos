package edu.ub.pis.giickos.model.factories;

import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.user.UserManager;
import edu.ub.pis.giickos.resources.dao.firebase.ProjectDAO;
import edu.ub.pis.giickos.resources.dataservice.DataService;

public class GiickosMockManagerFactory extends AbstractManagerFactory {

    private DataService data;

    // DataService is needed to inject the DAOs
    @Override
    public void setDataService(DataService data) {
        this.data = data;
    }

    @Override
    public ProjectManager createProjectManager(UserManager userManager) {
        return new ProjectManager(data.getDAOProject(), userManager);
    }

    @Override
    public UserManager createUserManager() {
        return new UserManager();
    }
}

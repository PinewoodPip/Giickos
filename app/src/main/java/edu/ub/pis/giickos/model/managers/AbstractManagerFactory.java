package edu.ub.pis.giickos.model.managers;

import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.resources.dataservice.DataService;

//Components that we need in the model
public abstract class AbstractManagerFactory {
    public abstract void setDataService(DataService dataService);
    public abstract ProjectManager createProjectManager();
}

package edu.ub.pis.giickos.resources.dao.firebase;

import edu.ub.pis.giickos.model.user.User;
import edu.ub.pis.giickos.resources.dao.CachedGardenDAO;

public class GardenDAO extends CachedGardenDAO {
    public GardenDAO() {
        super();
    }

    /**
     * @param user
     * @param listener
     */
    @Override
    public void loadDataForUser(User user, edu.ub.pis.giickos.resources.dao.GardenDAO.DataLoadedListener listener)
    {
        //TODO: Implement this method to load data from Firebase
    }

}

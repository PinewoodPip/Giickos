package edu.ub.pis.giickos.model.garden;

import java.util.List;
import java.util.Map;

import edu.ub.pis.giickos.model.user.UserManager;
import edu.ub.pis.giickos.resources.dao.GardenDAO;

public class GardenManager
{
    private GardenDAO daoGarden;
    public GardenManager(GardenDAO daoGarden, UserManager userManager) {
        this.daoGarden = daoGarden;
        daoGarden.loadDataForUser(userManager.getLoggedInUser(), new GardenDAO.DataLoadedListener() {
            @Override
            public void onLoad(boolean success) {
                //Use userManager to load data
            }
        });
    }

    public Map <Integer, Bamboo> getPlantedBamboos()
    {
        return daoGarden.getPlantedBamboos();
    }

    public boolean plantBamboo(Bamboo bamboo)
    {
        return daoGarden.plantBamboo(bamboo);
    }
    public boolean updatePlantedBamboo(Bamboo bamboo)
    {
        return daoGarden.updatePlantedBamboo(bamboo);
    }
    public boolean deletePlantedBamboo(int slotID)
    {
        return daoGarden.deletePlantedBamboo(slotID);
    }

    // Methods for storing bamboo
    public List<Bamboo> getStoredBamboos()
    {
        return daoGarden.getStoredBamboos();
    }
    public boolean addStoredBamboo(Bamboo bamboo)
    {
        return daoGarden.addStoredBamboo(bamboo);
    }
    public boolean saveBambooToStorage(Bamboo bamboo)
    {
        return daoGarden.saveBambooToStorage(bamboo);
    }
    public boolean deleteStoredBamboo(Bamboo bamboo)
    {
        return daoGarden.deleteStoredBamboo(bamboo);
    }

}

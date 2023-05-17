package edu.ub.pis.giickos.model.garden;

import java.util.List;
import java.util.Map;

import edu.ub.pis.giickos.model.observer.ObservableEvent;
import edu.ub.pis.giickos.model.observer.Observer;
import edu.ub.pis.giickos.model.user.UserManager;
import edu.ub.pis.giickos.resources.dao.GardenDAO;

public class GardenManager
{
    private GardenDAO daoGarden;
    public GardenManager(GardenDAO daoGarden, UserManager userManager) {
        this.daoGarden = daoGarden;
        userManager.subscribe(UserManager.Events.LOGGED_IN, new Observer() {
            @Override
            public void update(ObservableEvent eventData) {
                daoGarden.loadDataForUser(userManager.getLoggedInUser(), new GardenDAO.DataLoadedListener() {
                    @Override
                    public void onLoad(boolean success) {
                    System.out.println("GardenManager updated: " + success);
                    }
                });
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
    public boolean saveBambooToStorage(Bamboo bamboo)
    {
        return daoGarden.saveBambooToStorage(bamboo);
    }
    public boolean deleteStoredBamboo(Bamboo bamboo)
    {
        return daoGarden.deleteStoredBamboo(bamboo);
    }




}

package edu.ub.pis.giickos.resources.dao;

import java.util.List;
import java.util.Map;

import edu.ub.pis.giickos.model.garden.Bamboo;
import edu.ub.pis.giickos.model.user.User;

public interface GardenDAO {
    // Returns the project of a task.
    Map <Integer, Bamboo> getPlantedBamboos();

    Bamboo getPlantedBamboo(int slotID);
    boolean plantBamboo(Bamboo bamboo);
    boolean updatePlantedBamboo(Bamboo bamboo);
    boolean deletePlantedBamboo(int slotID);

    // Methods for storing bamboo

    List<Bamboo> getStoredBamboos();

    boolean addStoredBamboo(Bamboo bamboo);
    boolean saveBambooToStorage(Bamboo bamboo);
    boolean deleteStoredBamboo(Bamboo bamboo);

    // General methods
    void loadDataForUser(User user, GardenDAO.DataLoadedListener listener);
    public static abstract class DataLoadedListener {
        public abstract void onLoad(boolean success);
    }

    void clearData();
}

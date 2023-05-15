package edu.ub.pis.giickos.resources.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.ub.pis.giickos.model.garden.Bamboo;

// A ProjectDAO implementation that caches Planted bamboos and Stored bamboos in memory.
public abstract class CachedGardenDAO implements GardenDAO {

    protected Map<Integer, Bamboo> plantedBamboos;
    protected List<Bamboo> storedBamboos;

    public CachedGardenDAO() {
        this.plantedBamboos = new HashMap<>();
        this.storedBamboos = new LinkedList<>();
    }

    /**
     * This method returns the planted bamboos.
     * @return
     */
    @Override
    public Map<Integer, Bamboo> getPlantedBamboos() {
        return plantedBamboos;
    }

    /**
     * This method tries to put a new Bamboo in the garden using the slot as the key.
     * @param bamboo
     * @return
     */
    @Override
    public boolean plantBamboo(Bamboo bamboo)
    {
        boolean success = false;
        Integer ID = bamboo.getSlot();

        if(plantedBamboos.get(ID) == null){
            plantedBamboos.put(ID, bamboo);
            success = true;
        }

        return success;
    }

    /**
     * Tries to update the bamboo in the garden using the slot as the key.
     * @param bamboo
     * @return
     */
    @Override
    public boolean updatePlantedBamboo(Bamboo bamboo)
    {
        Bamboo oldBamboo = getPlantedBamboo(bamboo.getSlot());
        boolean success = false;

        if (oldBamboo != null) {
            plantedBamboos.replace(bamboo.getSlot(), bamboo);
            success = true;
        }
        return success;
    }

    /**
     * Tries to delete the bamboo in the garden using the slot as the key.
     * @param slotID
     * @return
     */
    @Override
    public boolean deletePlantedBamboo(int slotID) {
        boolean succes = false;
        Bamboo bamboo = getPlantedBamboo(slotID);

        if (bamboo != null) {
            plantedBamboos.remove(slotID);
            succes = true;
        }

        return succes;
    }

    /**
     * This method returns the list of stored bamboos.
     * @return
     */
    @Override
    public List<Bamboo> getStoredBamboos() {
        return storedBamboos;
    }

    /**
     * This method tries to save a bamboo to the list of stored bamboos and delete it from the garden.
     * @param bamboo
     * @return
     */
    @Override
    public boolean saveBambooToStorage(Bamboo bamboo) {
        boolean success = false;
        if(addStoredBamboo(bamboo) && deletePlantedBamboo(bamboo.getSlot()))
        {
            success = true;
        }
        return success;
    }

    /**
     * This method tries to add a new bamboo to the list of stored bamboos.
     * @param bamboo
     * @return
     */
    @Override
    public boolean addStoredBamboo(Bamboo bamboo) {
        boolean success = false;
        if(!storedBamboos.contains(bamboo))
        {
            storedBamboos.add(bamboo);
            success = true;
        }

        return success;
    }

    /**
     * This method tries to delete a bamboo from the list of stored bamboos.
     * @param bamboo
     * @return
     */
    @Override
    public boolean deleteStoredBamboo(Bamboo bamboo) {
        boolean success = false;

        for (Bamboo currrentBamboo : storedBamboos) {
            if (bamboo.equals(currrentBamboo))
            {
                storedBamboos.remove(bamboo);
                success = true;
                break;
            }
        }
        return success;
    }

    /**
     * @param slotID
     * @return
     */
    @Override
    public Bamboo getPlantedBamboo(int slotID) {
        return plantedBamboos.get(slotID);
    }

}

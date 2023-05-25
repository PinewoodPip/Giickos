package edu.ub.pis.giickos.model.garden;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ub.pis.giickos.model.observer.ObservableEvent;
import edu.ub.pis.giickos.model.observer.Observer;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.model.statistics.Statistic;
import edu.ub.pis.giickos.model.statistics.StatisticsProvider;
import edu.ub.pis.giickos.model.user.UserManager;
import edu.ub.pis.giickos.resources.dao.GardenDAO;

public class GardenManager implements StatisticsProvider
{
    // Stat IDs for StatisticsProvider
    public static final String STAT_STORED_BAMBOOS = "GARDEN_MANAGER_STORED_BAMBOOS";
    public static final String STAT_TIMES_WATERED = "GARDEN_MANAGER_TIMES_WATERED_A_BAMBOO";

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


    /**
     * @param startDate 
     * @param endDate
     * @return
     */
    @Override
    public Set<Statistic> getStatistics(LocalDate startDate, LocalDate endDate)
    {
        Set<Statistic> stats = new HashSet<>();

        // "Times Watered" stat, for each bamboo we see how many times it was watered
        int timesWatered = 0;
        for (Bamboo bamboo : getPlantedBamboos().values()) {
            LocalDate creationDate = LocalDate.ofEpochDay(bamboo.getCreationDate());
            if (creationDate.isEqual(startDate) || creationDate.isAfter(startDate) && ( creationDate.isBefore(endDate) || creationDate.isEqual(endDate))) {
                timesWatered += bamboo.getGrowth(); // Each unit of growth indicates times watered (1 unit = 1 day)
            }
        }

        stats.add(new Statistic(STAT_TIMES_WATERED, timesWatered));

        // "Stored bamboo" stat
        int storedBamboo = 0;
        for (Bamboo bamboo : getStoredBamboos()) {
            LocalDate storedDate = LocalDate.ofEpochDay(bamboo.getStoredDate());
            if (storedDate.isEqual(startDate) || storedDate.isAfter(startDate) && ( storedDate.isBefore(endDate) || storedDate.isEqual(endDate))) {
                storedBamboo++; // Each unit of growth indicates times watered (1 unit = 1 day)
            }
        }
        stats.add(new Statistic(STAT_STORED_BAMBOOS, storedBamboo));

        return stats;
    }
}

package edu.ub.pis.giickos.resources.dao.mock;

import java.util.HashMap;
import java.util.Map;

import edu.ub.pis.giickos.model.garden.Bamboo;
import edu.ub.pis.giickos.model.user.User;
import edu.ub.pis.giickos.resources.dao.CachedGardenDAO;
import edu.ub.pis.giickos.resources.dao.GardenDAO;

public class MockGardenDAO extends CachedGardenDAO{

    public MockGardenDAO() {
        super();
    }

    /**
     * @param user
     * @param listener
     */
    @Override
    public void loadDataForUser(User user, GardenDAO.DataLoadedListener listener)
    {
        plantedBamboos.clear();
        storedBamboos.clear();

        addMockData();
        listener.onLoad(true);
    }

    private void addMockData() {

        Map<String, String> answers = new HashMap<>();
        //Genearte answers that responds to the question related to drink water
        answers.put("1", "First");
        answers.put("2", "Second");
        answers.put("3", "Third");
        answers.put("4", "Fourth");
        answers.put("letter", "This is the letter");

        Bamboo bamboo1 = new Bamboo(0, "Drink water", answers, 1, 3);
        Bamboo bamboo2 = new Bamboo(1, "Sleep well", answers, 2, 7);
        Bamboo bamboo3 = new Bamboo(2, "Clean the dishes", answers, 3, 3);
        Bamboo bamboo5 = new Bamboo(4, "Exercise", answers, 15, 30);
        Bamboo bamboo6 = new Bamboo(5, "Water the plants", answers, 28, 30);


        plantedBamboos.put(0, bamboo1);
        plantedBamboos.put(1, bamboo2);
        plantedBamboos.put(2, bamboo3);
        plantedBamboos.put(4, bamboo5);
        plantedBamboos.put(5, bamboo6);

        Map<String, String> doneAnswers = new HashMap<>();
        doneAnswers.put("1", "P1");
        doneAnswers.put("2", "P2");
        doneAnswers.put("3", "P3");
        doneAnswers.put("4", "P4");
        doneAnswers.put("letter", "L1");

        Bamboo bamboo4 = new Bamboo(3, "Work in the project", doneAnswers, 60, 60);

        storedBamboos.add(bamboo4);
    }

}

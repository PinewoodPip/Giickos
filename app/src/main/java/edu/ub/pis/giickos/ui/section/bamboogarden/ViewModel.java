package edu.ub.pis.giickos.ui.section.bamboogarden;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.ModelHolder;
import edu.ub.pis.giickos.model.garden.Bamboo;
import edu.ub.pis.giickos.model.garden.GardenManager;

public class ViewModel  extends androidx.lifecycle.ViewModel
{
    //An enumerator that holds the different growth times for the bamboo
    public enum BAMBOO_GROWTH_TIME
    {
        THREE_DAYS(R.string.bamboo_growthtime_3days, 3),
        ONE_WEEK(R.string.bamboo_growthtime_1week, 7),
        TWO_WEEKS(R.string.bamboo_growthtime_2weeks, 14),
        ONE_MONTH(R.string.bamboo_growthtime_1month, 30),
        TWO_MONTHS(R.string.bamboo_growthtime_2months, 60),
        THREE_MONTHS(R.string.bamboo_growthtime_3months, 90),
        SIX_MONTHS(R.string.bamboo_growthtime_6months, 180),
        ONE_YEAR(R.string.bamboo_growthtime_1year, 365);

        private final int nameStringResource;
        private final int days;

        BAMBOO_GROWTH_TIME(int nameResource, int days) {
            this.nameStringResource = nameResource;
            this.days = days;
        }

        public int getNameResource() {
            return nameStringResource;
        }

        public int getDays() {
            return days;
        }
    }

    //An enumerator that holds the different questions for the bamboo and what key they have for the hashmap
    public enum BAMBOO_QUESTIONS
    {
        QUESTION_ONE("1"),
        QUESTION_TWO("2"),
        QUESTION_THREE("3"),
        QUESTION_FOUR("4"),
        QUESTION_LETTER("letter");

        private final String key;
        BAMBOO_QUESTIONS(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    //Max number of bamboos that can be planted
    private final int MAX_BAMBOOS = 6;

    //Map that holds the bamboos planted, the key is the position in the garden and the value is the bamboo
    MutableLiveData<Map <Integer, Bamboo>> bamboos = new MutableLiveData<>();


    //List that holds the harvested bamboos
    MutableLiveData<List<Bamboo>> harvestedBamboos = new MutableLiveData<>();


    //A bamboo form that holds the information of the bamboo that is being planted
    BambooForm bambooForm = new BambooForm();

    //When  touching a bamboo in the garden, this variable holds the position of the bamboo
    private int selectedSlot = -1;
    private Bamboo selectedHarvestedBamboo = null;

    private class BambooForm{
        public String title = "";
        public Map<String, String> questionsAnswers;
        public int totalGrowth = 0;

        public BambooForm()
        {
            this.questionsAnswers = new HashMap<>();
        }

        public boolean isComplete()
        {
            if (!this.title.equals("") && this.questionsAnswers.size() == BAMBOO_QUESTIONS.values().length && this.totalGrowth != 0)
            {
                return true;
            }
            return false;
        }

    }

    private GardenManager gardenManager;

    public ViewModel()
    {
        this.gardenManager = ModelHolder.INSTANCE.getGardenManager();
        this.bamboos.setValue(new HashMap<>());
        this.harvestedBamboos.setValue(new LinkedList<>());
        loadFromManager();
    }

    private void loadFromManager()
    {
        harvestedBamboos.getValue().clear();
        harvestedBamboos.setValue(gardenManager.getStoredBamboos());

        bamboos.getValue().clear();
        bamboos.setValue(gardenManager.getPlantedBamboos());
    }

    //LiveData---------------------------------
    public LiveData<Map<Integer, Bamboo>> getPlantedBamboo()
    {
        return bamboos;
    }

    public LiveData<List<Bamboo>> getBamboos()
    {
        return harvestedBamboos;
    }
    //--------------------------------------------------------------

    //Loads the recycler view by using the list of harvested Bamboos
    public List<Bamboo> getHarvestedBamboos()
    {
        return harvestedBamboos.getValue();
    }



    //Checks if there is a bamboo planted in the given slot
    public boolean isBambooPlanted(int slot)
    {
        return bamboos.getValue().containsKey(slot);
    }

    //Returns the first free slot in the garden, if there is no free slot, returns -1
    public int getFreeSlot()
    {
        Map <Integer, Bamboo> placedBamboos = bamboos.getValue();
        for(int i = 0; i < MAX_BAMBOOS; i++)
        {
            if(!placedBamboos.containsKey(i))
            {
                return i;
            }
        }

        return -1;
    }

    //[TODO] probably when using firebase, handle when is not successful, maybe change boolean to int in order to return error codes

    //If the bamboo form is complete, it creates a bamboo and adds it to the map of bamboos
    //---------------------------------- Start of bamboo form methods ----------------------------------
    public boolean plantBamboo(int slot)
    {
        if(!bambooForm.isComplete())
        {
            //System.out.println("Bamboo not complete");
            return false;
        }

        //Retrieves the data from the form that the user has inputed from the menu  to generate a bamboo
        Bamboo bamboo = new Bamboo(slot, bambooForm.title, bambooForm.questionsAnswers,
                0, bambooForm.totalGrowth , UUID.randomUUID().toString());

        //Gets the current bamboos that are planted, and add the new one

        if(gardenManager.plantBamboo(bamboo))
        {
            System.out.println("Bamboo planted in garden manager");
        }

        //bamboos.getValue().put(slot, bamboo);
        bamboos.setValue(bamboos.getValue()); //Making it this way, we update and call the observer


        //Clear the form for the next bamboo by setting a new empty BambooForm
        bambooForm = new BambooForm();
        System.out.println("Bamboo planted");
        return true;
    }

    //This part is for the bambooForm, it sets the different values of the bambooForm
    public void setBambooTitle(String title) {
        bambooForm.title = title;
    }
    public void setBambooGrowTime(BAMBOO_GROWTH_TIME growthTime) {
        bambooForm.totalGrowth = growthTime.getDays();

        Log.d("VM", "Set growth time: " + growthTime.getDays());
    }
    public void addBambooQuestionAnswer(String question, String answer)
    {
        //Adds an answer to the bambooForm, if the answer is empty, it removes the question from the map
        if(answer.equals(""))
        {
            if(bambooForm.questionsAnswers.containsKey(question))
            {
                bambooForm.questionsAnswers.remove(question);
            }
            return;
        }
        bambooForm.questionsAnswers.put(question, answer);
    }
    //----------------------------------- End of bamboo form methods ----------------------------------------

    //Method that returns the bamboo that is planted in the given slot
    public Bamboo getSlotBamboo(int slot) {
        return bamboos.getValue().get(slot);
    }

    //Methods that sets the selected slot
    public void setSelectedSlot(int slot) {
        selectedSlot = slot;
    }
    //Method that returns the selected slot
    public int getSelectedSlot() {
        return selectedSlot;
    }

    //Method that waters the bamboo in the selected slot
    public boolean waterBamboo(int selectedSlot) {
        Bamboo currentBamboo = bamboos.getValue().get(selectedSlot);
        //Tries to water the bamboo, if it is watered, it updates the value of the bamboo
        if(currentBamboo.water())
        {
            if(gardenManager.updatePlantedBamboo(currentBamboo))
            {
                System.out.println("Bamboo updated in garden manager");
            }

            //bamboos.getValue().put(selectedSlot, currentBamboo);
            bamboos.setValue(bamboos.getValue());

            return true;
        }
        //Otherwise, it returns false as the info is not updated
        return false;
    }

    //Method that removes the bamboo in the selected slot
    public void removeBamboo(int selectedSlot) {

        if(gardenManager.deletePlantedBamboo(selectedSlot))
        {
            System.out.println("Bamboo removed in garden manager");
        }

        //bamboos.getValue().remove(selectedSlot);
        bamboos.setValue(bamboos.getValue());


    }

    //Method that harvests the bamboo in the selected slot
    public boolean harvestBamboo(int selectedSlot) {

        Bamboo currentBamboo = bamboos.getValue().get(selectedSlot);
        //Tries to harvest the bamboo, if it is not ready, it returns false
        if(!currentBamboo.harvest())
            return false;

        //Otherwise, it adds the bamboo to the harvested bamboos and removes it from the planted bamboos
        if(gardenManager.saveBambooToStorage(currentBamboo))
        {
            System.out.println("Bamboo harvested in garden manager");
        }
        //harvestedBamboos.getValue().add(currentBamboo);
        harvestedBamboos.setValue(harvestedBamboos.getValue());

        //bamboos.getValue().remove(selectedSlot);
        bamboos.setValue(bamboos.getValue());



        return true;
    }

    public void setCurrentHarvestedBamboo(Bamboo bamboo) {
        selectedHarvestedBamboo = bamboo;
    }

    public void removeHarvestedBamboo() {

        if(gardenManager.deleteStoredBamboo(selectedHarvestedBamboo))
        {
            System.out.println("Bamboo deleted in garden manager");
        }

        //harvestedBamboos.getValue().remove(selectedHarvestedBamboo);
        harvestedBamboos.setValue(harvestedBamboos.getValue());
    }





}

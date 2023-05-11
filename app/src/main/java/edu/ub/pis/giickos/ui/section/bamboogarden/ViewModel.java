package edu.ub.pis.giickos.ui.section.bamboogarden;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.garden.Bamboo;

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
    public enum bambooQuestions
    {
        QUESTION_ONE("1"),
        QUESTION_TWO("2"),
        QUESTION_THREE("3"),
        QUESTION_FOUR("4"),
        QUESTION_LETTER("letter");

        private final String key;
        bambooQuestions(String key) {
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

    //A bamboo form that holds the information of the bamboo that is being planted
    BambooForm bambooForm = new BambooForm();

    private class BambooForm{
        public String title = "";
        public String label = "";
        public Map<String, String> questionsAnswers;
        public int totalGrowth = 0;

        public BambooForm()
        {
            this.questionsAnswers = new HashMap<>();
        }

        public boolean isComplete()
        {
            if (!this.title.equals("") && !this.label.equals("") && this.questionsAnswers.size() == bambooQuestions.values().length && this.totalGrowth != 0)
            {
                return true;
            }
            return false;
        }

    }
    public ViewModel()
    {
        this.bamboos.setValue(new HashMap<>());
    }
    public LiveData<Map<Integer, Bamboo>> getPlantedBamboo()
    {
        return bamboos;
    }

    //Provisional --------------------------------------------------------------
    public List<Bamboo> getBamboos()
    {
        //TODO: Get the bamboos from the map
        //TODO: Get the bamboos from database
        List<Bamboo> bamboos = new ArrayList<>();

        Map<String, String> questionsAnswers = new HashMap<>();
        questionsAnswers.put("1", "This is an answer");
        questionsAnswers.put("2", "This is an answer");
        questionsAnswers.put("3", "This is an answer");
        questionsAnswers.put("4", "This is an answer");
        questionsAnswers.put("letter", "This is an answer");

        Map<String, String> thisIsTheAnswer = new HashMap<>();
        thisIsTheAnswer.put("1", "rewsmar na si sihT");
        thisIsTheAnswer.put("2", "rewsmar na si sihT");
        thisIsTheAnswer.put("3", "rewsmar na si sihT");
        thisIsTheAnswer.put("4", "rewsmar na si sihT");
        thisIsTheAnswer.put("letter", "rewsmar na si sihT");



        bamboos.add(new Bamboo(0,"Drink water", "HWater", questionsAnswers, 1, 7));
        bamboos.add(new Bamboo(1,"Use Giickos", "xGiickos", thisIsTheAnswer, 3,21));
        return bamboos;
    }
    //--------------------------------------------------------------


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
        Bamboo bamboo = new Bamboo(slot, bambooForm.title, bambooForm.label,
                bambooForm.questionsAnswers, 0, bambooForm.totalGrowth);

        //Gets the current bamboos that are planted, and add the new one
        Map <Integer, Bamboo> newBamboo = bamboos.getValue();
        newBamboo.put(slot, bamboo);
        bamboos.setValue(newBamboo); //Making it this way, we update and call the observer

        //TODO add the info the the database by calling the model

        //Clear the form for the next bamboo by setting a new empty BambooForm
        bambooForm = new BambooForm();
        System.out.println("Bamboo planted");
        return true;
    }

    //This part is for the bambooForm, it sets the different values of the bambooForm
    public void setBambooTitle(String title) {
        bambooForm.title = title;
    }
    public void setBambooLabel(String label) {
        bambooForm.label = label;
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






}

package edu.ub.pis.giickos.model.garden;

import java.time.LocalDate;
import java.util.Map;

public class Bamboo
{
    private String uniqueID;
    private int slot;
    private String title;
    private Map<String, String> questionsAnswers;
    private int growth;
    private int totalGrowth;

    private LocalDate creationDate = null;

    private LocalDate storedDate = null;

    private LocalDate lastWatered;

    private final int TOTAL_PHASES = 5; // 5 phases of growth the initial one does not count

    public Bamboo(int slot, String title, Map<String, String> questionsAnswers, int growth, int totalGrowth, String uniqueID)
    {
        this.slot = slot;
        this.title = title;
        this.questionsAnswers = questionsAnswers;
        this.growth = growth;
        this.totalGrowth = totalGrowth;
        this.uniqueID = uniqueID;
    }

    public void addAnswer(String question, String answer)
    {
        this.questionsAnswers.put(question, answer);
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestionsAnswers(Map<String, String> questionsAnswers) {
        this.questionsAnswers = questionsAnswers;
    }

    public Map<String, String> getAnswers() {
        return questionsAnswers;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public int getTotalGrowth() {
        return totalGrowth;
    }

    public void setTotalGrowth(int totalGrowth) {
        this.totalGrowth = totalGrowth;
    }

    public String getTotalGrowthTime()
    {
        return "Total growth time: " + this.totalGrowth;
    }

    public String getAnswer(String question)
    {
        return this.questionsAnswers.get(question);
    }

    public int getCurrentPhase()
    {
        float phase = (float) this.growth / (float) this.totalGrowth;
        return (int) Math.floor((phase) * (this.TOTAL_PHASES ));
    }

    public boolean water()
    {
        //Checks if the bamboo can be watered
        if(!canBeWatered())
            return false;

        //If it can be watered, update the attributes
        this.growth++;
        if(this.growth > this.totalGrowth)
        {
            this.growth = this.totalGrowth;
        }
        this.lastWatered = LocalDate.now();

        return true;
    }
    public boolean canBeWatered()
    {
        //For TESTING purposes, make this method always return true in order to infinite watering

        //First time watering, so ofc it can be watered
        if(lastWatered == null)
            return true;

        //Checks if the bamboo can be watered, the condition is once per day
        LocalDate currentDate = LocalDate.now();

        //If the current is equal or greater than the last watered date plus one day, it can be watered
        if(currentDate.isEqual(this.lastWatered.plusDays(1))
        || currentDate.isAfter(this.lastWatered.plusDays(1)))
            return true;

        return false;
    }

    public boolean canHarvest()
    {
        return this.growth >= this.totalGrowth;
    }


    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public long getLastTimeWatered() {
        if(lastWatered == null)
            return 0;
        return lastWatered.toEpochDay();
    }
    public void setLastWatered(long lastWatered) {
        this.lastWatered = LocalDate.ofEpochDay(lastWatered);
    }

    public void setStoredDate()
    {
        storedDate = LocalDate.now();
    }

    public void setCreationDate()
    {
        creationDate = LocalDate.now();
    }

    public void setCreationDate(LocalDate creationDate)
    {
        this.creationDate = creationDate;
    }

    public void setStoredDate(LocalDate storedDate)
    {
        this.storedDate = storedDate;
    }




    public long getStoredDate()
    {
        if (storedDate == null)
            return 0;

        return storedDate.toEpochDay();
    }

    public long getCreationDate()
    {
        if ( creationDate == null)
            return 0;
        return creationDate.toEpochDay();
    }



}

package edu.ub.pis.giickos.model.garden;

import java.time.LocalDate;
import java.util.Map;

public class Bamboo
{
    //public String id;
    private int slot;
    private String title;
    private Map<String, String> questionsAnswers;
    private int growth;
    private int totalGrowth;

    private LocalDate lastWatered;

    private final int TOTAL_PHASES = 5; // 5 phases of growth the initial one does not count

    public Bamboo(int slot, String title, Map<String, String> questionsAnswers, int growth, int totalGrowth)
    {
        this.slot = slot;
        this.title = title;
        this.questionsAnswers = questionsAnswers;
        this.growth = growth;
        this.totalGrowth = totalGrowth;
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

    public Map<String, String> getQuestionsAnswers() {
        return questionsAnswers;
    }

    public void setQuestionsAnswers(Map<String, String> questionsAnswers) {
        this.questionsAnswers = questionsAnswers;
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

    public boolean harvest()
    {
        return this.growth == this.totalGrowth;
    }


}

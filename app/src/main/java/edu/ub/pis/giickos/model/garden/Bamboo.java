package edu.ub.pis.giickos.model.garden;

import java.util.List;
import java.util.Map;

public class Bamboo
{
    //public String id;
    private int slot;
    private String title;
    private String label;
    private Map<String, String> questionsAnswers;
    private int growth;
    private int totalGrowth;

    private final int TOTAL_PHASES = 5; // 5 phases of growth the initial one does not count

    public Bamboo(int slot, String title, String label, Map<String, String> questionsAnswers, int growth, int totalGrowth)
    {
        this.slot = slot;
        this.title = title;
        this.label = label;
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

    public void setLabel(String label) {
        this.label = label;
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

    public String getLabel()
    {
        return this.label;
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
        return (int) Math.floor((this.growth / this.totalGrowth) * (this.TOTAL_PHASES ));
    }

}

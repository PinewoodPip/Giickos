package edu.ub.pis.giickos.model.garden;

import java.util.List;
import java.util.Map;

public class Bamboo
{
    //public String id;
    private String title;
    private String label;
    private Map<String, String> questionsAnswers;
    private int growth;
    private int totalGrowth;

    public Bamboo(String title, String label, Map<String, String> questionsAnswers, int growth, int totalGrowth)
    {
        this.title = title;
        this.label = label;
        this.questionsAnswers = questionsAnswers;
        this.growth = growth;
        this.totalGrowth = totalGrowth;
    }
    {
        this.title = title;
        this.label = label;
        this.questionsAnswers = questionsAnswers;
        this.growth = growth;
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










}

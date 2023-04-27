package edu.ub.pis.giickos.ui.section.bamboogarden;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ub.pis.giickos.model.garden.Bamboo;

public class ViewModel  extends androidx.lifecycle.ViewModel
{
    //Provisional
    public List<Bamboo> getBamboos()
    {
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



        bamboos.add(new Bamboo("Drink water", "HWater", questionsAnswers, 1, 7));
        bamboos.add(new Bamboo("Use Giickos", "xGiickos", thisIsTheAnswer, 3,21));
        return bamboos;
    }








}

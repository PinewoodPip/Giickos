package edu.ub.pis.giickos.ui.section.miscellaneous.team;


import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.team.Team;

public class ViewModel extends androidx.lifecycle.ViewModel{

    //Provisional, és només per veure com es veu el layout
    public ViewModel() {

    }

    public List<Team> getTeams()
    {
        List<Team> teams  = new ArrayList<>();
        teams.add(new Team(R.drawable.profile_colored,"Giickos", "This is the official team of giickos", new String[]{"Giickos1", "Giicko2", "Giicko3"}));
        teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test", new String[]{"test1", "test2", "test3"}));

        /*teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test", new String[]{"test1", "test2", "test3"}));
        teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test", new String[]{"test1", "test2", "test3"}));
        teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test", new String[]{"test1", "test2", "test3"}));
        teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test", new String[]{"test1", "test2", "test3"}));
        teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test", new String[]{"test1", "test2", "test3"}));
        teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test", new String[]{"test1", "test2", "test3"}));
        teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test", new String[]{"test1", "test2", "test3"}));
        teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test", new String[]{"test1", "test2", "test3"}));
        teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test", new String[]{"test1", "test2", "test3"}));
        teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test", new String[]{"test1", "test2", "test3"}));
        */
        return teams;
    }






}

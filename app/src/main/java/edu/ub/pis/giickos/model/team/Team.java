package edu.ub.pis.giickos.model.team;

import android.widget.ImageView;
import android.widget.TextView;

public class Team {
    //Provisional class for the team object. It lacks team id etc...
    private int teamImage;
    private String teamName;
    private String teamDescription;

    private String[] teamMembers;

    public Team(int teamImage, String teamName, String teamDescription, String[] teamMembers) {
        this.teamImage = teamImage;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.teamMembers = teamMembers;
    }

    public int getTeamImage() {
        return teamImage;
    }

    public void setTeamImage(int teamImage) {
        this.teamImage = teamImage;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamDescription() {
        return teamDescription;
    }

    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
    }

    public String[] getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String[] teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getSomeMembers(int numMembers)
    {
        if (numMembers > teamMembers.length)
            numMembers = teamMembers.length;

        String members = "";
        for(int i = 0; i < numMembers; i++)
        {
            members += teamMembers[i];
            if(i != numMembers - 1)
            {
                members += ", ";
            }
        }
        return members;
    }
    public String getSomeMembers()
    {
        return getSomeMembers(3);
    }

}

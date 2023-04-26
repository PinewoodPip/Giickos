package edu.ub.pis.giickos.model.team;

public class Invitation {
    //Provisional class for the Invitations.
    private String teamName;
    private String teamInviter;
    private String teamId;


    public Invitation(String teamName, String teamInviter, String teamId) {
        this.teamName = teamName;
        this.teamInviter = teamInviter;
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public String getTeamInviter() {
        return teamInviter;
    }
    public void setTeamInviter(String teamInviter) {
        this.teamInviter = teamInviter;
    }
    public String getTeamId() {
        return teamId;
    }
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
    public String getInvitationText()
    {
       return teamInviter + " has invited you to join " + teamName;
    }


}

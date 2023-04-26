package edu.ub.pis.giickos.ui.section.miscellaneous.team;


import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.team.Invitation;
import edu.ub.pis.giickos.model.team.Team;

public class ViewModel extends androidx.lifecycle.ViewModel{

    //Provisional, és només per veure com es veu el layout
    public ViewModel() {

    }

    public List<Team> getTeams()
    {
        List<Team> teams  = new ArrayList<>();
        List<Team.Member> members =  new ArrayList<>();
        members.add(new Team.Member("Giickos0", Team.Role.OWNER));
        members.add(new Team.Member("Giickos1", Team.Role.MEMBER));
        members.add(new Team.Member("Giickos2", Team.Role.MEMBER));
        members.add(new Team.Member("Giickos3", Team.Role.MEMBER));

        List<Team.Member> members_2 =  new ArrayList<>();
        members_2.add(new Team.Member("Tester0", Team.Role.OWNER));
        members_2.add(new Team.Member("Tester1", Team.Role.MEMBER));
        members_2.add(new Team.Member("Tester2", Team.Role.MEMBER));
        members_2.add(new Team.Member("Tester3", Team.Role.MEMBER));
        members_2.add(new Team.Member("Tester4", Team.Role.MEMBER));
        members_2.add(new Team.Member("Tester5", Team.Role.MEMBER));
        members_2.add(new Team.Member("Tester6", Team.Role.MEMBER));
        members_2.add(new Team.Member("Tester7", Team.Role.MEMBER));
        members_2.add(new Team.Member("Tester8", Team.Role.MEMBER));



        teams.add(new Team(R.drawable.profile_colored,"Giickos", "This is the official team of giickos",
                members));
        teams.add(new Team(R.drawable.giickos_plus,"Test", "This is a test",
                members_2));

        return teams;
    }
    public List<Invitation> getInvitations()
    {
        List<Invitation> invitations = new ArrayList<>();
        invitations.add(new Invitation("Giickos", "Jiaqi Li", "0"));
        invitations.add(new Invitation("Google", "Larry Page", "1"));
        invitations.add(new Invitation("Tesla", "Elon Musk", "2"));
        invitations.add(new Invitation("Amazon", "Jeff Bezos", "3"));
        invitations.add(new Invitation("Microsoft", "Bill Gates", "4"));
        return invitations;
    }






}

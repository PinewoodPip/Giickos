package edu.ub.pis.giickos.model.team;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Team {
    //Provisional class for the team object. It lacks team id etc...

    public enum Role {
        OWNER ("Owner"),
        MEMBER  ("Member"),
        ;
        private String role;

        Role(String role) {
            this.role = role;
        }
        public String getRole() {
            return role;
        }
    }
    private int teamImage;
    private String teamName;
    private String teamDescription;
    private List<Member> teamMembers;

    private String owner;

    public static class Member {
        private String name;
        private Role role;
        public Member(String name, Role role) {
            this.name = name;
            this.role = role;
        }
        public String getRole() {
            return role.getRole();
        }
        public void setRole(Role role) {
            this.role = role;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

    }

    public Team(int teamImage, String teamName, String teamDescription, List<Member> teamMembers) {
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

    public List<Member> getTeamMembers() {
        return teamMembers;
    }

    public void addTeamMember(Member member)
    {
        teamMembers.add(member);
    }
    public void setTeamMembers(List<Member> teamMembers) {
        this.teamMembers = teamMembers;
    }
    public String getTeamOwner()
    {
        for (Member member : teamMembers)
        {
            if (member.getRole().equalsIgnoreCase(Role.OWNER.getRole()))
            {
                return member.getName();
            }
        }
        return null;
    }

    public String getSomeMembers(int numMembers)
    {
        if (numMembers > teamMembers.size())
            numMembers = teamMembers.size();

        String members = "";
        for(int i = 0; i < numMembers; i++)
        {
            members += teamMembers.get(i).name;
            if(i != numMembers - 1)
            {
                members += ", ";
            }
        }
        return members;
    }
    public String getSomeMembers()
    {
        return getSomeMembers(2);
    }
}

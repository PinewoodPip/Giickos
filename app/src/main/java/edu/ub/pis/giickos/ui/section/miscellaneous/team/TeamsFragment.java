package edu.ub.pis.giickos.ui.section.miscellaneous.team;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Debug;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.team.Team;
import edu.ub.pis.giickos.ui.generic.form.FancyFormCard;
import edu.ub.pis.giickos.ui.generic.form.FormCard;
import edu.ub.pis.giickos.ui.section.miscellaneous.team.ViewModel;

// Displays the user's teams. TODO
public class TeamsFragment extends GiickosFragment {

    private ViewModel viewModel;
    public TeamsFragment() {
        // Required empty public constructor
    }

    public static TeamsFragment newInstance() {
        TeamsFragment fragment = new TeamsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_miscellaneous_teams, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        closeWindows(view); //Close all windows

        //Invitations menu
        Button teamInvitations = view.findViewById(R.id.teams_invitation_button);
        Button teamInvitationsClose = view.findViewById(R.id.exit_invitation_menu);
        View blockerMenuInvitation = view.findViewById(R.id.team_blocker_invitation_menu);

        //Team Create menu
        Button addTeam = view.findViewById(R.id.teams_add_button);
        Button addTeamClose = view.findViewById(R.id.exit_create_menu);
        Button addTeamCreate = view.findViewById(R.id.create_team);
        View blockerMenuCreate = view.findViewById(R.id.team_blocker_create_menu);

        TextView invitationsCount = view.findViewById(R.id.teams_invitations_alert_count);

        //Team menu
        Button teamAddMembers = view.findViewById(R.id.team_add_member);
        Button teamRemoveMembers = view.findViewById(R.id.team_remove_member);
        Button teamLeave = view.findViewById(R.id.team_leave_team);
        Button teamBack = view.findViewById(R.id.exit_team_menu);
        View blockerMenuTeam = view.findViewById(R.id.team_blocker_team_menu);

        TextView teamName = view.findViewById(R.id.team_menu_name);
        TextView teamDescription = view.findViewById(R.id.team_menu_description);
        ImageView teamIcon = view.findViewById(R.id.team_menu_icon);


        invitationsCount.setText("0"); //TODO get invitations count using live data

        //Invitations buttons
        RecyclerView invitationsView = view.findViewById(R.id.teams_invitations_recycler_view);
        //Li assignem un layout manager
        LinearLayoutManager invitationManager = new LinearLayoutManager(getContext());
        invitationsView.setLayoutManager(invitationManager);

        //Init adapter + set adapter to recycler view
        TeamInvitationListAdapter teamInvitationListAdapter = new TeamInvitationListAdapter(getContext(),viewModel.getInvitations());
        invitationsView.setAdapter(teamInvitationListAdapter);
        teamInvitations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindows(view);
                openView(view.findViewById(R.id.show_team_invitation_menu));
                //TODO get the model and get the invitations
            }
        });
        teamInvitationsClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindows(view);
            }
        });
        blockerMenuInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do nothing, block background clicks
            }
        });


        //Create team buttons
        addTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindows(view);
                openView(view.findViewById(R.id.show_create_team_menu));
            }
        });
        addTeamClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindows(view);
            }
        });
        addTeamCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create team
                //Calls the view model to create the team with the inputed data
                closeWindows(view);
            }
        });
        blockerMenuCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do nothing, block background clicks
            }
        });

        //Create teams interactables cards
        addTextField(R.drawable.title, "Title: ", "", InputType.TYPE_TEXT_VARIATION_NORMAL, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                //viewModel.setTaskName(editable.toString())
                //TODO set title
                System.out.println("Title: " + editable.toString());
            }
        });
        addTextField(R.drawable.profile_white, "Members: ", "", InputType.TYPE_TEXT_VARIATION_NORMAL, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                //viewModel.setTaskName(editable.toString())
                //TODO set members
                System.out.println("Title: " + editable.toString());
            }
        });

        FancyFormCard field = addField(R.drawable.image_white, "Team Icon: ");
        field.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO set icon
                System.out.println("Intent, spinner or something to set image");
            }
        });

        addTextField(R.drawable.description_white, "Description: ", "", InputType.TYPE_TEXT_VARIATION_NORMAL, new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                //viewModel.setTaskName(editable.toString())
                //TODO set description of the team
                System.out.println("Title: " + editable.toString());
            }
        });
        //Team buttons
        RecyclerView recyclerView = view.findViewById(R.id.teams_recycler_view);
        //Li assignem un layout manager
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        //Init adapter + set adapter to recycler view
        TeamListAdapter teamListAdapter = new TeamListAdapter(getContext(),viewModel.getTeams());
        recyclerView.setAdapter(teamListAdapter);

        teamListAdapter.setOnItemClickListener(new TeamListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Team team) {
                teamName.setText(team.getTeamName());
                teamDescription.setText(team.getTeamDescription());
                teamIcon.setImageResource(team.getTeamImage());

                //Loads the recycler view with the members of the team
                RecyclerView recyclerView = view.findViewById(R.id.teams_team_recycler_view);

                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(manager);

                //Init adapter + set adapter to recycler view
                TeamMembersListAdapter teamListAdapter = new TeamMembersListAdapter(getContext(),team);
                recyclerView.setAdapter(teamListAdapter);

                openView(view.findViewById(R.id.show_team_menu));
            }
        });
        teamAddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add members
                System.out.println("Add members");
            }
        });
        teamRemoveMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO remove members
                System.out.println("Remove members");
            }
        });
        teamLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO leave team
                System.out.println("Leave team");
            }
        });
        teamBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindows(view);
            }
        });
        blockerMenuTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do nothing, block background clicks
            }
        });




    }
    //No the best way to do this, but it works
    private void closeWindows(View view){
        View showInvitation = view.findViewById(R.id.show_team_invitation_menu);
        showInvitation.setVisibility(View.GONE);
        View showCreate = view.findViewById(R.id.show_create_team_menu);
        showCreate.setVisibility(View.GONE);
        View showTeam = view.findViewById(R.id.show_team_menu);
        showTeam.setVisibility(View.GONE);
    }
    private void openView(View view){
        view.setVisibility(View.VISIBLE);
    }
    private void addTextField(int iconID, String label, String inputLabel, int inputType, @Nullable TextWatcher listener) {
        FancyFormCard field = addField(iconID, label);

        field.addTextField(inputType, inputLabel, listener);
    }
    private FancyFormCard addField(int iconID, String label) {
        return addField(iconID, label, -1);
    }
    private FancyFormCard addField(int iconID, String label, int backgroundColor) {
        FancyFormCard field = FancyFormCard.newInstance(iconID, label, backgroundColor);

        addChildFragment(field, R.id.create_team_list, true);

        return field;
    }
}
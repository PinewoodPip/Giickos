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
import android.widget.ImageView;
import android.widget.TextView;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
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

        RecyclerView recyclerView = view.findViewById(R.id.teams_recycler_view);
        //Li assignem un layout manager
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        //Init adapter + set adapter to recycler view
        TeamListAdapter teamListAdapter = new TeamListAdapter(getContext(),viewModel.getTeams());
        recyclerView.setAdapter(teamListAdapter);

        //Other buttons
        closeWindows(view); //Close all windows
        ImageView teamInvitations = view.findViewById(R.id.teams_invitation_button);
        ImageView teamInvitationsClose = view.findViewById(R.id.exit_invitation_menu);


        ImageView addTeam = view.findViewById(R.id.teams_add_button);
        ImageView addTeamClose = view.findViewById(R.id.exit_create_menu);
        ImageView addTeamCreate = view.findViewById(R.id.create_team);

        TextView invitationsCount = view.findViewById(R.id.teams_invitations_alert_count);

        invitationsCount.setText("0"); //TODO get invitations count using live data

        //Invitations buttons
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

        FormCard field = addField(R.drawable.image_white, "Team Icon: ");
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

    }
    //No the best way to do this, but it works
    private void closeWindows(View view){
        View showInvitation = view.findViewById(R.id.show_team_invitation_menu);
        showInvitation.setVisibility(View.GONE);
        View showCreate = view.findViewById(R.id.show_create_team_menu);
        showCreate.setVisibility(View.GONE);
        /*View showTeam = view.findViewById(R.id.show_team_menu);
        showTeam.setVisibility(View.GONE);*/
    }
    private void openView(View view){
        view.setVisibility(View.VISIBLE);
    }
    private void addTextField(int iconID, String label, String inputLabel, int inputType, @Nullable TextWatcher listener) {
        FormCard field = addField(iconID, label);

        field.addTextField(inputType, inputLabel, listener);
    }
    private FormCard addField(int iconID, String label) {
        return addField(iconID, label, -1);
    }
    private FormCard addField(int iconID, String label, int backgroundColor) {
        FormCard field = FormCard.newInstance(iconID, label, backgroundColor);

        addChildFragment(field, R.id.create_team_list, true);

        return field;
    }
}
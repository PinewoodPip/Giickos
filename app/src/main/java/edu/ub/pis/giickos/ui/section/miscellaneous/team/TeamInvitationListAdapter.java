package edu.ub.pis.giickos.ui.section.miscellaneous.team;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.team.Invitation;
import edu.ub.pis.giickos.model.team.Team;

public class TeamInvitationListAdapter extends RecyclerView.Adapter<TeamInvitationListAdapter.ViewHolder>
{
    private List<Invitation> invitations;
    private LayoutInflater inflater;
    private Context context;

    public TeamInvitationListAdapter(Context context, List<Invitation> invitations)
    {
        this.invitations = invitations;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public TeamInvitationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_teams_invitations, parent, false);

        return new TeamInvitationListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamInvitationListAdapter.ViewHolder holder, int position) {
        holder.bind(invitations.get(holder.getBindingAdapterPosition()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //TODO do something like ampliate the invitation or not
            }
        });
        ImageView accept = holder.itemView.findViewById(R.id.team_accept_invitation);
        ImageView decline = holder.itemView.findViewById(R.id.team_decline_invitation);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO accept invitation and add to team remove from invitations
                System.out.println("Invitation accepted");
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO decline invitation and remove from invitations
                System.out.println("Invitation declined");
            }
        });


    }



    @Override
    public int getItemCount() {
        return invitations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView teamInvitationText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamInvitationText = itemView.findViewById(R.id.team_invitation_text);
        }

        public void bind(final Invitation invitation) {
            teamInvitationText.setText(invitation.getInvitationText());
        }



    }
}

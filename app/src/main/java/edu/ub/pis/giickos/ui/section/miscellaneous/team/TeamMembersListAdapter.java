package edu.ub.pis.giickos.ui.section.miscellaneous.team;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Member;
import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.team.Invitation;
import edu.ub.pis.giickos.model.team.Team;

public class TeamMembersListAdapter  extends RecyclerView.Adapter<TeamMembersListAdapter.ViewHolder>
{
    private List<Team.Member> members;
    private String owner;
    private LayoutInflater inflater;
    private Context context;

    public TeamMembersListAdapter(Context context, Team team)
    {
        this.members = team.getTeamMembers();
        this.owner = (team.getTeamOwner());

        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public TeamMembersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_teams_member, parent, false);

        return new TeamMembersListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamMembersListAdapter.ViewHolder holder, int position) {
        //I know is very ugly...
        String memberName = members.get(holder.getBindingAdapterPosition()).getName();
        String role = Team.Role.MEMBER.getRole();
        int frameColor = Color.rgb(70,181,137);

        if (members.get(holder.getBindingAdapterPosition()).getName().equalsIgnoreCase(owner))
        {
            role = Team.Role.OWNER.getRole();
            frameColor = Color.rgb(177,189,105);
        }
        holder.bind(memberName, role, frameColor);
        //TODO select member and then use buttons or something

    }



    @Override
    public int getItemCount() {
        return members.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView memberName;
        private final TextView memberRole;
        private ImageView frame;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.team_member_name);
            memberRole = itemView.findViewById(R.id.team_member_role);
            frame = itemView.findViewById(R.id.team_member_frame);
        }

        public void bind(final String member, final String role, final int frameColor) {
            memberName.setText(member);
            memberRole.setText(role);
            this.frame.setBackgroundColor(frameColor);
        }



    }
}

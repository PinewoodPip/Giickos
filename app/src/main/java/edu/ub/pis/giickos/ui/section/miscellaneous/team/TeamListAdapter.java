package edu.ub.pis.giickos.ui.section.miscellaneous.team;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.team.Team;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder>
{
    public interface OnItemClickListener {
        void onItemClick(Team team);
    }
    private List<Team> teams;
    private LayoutInflater inflater;
    private Context context;

    private OnItemClickListener mListener;

    public TeamListAdapter(Context context, List<Team> teams)
    {
        this.teams = teams;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public TeamListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_teams_team, parent, false);

        return new TeamListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamListAdapter.ViewHolder holder, int position) {
        holder.bind(teams.get(holder.getBindingAdapterPosition()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(teams.get(holder.getBindingAdapterPosition()));
                }
            }
        });
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @Override
    public int getItemCount() {
        return teams.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageView teamImage;
        private final TextView teamName;
        private final TextView teamMembers;
        //private final TextView teamDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamImage = itemView.findViewById(R.id.team_image);
            teamName = itemView.findViewById(R.id.team_name);
            teamMembers = itemView.findViewById(R.id.team_members);
            //teamDescription = itemView.findViewById(R.id.team_description);

        }

        public void bind(final Team team) {
            teamImage.setImageResource(team.getTeamImage());
            teamName.setText(team.getTeamName());
            teamMembers.setText(team.getSomeMembers());
            //teamDescription.setText(team.getTeamDescription());
        }



    }
}

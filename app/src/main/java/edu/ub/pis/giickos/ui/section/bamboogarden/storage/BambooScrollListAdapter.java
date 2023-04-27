package edu.ub.pis.giickos.ui.section.bamboogarden.storage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.garden.Bamboo;

public class BambooScrollListAdapter extends RecyclerView.Adapter<BambooScrollListAdapter.ViewHolder>
{
    public interface OnItemClickListener {
        void onItemClick(Bamboo bamboo);
    }
    private List<Bamboo> bamboos;
    private LayoutInflater inflater;
    private Context context;

    private OnItemClickListener mListener;

    public BambooScrollListAdapter(Context context, List<Bamboo> bamboos)
    {
        this.bamboos = bamboos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public BambooScrollListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_bamboo_scroll, parent, false);

        return new BambooScrollListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BambooScrollListAdapter.ViewHolder holder, int position) {
        holder.bind(bamboos.get(holder.getBindingAdapterPosition()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(bamboos.get(holder.getBindingAdapterPosition()));
                }
            }
        });
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @Override
    public int getItemCount() {
        return bamboos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView name;
        private final TextView totalTime;
        //private final TextView teamDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bamboo_name);
            totalTime = itemView.findViewById(R.id.bamboo_time);
        }

        public void bind(final Bamboo bamboo) {
            name.setText(bamboo.getTitle());
            totalTime.setText(bamboo.getTotalGrowthTime());
        }



    }
}

package edu.ub.pis.giickos.taskexplorer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.ub.pis.giickos.R;

// Displays a task within the task explorer.
public class Task extends Fragment {
    public static final String ARG_ICON = "Icon";
    public static final String ARG_LABEL = "Label";

    private int iconID;
    private String label;
    // TODO identifier field for task

    public Task() {} // Required empty public constructor

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param iconID
     * @param label
     * @return A new instance of fragment Task.
     */
    public static Task newInstance(int iconID, String label) {
        Task fragment = new Task();
        Bundle args = new Bundle();

        args.putInt(ARG_ICON, iconID);
        args.putString(ARG_LABEL, label);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            iconID = arguments.getInt(ARG_ICON);
            label = arguments.getString(ARG_LABEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        ImageView iconView = view.findViewById(R.id.icon_task);
        TextView labelView = view.findViewById(R.id.label_task_name);

        iconView.setImageResource(iconID);
        labelView.setText(label);

        return view;
    }
}
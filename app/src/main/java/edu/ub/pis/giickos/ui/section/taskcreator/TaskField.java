package edu.ub.pis.giickos.ui.section.taskcreator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;

// Fragment for fields within task creator.
public class TaskField extends GiickosFragment {
    private static final String ARG_ICON = "Icon";
    private static final String ARG_LABEL = "Label";

    private int iconID;
    private String label;

    public TaskField() {} // Required empty public constructor

    public static TaskField newInstance(int iconID, String label) {
        TaskField fragment = new TaskField();
        Bundle args = new Bundle();

        args.putInt(ARG_ICON, iconID);
        args.putString(ARG_LABEL, label);
        fragment.setArguments(args);

        return fragment;
    }

    public void addElement(Fragment fragment) {
        addChildFragment(fragment, R.id.list_main, true);
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
        View view = inflater.inflate(R.layout.fragment_task_field, container, false);
        ImageView image = view.findViewById(R.id.image_icon);
        TextView text = view.findViewById(R.id.label);

        image.setImageResource(iconID);
        text.setText(label);

        return view;
    }
}
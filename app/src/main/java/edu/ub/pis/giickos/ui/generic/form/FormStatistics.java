package edu.ub.pis.giickos.ui.generic.form;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;

public class FormStatistics extends GiickosFragment
{
    public static final String ARG_ICON = "Icon";
    public static final String ARG_LABEL = "Label";
    public static final String ARG_VALUE = "Value";

    private int iconID;
    private String label;
    private String value;

    private View thisView;
    public FormStatistics() {} // Required empty public constructor

    public static FormStatistics newInstance(int iconID, String label, String value) {
        FormStatistics fragment = new FormStatistics();
        Bundle args = new Bundle();

        args.putInt(ARG_ICON, iconID);
        args.putString(ARG_LABEL, label);
        args.putString(ARG_VALUE, value);

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
            value = arguments.getString(ARG_VALUE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_statistics, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        ImageView image = view.findViewById(R.id.Statistics_Icon);
        TextView label = (TextView) view.findViewById(R.id.Statistics_Label);
        TextView value = (TextView) view.findViewById(R.id.Statistics_Value);

        // Set icon, label, value
        image.setImageResource(iconID);
        label.setText(this.label);
        value.setText(this.value);
    }








}

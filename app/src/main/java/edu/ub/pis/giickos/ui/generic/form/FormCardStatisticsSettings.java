package edu.ub.pis.giickos.ui.generic.form;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.List;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.DatePickerListener;
import edu.ub.pis.giickos.ui.main.TimePickerListener;

public class FormCardStatisticsSettings extends GiickosFragment
{
    public static final String ARG_ICON = "Icon";
    public static final String ARG_LABEL = "Label";
    public static final String ARG_TL_COLOR = "LeftColor";
    public static final String ARG_TR_COLOR = "RightColor";

    public static final String ARG_TEXT_COLOR = "TextColor";

    private int iconID;
    private String label;
    private int colorLeft;
    private int colorRight;

    private int colorText;

    private View.OnClickListener clickListener;

    public FormCardStatisticsSettings() {} // Required empty public constructor

    public static FormCardStatisticsSettings newInstance(int iconID, String label, int colorLeft, int colorRight, int colorText) {
        FormCardStatisticsSettings fragment = new FormCardStatisticsSettings();
        Bundle args = new Bundle();


        args.putInt(ARG_ICON, iconID);
        args.putString(ARG_LABEL, label);
        args.putInt(ARG_TL_COLOR, colorLeft);
        args.putInt(ARG_TR_COLOR, colorRight);
        args.putInt(ARG_TEXT_COLOR, colorText);
        fragment.setArguments(args);

        return fragment;
    }

    public static FormCardStatisticsSettings newInstance(int iconID, String label) {
        return newInstance(iconID, label, -1, -1 , -1);
    }

    public void setClickListener(View.OnClickListener listener) {
        this.clickListener = listener;

        // Update the listener on the element if this method is called after the view was created
        updateClickListener(getView());
    }

    // Updates the click listener on the view.
    private void updateClickListener(View view) {
        if (view != null) {
            view.setOnClickListener(clickListener);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            iconID = arguments.getInt(ARG_ICON);
            label = arguments.getString(ARG_LABEL);
            colorLeft = arguments.getInt(ARG_TL_COLOR);
            colorRight =  arguments.getInt(ARG_TR_COLOR);
            colorText = arguments.getInt(ARG_TEXT_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_form_statistics, container, false);
    }

    public void addElement(Fragment fragment) {
        addChildFragment(fragment, R.id.statistics_settings, true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        ImageView icon = view.findViewById(R.id.statistics_settings_icon);
        ImageView leftFrame = getView().findViewById(R.id.statistics_frame_left);
        ImageView rightFrame = getView().findViewById(R.id.statistics_frame_right);
        TextView text = view.findViewById(R.id.statistics_settings_label);
        // Set icon and labeld
        icon.setImageResource(iconID);
        text.setText(label);

        if(colorLeft != -1 && colorRight != -1)
        {
            leftFrame.setColorFilter(colorLeft, PorterDuff.Mode.ADD);
            rightFrame.setColorFilter(colorRight, PorterDuff.Mode.ADD);
            text.setTextColor(colorText);
        }
        updateClickListener(view);
    }

}

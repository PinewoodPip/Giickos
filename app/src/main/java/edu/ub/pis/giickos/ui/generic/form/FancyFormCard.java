package edu.ub.pis.giickos.ui.generic.form;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import edu.ub.pis.giickos.R;

public class FancyFormCard extends ContainerCard
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

    public FancyFormCard() {} // Required empty public constructor

    public static FancyFormCard newInstance(int iconID, String label, int colorLeft, int colorRight, int colorText) {
        FancyFormCard fragment = new FancyFormCard();
        Bundle args = new Bundle();

        args.putInt(ARG_ICON, iconID);
        args.putString(ARG_LABEL, label);
        args.putInt(ARG_TL_COLOR, colorLeft);
        args.putInt(ARG_TR_COLOR, colorRight);
        args.putInt(ARG_TEXT_COLOR, colorText);
        fragment.setArguments(args);

        return fragment;
    }

    public static FancyFormCard newInstance(int iconID, String label, int color) {
        return newInstance(iconID, label, color, color, -1);
    }

    public static FancyFormCard newInstance(int iconID, String label) {
        return newInstance(iconID, label, -1, -1, -1);
    }

    public void setClickListener(View.OnClickListener listener) {
        this.clickListener = listener;

        // Update the listener on the element if this method is called after the view was created
        updateClickListener(getView());
    }

    // Updates the click listener on the view.
    private void updateClickListener(View view) {
        if (view != null) {
            // The subcards need to also have this set; click events from them do not bubble
            CardView leftCard = view.findViewById(R.id.statistics_frame_left);
            CardView rightCard = view.findViewById(R.id.statistics_frame_right);
            FrameLayout clickOverlay = view.findViewById(R.id.click_dummy);

            view.setOnClickListener(clickListener);
            leftCard.setOnClickListener(clickListener);
            rightCard.setOnClickListener(clickListener);

            clickOverlay.setOnClickListener(clickListener);
            clickOverlay.setVisibility(clickListener == null ? View.GONE : View.VISIBLE);
        }
    }

    public void updateLabel(String label)
    {
        TextView text = getView().findViewById(R.id.statistics_settings_label);
        text.setText(label);
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
        return inflater.inflate(R.layout.fragment_card_form_fancy, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        ImageView icon = view.findViewById(R.id.statistics_settings_icon);
        FrameLayout leftFrame = getView().findViewById(R.id.statistics_frame_left);
        FrameLayout rightFrame = getView().findViewById(R.id.statistics_frame_right);
        TextView text = view.findViewById(R.id.statistics_settings_label);

        // Set icon and label
        icon.setImageResource(iconID);
        text.setText(label);

        if (colorLeft != -1) {
            leftFrame.setBackgroundTintList(ColorStateList.valueOf(requireContext().getResources().getColor(colorLeft, null)));
        }
        if (colorRight != -1) {
            rightFrame.setBackgroundTintList(ColorStateList.valueOf(requireContext().getResources().getColor(colorRight, null)));
        }
        if (colorText != -1) {
            text.setTextColor(requireContext().getResources().getColor(colorText, null));
        }

        updateClickListener(view);
        setListDirection(listDirection);
    }

    @Override
    protected int getContainerID() {
        return R.id.statistics_settings;
    }
}

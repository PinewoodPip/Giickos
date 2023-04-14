package edu.ub.pis.giickos.ui.generic.form;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.DatePickerListener;
import edu.ub.pis.giickos.ui.main.TimePickerListener;

// Card fragment with an icon, label, and a horizontal list of custom elements.
public class FormCard extends GiickosFragment {
    public static final String ARG_ICON = "Icon";
    public static final String ARG_LABEL = "Label";
    public static final String ARG_BG_COLOR = "BackgroundColor";

    private int iconID;
    private String label;
    private int backgroundColorOverride; // Set to -1 to use default background color
    private int listDirection;

    private View.OnClickListener clickListener;

    public FormCard() {} // Required empty public constructor

    public static FormCard newInstance(int iconID, String label, int color) {
        FormCard fragment = new FormCard();
        Bundle args = new Bundle();

        // Default direction is Left-to-right
        fragment.listDirection = LinearLayout.LAYOUT_DIRECTION_LTR;

        args.putInt(ARG_ICON, iconID);
        args.putString(ARG_LABEL, label);
        args.putInt(ARG_BG_COLOR, color);
        fragment.setArguments(args);

        return fragment;
    }

    public static FormCard newInstance(int iconID, String label) {
        return newInstance(iconID, label, -1);
    }

    public void addElement(Fragment fragment) {
        addChildFragment(fragment, R.id.list_main, true);
    }


    public TextField addTextField(int inputType, String label, TextWatcher listener) {
        TextField textField = TextField.newInstance(label, inputType);
        addElement(textField);

        textField.setListener(listener);

        return textField;
    }

    public TimeField addTimeField(String id, String label, TimePickerListener listener) {
        TimeField timeField = TimeField.newInstance(id, label);
        addElement(timeField);

        timeField.setListener(listener);

        return timeField;
    }

    public DateField addDateField(String id, String label, DatePickerListener listener) {
        DateField dateField = DateField.newInstance(id, label);
        addElement(dateField);

        dateField.setListener(listener);

        return dateField;
    }

    public FormSpinner addSpinner(List<String> items, int selectedIndex) {
        FormSpinner spinner = FormSpinner.newInstance(items, selectedIndex);
        addElement(spinner);

        return spinner;
    }

    // Sets the direction of the list of child elements added via addElement().
    public void setListDirection(int direction) {
        View view = getView();

        this.listDirection = direction;

        if (view != null) {
            LinearLayout listView = view.findViewById(R.id.list_main);

            listView.setLayoutDirection(listDirection);
        }
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
            backgroundColorOverride = arguments.getInt(ARG_BG_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CardView view = (CardView)inflater.inflate(R.layout.fragment_card_form, container, false);
        ImageView image = view.findViewById(R.id.image_icon);
        TextView text = view.findViewById(R.id.label);

        // Set background color
        if (backgroundColorOverride != -1) {
            view.setBackgroundTintList(ColorStateList.valueOf(backgroundColorOverride));
        }

        // Set icon and label
        image.setImageResource(iconID);
        text.setText(label);

        updateClickListener(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        setListDirection(listDirection);
    }
}
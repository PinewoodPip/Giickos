package edu.ub.pis.giickos.ui.section.miscellaneous.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.Switch;
import edu.ub.pis.giickos.ui.generic.form.FormCard;
import edu.ub.pis.giickos.ui.generic.form.TextField;

// Displays the settings of the app. TODO
public class SettingsFragment extends GiickosFragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_miscellaneous_settings, container, false);
    }

    private FormCard addCard(int iconID, String label, int backgroundColor) {
        FormCard card = FormCard.newInstance(iconID, label, backgroundColor);

        addChildFragment(card, R.id.list_main, true);

        return card;
    }

    private FormCard addCard(int iconID, String label) {
        return addCard(iconID, label, -1);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        LinearLayout list = view.findViewById(R.id.list_main);

        FormCard notificationsCard = addCard(R.drawable.placeholder, getString(R.string.miscellaneous_tab_settings_notifications));
        Switch notificationsSwitch = Switch.newInstance(true);
        notificationsSwitch.setListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("TODO", "Notifications toggled");
            }
        });
        notificationsCard.setListDirection(LinearLayout.LAYOUT_DIRECTION_RTL);
        notificationsCard.addElement(notificationsSwitch);

        FormCard feedbackCard = addCard(R.drawable.placeholder, getString(R.string.miscellaneous_tab_settings_feedback));
        FormCard aboutUsCard = addCard(R.drawable.placeholder, getString(R.string.miscellaneous_tab_settings_about));

        FormCard usernameCard = addCard(R.drawable.placeholder, getString(R.string.generic_label_username));
        TextField usernameTextField = TextField.newInstance("TODO", InputType.TYPE_TEXT_VARIATION_PERSON_NAME, false);
        usernameCard.addElement(usernameTextField);

        FormCard emailCard = addCard(R.drawable.placeholder, getString(R.string.generic_label_email));
        TextField emailTextField = TextField.newInstance("TODO", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, false);
        emailCard.addElement(emailTextField);

        FormCard changeAccountCard = addCard(R.drawable.placeholder, getString(R.string.miscellaneous_tab_settings_changeaccount));
        FormCard logoutCard = addCard(R.drawable.placeholder, getString(R.string.miscellaneous_tab_settings_logout), getResources().getColor(R.color.destructive_action));
    }
}
package edu.ub.pis.giickos.ui.section.timer.detox;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProvider;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.form.FancyFormCard;

// Fragment for the detox menu.
public class DetoxFragment extends GiickosFragment {

    private ViewModelDetox viewModelDetox;
    private NotificationManager notificationManager;

    public DetoxFragment() {
        // Required empty public constructor
    }

    public static DetoxFragment newInstance() {
        DetoxFragment fragment = new DetoxFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelDetox = new ViewModelProvider(getActivity()).get(ViewModelDetox.class);
    }

    public void onViewCreated(View view, Bundle bundle){

        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        viewModelDetox.notificationManager = notificationManager;

        FancyFormCard[] cards = new FancyFormCard[3];

        cards[0] = addCardWithTint(R.drawable.calls_block, getString(R.string.timer_option_blockcalls),
                R.color.detox_1, // left frame
                R.color.detox_2, // right frame
                R.color.detox_text); // text color

        cards[1] = addCardWithTint(R.drawable.notification_block, getString(R.string.timer_option_blocknotifications),
                R.color.detox_1, // left frame
                R.color.detox_2, // right frame
                R.color.detox_text); // text color
        cards[2] = addCardWithTint(R.drawable.bloqued, getString(R.string.timer_option_blockapps),
                R.color.detox_1, // left frame
                R.color.detox_2, // right frame
                R.color.detox_text); // text color

        // Adds a switch field allow or block the incoming calls
        cards[0].addSwitchField(false, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if (!notificationManager.isNotificationPolicyAccessGranted())
                {
                    // Ask the user to grant permission to use the Notification Policy
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                    // Always put to false if we try to grant acces, if the user did not allow it in settings,
                    // The checked should be false, otherwise, just touch again and works flawlessly
                    compoundButton.setChecked(false);

                }
                else {
                    Log.d("UI", "emergency calls switched: " + b);
                    viewModelDetox.emergencyCallsSwitchIsChecked.setValue(b);
                }

            }});

        // Adds a switch field in order to allow or block the notifications
        cards[1].addSwitchField(false, new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if (!notificationManager.isNotificationPolicyAccessGranted()) {
                    // Ask the user to grant permission to use the Notification Policy
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                    // Same logic as the previous one
                    compoundButton.setChecked(false);
                }
                else {
                    Log.d("UI", "No notification switched: " + b);
                    viewModelDetox.noNotificationSwitchIsChecked.setValue(b);
                }
            }});

        // Adds a switch field in order to block or no all the apps.
        cards[2].addSwitchField(false, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    Log.d("UI", "All apps locked switched on");

                } else {
                    Log.d("UI", "All apps locked switched off");
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_timer_detox, container, false);
    }

    private FancyFormCard addCardWithTint(int iconID, String label, int colorLeft, int colorRight, int colorText) {
        FancyFormCard card = FancyFormCard.newInstance(iconID, label, colorLeft, colorRight, colorText);
        card.setListDirection(LinearLayout.LAYOUT_DIRECTION_RTL);
        addChildFragment(card, R.id.detox_settings_list, true);

        return card;
    }
}
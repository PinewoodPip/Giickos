package edu.ub.pis.giickos.ui.section.timer.detox;


import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;


import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;

import edu.ub.pis.giickos.ui.activities.main.MainActivity;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetoxFragment extends GiickosFragment {

    private Switch noNotificationSwitch;

    private Switch emergencyCallsSwitch;

    private Switch allAppsLockedSwitch;

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


        emergencyCallsSwitch = view.findViewById(R.id.switch_emergency_calls);
        noNotificationSwitch = view.findViewById(R.id.switch_no_notification);
        allAppsLockedSwitch = view.findViewById(R.id.switch_all_apps_locked);

        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        viewModelDetox.notificationManager = notificationManager;
        emergencyCallsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!notificationManager.isNotificationPolicyAccessGranted()) {
                    // Ask the user to grant permission to use the Notification Policy
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);

                }else{
                    if(emergencyCallsSwitch.isChecked()){
                        System.out.println("emergency calls switched on");
                        viewModelDetox.emergencyCallsSwitchIsChecked.setValue(true);
                    }else{
                        System.out.println("emergency calls switched off");
                        viewModelDetox.emergencyCallsSwitchIsChecked.setValue(false);
                    }
                    viewModelDetox.controlNotification();
                }
            }
        });

        noNotificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!notificationManager.isNotificationPolicyAccessGranted()) {
                    // Ask the user to grant permission to use the Notification Policy
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }else{
                    if(noNotificationSwitch.isChecked()){
                        System.out.println("no notification switched on");
                        viewModelDetox.noNotificationSwitchIsChecked.setValue(true);
                    }else{
                        System.out.println("no notification switched off");
                        viewModelDetox.noNotificationSwitchIsChecked.setValue(false);
                    }
                    viewModelDetox.controlNotification();
                }

            }
        });

        allAppsLockedSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allAppsLockedSwitch.isChecked()){
                    System.out.println("all apps locked switched on");

                }else{
                    System.out.println("all apps locked switched off");

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



}
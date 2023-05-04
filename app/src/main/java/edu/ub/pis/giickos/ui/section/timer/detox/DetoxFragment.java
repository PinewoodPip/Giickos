package edu.ub.pis.giickos.ui.section.timer.detox;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.section.timer.TimerFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetoxFragment extends GiickosFragment {

    private Switch noNotificationSwitch;

    private Switch emergencyCallsSwitch;

    private Switch allAppsLockedSwitch;


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

    }

    public void onViewCreated(View view, Bundle bundle){


        emergencyCallsSwitch = view.findViewById(R.id.switch_emergency_calls);
        noNotificationSwitch = view.findViewById(R.id.switch_no_notification);
        allAppsLockedSwitch = view.findViewById(R.id.switch_all_apps_locked);

        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        emergencyCallsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("emergency calls switched");
                if (!notificationManager.isNotificationPolicyAccessGranted()) {
                    // Ask the user to grant permission to use the Notification Policy
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    
                    startActivity(intent);
                }
                controlNotification();

            }
        });

        noNotificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("no notification switched");
                if (!notificationManager.isNotificationPolicyAccessGranted()) {
                    // Ask the user to grant permission to use the Notification Policy
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }
                controlNotification();
            }
        });

        allAppsLockedSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("all apps locked switched");
            }
        });



    }

    // Aquest metode controla les notificacions, podem dir que depenent dels dos
    // switches (emergency call i notifications) habilitem les notificaicions d'una manera o d'un altre
    public void controlNotification(){

        if (emergencyCallsSwitch.isChecked() && noNotificationSwitch.isChecked()){
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
        }else if (emergencyCallsSwitch.isChecked() && !noNotificationSwitch.isChecked()){
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
            NotificationManager.Policy policy = new NotificationManager.Policy(
                    NotificationManager.Policy.PRIORITY_CATEGORY_MESSAGES,
                    NotificationManager.Policy.PRIORITY_SENDERS_ANY,
                    NotificationManager.Policy.SUPPRESSED_EFFECT_SCREEN_ON |
                            NotificationManager.Policy.SUPPRESSED_EFFECT_SCREEN_OFF |
                            NotificationManager.Policy.SUPPRESSED_EFFECT_AMBIENT);

            notificationManager.setNotificationPolicy(policy);
        }else if (!emergencyCallsSwitch.isChecked() && noNotificationSwitch.isChecked()){
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
        }else if (!emergencyCallsSwitch.isChecked() && !noNotificationSwitch.isChecked()){
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_timer_detox, container, false);
    }



}
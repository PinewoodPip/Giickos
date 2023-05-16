package edu.ub.pis.giickos.ui.section.timer.detox;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.activities.main.MainActivity;
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
                }else{
                    controlNotification();
                }


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
                }else{
                    controlNotification();
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
    /*
    @Override
    public void onResume() {
        super.onResume();
        if (isRestrictNavigation) {
            getActivity().registerReceiver(homeButtonReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isRestrictNavigation) {
            getActivity().unregisterReceiver(homeButtonReceiver);
        }
    }
    private BroadcastReceiver homeButtonReceiver = new BroadcastReceiver() {
        String SYSTEM_DIALOG_REASON_KEY = "reason";
        String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY) || reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                    // Prompt the user before allowing navigation
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setMessage("Are you sure you want to leave the app?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                // Allow navigation
                                dialog.dismiss();
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                                // Dismiss dialog and avoid navigation
                                dialog.dismiss();
                            });
                    builder.show();
                }
            }
        }
    };
    */
    /*
    @Override
    public void onResume() {
        super.onResume();
        startDetectingAppSwitch();
    }

    private boolean hasUsageStatsPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        UsageStatsManager usageStatsManager = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
        if (usageStatsManager == null) {
            return false;
        }
        long time = System.currentTimeMillis();
        List<UsageStats> stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
        return !stats.isEmpty();
    }

    private boolean isAttached;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isAttached = true;
        startDetectingAppSwitch();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }

    private void startDetectingAppSwitch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isAttached) {
            UsageStatsManager usageStatsManager = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
            long interval = 1000;
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    long endTime = System.currentTimeMillis();
                    long beginTime = endTime - interval;
                    List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, beginTime, endTime);
                    if (usageStatsList != null && !usageStatsList.isEmpty()) {
                        UsageStats recentStats = usageStatsList.get(0);
                        String packageName = recentStats.getPackageName();
                        String hola = getContext().getPackageName();
                        if (!hola.equals(packageName)) {
                            bringAppToFront();
                        }
                    }
                    handler.postDelayed(this, interval);
                }
            }, interval);
        }
    }
    private void bringAppToFront() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    */




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
package edu.ub.pis.giickos.ui.section.timer.detox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;

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


        emergencyCallsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("emergency calls switched");
            }
        });

        noNotificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("no notification switched");
            }
        });

        allAppsLockedSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("all apps locked switched");
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
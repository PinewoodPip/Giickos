package edu.ub.pis.giickos.ui.section.timer.detox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetoxFragment extends GiickosFragment {


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

        Button detoxStartButton = view.findViewById(R.id.button_detox_start);

        detoxStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO;
                System.out.println("detoxStartButton clicked");
                if (detoxStartButton.getText().equals("STOP")){
                    detoxStartButton.setText("START");
                }else{
                    detoxStartButton.setText("STOP");
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
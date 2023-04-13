package edu.ub.pis.giickos.ui.section.timer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.MainViewModel;
import edu.ub.pis.giickos.ui.section.Section;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerSection#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerSection extends Section {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STOP = "STOP";
    private static final String ARG_SELECT_TASK = "SELECT_TASK";

    // TODO: Rename and change types of parameters
    private Button mParam1;
    private String mParam2;

    public TimerSection() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TimerSection.
     */
    // TODO: Rename and change types and number of parameters
    public static TimerSection newInstance() {
        TimerSection fragment = new TimerSection();
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
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    public void onViewCreated(View view, Bundle bundle){

        Button stopButton = view.findViewById(R.id.button_stop_timer);
        Button selectTaskButton = view.findViewById(R.id.button_select_task);
        CheckBox detoxCheckbox = view.findViewById(R.id.checkBox_detox);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO;
                System.out.println("stopButton clicked");
            }
        });

        selectTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO;
                System.out.println("selectTaskButton clicked");
            }
        });

        detoxCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO;
                System.out.println("detoxCheckbox clicked");
            }
        });

    }

    @Override
    public MainViewModel.TYPE getType() {
        return MainViewModel.TYPE.TIMER;
    }
}
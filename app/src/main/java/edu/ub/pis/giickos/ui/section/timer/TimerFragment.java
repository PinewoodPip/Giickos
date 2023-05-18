package edu.ub.pis.giickos.ui.section.timer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.ViewModelHelpers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    private Button startPauseButton;
    private Button selectTaskButton;

    private Button resetButton;
    private Button setPomodoroButton;

    private Button setBreakButton;

    private CheckBox detoxCheckbox;
    private EditText editMinuteEdittext;

    private EditText editBreakMinuteEdittext;
    private TextView timerView;

    private TextView timerModeTextView;

    private Spinner selectTaskSpinner;

    private ViewModel viewModel;



    public TimerFragment() {
        // Required empty public constructor
    }


    public static TimerFragment newInstance() {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
    }

    public void onViewCreated(View view, Bundle bundle){

        startPauseButton = view.findViewById(R.id.button_start_timer);
        selectTaskButton = view.findViewById(R.id.button_select_task);
        setPomodoroButton = view.findViewById(R.id.button_set_minutes);
        resetButton = view.findViewById(R.id.button_reset_timer);
        setBreakButton = view.findViewById(R.id.button_set_break);

        detoxCheckbox = view.findViewById(R.id.checkBox_detox);
        editMinuteEdittext = view.findViewById(R.id.edit_text_minutes);
        editBreakMinuteEdittext = view.findViewById(R.id.edit_text_minutes_break);
        timerView = view.findViewById(R.id.textView_timer);
        timerModeTextView = view.findViewById(R.id.textView_timer_mode);

        selectTaskSpinner = view.findViewById(R.id.spinner_select_task);


        List<ViewModelHelpers.TaskData> tasks = viewModel.getTasks().getValue();
        List<Object> taskObjects = new ArrayList<>();
        taskObjects.add("None");
        taskObjects.addAll(tasks);

        ArrayAdapter<Object> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, taskObjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        selectTaskSpinner.setAdapter(adapter);


        timerModeTextView.setText("Pomodoro");
        viewModel.setTime(viewModel.pomodoroTimeInMillis);
        viewModel.isPomodoro = true;


        selectTaskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0){
                    long millisInput = tasks.get(i-1).durationInMinutes * 60000;
                    viewModel.setTime(millisInput);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setPomodoroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = editMinuteEdittext.getText().toString();

                //change minutes to milisecons
                long millisInput = Long.parseLong(input) * 60000;

                if (input.length() >= 0 && millisInput >= 0){
                    System.out.println("set metode correcte");
                    viewModel.pomodoroTimeInMillis = millisInput;
                    if(viewModel.getIsPomodoro()){
                        viewModel.setTime(millisInput);
                    }

                    editMinuteEdittext.setText("");
                }

            }
        });

        setBreakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editBreakMinuteEdittext.getText().toString();
                long millisInput = Long.parseLong(input) * 60000;
                if (input.length() >= 0 && millisInput >= 0){
                    System.out.println("set metode correcte");
                    viewModel.breakTimeInMillis = millisInput;
                    if(!viewModel.getIsPomodoro()){
                        viewModel.setTime(millisInput);
                    }
                    editBreakMinuteEdittext.setText("");
                }


            }
        });

        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (viewModel.getIsIstimerRunning()) {
                    viewModel.pauseTimer();
                } else {
                    viewModel.startTimer();

                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.resetTimer();
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

        /*
        final Observer<List<ViewModelHelpers.TaskData>> observerTask = new Observer<List<ViewModelHelpers.TaskData>>() {
            @Override
            public void onChanged(List<ViewModelHelpers.TaskData> task) {
                adapter.clear();
                adapter.addAll(taskObjects);
            }
        };
        viewModel.getTasks().observe(this.getViewLifecycleOwner(), observerTask);
         */
        final Observer<String> observerTimer = new Observer<String>() {
            @Override
            public void onChanged(String timer) {
                timerView.setText(timer);
            }
        };

        viewModel.getTimer().observe(this.getViewLifecycleOwner(), observerTimer);


        final Observer<String> observerTextStartPauseButton = new Observer<String>() {
            @Override
            public void onChanged(String textStartPauseButton) {
                startPauseButton.setText(textStartPauseButton);
            }
        };
        viewModel.getTextStartPauseButton().observe(this.getViewLifecycleOwner(), observerTextStartPauseButton);

        final Observer<String> observerTextTimerMode = new Observer<String>() {
            @Override
            public void onChanged(String textTimerMode) {
                timerModeTextView.setText(textTimerMode);
            }
        };

        viewModel.getTextTimerMode().observe(this.getViewLifecycleOwner(), observerTextTimerMode);


        final Observer<Boolean> observerVisibilityEditMinuteEdittext = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visibilityEditMinuteEdittext) {
                editMinuteEdittext.setVisibility(visibilityEditMinuteEdittext? View.VISIBLE : View.INVISIBLE);
            }
        };
        viewModel.getVisibilityEditMinuteEdittext().observe(this.getViewLifecycleOwner(), observerVisibilityEditMinuteEdittext);

        final Observer<Boolean> observerVisibilityEditBreakMinuteEdittext = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visibilityEditBreakMinuteEdittext) {
                editBreakMinuteEdittext.setVisibility(visibilityEditBreakMinuteEdittext ? View.VISIBLE : View.INVISIBLE);
            }
        };
        viewModel.getVisibilityEditBreakMinuteEdittext().observe(this.getViewLifecycleOwner(), observerVisibilityEditBreakMinuteEdittext);


        final Observer<Boolean> observerVisibilitySetPomodoroButton = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visibilitySetPomodoroButton) {
                setPomodoroButton.setVisibility(visibilitySetPomodoroButton? View.VISIBLE : View.INVISIBLE);
            }
        };
        viewModel.getVisibilitySetPomodoroButton().observe(this.getViewLifecycleOwner(), observerVisibilitySetPomodoroButton);

        final Observer<Boolean> observerVisibilitySetBreakButton = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visibilitySetBreakButton) {
                setBreakButton.setVisibility(visibilitySetBreakButton? View.VISIBLE : View.INVISIBLE);
            }
        };
        viewModel.getVisibilitySetBreakButton().observe(this.getViewLifecycleOwner(), observerVisibilitySetBreakButton);


        final Observer<Boolean> observerVisibilityResetButton = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visibilityResetButton) {
                resetButton.setVisibility(visibilityResetButton? View.VISIBLE : View.INVISIBLE);
            }
        };
        viewModel.getVisibilityResetButton().observe(this.getViewLifecycleOwner(), observerVisibilityResetButton);


        final Observer<Boolean> observerVisibilityStartPauseButton = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visibilityStartPauseButton) {
                startPauseButton.setVisibility(visibilityStartPauseButton? View.VISIBLE : View.INVISIBLE);
            }
        };
        viewModel.getVisibilityStartPauseButton().observe(this.getViewLifecycleOwner(), observerVisibilityStartPauseButton);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_timer_timer, container, false);
    }

}
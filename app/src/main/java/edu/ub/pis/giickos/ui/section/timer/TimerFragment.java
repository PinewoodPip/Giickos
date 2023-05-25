package edu.ub.pis.giickos.ui.section.timer;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.stephenvinouze.materialnumberpickercore.MaterialNumberPicker;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.ViewModelHelpers;
import edu.ub.pis.giickos.ui.activities.main.MainActivity;
import edu.ub.pis.giickos.ui.utils.notification.Notification;

// Fragment for the timer tab.
public class TimerFragment extends Fragment {

    private Button startPauseButton, resetButton;

    private CheckBox detoxCheckbox;

    private TextView timerView, pomodoroTextView, breakTextView;

    private TextView timerModeTextView;

    private Spinner selectTaskSpinner;

    private MaterialNumberPicker pomodoroTimePicker, breakTimePicker;

    private ViewModel viewModel;
    private TimerFragment timerFragment = this;



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

    private void setupTasksSpinner() {
        View view = getView();
        selectTaskSpinner = view.findViewById(R.id.spinner_select_task);


        List<ViewModelHelpers.TaskData> tasks = viewModel.getTasks().getValue();
        List<Object> taskObjects = new ArrayList<>();
        taskObjects.add(getString(R.string.generic_label_none));
        taskObjects.addAll(tasks);
        /*
        for (ViewModelHelpers.TaskData tasca: tasks) {
            if (tasca.durationInMinutes > 0){
                taskObjects.add(tasca);
            }
        }

         */

        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(getContext(), android.R.layout.simple_spinner_item, taskObjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        int selectedIndex = 0;

        // Restore spinner selection
        ViewModelHelpers.TaskData selectedTask = viewModel.getSelectedTask().getValue();
        if (selectedTask != null) {
            for (int i = 0; i < tasks.size() && selectedIndex == 0; ++i) {
                ViewModelHelpers.TaskData task = tasks.get(i);
                if (task.id.equals(selectedTask.id)) {
                    selectedIndex = i + 1; // +1 to account for "None" option
                }
            }
        }

        selectTaskSpinner.setAdapter(adapter);
        selectTaskSpinner.setSelection(selectedIndex);

        selectTaskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.newTaskIndex.setValue(i);

                if (i != 0 && viewModel.newTaskIndex.getValue() != viewModel.oldTaskIndex.getValue() ) {
                    ViewModelHelpers.TaskData task = tasks.get(i-1);
                    viewModel.selectTask(task);
                    viewModel.oldTaskIndex.setValue(viewModel.newTaskIndex.getValue());
                }
                else {
                    viewModel.selectTask(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void updatePickers() {
        View view = getView();

        if (view != null) {
            view.findViewById(R.id.list_editors).setVisibility(viewModel.getEditMode().getValue() ? View.VISIBLE : View.INVISIBLE);

            pomodoroTimePicker.setValue(viewModel.getPomodoroTime());
            breakTimePicker.setValue(viewModel.getBreakTime());
        }
    }

    private void updateProgressBar(boolean smooth) {
        CircularProgressIndicator progressBar = getView().findViewById(R.id.progressbar);
        double progress = (double) viewModel.getMillisLeft() / (double) viewModel.getStartingMillis() * 100;

        progressBar.setProgress((int) progress, smooth);
    }

    public void onViewCreated(View view, Bundle bundle) {
        FrameLayout timerFrame = view.findViewById(R.id.frame_timer);
        Button confirmEditButton = view.findViewById(R.id.button_confirm);

        startPauseButton = view.findViewById(R.id.button_start_timer);
        resetButton = view.findViewById(R.id.button_reset_timer);
        detoxCheckbox = view.findViewById(R.id.checkBox_detox);

        timerView = view.findViewById(R.id.textView_timer);
        timerModeTextView = view.findViewById(R.id.textView_timer_mode);

        pomodoroTimePicker = view.findViewById(R.id.pomodoroTimePicker);
        breakTimePicker = view.findViewById(R.id.breakTimePicker);

        pomodoroTextView = view.findViewById(R.id.textView_pomodoro);
        breakTextView = view.findViewById(R.id.textView_break);

        // Toggle edit mode when the timer is clicked.
        timerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!viewModel.getIsIstimerRunning()) {
                    viewModel.toggleEditMode();
                }
            }
        });

        // Close edit mode when confirm is clicked.
        confirmEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.toggleEditMode();
            }
        });

        // Toggle the number pickers based on whether editing is enabled.
        viewModel.getEditMode().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                updatePickers();
            }
        });
        updatePickers();

        pomodoroTimePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int minutes) {
                long millis = minutes * 60000;
                viewModel.textPomodoroPicker.setValue((millis / 60000));
                if(viewModel.getIsPomodoro()){
                    viewModel.setTime(millis);
                }
            }
        });



        breakTimePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int minutes) {
                long millis = minutes * 60000;
                viewModel.textBreakPicker.setValue((millis / 60000));
                if(!viewModel.getIsPomodoro()){
                    viewModel.setTime(millis);
                }
            }
        });

        setupTasksSpinner();

        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getIsIstimerRunning()) {
                    viewModel.pauseTimer();
                }
                else {
                    viewModel.startTimer();
                    Notification.pomodoroNotification( timerFragment.getContext(), "Giickos pomodoro",
                            "time finished", MainActivity.class, pomodoroTimePicker.getValue());
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.resetTimer();
            }
        });

        detoxCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO;
                System.out.println("detoxCheckbox clicked");

            }
        });

        final Observer<String> observerTimer = new Observer<String>() {
            @Override
            public void onChanged(String timer) {
                timerView.setText(viewModel.getTimerLabel());
            }
        };
        timerView.setText(viewModel.getTimerLabel());

        // Update the progress bar
        viewModel.getTimer().observe(getViewLifecycleOwner(), new Observer<String>() {
            // Why is this a string?
            @Override
            public void onChanged(String s) {
                updateProgressBar(true);
            }
        });
        updateProgressBar(false);

        viewModel.getTimer().observe(this.getViewLifecycleOwner(), observerTimer);

        final Observer<String> observerTextStartPauseButton = new Observer<String>() {
            @Override
            public void onChanged(String textStartPauseButton) {
                startPauseButton.setText(textStartPauseButton);

                // the ideal should be using enums from viewmodel...
                if (textStartPauseButton.equals("Start")) {
                    startPauseButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.custom_button_animated_green, null));
                }
                else {
                    startPauseButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.custom_button_animated_red, null));
                }



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

        final Observer<Boolean> observerVisibilitySelectTaskSpinner = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visibilitySelectTaskSpinner) {
                selectTaskSpinner.setVisibility(visibilitySelectTaskSpinner? View.VISIBLE : View.INVISIBLE);
            }
        };
        viewModel.getVisibilitySelectTaskSpinner().observe(this.getViewLifecycleOwner(), observerVisibilitySelectTaskSpinner);

        final Observer<Boolean> observerVisibilityDetoxCheckBox = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visibilityDetoxCheckBox) {
                detoxCheckbox.setVisibility(visibilityDetoxCheckBox? View.VISIBLE : View.INVISIBLE);
            }
        };
        viewModel.getVisibilityDetoxCheckBox().observe(this.getViewLifecycleOwner(), observerVisibilityDetoxCheckBox);



        final Observer<Boolean> observerIsTaskSelected = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visibilityIsTaskSelected) {
                pomodoroTextView.setVisibility(!visibilityIsTaskSelected? View.VISIBLE : View.INVISIBLE);
                pomodoroTimePicker.setVisibility(!visibilityIsTaskSelected? View.VISIBLE : View.INVISIBLE);
            }
        };
        viewModel.getIsTaskSelected().observe(this.getViewLifecycleOwner(), observerIsTaskSelected);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_timer_timer, container, false);
    }
}
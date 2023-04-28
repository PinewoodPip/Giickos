package edu.ub.pis.giickos.ui.section.timer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import edu.ub.pis.giickos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    private Button startPauseButton;
    private Button selectTaskButton;

    private Button resetButton;
    private Button setMinuteButton;
    private CheckBox detoxCheckbox;
    private EditText editMinuteEdittext;
    private TextView timerView;

    private CountDownTimer countDownTimer;

    private boolean istimerRunning;

    private long startTimeInMillis;
    private long timeLeftInMillis;
    private long endTime;

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

    }
    public void onViewCreated(View view, Bundle bundle){

        startPauseButton = view.findViewById(R.id.button_start_timer);
        selectTaskButton = view.findViewById(R.id.button_select_task);
        setMinuteButton = view.findViewById(R.id.button_set_minutes);
        resetButton = view.findViewById(R.id.button_reset_timer);

        detoxCheckbox = view.findViewById(R.id.checkBox_detox);
        editMinuteEdittext = view.findViewById(R.id.edit_text_minutes);
        timerView = view.findViewById(R.id.textView_timer);



        setMinuteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("set button clickat");
                String input = editMinuteEdittext.getText().toString();

                long millisInput = Long.parseLong(input) * 60000;

                if (input.length() >= 0 && millisInput >= 0){
                    System.out.println("set metode correcte");
                    setTime(millisInput);

                    editMinuteEdittext.setText("");
                }

            }
        });

        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("startButton clicked");

                if (istimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
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
    private void setTime(long milliseconds) {
        startTimeInMillis = milliseconds;
        resetTimer();
    }

    private void startTimer() {
        endTime = System.currentTimeMillis() + timeLeftInMillis;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                istimerRunning = false;
                updateWatchInterface();
                //TODO: notification
            }
        }.start();

        istimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        istimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        timeLeftInMillis = startTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        timerView.setText(timeLeftFormatted);

    }

    private void updateWatchInterface() {
        if (istimerRunning) {
            editMinuteEdittext.setVisibility(View.INVISIBLE);
            setMinuteButton.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.INVISIBLE);
            startPauseButton.setText("Pause");
        } else {
            editMinuteEdittext.setVisibility(View.VISIBLE);
            setMinuteButton.setVisibility(View.VISIBLE);
            startPauseButton.setText("Start");

            if (timeLeftInMillis < 1000) {
                startPauseButton.setVisibility(View.INVISIBLE);
            } else {
                startPauseButton.setVisibility(View.VISIBLE);
            }

            if (timeLeftInMillis < startTimeInMillis) {
                resetButton.setVisibility(View.VISIBLE);
            } else {
                resetButton.setVisibility(View.INVISIBLE);
            }
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_timer_timer, container, false);
    }
}
package edu.ub.pis.giickos.ui.section.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Locale;

import edu.ub.pis.giickos.R;

public class ViewModel extends androidx.lifecycle.ViewModel{

    public Button startPauseButton;
    public Button selectTaskButton;

    public Button resetButton;
    public Button setPomodoroButton;

    public Button setBreakButton;

    public CheckBox detoxCheckbox;
    public EditText editMinuteEdittext;
    public EditText editBreakMinuteEdittext;
    public TextView timerView;
    public TextView timerMode;
    public CountDownTimer countDownTimer;
    public boolean istimerRunning;
    public boolean isPomodoro;
    public long pomodoroTimeInMillis = 25 * 60000;
    public long breakTimeInMillis = 5 * 60000;
    public long timerInMillis;
    public long timeLeftInMillis;
    public long endTime;

    //public MutableLiveData<Long> pomodoroTimeInMillis, breakTimeInMillis;
    public MutableLiveData<String> timer,textStartPauseButton, textTimerMode;

    private MutableLiveData<Boolean> visibilityEditMinuteEdittext,
            visibilityEditBreakMinuteEdittext,visibilitySetPomodoroButton,
            visibilitySetBreakButton,visibilityResetButton, visibilityStartPauseButton;





    public ViewModel() {
        timer = new MutableLiveData<String>();
        textStartPauseButton = new MutableLiveData<String>();
        textTimerMode = new MutableLiveData<String>();

        //pomodoroTimeInMillis = new MutableLiveData<Long>();
        //breakTimeInMillis = new MutableLiveData<Long>();

        visibilityEditMinuteEdittext = new MutableLiveData<Boolean>(true);
        visibilityEditBreakMinuteEdittext = new MutableLiveData<>(true);
        visibilitySetPomodoroButton = new MutableLiveData<>(true);
        visibilitySetBreakButton = new MutableLiveData<>(true);
        visibilityResetButton = new MutableLiveData<>(true);
        visibilityStartPauseButton = new MutableLiveData<>(true);

    }

    public LiveData<String> getTimer() {return timer;}
    public LiveData<String> getTextTimerMode() {return textTimerMode;}
    public LiveData<String> getTextStartPauseButton() {return textStartPauseButton;}
    public LiveData<Boolean> getVisibilityEditMinuteEdittext() {return visibilityEditMinuteEdittext;}
    public LiveData<Boolean> getVisibilityEditBreakMinuteEdittext() {return visibilityEditBreakMinuteEdittext;}
    public LiveData<Boolean> getVisibilitySetPomodoroButton() {return visibilitySetPomodoroButton;}
    public LiveData<Boolean> getVisibilitySetBreakButton() {return visibilitySetBreakButton;}
    public LiveData<Boolean> getVisibilityResetButton() {return visibilityResetButton;}
    public LiveData<Boolean> getVisibilityStartPauseButton() {return visibilityStartPauseButton;}

    //public LiveData<Long> getPomodoroTimeInMillis(){return pomodoroTimeInMillis;}
    //public LiveData<Long> getBreakTimeInMillis(){return breakTimeInMillis;}




    public void startTimer() {
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
                if (isPomodoro){
                    timeLeftInMillis = breakTimeInMillis;

                    setTime(timeLeftInMillis);
                    updateCountDownText();
                    textTimerMode.setValue("Break");
                    isPomodoro = false;
                }else{
                    timeLeftInMillis = pomodoroTimeInMillis;
                    setTime(timeLeftInMillis);
                    updateCountDownText();
                    textTimerMode.setValue("Pomodoro");
                    isPomodoro = true;
                }

                //TODO: notification
            }
        }.start();

        istimerRunning = true;
        updateWatchInterface();
    }

    public void setTime(long milliseconds) {
        timerInMillis = milliseconds;
        resetTimer();
    }

    public void pauseTimer() {
        countDownTimer.cancel();
        istimerRunning = false;
        updateWatchInterface();
    }

    public void resetTimer() {
        timeLeftInMillis = timerInMillis;
        updateCountDownText();
        updateWatchInterface();
    }
    public void updateCountDownText() {
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

        //timerView.setText(timeLeftFormatted);
        timer.setValue(timeLeftFormatted);


    }

    public void updateWatchInterface() {
        if (istimerRunning) {
            /*
            editMinuteEdittext.setVisibility(View.INVISIBLE);
            editBreakMinuteEdittext.setVisibility(View.INVISIBLE);
            setPomodoroButton.setVisibility(View.INVISIBLE);
            setBreakButton.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.INVISIBLE);
            startPauseButton.setText("Pause");
            */

            visibilityEditMinuteEdittext.setValue(false);
            visibilityEditBreakMinuteEdittext.setValue(false);
            visibilitySetPomodoroButton.setValue(false);
            visibilitySetBreakButton.setValue(false);
            visibilityResetButton.setValue(false);

            textStartPauseButton.setValue("Pause");


        } else {
            /*
            editMinuteEdittext.setVisibility(View.VISIBLE);
            editBreakMinuteEdittext.setVisibility(View.VISIBLE);
            setPomodoroButton.setVisibility(View.VISIBLE);
            setBreakButton.setVisibility(View.VISIBLE);
            startPauseButton.setText("Start");
            */

            visibilityEditMinuteEdittext.setValue(true);
            visibilityEditBreakMinuteEdittext.setValue(true);
            visibilitySetPomodoroButton.setValue(true);
            visibilitySetBreakButton.setValue(true);

            textStartPauseButton.setValue("Start");

            if (timeLeftInMillis < 1000) {
                //startPauseButton.setVisibility(View.INVISIBLE);
                visibilityStartPauseButton.setValue(false);

            } else {
                //startPauseButton.setVisibility(View.VISIBLE);
                visibilityStartPauseButton.setValue(true);
            }

            if (timeLeftInMillis < pomodoroTimeInMillis || timeLeftInMillis < breakTimeInMillis) {
                //resetButton.setVisibility(View.VISIBLE);
                visibilityResetButton.setValue(true);
            } else {
                //resetButton.setVisibility(View.INVISIBLE);
                visibilityResetButton.setValue(false);
            }
        }
    }

    public boolean getIsPomodoro(){
        return isPomodoro;
    }

    public boolean getIsIstimerRunning(){
        return istimerRunning;
    }

}

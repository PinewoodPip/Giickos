package edu.ub.pis.giickos.ui.section.timer;


import android.os.CountDownTimer;


import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import java.util.List;
import java.util.Locale;
import java.util.Set;


import edu.ub.pis.giickos.model.ModelHolder;

import edu.ub.pis.giickos.model.observer.ObservableEvent;
import edu.ub.pis.giickos.model.observer.Observer;

import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.ui.ViewModelHelpers;
import edu.ub.pis.giickos.ui.utils.notification.Notification;


public class ViewModel extends androidx.lifecycle.ViewModel{

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


    private ProjectManager model;

    private MutableLiveData<List<ViewModelHelpers.TaskData>> tasks;
    private MutableLiveData<ViewModelHelpers.TaskData> selectedTask;

    private Observer modelTasksObserver;




    public ViewModel() {
        timer = new MutableLiveData<String>();
        textStartPauseButton = new MutableLiveData<String>();
        textTimerMode = new MutableLiveData<String>();


        visibilityEditMinuteEdittext = new MutableLiveData<Boolean>(true);
        visibilityEditBreakMinuteEdittext = new MutableLiveData<>(true);
        visibilitySetPomodoroButton = new MutableLiveData<>(true);
        visibilitySetBreakButton = new MutableLiveData<>(true);
        visibilityResetButton = new MutableLiveData<>(true);
        visibilityStartPauseButton = new MutableLiveData<>(true);

        this.model = ModelHolder.INSTANCE.getProjectManager();

        this.tasks = new MutableLiveData<>(new ArrayList<>());
        updateTask();
        this.selectedTask = new MutableLiveData<>(null);

        // Listen for tasks being updated in the model
        this.modelTasksObserver = new Observer() {
            @Override
            public void update(ObservableEvent eventData) {
                updateTask();
            }
        };
        model.subscribe(ProjectManager.Events.TASKS_UPDATED, this.modelTasksObserver);
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

    public void startTimer() {
        endTime = System.currentTimeMillis() + timeLeftInMillis;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();

                ViewModelHelpers.TaskData task = selectedTask.getValue();
                if (task != null) {
                    model.addTimeSpentToTask(task.id, 1);
                }
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

                syncTask();

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

        syncTask();
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

            visibilityEditMinuteEdittext.setValue(false);
            visibilityEditBreakMinuteEdittext.setValue(false);
            visibilitySetPomodoroButton.setValue(false);
            visibilitySetBreakButton.setValue(false);
            visibilityResetButton.setValue(false);

            textStartPauseButton.setValue("Pause");
        }
        else {
            visibilityEditMinuteEdittext.setValue(true);
            visibilityEditBreakMinuteEdittext.setValue(true);
            visibilitySetPomodoroButton.setValue(true);
            visibilitySetBreakButton.setValue(true);
            textStartPauseButton.setValue("Start");

            if (timeLeftInMillis < 1000) {
                visibilityStartPauseButton.setValue(false);

            } else {
                visibilityStartPauseButton.setValue(true);
            }

            if (timeLeftInMillis < pomodoroTimeInMillis || timeLeftInMillis < breakTimeInMillis) {
                visibilityResetButton.setValue(true);
            } else {
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

    public void updateTask() {
        Set<Task> tasks = model.getTasks();
        List<ViewModelHelpers.TaskData> taskData = ViewModelHelpers.sortTasks(tasks);

        this.tasks.setValue(taskData);
    }

    public LiveData<List<ViewModelHelpers.TaskData>> getTasks() {
        return tasks;
    }

    /*
    public List<ViewModelHelpers.TaskData> getTasksById(String projectGUID) {
        Set<Task> tasks = model.getTasks(projectGUID);

        return ViewModelHelpers.sortTasks(tasks);
    }
     */

    public LiveData<ViewModelHelpers.TaskData> getSelectedTask() {
        return selectedTask;
    }

    // Syncs selected task to DB
    public void syncTask() {
        ViewModelHelpers.TaskData selectedTask = this.selectedTask.getValue();
        if (selectedTask != null) {
            model.markTaskAsDirty(selectedTask.id);
        }
    }

    public void selectTask(@Nullable ViewModelHelpers.TaskData task) {
        syncTask();
        selectedTask.setValue(task);

        if (task != null) {
            // Set timer duration from task duration
            if (task.durationInMinutes > 0) {
                long millis = task.durationInMinutes * 60000L;

                setTime(millis);
            }
        }
    }
}

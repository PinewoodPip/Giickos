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


public class ViewModel extends androidx.lifecycle.ViewModel{

    public CountDownTimer countDownTimer;
    public boolean istimerRunning;
    public boolean isPomodoro = true;

    // Defaults to 1 minute
    public long pomodoroTimeInMillis = 60000;
    public long breakTimeInMillis = 60000;
    public long timerInMillis = 60000;
    public long timeLeftInMillis = 60000;

    public MutableLiveData<String> timer,textStartPauseButton, textTimerMode;
    public MutableLiveData<Long> textPomodoroPicker, textBreakPicker;
    public MutableLiveData<Integer> oldTaskIndex, newTaskIndex;
    public MutableLiveData<Boolean> visibilityResetButton, visibilityStartPauseButton,
            visibilitySelectTaskSpinner, visibilityDetoxCheckBox, isTaskSelected;

    private ProjectManager model;

    private MutableLiveData<List<ViewModelHelpers.TaskData>> tasks;
    private MutableLiveData<ViewModelHelpers.TaskData> selectedTask;
    private MutableLiveData<Boolean> editMode;

    private Observer modelTasksObserver;

    public ViewModel() {
        timer = new MutableLiveData<String>();
        textStartPauseButton = new MutableLiveData<String>();
        textTimerMode = new MutableLiveData<String>();
        textPomodoroPicker = new MutableLiveData<Long>();
        textBreakPicker = new MutableLiveData<Long>();
        oldTaskIndex = new MutableLiveData<>();
        newTaskIndex = new MutableLiveData<>();

        visibilityResetButton = new MutableLiveData<>(true);
        visibilityStartPauseButton = new MutableLiveData<>(true);
        visibilitySelectTaskSpinner = new MutableLiveData<>(true);
        visibilityDetoxCheckBox = new MutableLiveData<>(true);
        isTaskSelected = new MutableLiveData<>(false);
        editMode = new MutableLiveData<>(false);

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

    public LiveData<Boolean> getVisibilityResetButton() {return visibilityResetButton;}
    public LiveData<Boolean> getVisibilityStartPauseButton() {return visibilityStartPauseButton;}
    public LiveData<Boolean> getVisibilitySelectTaskSpinner() {return visibilitySelectTaskSpinner;}
    public LiveData<Boolean> getVisibilityDetoxCheckBox() {return visibilityDetoxCheckBox;}
    public LiveData<Boolean> getIsTaskSelected() {return isTaskSelected;}

    public LiveData<Boolean> getEditMode() {return editMode;}

    public void startTimer() {
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
                if (isPomodoro) {
                    timeLeftInMillis = breakTimeInMillis;
                    setTime(timeLeftInMillis);
                    updateCountDownText();
                    textTimerMode.setValue("Break");
                    isPomodoro = false;
                }
                else {
                    timeLeftInMillis = pomodoroTimeInMillis;
                    setTime(timeLeftInMillis);
                    updateCountDownText();
                    textTimerMode.setValue("Pomodoro");
                    isPomodoro = true;
                }

                syncTask();
            }
        }.start();

        istimerRunning = true;
        updateWatchInterface();
        editMode.setValue(false); // Toggle editing off
    }

    public void setTime(long milliseconds) {
        timerInMillis = milliseconds;
        if (getIsPomodoro()){
            pomodoroTimeInMillis = timerInMillis;
            textPomodoroPicker.setValue((timeLeftInMillis / 60000));
        }else{
            breakTimeInMillis = timerInMillis;
            textBreakPicker.setValue((timeLeftInMillis / 60000));
        }
        resetTimer();
    }

    public long getMillisLeft() {
        return timeLeftInMillis;
    }

    public long getStartingMillis() {
        return isPomodoro ? pomodoroTimeInMillis : breakTimeInMillis;
    }

    public int getPomodoroTime() {
        return (int)(pomodoroTimeInMillis / 60000);
    }

    public int getBreakTime() {
        return (int)(breakTimeInMillis / 60000);
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
        timer.setValue(getTimerLabel());
    }

    public String getTimerLabel() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;

        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        }
        else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        return timeLeftFormatted;
    }

    public void updateWatchInterface() {
        if (istimerRunning) {
            visibilityResetButton.setValue(false);
            visibilitySelectTaskSpinner.setValue(false);
            visibilityDetoxCheckBox.setValue(false);
            visibilityResetButton.setValue(false);

            textStartPauseButton.setValue("Pause");
        }
        else {
            visibilitySelectTaskSpinner.setValue(true);
            visibilityDetoxCheckBox.setValue(true);
            visibilityResetButton.setValue(true);
            textStartPauseButton.setValue("Start");

            if (timeLeftInMillis < 1000) {
                visibilityStartPauseButton.setValue(false);

            } else {
                visibilityStartPauseButton.setValue(true);
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
                isTaskSelected.setValue(true);
            }else{
                setTime(0);
            }
        }else{
            isTaskSelected.setValue(false);

        }
    }

    public void toggleEditMode() {
        editMode.setValue(!editMode.getValue());
    }
}

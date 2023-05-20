package edu.ub.pis.giickos.ui.section.miscellaneous.statistics;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.ModelHolder;
import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.statistics.Statistic;
import edu.ub.pis.giickos.model.statistics.StatisticsManager;

public class ViewModel extends androidx.lifecycle.ViewModel {

    public enum STATISTIC {
        TASKS_CREATED(R.string.statistics_tasks_created, R.drawable.projects, ProjectManager.STAT_TASKS_CREATED),
        TASKS_COMPLETED(R.string.statistics_tasks_completed, R.drawable.projects, ProjectManager.STAT_TASKS_COMPLETED),

        TIMER_TIME(R.string.statistics_timer_time, R.drawable.timer, ProjectManager.STAT_TIMER_TIMESPENT),
//        TIMER_DETOXTIME(R.string.statistics_timer_detoxtime, R.drawable.timer),
//
//        BAMBOO_PLANTED(R.string.statistics_bamboo_planted, R.drawable.bamboo),
//        BAMBOO_COLLECTED(R.string.statistics_bamboo_collected, R.drawable.bamboo),
        ;

        public final int nameStringResource;
        public final int iconDrawableResource;
        public final String stringID;

        STATISTIC(int nameStringResource, int iconDrawableResource, String stringID) {
            this.nameStringResource = nameStringResource;
            this.iconDrawableResource = iconDrawableResource;
            this.stringID = stringID;
        }
    }

    private MutableLiveData<LocalDate> startDate;
    private MutableLiveData<LocalDate> endDate;

    private StatisticsManager manager;

    public ViewModel() {
        this.manager = ModelHolder.INSTANCE.getStatisticsManager();

        this.startDate = new MutableLiveData<>(LocalDate.now());
        this.endDate = new MutableLiveData<>(LocalDate.now());
    }

    public Map<STATISTIC, String> getStatistics() {
        Set<Statistic> stats = manager.getStatistics(startDate.getValue(), endDate.getValue());
        Map<STATISTIC, String> statValues = new HashMap<>();

        // TODO map this better
        for (Statistic stat : stats) {
            for (STATISTIC statID : STATISTIC.values()) {
                if (statID.stringID.equals(stat.getId())) {
                    String label = null;

                    switch (statID) {
                        case TASKS_CREATED:
                        case TASKS_COMPLETED: {
                            label = String.format("%.0f", stat.getValue());
                            break;
                        }
                        case TIMER_TIME: {
                            Date date = new Date();
                            date.setMinutes((int) (stat.getValue() / 60));
                            date.setSeconds((int) (stat.getValue() % 60));
                            label = new SimpleDateFormat("mm:ss").format(date);
                            break;
                        }
                        default: {
                            Log.w("TODO", "Stat label formatting not implemented: " + statID);
                        }
                    }

                    if (label != null) {
                        statValues.put(statID, label);
                    }

                    break;
                }
            }
        }

        return statValues;
    }

    public LiveData<LocalDate> getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.setValue(startDate);
    }

    public LiveData<LocalDate> getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate.setValue(endDate);
    }

    public void setToToday() {
        this.startDate.setValue(LocalDate.now());
        this.endDate.setValue(LocalDate.now());
    }
}

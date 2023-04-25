package edu.ub.pis.giickos.model.project;

import java.time.LocalDateTime;

import edu.ub.pis.giickos.Utils;
import edu.ub.pis.giickos.model.project.ProjectElement;

public class Task extends ProjectElement {

    public enum PRIORITY {
        NONE,
        LOW,
        MEDIUM,
        HIGH,
    }

    public enum REPEAT_MODE {
        NONE,
        DAILY,
        WEEKLY,
    }

    private PRIORITY priority = PRIORITY.NONE;
    private REPEAT_MODE repeatMode = REPEAT_MODE.NONE;
    private String description = "";
    private boolean isCompleted = false;
    private String projectID;

    private long startTime; // In millis since epoch
    private int durationInMinutes;
    private boolean takesAllDay; // If true, startTime indicates the day of the task only and its hour+minute should be disregarded

    public Task(String id, String name) // Kamil: I'm not a fan of bloated constructor params, so try to delegate initialization to setters instead (ex. how it's done with priority) with the exception of essential fields like name/id.
    {
        super(id, name);
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public PRIORITY getPriority() {
        return priority;
    }

    public void setPriority(PRIORITY priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public REPEAT_MODE getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(REPEAT_MODE repeatMode) {
        this.repeatMode = repeatMode;
    }

    public LocalDateTime getStartTime() {
        return Utils.instantToLocalDateTime(startTime);
    }

    public long getStartTimeMillis() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return durationInMinutes;
    }

    public void setDuration(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public boolean takesAllDay() {
        return takesAllDay;
    }

    public void setTakesAllDay(boolean takesAllDay) {
        this.takesAllDay = takesAllDay;
    }

    /*
     * Methods to fulfill the next functions:
     *
     * Start from x time
     * End at x time
     * Notify x time before start
     * Repeatable (default - no repeat, custom, select repeatable days)
     * Description
     * Add labels
     * Add members
     * Task priority level (("no pritority?"), low priority, medium priority, high pritority)
     * Task status (not completed, completed)
     * */


}

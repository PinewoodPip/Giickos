package edu.ub.pis.giickos.model.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import edu.ub.pis.giickos.Utils;

public class Task {

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

    private String id;
    private String name;
    private PRIORITY priority = PRIORITY.NONE;
    private REPEAT_MODE repeatMode = REPEAT_MODE.NONE;
    private String description = "";
    private Set<String> completionDates; // Dates this task was completed on. Recurring tasks may be completed on multiple different days.
    private String projectID;

    private long startTime; // In millis since epoch
    private int durationInMinutes;
    private boolean takesAllDay; // If true, startTime indicates the day of the task only and its hour+minute should be disregarded

    public Task(String id, String name) // Kamil: I'm not a fan of bloated constructor params, so try to delegate initialization to setters instead (ex. how it's done with priority) with the exception of essential fields like name/id.
    {
        this.id = id;
        this.name = name;
        this.completionDates = new HashSet<>();
    }

    public String getID() {
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
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

    public boolean isCompletedOnDay(LocalDate day) {
        return completionDates.contains(getDateID(day));
    }

    public void setCompletedOnDay(String dateID, boolean completed) {
        if (completed) {
            completionDates.add(dateID);
        }
        else {
            completionDates.remove(dateID);
        }
    }

    public void setCompletedOnDay(LocalDate day, boolean completed) {
        String dateID = getDateID(day);

        setCompletedOnDay(dateID, completed);
    }

    public Set<String> getCompletedDates() {
        return completionDates;
    }

    private String getDateID(LocalDate day) {
        String dateID = String.format(Locale.getDefault(), "%d/%d/%d", day.getDayOfMonth(), day.getMonthValue(), day.getYear());

        return dateID;
    }
}

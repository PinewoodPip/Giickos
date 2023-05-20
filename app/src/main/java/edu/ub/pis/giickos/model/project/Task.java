package edu.ub.pis.giickos.model.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
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
    private Map<String, Long> timeSpent; // Time spent working on the task on a given day (as string key), in seconds.
    private String projectID;

    private long creationTime;
    private long startTime; // In millis since epoch
    private int durationInMinutes;
    private boolean takesAllDay; // If true, startTime indicates the day of the task only and its hour+minute should be disregarded

    public Task(String id, String name) // Kamil: I'm not a fan of bloated constructor params, so try to delegate initialization to setters instead (ex. how it's done with priority) with the exception of essential fields like name/id.
    {
        this.id = id;
        this.name = name;
        this.completionDates = new HashSet<>();
        this.timeSpent = new HashMap<>();
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

    public LocalDateTime getCreationTime() {
        return Utils.instantToLocalDateTime(creationTime);
    }

    public long getCreationTimeMillis() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public boolean hasStartDateSet() {
        return this.startTime != -1;
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

    public boolean isCompletedOnDay(LocalDate date) {
        boolean completed = false;

        switch (repeatMode) {
            case WEEKLY: {
                LocalDateTime dateWithTime = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0);
                int dateYear = date.getYear();
                int dateWeek = Utils.localDateTimeToUTC(dateWithTime).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                for (String dateID : getCompletedDates()) {
                    LocalDate completionLocalDate = getCompletionDate(dateID);
                    int week = completionLocalDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

                    if (completionLocalDate.getYear() == dateYear && week == dateWeek) {
                        completed = true;
                        break;
                    }
                }
                break;
            }
            case DAILY:
            default: {
                completed = completionDates.contains(getDateID(date));
            }
        }

        return completed;
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

    public Set<LocalDate> getCompletedDates(LocalDate startDate, LocalDate endDate) {
        Set<LocalDate> dates = new HashSet<>();

        for (String dateID : getCompletedDates()) {
            LocalDate date = getCompletionDate(dateID);

            // Range is inclusive
            if ((date.isAfter(startDate) || date.isEqual(startDate)) && (date.isBefore(endDate) || date.isEqual(endDate))) {
                dates.add(date);
            }
        }

        return dates;
    }

    public Map<String, Long> getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(LocalDate day, long time) {
        setTimeSpent(getDateID(day), time);
    }

    public void setTimeSpent(String day, long time) {
        timeSpent.put(day, time);
    }

    public void addTimeSpent(LocalDate day, long time) {
        long existingTime = timeSpent.getOrDefault(getDateID(day), 0L);

        setTimeSpent(day, existingTime + time);
    }

    private String getDateID(LocalDate day) {
        String dateID = String.format(Locale.getDefault(), "%d/%d/%d", day.getDayOfMonth(), day.getMonthValue(), day.getYear());

        return dateID;
    }

    // tech debt be hitting hard here
    private LocalDate getCompletionDate(String dateID) {
        String[] values = dateID.split("/");

        return LocalDate.of(Integer.parseInt(values[2]), Integer.parseInt(values[1]), Integer.parseInt(values[0]));
    }
}

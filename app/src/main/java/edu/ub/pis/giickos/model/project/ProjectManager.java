package edu.ub.pis.giickos.model.project;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import edu.ub.pis.giickos.model.observer.EmptyEvent;
import edu.ub.pis.giickos.model.observer.Observable;
import edu.ub.pis.giickos.model.observer.ObservableEvent;
import edu.ub.pis.giickos.model.observer.Observer;
import edu.ub.pis.giickos.model.statistics.Statistic;
import edu.ub.pis.giickos.model.statistics.StatisticsProvider;
import edu.ub.pis.giickos.model.user.UserManager;
import edu.ub.pis.giickos.resources.dao.ProjectDAO;

public class ProjectManager extends Observable<ProjectManager.Events> implements StatisticsProvider {

    // Stat IDs for StatisticsProvider
    public static final String STAT_TASKS_CREATED = "PROJECTMANAGER_TASKS_CREATED";
    public static final String STAT_TASKS_COMPLETED = "PROJECTMANAGER_TASKS_COMPLETED";

    public enum Events {
        PROJECTS_UPDATED,
        TASKS_UPDATED,
        ;
    }

    private ProjectDAO daoProject;

    public ProjectManager(ProjectDAO daoProject, UserManager userManager) {
        this.daoProject = daoProject;

        // Reload user data when login changes
        userManager.subscribe(UserManager.Events.LOGGED_IN, new Observer() {
            @Override
            public void update(ObservableEvent eventData) {
                daoProject.loadDataForUser(userManager.getLoggedInUser(), new ProjectDAO.DataLoadedListener() {
                    @Override
                    public void onLoad(boolean success) {
                        // TODO decide how to handle failure
                        notifyProjectsUpdated();
                        notifyTasksUpdated();
                    }
                });
            }
        });
    }

    public Set<Project> getProjects() {
        return daoProject.getProjects();
    }

    public Project getProject(String id) {
        return daoProject.getProject(id);
    }

    public Set<Task> getProjectTasks(String projectGUID) {
        return daoProject.getTasks(projectGUID);
    }

    public Set<Task> getTasks(String projectGUID) {
        return daoProject.getTasks(projectGUID);
    }

    // Returns all tasks across all projects.
    public Set<Task> getTasks() {
        Set<Task> tasks = new HashSet<>();

        for (Project project : getProjects()) {
            tasks.addAll(getTasks(project.getId()));
        }

        return tasks;
    }

    public Task getTask(String taskGUID) {
        return daoProject.getTask(taskGUID);
    }

    public boolean addTask(String projectGUID, Task task) {
        boolean success = daoProject.addTask(projectGUID, task);

        if (success) {
            notifyTasksUpdated();
        }

        return success;
    }

    public boolean updateTask(Task task) {
        boolean success = daoProject.updateTask(task);

        if (success) {
            notifyTasksUpdated();
        }

        return success;
    }

    public Project getTaskProject(String taskID) {
        return daoProject.getTaskProject(taskID);
    }

    public boolean updateProject(Project project) {
        boolean success = daoProject.updateProject(project);

        if (success) {
            notifyProjectsUpdated();
        }

        return success;
    }

    public boolean deleteTask(String taskID) {
        boolean success = daoProject.deleteTask(taskID);

        if (success) {
            notifyTasksUpdated();
        }

        return success;
    }

    public boolean createProject(String name) {
        boolean success = false;

        // Cannot create projects with blank name
        if (!name.isEmpty()) {
            Project project = new Project(UUID.randomUUID(), name);

            success = daoProject.addProject(project);
        }

        if (success) {
            notifyProjectsUpdated();
        }

        return success;
    }

    public boolean deleteProject(String projectID) {
        boolean success = daoProject.deleteProject(projectID);

        if (success) {
            notifyTasksUpdated();
        }

        return success;
    }

    // Returns all tasks that occur on a day, regardless of project. Accepts an optional predicate for custom filtering.
    public Set<Task> getTasksForDay(LocalDateTime day, @Nullable TaskPredicate predicate) {
        Set<Task> allTasks = daoProject.getTasks();
        Set<Task> tasks = new HashSet<>();

        for (Task task : allTasks) {
            // Check if date matches
            if (isTaskValidForDay(task, day)) {
                // Check predicate, if any
                if (predicate == null || predicate.isValid(task)) {
                    tasks.add(task);
                }
            }
        }

        return tasks;
    }

    // Returns whether a task occurs on a given day. Considers recurring tasks.
    private boolean isTaskValidForDay(Task task, LocalDateTime day) {
        LocalDateTime taskTime = task.getStartTime();
        boolean isValid;

        switch (task.getRepeatMode()) {
            case DAILY: { // Daily tasks always pass this check
                isValid = true;
                break;
            }
            case WEEKLY: { // Day of week must match
                isValid = taskTime.getDayOfWeek() == day.getDayOfWeek();
                break;
            }
            default: { // Day of year and year must match
                isValid = taskTime.getDayOfYear() == day.getDayOfYear() && taskTime.getYear() == day.getYear();
                break;
            }
        }

        // Tasks are only valid after their start date (if they have one set)
        if (task.hasStartDateSet()) {
            LocalDateTime startTime = task.getStartTime();
            
            isValid = isValid && startTime.getYear() <= day.getYear() && startTime.getDayOfYear() <= day.getDayOfYear();
        }

        return isValid;
    }

    public boolean isTaskCompleted(String taskID, LocalDate day) {
        Task task = getTask(taskID);
        boolean completed = task != null && task.isCompletedOnDay(day);

        return completed;
    }

    public Set<Task> getTasksForDay(LocalDateTime day) {
        return getTasksForDay(day, null);
    }

    @Override
    public Set<Statistic> getStatistics(LocalDate startDate, LocalDate endDate) {
        Set<Statistic> stats = new HashSet<>();

        // "Tasks completed" stat
        int tasksCompleted = 0;
        for (Task task : getTasks()) {
            tasksCompleted += task.getCompletedDates(startDate, endDate).size();
        }
        stats.add(new Statistic(STAT_TASKS_COMPLETED, tasksCompleted));

        // "Tasks created" stat
        stats.add(new Statistic(STAT_TASKS_CREATED, getTasksCreatedCount(startDate, endDate)));

        return stats;
    }

    public int getTasksCreatedCount(LocalDate startDate, LocalDate endDate) {
        int count = 0;
        LocalDateTime startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth(), 0, 0);

        // Makes the "before or on the same day" comparison easier
        startDateTime = startDateTime.minusDays(1);
        endDateTime = endDateTime.plusDays(1);

        for (Task task : getTasks()) {
            LocalDateTime creationTime = task.getCreationTime();
            if (creationTime.isBefore(endDateTime) && creationTime.isAfter(startDateTime)) {
                count++;
            }
        }

        return count;
    }

    // TODO move elsewhere
    public LocalDate getCurrentTime() {
        return LocalDate.now();
    }

    private void notifyProjectsUpdated() {
        notifyObservers(Events.PROJECTS_UPDATED, new EmptyEvent(this, Events.PROJECTS_UPDATED));
    }

    private void notifyTasksUpdated() {
        notifyObservers(Events.TASKS_UPDATED, new EmptyEvent(this, Events.TASKS_UPDATED));
    }

    // Predicate class for methods that allow filtering task lists.
    public static abstract class TaskPredicate {
        public abstract boolean isValid(Task task);
    }
}

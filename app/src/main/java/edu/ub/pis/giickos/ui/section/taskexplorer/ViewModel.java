package edu.ub.pis.giickos.ui.section.taskexplorer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.ub.pis.giickos.model.ModelHolder;
import edu.ub.pis.giickos.model.project.ProjectManager;
import edu.ub.pis.giickos.model.observer.ObservableEvent;
import edu.ub.pis.giickos.model.observer.Observer;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.model.project.Project;
import edu.ub.pis.giickos.ui.ViewModelHelpers;
import edu.ub.pis.giickos.ui.ViewModelHelpers.*;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private ProjectManager model;

    private MutableLiveData<List<ProjectData>> projects;
    private Set<String> openedProjects; // Set of IDs of projects that are open (showing their tasks) in the view
    private Observer modelProjectObserver;
    private Observer modelTasksObserver;

    public ViewModel() {
        this.model = ModelHolder.INSTANCE.getProjectManager();
        this.openedProjects = new HashSet<>();

        this.projects = new MutableLiveData<>(new ArrayList<>());
        updateProjects();

        // Listen for projects being updated in the model
        this.modelProjectObserver = new Observer<ProjectManager.Events>() {
            @Override
            public void update(ObservableEvent<ProjectManager.Events> eventData) {
                updateProjects();
            }
        };
        model.subscribe(ProjectManager.Events.PROJECTS_UPDATED, this.modelProjectObserver);

        // Listen for tasks being updated in the model
        this.modelTasksObserver = new Observer() {
            @Override
            public void update(ObservableEvent eventData) {
                updateProjects();
            }
        };
        model.subscribe(ProjectManager.Events.TASKS_UPDATED, this.modelTasksObserver);
    }

    @Override
    public void onCleared() {
        // Unsubscribe listeners to prevent memory leaks
        model.unsubscribe(ProjectManager.Events.PROJECTS_UPDATED, modelProjectObserver);
        model.unsubscribe(ProjectManager.Events.TASKS_UPDATED, modelTasksObserver);
    }

    public boolean createProject(String name) {
        return model.createProject(name);
    }

    public LiveData<List<ProjectData>> getProjects() {
        return projects;
    }

    public List<TaskData> getTasks(String projectGUID) {
        Set<Task> tasks = model.getTasks(projectGUID);

        return ViewModelHelpers.sortTasks(tasks);
    }

    public ProjectData getProject(String id) {
        ProjectData data = null;
        Project project = model.getProject(id);

        if (project != null) {
            data = new ProjectData(project.getId(), project.getName());
        }

        return data;
    }

    public boolean isProjectOpen(String id) {
        return openedProjects.contains(id);
    }

    public void setProjectOpen(String id, boolean open) {
        if (open) {
            openedProjects.add(id);
        }
        else {
            openedProjects.remove(id);
        }
    }

    public void updateProjects() {
        Set<Project> projects = model.getProjects();
        List<ProjectData> projectData = ViewModelHelpers.sortProjects(projects);

        this.projects.setValue(projectData);
    }

    public boolean deleteProject(String projectID) {
        return model.deleteProject(projectID);
    }

    public boolean isTaskCompleted(String taskID) {
        return model.isTaskCompleted(taskID, LocalDate.now());
    }

    public TASK_PRIORITY getTaskPriority(String taskID) {
        Task task = model.getTask(taskID);
        TASK_PRIORITY priority = TASK_PRIORITY.NONE;

        if (task != null) {
            priority = TASK_PRIORITY.values()[task.getPriority().ordinal()];
        }

        return priority;
    }
}

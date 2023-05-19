package edu.ub.pis.giickos.resources.dao.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ub.pis.giickos.model.project.Project;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.model.user.User;
import edu.ub.pis.giickos.resources.dao.CachedProjectDAO;

public class ProjectDAO extends CachedProjectDAO {

    private String userEmail;
    private boolean dataLoaded = false;

    private FirebaseFirestore db;

    public ProjectDAO() {
        super();

        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public boolean addProject(Project project) {
        boolean success = super.addProject(project);

        // Write to firebase
        if (success && dataLoaded) {
            updateProjectEntry(project);
        }

        return success;
    }

    @Override
    public boolean updateProject(Project project) {
        boolean success = super.updateProject(project);

        if (success && dataLoaded) {
            updateProjectEntry(project);
        }

        return success;
    }

    private void updateProjectEntry(Project project) {
        DocumentReference ref = getProjectDocument(project.getId());
        Map<String, Object> entry = new HashMap<>();

        entry.put("name", project.getName());
        entry.put("tasks", new ArrayList<>(project.getTasks()));

        // TODO decide how to handle failure - need an event system
        ref.set(entry);
    }

    @Override
    public boolean deleteProject(String projectID) {
        boolean success = super.deleteProject(projectID);

        if (success) {
            DocumentReference ref = getProjectDocument(projectID);

            ref.delete();
        }

        return success;
    }

    @Override
    public boolean addTask(String projectGUID, Task task) {
        boolean success = super.addTask(projectGUID, task);

        // Write to firebase
        if (success && dataLoaded) {
            updateTaskEntry(task);
            updateProjectEntry(getProject(task.getProjectID()));
        }

        return success;
    }

    @Override
    public boolean deleteTask(String taskID) {
        String projectID = getTask(taskID).getProjectID();
        boolean success = super.deleteTask(taskID);

        if (success && dataLoaded) {
            DocumentReference ref = getTaskDocument(taskID);

            ref.delete();

            // Update project
            updateProjectEntry(getProject(projectID));
        }

        return success;
    }

    @Override
    public boolean updateTask(Task task) {
        String previousProjectID = getTask(task.getID()).getProjectID();
        boolean success = super.updateTask(task);

        if (success && dataLoaded) {
            updateTaskEntry(task);
            updateProjectEntry(getProject(task.getProjectID()));
            updateProjectEntry(getProject(previousProjectID));
        }

        return success;
    }

    private void updateTaskEntry(Task task) {
        DocumentReference ref = getTaskDocument(task.getID());
        Map<String, Object> entry = new HashMap<>();

        entry.put("description", task.getDescription());
        entry.put("duration_minutes", task.getDuration());
        entry.put("name", task.getName());
        entry.put("priority", task.getPriority().ordinal());
        entry.put("project_id", task.getProjectID());
        entry.put("repeat_mode", task.getRepeatMode().ordinal());
        entry.put("start_time", task.getStartTimeMillis());
        entry.put("takes_all_day", task.takesAllDay());
        entry.put("completion_dates", new ArrayList<>(task.getCompletedDates()));
        entry.put("creation_time", task.getCreationTimeMillis());

        // TODO decide how to handle failure - need an event system
        ref.set(entry);

        // Update project
        updateProject(getProject(task.getProjectID()));
    }

    @Override
    public void loadDataForUser(User user, DataLoadedListener listener) { // TODO change param to string
        userEmail = user.getEmail();
        dataLoaded = false;

        CollectionReference tasksRef = db.collection(String.format("userdata/%s/tasks", userEmail));
        CollectionReference projectsRef = db.collection(String.format("userdata/%s/projects", userEmail));

        projects.clear();
        tasks.clear();

        projectsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot projectDoc : queryDocumentSnapshots.getDocuments()) {
                    Map<String, Object> project = projectDoc.getData();
                    List<String> projectTasks = (List<String>) project.get("tasks");
                    String projectName = (String) project.get("name");

                    Project projectInstance = new Project(projectDoc.getId(), projectName);

                    for (String taskID : projectTasks) {
                        projectInstance.addElement(taskID);
                    }

                    addProject(projectInstance);
                }

                // Tasks must be loaded afterwards - TODO improve, as this is inefficient. Querying in parallel would be ideal.
                tasksRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot taskDoc : queryDocumentSnapshots.getDocuments()) {
                            Map<String, Object> task = taskDoc.getData();
                            Task taskInstance = new Task(taskDoc.getId(), (String) task.get("name"));

                            taskInstance.setDescription((String) task.get("description"));
                            taskInstance.setStartTime((Long) task.get("start_time"));
                            taskInstance.setRepeatMode(Task.REPEAT_MODE.values()[((Long) task.get("repeat_mode")).intValue()]);
                            taskInstance.setDuration(((Long) task.get("duration_minutes")).intValue());
                            taskInstance.setPriority(Task.PRIORITY.values()[((Long) task.get("priority")).intValue()]);
                            taskInstance.setProjectID((String) task.get("project_id"));
                            taskInstance.setTakesAllDay((Boolean) task.get("takes_all_day"));

                            List<String> completionDates = (List<String>) task.get("completion_dates");
                            if (completionDates != null) { // This field was added on 9/5/23
                                for (String dateID : completionDates) {
                                    taskInstance.setCompletedOnDay(dateID, true);
                                }
                            }

                            if (task.containsKey("creation_time")) { // This field was added on 19/5/23
                                taskInstance.setCreationTime(((Long) task.get("creation_time")));
                            }

                            addTask(taskInstance.getProjectID(), taskInstance);
                        }

                        dataLoaded = true;
                        listener.onLoad(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onLoad(false);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onLoad(false);
            }
        });
    }

    private DocumentReference getTaskDocument(String taskID) {
        return db.document(String.format("userdata/%s/tasks/%s", userEmail, taskID));
    }

    private DocumentReference getProjectDocument(String projectID) {
        return db.document(String.format("userdata/%s/projects/%s", userEmail, projectID));
    }
}

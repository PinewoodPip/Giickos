package edu.ub.pis.giickos.resources.dao.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ub.pis.giickos.model.project.Project;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.model.user.User;
import edu.ub.pis.giickos.resources.dao.CachedProjectDAO;

// TODO: updating database. Currently only reads, never writes.
public class ProjectDAO extends CachedProjectDAO {

    private String userEmail;
    private boolean dataLoaded = false;

    public ProjectDAO() {
        super();
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

    private void updateProjectEntry(Project project) { // TODO investigate bug with moving tasks between projects - merge is possibly the issue
        DocumentReference ref = getUserDocument();
        Map<String, Object> entry = new HashMap<>();
        Map<String, Object> projectEntry = new HashMap<>();
        Map<String, Object> projectsMap = new HashMap<>();

        projectEntry.put("name", project.getName());
        projectEntry.put("tasks", new ArrayList<>(project.getTasks()));
        projectsMap.put(project.getId(), projectEntry);
        entry.put("projects", projectsMap);

        // TODO decide how to handle failure - need an event system
        ref.set(entry, SetOptions.merge());
    }

    @Override
    public boolean deleteProject(String projectID) {
        boolean success = super.deleteProject(projectID);

        if (success) {
            DocumentReference ref = getUserDocument();

            // TODO decide how to handle failure - need an event system
            ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    // TODO use documents for these instead; this is inefficient
                    Map<String, Object> doc = documentSnapshot.getData();
                    Map<String, Object> projects = (Map<String, Object>) doc.get("projects");
                    projects.remove(projectID);

                    ref.update(doc);
                }
            });
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
            // Update project
            updateProjectEntry(getProject(projectID));

            DocumentReference ref = getUserDocument();

            // TODO decide how to handle failure - need an event system
            ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    // TODO use documents for these instead; this is inefficient
                    Map<String, Object> doc = documentSnapshot.getData();
                    Map<String, Object> tasks = (Map<String, Object>) doc.get("tasks");
                    tasks.remove(taskID);

                    ref.update(doc);
                }
            });
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
        DocumentReference ref = getUserDocument();
        Map<String, Object> entry = new HashMap<>();
        Map<String, Object> taskEntry = new HashMap<>();
        Map<String, Object> tasksMap = new HashMap<>();

        taskEntry.put("completed", task.getCompleted());
        taskEntry.put("description", task.getDescription());
        taskEntry.put("duration_minutes", task.getDuration());
        taskEntry.put("name", task.getName());
        taskEntry.put("priority", task.getPriority().ordinal());
        taskEntry.put("project_id", task.getProjectID());
        taskEntry.put("repeat_mode", task.getRepeatMode().ordinal());
        taskEntry.put("start_time", task.getStartTimeMillis());
        taskEntry.put("takes_all_day", task.takesAllDay());

        tasksMap.put(task.getID(), taskEntry);
        entry.put("tasks", tasksMap);

        // TODO decide how to handle failure - need an event system
        ref.set(entry, SetOptions.merge());

        // Update project
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> doc = documentSnapshot.getData();
                Map<String, Object> projects = (Map<String, Object>) doc.get("projects");
                Map<String, Object> project = (Map<String, Object>) projects.get(task.getProjectID());
                ArrayList<String> tasks = (ArrayList<String>) project.get("tasks");

                if (!tasks.contains(task.getID())) {
                    tasks.add(task.getID());
                }

                ref.set(doc, SetOptions.merge());
            }
        });
    }

    @Override
    public void loadDataForUser(User user, DataLoadedListener listener) { // TODO change param to string
        userEmail = user.getEmail();
        dataLoaded = false;

        DocumentReference ref = getUserDocument();

        projects.clear();
        tasks.clear();

        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> doc = documentSnapshot.getData();

                // Happens on registration - TODO handle this better
                if (doc == null || !doc.containsKey("projects")) {
                    listener.onLoad(true);
                    dataLoaded = true;
                    return;
                }

                Map<String, Object> projects = (Map<String, Object>) doc.get("projects");
                Map<String, Object> tasks = (Map<String, Object>) doc.get("tasks");

                // Load projects
                for (String guid : projects.keySet()) {
                    Map<String, Object> project = (Map<String, Object>) projects.get(guid);
                    List<String> projectTasks = (List<String>) project.get("tasks");
                    String projectName = (String) project.get("name");

                    Project projectInstance = new Project(guid, projectName);

                    for (String taskID : projectTasks) {
                        projectInstance.addElement(taskID);
                    }

                    addProject(projectInstance);
                }

                // Load tasks
                for (String taskID : tasks.keySet()) {
                    Map<String, Object> task = (Map<String, Object>) tasks.get(taskID);
                    Task taskInstance = new Task(taskID, (String) task.get("name"));

                    taskInstance.setDescription((String) task.get("description"));
                    taskInstance.setStartTime((Long) task.get("start_time"));
                    taskInstance.setRepeatMode(Task.REPEAT_MODE.values()[((Long) task.get("repeat_mode")).intValue()]);
                    taskInstance.setDuration(((Long) task.get("duration_minutes")).intValue());
                    taskInstance.setPriority(Task.PRIORITY.values()[((Long) task.get("priority")).intValue()]);
                    taskInstance.setProjectID((String) task.get("project_id"));
                    taskInstance.setTakesAllDay((Boolean) task.get("takes_all_day"));

                    addTask(taskInstance.getProjectID(), taskInstance);
                }

                listener.onLoad(true);
                dataLoaded = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("DAO", "Failed to load document");

                listener.onLoad(false);
            }
        });
    }

    private DocumentReference getUserDocument() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference ref = firestore.collection("userdata").document(userEmail);

        return ref;
    }
}

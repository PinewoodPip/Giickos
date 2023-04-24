package edu.ub.pis.giickos.resources.dao.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import edu.ub.pis.giickos.model.project.Project;
import edu.ub.pis.giickos.model.project.Task;
import edu.ub.pis.giickos.model.user.User;
import edu.ub.pis.giickos.resources.dao.CachedProjectDAO;

// TODO: updating database. Currently only reads, never writes.
public class ProjectDAO extends CachedProjectDAO {

    public ProjectDAO() {
        super();
    }

    @Override
    public void loadDataForUser(User user, DataLoadedListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference ref = firestore.collection("userdata").document(user.getEmail());

        projects.clear();
        tasks.clear();

        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> doc = documentSnapshot.getData();
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
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("DAO", "Failed to load document");

                listener.onLoad(false);
            }
        });
    }
}

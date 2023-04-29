package edu.ub.pis.giickos.ui.activities.register;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.ub.pis.giickos.model.ModelHolder;
import edu.ub.pis.giickos.model.user.UserManager;

public class ViewModel extends androidx.lifecycle.ViewModel {

    public enum CREDENTIAL_VALIDATION_RESULT {
        VALID,
        PASSWORD_MISMATCH,
        MISSING_FIELDS,
        ;
    }

    private UserManager userManager;
    private FirebaseAuth auth;

    private String email = "";
    private String username = "";
    private String password = "";
    private String passwordConfirmation = "";
    private boolean initialized = false;

    public ViewModel() {
        this.userManager = ModelHolder.INSTANCE.getUserManager();
        this.auth = FirebaseAuth.getInstance();
    }

    public CREDENTIAL_VALIDATION_RESULT validateCredentials() {
        CREDENTIAL_VALIDATION_RESULT result = CREDENTIAL_VALIDATION_RESULT.VALID;

        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
            result = CREDENTIAL_VALIDATION_RESULT.MISSING_FIELDS;
        }
        else if (!passwordConfirmation.equals(password)) {
            result = CREDENTIAL_VALIDATION_RESULT.PASSWORD_MISMATCH;
        }

        return result;
    }

    public Task createAccount() {
        Task task = auth.createUserWithEmailAndPassword(email, password);

        // Log-in on success.
        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();

                builder.setDisplayName(username);

                auth.getCurrentUser().updateProfile(builder.build());
                userManager.tryLogIn(email, password);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> doc = new HashMap<>();
                doc.put("projects", new HashMap<>());
                doc.put("tasks", new HashMap<>());
                db.collection("userdata").document(email).set(doc);
            }
        });

        return task;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}

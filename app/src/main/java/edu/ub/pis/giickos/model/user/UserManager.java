package edu.ub.pis.giickos.model.user;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.ub.pis.giickos.model.observer.EmptyEvent;
import edu.ub.pis.giickos.model.observer.Observable;

public class UserManager extends Observable<UserManager.Events> {

    public enum Events {
        LOGGED_IN,
        LOGIN_FAILED,
        ;
    }

    private User loggedInUser = null;
    private FirebaseAuth firebaseAuth;

    public UserManager() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    // Will throw LOGGED_IN or LOGIN_FAILED event in addition to returning a task.
    public Task tryLogIn(String email, String password) {
        Task task = firebaseAuth.signInWithEmailAndPassword(email, password);

        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    loggedInUser = new User(email, firebaseUser.getDisplayName(), User.SUBSCRIPTION_STATUS.NOT_SUBSCRIBED); // TODO username
                    // Subscription status is also TODO - though won't be implemented in deliverable 3

                    notifyObservers(Events.LOGGED_IN, new EmptyEvent(UserManager.this, Events.LOGGED_IN));
                }
                else {
                    notifyObservers(Events.LOGIN_FAILED, new EmptyEvent(UserManager.this, Events.LOGIN_FAILED));
                }
            }
        });

        return task;
    }

    public void logOut() {
        this.loggedInUser = null;
    }

    public void removeAccount()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete();
    }
}

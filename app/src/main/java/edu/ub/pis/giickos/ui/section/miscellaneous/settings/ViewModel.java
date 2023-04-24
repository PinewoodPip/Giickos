package edu.ub.pis.giickos.ui.section.miscellaneous.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.ub.pis.giickos.model.ModelHolder;
import edu.ub.pis.giickos.model.observer.ObservableEvent;
import edu.ub.pis.giickos.model.observer.Observer;
import edu.ub.pis.giickos.model.user.User;
import edu.ub.pis.giickos.model.user.UserManager;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private UserManager userManager;

    private MutableLiveData<UserData> loggedInUser;

    public ViewModel() {
        userManager = ModelHolder.INSTANCE.getUserManager();
        loggedInUser = new MutableLiveData<>(null);

        // Listen for log ins. This is necessary as the corresponding fragment might exist in the "background" while the user logs back in from the login activity.
        userManager.subscribe(UserManager.Events.LOGGED_IN, new Observer() {
            @Override
            public void update(ObservableEvent eventData) {
                updateLoggedInUser();
            }
        });

        updateLoggedInUser();
    }

    public void logOut() {
        userManager.logOut();
    }

    public LiveData<UserData> getLoggedInUser() {
        return loggedInUser;
    }

    // Updates the UserData of the logged in user from the model.
    private void updateLoggedInUser() {
        User user = userManager.getLoggedInUser();
        UserData userData = null;

        if (user != null) {
            userData = new UserData(user.getEmail(), user.getUsername());
        }

        loggedInUser.setValue(userData);
    }

    /*
        Auxiliary classes
    */
    public static class UserData {
        public final String email;
        public final String username;

        public UserData(String email, String username) {
            this.email = email;
            this.username = username;
        }
    }
}

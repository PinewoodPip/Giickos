package edu.ub.pis.giickos.ui.section.miscellaneous.settings;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.ModelHolder;
import edu.ub.pis.giickos.model.observer.ObservableEvent;
import edu.ub.pis.giickos.model.observer.Observer;
import edu.ub.pis.giickos.model.user.User;
import edu.ub.pis.giickos.model.user.UserManager;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private UserManager userManager;

    private MutableLiveData<UserData> loggedInUser;

    private Observer loggedInObserver;

    public ViewModel() {
        userManager = ModelHolder.INSTANCE.getUserManager();
        loggedInUser = new MutableLiveData<>(null);

        loggedInObserver = new Observer() {
            @Override
            public void update(ObservableEvent eventData) {
                updateLoggedInUser();
            }
        };

        // Listen for log-ins and update the LiveData for the logged in user. This is necessary as the corresponding fragment to this VM might exist in the "background" while the user logs back in from the login activity.
        userManager.subscribe(UserManager.Events.LOGGED_IN, loggedInObserver);

        updateLoggedInUser();
    }

    @Override
    public void onCleared() {
        // Unsubscribe listeners to prevent memory leaks
        userManager.unsubscribe(UserManager.Events.LOGGED_IN, loggedInObserver);
    }
    public boolean isGuest()
    {   //If the user is not logged in, it is a guest
        return !(userManager.isLoggedIn());
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
    public void removeAccount()
    {
        userManager.removeAccount();
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
    public Intent composeEmail(String[] emails, String subject)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this

        emailIntent.putExtra(Intent.EXTRA_EMAIL, emails);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        return emailIntent;
    }
    public Intent openBrowser(String url)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        return browserIntent;
    }



}

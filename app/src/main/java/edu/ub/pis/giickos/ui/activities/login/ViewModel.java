package edu.ub.pis.giickos.ui.activities.login;

import com.google.android.gms.tasks.Task;

import edu.ub.pis.giickos.model.ModelHolder;
import edu.ub.pis.giickos.model.user.UserManager;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private UserManager userManager;

    private String email = "";
    private String password = "";
    private boolean rememberLogin = false;

    public ViewModel() {
        userManager = ModelHolder.INSTANCE.getUserManager();
    }

    public Task tryLogIn() {
        return userManager.tryLogIn(email, password);
    }

    public boolean canLogIn() {
        return !email.isEmpty() && !password.isEmpty();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getRememberLogin() {
        return rememberLogin;
    }

    public void setRememberLogin(boolean rememberLogin) {
        this.rememberLogin = rememberLogin;
    }
}

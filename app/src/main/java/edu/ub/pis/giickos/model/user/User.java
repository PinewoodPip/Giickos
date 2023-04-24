package edu.ub.pis.giickos.model.user;

// Represents a user of the application.
// May be used to identify not only the logged in user, but also other users displayed throughout the application (ex. in teams)
public class User {
    private String email;
    private String username;

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}

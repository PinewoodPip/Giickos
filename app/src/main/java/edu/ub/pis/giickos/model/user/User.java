package edu.ub.pis.giickos.model.user;

// Represents a user of the application.
// May be used to identify not only the logged in user, but also other users displayed throughout the application (ex. in teams)
public class User {
    public enum SUBSCRIPTION_STATUS {
        NOT_SUBSCRIBED,
        SUBSCRIBED,
        ;
    }

    private String email;
    private String username;
    private SUBSCRIPTION_STATUS subscriptionStatus;

    public User(String email, String username, SUBSCRIPTION_STATUS subscriptionStatus) {
        this.email = email;
        this.username = username;
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public SUBSCRIPTION_STATUS getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(SUBSCRIPTION_STATUS subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
}

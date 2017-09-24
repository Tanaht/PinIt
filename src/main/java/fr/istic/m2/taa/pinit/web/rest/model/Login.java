package fr.istic.m2.taa.pinit.web.rest.model;

public class Login {

    private String login;
    private String passwordHashed;

    public Login(String login, String passwordHashed) {
        this.login = login;
        this.passwordHashed = passwordHashed;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHashed() {
        return passwordHashed;
    }

    public void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    @Override
    public String toString() {
        return "Login{" +
                "login='" + login + '\'' +
                ", passwordHashed='" + passwordHashed + '\'' +
                '}';
    }
}

package fr.istic.m2.taa.pinit.web.rest.model;

public class Login {

    private String login;
    private String password;

    public Login() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordHashed) {
        this.password = passwordHashed;
    }

    @Override
    public String toString() {
        return "Login{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

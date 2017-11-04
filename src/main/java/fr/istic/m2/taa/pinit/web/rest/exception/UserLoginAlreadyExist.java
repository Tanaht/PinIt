package fr.istic.m2.taa.pinit.web.rest.exception;

public class UserLoginAlreadyExist extends Exception{
    public UserLoginAlreadyExist(String login){
        super("User login "+login+" already exist");
    }
}

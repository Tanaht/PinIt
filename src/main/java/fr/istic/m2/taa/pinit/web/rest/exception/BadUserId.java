package fr.istic.m2.taa.pinit.web.rest.exception;

public class BadUserId extends Exception {
    public BadUserId(long id){
        super("User id "+id+" doesn't exist");
    }
}

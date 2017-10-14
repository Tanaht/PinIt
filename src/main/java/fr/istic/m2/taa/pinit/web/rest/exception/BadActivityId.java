package fr.istic.m2.taa.pinit.web.rest.exception;

public class BadActivityId extends Exception {
    public BadActivityId(long id){
        super("Activity id "+id+" doesn't exist");
    }
}

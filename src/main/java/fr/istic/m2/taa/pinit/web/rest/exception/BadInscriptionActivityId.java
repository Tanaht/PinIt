package fr.istic.m2.taa.pinit.web.rest.exception;

public class BadInscriptionActivityId extends Exception {
    public BadInscriptionActivityId(long id){
        super("inscriptionActivity id "+id+" doesn't exist");
    }
}

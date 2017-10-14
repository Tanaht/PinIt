package fr.istic.m2.taa.pinit.web.rest.model;

import fr.istic.m2.taa.pinit.domain.meteo.CoordGps;

public class InscriptionActivityRegister {

    private long userId;

    private long activityId;

    private CoordGps coordonne;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long actiivityId) {
        this.activityId = actiivityId;
    }

    public CoordGps getCoordonne() {
        return coordonne;
    }

    public void setCoordonne(CoordGps coordonne) {
        this.coordonne = coordonne;
    }

}

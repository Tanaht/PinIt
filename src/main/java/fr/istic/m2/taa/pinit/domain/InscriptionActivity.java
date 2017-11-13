package fr.istic.m2.taa.pinit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.istic.m2.taa.pinit.domain.meteo.CoordGps;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class InscriptionActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @OneToOne
    private User user;

    @OneToOne
    private Activity activity;

    @Embedded
    private CoordGps localisation;

    public InscriptionActivity(){

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public CoordGps getLocalisation() {
        return localisation;
    }

    public void setLocalisation(CoordGps localisation) {
        this.localisation = localisation;
    }
}

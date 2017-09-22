package fr.istic.m2.taa.pinit.domain;

import fr.istic.m2.taa.pinit.domain.meteo.CoordGps;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class InscriptionActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

    @OneToOne
    private Activity activity;

    @Embedded
    private CoordGps localisation;


    private Date dateDebut;
    private Date dateFin;

    public InscriptionActivity(){

    }

}

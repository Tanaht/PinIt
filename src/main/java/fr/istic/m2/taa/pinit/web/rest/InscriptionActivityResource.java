package fr.istic.m2.taa.pinit.web.rest;

import fr.istic.m2.taa.pinit.domain.InscriptionActivity;
import fr.istic.m2.taa.pinit.repository.ActivityRepository;
import fr.istic.m2.taa.pinit.repository.InscriptionActivityRepository;
import fr.istic.m2.taa.pinit.repository.UserRepository;
import fr.istic.m2.taa.pinit.service.ActivityService;
import fr.istic.m2.taa.pinit.service.InscriptionActivityService;
import fr.istic.m2.taa.pinit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InscriptionActivityResource {
    private final Logger log = LoggerFactory.getLogger(InscriptionActivityResource.class);

    private InscriptionActivityRepository inscriptionActivityRepository;
    private InscriptionActivityService inscriptionActivityService;

    private ActivityRepository activityRepository;
    private ActivityService activityService;

    private UserRepository userRepository;
    private UserService userService;


    public InscriptionActivityResource(InscriptionActivityRepository inscriptionActivityRepository, InscriptionActivityService inscriptionActivityService, ActivityRepository activityRepository, ActivityService activityService, UserRepository userRepository, UserService userService) {
        this.inscriptionActivityRepository = inscriptionActivityRepository;
        this.inscriptionActivityService = inscriptionActivityService;
        this.activityRepository = activityRepository;
        this.activityService = activityService;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @GetMapping("/inscriptions")
    @Secured("ROLE_USER")
    public List<InscriptionActivity> getAllInscriptionActivity(String userLogin){
        return inscriptionActivityRepository.findAllByUser_Login(userLogin);
    }

    @PostMapping("/inscriptions")
    @Secured("ROLE_USER")
    public void addInscriptionToUser(){

    }





}

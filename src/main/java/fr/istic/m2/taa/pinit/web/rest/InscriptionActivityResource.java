package fr.istic.m2.taa.pinit.web.rest;

import fr.istic.m2.taa.pinit.domain.Activity;
import fr.istic.m2.taa.pinit.domain.Authority;
import fr.istic.m2.taa.pinit.domain.InscriptionActivity;
import fr.istic.m2.taa.pinit.domain.User;
import fr.istic.m2.taa.pinit.repository.ActivityRepository;
import fr.istic.m2.taa.pinit.repository.InscriptionActivityRepository;
import fr.istic.m2.taa.pinit.repository.UserRepository;
import fr.istic.m2.taa.pinit.service.ActivityService;
import fr.istic.m2.taa.pinit.service.InscriptionActivityService;
import fr.istic.m2.taa.pinit.service.UserService;
import fr.istic.m2.taa.pinit.web.rest.exception.BadActivityId;
import fr.istic.m2.taa.pinit.web.rest.exception.BadUserId;
import fr.istic.m2.taa.pinit.web.rest.model.InscriptionActivityRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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


    @RequestMapping(value="/user/{userId}/inscriptions", method = RequestMethod.GET)
    @Secured(Authority.USER)
    public List<InscriptionActivity> getInscriptionActivitiesByID(@PathVariable("userId") long userId) throws BadUserId {

        Optional<User> potentialUser = userRepository.findUserById(userId);
        if (!potentialUser.isPresent()){
            throw new BadUserId(userId);
        }
        return inscriptionActivityRepository.findAllByUser_Id(userId);
    }

    @RequestMapping(value="/user/{userId}/inscriptions", method = RequestMethod.POST)
    //@Secured(Authority.USER)
    public ResponseEntity addInscriptionToUser(@Valid @RequestBody InscriptionActivityRegister ins) throws BadUserId, BadActivityId {
        Optional<User> potentialUser = userRepository.findUserById(ins.getUserId());

        if (!potentialUser.isPresent()){
            throw new BadUserId(ins.getUserId());
        }

        Optional<Activity> potentialActivity = activityRepository.findById(ins.getActivityId());

        if (!potentialActivity.isPresent()){
            throw new BadActivityId(ins.getActivityId());
        }

        InscriptionActivity inscriptionActivity = new InscriptionActivity();
        inscriptionActivity.setUser(potentialUser.get());
        inscriptionActivity.setActivity(potentialActivity.get());
        inscriptionActivity.setLocalisation(ins.getCoordonne());

        inscriptionActivityRepository.save(inscriptionActivity);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value="/inscription/{inscriptionId}", method = RequestMethod.DELETE)
    //@Secured(Authority.USER)
    public ResponseEntity removeInscriptionById(@PathVariable("inscriptionId") long inscriptionId) throws BadActivityId{

        Optional<Activity> potentialActivity = activityRepository.findById(inscriptionId);
        if (!potentialActivity.isPresent()){
            throw new BadActivityId(inscriptionId);
        }

        inscriptionActivityRepository.deleteById(inscriptionId);

        return ResponseEntity.ok().build();
    }


    @ExceptionHandler({BadUserId.class, BadActivityId.class})
    void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

}

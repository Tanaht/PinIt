package fr.istic.m2.taa.pinit.web.rest;

import fr.istic.m2.taa.pinit.domain.Activity;
import fr.istic.m2.taa.pinit.domain.Authority;
import fr.istic.m2.taa.pinit.domain.InscriptionActivity;
import fr.istic.m2.taa.pinit.domain.User;
import fr.istic.m2.taa.pinit.repository.ActivityRepository;
import fr.istic.m2.taa.pinit.repository.InscriptionActivityRepository;
import fr.istic.m2.taa.pinit.repository.UserRepository;
import fr.istic.m2.taa.pinit.service.SecurityUtilsService;
import fr.istic.m2.taa.pinit.web.rest.exception.BadActivityId;
import fr.istic.m2.taa.pinit.web.rest.exception.BadInscriptionActivityId;
import fr.istic.m2.taa.pinit.web.rest.exception.BadUserId;
import fr.istic.m2.taa.pinit.web.rest.exception.NotAuthorized;
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

    private ActivityRepository activityRepository;

    private UserRepository userRepository;

    private SecurityUtilsService securityUtilsService;


    public InscriptionActivityResource(InscriptionActivityRepository inscriptionActivityRepository, ActivityRepository activityRepository, UserRepository userRepository, SecurityUtilsService securityUtilsService) {
        this.inscriptionActivityRepository = inscriptionActivityRepository;

        this.activityRepository = activityRepository;

        this.userRepository = userRepository;

        this.securityUtilsService = securityUtilsService;
    }

    @RequestMapping(value="/users/{userId}/inscriptions", method = RequestMethod.GET)
    @Secured(Authority.USER)
    public List<InscriptionActivity> getInscriptionActivitiesByID(@PathVariable("userId") long userId) throws BadUserId, NotAuthorized {

        Optional<User> potentialUser = userRepository.findUserById(userId);
        if (!potentialUser.isPresent()){
            throw new BadUserId(userId);
        }

        long actualUser = securityUtilsService.getCurrentUserLoginId();
        if (actualUser != userId && !securityUtilsService.isCurrentUserInRole(Authority.ADMIN)){
            throw new NotAuthorized("User not authorized to get inscriptionActivity of another user");
        }
        return inscriptionActivityRepository.findAllByUser_Id(userId);
    }

    @RequestMapping(value="/users/{userId}/inscriptions", method = RequestMethod.POST)
    @Secured(Authority.USER)
    public InscriptionActivity addInscriptionToUser(@PathVariable("userId") long userId, @Valid @RequestBody InscriptionActivityRegister ins) throws BadUserId, BadActivityId, NotAuthorized {
        Optional<User> potentialUser = userRepository.findUserById(userId);

        //On vérifie la présence de l'utilisateur en base.
        if (!potentialUser.isPresent()){
            throw new BadUserId(userId);
        }

        //On vérifie que l'utilisateur qui accède à cette resource est légitime.
        long actualUser = securityUtilsService.getCurrentUserLoginId();
        if (actualUser != userId && !securityUtilsService.isCurrentUserInRole(Authority.ADMIN)){
            throw new NotAuthorized("User not authorized to edit inscriptionActivity of another user");
        }

        Optional<Activity> potentialActivity = activityRepository.findById(ins.getActivityId());

        // On vérifie que l'activité choisie existe.
        if (!potentialActivity.isPresent()){
            throw new BadActivityId(ins.getActivityId());
        }

        InscriptionActivity inscriptionActivity = new InscriptionActivity();
        inscriptionActivity.setUser(potentialUser.get());
        inscriptionActivity.setActivity(potentialActivity.get());
        inscriptionActivity.setLocalisation(ins.getCoordonne());

        inscriptionActivityRepository.save(inscriptionActivity);

        log.debug("InscriptionActivity ID: {}", inscriptionActivity.getId());
        return inscriptionActivity;
    }

    @RequestMapping(value="/inscriptions/{inscriptionId}", method = RequestMethod.DELETE)
    @Secured(Authority.USER)
    public ResponseEntity removeInscriptionById(@PathVariable("inscriptionId") long inscriptionId) throws BadActivityId{

        Optional<Activity> potentialActivity = activityRepository.findById(inscriptionId);
        if (!potentialActivity.isPresent()){
            throw new BadActivityId(inscriptionId);
        }

        //On vérifie que l'utilisateur qui accède à cette resource est légitime.
/*        long actualUser = securityUtilsService.getCurrentUserLoginId();
        if (actualUser != potentialActivity. && !securityUtilsService.isCurrentUserInRole(Authority.ADMIN)){
            throw new NotAuthorized("User not authorized to edit inscriptionActivity of another user");
        }*/


        inscriptionActivityRepository.deleteById(inscriptionId);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value="/inscriptions/{inscriptionId}", method = RequestMethod.PUT)
    @Secured(Authority.USER)
    public ResponseEntity editInscriptionActivity(@PathVariable("inscriptionId") long inscriptionId,@RequestBody InscriptionActivityRegister ins) throws BadActivityId, BadUserId, BadInscriptionActivityId {
        Optional<Activity> potentialActivity = activityRepository.findById(ins.getActivityId());
        if (!potentialActivity.isPresent()){
            throw new BadActivityId(ins.getActivityId());
        }
        Optional<InscriptionActivity> potentialInscriptionActivity = inscriptionActivityRepository.findById(inscriptionId);

        if (!potentialInscriptionActivity.isPresent()){
            throw new BadInscriptionActivityId(inscriptionId);
        }

        InscriptionActivity inscriptionActivity = potentialInscriptionActivity.get();
        inscriptionActivity.setActivity(potentialActivity.get());
        inscriptionActivity.setLocalisation(ins.getCoordonne());

        inscriptionActivityRepository.save(inscriptionActivity);

        return ResponseEntity.ok().build();
    }


    @ExceptionHandler({BadUserId.class, BadActivityId.class,BadInscriptionActivityId.class})
    void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

}

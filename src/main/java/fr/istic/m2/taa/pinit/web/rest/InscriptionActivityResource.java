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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Api(value="InscriptionActivityResource", description="Operation about user inscription in activities")
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

    @ApiOperation(value = "Return the list of all activities where the user is regiter")
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

    @ApiOperation(value = "Add an activity to an user")
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

    @ApiOperation(value = "Remove an activity from an user")
    @RequestMapping(value="/inscriptions/{inscriptionId}", method = RequestMethod.DELETE)
    @Secured(Authority.USER)
    public Map<String, String> removeInscriptionById(@PathVariable("inscriptionId") long inscriptionId) throws BadActivityId, BadInscriptionActivityId, NotAuthorized {

        Optional<InscriptionActivity> potentialInscription = inscriptionActivityRepository.findById(inscriptionId);

        if (!potentialInscription.isPresent()){
            throw new BadInscriptionActivityId(inscriptionId);
        }

        Optional<Activity> potentialActivity = activityRepository.findById(potentialInscription.get().getActivity().getId());
        if (!potentialActivity.isPresent()){
            throw new BadActivityId(inscriptionId);
        }


        //On vérifie que l'utilisateur qui accède à cette resource est légitime.
        long actualUser = securityUtilsService.getCurrentUserLoginId();
        if (actualUser != potentialInscription.get().getUser().getId() && !securityUtilsService.isCurrentUserInRole(Authority.ADMIN)){
            throw new NotAuthorized("User not authorized to edit inscriptionActivity of another user");
        }


        inscriptionActivityRepository.delete(potentialInscription.get());

        Map<String, String> response = new HashMap<String, String>();
        response.put("result", "InscriptionActivity has been deleted with success");

        return response;
    }

    @ApiOperation(value = "edit a user activity register")
    @RequestMapping(value="/inscriptions/{inscriptionId}", method = RequestMethod.PUT)
    @Secured(Authority.USER)
    public Map<String, String> editInscriptionActivity(@PathVariable("inscriptionId") long inscriptionId,@RequestBody InscriptionActivityRegister ins) throws BadActivityId, BadUserId, BadInscriptionActivityId {
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


        Map<String, String> response = new HashMap<String, String>();
        response.put("result", "InscriptionActivity has been updated with success");

        return response;
    }


    @ExceptionHandler({BadUserId.class, BadActivityId.class,BadInscriptionActivityId.class})
    void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

}

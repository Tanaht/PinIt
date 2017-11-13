package fr.istic.m2.taa.pinit.web.rest;

import fr.istic.m2.taa.pinit.domain.Activity;
import fr.istic.m2.taa.pinit.repository.ActivityRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
@Api(value="ActivityResource", description="Operation about Activities resource")
public class ActivityResource {

    private ActivityRepository activityRepository;

    public ActivityResource(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }


    @ApiOperation(value = "Return a list of all activities available")
    @GetMapping("/activities")
    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }



}

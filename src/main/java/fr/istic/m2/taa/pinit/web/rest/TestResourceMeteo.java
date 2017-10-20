package fr.istic.m2.taa.pinit.web.rest;

import fr.istic.m2.taa.pinit.domain.meteo.ListMeteo;
import fr.istic.m2.taa.pinit.service.MeteoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestResourceMeteo {

    private Logger log;
    private MeteoService meteoService;

    public TestResourceMeteo(MeteoService meteoService) {
        this.meteoService = meteoService;
        this.log = LoggerFactory.getLogger(TestResourceMeteo.class);
    }

    @GetMapping("/meteo")
    @Secured("ROLE_USER")
    public ListMeteo getMeteo() {

        ListMeteo resApi = meteoService.getMeteo(48.117266,-1.6777925999999752);

        return resApi;
    }
}

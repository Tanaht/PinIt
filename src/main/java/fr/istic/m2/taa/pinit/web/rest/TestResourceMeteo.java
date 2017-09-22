package fr.istic.m2.taa.pinit.web.rest;

import fr.istic.m2.taa.pinit.domain.Authority;
import fr.istic.m2.taa.pinit.domain.meteo.ListMeteo;
import fr.istic.m2.taa.pinit.domain.meteo.Meteo;
import fr.istic.m2.taa.pinit.service.MeteoService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestResourceMeteo {

    private MeteoService meteoService;

    public TestResourceMeteo(MeteoService meteoService) {
        this.meteoService = meteoService;
    }

    @GetMapping("/meteo")
    @Secured(Authority.ADMIN)
    public ListMeteo getMeteo() {

        ListMeteo resApi = meteoService.getMeteo(48.117266,-1.6777925999999752);

        return resApi;
    }
}

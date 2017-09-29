package fr.istic.m2.taa.pinit.web.rest;

import fr.istic.m2.taa.pinit.domain.Authority;
import fr.istic.m2.taa.pinit.domain.meteo.ListMeteo;
import fr.istic.m2.taa.pinit.service.MeteoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
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

    //When logged as ROLE_USER: we can use @Secured("ROLE_USER") or @PreAuthorize("hasRole('ROLE_USER')")
    //When logged as ROLE_ADMIN: we can use @Secured("ROLE_ADMIN") or @PreAuthorize("hasRole('ROLE_ADMIN')")

    @GetMapping("/meteo")
    @Secured("ROLE_USER")
    public ListMeteo getMeteo() {

        log.debug("Contains user: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(Authority.ADMIN));

        ListMeteo resApi = meteoService.getMeteo(48.117266,-1.6777925999999752);

        return resApi;
    }
}

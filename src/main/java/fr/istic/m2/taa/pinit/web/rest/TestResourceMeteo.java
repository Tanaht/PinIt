package fr.istic.m2.taa.pinit.web.rest;

import fr.istic.m2.taa.pinit.domain.meteo.ListMeteo;
import fr.istic.m2.taa.pinit.domain.meteo.Meteo;
import fr.istic.m2.taa.pinit.service.MeteoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestResourceMeteo {

    private MeteoService meteoService;

    public TestResourceMeteo(MeteoService meteoService) {
        this.meteoService = meteoService;
    }

    @GetMapping("/meteo")
    public ListMeteo getMeteo() {

        ListMeteo resApi = meteoService.getMeteo(48.117266,-1.6777925999999752);

        for (Meteo meteo : resApi.getList()) {
            System.out.println(meteo.getDate());
        }
        return resApi;
    }
}

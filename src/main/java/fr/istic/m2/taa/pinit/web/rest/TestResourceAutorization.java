package fr.istic.m2.taa.pinit.web.rest;

import fr.istic.m2.taa.pinit.domain.Authority;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestResourceAutorization {

    @GetMapping("/test/noAuthority")
    public String ressourceNoAuthority(){
        return "success";
    }

    @GetMapping("/test/userAuthority")
    @Secured(Authority.USER)
    public String ressourceUserAuthority(){
        return "success";
    }


    @GetMapping("/test/adminAuthority")
    @Secured(Authority.ADMIN)
    public String ressourceAdminAuthority(){
        return "success";
    }

}

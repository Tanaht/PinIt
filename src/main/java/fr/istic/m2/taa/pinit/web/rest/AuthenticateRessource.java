package fr.istic.m2.taa.pinit.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.istic.m2.taa.pinit.config.WebSecurityConfigurer;
import fr.istic.m2.taa.pinit.domain.User;
import fr.istic.m2.taa.pinit.repository.UserRepository;
import fr.istic.m2.taa.pinit.security.jwt.JWTConfigurer;
import fr.istic.m2.taa.pinit.security.jwt.TokenProvider;
import fr.istic.m2.taa.pinit.service.AuthenticateService;
import fr.istic.m2.taa.pinit.web.rest.model.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthenticateRessource {
    private final Logger log = LoggerFactory.getLogger(AuthenticateRessource.class);

    private final UserRepository userRepository;

    private final AuthenticateService authenticateService;

    private final TokenProvider tokenProvider;

    public AuthenticateRessource(UserRepository userRepository, AuthenticateService authenticateService, TokenProvider tokenProvider) {
        this.userRepository = userRepository;

        this.authenticateService = authenticateService;

        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/authenticate/login")
    public ResponseEntity connectUser(@Valid Login login, HttpServletResponse response){

        //login exist ?
        Optional<User> potentialUser = userRepository.findOneByLogin(login.getLogin().toLowerCase());

        UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());


        try {
            Authentication authentication = this.authenticateService.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.createToken(authentication);
            //String jwt = "tokenToujoursValide";

            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, jwt);
            return ResponseEntity.ok(new JWTToken(jwt));

        }catch (BadCredentialsException e){
            log.debug("bad credential for login : {}", login);
        }


        return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                "bad login or bad password"), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/authenticate/logout")
    public boolean disconnectUser(@RequestBody String userLogin){

        return true;
    }


    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}

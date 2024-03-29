package fr.istic.m2.taa.pinit.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.istic.m2.taa.pinit.config.security.JWTConfigurer;
import fr.istic.m2.taa.pinit.config.security.TokenProvider;
import fr.istic.m2.taa.pinit.domain.Authority;
import fr.istic.m2.taa.pinit.domain.User;
import fr.istic.m2.taa.pinit.repository.UserRepository;
import fr.istic.m2.taa.pinit.service.AuthenticateService;
import fr.istic.m2.taa.pinit.web.rest.model.Login;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Api(value="AuthenticateRessource", description="Operation about autentification of user")
public class AuthenticateRessource{
    private final Logger log = LoggerFactory.getLogger(AuthenticateRessource.class);

    private final UserRepository userRepository;

    private final AuthenticateService authenticateService;

    private final TokenProvider tokenProvider;

    public AuthenticateRessource(UserRepository userRepository, AuthenticateService authenticateService, TokenProvider tokenProvider) {
        this.userRepository = userRepository;

        this.authenticateService = authenticateService;

        this.tokenProvider = tokenProvider;
    }

    @ApiOperation(value = "Authenticate a user and return info user with token in header")
    @PostMapping("/authenticate/login")
    public User connectUser(@Valid @RequestBody Login login, HttpServletResponse response){
        UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());

        log.debug("Attempt to login of {}", authenticationToken.getPrincipal());
        Authentication authentication = this.authenticateService.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        log.debug("A token has been generated for {}: {}", authentication.getPrincipal(), token);

        response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, token);
        log.debug("Header filled with token");
        return userRepository.findOneByLogin(login.getLogin()).get();
    }

    @ApiOperation(value = "pas utilisé, à supprimer")
    @RequestMapping(value="/authenticate/logout", method = RequestMethod.GET)
    @Secured(Authority.USER)
    public boolean disconnectUser(){

        return true;
    }



    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String token;

        JWTToken(String token) {
            this.token = token;
        }

        @JsonProperty("token")
        String getToken() {
            return token;
        }

        void setToken(String token) {
            this.token = token;
        }
    }
}

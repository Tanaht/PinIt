package fr.istic.m2.taa.pinit.web.rest;

import fr.istic.m2.taa.pinit.config.security.JWTConfigurer;
import fr.istic.m2.taa.pinit.config.security.TokenProvider;
import fr.istic.m2.taa.pinit.domain.User;
import fr.istic.m2.taa.pinit.repository.UserRepository;
import fr.istic.m2.taa.pinit.service.AuthenticateService;
import fr.istic.m2.taa.pinit.service.UserService;
import fr.istic.m2.taa.pinit.web.rest.exception.BadActivityId;
import fr.istic.m2.taa.pinit.web.rest.exception.BadUserId;
import fr.istic.m2.taa.pinit.web.rest.exception.UserLoginAlreadyExist;
import fr.istic.m2.taa.pinit.web.rest.model.Login;
import fr.istic.m2.taa.pinit.web.rest.model.UserRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class UserResource {
    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserRepository userRepository;

    private final UserService userService;
    private final AuthenticateService authenticateService;

    private final TokenProvider tokenProvider;

    public UserResource(UserRepository userRepository, UserService userService, AuthenticateService authenticateService, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authenticateService = authenticateService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody UserRegister newUser, HttpServletResponse response) throws UserLoginAlreadyExist {
        log.debug("REST request to save User : {}", newUser);

        if (userRepository.findOneByLogin(newUser.getLogin().toLowerCase()).isPresent()) {
            throw new UserLoginAlreadyExist(newUser.getLogin());
        } else {
            userService.createUser(newUser);

            //authentification user
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(newUser.getLogin(), newUser.getPassword());

            log.debug("Attempt to login of {}", authenticationToken.getPrincipal());
            Authentication authentication = this.authenticateService.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenProvider.generateToken(authentication);

            log.debug("A token has been generated for {}: {}", authentication.getPrincipal(), token);

            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, token);
            log.debug("Header filled with token");
            return userRepository.findOneByLogin(newUser.getLogin()).get();

            //return ResponseEntity.ok("user created");
        }
    }

    @ExceptionHandler({UserLoginAlreadyExist.class})
    void handleBadRequests(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}

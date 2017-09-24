package fr.istic.m2.taa.pinit.web.rest;

import fr.istic.m2.taa.pinit.domain.User;
import fr.istic.m2.taa.pinit.repository.UserRepository;
import fr.istic.m2.taa.pinit.service.AuthenticateService;
import fr.istic.m2.taa.pinit.service.UserService;
import fr.istic.m2.taa.pinit.web.rest.model.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthenticateRessource {
    private final Logger log = LoggerFactory.getLogger(AuthenticateRessource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final AuthenticateService authenticateService;


    public AuthenticateRessource(UserRepository userRepository, UserService userService, AuthenticateService authenticateService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authenticateService = authenticateService;
    }

    @PostMapping("/login/connect")
    public String connectUser(@RequestBody Login login){

        //login exist ?

        Optional<User> potentialUser = userRepository.findOneByLogin(login.getLogin().toLowerCase());

        if (potentialUser.isPresent()) {
            User user = potentialUser.get();
            if (login.getPasswordHashed().equals(user.getPassword())){

            }else{//bad password
                log.debug("bad password for login : {}", login);
            }
        }else{// bad login
            log.debug("bad login for login : {}", login);

        }

        return "";
    }

    @GetMapping("/login/disconnect")
    public boolean disconnectUser(@RequestBody String userLogin){

        return true;
    }
}

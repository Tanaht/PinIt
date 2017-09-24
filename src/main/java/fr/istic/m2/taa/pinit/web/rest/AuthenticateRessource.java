package fr.istic.m2.taa.pinit.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.istic.m2.taa.pinit.config.SecurityConfig;
import fr.istic.m2.taa.pinit.domain.User;
import fr.istic.m2.taa.pinit.repository.UserRepository;
import fr.istic.m2.taa.pinit.web.rest.model.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthenticateRessource {
    private final Logger log = LoggerFactory.getLogger(AuthenticateRessource.class);

    private final UserRepository userRepository;



    private final AuthenticationManager authenticationManager;

    public AuthenticateRessource(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;

        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login/connect")
    public ResponseEntity connectUser( Login login, HttpServletResponse response){

        //login exist ?
        Optional<User> potentialUser = userRepository.findOneByLogin(login.getLogin().toLowerCase());
        //if (potentialUser.isPresent()) {
        if (true) {
            //User user = potentialUser.get();
            if (true){
            //if (login.getPassword().equals(user.getPassword())){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());

                log.info(authenticationToken.toString());

                try {
                    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);




                SecurityContextHolder.getContext().setAuthentication(authentication);

                //String jwt = tokenProvider.createToken(authentication);
                    String jwt = "tokenToujoursValide";

                response.addHeader(SecurityConfig.AUTHORIZATION_HEADER, "Bearer " + jwt);

                return ResponseEntity.ok(new JWTToken(jwt));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{//bad password
                log.debug("bad password for login : {}", login);
            }
        }else{// bad login
            log.debug("bad login for login : {}", login);

        }

        return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
                "bad login or bad password"), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/login/disconnect")
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

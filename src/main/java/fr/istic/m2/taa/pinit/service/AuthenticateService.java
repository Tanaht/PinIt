package fr.istic.m2.taa.pinit.service;


import fr.istic.m2.taa.pinit.domain.User;
import fr.istic.m2.taa.pinit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service used to authenticate a user according to its credentials (username, password)
 */
@Service
public class AuthenticateService implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(AuthenticateService.class);

    private PasswordEncoder encoder;

    private UserRepository userRepository;

    public AuthenticateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Authenticate a user and return an authentication object.
     * @param authentication
     * @return Authentication
     * @throws AuthenticationException
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";
        log.debug("Try to authenticate user with credentials: [{}, {}]", username, password);

        Optional<User> potentialUser = userRepository.findOneByLogin(username.toLowerCase());

        if (!potentialUser.isPresent()) {
            throw new BadCredentialsException("User not found in database");
        }


        if (!encoder.matches(password, potentialUser.get().getPassword())){
            throw new BadCredentialsException("Failed to login, incorrect password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, potentialUser.get().getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

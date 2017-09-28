package fr.istic.m2.taa.pinit.service;


import fr.istic.m2.taa.pinit.domain.User;
import fr.istic.m2.taa.pinit.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticateService implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(AuthenticateService.class);

    private UserRepository userRepository;

    public AuthenticateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        Optional<User> potentialUser = userRepository.findOneByLogin(username.toLowerCase());

        if (!potentialUser.isPresent()) {
            //bad login
            throw new BadCredentialsException("1000");
        }

        if (!potentialUser.get().getPassword().equals(password)){
            //bad password
            throw new BadCredentialsException("1000");
        }

        return new UsernamePasswordAuthenticationToken(username, password, potentialUser.get().getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}

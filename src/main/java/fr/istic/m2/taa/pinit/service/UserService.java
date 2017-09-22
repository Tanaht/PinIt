package fr.istic.m2.taa.pinit.service;

import fr.istic.m2.taa.pinit.domain.Authority;
import fr.istic.m2.taa.pinit.domain.User;
import fr.istic.m2.taa.pinit.repository.AuthorityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    public UserService(PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    public User createUser(String login, String password) {
        User newUser = new User();
        Authority authority = authorityRepository.findOne(Authority.USER);

        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);


        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        newUser.setAuthorities(authorities);


        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

}

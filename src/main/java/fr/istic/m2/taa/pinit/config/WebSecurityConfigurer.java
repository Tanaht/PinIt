package fr.istic.m2.taa.pinit.config;


import fr.istic.m2.taa.pinit.config.security.JWTConfigurer;
import fr.istic.m2.taa.pinit.config.security.TokenProvider;
import fr.istic.m2.taa.pinit.service.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    private TokenProvider tokenProvider;

    private AuthenticateService authenticateService;

    public WebSecurityConfigurer(TokenProvider tokenProvider, AuthenticateService authenticateService) {
        this.tokenProvider = tokenProvider;
        this.authenticateService = authenticateService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/","/app/**","/api/authenticate/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(securityConfigurerAdapter());

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticateService);
        /*
        auth
                .inMemoryAuthentication()
                .withUser("user").password("user").roles(Authority.USER).
                and()
                .withUser("admin").password("admin").roles(Authority.ADMIN);
        */
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }*/

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}

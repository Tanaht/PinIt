package fr.istic.m2.taa.pinit.config;


import fr.istic.m2.taa.pinit.security.jwt.JWTConfigurer;
import fr.istic.m2.taa.pinit.security.jwt.TokenProvider;
import fr.istic.m2.taa.pinit.service.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity(debug = false)
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
                .antMatchers("/","/app/**","/api/authenticate/login","/api/users","/api/**","/api/*").permitAll()


                .and()
                .apply(securityConfigurerAdapter());

        //Implementing Token based authentication in this filter
        //final JWTFilter tokenFilter = new JWTFilter(tokenProvider);
        //http.addFilterBefore(tokenFilter, BasicAuthenticationFilter.class);

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


    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


}

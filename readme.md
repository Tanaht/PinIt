Pin It Application
-

###Gestion Sécurité:

Pour créer un point d'entrée vers une resource Rest, il faut veiller aux contrôles d'accès a cette ressource.

Il y a actuellement trois accès possible Libre, ou connecté en tant qu'administrateur ou utilisateur:

Pour autoriser l'accès à une ressource à tout utilisateur, mettre le chemin de la ressource comme suit dans la classe WebSecurityConfigurer :

    package fr.istic.m2.taa.pinit.config;
    
    public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatchers("/resource/libre1", /resource/libre2).permitAll();
    
        }
        
    }
    
    
Pour sécurisée l'accès à une resource spécifique il faut utiliser une des deux annotations suivantes sur la méthode qui sert de point d'entrée pour accèder à la resource:

    @RestController
    @RequestMapping("/api")
    public class RestResource {
    
        @GetMapping("/resource")
        @Secured("ROLE_USER")
        public String getResource() {
            return "Resource accessible uniquement par les utilisateurs disposant du rôle ROLE_USER";
        }
        
        
        @GetMapping("/resource")
        @PreAuthorize("hasRole('ROLE_ADMIN')
        public String getResource() {
            return "Resource accessible uniquement par les utilisateurs disposant du rôle ROLE_ADMIN";
        }
    }

La classe fr.istic.m2.taa.pinit.domain.Authority met à disposition un raccourcis pour ne pas se tromper dans l'écriture des rôles:
    
    Authority.USER = "ROLE_USER";

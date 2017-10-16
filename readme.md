Pin It Application
-

###Données de développements:

Durant le développement un script SQL est utilisé pour hydrater la base de données au démarrage de Spring.
le fichier `src/main/resources/import.sql` est exécuté lorsque la propriété de configuration Spring `spring.jpa.ddl-auto` est égale à `create` ou `create-drop`.

Ce fichier SQL génère notamment deux utilisateurs de base, un utilisateur lambda et un admin:

```mysql
INSERT INTO user VALUES (1, 'charp.antoine+pinit-user@gmail.com', 'user', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.'); #PWD = 123456
INSERT INTO user VALUES (2, 'charp.antoine+pinit-admin@gmail.com', 'admin', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.'); #PWD = 123456
```

Etant donné que les mots de passe de la base de donnée sont crypté avec l'algorithme BCrypt les couples login/mots de passe sont:

* **user/123456**
* **admin/123456**
###Gestion Sécurité:

Pour créer un point d'entrée vers une resource Rest, il faut veiller aux contrôles d'accès a cette ressource.

Il y a actuellement trois accès possible Libre, ou connecté en tant qu'administrateur ou utilisateur:

Pour autoriser l'accès à une ressource à tout utilisateur, mettre le chemin de la ressource comme suit dans la classe WebSecurityConfigurer :

```java
package fr.istic.m2.taa.pinit.config;

public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatchers("/resource/libre1", "/resource/libre2").permitAll();

    }
    
}
```
    
    
Pour sécuriser l'accès à une resource spécifique il faut utiliser une des deux annotations suivantes sur la méthode qui sert de point d'entrée pour accèder à la resource:

```java
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
```

La classe fr.istic.m2.taa.pinit.domain.Authority met à disposition un raccourcis pour ne pas se tromper dans l'écriture des rôles:
    
```java
class Authority {
    private static final String USER = "ROLE_USER";
}
```

###API

`POST /api/authenticate/login`: Permet la connexion d'un utilisateur.

`GET /api/authenticate/logout` Permet la déconnexion d'un utilisateur.

`POST /api/users`: Permet de créer un nouvel utilisateur.

`GET /api/users/{idUser}/inscriptions`: Permet de retourner tous les lieux choisit par un utilisateur

`POST /api/users/{idUser}/inscriptions`: Permet de rajouter une inscription.

`DELETE /api/inscriptions/{idInscription}`: Permet de supprimer une inscription.

`PUT /api/inscriptions/{idInscription}`: Permet de modifier une inscription.
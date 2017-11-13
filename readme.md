Pin It Application
-

### Informations utiles:

Security is not the issue of this topic so passwords are stupidly simple.

There may also be severall exploit like CSRF (we do no CSRF protection on forms submission).

#### Tutoriels:

Google MAP API Documentations: https://angular-maps.com/

Angular 4: https://angular.io/

Angular Material: https://material.angular.io/

Flex Layout: https://github.com/angular/flex-layout/wiki/API-Documentation

#### Informations:
GOOGLE MAP API KEY: AIzaSyCQaM6Mw4t5EbZVhbab3mBuWWROC_pcNT0

ISTIC COORDINATE: 48.115464, -1.638707


###Docker-isation:

// TODO: Use Docker Compose with yaml configuration file if time is not over.

`docker/mysql-env.list` Configuration to run Mysql Image:

Mysql database: pinit
Mysql user: pinit
Mysql password: 123456789
Mysql root password: 123456789

`docker/pinit-env.list` Configuration to override Spring environment variable to connect to mysql datasource

/!\ Before these commands, if no docker image exists run the following command:
```Shell
sudo mvn dockerfile:build -Denv=prod
```

```Shell
sudo docker run --name mysql-for-pinit --env-file=docker/mysql-env.list -d mysql:latest
sudo docker run --name pinit-app-1 --env-file=docker/pinit-env.list --link mysql-for-pinit:mysql -p 8080:8080 -d pinit:latest
```


And if we want to expand application but with the same underlying database:

<span style="color:red;">/!\ Be sure to remove automated `src/main/resources/import.sql` file in this mode.</span>
Because it will eraise the database at each new server start.

```Shell
sudo docker run --name mysql-for-pinit --env-file=docker/mysql-env.list -p 50000-50050:3306 -d mysql:latest

#Pin It App 1
sudo docker run --name pinit-app-1 --env-file=docker/pinit-env.list --link mysql-for-pinit:mysql -p 50050-50100:8080 -d pinit:latest

Pin It App 2
sudo docker run --name pinit-app-2 --env-file=docker/pinit-env.list --link mysql-for-pinit:mysql -p 50050-50100:8080 -d pinit:latest

```

With this config we can have up to 50 Pin It Application running for the same underlying database:
```Shell
$ sudo docker ps -a
CONTAINER ID        IMAGE               COMMAND                  CREATED              STATUS              PORTS                     NAMES
c61abb1473b1        pinit:latest        "/bin/sh -c 'exec ..."   36 seconds ago       Up 34 seconds       0.0.0.0:50051->8080/tcp   pinit-app-2
7b64d296bdf2        pinit:latest        "/bin/sh -c 'exec ..."   About a minute ago   Up About a minute   0.0.0.0:50050->8080/tcp   pinit-app-1
5ca328a40833        mysql:latest        "docker-entrypoint..."   3 minutes ago        Up 3 minutes        0.0.0.0:50000->3306/tcp   mysql-for-pinit
```
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

Documentation de l'api : http://localhost:8080/swagger-ui.html

`POST /api/authenticate/login`: Permet la connexion d'un utilisateur.

`GET /api/authenticate/logout` Permet la déconnexion d'un utilisateur.

`POST /api/users`: Permet de créer un nouvel utilisateur.

`GET /api/users/{idUser}/inscriptions`: Permet de retourner tous les lieux choisit par un utilisateur

`POST /api/users/{idUser}/inscriptions`: Permet de rajouter une inscription.

`DELETE /api/inscriptions/{idInscription}`: Permet de supprimer une inscription.

`PUT /api/inscriptions/{idInscription}`: Permet de modifier une inscription.
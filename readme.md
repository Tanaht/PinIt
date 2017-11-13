Pin It Application
-

### Informations utiles:

Security is not the issue of this topic so passwords are stupidly simple.

There may also be severall exploit like CSRF (we do no CSRF protection on forms submission).

Ce projet s'inspire de la configuration d'une application monolithique en JHIPSTER.

Notamment au niveau de la configuration Spring et Webpack, 
et la façon dont jhipster surcharge la configuration Spring du fichier application.yml par les variables d'environnements 
Docker à aussi été réutilisée.

#### Exigences techniques:

L'application consiste en un serveur réalisée avec le framework Spring-Boot et un client web en Angular 4.

- Il ne doit pas être possible, pour un utilisateur, d’accéder à (ni modifier) une information concernant un autre utilisateur
  
  De base les utilisateurs s'authentifient et recoivent un token, chaque token identifie un utilisateur, de ce fait lorsqu'une resource concernant un utilisateur est demandée, on vérifie que le token correspond à cet utilisateur, sauf en cas d'utilisateurs admin.

- L’état de l’application doit être persistant (si on éteint puis rallume l’application, elle doit retrouver son état avant extinction)
  
  Ca fonctionne, mais la configuration actuelle recharge la base de donnée gràce aux script `src/main/resources/import.sql` 
  par défaut le script sera renommé en `src/main/resources/_import.sql` pour assurer la persistence.
  
- Toutes les actions doivent pouvoir être réalisées depuis des points d’entrée HTTP (le corps des requêtes et des réponses doit être au format JSON)
  
  On peut le visualiser grâce à Swagger.

- Le système doit informer différemment l’utilisateur des échecs selon que ceux-ci soient dus à une erreur de la part de l’utilisateur ou à un problème technique interne

  Plusieurs Snackbar (notifications en bas à droite de l'interface) ont été implémentée sur l'interface qui annonce les problèmes de connexion ou les erreurs côté serveurs.
  
  
- Le serveur HTTP est scalable horizontalement (le fait de démarrer plusieurs instances en parallèle ne pose pas de problème)

  Résolue avec Docker, example plus bas.
  
- L’API respecte les principes d’architecture REST

  Les principes REST de base sont présent à savoir qu'une requête DELETE correspond à la suppression de la resource ciblé par l'url.

- L’API est découvrable en lecture

  Il est possible de voir l'api grâce à swagger avec l'URL /swagger-ui.html
  
- Dans la mesure du possible, tous les traitements doivent être réalisés de façon non bloquante, et utiliser au mieux la capacité de calcul des machines (e.g. multi-cœurs)


#### Client Side Documentation pour les développeurs:

Google MAP API Documentations: https://angular-maps.com/

Angular 4: https://angular.io/

Angular Material: https://material.angular.io/


// TODO: It would be great to remove this dependencies not so usefull and a little buggy.
Flex Layout: https://github.com/angular/flex-layout/wiki/API-Documentation

#### Informations:
GOOGLE MAP API KEY: AIzaSyCQaM6Mw4t5EbZVhbab3mBuWWROC_pcNT0

ISTIC (parking) COORDINATE: 48.115464, -1.638707


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

*/!\ Be sure to remove automated `src/main/resources/import.sql` file in this mode.*
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
Finalement le role d'administrateur est inutile pour le moment, mais dans le cas d'une éventuel évolution, cette fonctionnalités et préimplémentée.

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
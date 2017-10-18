import {Injectable, Injector} from '@angular/core';
import {RestService} from '../services/rest/rest.service';
import {LoggerService} from '../logger/logger.service';
import {MatSnackBar, MatSnackBarConfig} from '@angular/material';
import {Router} from '@angular/router';
import {User} from '../model/user';
import {Authority} from '../model/authority';
import {Http} from '@angular/http';

@Injectable()
export class AuthenticationService {
    private user: User;

    constructor(private router: Router, private snackBar: MatSnackBar, private logger: LoggerService, private http: Http) {
        this.user = null;
    }

    public authenticate(username: string, password: string): void {
        this.logger.debug('AuthenticationService', username, password);
        const config = new MatSnackBarConfig();
        config.verticalPosition = 'bottom';
        config.horizontalPosition = 'right';
        config.duration = 2000;

        this.http.post('/api/authenticate/login', { login: username, password: password}).map(function(res) {

            return new User(res.json().id, res.json().login, res.json().authorities.map(function(authority) {
                return new Authority(authority.authority);
            }), res.headers.get('token'), res.json().email);

        }).subscribe(
            (user) => {
                this.user = user;

                this.logger.debug('AuthenticationService', 'TOKEN', this.user.token);
                this.snackBar.open("Bonjour " + this.user.username, null, config);
                this.router.navigateByUrl("/");
            },
            (err) => {
                this.logger.error('AuthenticationService', err);

                config.extraClasses = ['pi-snackbar-warn'];

                this.snackBar.open("Echec de la connexion, merci de réessayer !", null, config);
            }
        );
    }

    public register(username: string, password: string, email: string): void {
        this.logger.debug('AuthenticationService register', username, password, email);
        const config = new MatSnackBarConfig();

        this.http.post('/api/users', { login: username, password: password, email: email}).subscribe(
            (data) => {

                this.logger.debug("AuthenticationService", "Success to register a new user", data);
                this.router.navigateByUrl("home");
            },
            (err) => {
                this.logger.error("AuthenticationService", "Une erreur à eu lieu lors de l'inscription", err);
                config.extraClasses = ['pi-snackbar-warn'];
                this.snackBar.open("Une erreur à eu lieu lors de l'inscription !", null, config);

            }
        );
    }

    public isAuthenticated(): boolean {
        return this.user !== null;
    }

    public hasRole(role: string): boolean {
        if ( !this.isAuthenticated()) {
            return false;
        }

        return this.user.hasRole(role);
    }

    public getUser(): User {
        return this.user;
    }
}

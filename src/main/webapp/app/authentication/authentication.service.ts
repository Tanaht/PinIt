import {Injectable, Injector} from '@angular/core';
import {RestService} from '../rest/rest.service';
import {LoggerService} from '../logger/logger.service';
import {MatSnackBar, MatSnackBarConfig} from '@angular/material';
import {Router} from '@angular/router';
import {User} from '../model/user';

@Injectable()
export class AuthenticationService {
    private user: User;
    private rest: RestService;

    constructor(private router: Router, private snackBar: MatSnackBar, private logger: LoggerService, private injector: Injector) {
        this.rest = injector.get(RestService);
        this.user = null;
    }

    public authenticate(username: string, password: string): void {
        this.logger.debug('AuthenticationService', username, password);
        const config = new MatSnackBarConfig();
        config.verticalPosition = 'bottom';
        config.horizontalPosition = 'right';
        config.duration = 2000;

        this.rest.post('/api/authenticate/login', { login: username, password: password}).subscribe(
            (data) => {

                this.user = new User(username, data.json().token);
                this.logger.debug('AuthenticationService', 'TOKEN', this.user.token);
                this.snackBar.open("Bonjour " + this.user.username, null, config);
                this.router.navigateByUrl("/register");
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
        const config = new MdSnackBarConfig();

        this.rest.post('/api/users', { login: username, password: password, email: email}).subscribe(
            (data) => {

                this.logger.error("Authenticationservice", "register success", data);
                this.router.navigateByUrl("/");
            },
            (err) => {

                this.logger.error("Authenticationservice", "bad login", err);
                if (err.status === 400) {
                    config.extraClasses = ['pi-snackbar-warn'];
                    this.snackBar.open("Ce login existe déjà !", null, config);
                }

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

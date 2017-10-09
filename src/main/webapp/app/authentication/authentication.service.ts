import {Injectable} from '@angular/core';
import {RestService} from '../rest/rest.service';
import {LoggerService} from '../logger/logger.service';
import {MatSnackBar, MdSnackBarConfig} from '@angular/material';
import {Router} from '@angular/router';
import {RegisterComponent} from './register/register.component';

@Injectable()
export class AuthenticationService {
    private token: string;
    constructor(private router: Router, private rest: RestService, private snackBar: MatSnackBar, private logger: LoggerService) {}

    public authenticate(username: string, password: string): void {
        this.logger.debug('AuthenticationService', username, password);
        const config = new MdSnackBarConfig();
        config.verticalPosition = 'bottom';
        config.horizontalPosition = 'right';
        config.duration = 2000;

        this.rest.post('/api/authenticate/login', { login: username, password: password}).subscribe(
            (data) => {
                this.token = data.json().token;
                this.logger.debug('TOKEN', this.token);
                this.snackBar.open("Bonjour " + username, null, config);
                this.router.navigateByUrl("/register");
            },
            (err) => {
                this.logger.error('Authentication failed', err);

                config.extraClasses = ['pi-snackbar-warn'];

                this.snackBar.open("Echec de la connexion, merci de réessayer !", null, config);
            }
        );
    }

    public register(username: string, password: string, email: string): void{
        this.logger.debug('AuthenticationService register', username, password, email);
        let config = new MdSnackBarConfig();

        this.rest.post('/api/users', { login: username, password: password, email: email}).subscribe(
            (data) => {

                this.logger.error("result",data);
                this.router.navigateByUrl("/");
            },
            (err) => {

                this.logger.error('Register failed', err);
                if(err.status == 400){
                    config.extraClasses = ['pi-snackbar-warn'];
                    this.snackBar.open("Ce login existe déjà !", null, config);
                }

            }
        );
    }
}

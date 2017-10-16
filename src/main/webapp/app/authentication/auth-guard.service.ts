import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {LoggerService} from '../logger/logger.service';
import {AuthenticationService} from './authentication.service';
import {MatSnackBar, MatSnackBarConfig} from '@angular/material';

@Injectable()
export class AuthGuardService implements CanActivate {

    constructor(private auth: AuthenticationService, private log: LoggerService, private snackBar: MatSnackBar) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {

        const config = new MatSnackBarConfig();
        config.verticalPosition = 'bottom';
        config.horizontalPosition = 'right';
        config.duration = 2000;

        if (this.auth.isAuthenticated()) {
            this.log.info("AuthGuardService", "Access granted for user '" + this.auth.getUser().username + "'");
            return true;
        } else {
            config.extraClasses = ['pi-snackbar-warn'];
            this.log.warn("AuthGuardService", "Anonymous user tried to access to unallowed resources");
            this.snackBar.open("Vous n'avez pas les droits d'accès", null, config);
            return false;
        }
    }

}

import { Component } from '@angular/core';
import {AuthenticationService} from '../authentication.service';

@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styles: ['md-card { margin-top: 40px; }']
})
export class LoginComponent {
    constructor(private auth: AuthenticationService) {}

    public authenticate(username:string, password:string): void {
        this.auth.authenticate(username, password);
    }
}

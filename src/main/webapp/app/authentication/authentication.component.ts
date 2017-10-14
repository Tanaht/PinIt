import { Component } from '@angular/core';
import {MatMenu} from '@angular/material';
import {AuthenticationService} from './authentication.service';

@Component({
    selector: 'authentication',
    templateUrl: './authentication.component.html'
})
export class AuthenticationComponent {
    authenticationMenu: MatMenu;

    constructor(public auth: AuthenticationService) {}
}

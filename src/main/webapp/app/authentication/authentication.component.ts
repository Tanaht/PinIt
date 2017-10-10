import { Component } from '@angular/core';
import {MatMenu} from '@angular/material';

@Component({
    selector: 'authentication',
    templateUrl: './authentication.component.html'
})
export class AuthenticationComponent {
    authenticationMenu: MatMenu;
}

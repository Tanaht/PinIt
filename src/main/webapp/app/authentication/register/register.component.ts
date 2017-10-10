import { Component } from '@angular/core';
import {User} from '../user';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../authentication.service';
import {LoggerService} from '../../logger/logger.service';

@Component({
    selector: 'register',
    templateUrl: './register.component.html',
    styles: ['md-card { margin-top: 40px; }']
})
export class RegisterComponent {
    private authenticated: User;
    loginForm: FormGroup;
    constructor(private auth: AuthenticationService, private fb: FormBuilder, private logger: LoggerService) {
        this.authenticated = new User();
        this.createForm();
    }

    createForm() {
        this.loginForm = this.fb.group({
            username: ['', Validators.required],
            password: ['', Validators.required],
            passwordConfirm: ['', Validators.required],
            email: ['', Validators.email]
        }, {validator: this.checkPasswordsConfirm});
    }

    register(): void {
        this.auth.register( this.loginForm.get("username").value, this.loginForm.get("password").value, this.loginForm.get("email").value);
    }

    checkPasswordsConfirm(group: FormGroup) {
        return group.get('password').value === group.get('passwordConfirm').value
            ? null : {'mismatch': true};
    }
}

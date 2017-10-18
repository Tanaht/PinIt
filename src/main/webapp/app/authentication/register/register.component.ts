import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../authentication.service';
import {LoggerService} from '../../logger/logger.service';

@Component({
    selector: 'register',
    templateUrl: './register.component.html',
    styleUrls: ['../../router-outlet-component-layout.css'],
})
export class RegisterComponent {
    loginForm: FormGroup;
    constructor(private auth: AuthenticationService, private fb: FormBuilder, private logger: LoggerService) {
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

import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../authentication.service';
import {LoggerService} from '../../logger/logger.service';

@Component({
    selector: 'register',
    templateUrl: './register.component.html'
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
        }, { validator: this.checkPasswords });
    }

    register(): void {
        this.auth.register( this.loginForm.get("username").value, this.loginForm.get("password").value, this.loginForm.get("email").value);
    }

    checkPasswords(group: FormGroup) {
        const pass = group.controls.password.value;
        const confirmPass = group.controls.passwordConfirm.value;

        if (pass === confirmPass) {
            // group.controls.passwordConfirm.setErrors({'incorrect': false});
            return null;
        }else {
            // group.controls.passwordConfirm.setErrors({'incorrect': true});
            return true;
        }
    }
}

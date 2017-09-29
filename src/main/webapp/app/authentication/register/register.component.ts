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
        },{validator: this.checkPasswords});
    }

    register(): void {
        this.auth.register(this.loginForm.get("username").value, this.loginForm.get("password").value,this.loginForm.get("email").value);
    }

    checkPasswords(group: FormGroup) {
        let pass = group.controls.password.value;
        let confirmPass = group.controls.passwordConfirm.value;


        if(pass === confirmPass){
            //group.controls.passwordConfirm.setErrors({'incorrect': false});
            return null;
        }else{
            //group.controls.passwordConfirm.setErrors({'incorrect': true});
            return true;
        }
    }
}

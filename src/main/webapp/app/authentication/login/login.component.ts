import {Component} from '@angular/core';
import {AuthenticationService} from '../authentication.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {LoggerService} from '../../logger/logger.service';
import {User} from '../user';

@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styles: ['md-card { margin-top: 40px; }']
})
export class LoginComponent {
    private authenticated: User;
    public loginForm: FormGroup;

    constructor(private auth: AuthenticationService, private fb: FormBuilder, private logger: LoggerService) {
        this.authenticated = new User();
        this.createForm();
    }

    createForm() {
        this.loginForm = this.fb.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });
    }

    authenticate(): void {
        this.auth.authenticate(this.loginForm.get("username").value, this.loginForm.get("password").value);
    }

   /* ngOnChanges() {
        this.loginForm.setValue({
            username:    this.authenticated.username,
            password: this.authenticated.password
        });
    }*/
}

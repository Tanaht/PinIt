import {NgModule} from '@angular/core';
import {LoginComponent} from './login/login.component';
import {AuthenticationComponent} from './authentication.component';
import {
    MatButtonModule, MatCardModule, MatFormFieldModule, MatIconModule, MatInputModule,
    MatMenuModule, MatSnackBarModule
} from '@angular/material';
import {RegisterComponent} from './register/register.component';
import {AppRoutingModule} from '../app-routing.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {HttpModule} from '@angular/http';
import {AuthenticationService} from './authentication.service';
import {LoggerService} from '../logger/logger.service';
import {RestService} from '../services/rest/rest.service';
import {ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {AuthGuardService} from './auth-guard.service';
import { LogoutComponent } from './logout/logout.component';

@NgModule({
    declarations: [
        AuthenticationComponent,
        LoginComponent,
        RegisterComponent,
        LogoutComponent
    ],
    imports: [
        AppRoutingModule,

        CommonModule,
        ReactiveFormsModule,
        HttpModule,
        MatMenuModule,
        MatButtonModule,
        MatIconModule,
        MatCardModule,
        MatInputModule,
        MatFormFieldModule,
        MatSnackBarModule,
        MatSnackBarModule,
        FlexLayoutModule
    ],
    providers: [
        AuthGuardService,
        LoggerService,
        RestService,
        AuthenticationService
    ],
    exports: [
        AuthenticationComponent,
        LoginComponent,
        RegisterComponent
    ]
})
export class AuthenticationModule {
}

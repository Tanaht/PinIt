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
import {RestService} from '../rest/rest.service';
import {ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@NgModule({
    declarations: [
        AuthenticationComponent,
        LoginComponent,
        RegisterComponent
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

import {NgModule} from '@angular/core';
import {LoginComponent} from './login/login.component';
import {AuthenticationComponent} from './authentication.component';
import {
    MdButtonModule, MdCardModule, MdFormFieldModule, MdIconModule, MdInputModule,
    MdMenuModule
} from '@angular/material';
import {RegisterComponent} from './register/register.component';
import {AppRoutingModule} from '../app-routing.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {HttpModule} from '@angular/http';
import {AuthenticationService} from './authentication.service';
import {LoggerService} from '../logger/logger.service';
import {RestService} from '../rest/rest.service';

@NgModule({
    declarations: [
        AuthenticationComponent,
        LoginComponent,
        RegisterComponent
    ],
    imports: [
        AppRoutingModule,

        HttpModule,
        MdMenuModule,
        MdButtonModule,
        MdIconModule,
        MdCardModule,
        MdInputModule,
        MdFormFieldModule,

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

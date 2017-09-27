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

@NgModule({
    declarations: [
        AuthenticationComponent,
        LoginComponent,
        RegisterComponent
    ],
    imports: [
        AppRoutingModule,
        MdMenuModule,
        MdButtonModule,
        MdIconModule,
        MdCardModule,
        MdInputModule,
        MdFormFieldModule,

        FlexLayoutModule
    ],
    providers: [
    ],
    exports: [
        AuthenticationComponent,
        LoginComponent,
        RegisterComponent
    ]
})
export class AuthenticationModule {
}

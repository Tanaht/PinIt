import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {LoginComponent} from './authentication/login/login.component';
import {RegisterComponent} from './authentication/register/register.component';

const appRoutes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent }
];

@NgModule({
    imports: [
        RouterModule.forRoot(
            appRoutes,
            { enableTracing: false, useHash: true }
        )
    ],
    exports: [
        RouterModule
    ]
})
export class AppRoutingModule {}

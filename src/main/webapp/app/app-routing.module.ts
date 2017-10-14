import { NgModule } from '@angular/core';
import {RouterModule, RouterOutlet, Routes} from '@angular/router';

import {LoginComponent} from './authentication/login/login.component';
import {RegisterComponent} from './authentication/register/register.component';
import {MapComponent} from './map/map.component';

const appRoutes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'map', component: MapComponent }
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
export class AppRoutingModule {
/*    constructor(private router: RouterOutlet) {
        this.router.
    }*/
}

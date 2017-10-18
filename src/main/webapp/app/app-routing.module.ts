import { NgModule } from '@angular/core';
import {RouterModule, RouterOutlet, Routes} from '@angular/router';

import {LoginComponent} from './authentication/login/login.component';
import {RegisterComponent} from './authentication/register/register.component';
import {MapComponent} from './map/map.component';
import {AuthGuardService} from './authentication/auth-guard.service';
import {LogoutComponent} from './authentication/logout/logout.component';
import {HomeComponent} from './home/home.component';

const appRoutes: Routes = [
    { path: '', pathMatch: 'full', redirectTo: 'home'},
    { path: 'home', component: HomeComponent},
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: '',
        canActivate: [AuthGuardService],
        children: [
            { path: 'map', component: MapComponent },
            { path: 'logout', component: LogoutComponent }
        ]
    }
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

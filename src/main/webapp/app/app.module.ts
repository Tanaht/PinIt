import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
    MatButtonModule, MatCardModule, MatIconModule, MatIconRegistry, MatListModule, MatMenuModule, MatSidenavModule,
    MatToolbarModule
} from '@angular/material';

import {AuthenticationModule} from './authentication/authentication.module';
import {AppRoutingModule} from './app-routing.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import {RestService} from './rest/rest.service';
import {LoggerService} from './logger/logger.service';
import {HttpModule} from '@angular/http';
import {ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import { MapComponent } from './map/map.component';
import {AgmCoreModule} from '@agm/core';

@NgModule({
    declarations: [
        AppComponent,
        MapComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,

        CommonModule,
        ReactiveFormsModule,
        HttpModule,
        MatToolbarModule,
        MatCardModule,
        MatSidenavModule,
        MatButtonModule,
        MatIconModule,
        MatListModule,
        MatMenuModule,
        MatButtonModule,

        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyCQaM6Mw4t5EbZVhbab3mBuWWROC_pcNT0'
        }),

        FlexLayoutModule,

        AuthenticationModule,
    ],
    providers: [
        MatIconRegistry, RestService, LoggerService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
    constructor(iconReg: MatIconRegistry) {
        iconReg.registerFontClassAlias('fontawesome', 'fa');
    }
}

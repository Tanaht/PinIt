import './vendor.ts';

import {Injector, isDevMode, NgModule, ReflectiveInjector} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
    MatButtonModule, MatCardModule, MatDialogModule, MatIconModule, MatIconRegistry, MatListModule, MatMenuModule,
    MatSidenavModule,
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
import { HomeComponent } from './home/home.component';
import {AuthenticationService} from './authentication/authentication.service';
import {HttpClientModule} from '@angular/common/http';
import { MarkerEditorComponent } from './marker-editor/marker-editor.component';

@NgModule({
    declarations: [
        AppComponent,
        MapComponent,
        HomeComponent,
        MarkerEditorComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,

        CommonModule,
        ReactiveFormsModule,
        HttpClientModule,
        HttpModule,
        MatToolbarModule,
        MatDialogModule,
        MatCardModule,
        MatSidenavModule,
        MatButtonModule,
        MatIconModule,
        MatListModule,
        MatMenuModule,
        MatButtonModule,

        AgmCoreModule.forRoot({
            apiKey: AppModule.mapApiKey
        }),

        FlexLayoutModule,

        AuthenticationModule,
    ],
    entryComponents: [MarkerEditorComponent],
    providers: [
        MatIconRegistry, RestService, LoggerService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
    static mapApiKey: string;
    static injector: Injector;

    constructor(iconReg: MatIconRegistry, auth: AuthenticationService, injector: Injector) {
        AppModule.mapApiKey = 'AIzaSyCQaM6Mw4t5EbZVhbab3mBuWWROC_pcNT0';

        AppModule.injector = injector;
        iconReg.registerFontClassAlias('fontawesome', 'fa');

        // Generate values for dev purpose
        if ( isDevMode()) {
            auth.authenticate('user', '123456');
        }
    }
}

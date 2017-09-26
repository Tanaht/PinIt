import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
    MdButtonModule, MdCardModule, MdIconModule, MdIconRegistry, MdListModule, MdMenuModule, MdSidenavModule,
    MdToolbarModule
} from '@angular/material';

import {AuthenticationModule} from './authentication/authentication.module';
import {AppRoutingModule} from './app-routing.module';

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,

        MdToolbarModule,
        MdCardModule,
        MdSidenavModule,
        MdButtonModule,
        MdIconModule,
        MdListModule,
        MdMenuModule,
        MdButtonModule,
        AuthenticationModule
    ],
    providers: [
        MdIconRegistry
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
    constructor(iconReg: MdIconRegistry) {
        iconReg.registerFontClassAlias('fontawesome', 'fa');
    }
}

import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
    MdButtonModule, MdCardModule, MdIconModule, MdIconRegistry, MdListModule, MdSidenavModule,
    MdToolbarModule
} from '@angular/material';

// jhipster-needle-angular-add-module-import JHipster will add new module here

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        MdToolbarModule,
        MdCardModule,
        MdSidenavModule,
        MdButtonModule,
        MdIconModule,
        MdListModule
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

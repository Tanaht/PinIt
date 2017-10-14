import { Component } from '@angular/core';
import {LoggerService} from './logger/logger.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private log: LoggerService, private router: Router) {
    this.router.events.subscribe((value) => { this.log.debug("AppComponent", "VALUE", value); }, (error) => { this.log.debug("AppComponent", "ERROR", error); });
  }

  onActivate(component: Component): void {
    this.log.debug("AppComponent", component);
  }
}

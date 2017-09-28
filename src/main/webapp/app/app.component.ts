import { Component } from '@angular/core';
import {RestService} from './rest/rest.service';
import {LoggerService} from './logger/logger.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(rest: RestService, logger: LoggerService) {
  }
}

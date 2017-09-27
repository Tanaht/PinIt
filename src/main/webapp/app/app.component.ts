import { Component } from '@angular/core';
import {RestService} from './rest/rest.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  constructor(rest: RestService) {
      rest.send('/api/authenticate/login', {
          login: 'user',
          password: 'user'
      }, null);
  }
}

import {Component, OnInit, AfterViewInit, DoCheck, AfterContentInit} from '@angular/core';
import {AuthenticationService} from '../authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-logout',
  template: '',
  styles: []
})
export class LogoutComponent {

  constructor(private auth: AuthenticationService, private router: Router) {
      this.auth.logout();
      this.router.navigateByUrl('/');
  }

}

import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../authentication.service';

@Component({
  selector: 'app-logout',
  template: '',
  styles: []
})
export class LogoutComponent implements OnInit {

  constructor(private auth: AuthenticationService) { }

  ngOnInit() {
    throw new Error('Method Not Implemented');
  }

}

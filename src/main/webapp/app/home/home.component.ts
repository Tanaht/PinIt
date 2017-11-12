import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../authentication/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styles: []
})
export class HomeComponent implements OnInit {

  constructor(public auth: AuthenticationService) {}

  ngOnInit() {
  }

}

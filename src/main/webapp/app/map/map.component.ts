import { Component, OnInit } from '@angular/core';
import {ActivityMarker} from '../model/activity-marker';
import {RestService} from '../rest/rest.service';
import {LoggerService} from '../logger/logger.service';
import {AuthenticationService} from '../authentication/authentication.service';

@Component({
  selector: 'map-view',
  templateUrl: './map.component.html',
  styleUrls: ['../router-outlet-component-layout.css', './map.component.css'],
})
export class MapComponent implements OnInit {
  lat: number;
  long: number;
  markers: ActivityMarker[];

  constructor(private rest: RestService, private logger: LoggerService, private auth: AuthenticationService) {}

  ngOnInit() {
    this.lat = 46.1369301;
    this.long = -2.4342062;

    this.loadMarkers();
  }

  private loadMarkers(): void {
      this.rest.retrieve("/api/inscriptions/" + this.auth.getUser().id).subscribe((value) => { this.logger.debug("MapComponent", value); });
      // here load markers from rest, but now we used a static source;
      this.markers = [
          new ActivityMarker(49.335, -0.454, "Surf"),
          new ActivityMarker(48.8544586, 2.3388662, "Balade en ville"),
          new ActivityMarker(45.892, -3.093, "Noyade"),
          new ActivityMarker(48.1148993, -1.6370679, "Etude"),
      ];
  }
}

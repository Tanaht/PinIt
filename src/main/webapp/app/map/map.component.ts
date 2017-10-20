import {Component, EventEmitter, OnInit, ViewChild} from '@angular/core';
import {ActivityMarker} from '../model/activity-marker';
import {RestService} from '../services/rest/rest.service';
import {LoggerService} from '../logger/logger.service';
import {AuthenticationService} from '../authentication/authentication.service';
import {AgmMap} from '@agm/core';
import {MatDialog} from '@angular/material';
import {MarkerEditorComponent} from '../marker-editor/marker-editor.component';
import {Intent} from '../model/Intent';
import {Activity} from '../model/activity';

@Component({
    selector: 'map-view',
    templateUrl: './map.component.html',
    styleUrls: ['../router-outlet-component-layout.css', './map.component.css']
})
export class MapComponent implements OnInit {
  lat: number;
  long: number;
  zoom: number;

  markers: ActivityMarker[];

  @ViewChild(AgmMap)
  private agmMap: AgmMap;

  constructor(private rest: RestService, private logger: LoggerService, private auth: AuthenticationService, private dialog: MatDialog) {}

  ngOnInit() {
    this.lat = 46.1369301;
    this.long = 1.5648173;
    this.zoom = 6;
    this.agmMap.usePanning = true;

    this.logger.debug("MapComponent", this.agmMap);
    this.loadMarkers();
  }

  public zoomOn(marker: ActivityMarker): void {
      this.zoom = 12;
      this.lat = marker.lat;
      this.long = marker.long;
  }

  private loadMarkers(): void {
      this.rest.retrieve('/api/users/' + this.auth.getUser().id + '/inscriptions').map(function(res) {
          let array: object[];
          array = res as object[];

          return array.map(function(item) {
              return new ActivityMarker(
                  item['localisation'].id,
                  item['localisation'].latitude,
                  item['localisation'].longitude,
                  new Activity(item['activity'].id, item['activity'].nameActivity)
              );
          });
      }).subscribe((value) => {
          this.logger.debug('MapComponent', value);
          this.markers = value;
      });
  }

  public edit(marker: ActivityMarker): void {
      this.dialog.open(MarkerEditorComponent, {
          data: marker
      }).afterClosed().subscribe((intent: Intent) => {
          if (intent === Intent.Close) {
                // TODO: this.rest.update("/users/" + this.auth.getUser().id + "/inscriptions/" + marker.id, marker);
          }
      });
  }

  public addActivity($event: EventEmitter<MouseEvent>): void {
      this.logger.info("MapComponent#AddActivity()", "Add a new Activity at: " + $event['coords'].lat + ', ' + $event['coords'].lng);

      const marker = new ActivityMarker(null, $event['coords'].lat, $event['coords'].lng, null);

      const dialogRef = this.dialog.open(MarkerEditorComponent, {
          data: marker
      });
      dialogRef.afterClosed().subscribe((intent: Intent) => {
          switch (intent) {
              case Intent.Close:
                  // TODO call CREATE Marker here
                  this.markers.push(marker);
                  break;
          }
      });
  }
}

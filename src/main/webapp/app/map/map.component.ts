import {Component, EventEmitter, OnInit, ViewChild} from '@angular/core';
import {ActivityMarker} from '../model/activity-marker';
import {RestService} from '../services/rest/rest.service';
import {LoggerService} from '../logger/logger.service';
import {AuthenticationService} from '../authentication/authentication.service';
import {AgmMap} from '@agm/core';
import {MatDialog, MatSnackBar, MatSnackBarConfig} from '@angular/material';
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

  constructor(private rest: RestService, private logger: LoggerService, private auth: AuthenticationService, private dialog: MatDialog, private snackbar: MatSnackBar) {}

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
                  item['id'],
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

  public editMarker(marker: ActivityMarker, newLat?: number, newLong?: number): void {

      const markerClone: ActivityMarker = marker;

      if (newLat !== undefined && newLong !== undefined) {
         this.logger.debug("MapComponent#editMarker", "Perform update of LatLng values");
         marker.lat = newLat;
         marker.long = newLong;
      }

      // TODO: refactor snackbar call (mutualize call to it)
      const config = new MatSnackBarConfig();
      config.verticalPosition = 'bottom';
      config.horizontalPosition = 'right';
      config.duration = 2000;

      this.dialog.open(MarkerEditorComponent, {
          data: marker,
          width: "500px"
      }).afterClosed().subscribe((intent: Intent) => {
          if (intent === Intent.Close) {
              this.rest.update("/api/inscriptions/" + marker.id, {
                  activityId: marker.activity.id,
                  coordonne: {
                      latitude: marker.lat,
                      longitude: marker.long,
                  }
              }).subscribe( null, (error) => {
                  marker = markerClone;
                  config.extraClasses = ['pi-snackbar-warn'];
                  this.snackbar.open("Failed to update marker, perhaps the server is down ?", null, config);
              });
          }
      });
  }

  public editMarkerCoordinate($event: EventEmitter<MouseEvent>, marker: ActivityMarker): void {
      this.editMarker(marker, $event['coords'].lat, $event['coords'].lng);
  }

  public delete(marker: ActivityMarker): void {
      this.logger.info("MapComponent#delete()", "Remove Activity " + marker.id + "at: " + marker.lat + ', ' + marker.long);

      // TODO: refactor snackbar call (mutualize call to it)
      const config = new MatSnackBarConfig();
      config.verticalPosition = 'bottom';
      config.horizontalPosition = 'right';
      config.duration = 2000;

      this.rest.delete("/api/inscriptions/" + marker.id).subscribe((result) => {
          const idx: number = this.markers.indexOf(marker);
          if (idx !== -1) {
              this.markers.splice(idx, 1);
          } else {
              this.logger.debug("MapComponent#delete()", "Unable to find Marker in Markers, that should not happen.");
          }
      }, (error) => {
          this.logger.warn("MapComponent#delete()", "unable to remove an activityMarker");

          config.extraClasses = ['pi-snackbar-warn'];
          this.snackbar.open("Failed to remove marker, perhaps the server is down ?", null, config);
      });
  }

  public addActivity($event: EventEmitter<MouseEvent>): void {
      this.logger.info("MapComponent#AddActivity()", "Add a new Activity at: " + $event['coords'].lat + ', ' + $event['coords'].lng);

      const marker = new ActivityMarker(null, $event['coords'].lat, $event['coords'].lng, null);

      const config = new MatSnackBarConfig();
      config.verticalPosition = 'bottom';
      config.horizontalPosition = 'right';
      config.duration = 2000;

      const dialogRef = this.dialog.open(MarkerEditorComponent, {
          data: marker,
          width: "500px"
      });
      dialogRef.afterClosed().subscribe((intent: Intent) => {
          switch (intent) {
              case Intent.Close:
                  this.rest.create("/api/users/" + this.auth.getUser().id + "/inscriptions", {
                      activityId: marker.activity.id,
                      coordonne: {
                          latitude: marker.lat,
                          longitude: marker.long,
                      }
                  }).map(function(res) {
                      return res['id']
                  }).subscribe((id: number) => {
                      marker.id = id;
                      this.markers.push(marker);
                  }, (error) => {
                      config.extraClasses = ['pi-snackbar-warn'];
                      this.snackbar.open("Failed to add new marker, perhaps the server is down ?", null, config);
                  });
                  break;
          }
      });
  }
}

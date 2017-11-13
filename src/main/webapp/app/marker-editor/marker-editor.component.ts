import {Component, ElementRef, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef, MatInput} from '@angular/material';
import {LoggerService} from '../logger/logger.service';
import {ActivityMarker} from '../model/activity-marker';
import {Intent} from '../model/Intent';
import {ActivityProviderService} from '../services/activity-provider/activity-provider.service';
import {Activity} from '../model/activity';

@Component({
  selector: 'app-marker-editor',
  templateUrl: './marker-editor.component.html',
  styles: []
})
export class MarkerEditorComponent implements OnInit {
    activities: Array<Activity>;
    marker: ActivityMarker;
    @ViewChild("addressSearch") addressSearchElement: ElementRef;

    private autocomplete: google.maps.places.Autocomplete;

    constructor(
        public dialogRef: MatDialogRef<MarkerEditorComponent>,
        @Inject(MAT_DIALOG_DATA) public data: ActivityMarker,
        private logger: LoggerService,
        private activityProvider: ActivityProviderService) {

        this.marker = data;
    }

    byID(item1: Activity, item2: Activity) {
        return item1.id === item2.id;
    }

    cancel(): void {
        this.dialogRef.close(Intent.Dismiss);
    }

    updateMarker(): void {
        this.dialogRef.close(Intent.Close);
    }

    ngOnInit() {
        this.activityProvider.getActivities().subscribe((activities: Array<Activity>) => {
            this.logger.debug("MarkerEditorComponent#ngOnInit", "Successfully retrieved activities");
            this.activities = activities;
        });

        this.marker.updateAddress().subscribe((res) => {
            this.logger.debug("SUBSCRIBRE UPDATE ADDRESS", res, this.marker);
            this.autocomplete = new google.maps.places.Autocomplete(this.addressSearchElement.nativeElement, {
                types: ['address'],
                componentRestrictions: {country: 'fr'}
            });

            const self = this;
            this.autocomplete.addListener("place_changed", function() {
                const result = self.autocomplete.getPlace();

                if (result.formatted_address !== undefined) {
                    self.marker.address = result.formatted_address;
                    self.marker.lat = result.geometry.location.lat();
                    self.marker.long = result.geometry.location.lng();

                } else {
                    self.logger.debug("Undefined");
                }
            });
        });
    }

}

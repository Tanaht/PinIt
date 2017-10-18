import {Component, Inject, OnInit, ViewChild, ViewRef} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {LoggerService} from '../logger/logger.service';
import {ActivityMarker} from '../model/activity-marker';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
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
      // this.marker.activity = ...
      this.dialogRef.close(Intent.Close);
  }

  ngOnInit() {
      this.activityProvider.getActivities().subscribe((activities: Array<Activity>) => {
          this.logger.debug("MarkerEditorComponent#ngOnInit", "Successfully retrieved activities");
          this.activities = activities;
      });
  }

}

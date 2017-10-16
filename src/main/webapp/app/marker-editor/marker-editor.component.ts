import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material';
import {LoggerService} from '../logger/logger.service';
import {ActivityMarker} from '../model/activity-marker';

@Component({
  selector: 'app-marker-editor',
  templateUrl: './marker-editor.component.html',
  styles: []
})
export class MarkerEditorComponent implements OnInit {
  marker: ActivityMarker;

  constructor(@Inject(MAT_DIALOG_DATA) public passedMarker: ActivityMarker, private logger: LoggerService) {
      this.marker = passedMarker;
  }

  ngOnInit() {
  }

}

import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'map-view',
  templateUrl: './map.component.html',
  styles: ['mat-card-content{height: 300px}']
})
export class MapComponent implements OnInit {
  lat: number;
  long: number;

  constructor() { }

  ngOnInit() {
    this.lat = 48.115;
    this.long = -1.638;
  }

}

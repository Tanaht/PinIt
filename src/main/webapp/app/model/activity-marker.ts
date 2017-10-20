import {AppModule} from '../app.module';
import {RestService} from '../services/rest/rest.service';
import {Activity} from './activity';

export class ActivityMarker {
    id: number;

    lat: number;
    long: number;
    activity: Activity;
    address: string;

    rest: RestService;

    constructor(id: number, lat: number, long: number, activity: Activity) {
        this.id = id;
        this.lat = lat;
        this.long = long;
        this.activity = activity;

        this.rest = AppModule.injector.get(RestService);
        this.updateAddress();
    }

    private updateAddress(): void {
        console.log("URI: " + 'https://maps.googleapis.com/maps/api/geocode/json?latlng=' + this.lat + ',' + this.long + '&key=' + AppModule.mapApiKey);
        this.rest.get('https://maps.googleapis.com/maps/api/geocode/json?latlng=' + this.lat + ',' + this.long + '&key=' + AppModule.mapApiKey).subscribe((res) => {

            if (res.status === "OK") {
                this.address = res.results[0].formatted_address;
            } else {
                this.address = "";
            }
        });
    }
}

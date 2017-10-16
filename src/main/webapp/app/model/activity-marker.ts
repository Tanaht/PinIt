import {AppModule} from '../app.module';
import {RestService} from '../rest/rest.service';

export class ActivityMarker {
    lat: number;
    long: number;
    sport: string;
    address: string;

    rest: RestService;

    constructor(lat: number, long: number, sport: string) {
        this.lat = lat;
        this.long = long;
        this.sport = sport;

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

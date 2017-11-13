import { Injectable } from '@angular/core';
import {LoggerService} from '../../logger/logger.service';
import {RestService} from '../rest/rest.service';
import {Activity} from '../../model/activity';
import {Observable} from 'rxjs/Observable';
import {Subscriber} from 'rxjs/Subscriber';

@Injectable()
export class ActivityProviderService {

  private activities: Array<Activity>;

  constructor(private logger: LoggerService, private rest: RestService) {
  }

  getActivities(): Observable<Array<Activity>> {
    return new Observable<Array<Activity>>((observer: Subscriber<Array<Activity>>) => {
      if (this.activities != null) {
        observer.next(this.activities);
      } else {
          this.rest.retrieve("/api/activities").map((data: Array<Activity>) => {
            return data.map((item) =>  new Activity(item.id, item.nameActivity));
          }).subscribe((data: Array<Activity>) => {
              this.logger.debug("ActivityProviderService", data);
              this.activities = data;
              observer.next(this.activities);
          }, (err) => {
              observer.error(err);
          });
      }
    });
  }

}

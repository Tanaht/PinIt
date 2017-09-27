import {Injectable} from '@angular/core';
import {LoggerService} from '../logger/logger.service';
import {Http} from '@angular/http';
@Injectable()
export class RestService {

  constructor(private logger: LoggerService, private http: Http) {}

  public onSuccess(data: object): void {
    this.logger.debug("RestService", "Success request");
  }

  public onError(): void {
      this.logger.error("RestService", "Error request");
  }

  public require(uri: string, obtain: Function): void {

  }

  public send(uri: string, data: object, obtain: Function): void {
    this.http.post(uri, data).subscribe((data) => {
        this.onSuccess(data);
    })
  }

}

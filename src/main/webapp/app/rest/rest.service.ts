import {Injectable, Injector} from '@angular/core';
import {LoggerService} from '../logger/logger.service';
import {Headers, Http, RequestOptionsArgs, Response} from '@angular/http';
import {Observable, Subscriber} from 'rxjs';
import {AuthenticationService} from '../authentication/authentication.service';

@Injectable()
export class RestService {
    private headers: Headers;

    constructor(private logger: LoggerService, private http: Http, private auth: AuthenticationService) {
        this.headers = new Headers({'Content-Type': 'application/json'});
    }

    public onSuccess(data: object): void {
      this.logger.debug("RestService", data)
    }

    public onError(data: object): void {
      this.logger.error("RestService", data);
    }

    private manipulateHeaders(): void {
        if ( this.auth.isAuthenticated()) {
            this.headers.append("token", this.auth.getUser().token);
        } else {
            this.headers.delete("token");
        }
    }

    public create(uri: string, datas: object): Observable<object> {
        this.manipulateHeaders();
        return new Observable<object>((observer: Subscriber<object>) => {

            this.http.post(uri, datas, {headers: this.headers}).subscribe((data) => {
                    this.onSuccess(data);
                    observer.next(data);
                },
                (error) => {
                    this.onError(error);
                    observer.error(error);
                });
        });
    }

    public retrieve(uri: string): Observable<object> {
        this.manipulateHeaders();
        return new Observable<object>((observer: Subscriber<object>) => {

          this.http.get(uri, {headers: this.headers}).subscribe((data) => {
                  this.onSuccess(data);
                  observer.next(data);
              },
              (error) => {
                  this.onError(error);
                  observer.error(error);
              });
        });
    }

    public update(uri: string, datas: object): Observable<object> {
        this.manipulateHeaders();
        return new Observable<object>((observer: Subscriber<object>) => {

          this.http.put(uri, datas, {headers: this.headers}).subscribe((data) => {
                  this.onSuccess(data);
                  observer.next(data);
              },
              (error) => {
                  this.onError(error);
                  observer.error(error);
              });
        });
    }

    public delete(uri: string): Observable<object> {
        this.manipulateHeaders();
        return new Observable<object>((observer: Subscriber<object>) => {

            this.http.delete(uri, {headers: this.headers}).subscribe((data) => {
                    this.onSuccess(data);
                    observer.next(data);
                },
                (error) => {
                    this.onError(error);
                    observer.error(error);
                });
        });
    }

    public get(url: string, options?: RequestOptionsArgs): Observable<Response> {
        return this.http.get(url, options);
    }

    public post(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
        return this.http.post(url, body, options);
    }

    public put(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
        return this.http.put(url, body, options);
    }
}

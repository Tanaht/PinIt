import {Injectable, Injector} from '@angular/core';
import {LoggerService} from '../../logger/logger.service';
import {Headers, Http, RequestOptionsArgs, Response} from '@angular/http';
import {Observable, Subscriber} from 'rxjs';
import {AuthenticationService} from '../../authentication/authentication.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class RestService {
    private headers: HttpHeaders;

    constructor(private logger: LoggerService, private http: HttpClient, private auth: AuthenticationService) {
        this.headers = new HttpHeaders().set('Content-Type', 'application/json');
    }

    public onSuccess(data: object): void {
      this.logger.debug("RestService", data)
    }

    public onError(data: object): void {
      this.logger.error("RestService", data);
    }

    private manipulateHeaders(): void {
        if ( this.auth.isAuthenticated()) {
            this.headers = this.headers.set("token", this.auth.getUser().token);
        } else {
            this.headers = this.headers.delete("token");
        }
    }

    public create(uri: string, datas: object): Observable<object> {
        this.manipulateHeaders();
        return new Observable<object>((observer: Subscriber<object>) => {

            this.http.post(uri, datas, {headers: this.headers, responseType: 'json'}).subscribe((data) => {
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

          this.http.get(uri, {headers: this.headers, responseType: 'json'}).subscribe((data) => {
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

          this.http.put(uri, datas, {headers: this.headers, responseType: 'json'}).subscribe((data) => {
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

            this.http.delete(uri, {headers: this.headers, responseType: 'json'}).subscribe((data) => {
                    this.onSuccess(data);
                    observer.next(data);
                },
                (error) => {
                    this.onError(error);
                    observer.error(error);
                });
        });
    }

    public get(url: string, options?: object): Observable<any> {
        return this.http.get(url, options);
    }

    public post(url: string, body: any, options?: object): Observable<any> {
        return this.http.post(url, body, options);
    }

    public put(url: string, body: any, options?: object): Observable<any> {
        return this.http.put(url, body, options);
    }
}

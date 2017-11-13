import {Injectable, Injector} from '@angular/core';
import {LoggerService} from '../../logger/logger.service';
import {Observable, Subscriber} from 'rxjs';
import {AuthenticationService} from '../../authentication/authentication.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {MatSnackBar, MatSnackBarConfig} from '@angular/material';
import {Router} from '@angular/router';

@Injectable()
export class RestService {
    private headers: HttpHeaders;

    constructor(private logger: LoggerService, private http: HttpClient, private auth: AuthenticationService, private snackbar: MatSnackBar, private router: Router) {
        this.headers = new HttpHeaders().set('Content-Type', 'application/json');
    }

    public onSuccess(data: object): void {
      this.logger.debug('RestService', 'Request successfully ended');
    }

    public onError(data: object): void {

        if (data['status'] === 403 && data['statusText'] === 'Forbidden' && this.auth.getUser() != null) {
            const config = new MatSnackBarConfig();
            config.verticalPosition = 'bottom';
            config.horizontalPosition = 'right';
            config.duration = 4000;
            config.extraClasses = ['pi-snackbar-warn'];

            this.logger.error('Le token utilisateur à été corrompu ou à expirer. une reconnexion est requise !');
            this.snackbar.open("Vous n\'êtes plus connecté au serveur, une reconnexion est requise !", null, config);
            this.auth.logout();
            this.router.navigateByUrl("/home");

        } else {
            this.logger.error('RestService', data);
        }
    }

    private manipulateHeaders(): void {
        if ( this.auth.isAuthenticated()) {
            this.headers = this.headers.set('token', this.auth.getUser().token);
        } else {
            this.headers = this.headers.delete('token');
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

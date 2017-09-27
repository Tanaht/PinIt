import { Injectable } from '@angular/core';
import {RestService} from '../rest/rest.service';
import {LoggerService} from '../logger/logger.service';

@Injectable()
export class AuthenticationService {
    private token: string;
    constructor(private rest: RestService, private logger: LoggerService) {}

    public authenticate(username: string, password: string): void {
        this.rest.post('/api/authenticate/login', { login: username, password: password}).subscribe((data) => {
            this.token = data.json().token;
            this.logger.debug('TOKEN', this.token);
        })
    }
}

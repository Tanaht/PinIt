import {Authority} from './authority';

export class User {
    public id: number;
    public firstname: string;
    public lastname: string;
    public email: string;
    public roles: Authority[];

    public token: string;

    public username: string;

    constructor(id: number, username: string, roles: Authority[], token: string, email?: string) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.token = token;
        this.email = email;
    }

    public hasRole(role: string): boolean {
        return this.roles.filter(function(authority: Authority) {
            return authority.authority === role;
        }).length > 0;
    }

}

export class User {
    public firstname: string;
    public lastname: string;
    public email: string;
    public roles: string[];

    public token: string;

    public username: string;

    constructor(username: string, token: string) {
        this.username = username;
        this.token = token;
    }

    public hasRole(role: string): boolean {
        return this.roles.indexOf(role) !== -1;
    }

}

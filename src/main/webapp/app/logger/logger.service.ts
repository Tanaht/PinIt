import {Injectable, isDevMode} from '@angular/core';

@Injectable()
export class LoggerService {

  public info(what: string, ...messages: any[]): void {

      console.info("[" + what + "]", messages);
  }

  public error(what: string, ...messages: any[]): void {
      console.error("[" + what + "]", messages);
  }

  public warn(what: string, ...messages: any[]): void {
      if ( !isDevMode()) {
          return;
      }

      console.warn("[" + what + "]", messages);
  }

  public debug(what: string, ...messages: any[]): void {
      if ( !isDevMode()) {
          return;
      }

      console.debug("[" + what + "]", messages);
  }

}

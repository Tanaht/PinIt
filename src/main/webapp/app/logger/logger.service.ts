import {Injectable, isDevMode} from '@angular/core';
import { environment } from '../../../../../environments/environment';

@Injectable()
export class LoggerService {

  public info(what: string, message: string):void {
      if(!isDevMode())
          return;

      console.info("[" + what + "]:" + message);
  }

  public error(what: string, message: string): void{
      console.error("[" + what + "]:" + message);
  }

  public warn(what: string, message: string):void {
      if(!isDevMode())
          return;

      console.warn("[" + what + "]:" + message);
  }

  public debug(what: string, message: string):void {
      if(!environment.debug)
        return;

      console.debug("[" + what + "]:" + message);
  }

}

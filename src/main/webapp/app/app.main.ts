import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import {AppModule} from './app.module';

if (module['hot']) {
    module['hot'].accept();
}

// enableProdMode();

platformBrowserDynamic().bootstrapModule(AppModule)
.then((success) => console.log(`Application started`))
.catch((err) => console.error(err));

import { Component } from '@angular/core';
import { GlobalConstants } from './global-constants';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  logged = GlobalConstants.logged;
  constructor(public globalConstants: GlobalConstants) {}
  logout() {
    this.globalConstants.logout();
  }
}

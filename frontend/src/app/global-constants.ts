import {Component, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalConstants {
  public static logged = false;
  public getLoggedInfo(): boolean {
    return GlobalConstants.logged;
  }
  public login() {
    GlobalConstants.logged = true;
  }
  public logout() {
    GlobalConstants.logged = false;
  }
}

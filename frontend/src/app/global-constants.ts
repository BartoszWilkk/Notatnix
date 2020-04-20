import {Component, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalConstants {
  public static logged = false;
  public static user: string = null;
  public getLoggedInfo(): boolean {
    return GlobalConstants.logged;
  }
  public login(newId: string) {
    GlobalConstants.logged = true;
    this.setOwnerId(newId);
  }
  public logout() {
    GlobalConstants.logged = false;
    this.setOwnerId(null);
  }
  public getOwnerId(): string {
    return GlobalConstants.user;
  }
  public setOwnerId(newId: string) {
    GlobalConstants.user = newId;
  }
}

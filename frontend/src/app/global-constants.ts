import {Component, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalConstants {
  public static logged = false;
  public static user: string = null;
  public static role: string = null;
  public getLoggedInfo(): boolean {
    return GlobalConstants.logged;
  }
  public login(newId: string, newRole: string) {
    GlobalConstants.logged = true;
    this.setOwnerId(newId);
    this.setRole(newRole);
  }
  public logout() {
    GlobalConstants.logged = false;
    this.setOwnerId(null);
    this.setRole(null);
  }
  public getOwnerId(): string {
    return GlobalConstants.user;
  }
  public setOwnerId(newId: string) {
    GlobalConstants.user = newId;
  }
  public getRole(): string {
    return GlobalConstants.role;
  }
  public setRole(role: string) {
    GlobalConstants.role = role;
  }
  public getAdminRoleInfo(): boolean {
    if (GlobalConstants.role == null) {
      return false;
    } else {
      return GlobalConstants.role === 'admin';
    }
  }
}

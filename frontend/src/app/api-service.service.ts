import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {
  private BASE_URL = 'http://localhost:8080/home';

  constructor(private http: HttpClient) { }
  getHome(): Observable<any> {
    return this.http.get(this.BASE_URL, {responseType: 'text'});
  }
}

import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Note} from '../model/note';
import {Credentials} from '../model/credentials';
import {User} from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {
  private BASE_URL = 'http://localhost:8089';

  private ALL_NOTES = `${this.BASE_URL}/note/getAll`;
  private GET_MY_NOTES = `${this.BASE_URL}/note/getMyNotes/`;
  private SAVE_NOTE = `${this.BASE_URL}/note/save`;
  private GET_NOTE = `${this.BASE_URL}/note/get/`;
  private EDIT_NOTE = `${this.BASE_URL}/note/edit/`;
  private EDIT_NOTE_CHECK_TITLE = `${this.BASE_URL}/note/edit/checkTitle/`;
  private LOGIN = `${this.BASE_URL}/user/login/`;
  private REGISTRY = `${this.BASE_URL}/user/save/`;
  private GET_USER = `${this.BASE_URL}/user/get/`;

  constructor(private http: HttpClient) { }

  getAllNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(this.ALL_NOTES);
  }

  getNote(id): Observable<Note> {
    return this.http.get<Note>(this.GET_NOTE + id);
  }

  getMyNotes(id): Observable<Note[]> {
    return this.http.get<Note[]>(this.GET_MY_NOTES + id);
  }

  saveNote(note: Note): Observable<Note> {
    return this.http.post<Note>(this.SAVE_NOTE, note);
  }

  editNote(note: Note, checkTitle: boolean): Observable<Note> {
    if (checkTitle) {
      return this.http.post<Note>(this.EDIT_NOTE_CHECK_TITLE + note.id, note);
    } else { return this.http.post<Note>(this.EDIT_NOTE + note.id, note); }
  }

  login(credentials: Credentials): Observable<User> {
    return this.http.post<User>(this.LOGIN, credentials);
  }

  registry(user: User): Observable<User> {
    return this.http.post<User>(this.REGISTRY, user);
  }

  getUserById(id: string): Observable<User> {
    return this.http.get<User>(this.GET_USER + id);
  }
}

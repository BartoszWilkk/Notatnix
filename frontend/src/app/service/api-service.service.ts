import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Note} from '../model/note';
import {Credentials} from '../model/credentials';
import {User} from '../model/user';
import {FileModel} from '../model/fileModel';

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {
  private BASE_URL = 'http://localhost:8088';

  private ALL_NOTES = `${this.BASE_URL}/note/getAll`;
  private GET_MY_NOTES = `${this.BASE_URL}/note/getMyNotes/`;
  private SAVE_NOTE = `${this.BASE_URL}/note/save`;
  private GET_NOTE = `${this.BASE_URL}/note/get/`;
  private EDIT_NOTE = `${this.BASE_URL}/note/edit/`;
  private EDIT_NOTE_CHECK_TITLE = `${this.BASE_URL}/note/edit/checkTitle/`;

  private LOGIN = `${this.BASE_URL}/user/login/`;
  private REGISTRY = `${this.BASE_URL}/user/save/`;
  private GET_USER = `${this.BASE_URL}/user/get/`;

  private SAVE_FILE = `${this.BASE_URL}/file/save/`;
  private GET_FILE_BY_NOTE = `${this.BASE_URL}/file/getByNoteId/`;
  public DOWNLOAD_FILE = `${this.BASE_URL}/file/get/`;

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

  saveFile(file: File, id: string): Observable<HttpEvent<{}>> {
    const data: FormData = new FormData();
    data.append('file', file);
    const newRequest = new HttpRequest('POST', this.SAVE_FILE + id, data, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.http.request(newRequest);
  }

  getFilesByNoteId(id: string): Observable<FileModel[]> {
    return this.http.get<FileModel[]>(this.GET_FILE_BY_NOTE + id);
  }

  downloadFile(id: string) {
    return this.http.get<File>(this.DOWNLOAD_FILE + id);
  }
}

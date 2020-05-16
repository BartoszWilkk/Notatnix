import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Note} from '../model/note';
import {Credentials} from '../model/credentials';
import {User} from '../model/user';
import {FileModel} from '../model/fileModel';
// import {resolve} from 'url';
// import * as url from 'url';
import {Tag} from '../model/tag';
import {Rate} from '../model/rate';
import {RateId} from '../model/rate-id';
// import {url} from 'inspector';

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {
  BASE_URL = 'http://localhost:8088';

   ALL_NOTES = `${this.BASE_URL}/note/getAll`;
   GET_MY_NOTES = `${this.BASE_URL}/note/getMyNotes/`;
   SAVE_NOTE = `${this.BASE_URL}/note/save`;
   GET_NOTE = `${this.BASE_URL}/note/get/`;
   EDIT_NOTE = `${this.BASE_URL}/note/edit/`;
   EDIT_NOTE_CHECK_TITLE = `${this.BASE_URL}/note/edit/checkTitle/`;
  DELETE_NOTE = `${this.BASE_URL}/note/delete/`;

   ALL_TAGS = `${this.BASE_URL}/tag/getAll`;

   SAVE_RATE = `${this.BASE_URL}/rate/save`;
   GET_RATE = `${this.BASE_URL}/rate/getMyRate`;

   LOGIN = `${this.BASE_URL}/user/login/`;
   REGISTRY = `${this.BASE_URL}/user/save/`;
   GET_USER = `${this.BASE_URL}/user/get/`;

   SAVE_FILE = `${this.BASE_URL}/file/save/`;
   GET_FILE_BY_NOTE = `${this.BASE_URL}/file/getByNoteId/`;
   DOWNLOAD_FILE = `${this.BASE_URL}/file/get/`;

  constructor(public http: HttpClient) { }

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

  getAllTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(this.ALL_TAGS);
  }

  saveRate(rate: Rate): Observable<string> {
    return this.http.post<string>(this.SAVE_RATE, rate);
  }

  getMyRate(rateId: RateId): Observable<string> {
    return this.http.post<string>(this.GET_RATE, rateId);
  }

  deleteNote(id: string) {
    return this.http.get(this.DELETE_NOTE + id);
  }

  downloadFile(id: string): Observable<File> {
    return this.http.get<File>(this.DOWNLOAD_FILE + id);
  }

  // downloadFile2(id: string): string {
  //   let fileUrl;
  //   this.http.get(this.DOWNLOAD_FILE + id, {responseType: 'blob'}).toPromise().then((res) => {
  //     fileUrl = (window.URL).createObjectURL(res);
  //     resolve({file: fileUrl, type: url.type, name: url.name});
  //   });
  //   return fileUrl;
  // }
}

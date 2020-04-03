import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Note} from '../model/note';

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {
  private BASE_URL = 'http://localhost:8080';

  private ALL_NOTES = `${this.BASE_URL}/note/getAll`;
  private SAVE_NOTE = `${this.BASE_URL}/note/save`;

  constructor(private http: HttpClient) { }

  getAllNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(this.ALL_NOTES);
  }

  saveNote(note: Note): Observable<Note> {
    return this.http.post<Note>(this.SAVE_NOTE, note);
  }
}

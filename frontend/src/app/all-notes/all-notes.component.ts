import { Component, OnInit } from '@angular/core';
import {Note} from '../model/note';
import {ApiServiceService} from '../service/api-service.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-all-notes',
  templateUrl: './all-notes.component.html',
  styleUrls: ['./all-notes.component.css']
})
export class AllNotesComponent implements OnInit {

  private notes: Note[];

  constructor(private apiService: ApiServiceService, private router: Router) { }

  ngOnInit() {
    this.getAllNotes();
  }

  // getUsernameById(id): string {
  //   let username = '';
  //   this.apiService.getUserById(id).subscribe(
  //     res => {
  //       console.log(res.username);
  //       username = res.username;
  //     },
  //     error => {
  //       alert('error w metodzie getUsernameById(id) w komponencie AllNotesComponent');
  //     }
  //   );
  //   return username;
  // }

  public getAllNotes() {
    this.apiService.getAllNotes().subscribe(
      res => {
        this.notes = res;
      },
      err => {
        alert('An error has occurred in method getAllNotes() in AllNotesComponent');
      }
    );
  }

  public openNote(id) {

    this.router.navigate(['/app-view-note', id]);
  }

}
